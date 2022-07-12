package com.mdq.auditinspectionapp.Http;


import com.mdq.auditinspectionapp.Utils.ApiClass;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

//For making the connection with the backend server
public class AuditApiClient {

    private static Retrofit retrofit = null;
    private static ApiClass apiClass;

    public AuditApiClient() {
    }

    public static ApiInterface apiinterface() {
        apiClass = new ApiClass();
        if (retrofit == null) {
            OkHttpClient defaultHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
                    .connectTimeout(2, TimeUnit.MINUTES)
                    .readTimeout(40, TimeUnit.SECONDS) // read timeout
                    .retryOnConnectionFailure(true)
                    .build();

            retrofit = new Retrofit.Builder()
                    .baseUrl(apiClass.BASE_URL)
                    .addConverterFactory(JacksonConverterFactory.create())
                    .client(defaultHttpClient)
                    .build();
        }
        return retrofit.create(ApiInterface.class);
    }
}


