package com.company.model;

import java.util.Date;

public class Information {
    Date date;
    String nameClient;

    public Information(Date date, String nameClient) {
        this.date = date;
        this.nameClient = nameClient;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getNameClient() {
        return nameClient;
    }

    public void setNameClient(String nameClient) {
        this.nameClient = nameClient;
    }
}
