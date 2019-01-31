package com.quandoo.androidtask.data

import com.quandoo.androidtask.api.RestaurantService
import com.quandoo.androidtask.api.responses.CustomerResp
import com.quandoo.androidtask.data.models.Customer
import com.quandoo.androidtask.data.models.Reservation
import com.quandoo.androidtask.data.models.Table
import com.quandoo.androidtask.data.room.AppDataBase
import com.quandoo.androidtask.data.room.customers.CustomerDto
import com.quandoo.androidtask.data.room.reservations.ReservationDto
import com.quandoo.androidtask.data.room.tables.TableDto
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.functions.BiFunction


class DataManager(private val restaurantService: RestaurantService, private val appDataBase: AppDataBase) {

    fun getUpdatedTableList(): Observable<List<Table>> {
        return getAllReservations()
                .flatMapIterable { reservations ->
                    reservations
                }
                .flatMapCompletable {
                    findReservedTableByCustomer(it.tableId, it.userId).toCompletable()
                }
                .andThen(getAllTables())
    }

    private fun findReservedTableByCustomer(tableId: Long, customerId: Long): Single<Unit> {
        return Single.zip(findCustomerById(customerId),
                findTableById(tableId),
                BiFunction<CustomerDto, TableDto, Unit> { customer, table ->
                    val reservedBy = customer.firstName + " " + customer.lastName
                    val newTable = TableDto(table.id, table.shape, reservedBy, customer.imageUrl)
                    appDataBase.tableDao().addTable(newTable)
        })
    }

    fun reserveTable(selectedTableId: Long, customer: Customer): Completable {
        return appDataBase.tableDao().getTableById(selectedTableId)
                .flatMapCompletable { table: TableDto ->

                    val reservation = ReservationDto(customer.id + table.id, customer.id, table.id)

                    Completable.fromAction {
                        appDataBase.reservationDao().addReservation(reservation)
                    }
                }
    }

    fun deleteReservationAndGetUpdatedList(clickedTable: Table): Observable<List<Table>> {
        return Observable.fromCallable {

            appDataBase.reservationDao().deleteReservationByTable(clickedTable.id)

            val freedTable = TableDto(clickedTable.id, null, null, null)
            appDataBase.tableDao().addTable(freedTable)
        }.flatMap {
            getUpdatedTableList()
        }
    }


    fun loadAllData(): Completable {
        return Completable.fromObservable(getAllCustomers())
                .andThen(Completable.fromObservable(getAllTables()))
                .andThen(Completable.fromObservable(getAllReservations()))
    }

    fun getAllCustomers(): Observable<List<Customer>> {
        return getAllCustomersFromDB()
                .flatMap { customerList ->
                    if (customerList.isEmpty()) {
                        retrieveCustomersFromServer()
                    } else {
                        Observable.just(customerList)
                    }
                }
    }

    fun getAllCustomersFromDB(): Observable<List<Customer>> {
        return appDataBase.customerDao().getAllCustomers()
                .toObservable()
                .flatMapIterable { list ->
                    list
                }
                .map { customerDto: CustomerDto ->
                    customerDto.toCustomer()
                }
                .toList().toObservable()
    }

    fun retrieveCustomersFromServer(): Observable<List<Customer>> {
        return restaurantService.getCustomers()
                .toObservable()
                .flatMapIterable { customers -> customers }
                .map { customerResp: CustomerResp ->
                    appDataBase.customerDao().addCustomer(customerResp.toCustomerDto())
                    customerResp.toCustomer()
                }
                .toList().toObservable()
    }



    fun getAllTables(): Observable<List<Table>> {
        return getAllTablesFromDB()
                .flatMap { tables ->

                    if (tables.isEmpty()) {
                        retrieveTablesFromServer()
                    } else {
                        Observable.just(tables)
                    }
                }
    }

    fun getAllTablesFromDB(): Observable<List<Table>> {
        return appDataBase.tableDao().getAllTables()
                .toObservable()
                .flatMapIterable { tablesDtoList ->
                    tablesDtoList
                }
                .map { tableDto ->
                    tableDto.toTable()
                }
                .toList().toObservable()
    }

    fun retrieveTablesFromServer(): Observable<List<Table>> {
        return restaurantService.getTables()
                .toObservable()
                .flatMapIterable { list ->
                    list
                }
                .map { tableResp ->
                    appDataBase.tableDao().addTable(tableResp.toTableDto())
                    tableResp.toTable()
                }
                .toList().toObservable()
    }


    fun getAllReservations(): Observable<List<Reservation>> {
        return getAllReservationsFromDB()
                .flatMap { reservations ->
                    val tables = appDataBase.tableDao().getItemCount()
                    val customers = appDataBase.customerDao().getItemCount()

                    if (tables == 0 && customers == 0) {
                        retrieveReservationsFromServer()
                    } else {
                        Observable.just(reservations)
                    }
                }
    }

    fun getAllReservationsFromDB(): Observable<List<Reservation>> {
        return appDataBase.reservationDao().getAllReservations()
                .toObservable()
                .flatMapIterable { reservationDtoList ->
                    reservationDtoList
                }
                .map { reservationDto ->
                    reservationDto.toReservation()
                }
                .toList().toObservable()
    }

    fun retrieveReservationsFromServer(): Observable<List<Reservation>> {
        return restaurantService.getReservations()
                .toObservable()
                .flatMapIterable { reservationsResp
                    -> reservationsResp
                }
                .map { reservationResp ->
                    appDataBase.reservationDao().addReservation(reservationResp.toReservationDto())
                    reservationResp.toReservation()
                }
                .toList().toObservable()
    }

    private fun findTableById(tableId: Long): Single<TableDto> {
        return appDataBase.tableDao().getTableById(tableId).toSingle()
    }

    private fun findCustomerById(customerId: Long): Single<CustomerDto> {
        return appDataBase.customerDao().getCustomerById(customerId).toSingle()
    }


}