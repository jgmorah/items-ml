package com.ml.items.adapters.retrofit;

import okhttp3.OkHttpClient;
import org.springframework.stereotype.Component;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Component
public class RetrofitClientBuilder {
    static final String BASE_URL = "https://api.mercadolibre.com/";
    Retrofit retrofitClient;

    /**
     * Singleton
     */
    public Retrofit getClient() {
        if (this.retrofitClient == null) {
            initClient();

        }
        return retrofitClient;
    }

    private void initClient() {

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        this.retrofitClient = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();
    }
}
