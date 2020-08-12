package com.kalu.otab.ui.MyLessons;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kalu.otab.DataManagement.Lesson;
import com.kalu.otab.R;

import java.util.List;

public class MyLessons extends Fragment {

    public static final int NEW_LESSON_ACTIVITY_REQUEST_CODE = 1;
    private MyLessonsViewModel mMyLessonsViewModel;

    Context thisContext;
    private Bundle bundle;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        thisContext = container.getContext();


        View root = inflater.inflate(R.layout.activity_my_lessons, container, false);
        return root;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView lessonRecycler = view.findViewById(R.id.my_lessons);
        final MyLessonsRecyclerAdapter myLessonsRecyclerAdapter = new MyLessonsRecyclerAdapter(view.getContext());
        lessonRecycler.setAdapter(myLessonsRecyclerAdapter);
        lessonRecycler.setLayoutManager(new LinearLayoutManager(view.getContext()));

        mMyLessonsViewModel =  ViewModelProviders.of(this).get(MyLessonsViewModel.class);
        mMyLessonsViewModel.getAllLessons().observe(getViewLifecycleOwner(), new Observer<List<Lesson>>() {
            @Override
            public void onChanged(@Nullable final List<Lesson> lessons) {
                // Update the cached copy of the words in the adapter.
                myLessonsRecyclerAdapter.setTheLessons(lessons);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                mMyLessonsViewModel.deleteLesson(myLessonsRecyclerAdapter.getLessonAt(viewHolder.getAdapterPosition()));
                Toast.makeText(thisContext,"Lesson deleted",Toast.LENGTH_SHORT).show();

            }
        }).attachToRecyclerView(lessonRecycler);

    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.delete_all,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_delete_all){
            mMyLessonsViewModel.deleteAll();
            Toast.makeText(thisContext, "All lessons have been deleted", Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }
}