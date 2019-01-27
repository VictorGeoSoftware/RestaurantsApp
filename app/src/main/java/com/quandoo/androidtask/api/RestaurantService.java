package com.quandoo.androidtask.api;

import com.quandoo.androidtask.data.models.Customer;
import com.quandoo.androidtask.data.models.Reservation;
import com.quandoo.androidtask.data.models.Table;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface RestaurantService {

    @GET("/quandoo-assessment/customers.json")
    Single<List<Customer>> getCustomers();

    @GET("/quandoo-assessment/reservations.json")
    Single<List<Reservation>> getReservations();

    @GET("/quandoo-assessment/tables.json")
    Single<List<Table>> getTables();
}