package com.example.practice.saloonapp;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.practice.saloonapp.fragments.Accepted_Ops;
import com.example.practice.saloonapp.fragments.All_Ops;
import com.example.practice.saloonapp.fragments.Rejected_Ops;
import com.example.practice.saloonapp.fragments.Requested_Ops;

public class SaloonOpFragAdapter extends FragmentStateAdapter {

    private String selectedDate;

    public SaloonOpFragAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        // Create a new instance of the fragment based on the position
        Fragment fragment;
        switch (position) {
            case 0:
                fragment = new All_Ops();
                break;
            case 1:
                fragment = new Requested_Ops();
                break;
            case 2:
                fragment = new Accepted_Ops();
                break;
            case 3:
                fragment = new Rejected_Ops();
                break;
            default:
                return new Fragment();
        }

        return fragment;
    }

    @Override
    public int getItemCount() {
        return 4;
    }

    public void setSelectedDate(String selectedDate) {
        this.selectedDate = selectedDate;
        notifyDataSetChanged();
    }
}

