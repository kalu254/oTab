package com.kalu.otab.ui.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.kalu.otab.model.Lesson;
import com.kalu.otab.repository.DetailsRepository;

import java.util.List;

public class MyLessonsViewModel extends AndroidViewModel {

    private DetailsRepository mDetailsRepository;
    private LiveData<List<Lesson>> mAllLessons;

    public MyLessonsViewModel(@NonNull Application application) {
        super(application);

        mDetailsRepository = new DetailsRepository(application);
        mAllLessons = mDetailsRepository.getAllLessons();

    }

    public LiveData<List<Lesson>> getAllLessons() {
        return mAllLessons;
    }

    public void insert(Lesson lesson){
        mDetailsRepository.insertLesson(lesson);
    }

    public void deleteAll() {
        mDetailsRepository.deleteAllLessons();
    }

    public void deleteLesson(Lesson l) {
        mDetailsRepository.deleteLesson(l);
    }
}
