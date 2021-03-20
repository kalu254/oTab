package com.kalu.otab.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.kalu.otab.model.Lesson;

import java.util.List;

@Dao
public interface LessonDAO {

    @Insert
    void insertLesson(Lesson lesson);

    @Insert
    void insertMultipleLessons(List<Lesson> lessons);

    @Query("DELETE FROM lesson_table")
    void deleteAll();

    @Delete
    void deleteLesson(Lesson lesson);

    @Query("SELECT * from lesson_table ORDER BY id ASC")
    LiveData<List<Lesson>> getAllLessons();




}
