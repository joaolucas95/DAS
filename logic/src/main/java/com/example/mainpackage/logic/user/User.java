/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.mainpackage.logic.user;

import java.io.Serializable;

public class User implements Serializable{

    private static User sInstance;

    private String username;

    public static User getInstance() {
        if (sInstance == null) {
            synchronized (User.class) {
                if (sInstance == null) {
                    sInstance = new User();
                }
            }
        }

        return sInstance;
    }

    private User() {
        // Do nothing.
    }
    
    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return this.username;
    }

    @Override
    public String toString() {
        return "User{" + "username=" + username + '}';
    }

}