package com.company.dao;

import com.company.model.User;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Component
public class UserDAO {
    private List<User> users = new ArrayList<>();
    private final String FILE_PATH = "src/main/resources/";
    private final String UPLOADED_FILES_PATH = "src/main/resources/uploaded/";
    private final String FILE_NAME = "users.txt";

    public UserDAO() throws IOException {
        addUsersToListFromFile(FILE_PATH + FILE_NAME);
    }

    private void addUsersToListFromFile(String filePath) throws IOException {
        File file = new File(filePath);
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

        File file = new File(FILE_PATH + FILE_NAME);
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

    public void addUsersFromFile(MultipartFile file) throws IOException {
        uploadFile(file);
        addUsersToListFromFile(UPLOADED_FILES_PATH + file.getOriginalFilename());
        fileOverwrite(FILE_PATH + FILE_NAME);
    }

    private void fileOverwrite(String filePath) throws IOException {
        File oldFile = new File(filePath);
        oldFile.delete();

        File newFile = new File(filePath);
        FileWriter fw = new FileWriter(newFile, true);
        for(User user : users){
            fw.write(user.toString() + "\n");
        }
        fw.close();
    }

    private void uploadFile(MultipartFile file) throws IOException {
        File fileFrom = new File(UPLOADED_FILES_PATH + file.getOriginalFilename());
        fileFrom.createNewFile();
        FileOutputStream fout = new FileOutputStream(fileFrom);
        fout.write(file.getBytes());
        fout.flush();
        fout.close();
    }

}
