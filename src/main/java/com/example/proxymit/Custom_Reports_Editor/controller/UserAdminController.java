package com.example.proxymit.Custom_Reports_Editor.controller;

import com.example.proxymit.Custom_Reports_Editor.model.User_Admin;
import com.example.proxymit.Custom_Reports_Editor.service.UserAdminService.UserAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user_admin")
@CrossOrigin(origins ="http://localhost:5173")
public class UserAdminController {
    @Autowired
    private UserAdminService user_adminService;

    @PostMapping(value = "/add")
    @ResponseBody
    public String add(@RequestBody User_Admin user_admin) {
        user_adminService.savedUser_Admin(user_admin);
        return "new user_admin is added";
    }

    @GetMapping("/getAll")
    public List<User_Admin> getAllUser_Admins(){
        return user_adminService.getAllUser_Admins();
    }

//    @GetMapping("/getUser")
//    public List<User_Admin> getUser_Admins(@RequestParam String adresse) {
//        return user_adminService.getUser_Admins(adresse);
//    }
    @GetMapping("/getUser/{adresse}")
    public User_Admin getUser_Admin(@PathVariable String adresse) {
        return user_adminService.getUser_Admin(adresse);
    }

    @GetMapping("/getUserExists/{adresse}")
    public boolean User_AdminAlreadyExists(@PathVariable String adresse) {
        return user_adminService.User_AdminAlreadyExists(adresse);
    }
    @PostMapping("/checkUserAdminPassword")
    public boolean User_AdminCheckPassword(@RequestBody User_Admin user_admin) {
      return user_adminService.User_AdminCheckPassword(user_admin);
    }
    @GetMapping("getUserID/{adresse}")
    public Integer getUser_AdminID(@PathVariable String adresse) {
      return user_adminService.getUser_AdminID(adresse);
    }

}
