package com.example.proxymit.Custom_Reports_Editor.repository;

import com.example.proxymit.Custom_Reports_Editor.model.HistoryReport;
import com.example.proxymit.Custom_Reports_Editor.model.Template;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HistoryReportRepository extends JpaRepository<HistoryReport, Integer> {
    List<HistoryReport> findByTemplate(Template template);
}
