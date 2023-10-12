package com.example.practice.searchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.practice.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchActivity extends AppCompatActivity {


    ArrayList<User> users;
    RecyclerView recyclerView_id;
    SearchView searchView_id;
    SearchAdopter adopter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        recyclerView_id = findViewById(R.id.recyclerView_id);
        searchView_id = findViewById(R.id.searchView_id);


        users = new ArrayList<>();

        users.add(new User(1, "one", "one", "one@gmail.com", "Female"));
        users.add(new User(2, "two", "two", "two@gmail.com", "Male"));
        users.add(new User(3, "three", "three", "three@gmail.com", "Female"));
        users.add(new User(4, "four", "four", "four@gmail.com", "Male"));
        users.add(new User(5, "five", "five", "five@gmail.com", "Female"));
//        users.add(new User(6, "six", "six", "six@gmail.com", "Male"));
//        users.add(new User(7, "seven", "seven", "seven@gmail.com", "Female"));


        recyclerView_id.setLayoutManager(new LinearLayoutManager(this));

        adopter = new SearchAdopter(users, SearchActivity.this);
        recyclerView_id.setAdapter(adopter);

        searchView_id.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String input) {
                filterRecords(input);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String input) {

                filterRecords(input);
                return true;
            }
        });
    }

    private void filterRecords(String input) {
        String userInput = input.toLowerCase().trim();
        ArrayList<User> filteredUsers = new ArrayList<>();

        if (input.isEmpty()) {
            filteredUsers.addAll(users);
        } else {
            filteredUsers.clear();
            for (User user : users) {
                if (user.getUser_first_name().toLowerCase().contains(userInput) ||
                        user.getUser_last_name().toLowerCase().contains(userInput) ||
                        user.getUser_email().toLowerCase().contains(userInput)) {
                    filteredUsers.add(user);
                }
            }
        }
        adopter.filteredData(filteredUsers);
    }
}