package com.example.practice.searchView;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.practice.R;

import java.util.ArrayList;

public class SearchAdopter extends RecyclerView.Adapter<SearchAdopter.ViewChild> {

    ArrayList<User> users;
    Context context;

    public SearchAdopter(ArrayList<User> users, Context context) {
        this.users = users;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewChild onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.search_recycler_design, parent, false);
        return new ViewChild(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewChild holder, int position) {

        User user = users.get(position);

        holder.firstName.setText(user.getUser_first_name());
        holder.lastName.setText(user.getUser_last_name());
        holder.email.setText(user.getUser_email());
        holder.gender.setText(user.getUser_gender());
        Log.d("test_",user.getUser_gender());
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public void filteredData(ArrayList<User> filteredUsers) {
//        users.clear();
//        this.users = filteredUsers;

        this.users.clear(); // Clear the existing data.
        this.users.addAll(filteredUsers); // Add the filtered data.
        notifyDataSetChanged(); // Notify the adapter of the data change.
    }

    public class ViewChild extends RecyclerView.ViewHolder{

        TextView firstName, lastName, email, gender;

        public ViewChild(@NonNull View itemView) {
            super(itemView);

            firstName = itemView.findViewById(R.id.firstName);
            lastName = itemView.findViewById(R.id.lastName);
            email = itemView.findViewById(R.id.email);
            gender = itemView.findViewById(R.id.gender);
        }
    }
}
