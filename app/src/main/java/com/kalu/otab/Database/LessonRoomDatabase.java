package com.kalu.otab.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.kalu.otab.DAO.LessonDAO;
import com.kalu.otab.model.Lesson;

@Database(entities = {Lesson.class}, version = 4, exportSchema = false)
public abstract class LessonRoomDatabase extends RoomDatabase{

    public abstract LessonDAO lessonDAO();


    private static LessonRoomDatabase INSTANCE;

    public static LessonRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (LessonRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), LessonRoomDatabase.class,"unit_db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }

}
