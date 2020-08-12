package com.kalu.otab.ui.MyLessons;

import android.util.Log;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.kalu.otab.DataManagement.Unit;

import java.util.HashMap;
import java.util.Map;

public class UserSelectedLessons {

    private Map<String,Unit> userSelection = new HashMap<>();

    FloatingActionButton fab;
    public UserSelectedLessons() {
    }

    public UserSelectedLessons(Map<String, Unit> userSelection) {
        this.userSelection = userSelection;
    }


    public void getUserSelection() {

    }

    public void setUserSelection(Map<String, Unit> userSelection) {
        this.userSelection = userSelection;
    }
}
