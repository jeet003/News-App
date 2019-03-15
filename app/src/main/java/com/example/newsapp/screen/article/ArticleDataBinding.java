package com.example.newsapp.screen.article;

import android.databinding.BindingAdapter;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import com.example.newsapp.data.ArticleModel;

import java.util.List;

public class ArticleDataBinding {
    @BindingAdapter("app:items")
    public static void setItems(RecyclerView recyclerView, List<ArticleModel> items) {
        ArticleAdapter adapter = (ArticleAdapter) recyclerView.getAdapter();
        if (adapter != null) {
            adapter.replaceData(items);
        }
    }

    @BindingAdapter("app:onRefresh")
    public static void setSwipeRefreshLayoutOnRefreshListener(SwipeRefreshLayout view,
                                                              final ArticleViewModel viewModel) {
        view.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                viewModel.loadArticlesBySource(true, viewModel.sourceId.get());
            }
        });
    }
}
