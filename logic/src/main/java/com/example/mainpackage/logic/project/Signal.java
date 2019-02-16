/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.mainpackage.logic.project;

import java.util.List;

/**
 *
 * @author BrunoCoelho
 */
public class Signal {
    private List<Combination> combinations;

    public Signal(List<Combination> combinations) {
        this.combinations = combinations;
    }
    
    public List<Combination> getCombinations() {
        return combinations;
    }

    public void setCombinations(List<Combination> combinations) {
        this.combinations = combinations;
    }
    
}
