package com.example.couscousapp;

import java.util.List;

public class Data {

    List<Content> listContent;
    String category;

    public Data(List<Content> list, String category) {
        this.listContent = list;
        this.category = category;
    }

    public List<Content> getList() {
        return listContent;
    }

    public String getCategory() {
        return category;
    }

}
