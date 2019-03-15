package com.example.newsapp.data.source;

import android.support.annotation.NonNull;

import com.example.newsapp.data.ArticleModel;
import com.example.newsapp.data.SourceModel;

import java.util.ArrayList;
import java.util.List;

public class NewslyRepository implements NewslyDataSource {

    private volatile static NewslyRepository INSTANCE = null;
    private final NewslyDataSource newslyRemoteDataSource;
    private final NewslyDataSource newslyLocalDataSource;

    private NewslyRepository(@NonNull NewslyDataSource newslyRemoteDataSource,
                             @NonNull NewslyDataSource newslyLocalDataSource) {
        this.newslyRemoteDataSource = newslyRemoteDataSource;
        this.newslyLocalDataSource = newslyLocalDataSource;
    }

    public static NewslyRepository getInstance(NewslyDataSource newslyRemoteDataSource,
                                               NewslyDataSource newslyLocalDataSource) {
        if (INSTANCE == null) {
            synchronized (NewslyRepository.class) {
                if (INSTANCE == null) {
                    INSTANCE = new NewslyRepository(newslyRemoteDataSource, newslyLocalDataSource);
                }
            }
        }

        return INSTANCE;
    }

    @Override
    public void getSources(@NonNull final LoadSourcesCallback callback) {
        newslyLocalDataSource.getSources(new LoadSourcesCallback() {
            @Override
            public void onSourcesLoaded(List<SourceModel> sources) {
                callback.onSourcesLoaded(new ArrayList<>(sources));
            }

            @Override
            public void onDataNotAvailable(String message) {
                getSourcesFromRemoteDataSource(callback);
            }
        });
    }

    @Override
    public void saveSource(@NonNull SourceModel source) {
        newslyLocalDataSource.saveSource(source);
    }

    @Override
    public void deleteAllSources() {
        newslyLocalDataSource.deleteAllSources();
    }

    @Override
    public void getArticlesBySource(@NonNull final LoadArticlesCallback callback, final String sourceId) {
        newslyLocalDataSource.getArticlesBySource(new LoadArticlesCallback() {
            @Override
            public void onArticlesLoaded(List<ArticleModel> articles) {
                callback.onArticlesLoaded(new ArrayList<>(articles));
            }

            @Override
            public void onDataNotAvailable(String message) {
                getArticlesBySourceFromRemoteDataSource(callback, sourceId);
            }
        }, sourceId);
    }

    @Override
    public void getArticlesBySourceAndQuery(@NonNull final LoadArticlesCallback callback, final String sourceId, final String query) {
        newslyRemoteDataSource.getArticlesBySourceAndQuery(new LoadArticlesCallback() {
            @Override
            public void onArticlesLoaded(List<ArticleModel> articles) {
                callback.onArticlesLoaded(new ArrayList<>(articles));
            }

            @Override
            public void onDataNotAvailable(String message) {
                callback.onDataNotAvailable(message);
            }
        }, sourceId, query);
    }

    @Override
    public void saveArticle(@NonNull ArticleModel article) {
        newslyLocalDataSource.saveArticle(article);
    }

    @Override
    public void deleteAllArticlesBySource(String sourceId) {
        newslyLocalDataSource.deleteAllArticlesBySource(sourceId);
    }

    private void refreshLocalDataSourceForSource(List<SourceModel> sources) {
        newslyLocalDataSource.deleteAllSources();
        for (SourceModel source : sources) {
            newslyLocalDataSource.saveSource(source);
        }
    }

    private void getSourcesFromRemoteDataSource(@NonNull final LoadSourcesCallback callback) {
        newslyRemoteDataSource.getSources(new LoadSourcesCallback() {
            @Override
            public void onSourcesLoaded(List<SourceModel> sources) {
                refreshLocalDataSourceForSource(sources);

                callback.onSourcesLoaded(new ArrayList<>(sources));
            }

            @Override
            public void onDataNotAvailable(String message) {
                callback.onDataNotAvailable(message);
            }
        });
    }

    private void refreshLocalDataSourceForArticle(List<ArticleModel> articles) {
        if(articles.size()>0) {
            newslyLocalDataSource.deleteAllArticlesBySource(articles.get(0).getSource().getSourceId());
            for (ArticleModel article : articles) {
                article.setSourceId(article.getSource().getSourceId());
                newslyLocalDataSource.saveArticle(article);
            }
        }
    }

    private void getArticlesBySourceFromRemoteDataSource(@NonNull final LoadArticlesCallback callback, String sourceId) {
        newslyRemoteDataSource.getArticlesBySource(new LoadArticlesCallback() {
            @Override
            public void onArticlesLoaded(List<ArticleModel> articles) {
                refreshLocalDataSourceForArticle(articles);

                callback.onArticlesLoaded(new ArrayList<>(articles));
            }

            @Override
            public void onDataNotAvailable(String message) {
                callback.onDataNotAvailable(message);
            }
        }, sourceId);
    }
}
