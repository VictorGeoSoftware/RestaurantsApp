package com.quandoo.androidtask.utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.quandoo.androidtask.api.responses.TableResp
import com.quandoo.androidtask.data.models.Customer
import com.quandoo.androidtask.data.models.Reservation
import com.quandoo.androidtask.data.models.Table


fun getMockCustomerList(): List<Customer> {
    val customerListString = "[\n" +
            "  {\n" +
            "    \"first_name\": \"Marilyn\",\n" +
            "    \"last_name\": \"Monroe\",\n" +
            "    \"image_url\": \"https://s3-eu-west-1.amazonaws.com/quandoo-assessment/images/profile1.png\",\n" +
            "    \"id\": 0\n" +
            "  },\n" +
            "  {\n" +
            "    \"first_name\": \"Abraham\",\n" +
            "    \"last_name\": \"Lincoln\",\n" +
            "    \"image_url\": \"https://s3-eu-west-1.amazonaws.com/quandoo-assessment/images/profile2.png\",\n" +
            "    \"id\": 1\n" +
            "  }\n" +
            "]"

    val listType = object : TypeToken<List<Customer>>(){}.type
    return Gson().fromJson(customerListString, listType)
}


fun getMockTableRespList(): List<TableResp> {
    val tableRespListString = "[\n" +
            "  {\n" +
            "    \"shape\": \"circle\",\n" +
            "    \"id\": 101\n" +
            "  },\n" +
            "  {\n" +
            "    \"shape\": \"square\",\n" +
            "    \"id\": 102\n" +
            "  },\n" +
            "  {\n" +
            "    \"shape\": \"rectangle\",\n" +
            "    \"id\": 103\n" +
            "  }\n" +
            "]"

    val listType = object : TypeToken<List<TableResp>>(){}.type
    return Gson().fromJson(tableRespListString, listType)
}

fun getMockTableList(): List<Table> {
    val tableRespListString = "[\n" +
            "  {\n" +
            "    \"shape\": \"circle\",\n" +
            "    \"id\": 101\n" +
            "  },\n" +
            "  {\n" +
            "    \"shape\": \"square\",\n" +
            "    \"id\": 102\n" +
            "  },\n" +
            "  {\n" +
            "    \"shape\": \"rectangle\",\n" +
            "    \"id\": 103\n" +
            "  }\n" +
            "]"

    val listType = object : TypeToken<List<Table>>(){}.type
    return Gson().fromJson(tableRespListString, listType)
}

fun getMockReservationList(): List<Reservation> {
    val tableRespListString = "[\n" +
            "  {\n" +
            "    \"user_id\": 2,\n" +
            "    \"table_id\": 103,\n" +
            "    \"id\": 1001\n" +
            "  },\n" +
            "    {\n" +
            "    \"user_id\": 2,\n" +
            "    \"table_id\": 104,\n" +
            "    \"id\": 1002\n" +
            "  },\n" +
            "    {\n" +
            "    \"user_id\": 17,\n" +
            "    \"table_id\": 105,\n" +
            "    \"id\": 1003\n" +
            "  }\n" +
            "]"

    val listType = object : TypeToken<List<Reservation>>(){}.type
    return Gson().fromJson(tableRespListString, listType)
}