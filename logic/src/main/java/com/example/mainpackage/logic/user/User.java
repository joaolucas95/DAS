/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.mainpackage.logic.user;

import java.io.Serializable;

public class User implements Serializable{
    
    private final String username;
    
    public static User createUser(String username) {
        if (username == null || username.isEmpty()) {
            return null;
        }
        
        return new User(username);
    }
    
    private User(String username) {
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