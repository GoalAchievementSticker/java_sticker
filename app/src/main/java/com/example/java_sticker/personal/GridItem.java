package com.example.java_sticker.personal;

import android.net.Uri;
import android.widget.ImageView;

import java.util.ArrayList;

public class GridItem {

    String goal_id; //도장판 아이디(0~1)
    String test;//도장판

    public GridItem(String goal_id, String test){
        this.goal_id = goal_id;
        //this.goal_img_Id = goal_img_Id;
        this.test = test;

    }

    public GridItem(){


    }

    public  String getGoal_id() {
        return goal_id;
    }

    public void setGoal_id( String goal_id) {
        this.goal_id = goal_id;
    }

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }



}