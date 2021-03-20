package com.kalu.otab.ui.Admin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.kalu.otab.R;
import com.kalu.otab.model.Unit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Insert extends AppCompatActivity {
    public static String mDepartment;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    List<String> mSpinnerUnits = new ArrayList<>();

    EditText id;
    EditText day;
    EditText lectureHall;
    EditText lecturer;
    EditText time;
    Spinner mSpinner;

    Button save;
    private Map<String, Unit> mUnitDetails;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);

        getDepart();
        fillSpinner();

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String unitName = (String) mSpinner.getSelectedItem();
                String ID = id.getText().toString();
                String Day = day.getText().toString();
                String LectureHall = lectureHall.getText().toString();
                String Lecturer = lecturer.getText().toString();
                String Time = time.getText().toString();

                String key = mFirebaseDatabase.getReference().child("Units").child(mDepartment).child(unitName).getKey();
                Unit unit = new Unit(ID,Day,LectureHall,Lecturer,Time);
                Map<String,Object> u = unit.toMap();

                Map<String,Object> childUpdates = new HashMap<>();
                childUpdates.put(key,unit);

                mDatabaseReference.updateChildren(childUpdates);

                Toast.makeText(Insert.this, "clicked", Toast.LENGTH_SHORT).show();

                clear();
            }


            private void clear() {
                id.setText(" ");
                day.setText(" ");
                lectureHall.setText(" ");
                lecturer.setText(" ");
                time.setText(" ");
            }
        });


    }

    private void fillSpinner() {

        id = findViewById(R.id.edt_txt_id);
        day = findViewById(R.id.edt_txt_day);
        lectureHall = findViewById(R.id.edt_txt_lecture_hall);
        lecturer  = findViewById(R.id.edt_txt_lecturer);
        time = findViewById(R.id.edt_txt_time);
        save = findViewById(R.id.btn_update_unit);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference().child("Units").child(mDepartment);

        Query unitsInSpinner = mDatabaseReference.orderByKey();
        unitsInSpinner.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                mUnitDetails = (Map<String, Unit>) dataSnapshot.getValue();
                Iterator<String> iterator = mUnitDetails.keySet().iterator();
                while (iterator.hasNext()){
                    mSpinnerUnits.add(iterator.next());
                }


                mSpinner = findViewById(R.id.spinner_unit_names);

                ArrayAdapter<String> spinnerFill = new ArrayAdapter<>(Insert.this,android.R.layout.simple_spinner_item,mSpinnerUnits);
                spinnerFill.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                mSpinner.setAdapter(spinnerFill);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void getDepart() {
        Intent dep = getIntent();
        mDepartment = dep.getStringExtra(mDepartment);
    }


}
