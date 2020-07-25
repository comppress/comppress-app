package com.example.couscousapp.json_model;

import java.util.List;

public class Data {

    List<ContentPojo> listContent;
    String category;

    public Data(List<ContentPojo> list, String category) {
        this.listContent = list;
        this.category = category;
    }

    public List<ContentPojo> getList() {
        return listContent;
    }

    public String getCategory() {
        return category;
    }

}
