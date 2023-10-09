package com.example.practice.fragment;

import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.practice.R;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class OneFragment extends Fragment {

    EditText name, email, location;
    Button insert_btn, view_btn;
    View rootView;
    DbHelper dbHelper;
    Recycler_Adopter adopter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Initialize dbHelper with the adapter
        dbHelper = new DbHelper(getActivity().getApplicationContext(), adopter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_one, container, false);

        name = rootView.findViewById(R.id.userName_id);
        email = rootView.findViewById(R.id.userEmail_id);
        location = rootView.findViewById(R.id.userLocation_id);
        insert_btn = rootView.findViewById(R.id.insert_btn);
        view_btn = rootView.findViewById(R.id.view_btn);

        insertUser();
        viewUser();
        return rootView;
    }

    public void insertUser() {
        insert_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String isEmail = email.getText().toString().trim();
                if (isEmail.isEmpty()) {
                    Toast.makeText(getContext().getApplicationContext(), "Email is required", Toast.LENGTH_SHORT).show();
                    return; // Exit the method
                } else if (!isValidEmail(isEmail)) {
                    Toast.makeText(getContext().getApplicationContext(), "Invalid email", Toast.LENGTH_SHORT).show();
                    return; // Exit the method
                } else {

                    boolean isUserPresentInDb = dbHelper.getUserByEmail(email.getText().toString());

                    if (isUserPresentInDb) {
                        Toast.makeText(getContext().getApplicationContext(), "User is already present in DB", Toast.LENGTH_SHORT).show();
                    } else {

                        boolean isInserted = dbHelper.insertData(name.getText().toString().trim(),
                                email.getText().toString().trim(),
                                location.getText().toString().trim());

                        if (isInserted) {
                            Toast.makeText(getActivity().getApplicationContext(), name.getText().toString().trim() + " is inserted", Toast.LENGTH_SHORT).show();
                            name.setText(null);
                            email.setText(null);
                            location.setText(null);
                        } else {
                            Toast.makeText(getActivity().getApplicationContext(), "Data is not inserted", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
    }

    public static boolean isValidEmail(String email) {
        String emailRegex_pattern = "^[a-z]+[a-z0-9]*@[a-z]{2,}\\.[a-z]{2,3}$";
        boolean isValidMail = email.matches(emailRegex_pattern);
        return isValidMail;
    }

    public void viewUser() {
        view_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TwoFragment twoFragment = new TwoFragment();

//                    Bundle bundle = new Bundle();
//                    bundle.putSerializable("Users_data", users);
//                    twoFragment.setArguments(bundle);

                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frameLayout, twoFragment)
                        .addToBackStack(null) // Optional: Allow back navigation
                        .commit();
            }
        });
    }
}