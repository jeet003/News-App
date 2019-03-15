package com.example.newsapp.screen.source;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;

import com.example.newsapp.R;
import com.example.newsapp.ViewModelFactory;
import com.example.newsapp.data.SourceModel;
import com.example.newsapp.databinding.ActivitySourceBinding;
import com.example.newsapp.screen.article.ArticleActivity;

import java.util.ArrayList;

public class SourceActivity extends AppCompatActivity {

    public static final String SOURCE_EXTRA = "source-extra";

    private SourceViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySourceBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_source);

        viewModel = obtainViewModel(this);
        binding.setViewModel(viewModel);

        setupToolbar();
        setupRecyclerView();

        viewModel.start();
        viewModel.errorMessage.observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                showSnackBar(s);
            }
        });
        viewModel.openSourceDetailEvent.observe(this, new Observer<SourceModel>() {
            @Override
            public void onChanged(@Nullable SourceModel s) {
                Intent intent = new Intent(SourceActivity.this, ArticleActivity.class);
                intent.putExtra(SOURCE_EXTRA, s);
                startActivity(intent);
            }
        });
    }

    private static SourceViewModel obtainViewModel(AppCompatActivity activity) {
        ViewModelFactory factory = ViewModelFactory.getInstance(activity.getApplication());

        SourceViewModel viewModel =
                ViewModelProviders.of(activity, factory).get(SourceViewModel.class);

        return viewModel;
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void setupRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.source_recyclerview_sources);
        recyclerView.setLayoutManager(new LinearLayoutManager(this) {
            @Override
            public RecyclerView.LayoutParams generateDefaultLayoutParams() {
                return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
            }
        });

        SourceAdapter adapter = new SourceAdapter(this, new ArrayList<SourceModel>(), viewModel);
        recyclerView.setAdapter(adapter);
    }

    private void showSnackBar(String message) {
        CoordinatorLayout coordinatorLayout = findViewById(R.id.source_coordinatorlayout_container);
        Snackbar.make(coordinatorLayout, message, Snackbar.LENGTH_LONG)
                .setAction("Close", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                })
                .show();
    }
}
