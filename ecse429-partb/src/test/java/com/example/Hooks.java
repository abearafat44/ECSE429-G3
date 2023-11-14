package com.example;

import io.cucumber.java.After;
import io.cucumber.java.Before;

public class Hooks {
    
    @Before
    public void beforeTests() {
        System.out.println("Hook running");
        HelperFunctions.startAPI();
    }

    @After
    public void afterTests(){
        System.out.println("Hook ending");
        HelperFunctions.stopAPI();
    }

}
