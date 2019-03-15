package com.example.newsapp.screen.source;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableList;
import android.support.annotation.NonNull;

import com.example.newsapp.data.SourceModel;
import com.example.newsapp.data.source.NewslyDataSource;
import com.example.newsapp.data.source.NewslyRepository;
import com.example.newsapp.util.SingleLiveEvent;

import java.util.List;

public class SourceViewModel extends AndroidViewModel {

    private final NewslyRepository repository;
    public final ObservableList<SourceModel> items = new ObservableArrayList<>();
    public final ObservableBoolean isDataLoading = new ObservableBoolean(false);
    public final ObservableBoolean isDataEmpty = new ObservableBoolean(false);
    public final SingleLiveEvent<String> errorMessage = new SingleLiveEvent<>();
    public final SingleLiveEvent<SourceModel> openSourceDetailEvent = new SingleLiveEvent<>();

    public SourceViewModel(@NonNull Application application, NewslyRepository repository) {
        super(application);
        this.repository = repository;
    }

    public void start() {
        loadSources(true);
    }

    public void loadSources(final boolean showLoadingUI) {
        if (showLoadingUI) {
            isDataLoading.set(true);
        }

        repository.getSources(new NewslyDataSource.LoadSourcesCallback() {
            @Override
            public void onSourcesLoaded(List<SourceModel> sources) {
                items.clear();
                items.addAll(sources);
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
        });
    }
}
