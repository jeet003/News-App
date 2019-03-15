package com.example.newsapp.data;

import java.util.List;

public class SourceResultModel extends BaseModel {
    private List<SourceModel> sources;

    public List<SourceModel> getSources() {
        return sources;
    }

    public void setSources(List<SourceModel> sources) {
        this.sources = sources;
    }
}
