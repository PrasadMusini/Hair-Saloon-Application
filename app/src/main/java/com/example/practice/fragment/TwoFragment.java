package com.example.practice.fragment;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.practice.R;

import java.util.ArrayList;

public class TwoFragment extends Fragment {

    Recycler_Adopter adopter;
    RecyclerView recyclerView;
    DbHelper dbHelper;
    TextView emptyTextView;
    ArrayList<User> users;

    public static boolean noUsers = false;

    public TwoFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHelper = new DbHelper(getActivity().getApplicationContext(), adopter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_two, container, false);

        recyclerView = view.findViewById(R.id.recycler_twoFrag);
        emptyTextView = view.findViewById(R.id.emptyTextView);

        users = new ArrayList<>();

        Cursor result = dbHelper.getUsers();

        while (result.moveToNext()) {
            int x = result.getColumnIndex(dbHelper.USER_NAME);
            int id = result.getInt(0);
            String name = result.getString(x);
            String email = result.getString(2);
            String location = result.getString(3);

            users.add(new User(id, name, email, location));
        }
//        test@test.com

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        adopter = new Recycler_Adopter(users, getActivity().getApplicationContext(), adopter, this);

        recyclerView.setAdapter(adopter);

        // Check if the RecyclerView is empty
        updateTextViewVisibility(users.isEmpty());

//        // Check if the RecyclerView is empty
//        if (adopter.getItemCount() == 0) {
//            emptyTextView.setVisibility(View.VISIBLE); // Show the message
//        } else {
//            emptyTextView.setVisibility(View.GONE); // Hide the message
//        }
        return view;
    }

    public void updateTextViewVisibility(boolean isEmpty) {
        if (emptyTextView != null) {
            emptyTextView.setVisibility(isEmpty ? View.VISIBLE : View.GONE);
        }
    }



//    @Override
//    public void onResume() {
//        super.onResume();
//        Toast.makeText(getActivity().getApplicationContext(), "onResume2", Toast.LENGTH_SHORT).show();
//    }
//
//    @Override
//    public void onStart() {
//        super.onStart();
//        Toast.makeText(getActivity().getApplicationContext(), "onStart2", Toast.LENGTH_SHORT).show();
//    }

}


//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        View view = inflater.inflate(R.layout.fragment_two, container, false);
//        recyclerView = view.findViewById(R.id.recycler_twoFrag);
//
//
//        Bundle bundle = getArguments();
//
//        if(bundle != null){
//            ArrayList<User> users = (ArrayList<User>) bundle.getSerializable("Users_data");
//            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
//            adopter = new Recycler_Adopter(users,getActivity().getApplicationContext());
//            recyclerView.setAdapter(adopter);
//        }
//        return view;
//    }

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        View view = inflater.inflate(R.layout.fragment_two, container, false);
//        recyclerView = view.findViewById(R.id.recycler_twoFrag);
//
//        Bundle bundle = getArguments();
//
//        if (bundle != null) {
//            ArrayList<User> users = (ArrayList<User>) bundle.getSerializable("Users_data");
//            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
//            adopter = new Recycler_Adopter(users, getActivity().getApplicationContext(), adopter); // Pass adopter reference
//            recyclerView.setAdapter(adopter);
//
//            // Initialize the dbHelper with the adapter
////            dbHelper = new DbHelper(getActivity().getApplicationContext(), adopter);
//            dbHelper = new DbHelper(getActivity().getApplicationContext(), adopter);
//        }
//        return view;
//    }
