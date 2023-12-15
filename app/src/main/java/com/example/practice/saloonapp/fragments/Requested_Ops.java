package com.example.practice.saloonapp.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.practice.R;
import com.example.practice.saloonapp.SaloonOPS;
import com.example.practice.saloonapp.fragments.andItsAdapters.AllOps_Adapter;
import com.example.practice.saloonapp.api.RetroFitAPI;
import com.example.practice.saloonapp.api.SaloonAPI;
import com.example.practice.saloonapp.fragments.andItsAdapters.RequestedOps_Adapter;
import com.example.practice.saloonapp.models.SaloonOPItem;
import com.example.practice.saloonapp.models.SaloonOPSModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Requested_Ops extends Fragment {

    public static final String TAG = "Requested_Ops";
    private RecyclerView requested_recyclerview;
    private RequestedOps_Adapter adopter;
    private ArrayList<SaloonOPItem> users;
    private Context context;
    private String currentDate;
    private String selectedDate;
    private SaloonAPI saloonAPI;
    private RetroFitAPI retroFitAPI;
    ProgressBar requested_progressBar;
    private int branchId;

    public Requested_Ops() {}

    public Requested_Ops(int branchId) {
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

        currentDate = year + "-" + (month + 1) + "-" + day;
        selectedDate = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_requested__ops, container, false);
        requested_recyclerview = view.findViewById(R.id.requested_recyclerview);
        requested_progressBar = view.findViewById(R.id.requested_progressBar);

        requested_recyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));
        requested_progressBar.setVisibility(View.VISIBLE);
// Initialize the adapter with an empty dataset
        users = new ArrayList<>();
        adopter = new RequestedOps_Adapter(context, users, new SaloonOPS());
        requested_recyclerview.setAdapter(adopter);

        // Load initial data
        loadRequestedOps(currentDate);
        return view;
    }

    public void loadRequestedOps(String date) {

        users = new ArrayList<>();
        String endpointUrl = "http://182.18.157.215/SaloonApp/API/api/Appointment/GetAppointment/1/" + branchId + "/null/" + date;

        retroFitAPI = new RetroFitAPI();
        saloonAPI = retroFitAPI.retrofitAPI(); // Replace with your actual base URL

        Call<SaloonOPSModel> call = saloonAPI.getData(endpointUrl);

        call.enqueue(new Callback<SaloonOPSModel>() {
            @Override
            public void onResponse(Call<SaloonOPSModel> call, Response<SaloonOPSModel> response) {

                requested_progressBar.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    List<SaloonOPItem> opItem = response.body().getOpListResult();

                    // Clear existing data
                    users.clear();

                    if (opItem != null && !opItem.isEmpty()) {
                        for (SaloonOPItem item : opItem) {
                            Log.d(TAG, "Status: "+item.getStatus());
                            if (item.getStatus().equals("Submited")) {
                                Log.d("TAG", "Requested_Ops: " + item.getCustomerName());
                                users.add(new SaloonOPItem(
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

                    } else {
                        // If no records are present, call the method in SaloonOPS
                        Log.d("TAG", "No ops");
                    }
                    adopter = new RequestedOps_Adapter(context, users, new SaloonOPS());
                    requested_recyclerview.setAdapter(adopter);
                }
            }

            @Override
            public void onFailure(Call<SaloonOPSModel> call, Throwable t) {
                Log.d("TAG", "onFailure: " + t.getMessage());
            }
        });
    }

    public void updateRecordsByDate_requestedOp(String selectedDate) {
        loadRequestBySelectedDate(selectedDate);
    }

    public void loadRequestBySelectedDate(String selectedDate) {
        requested_progressBar.setVisibility(View.VISIBLE);
        users = new ArrayList<>();
        String endpointUrl = "http://182.18.157.215/SaloonApp/API/api/Appointment/GetAppointment/1/" + branchId + "/null/" + selectedDate;

        retroFitAPI = new RetroFitAPI();
        saloonAPI = retroFitAPI.retrofitAPI(); // Replace with your actual base URL

        Call<SaloonOPSModel> call = saloonAPI.getData(endpointUrl);

        call.enqueue(new Callback<SaloonOPSModel>() {
            @Override
            public void onResponse(Call<SaloonOPSModel> call, Response<SaloonOPSModel> response) {

                requested_progressBar.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    List<SaloonOPItem> opItem = response.body().getOpListResult();

                    // Clear existing data
                    users.clear();

                    if (opItem != null && !opItem.isEmpty()) {
                        for (SaloonOPItem item : opItem) {
                            if (item.getStatus().equals("Submited")) {
                                users.add(new SaloonOPItem(
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
                            adopter = new RequestedOps_Adapter(context, users, new SaloonOPS());
                            requested_recyclerview.setAdapter(adopter);
                        }

                        adopter.updateData(users);

                    } else {
                        // If no records are present, call the method in SaloonOPS
                        users.clear();
                        adopter = new RequestedOps_Adapter(getContext(), users, new SaloonOPS());
                        requested_recyclerview.setAdapter(adopter);

                        ((SaloonOPS) getActivity()).displayNoRecordsText(true);
                    }
                }
            }

            @Override
            public void onFailure(Call<SaloonOPSModel> call, Throwable t) {
                Log.d("TAG", "onFailure: " + t.getMessage());
            }
        });
    }
    private void showLog(String text){
        Log.d(TAG, "showLog: "+text);
    }

}