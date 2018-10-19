package com.quandoo.androidtask.api;

import com.google.gson.annotations.SerializedName;

public class Reservation {

    @SerializedName("user_id")
    long userId;

    @SerializedName("table_id")
    long tableId;

    @SerializedName("id")
    long id;


    public Reservation(long userId, long tableId, long id) {
        this.userId = userId;
        this.tableId = tableId;
        this.id = id;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "userId=" + userId +
                ", tableId=" + tableId +
                ", id=" + id +
                '}';
    }

    public long getUserId() {
        return userId;
    }

    public long getTableId() {
        return tableId;
    }

    public long getId() {
        return id;
    }
}