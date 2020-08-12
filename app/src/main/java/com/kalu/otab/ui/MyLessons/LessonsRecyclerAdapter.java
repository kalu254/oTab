package com.kalu.otab.ui.MyLessons;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kalu.otab.DataManagement.Unit;
import com.kalu.otab.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class LessonsRecyclerAdapter extends RecyclerView.Adapter<LessonsRecyclerAdapter.ViewHolder> {

    public static String mDepartment;
    private  Context mContext;
    private  LayoutInflater mLayoutInflater;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private ChildEventListener mChildEventListener;

    Map<String,Unit> mUnits = new HashMap<>();
    private List<Unit> mUnitList;
    private List<String> mUnitName;
    private Iterator<String> mName;
    private Map<String,Unit> mLessonsSelected = new HashMap<>();;



    public LessonsRecyclerAdapter(String dep) {
        mDepartment = dep;
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference().child("Units").child(mDepartment);

        mChildEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

               mUnits.put(dataSnapshot.getKey(),dataSnapshot.getValue(Unit.class));
               notifyDataSetChanged();

                mUnitList = new ArrayList<>();
                mUnitName = new ArrayList<>();
                mName = mUnits.keySet().iterator();

                while (mName.hasNext()){
                    mUnitName.add(mName.next());
                }

                Iterator<Unit> iterator = mUnits.values().iterator();
                while (iterator.hasNext()){
                    mUnitList.add(iterator.next());

                }

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        mDatabaseReference.addChildEventListener(mChildEventListener);
    }

    public LessonsRecyclerAdapter(Context context,String dep) {
            this(dep);
            mContext = context;
            mLayoutInflater = LayoutInflater.from(mContext);
        }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View unit = mLayoutInflater.inflate(R.layout.unit,parent,false);
        return new ViewHolder(unit);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        for (int i=position; i<mUnitList.size();i++){
            holder.name.setText("Lecture: " + mUnitName.get(position));
            Unit unit = mUnitList.get(position);
            holder.id.setText("Id: " + unit.getId());
            holder.day.setText("Day: "+ unit.getDay());
            holder.lecture_hall.setText("Lecture Hall: "+ unit.getLecture_hall());
            holder.lecturer.setText("Lecturer: "+ unit.getLecturer());
            holder.time.setText("Time "+ unit.getTime());
        }

        holder.selectedLesson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                    final boolean isChecked = holder.selectedLesson.isChecked();
                        if (isChecked) {
                            if (!mLessonsSelected.containsKey(mUnitName.get(position)))
                                mLessonsSelected.put(mUnitName.get(position),mUnitList.get(position));
                            notifyDataSetChanged();

                        } else {
                            mLessonsSelected.remove(mUnitName.get(position));
                        }

            }


        });
        new SelectLessonsActivity().setTheMap(mLessonsSelected);
    }



    @Override
    public int getItemCount() {
        return mUnits.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

            TextView name;
            TextView day;
            TextView id;
            TextView lecturer;
            TextView lecture_hall;
            TextView time;
            CheckBox selectedLesson;


            public ViewHolder(@NonNull View itemView) {
                super(itemView);

                name = itemView.findViewById(R.id.txt_name);
                id = itemView.findViewById(R.id.txt_id);
                day = itemView.findViewById(R.id.txt_day);
                lecturer = itemView.findViewById(R.id.txt_lecturer);
                lecture_hall = itemView.findViewById(R.id.txt_lecture_hall);
                time = itemView.findViewById(R.id.txt_time);
                selectedLesson = itemView.findViewById(R.id.select_lesson);



            }


        }

    }
