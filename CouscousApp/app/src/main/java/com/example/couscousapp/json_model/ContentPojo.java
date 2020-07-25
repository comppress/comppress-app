package com.example.couscousapp.json_model;

public class ContentPojo {

    private Content content;
    private String source;

    public ContentPojo(Content content) {
        this.content = content;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Content getContent() {
        return content;
    }

    public String getSource() {
        return source;
    }
}

