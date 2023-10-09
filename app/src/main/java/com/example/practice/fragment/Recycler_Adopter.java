package com.example.practice.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.practice.R;

import java.util.ArrayList;

public class Recycler_Adopter extends RecyclerView.Adapter<Recycler_Adopter.ViewChild> {

    ArrayList<User> users;
    Context context;
    DbHelper dbHelper;
    Recycler_Adopter adopter;
    private boolean noUsers = false;
    TwoFragment twoFragment;

    public Recycler_Adopter(ArrayList<User> users, Context context, Recycler_Adopter adopter, TwoFragment twoFragment) {
        this.users = users;
        this.context = context;
        this.dbHelper = new DbHelper(context, adopter); // Initialize dbHelper here
        this.adopter = adopter;
        this.twoFragment = twoFragment; // Pass the reference to TwoFragment
    }

    @NonNull
    @Override
    public ViewChild onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_design, parent, false);
        return new ViewChild(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewChild holder, int position) {

        User user = users.get(position);

        holder.id.setText(String.valueOf(user.getId()));
        holder.name.setText(makeAsEllipseIfSizeExceeds(user.getName()));
        holder.email.setText(makeAsEllipseIfSizeExceeds(user.getEmail()));
        holder.location.setText(makeAsEllipseIfSizeExceeds(user.getLocation()));

        applyColorForRows(holder, position);

        updateUserById(holder, user);
        deleteUserById(holder, user);

    }

    private void applyColorForRows(ViewChild holder, int position) {
        if (!users.isEmpty()) {
            if (position % 2 == 0) {
                int color = Color.parseColor("#AE838181"); // Replace with the color code you want
                holder.recycler_parent.setBackgroundColor(color); // Set the background color
            }
        }
    }


    private void deleteUserById(ViewChild holder, User user) {

        holder.delete_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPosition = holder.getAdapterPosition(); // Retrieve the adapter position

                if (adapterPosition != RecyclerView.NO_POSITION) {
                    User user = users.get(adapterPosition); // Get the user at the adapter position

                    boolean res = dbHelper.deleteUser(user.getId());

                    if (res) {
                        Toast.makeText(context, "User got deleted", Toast.LENGTH_SHORT).show();
                        users.remove(adapterPosition); // Remove the item from the dataset
                        notifyItemRemoved(adapterPosition); // Notify the adapter about the removal


                        if (users.isEmpty()) {
                            twoFragment.updateTextViewVisibility(true); // Show the empty view
                            Toast.makeText(context, "No users left", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(context, "Error occurred", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void updateUserById(ViewChild holder, User user) {

        holder.update_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Dialog dialog = new Dialog(twoFragment.getActivity());
                dialog.setContentView(R.layout.custom_update_dialog);
                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.getWindow().setWindowAnimations(R.style.customAnimation);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setCancelable(false);

                EditText update_name, update_email, update_location;
                Button update_btn, cancel_btn;

                update_name = dialog.findViewById(R.id.update_name);
                update_email = dialog.findViewById(R.id.update_email);
                update_location = dialog.findViewById(R.id.update_location);
                update_btn = dialog.findViewById(R.id.update_btn);
                cancel_btn = dialog.findViewById(R.id.cancel_btn);


                update_name.setText(user.getName().toString());
                update_email.setText(user.getEmail().toString());
                update_location.setText(user.getLocation().toString());
                update_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isValidEmail = OneFragment.isValidEmail(update_email.getText().toString().trim());

                        if (!isValidEmail) {
                            Toast.makeText(context, "Email is invalid", Toast.LENGTH_SHORT).show();
                        } else if (update_email.getText().toString().isEmpty()) {
                            Toast.makeText(context, "Email is required", Toast.LENGTH_SHORT).show();
                        } else if (update_email.getText().toString().isEmpty() || update_name.getText().toString().isEmpty() || update_location.getText().toString().isEmpty()) {

                            Toast.makeText(context, "You need to fill all the fields", Toast.LENGTH_SHORT).show();
                        } else {
                            int adapterPosition = holder.getAdapterPosition();
                            User updatedUser = new User(user.getId(),
                                                         update_name.getText().toString().trim(),
                                                          update_email.getText().toString().trim(),
                                                           update_location.getText().toString().trim());

                            // Update the database with the new values
                            dbHelper.updateUser(updatedUser);

                            // Update the User object in your ArrayList
                            users.set(adapterPosition, updatedUser);

                            // Notify the adapter that data has changed at this position
                            notifyItemChanged(adapterPosition);

                            dialog.dismiss();
                        }
                    }
                });
                cancel_btn.setOnClickListener(v1 -> {
                    dialog.cancel();
                });

                dialog.show();
            }
        });

    }

    private String makeAsEllipseIfSizeExceeds(String input) {

        if (input.length() > 9) {
            input = input.substring(0, 5) + "...";
        }
        return input;
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class ViewChild extends RecyclerView.ViewHolder {
        TextView id, name, email, location;
        ImageView delete_id, update_id;
        LinearLayout recycler_parent;

        public ViewChild(@NonNull View itemView) {
            super(itemView);

            id = itemView.findViewById(R.id.user_id2);
            name = itemView.findViewById(R.id.user_name2);
            email = itemView.findViewById(R.id.user_email2);
            location = itemView.findViewById(R.id.user_location2);
            delete_id = itemView.findViewById(R.id.delete_id);
            update_id = itemView.findViewById(R.id.update_id);
            recycler_parent = itemView.findViewById(R.id.recycler_parent);
        }
    }
}


