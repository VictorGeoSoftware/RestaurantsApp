package com.quandoo.androidtask.data

import com.quandoo.androidtask.api.responses.CustomerResp
import com.quandoo.androidtask.api.responses.ReservationResp
import com.quandoo.androidtask.api.responses.TableResp
import com.quandoo.androidtask.data.models.Customer
import com.quandoo.androidtask.data.models.Reservation
import com.quandoo.androidtask.data.models.Table
import com.quandoo.androidtask.data.room.customers.CustomerDto
import com.quandoo.androidtask.data.room.reservations.ReservationDto
import com.quandoo.androidtask.data.room.tables.TableDto

fun CustomerDto.toCustomer(): Customer {
    val customer = Customer()
    customer.id = this.id
    customer.firstName = this.firstName
    customer.lastName = this.lastName
    customer.imageUrl = this.imageUrl

    return customer
}

fun CustomerResp.toCustomer(): Customer {
    val customer = Customer()
    customer.id = this.id
    customer.firstName = this.firstName
    customer.lastName = this.lastName
    customer.imageUrl = this.imageUrl

    return customer
}

fun CustomerResp.toCustomerDto(): CustomerDto {
    return CustomerDto(this.id, this.firstName, this.lastName, this.imageUrl)
}



fun TableDto.toTable(): Table {
    val table = Table()
    table.id = this.id.toLong()
    table.shape = this.shape

    return table
}

fun TableResp.toTable(): Table {
    val table = Table()
    table.id = this.id
    table.shape = this.shape

    return table
}

fun TableResp.toTableDto(): TableDto {
    return TableDto(this.id, this.shape, "", "")
}

fun Table.toTableDto(): TableDto {
    return TableDto(this.id, this.shape, this.reservedBy, this.avatarImageReserve)
}


fun ReservationDto.toReservation(): Reservation {
    return Reservation(userId, tableId, id)
}

fun ReservationResp.toReservation(): Reservation {
    return Reservation(this.userId, this.tableId, this.id)
}

fun ReservationResp.toReservationDto(): ReservationDto {
    return ReservationDto(this.id, this.userId, this.tableId)
}