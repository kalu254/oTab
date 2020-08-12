package com.kalu.otab.DataManagement;

import androidx.room.Entity;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Unit {

    private String id;
    private String day;
    private String lecture_hall;
    private String lecturer;
    private String time;


    public Unit() {
    }

    public Unit(String id, String day, String lecture_hall, String lecturer, String time) {
        this.id = id;
        this.day = day;
        this.lecture_hall = lecture_hall;
        this.lecturer = lecturer;
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public String getDay() {
        return day;
    }

    public String getLecture_hall() {
        return lecture_hall;
    }

    public String getLecturer() {
        return lecturer;
    }

    public String getTime() {
        return time;
    }

    @Override
    public String toString() {
        return "Unit{" +
                "id='" + id + '\'' +
                ", day='" + day + '\'' +
                ", lecture_hall='" + lecture_hall + '\'' +
                ", lecturer='" + lecturer + '\'' +
                ", time='" + time + '\'' +
                '}';
    }

    @Exclude
    public Map<String, Object> toMap() {

            HashMap<String, Object> result = new HashMap<>();
            result.put("id",id);
            result.put("day", day);
            result.put("lecture_hall", lecture_hall);
            result.put("lecturer", lecturer);
            result.put("time", time);

            return result;
    }
}