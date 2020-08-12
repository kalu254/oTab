package com.kalu.otab.ui.MyLessons;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kalu.otab.DataManagement.Lesson;
import com.kalu.otab.R;

import java.util.List;

public class MyLessonsRecyclerAdapter extends RecyclerView.Adapter<MyLessonsRecyclerAdapter.LessonViewHolder> {

    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private List<Lesson> mLessons;

    public MyLessonsRecyclerAdapter(Context context) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public LessonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View myLesson = mLayoutInflater.inflate(R.layout.lesson,parent,false);
        return new LessonViewHolder(myLesson);
    }

    @Override
    public void onBindViewHolder(@NonNull LessonViewHolder holder, int position) {
        Lesson lesson = mLessons.get(position);

        holder.lsnNumber.setText(lesson.getId());
        holder.lsnName.setText(lesson.getName());
        holder.lsnLecturer.setText(lesson.getLecturer());
        holder.lsnLectureHall.setText(lesson.getLecture_hall());
        holder.lsnTime.setText(lesson.getTime());
    }

    @Override
    public int getItemCount() {
        if (mLessons != null){
            return mLessons.size();
        }
        else {
            return 0;
        }
    }

     void setTheLessons(List<Lesson> lessons) {
        mLessons = lessons;
        if (mLessons == null){
            Toast.makeText(mContext,"Please select your lessons in your department",Toast.LENGTH_LONG).show();
        }
        notifyDataSetChanged();
    }

    public Lesson getLessonAt(int position) {
        return mLessons.get(position);
    }

    public class LessonViewHolder extends RecyclerView.ViewHolder{


        TextView lsnNumber;
        TextView lsnName;
        TextView lsnLecturer;
        TextView lsnLectureHall;
        TextView lsnTime;

        public LessonViewHolder(@NonNull View itemView) {
            super(itemView);

            lsnNumber = itemView.findViewById(R.id.lesson_number);
            lsnName = itemView.findViewById(R.id.lesson_name);
            lsnLecturer = itemView.findViewById(R.id.lesson_lecturer);
            lsnLectureHall = itemView.findViewById(R.id.lesson_lecture_hall);
            lsnTime = itemView.findViewById(R.id.lesson_time);
        }
    }
}