//                int adapterPosition = holder.getAdapterPosition(); // Retrieve the adapter position
//
//                if (adapterPosition != RecyclerView.NO_POSITION) {
//                    User user = users.get(adapterPosition); // Get the user at the adapter position
//
//                    boolean res = dbHelper.updateUser(
//                            user.getId(),
//                            holder.name.getText().toString(),
//                            holder.email.getText().toString(),
//                            holder.location.getText().toString()
//                    );
//
//                    if (res) {
//                        Toast.makeText(context, "Updated", Toast.LENGTH_SHORT).show();
//                    } else {
//                        Toast.makeText(context, "Not Updated", Toast.LENGTH_SHORT).show();
//                    }
//                }
//  jessy@gamil.com
//
//holder.update_id.setOnClickListener(new View.OnClickListener() {
//@Override
//public void onClick(View v) {
//
//        Dialog dialog = new Dialog(twoFragment.getActivity());
//        dialog.setContentView(R.layout.custom_update_dialog);
//        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        dialog.getWindow().setWindowAnimations(R.style.customAnimation);
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        dialog.setCancelable(false);
//
//        EditText update_name, update_email, update_location;
//        Button update_btn, cancel_btn;
//
//        update_name = dialog.findViewById(R.id.update_name);
//        update_email = dialog.findViewById(R.id.update_email);
//        update_location = dialog.findViewById(R.id.update_location);
//        update_btn = dialog.findViewById(R.id.update_btn);
//        cancel_btn = dialog.findViewById(R.id.cancel_btn);
//
//
//        update_name.setText(user.getName().toString());
//        update_email.setText(user.getEmail().toString());
//        update_location.setText(user.getLocation().toString());
//        update_btn.setOnClickListener(new View.OnClickListener() {
//@Override
//public void onClick(View v) {
//        boolean isValidEmail = OneFragment.isValidEmail(update_email.getText().toString());
//
//        if (!isValidEmail){
//        Toast.makeText(context, "Email is invalid", Toast.LENGTH_SHORT).show();
//        } else if (update_email.getText().toString().isEmpty()) {
//        Toast.makeText(context, "Email is required", Toast.LENGTH_SHORT).show();
//        }else if (update_email.getText().toString().isEmpty() || update_name.getText().toString().isEmpty() || update_location.getText().toString().isEmpty()) {
//
//        Toast.makeText(context, "You need to fill all the fields", Toast.LENGTH_SHORT).show();
//        }else {
//        int adapterPosition = holder.getAdapterPosition();
//        User updatedUser = new User(user.getId(), update_name.getText().toString(), update_email.getText().toString(), update_location.getText().toString());
//
//        // Update the database with the new values
//        dbHelper.updateUser(user.getId(), update_name.getText().toString(), update_email.getText().toString(), update_location.getText().toString());
//
//        // Update the User object in your ArrayList
//        users.set(adapterPosition, updatedUser);
//
//        // Notify the adapter that data has changed at this position
//        notifyItemChanged(adapterPosition);
//
//        dialog.dismiss();
//        }
//        }
//        });
//        cancel_btn.setOnClickListener(v1 -> {
//        dialog.cancel();
//        });
//
//
//        dialog.show();
//        }
//        });


//holder.delete_id.setOnClickListener(new View.OnClickListener() {
//@Override
//public void onClick(View v) {
//        int adapterPosition = holder.getAdapterPosition(); // Retrieve the adapter position
//
//        if (adapterPosition != RecyclerView.NO_POSITION) {
//        User user = users.get(adapterPosition); // Get the user at the adapter position
//
//        boolean res = dbHelper.deleteUser(user.getId());
//
//        if (res) {
//        Toast.makeText(context, "User got deleted", Toast.LENGTH_SHORT).show();
//        users.remove(adapterPosition); // Remove the item from the dataset
//        notifyItemRemoved(adapterPosition); // Notify the adapter about the removal
//
//        if (users.isEmpty()) {
//        twoFragment.updateTextViewVisibility(true); // Show the empty view
//        Toast.makeText(context, "No users left", Toast.LENGTH_SHORT).show();
//        }
//
//        } else {
//        Toast.makeText(context, "Error occurred", Toast.LENGTH_SHORT).show();
//        }
//        }
//        }
//        });