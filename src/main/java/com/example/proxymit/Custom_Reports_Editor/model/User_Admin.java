package com.example.proxymit.Custom_Reports_Editor.model;

import com.fasterxml.jackson.annotation.JsonAnySetter;
import jakarta.persistence.*;

@Entity
@Table(name = "user_admin")
public class User_Admin {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String adresse;
    private String password;

    public User_Admin(){
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getId() {
        return id;
    }
    public String getName() {
        return name;
    }

    public String getAdresse() {
        return adresse;
    }

    public String getPassword() {
        return password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
