package com.example.practice.crud.fragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.Toast;

import com.example.practice.R;
import com.example.practice.crud.fragment.OneFragment;
import com.example.practice.crud.fragment.ThreeFragment;
import com.example.practice.crud.fragment.TwoFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LayoutInflater inflater = getLayoutInflater();

        replaceFragment(new OneFragment());

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(view -> {

            switch(view.getItemId()){
                case R.id.home :
                    replaceFragment(new OneFragment());
                    break;
                case R.id.settings :
                    replaceFragment(new TwoFragment());
                    break;
                case R.id.heaven :
                    replaceFragment(new ThreeFragment());
                    break;
            }
            return true;
        });
    }

    private void replaceFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayout,fragment)
                .commit();
    }

    private void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

}


//        Make sure your app is running in debug mode.
//        In Android Studio, go to View > Tool Windows > Database Inspector.
//        Select your app process from the dropdown menu.
//        Navigate to the "File Explorer" tab, expand the "data" folder,
//        and you should see your app's database file (usually named something like "your_app_name.db").
//        Open the database file, and you can see a list of tables. If your table is listed, it means it has been created.









