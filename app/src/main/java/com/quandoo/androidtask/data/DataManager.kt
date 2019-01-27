package com.quandoo.androidtask.data

import com.quandoo.androidtask.api.RestaurantService
import com.quandoo.androidtask.data.models.Customer
import com.quandoo.androidtask.data.room.AppDataBase
import com.quandoo.androidtask.data.room.customers.CustomerDto
import com.quandoo.androidtask.data.room.reservations.ReservationDto
import com.quandoo.androidtask.data.room.tables.TableDto
import io.reactivex.Completable
import io.reactivex.Observable


class DataManager(private val restaurantService: RestaurantService, private val appDataBase: AppDataBase) {


    fun reserveTable(selectedTableId: Long, customer: Customer): Completable {

        return appDataBase.tableDao().getTableById(selectedTableId)
                .flatMapCompletable { table: TableDto ->

                    val reservedBy = customer.firstName + " " + customer.lastName
                    val newTable = TableDto(table.id, table.shape, reservedBy)
                    appDataBase.tableDao().addTable(newTable)

                    val reservation = ReservationDto(customer.id + newTable.id, customer.id, newTable.id)

                    Completable.fromAction {
                        appDataBase.reservationDao().addReservation(reservation)
                    }
                }
    }

    fun getAllCustomers(): Observable<List<Customer>> {
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

}


fun CustomerDto.toCustomer(): Customer {
    val customer = Customer()
    customer.id = this.id
    customer.firstName = this.firstName
    customer.lastName = this.lastName
    customer.imageUrl = this.imageUrl

    return customer
}