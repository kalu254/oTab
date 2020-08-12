package com.kalu.otab.ui.Admin;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kalu.otab.R;
import com.kalu.otab.ui.SelectDepartmentRecyclerAdapter;

public class SelectDepartment extends Fragment {

    private String userType = "ADMIN";
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.activity_departments, container, false);

        RecyclerView recyclerViewDep = root.findViewById(R.id.departments);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(root.getContext(),2);
        SelectDepartmentRecyclerAdapter selectDepartmentRecyclerAdapter = new SelectDepartmentRecyclerAdapter(root.getContext(),userType);

        recyclerViewDep.setLayoutManager(gridLayoutManager);
        recyclerViewDep.setAdapter(selectDepartmentRecyclerAdapter);
        return root;
    }
}