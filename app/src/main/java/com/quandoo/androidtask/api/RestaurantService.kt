package com.quandoo.androidtask.api

import com.quandoo.androidtask.api.responses.CustomerResp
import com.quandoo.androidtask.api.responses.ReservationResp
import com.quandoo.androidtask.api.responses.TableResp
import com.quandoo.androidtask.data.models.Customer
import com.quandoo.androidtask.data.models.Reservation
import com.quandoo.androidtask.data.models.Table

import io.reactivex.Single
import retrofit2.http.GET

interface RestaurantService {

    @GET("/quandoo-assessment/customers.json")
    fun getCustomers(): Single<List<CustomerResp>>

    @GET("/quandoo-assessment/reservations.json")
    fun getReservations(): Single<List<ReservationResp>>

    @GET("/quandoo-assessment/tables.json")
    fun getTables(): Single<List<TableResp>>
}