package com.seveniu.node;

import java.util.List;

/**
 * Created by seveniu on 5/12/16.
 * FieldResult
 */
public class FieldResult {
    private int fieldId;
    private int fieldHtmlType;
    private String name;
    private String result;
    private List<Link> linkResult;

    public int getFieldId() {
        return fieldId;
    }

    public void setFieldId(int fieldId) {
        this.fieldId = fieldId;
    }

    public int getFieldHtmlType() {
        return fieldHtmlType;
    }

    public void setFieldHtmlType(int fieldHtmlType) {
        this.fieldHtmlType = fieldHtmlType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public List<Link> getLinkResult() {
        return linkResult;
    }

    public void setLinkResult(List<Link> linkResult) {
        this.linkResult = linkResult;
    }

    @Override
    public String toString() {
        return "FieldResult{" +
                "fieldId=" + fieldId +
                ", fieldHtmlType=" + fieldHtmlType +
                ", name='" + name + '\'' +
                ", result='" + result + '\'' +
                ", linkResult=" + linkResult +
                '}';
    }
}
