package com.example.practice.saloonapp.api;

import com.example.practice.saloonapp.models.BannerData;
import com.example.practice.saloonapp.models.BranchData;
import com.example.practice.saloonapp.models.SaloonOPSModel;
import com.example.practice.saloonapp.models.SlotOPPost;
import com.example.practice.saloonapp.models.SlotStatus;
import com.example.practice.saloonapp.models.SlotsOPSModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Url;

public interface SaloonAPI {

    // https://hub.dummyapis.com/vj/stzUFUL

    @GET("Branch/null")
    Call<BranchData> getBranchData();

    @GET("Banner/null")
    Call<BannerData> getBannerData();

    @GET("Appointment/GetAppointment/1/1/null/2023-11-9")
    Call<SaloonOPSModel> getAllOps();

    @GET
    Call<SaloonOPSModel> getAllOps22(
            @Url String baseurl
    );

    @GET
    Call<SaloonOPSModel> getData(
            @Url String url
    );

    @GET
    Call<SlotsOPSModel> getSlotsData(
            @Url String url
    );
    @GET("SaloonApp/API/api/Appointment/GetSlotsByDateAndBranch/{date}/{branchId}")
    Call<SlotsOPSModel> getSlotsByDateAndBranch(
            @Path("date") String date,
            @Path("branchId") int branchId
    );
    @POST("Appointment/AddUpdateAppointment")
    Call<SlotOPPost> postAppointment(@Body SlotOPPost postOp);

    @POST("SaloonApp/API/api/Appointment/AddUpdateAppointment")
    Call<SlotOPPost> postOP(@Body SlotOPPost appointment);

    @POST("api/Appointment/GetApprovedDeclinedSlots")
    Call<SlotStatus> updateSlotStatus(@Body SlotStatus slotStatus);
}