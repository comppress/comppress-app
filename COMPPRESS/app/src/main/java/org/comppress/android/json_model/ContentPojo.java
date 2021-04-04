package org.comppress.android.json_model;

public class ContentPojo {

    private Content content;
    private String source;
    private Float rating;
    private Long countRatings;

    public ContentPojo(Content content) {
        this.content = content;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public Float getRating() {
        if (rating == null) {
            rating = -1f;
        }
        return rating;
    }

    public void setCountRatings(Float rating) {
        this.countRatings = countRatings;
    }

    public Long getCountRatings() {
        if (countRatings == null) {
            countRatings = -1L;
        }
        return countRatings;
    }

    public void setContent(Content content) {
        this.content = content;
    }

    public Content getContent() {
        return content;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSource() {
        return source;
    }
}

