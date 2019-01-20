package com.example.mukhtaradepoju.sample;

import android.content.Context;
import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.subscription.WebSocketSubscriptionTransport;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

public class MyApolloClient {

    private static String BASE_URL = "https://api.graph.cool/simple/v1/cjqr6zark3pt40140ag1bmi8n";
    private static final String SUBSCRIPTION_BASE_URL = "wss:/subscriptions.graph.cool/v1/cjqr6zark3pt40140ag1bmi8n";
    private static ApolloClient MyapolloClient;


    public static ApolloClient getApolloClient() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();


        MyapolloClient = ApolloClient.builder()
                .serverUrl(BASE_URL)
                .okHttpClient(client)
                .subscriptionTransportFactory(new WebSocketSubscriptionTransport.Factory(SUBSCRIPTION_BASE_URL, client))
                .build();
        return MyapolloClient;


    }

    }
