package com.example.proxymit.Custom_Reports_Editor.model;
import jakarta.persistence.*;
import java.util.Date;
@Entity
@Table(name = "ReportHistory")
public class HistoryReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "template_id")
    private Template template;

    @Column(name = "type_data_source")
    private String typeDataSource;

    @Column(name = "generation_date")
    private Date generationDate;

    @Column(name = "export_format")
    private String exportFormat;

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setTemplate(Template template) {
        this.template = template;
    }

    public Template getTemplate() {
        return template;
    }

    public Date getGenerationDate() {
        return generationDate;
    }

    public String getExportFormat() {
        return exportFormat;
    }

    public String getTypeDataSource() {
        return typeDataSource;
    }

    public void setExportFormat(String exportFormat) {
        this.exportFormat = exportFormat;
    }

    public void setGenerationDate(Date generationDate) {
        this.generationDate = generationDate;
    }

    public void setTypeDataSource(String typeDataSource) {
        this.typeDataSource = typeDataSource;
    }
}
