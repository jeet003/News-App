package com.example.newsapp.screen.article_detail;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;

public class ArticleDetailViewModel extends AndroidViewModel {

    public final ObservableField<String> url = new ObservableField<>();
    public final ObservableField<String> sourceId = new ObservableField<>();

    public ArticleDetailViewModel(@NonNull Application application) {
        super(application);
    }

    public void start(String url) {
        this.url.set(url);
    }
}
