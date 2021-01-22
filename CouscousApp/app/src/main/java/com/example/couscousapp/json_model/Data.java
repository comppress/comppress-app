package com.example.couscousapp.json_model;

import java.util.List;

public class Data {

    public Data(){}

    public Data(String category, List<ContentPojo> listContent){
        this.category = category;
        this.listContent = listContent;
        this.positionRatedNewsStart = -1;
    }

    private int positionRatedNewsStart;

    public void setPositionRatedNewsStart(int positionRatedNewsStart) {
        this.positionRatedNewsStart = positionRatedNewsStart;
    }

    public int getPositionRatedNewsStart() {
        return positionRatedNewsStart;
    }

    private String category;

    private List<ContentPojo> listContent;

    public void setCategory(String category) {
        this.category = category;
    }

    public void setListContent(List<ContentPojo> listContent) {
        this.listContent = listContent;
    }

    public String getCategory() {
        return category;
    }

    public List<ContentPojo> getListContent() {
        return listContent;
    }


}
