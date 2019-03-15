package com.example.newsapp.util;

import android.databinding.BindingAdapter;
import android.webkit.WebView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class NewslyDataBinding {
    @BindingAdapter("app:imageUrl")
    public static void setImageUrl(ImageView view, String url) {
        Glide.with(view.getContext())
                .load(url)
                .into(view);
    }

    @BindingAdapter("app:webViewUrl")
    public static void setWebViewUrl(WebView view, String url) {
        view.getSettings().setJavaScriptEnabled(true);
        view.loadUrl(url);
    }
}
