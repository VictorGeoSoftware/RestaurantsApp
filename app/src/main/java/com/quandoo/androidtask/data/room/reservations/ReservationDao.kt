package com.quandoo.androidtask.data.room.reservations

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query
import io.reactivex.Maybe

@Dao
interface ReservationDao {

    @Insert(onConflict = REPLACE)
    fun addReservation(reservationDto: ReservationDto)

    @Query("SELECT * FROM RESERVATIONS_DB WHERE id = :reservationId")
    fun getReservationById(reservationId: Long): Maybe<ReservationDto>

    @Query("DELETE FROM RESERVATIONS_DB WHERE id = :reservationId")
    fun deleteReservation(reservationId: Long)

    @Query("SELECT * FROM RESERVATIONS_DB")
    fun getAllReservations(): Maybe<List<ReservationDto>>

    @Query("DELETE FROM RESERVATIONS_DB WHERE tableId = :tableId")
    fun deleteReservationByTable(tableId: Long)

    @Query("SELECT COUNT(`id`) FROM RESERVATIONS_DB")
    fun getCount(): Int
}