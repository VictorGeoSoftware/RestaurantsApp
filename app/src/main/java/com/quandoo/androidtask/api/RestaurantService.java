package com.quandoo.androidtask.api;

import com.quandoo.androidtask.data.models.Customer;
import com.quandoo.androidtask.data.models.Reservation;
import com.quandoo.androidtask.data.models.Table;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface RestaurantService {

    String BASE_URL = "https://s3-eu-west-1.amazonaws.com/";

    @GET("/quandoo-assessment/customers.json")
    Single<List<Customer>> getCustomers();

    @GET("/quandoo-assessment/reservations.json")
    Single<List<Reservation>> getReservations();


    @GET("/quandoo-assessment/tables.json")
    Single<List<Table>> getTables();


//    class Creator {
//        public RestaurantService create() {
//            OkHttpClient.Builder httpClient = createHttpClient();
//            Retrofit retrofit = new Retrofit.Builder()
//                    .client(httpClient.build())
//                    .addCallAdapterFactory(
//                            RxJava2CallAdapterFactory.create())
//                    .addConverterFactory(
//                            GsonConverterFactory.create())
//                    .baseUrl(BASE_URL)
//                    .build();
//
//            return retrofit.create(RestaurantService.class);
//        }
//
//        private OkHttpClient.Builder createHttpClient() {
//            OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
//            HttpLoggingInterceptor logging = createLoggingInterceptor();
//            // add logging as last interceptor for better debugging
//            httpClient.addInterceptor(logging);
//            return httpClient;
//        }
//
//        private HttpLoggingInterceptor createLoggingInterceptor() {
//            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
//            logging.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);
//            return logging;
//        }
//    }
}