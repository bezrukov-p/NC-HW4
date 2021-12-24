package com.company.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

public class User {

    @NotEmpty(message = "Name should not be empty")
    private String firstName;

    @NotEmpty(message = "Name should not be empty")
    private String lastName;

    @NotEmpty(message = "Name should not be empty")
    private String patronymic;

    @Min(value = 0, message = "Age should be greater than 0")
    private int age;

    @Min(value = 0, message = "Salary should be greater than 0")
    private int salary;

    @NotEmpty(message = "Email should not be empty")
    @Email(message = "Email should be valid")
    private String email;

    @NotEmpty(message = "Place of work should not be empty")
    private String placeOfWork;

    public void stringToUser(String str) {
        String[] variables = str.split(" ");
        this.firstName = variables[0];
        this.lastName = variables[1];
        this.patronymic = variables[2];
        this.age = Integer.parseInt(variables[3]);
        this.salary = Integer.parseInt(variables[4]);
        this.email = variables[5];
        this.placeOfWork = variables[6];
    }

    @Override
    public String toString() {
        return this.firstName + " " + this.lastName + " "
                + this.patronymic + " " + this.age + " "
                + this.salary + " " + this.email + " "
                + this.placeOfWork;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPlaceOfWork() {
        return placeOfWork;
    }

    public void setPlaceOfWork(String placeOfWork) {
        this.placeOfWork = placeOfWork;
    }
}
