package com.example.proxymit.Custom_Reports_Editor.repository;
import com.example.proxymit.Custom_Reports_Editor.model.Template;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
@Repository
public interface TemplateRepository extends JpaRepository<Template,Integer> {
    Optional<Template> findByName(String name);
}
