package com.example.proxymit.Custom_Reports_Editor.service.UserAdminService;

import com.example.proxymit.Custom_Reports_Editor.model.User_Admin;
import com.example.proxymit.Custom_Reports_Editor.repository.User_Admin_repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserAdminServiceImpl implements UserAdminService {

    @Autowired
    private User_Admin_repository user_admin_repository;

    @Override
    public User_Admin savedUser_Admin(User_Admin user_admin) {
        return user_admin_repository.save(user_admin);
    }

    public List<User_Admin> getAllUser_Admins() {
        return user_admin_repository.findAll();
    }

    public User_Admin getUser_Admin(String adresse) {
        return user_admin_repository.findByAdresse(adresse);
    }

    public boolean User_AdminAlreadyExists(String adresse) {
        User_Admin user = getUser_Admin(adresse);
        return user != null;
    }

    public boolean User_AdminCheckPassword(User_Admin user_admin) {
        if (User_AdminAlreadyExists(user_admin.getAdresse())) {
             User_Admin user = getUser_Admin(user_admin.getAdresse());
            if (user != null && user.getPassword()!= null && user.getPassword().equals(user_admin.getPassword())) {
                return true;
            }
        }
        return false;
    }
    public Integer getUser_AdminID(String adresse) {
        return(getUser_Admin(adresse).getId());
    }
}