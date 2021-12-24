package com.company.controller;

import com.company.dao.UserDAO;
import com.company.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;

@Controller
public class MainController {

    private UserDAO userDAO;

    @Autowired
    public MainController(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @GetMapping("/main")
    public String mainForm(){
        return "main";
    }

    @GetMapping("/add-user")
    public String addUserForm(Model model){
        model.addAttribute("user", new User());
        return "add-user";
    }

    @PostMapping("/add-user")
    public String addUserSubmit(@ModelAttribute("user") @Valid User user,
                                BindingResult bindingResult, Model model) throws IOException {
        if (bindingResult.hasErrors())
            return "add-user";

        this.userDAO.addUser(user);
        model.addAttribute("users", userDAO.getUsers());
        return "list-users";
    }

    @GetMapping("/search-user")
    public String searchUserForm(Model model){
        model.addAttribute("user", new User());
        return "search-user";
    }

    @PostMapping("/search-user")
    public String searchUser(@ModelAttribute @Valid User user, BindingResult bindingResult,
                             Model model, HttpServletRequest request) {


        if (bindingResult.hasFieldErrors("firstName") || bindingResult.hasFieldErrors("lastName"))
            return "search-user";

        User user1 = this.userDAO.searchUser(user.getFirstName(), user.getLastName());
        if (user1 == null)
            return "user-not-found";
        model.addAttribute("user", user1);
        return "found-user";
    }

    @GetMapping("/list-users")
    public String allUsers(Model model) {
        model.addAttribute("users", this.userDAO.getUsers());
        return "list-users";
    }

}
