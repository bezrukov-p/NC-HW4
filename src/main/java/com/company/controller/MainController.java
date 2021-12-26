package com.company.controller;

import com.company.dao.UserDAO;
import com.company.model.Information;
import com.company.model.Letter;
import com.company.model.User;
import com.company.service.EmailService;
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
import java.util.Date;

@Controller
public class MainController {

    private UserDAO userDAO;
    private EmailService emailService;

    @Autowired
    public MainController(UserDAO userDAO, EmailService emailService) {
        this.userDAO = userDAO;
        this.emailService = emailService;
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
        return "redirect:/list-users";
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
        if (user1 == null) {
            model.addAttribute("user", user);
            return "user-not-found";
        }

        Date creationTime = new Date(request.getSession().getCreationTime());
        String clientInfo = request.getHeader("User-Agent");
        Information information = new Information(creationTime, clientInfo);

        model.addAttribute("user", user1);
        model.addAttribute("info", information);
        return "found-user";
    }

    @GetMapping("/list-users")
    public String allUsers(Model model) {
        model.addAttribute("users", this.userDAO.getUsers());
        return "list-users";
    }

    @GetMapping("/send-email")
    public String sendEmailForm(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("letter", new Letter());

        return "send-email";
    }

    @PostMapping("/send-email")
    public String sendEmail(@ModelAttribute Letter letter,
                            @ModelAttribute User user,
                            Model model) {

        User recipient = userDAO.searchUser(user.getFirstName(), user.getLastName());
        if (user == null) {
            model.addAttribute("user", user);
            return "user-not-found";
        }

        model.addAttribute("user", recipient);
        emailService.sendSimpleMessage(recipient.getEmail(), letter.getSubject(), letter.getMessage());

        return "message-sent";
    }

}
