package com.example;

import io.cucumber.java.After;
import io.cucumber.java.Before;

public class Hooks {
    
    @Before
    public void beforeTests() {
        HelperFunctions.startAPI();
    }

    @After
    public void afterTests(){
        HelperFunctions.stopAPI();
    }

}
