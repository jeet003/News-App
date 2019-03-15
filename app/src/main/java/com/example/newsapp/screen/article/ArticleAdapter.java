package com.example.newsapp.screen.article;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.newsapp.data.ArticleModel;
import com.example.newsapp.databinding.RowArticleBinding;

import java.util.List;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ViewHolder> {

    private Context context;
    private List<ArticleModel> articles;
    private ArticleViewModel viewModel;

    public ArticleAdapter(Context context, List<ArticleModel> articles, ArticleViewModel viewModel) {
        this.context = context;
        this.articles = articles;
        this.viewModel = viewModel;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        RowArticleBinding binding = RowArticleBinding.inflate(LayoutInflater.from(viewGroup.getContext()),
                viewGroup,
                false);
        return new ArticleAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final ArticleModel article = articles.get(i);

        ArticleItemListener listener = new ArticleItemListener() {
            @Override
            public void onArticleItemClicked(String url) {
                viewModel.openArticleDetailEvent.setValue(url);
            }
        };
        viewHolder.bindItem(article, listener);
    }

    @Override
    public int getItemCount() {
        if (articles == null)
            return 0;

        return articles.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final RowArticleBinding binding;

        ViewHolder(RowArticleBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bindItem(ArticleModel article, ArticleItemListener listener) {
            binding.setArticle(article);
            binding.setListener(listener);
            binding.executePendingBindings();
        }
    }

    public void replaceData(List<ArticleModel> articles) {
        this.articles = articles;
        notifyDataSetChanged();
    }
}
