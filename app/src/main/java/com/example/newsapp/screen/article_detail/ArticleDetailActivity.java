package com.example.newsapp.screen.article_detail;

import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.example.newsapp.R;
import com.example.newsapp.ViewModelFactory;
import com.example.newsapp.databinding.ActivityArticleDetailBinding;
import com.example.newsapp.screen.article.ArticleActivity;

public class ArticleDetailActivity extends AppCompatActivity {

    private ArticleDetailViewModel viewModel;
    private String url = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityArticleDetailBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_article_detail);

        viewModel = obtainViewModel(this);
        binding.setViewModel(viewModel);

        setupToolbar();

        url = getIntent().getStringExtra(ArticleActivity.ARTICLE_EXTRA);
        viewModel.start(url);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    private static ArticleDetailViewModel obtainViewModel(AppCompatActivity activity) {
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());

        ArticleDetailViewModel viewModel =
                ViewModelProviders.of(activity, factory).get(ArticleDetailViewModel.class);

        return viewModel;
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Article Detail");
    }
}
