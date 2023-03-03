package com.example.foodapp.entity;

import java.util.Objects;

public class StepsToRecipe {

    private int idRecord;
    private int idRecipe;
    private int stepNumber;
    private String title;
    private String content;

    public int getIdRecord() {
        return idRecord;
    }

    public void setIdRecord(int idRecord) {
        this.idRecord = idRecord;
    }

    public int getIdRecipe() {
        return idRecipe;
    }

    public void setIdRecipe(int idRecipe) {
        this.idRecipe = idRecipe;
    }

    public int getStepNumber() {
        return stepNumber;
    }

    public void setStepNumber(int stepNumber) {
        this.stepNumber = stepNumber;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "StepsToRecipe{" +
                "idRecord='" + idRecord + '\'' +
                ", idRecipe='" + idRecipe + '\'' +
                ", stepNumber='" + stepNumber + '\'' +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StepsToRecipe that = (StepsToRecipe) o;
        return Objects.equals(idRecord, that.idRecord) && Objects.equals(idRecipe, that.idRecipe) && Objects.equals(stepNumber, that.stepNumber) && Objects.equals(title, that.title) && Objects.equals(content, that.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idRecord, idRecipe, stepNumber, title, content);
    }
}
