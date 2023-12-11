package com.example.practice.saloonapp.fragments;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ProgressBar;

import com.example.practice.R;
import com.example.practice.saloonapp.SaloonOPS;
import com.example.practice.saloonapp.fragments.andItsAdapters.AllOps_Adapter;
import com.example.practice.saloonapp.api.RetroFitAPI;
import com.example.practice.saloonapp.api.SaloonAPI;
import com.example.practice.saloonapp.models.SaloonOPItem;
import com.example.practice.saloonapp.models.SaloonOPSModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class All_Ops extends Fragment {

    public static final String TAG = "All_Ops";
    RecyclerView all_recyclerview;
    AllOps_Adapter adopter;
    Context context;
    RetroFitAPI retroFitAPI;
    ArrayList<SaloonOPItem> user;
    SaloonAPI saloonAPI;
    String currentDate;
    private Dialog dialog;
    ProgressBar progressBar;
    View view;
    String BASEURL = "http://182.18.157.215/SaloonApp/API/api/";
    private String selectedDate;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity().getApplicationContext();

        Calendar now = Calendar.getInstance();
        int year = now.get(Calendar.YEAR);
        int month = now.get(Calendar.MONTH);
        int day = now.get(Calendar.DAY_OF_MONTH);

        selectedDate = null;

        currentDate = year + "-" + (month + 1) + "-" + day;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_all__ops, container, false);
        all_recyclerview = view.findViewById(R.id.all_recyclerview);
        progressBar = view.findViewById(R.id.progressBar);
        all_recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Initialize the adapter with an empty dataset
        user = new ArrayList<>();
        adopter = new AllOps_Adapter(context, user, new SaloonOPS());
        all_recyclerview.setAdapter(adopter);

        // Load initial data
        loadDataByDate(currentDate);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public void loadDataByDate(String date) {
        Log.d("TAG41", "loadDataByDate: " + date);
        user = new ArrayList<>();
        String endpointUrl = "http://182.18.157.215/SaloonApp/API/api/Appointment/GetAppointment/1/1/null/" + date;
        progressBar.setVisibility(View.VISIBLE);

        retroFitAPI = new RetroFitAPI();
        saloonAPI = retroFitAPI.retrofitAPI(); // Replace with your actual base URL

        Call<SaloonOPSModel> call = saloonAPI.getData(endpointUrl);

        call.enqueue(new Callback<SaloonOPSModel>() {
            @Override
            public void onResponse(Call<SaloonOPSModel> call, Response<SaloonOPSModel> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    List<SaloonOPItem> opItem = response.body().getOpListResult();

                    // Clear existing data
                    user.clear();

                    if (opItem != null && !opItem.isEmpty()) {
                        for (SaloonOPItem item : opItem) {
                            Log.d("TAG", "1: " + item.getCustomerName());
                            user.add(new SaloonOPItem(
                                    item.getId(),
                                    item.getBranchId(),
                                    item.getName(),
                                    item.getDate(),
                                    item.getSlotTime(),
                                    item.getCustomerName(),
                                    item.getPhoneNumber(),
                                    item.getEmail(),
                                    item.getGenderTypeId(),
                                    item.getGender(),
                                    item.getStatusTypeId(),
                                    item.getStatus(),
                                    item.getPurposeOfVisitId(),
                                    item.getPurposeOfVisit(),
                                    item.getIsActive(),
                                    item.getSlotDuration()));
                        }
                    } else {
                        // If no records are present, call the method in SaloonOPS
                        Log.d("TAG", "else 1");
                    }
                    adopter = new AllOps_Adapter(context, user, new SaloonOPS());
                    all_recyclerview.setAdapter(adopter);

                }
            }

            @Override
            public void onFailure(Call<SaloonOPSModel> call, Throwable t) {
                dialog.dismiss();
                Log.d("TAG", "onFailure: " + t.getMessage());
            }
        });
    }

    // This method is called when the selected date changes
    public void updateRecordsByDate(String selectedDate) {

        Log.d("TAG11", "1: " + selectedDate);
        String endpointUrl = "http://182.18.157.215/SaloonApp/API/api/Appointment/GetAppointment/1/1/null/" + selectedDate;
        loadDataAgain(endpointUrl);
    }

    public void loadDataAgain(String endpointUrl) {

        user = new ArrayList<>();
        retroFitAPI = new RetroFitAPI();
        saloonAPI = retroFitAPI.retrofitAPI(); // Replace with your actual base URL

        Call<SaloonOPSModel> call = saloonAPI.getAllOps22(endpointUrl);

        call.enqueue(new Callback<SaloonOPSModel>() {
            @Override
            public void onResponse(Call<SaloonOPSModel> call, Response<SaloonOPSModel> response) {
                if (response.isSuccessful()) {
                    List<SaloonOPItem> opItem = response.body().getOpListResult();

                    // Clear existing data
                    user.clear();

                    if (opItem != null && !opItem.isEmpty()) {
                        for (SaloonOPItem item : opItem) {
                            Log.d("TAG", "2: " + item.getCustomerName());
                            user.add(new SaloonOPItem(
                                    item.getId(),
                                    item.getBranchId(),
                                    item.getName(),
                                    item.getDate(),
                                    item.getSlotTime(),
                                    item.getCustomerName(),
                                    item.getPhoneNumber(),
                                    item.getEmail(),
                                    item.getGenderTypeId(),
                                    item.getGender(),
                                    item.getStatusTypeId(),
                                    item.getStatus(),
                                    item.getPurposeOfVisitId(),
                                    item.getPurposeOfVisit(),
                                    item.getIsActive(),
                                    item.getSlotDuration()));
                        }

                        if (adopter == null) {
                            adopter = new AllOps_Adapter(getContext(), user, new SaloonOPS());
                            all_recyclerview.setAdapter(adopter);
                        }

                        adopter.updateData(user);

                    } else {
                        // If no records are present, call the method in SaloonOPS
                        user.clear();
                        adopter = new AllOps_Adapter(getContext(), user, new SaloonOPS());
                        all_recyclerview.setAdapter(adopter);

                        ((SaloonOPS) getActivity()).displayNoRecordsText(true);

                        Log.d("TAG", "else 2");
                    }
                }
            }

            @Override
            public void onFailure(Call<SaloonOPSModel> call, Throwable t) {
                Log.d("TAG", "onFailure: " + t.getMessage());
            }
        });
    }

    private void customDialogForProgressBar() {
        dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_progress_bar);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    private void showLog(String text){
        Log.d(TAG, "showLog: "+text);
    }
}