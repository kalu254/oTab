package com.kalu.otab.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kalu.otab.DataManagement.Insert;
import com.kalu.otab.R;
import com.kalu.otab.ui.MyLessons.SelectLessonsActivity;

import java.util.ArrayList;

public class SelectDepartmentRecyclerAdapter extends RecyclerView.Adapter<SelectDepartmentRecyclerAdapter.ViewHolder> {
    public static final int NEW_LESSON_ACTIVITY_REQUEST_CODE = 1;

    private final Context mContext;
    private final LayoutInflater mLayoutInflater;
    private final String mUserType;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private ChildEventListener mChildEventListener;
    ArrayList<String> departs = new ArrayList<String>();


    public SelectDepartmentRecyclerAdapter(Context context,String userType) {
        mUserType = userType;
        Log.d("user",mUserType);
        mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference();

        mChildEventListener = new ChildEventListener() {

            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                for (DataSnapshot child: children){

                    departs.add(child.getKey());

                }
                notifyDataSetChanged();

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

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View departmentView = mLayoutInflater.inflate(R.layout.department,parent,false);
        return new ViewHolder(departmentView);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
      holder.mdepartment.setText(departs.get(position));
      holder.department = departs.get(position);
   }

    @Override
    public int getItemCount() {
        return departs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView  mdepartment;
        public String department;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mdepartment = itemView.findViewById(R.id.txt_department);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (mUserType.equals("ADMIN")) {
                        Intent intent = new Intent(mContext, Insert.class);
                        intent.putExtra(Insert.mDepartment, department);
                        mContext.startActivity(intent);
                    }
                    else {
                        Intent intent = new Intent(mContext, SelectLessonsActivity.class);
                        intent.putExtra(SelectLessonsActivity.mDepartment, department);
                        mContext.startActivity(intent);
                    }
                }
            });
        }
    }

}
