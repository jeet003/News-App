package com.example.newsapp.screen.article;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableList;
import android.support.annotation.NonNull;

import com.example.newsapp.data.ArticleModel;
import com.example.newsapp.data.source.NewslyDataSource;
import com.example.newsapp.data.source.NewslyRepository;
import com.example.newsapp.util.SingleLiveEvent;

import java.util.List;

public class ArticleViewModel extends AndroidViewModel {

    private final NewslyRepository repository;
    public final ObservableList<ArticleModel> items = new ObservableArrayList<>();
    public final ObservableField<String> sourceId = new ObservableField<>();
    public final ObservableBoolean isDataLoading = new ObservableBoolean(false);
    public final ObservableBoolean isDataEmpty = new ObservableBoolean(false);
    public final SingleLiveEvent<String> errorMessage = new SingleLiveEvent<>();
    public final SingleLiveEvent<String> openArticleDetailEvent = new SingleLiveEvent<>();

    public ArticleViewModel(@NonNull Application application, NewslyRepository repository) {
        super(application);
        this.repository = repository;
    }

    public void start(String sourceId) {
        loadArticlesBySource(true, sourceId);
    }

    public void loadArticlesBySource(final boolean showLoadingUI, String sourceId) {
        this.sourceId.set(sourceId);
        isDataEmpty.set(false);

        if (showLoadingUI) {
            isDataLoading.set(true);
        }

        repository.getArticlesBySource(new NewslyDataSource.LoadArticlesCallback() {
            @Override
            public void onArticlesLoaded(List<ArticleModel> articles) {
                items.clear();
                for (ArticleModel article : articles) {
                    if (article.getAuthor() == null)
                        article.setAuthor("Unknown");

                    items.add(article);
                }
                if (showLoadingUI) {
                    isDataLoading.set(false);
                }
                if (items.size() <= 0)
                    isDataEmpty.set(true);
            }

            @Override
            public void onDataNotAvailable(String message) {
                if (showLoadingUI) {
                    isDataLoading.set(false);
                }
                if (message != null)
                    errorMessage.setValue(message);

                items.clear();
                isDataEmpty.set(true);
            }
        }, sourceId);
    }

    public void loadArticlesBySourceAndQuery(final boolean showLoadingUI, String sourceId, String query) {
        isDataEmpty.set(false);

        repository.getArticlesBySourceAndQuery(new NewslyDataSource.LoadArticlesCallback() {
            @Override
            public void onArticlesLoaded(List<ArticleModel> articles) {
                items.clear();
                for (ArticleModel article : articles) {
                    if (article.getAuthor() == null)
                        article.setAuthor("Unknown");

                    items.add(article);
                }
                if (items.size() <= 0)
                    isDataEmpty.set(true);
            }

            @Override
            public void onDataNotAvailable(String message) {
                if (message != null)
                    errorMessage.setValue(message);

                items.clear();
                isDataEmpty.set(true);
            }
        }, sourceId, query);
    }
}
