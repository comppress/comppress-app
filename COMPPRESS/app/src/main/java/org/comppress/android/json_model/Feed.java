package org.comppress.android.json_model;

import java.util.List;

public class Feed {

    public Feed(String category, List<Content> listContent){
        this.category = category;
        this.listContent = listContent;
        this.positionRatedNewsStart = -1;
    }

    private String category;
    private int positionRatedNewsStart;
    private List<Content> listContent;

    public void setPositionRatedNewsStart(int positionRatedNewsStart) {
        this.positionRatedNewsStart = positionRatedNewsStart;
    }

    public int getPositionRatedNewsStart() {
        return positionRatedNewsStart;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setListContent(List<Content> listContent) {
        this.listContent = listContent;
    }

    public String getCategory() {
        return category;
    }

    public List<Content> getListContent() {
        return listContent;
    }
}