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
import com.quandoo.androidtask.utils.myTrace
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.functions.BiFunction


class DataManager(private val restaurantService: RestaurantService, private val appDataBase: AppDataBase) {

    fun getUpdatedTableList(): Observable<List<Table>> {
        return getAllReservations()
                .flatMap { reservations ->

                    reservations.map { reservation ->
                        myTrace("reservations -> to search :: ${reservation.tableId} || ${reservation.userId}")
                        val tableDto = appDataBase.tableDao().getTableById(reservation.tableId)
                        val customerDto = appDataBase.customerDao().getCustomerById(reservation.userId)

                        tableDto.flatMapCompletable {
                            Completable.fromAction { myTrace("reservations -> find user and table - found table $it") }
                        }

                        customerDto.flatMapCompletable {
                            Completable.fromAction { myTrace("reservations -> find user and table - found customer $it") }
                        }

                        Maybe.zip(tableDto, customerDto, BiFunction<TableDto, CustomerDto, Unit> { table, customer ->
                            myTrace("reservations -> find user and table $table || $customer")
                            val reservedBy = customer.firstName + " " + customer.lastName
                            val newTable = TableDto(table.id, table.shape, reservedBy, customer.imageUrl)
                            appDataBase.tableDao().addTable(newTable)
                        })
                    }
                    getAllTables()
                }
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
            clickedTable.shape = null
            clickedTable.avatarImageReserve = null
            clickedTable.reservedBy = null
            appDataBase.tableDao().addTable(clickedTable.toTableDto())
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
                    myTrace("getAllCustomersFromDB - count :: ${customerList.size}")
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
                    myTrace("getAllTables - count :: ${tables.size}")
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
                    if (reservations.isEmpty()) {
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




}