package com.kalu.otab.DataManagement;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.Arrays;
import java.util.List;

@Database(entities = {Lesson.class}, version = 1, exportSchema = false)
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

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final LessonDAO mLessonDAO;
        private List<Lesson> lessons = Arrays.asList(
                new Lesson("1","ms word","9","mr obongi","monday","8:00 am - 10:00 am"),
                new Lesson("2","ms access","4","mr mako","tuesday","10:00 am - 12:00")
        );




        PopulateDbAsync(LessonRoomDatabase db) {
            mLessonDAO = db.lessonDAO();
        }

        @Override
        protected Void doInBackground(final Void... params) {
            // Start the app with a clean database every time.
            // Not needed if you only populate the database
            // when it is first created
            mLessonDAO.deleteAll();

            for (int i = 0; i <= lessons.size() - 1; i++) {
                mLessonDAO.insertLesson(lessons.get(i));
            }
            return null;
        }
    }

}
