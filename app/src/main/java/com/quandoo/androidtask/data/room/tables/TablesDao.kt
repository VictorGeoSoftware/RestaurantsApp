package com.quandoo.androidtask.data.room.tables

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy.REPLACE
import android.arch.persistence.room.Query
import io.reactivex.Maybe

@Dao
interface TablesDao {

    @Insert(onConflict = REPLACE)
    fun addTable(tableDto: TableDto)

    @Query("SELECT * FROM TABLES_DB WHERE id = :id")
    fun getTableById(id: Long): Maybe<TableDto>

    @Query("SELECT * FROM TABLES_DB")
    fun getAllTables(): Maybe<List<TableDto>>

    @Query("SELECT COUNT(`shape`) FROM TABLES_DB")
    fun getItemCount(): Int
}