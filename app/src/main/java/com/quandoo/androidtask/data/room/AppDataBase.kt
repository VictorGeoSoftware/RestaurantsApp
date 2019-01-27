package com.quandoo.androidtask.data.room

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.quandoo.androidtask.data.room.customers.CustomerDao
import com.quandoo.androidtask.data.room.customers.CustomerDto
import com.quandoo.androidtask.data.room.reservations.ReservationDao
import com.quandoo.androidtask.data.room.reservations.ReservationDto
import com.quandoo.androidtask.data.room.tables.TableDto
import com.quandoo.androidtask.data.room.tables.TablesDao

@Database(entities = [CustomerDto::class, ReservationDto::class, TableDto::class], version = 1)
abstract class AppDataBase: RoomDatabase() {
    abstract fun customerDao(): CustomerDao
    abstract fun tableDao(): TablesDao
    abstract fun reservationDao(): ReservationDao
}