package com.example.newsapp.data.source.local;

import android.support.annotation.NonNull;

import com.example.newsapp.data.ArticleModel;
import com.example.newsapp.data.SourceModel;
import com.example.newsapp.data.source.NewslyDataSource;
import com.example.newsapp.util.AppExecutors;

import java.util.List;

public class NewslyLocalDataSource implements NewslyDataSource {

    private static NewslyLocalDataSource INSTANCE;
    private SourceDao sourceDao;
    private ArticleDao articleDao;
    private AppExecutors appExecutors;

    private NewslyLocalDataSource(@NonNull AppExecutors appExecutors,
                                  @NonNull SourceDao sourceDao,
                                  @NonNull ArticleDao articleDao) {
        this.appExecutors = appExecutors;
        this.sourceDao = sourceDao;
        this.articleDao = articleDao;
    }

    public static NewslyLocalDataSource getInstance(@NonNull AppExecutors appExecutors,
                                                    @NonNull SourceDao sourceDao,
                                                    @NonNull ArticleDao articleDao) {
        if (INSTANCE == null) {
            synchronized (NewslyLocalDataSource.class) {
                if (INSTANCE == null) {
                    INSTANCE = new NewslyLocalDataSource(appExecutors, sourceDao, articleDao);
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public void getSources(@NonNull final LoadSourcesCallback callback) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final List<SourceModel> sources = sourceDao.getSources();
                if (sources != null) {
                    if (sources.size() > 0) {
                        callback.onSourcesLoaded(sources);
                    } else {
                        callback.onDataNotAvailable("Data is empty");
                    }
                } else {
                    callback.onDataNotAvailable("Data is empty");
                }
            }
        };

        appExecutors.diskIO().execute(runnable);
    }

    @Override
    public void saveSource(@NonNull final SourceModel source) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                sourceDao.insertSource(source);
            }
        };
        appExecutors.diskIO().execute(runnable);
    }

    @Override
    public void deleteAllSources() {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                sourceDao.deleteAllSources();
            }
        };

        appExecutors.diskIO().execute(runnable);
    }

    @Override
    public void getArticlesBySource(@NonNull final LoadArticlesCallback callback, final String sourceId) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final List<ArticleModel> articles = articleDao.getArticlesBySource(sourceId);
                if (articles != null) {
                    if (articles.size() > 0) {
                        callback.onArticlesLoaded(articles);
                    } else {
                        callback.onDataNotAvailable("Data is empty");
                    }
                } else {
                    callback.onDataNotAvailable("Data is empty");
                }
            }
        };

        appExecutors.diskIO().execute(runnable);
    }

    @Override
    public void getArticlesBySourceAndQuery(@NonNull final LoadArticlesCallback callback, final String sourceId, final String query) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                final List<ArticleModel> articles = articleDao.getArticlesBySourceAndQuery(sourceId, query);
                if (articles != null) {
                    if (articles.size() > 0) {
                        callback.onArticlesLoaded(articles);
                    } else {
                        callback.onDataNotAvailable("Data is empty");
                    }
                } else {
                    callback.onDataNotAvailable("Data is empty");
                }
            }
        };

        appExecutors.diskIO().execute(runnable);
    }

    @Override
    public void saveArticle(@NonNull final ArticleModel article) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                articleDao.insertArticle(article);
            }
        };

        appExecutors.diskIO().execute(runnable);
    }

    @Override
    public void deleteAllArticlesBySource(final String sourceId) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                articleDao.deleteAllArticlesBySource(sourceId);
            }
        };

        appExecutors.diskIO().execute(runnable);
    }
}
