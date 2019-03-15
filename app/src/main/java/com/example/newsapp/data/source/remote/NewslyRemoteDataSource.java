package com.example.newsapp.data.source.remote;

import android.support.annotation.NonNull;

import com.example.newsapp.BuildConfig;
import com.example.newsapp.data.ArticleModel;
import com.example.newsapp.data.ArticleResultModel;
import com.example.newsapp.data.SourceModel;
import com.example.newsapp.data.SourceResultModel;
import com.example.newsapp.data.source.NewslyDataSource;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewslyRemoteDataSource implements NewslyDataSource {

    private static NewslyRemoteDataSource INSTANCE;

    public static NewslyRemoteDataSource getInstance() {
        if (INSTANCE == null) {
            synchronized (NewslyRemoteDataSource.class) {
                if (INSTANCE == null) {
                    INSTANCE = new NewslyRemoteDataSource();
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public void getSources(@NonNull final LoadSourcesCallback callback) {
        APIService.factory.create()
                .getSources(BuildConfig.NEWS_API_KEY)
                .enqueue(new Callback<SourceResultModel>() {
                    @Override
                    public void onResponse(Call<SourceResultModel> call, Response<SourceResultModel> response) {
                        callback.onSourcesLoaded(response.body().getSources());
                    }

                    @Override
                    public void onFailure(Call<SourceResultModel> call, Throwable t) {
                        callback.onDataNotAvailable(t.getMessage());
                    }
                });
    }

    @Override
    public void saveSource(@NonNull SourceModel source) {
    }

    @Override
    public void deleteAllSources() {
    }

    @Override
    public void getArticlesBySource(@NonNull final LoadArticlesCallback callback, String sourceId) {
        APIService.factory.create()
                .getArticles(BuildConfig.NEWS_API_KEY, sourceId, "")
                .enqueue(new Callback<ArticleResultModel>() {
                    @Override
                    public void onResponse(Call<ArticleResultModel> call, Response<ArticleResultModel> response) {
                        callback.onArticlesLoaded(response.body().getArticles());
                    }

                    @Override
                    public void onFailure(Call<ArticleResultModel> call, Throwable t) {
                        callback.onDataNotAvailable(t.getMessage());
                    }
                });
    }

    @Override
    public void getArticlesBySourceAndQuery(@NonNull final LoadArticlesCallback callback, String sourceId, String query) {
        APIService.factory.create()
                .getArticles(BuildConfig.NEWS_API_KEY, sourceId, query)
                .enqueue(new Callback<ArticleResultModel>() {
                    @Override
                    public void onResponse(Call<ArticleResultModel> call, Response<ArticleResultModel> response) {
                        callback.onArticlesLoaded(response.body().getArticles());
                    }

                    @Override
                    public void onFailure(Call<ArticleResultModel> call, Throwable t) {
                        callback.onDataNotAvailable(t.getMessage());
                    }
                });
    }

    @Override
    public void saveArticle(@NonNull ArticleModel article) {
    }

    @Override
    public void deleteAllArticlesBySource(String sourceId) {
    }
}
