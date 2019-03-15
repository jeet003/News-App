package com.example.newsapp.data.source.remote;

import com.example.newsapp.BuildConfig;
import com.example.newsapp.data.ArticleResultModel;
import com.example.newsapp.data.SourceResultModel;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIService {
    @GET("sources")
    Call<SourceResultModel> getSources(@Query("apiKey") String apiKey);

    @GET("everything?language=en&sortBy=popularity&pageSize=50")
    Call<ArticleResultModel> getArticles(@Query("apiKey") String apiKey,
                                                 @Query("sources") String sourceId,
                                                 @Query("q") String query);

    class factory {
        static APIService create() {
            OkHttpClient.Builder builder = new OkHttpClient().newBuilder();
            builder.readTimeout(10, TimeUnit.SECONDS);
            builder.connectTimeout(10, TimeUnit.SECONDS);
            builder.writeTimeout(10, TimeUnit.SECONDS);

            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(httpLoggingInterceptor);

            OkHttpClient client = builder.build();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BuildConfig.BASE_API_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            return retrofit.create(APIService.class);
        }
    }
}
