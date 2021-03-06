package com.kalu.otab.repository;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.kalu.otab.DAO.LessonDAO;
import com.kalu.otab.Database.LessonRoomDatabase;
import com.kalu.otab.model.Lesson;

import java.util.List;

public class DetailsRepository {

    private LessonDAO mLessonDAO;
    private LiveData<List<Lesson>> mAllLessons;

    public DetailsRepository(Application application) {
        LessonRoomDatabase db = LessonRoomDatabase.getDatabase(application);
        mLessonDAO = db.lessonDAO();

        mAllLessons = mLessonDAO.getAllLessons();


    }

    public LiveData<List<Lesson>> getAllLessons() {
        return mAllLessons;
    }


    public void insertLesson(Lesson lesson){
        new insertAsyncTask(mLessonDAO).execute(lesson);
    }

    public void deleteAllLessons(){
        new DeleteAllAsyncTask(mLessonDAO).execute();
    }

    public void deleteLesson(Lesson lesson) {

        new DeleteLessonAsyncTask(mLessonDAO).execute(lesson);
    }

    private static class insertAsyncTask extends AsyncTask<Lesson, Void, Void> {

        private LessonDAO mAsyncTaskDao;

        insertAsyncTask(LessonDAO dao) {
            mAsyncTaskDao = dao;
        }


        @Override
        protected Void doInBackground(Lesson... params) {
            mAsyncTaskDao.insertLesson(params[0]);
            return null;
        }

    }
    private class DeleteAllAsyncTask extends AsyncTask<Void,Void,Void>{

        private LessonDAO mLessonDAO;

        public DeleteAllAsyncTask(LessonDAO lessonDAO) {
            mLessonDAO = lessonDAO;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            mLessonDAO.deleteAll();
            return null;
        }
    }

    private class DeleteLessonAsyncTask extends AsyncTask<Lesson,Void,Void>{
        private  LessonDAO mLessonDAO;

        public DeleteLessonAsyncTask(LessonDAO lessonDAO) {
            mLessonDAO = lessonDAO;
        }

        @Override
        protected Void doInBackground(Lesson... lessons) {
            mLessonDAO.deleteLesson(lessons[0]);
            return null;
        }
    }

}
