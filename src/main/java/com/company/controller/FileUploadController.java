package com.company.controller;

import com.company.dao.UserDAO;
import com.company.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class FileUploadController {
    private UserDAO userDAO;

    @Autowired
    public FileUploadController(UserDAO userDAO) {
        this.userDAO = userDAO;
    }


    @GetMapping("/add-users-from-file")
    public String uploadFileForm() {
        return "add-users-from-file";
    }


    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file) throws IOException{
        userDAO.addUsersFromFile(file);

        return "redirect:/main";
    }
}
