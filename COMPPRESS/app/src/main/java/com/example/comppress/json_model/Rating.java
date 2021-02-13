package com.example.comppress.json_model;

public class Rating {

    //All vars related to rating included in database
    private Integer id;
    private Integer contentId;
    private Long personId;
    private Integer creationDate;
    private Integer credibility;
    private Integer informativity;
    private Integer factuality;
    private Integer sourceTransparency;
    private Integer neutrality;
    private Integer pluralityOfViews;
    private Integer impartiality;
    private Integer dispassion;

    public void setId(Integer id) {
        this.id = id;
    }

    public void setContentId(Integer contentId) {
        this.contentId = contentId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    public void setCreationDate(Integer creationDate) {
        this.creationDate = creationDate;
    }

    public void setCredibility(Integer credibility) {
        this.credibility = credibility;
    }

    public void setInformativity(Integer informativity) {
        this.informativity = informativity;
    }

    public void setFactuality(Integer factuality) {
        this.factuality = factuality;
    }

    public void setSourceTransparency(Integer sourceTransparency) {
        this.sourceTransparency = sourceTransparency;
    }

    public void setNeutrality(Integer neutrality) {
        this.neutrality = neutrality;
    }

    public void setPluralityOfViews(Integer pluralityOfViews) {
        this.pluralityOfViews = pluralityOfViews;
    }

    public void setImpartiality(Integer impartiality) {
        this.impartiality = impartiality;
    }

    public void setDispassion(Integer dispassion) {
        this.dispassion = dispassion;
    }

    public Integer getId() {
        return id;
    }

    public Integer getContentId() {
        return contentId;
    }

    public Long getPersonId() {
        return personId;
    }

    public Integer getCreationDate() {
        return creationDate;
    }

    public Integer getCredibility() {
        return credibility;
    }

    public Integer getInformativity() {
        return informativity;
    }

    public Integer getFactuality() {
        return factuality;
    }

    public Integer getSourceTransparency() {
        return sourceTransparency;
    }

    public Integer getNeutrality() {
        return neutrality;
    }

    public Integer getPluralityOfViews() {
        return pluralityOfViews;
    }

    public Integer getImpartiality() {
        return impartiality;
    }

    public Integer getDispassion() {
        return dispassion;
    }
}