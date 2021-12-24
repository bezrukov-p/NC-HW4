package com.company.dao;

import com.company.model.User;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Component
public class UserDAO {
    private List<User> users;
    private String filepath = "src/main/";

    public UserDAO() throws IOException {
        users = new ArrayList<>();
        File file = new File(filepath + "users.txt");
        file.createNewFile();

        FileReader fr = new FileReader(file);
        Scanner scan = new Scanner(fr);

        while(scan.hasNextLine()){
            String line = scan.nextLine();
            User newUser = new User();
            newUser.stringToUser(line);
            users.add(newUser);
        }

        fr.close();
    }

    public void addUser(User user) throws IOException {
        users.add(user);

        File file = new File(filepath + "users.txt");
        FileWriter fw = new FileWriter(file, true);

        fw.write(user.toString() + "\n");

        fw.close();
    }


    public List<User> getUsers() {
        return users;
    }


    public User searchUser(String fn, String ln) {
        for(User user : users){
            if (user.getFirstName().equals(fn) && user.getLastName().equals(ln))
                return user;
        }
        return null;
    }

}
