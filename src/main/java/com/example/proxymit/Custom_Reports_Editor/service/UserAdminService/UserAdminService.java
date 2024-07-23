package com.example.proxymit.Custom_Reports_Editor.service.UserAdminService;

import com.example.proxymit.Custom_Reports_Editor.model.User_Admin;

import java.util.List;

public interface UserAdminService {
   public User_Admin savedUser_Admin(User_Admin user_admin);

   public List<User_Admin> getAllUser_Admins();

   public User_Admin getUser_Admin(String adresse);

 public boolean User_AdminAlreadyExists(String adresse);

 public boolean User_AdminCheckPassword(User_Admin user_admin);
    public Integer getUser_AdminID(String adresse);

}