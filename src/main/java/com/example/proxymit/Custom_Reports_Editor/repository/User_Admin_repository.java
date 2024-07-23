package com.example.proxymit.Custom_Reports_Editor.repository;

import com.example.proxymit.Custom_Reports_Editor.model.User_Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface User_Admin_repository extends JpaRepository<User_Admin,Integer> {
     User_Admin findByAdresse(String adresse);

}
