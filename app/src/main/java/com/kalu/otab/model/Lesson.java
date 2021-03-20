package com.kalu.otab.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "lesson_table")
public class Lesson implements Parcelable {

    @ColumnInfo(name = "id")
    @PrimaryKey
    @NonNull
    private String id;

    @ColumnInfo(name = "name")
    @NonNull
    private String name;

    @ColumnInfo(name = "lecturer_hall")
    @NonNull
    private String lecture_hall;

    @ColumnInfo(name = "lecturer")
    @NonNull
    private String lecturer;

    @ColumnInfo(name = "day")
    @NonNull
    private String day;

    @ColumnInfo(name = "time")
    @NonNull
    private String time;


    public Lesson(@NonNull String id, @NonNull String name, @NonNull String lecture_hall, @NonNull String lecturer, @NonNull String day, @NonNull String time) {
        this.id = id;
        this.name = name;
        this.lecture_hall = lecture_hall;
        this.lecturer = lecturer;
        this.day = day;
        this.time = time;
    }

    public Lesson(Parcel in) {
        id = in.readString();
        name = in.readString();
        lecture_hall = in.readString();
        lecturer = in.readString();
        day = in.readString();
        time = in.readString();
    }

    public static final Creator<Lesson> CREATOR = new Creator<Lesson>() {
        @Override
        public Lesson createFromParcel(Parcel in) {
            return new Lesson(in);
        }

        @Override
        public Lesson[] newArray(int size) {
            return new Lesson[size];
        }
    };



    public Lesson(Parcelable parcelableExtra) {

    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    @NonNull
    public String getLecture_hall() {
        return lecture_hall;
    }

    public void setLecture_hall(@NonNull String lecture_hall) {
        this.lecture_hall = lecture_hall;
    }

    @NonNull
    public String getLecturer() {
        return lecturer;
    }

    public void setLecturer(@NonNull String lecturer) {
        this.lecturer = lecturer;
    }

    @NonNull
    public String getDay() {
        return day;
    }

    public void setDay(@NonNull String day) {
        this.day = day;
    }

    @NonNull
    public String getTime() {
        return time;
    }

    public void setTime(@NonNull String time) {
        this.time = time;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(lecture_hall);
        dest.writeString(lecturer);
        dest.writeString(day);
        dest.writeString(time);
    }
}
