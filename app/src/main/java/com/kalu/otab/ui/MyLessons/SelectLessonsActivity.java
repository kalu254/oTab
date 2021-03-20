package com.kalu.otab.ui.MyLessons;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kalu.otab.model.Lesson;
import com.kalu.otab.Database.LessonRoomDatabase;
import com.kalu.otab.model.Unit;
import com.kalu.otab.MainActivity;
import com.kalu.otab.R;
import com.kalu.otab.ui.viewmodel.MyLessonsViewModel;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class SelectLessonsActivity extends AppCompatActivity {


    private List<String> names = new ArrayList<>();
    private List<Unit> unitDetails = new ArrayList<>();

    private Lesson l;
    private List<Lesson> mLessons = new ArrayList<>();
    private Lesson mLesson;

    public static String mDepartment;
    private Context c;
    private static Map<String,Unit> mLessonsSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_lessons);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Select your lessons");


        getDepartment();

        RecyclerView recyclerViewDep = findViewById(R.id.lesson_selection);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        LessonsRecyclerAdapter departmentRecyclerAdapter = new LessonsRecyclerAdapter(this,mDepartment);

        recyclerViewDep.setLayoutManager(linearLayoutManager);
        recyclerViewDep.setAdapter(departmentRecyclerAdapter);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.select_lessons,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id =  item.getItemId();
        if (id == R.id.action_save){
            persistToDb();
            startActivity(new Intent(this, MainActivity.class));
        }
        if(id == android.R.id.home){
            this.finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

     void setTheMap(Map<String,Unit> slsns){

        mLessonsSelected = slsns;
    }
    private void persistToDb() {

        if (mLessonsSelected.size() != 0) {
            Iterator<String> iterator = mLessonsSelected.keySet().iterator();
            while (iterator.hasNext()) {
                names.add(iterator.next());
            }

            Iterator<Unit> iterator1 = mLessonsSelected.values().iterator();
            while (iterator1.hasNext()) {
                Unit u = iterator1.next();
                unitDetails.add(u);

            }
            for (int i = 0; i <= unitDetails.size() - 1; i++) {
                l = new Lesson(unitDetails.get(i).getId(),
                        names.get(i),
                        unitDetails.get(i).getLecture_hall(),
                        unitDetails.get(i).getLecturer(),
                        unitDetails.get(i).getDay(),
                        unitDetails.get(i).getTime());

                mLessons.add(l);
            }


            if (mLessons != null) {
                for (int i = 0; i <= mLessons.size() - 1; i++) {
                    mLesson = mLessons.get(i);
                }
            }

            new InsertLessonsAsync(getApplicationContext()).execute(mLessons);
        }
        else {
            Toast.makeText(this,"Please select some lessons",Toast.LENGTH_SHORT).show();
        }

    }

    private static class InsertLessonsAsync extends AsyncTask<List<Lesson>,Void,Void>{

        public Context mContext;

        public InsertLessonsAsync(Context context) {
            mContext = context;
        }

        @Override
        protected Void doInBackground(List<Lesson>... lists) {
            LessonRoomDatabase.getDatabase(mContext)
                    .lessonDAO()
                    .insertMultipleLessons(lists[0]);
            return null;
        }
    }

    private void getDepartment() {

        Intent dp = getIntent();
        mDepartment = dp.getStringExtra(mDepartment);
    }
}
