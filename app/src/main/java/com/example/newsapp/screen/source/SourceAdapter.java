package com.example.newsapp.screen.source;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.newsapp.data.SourceModel;
import com.example.newsapp.databinding.RowSourceBinding;

import java.util.List;

public class SourceAdapter extends RecyclerView.Adapter<SourceAdapter.ViewHolder> {

    private Context context;
    private List<SourceModel> sources;
    private SourceViewModel viewModel;

    public SourceAdapter(Context context, List<SourceModel> sources, SourceViewModel viewModel) {
        this.context = context;
        this.sources = sources;
        this.viewModel = viewModel;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        RowSourceBinding binding = RowSourceBinding.inflate(LayoutInflater.from(viewGroup.getContext()),
                viewGroup,
                false);
        return new SourceAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final SourceModel source = sources.get(i);

        SourceItemListener listener = new SourceItemListener() {
            @Override
            public void onSourceItemClicked(SourceModel source) {
                viewModel.openSourceDetailEvent.setValue(source);
            }
        };
        viewHolder.bindItem(source, listener);
    }

    @Override
    public int getItemCount() {
        if (sources == null)
            return 0;

        return sources.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private final RowSourceBinding binding;

        ViewHolder(RowSourceBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bindItem(SourceModel source, SourceItemListener listener) {
            binding.setSource(source);
            binding.setListener(listener);
            binding.executePendingBindings();
        }
    }

    public void replaceData(List<SourceModel> sources) {
        this.sources = sources;
        notifyDataSetChanged();
    }
}
