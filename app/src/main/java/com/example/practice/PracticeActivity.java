package com.example.practice;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class PracticeActivity extends AppCompatActivity {

    Button dialog_btn, cancelBtn, updateBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_practice);
        dialog_btn = findViewById(R.id.dialog_btn);

        dialog_btn.setOnClickListener(v -> {
            Dialog dialog = new Dialog(this);

            dialog.setContentView(R.layout.custom_update_dialog);
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().setWindowAnimations(R.style.customAnimation);

            dialog.findViewById(R.id.cancel_btn).setOnClickListener(v1 -> {
                Toast.makeText(this, "Cancel btn clicked", Toast.LENGTH_SHORT).show();
                dialog.cancel();
            });
            dialog.findViewById(R.id.update_btn).setOnClickListener(v1 -> {
                Toast.makeText(this, "Update btn clicked", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            });
            dialog.show();
        });
    }
}