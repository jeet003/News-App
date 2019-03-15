package com.example.newsapp.util;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.newsapp.data.source.NewslyRepository;
import com.example.newsapp.data.source.local.ArticleDatabase;
import com.example.newsapp.data.source.local.NewslyLocalDataSource;
import com.example.newsapp.data.source.local.SourceDatabase;
import com.example.newsapp.data.source.remote.NewslyRemoteDataSource;

public class Injection {
    public static NewslyRepository provideNewslyRepository(@NonNull Context context) {
        SourceDatabase sourceDatabase = SourceDatabase.getInstance(context);
        ArticleDatabase articleDatabase = ArticleDatabase.getInstance(context);
        return NewslyRepository.getInstance(NewslyRemoteDataSource.getInstance(),
                NewslyLocalDataSource.getInstance(new AppExecutors(), sourceDatabase.sourceDao(), articleDatabase.articleDao()));
    }
}
