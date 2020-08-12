package com.kalu.otab.ui.MyLessons;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.kalu.otab.DataManagement.Lesson;
import com.kalu.otab.DataManagement.LessonRepository;

import java.util.List;

public class MyLessonsViewModel extends AndroidViewModel {

    private LessonRepository mLessonRepository;
    private LiveData<List<Lesson>> mAllLessons;

    public MyLessonsViewModel(@NonNull Application application) {
        super(application);

        mLessonRepository = new LessonRepository(application);
        mAllLessons = mLessonRepository.getAllLessons();

    }

    public LiveData<List<Lesson>> getAllLessons() {
        return mAllLessons;
    }

    public void insert(Lesson lesson){
        mLessonRepository.insert(lesson);
    }

    public void deleteAll() {
        mLessonRepository.deleteAll();
    }

    public void deleteLesson(Lesson l) {
        mLessonRepository.deleteLesson(l);
    }
}
