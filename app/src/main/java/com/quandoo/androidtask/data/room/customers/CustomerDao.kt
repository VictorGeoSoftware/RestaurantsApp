package com.quandoo.androidtask.data.room.customers

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query
import io.reactivex.Maybe

@Dao
interface CustomerDao {

    @Insert(onConflict = REPLACE)
    fun addCustomer(customerDto: CustomerDto)

    @Query("SELECT * FROM CUSTOMERS_DB WHERE id = :customerId")
    fun getCustomerById(customerId: Long): Maybe<CustomerDto>

    @Query("SELECT * FROM CUSTOMERS_DB")
    fun getAllCustomers(): Maybe<List<CustomerDto>>
}