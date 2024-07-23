package com.example.proxymit.Custom_Reports_Editor.model;
import jakarta.persistence.*;
import java.util.Date;
@Entity
@Table(name = "Template")
public class Template {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "save_date")
    private Date saveDate;

    @Column(name = "modify_date")
    private Date modifyDate;

    @Column(name = "html_data", columnDefinition = "LONGTEXT")
    private String htmlData;

    @Column(name = "css_data", columnDefinition = "LONGTEXT")
    private String cssData;

    @Column(name = "context")
    private String context;

    public Date getModifyDate() {
        return modifyDate;
    }

    public String getContext() {
        return context;
    }

    public Date getSaveDate() {
        return saveDate;
    }

    public String getCssData() {
        return cssData;
    }

    public String getHtmlData() {
        return htmlData;
    }

    public String getName() {
        return name;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setHtmlData(String htmlData) {
        this.htmlData = htmlData;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }

    public void setSaveDate(Date saveDate) {
        this.saveDate = saveDate;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCssData(String cssData) {
        this.cssData = cssData;
    }
}
