package com.example.foodapp.entity;

import java.util.Objects;

public class Step {
    private int id;
    private int stepNumber;
    private String title;
    private String content;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
        return "Step{" +
                "id=" + id +
                ", stepNumber=" + stepNumber +
                ", title='" + title + '\'' +
                ", context='" + content + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Step step = (Step) o;
        return id == step.id && stepNumber == step.stepNumber && Objects.equals(title, step.title) && Objects.equals(content, step.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, stepNumber, title, content);
    }
}
