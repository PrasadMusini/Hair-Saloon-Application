package com.example.practice.saloonapp.fragments;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.practice.R;
import com.example.practice.saloonapp.SaloonOPS;
import com.example.practice.saloonapp.fragments.andItsAdapters.AllOps_Adapter;
import com.example.practice.saloonapp.api.RetroFitAPI;
import com.example.practice.saloonapp.api.SaloonAPI;
import com.example.practice.saloonapp.fragments.andItsAdapters.RejectedOps_Adapter;
import com.example.practice.saloonapp.models.SaloonOPItem;
import com.example.practice.saloonapp.models.SaloonOPSModel;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class Rejected_Ops extends Fragment {

    private RejectedOps_Adapter adopter;
    private Context context;
    private RetroFitAPI retroFitAPI;
    private ArrayList<SaloonOPItem> user;
    private SaloonAPI saloonAPI;
    private String currentDate;
    private RecyclerView rejected_recyclerview;
    ProgressBar rejected_progressBar;

    View view;
    String BASEURL = "http://182.18.157.215/SaloonApp/API/api/";
    private String selectedDate;
    private int branchId;

    public Rejected_Ops() {}

    public Rejected_Ops(int branchId) {
        this.branchId = branchId;
    }

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

        view = inflater.inflate(R.layout.fragment_rejected__ops, container, false);
        rejected_recyclerview = view.findViewById(R.id.rejected_recyclerview);
        rejected_progressBar = view.findViewById(R.id.rejected_progressBar);
        rejected_recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));

        rejected_progressBar.setVisibility(View.VISIBLE);
        // Initialize the adapter with an empty dataset
        user = new ArrayList<>();
        adopter = new RejectedOps_Adapter(context, user, new SaloonOPS());
        rejected_recyclerview.setAdapter(adopter);

        // Load initial data
        loadDataByDate(currentDate);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public void loadDataByDate(String date) {
//        branchId
        Log.d("TAG11", "4: " + date);
        user = new ArrayList<>();
        String endpointUrl = "http://182.18.157.215/SaloonApp/API/api/Appointment/GetAppointment/1/" + branchId + "/null/" + date;

        retroFitAPI = new RetroFitAPI();
        saloonAPI = retroFitAPI.retrofitAPI(); // Replace with your actual base URL

        Call<SaloonOPSModel> call = saloonAPI.getData(endpointUrl);

        call.enqueue(new Callback<SaloonOPSModel>() {
            @Override
            public void onResponse(Call<SaloonOPSModel> call, Response<SaloonOPSModel> response) {

                rejected_progressBar.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    List<SaloonOPItem> opItem = response.body().getOpListResult();

                    // Clear existing data
                    user.clear();

                    if (opItem != null && !opItem.isEmpty()) {
                        for (SaloonOPItem item : opItem) {
                            if (item.getStatus().equals("Declined")) {
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
                        }

                        // Notify the adapter about the new data
//                        if (adopter != null) {
//                            adopter.updateData(user);
//                        }
                        adopter = new RejectedOps_Adapter(context, user, new SaloonOPS());
                        rejected_recyclerview.setAdapter(adopter);

                    } else {
                        // If no records are present, call the method in SaloonOPS
                        displayNoOps();
                        Log.d("TAG", "Rejected_Ops else 1");
                    }
                }
            }

            @Override
            public void onFailure(Call<SaloonOPSModel> call, Throwable t) {
                Log.d("TAG", "Rejected_Ops onFailure: " + t.getMessage());
            }
        });
    }

    private void displayNoOps() {
        ((SaloonOPS) getActivity()).displayNoRecordsText(true);
    }

    // This method is called when the selected date changes
    public void updateRecordsByDate_rejectedOp(String selectedDate) {
        Log.d("TAG11", "4: " + selectedDate);
        String endpointUrl = "http://182.18.157.215/SaloonApp/API/api/Appointment/GetAppointment/1/" + branchId + "/null/" + selectedDate;
        loadDataAgain(endpointUrl);
    }

    public void loadDataAgain(String endpointUrl) {

        rejected_progressBar.setVisibility(View.VISIBLE);
        user = new ArrayList<>();
        retroFitAPI = new RetroFitAPI();
        saloonAPI = retroFitAPI.retrofitAPI(); // Replace with your actual base URL

        Call<SaloonOPSModel> call = saloonAPI.getAllOps22(endpointUrl);

        call.enqueue(new Callback<SaloonOPSModel>() {
            @Override
            public void onResponse(Call<SaloonOPSModel> call, Response<SaloonOPSModel> response) {

                rejected_progressBar.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    List<SaloonOPItem> opItem = response.body().getOpListResult();

                    // Clear existing data
                    user.clear();

                    if (opItem != null && !opItem.isEmpty()) {
                        for (SaloonOPItem item : opItem) {
                            if (item.getStatus().equals("Declined")) {
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
                        }

                        if (adopter == null) {
                            adopter = new RejectedOps_Adapter(context, user, new SaloonOPS());
                            rejected_recyclerview.setAdapter(adopter);
                        }

                        adopter.updateData(user);

                    } else {
                        // If no records are present, call the method in SaloonOPS
                        user.clear();
                        adopter = new RejectedOps_Adapter(getContext(), user, new SaloonOPS());
                        rejected_recyclerview.setAdapter(adopter);

                        ((SaloonOPS) getActivity()).displayNoRecordsText(true);

                        Log.d("TAG", "Rejected_Ops else 2");
                    }
                }
            }

            @Override
            public void onFailure(Call<SaloonOPSModel> call, Throwable t) {
                Log.d("TAG", "Rejected_Ops onFailure: " + t.getMessage());
            }
        });
    }
}