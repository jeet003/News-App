package com.example.newsapp.screen.source;

import android.databinding.BindingAdapter;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import com.example.newsapp.data.SourceModel;

import java.util.List;

public class SourceDataBinding {
    @BindingAdapter("app:items")
    public static void setItems(RecyclerView recyclerView, List<SourceModel> items) {
        SourceAdapter adapter = (SourceAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.replaceData(items);
        }
    }

    @BindingAdapter("app:onRefresh")
    public static void setSwipeRefreshLayoutOnRefreshListener(SwipeRefreshLayout view,
                                                              final SourceViewModel viewModel) {
        view.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                viewModel.loadSources(true);
            }
        });
    }
}
