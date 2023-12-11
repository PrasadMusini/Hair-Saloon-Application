package com.example.practice.saloonapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.emreesen.sntoast.SnToast;
import com.emreesen.sntoast.Type;
import com.example.practice.MainActivity;
import com.example.practice.R;
import com.example.practice.saloonapp.api.RetroFitAPI;
import com.example.practice.saloonapp.api.SaloonAPI;
import com.example.practice.saloonapp.models.BannerData;
import com.example.practice.saloonapp.models.BranchData;
import com.example.practice.saloonapp.models.BranchList;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SaloonActivity extends AppCompatActivity {

    public static final String TAG = "SaloonActivity";
    RecyclerView recyclerView;
    SaloonAdapter adopter;
    private List<BranchList> branches;
    ConstraintLayout websiteLink_layout;
    RetroFitAPI retroFitAPI;
    ImageView home_icon;
    RelativeLayout agent_btn;
    private ProgressBar progressBar;
    private Context cxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saloon);
        recyclerView = findViewById(R.id.recycler_branches);
        home_icon = findViewById(R.id.home_icon);
        websiteLink_layout = findViewById(R.id.websiteLink_layout);
        agent_btn = findViewById(R.id.agent_btn);
        progressBar = findViewById(R.id.progressBar);
        retroFitAPI = new RetroFitAPI();
        cxt = getApplicationContext();

        home_icon.setOnClickListener(view -> {
            if (checkPermission()){
                makeNotification();
            }
        });

        agent_btn.setOnClickListener(view -> {
            startActivity(new Intent(this, Saloon_Agent_Login.class));
        });

        websiteLink_layout.setOnClickListener(view -> {
            String url = "https://www.youtube.com/";
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            startActivity(intent);
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        progressBar.setVisibility(View.VISIBLE);
        getBranchData();
        getBannersData();
    }


    private void makeNotification() {
        String CHANNEL_ID = "saloon_notification_channel";

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID);
        builder.setSmallIcon(R.drawable.saloon_app_logo)
                .setAutoCancel(true)
                .setContentTitle("This is title.")
                .setContentText("Sample text.")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                .putExtra("data", "intent Sample Data");

        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(),
                0,
                intent,
                PendingIntent.FLAG_MUTABLE);
        builder.setContentIntent(pendingIntent);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = notificationManager.getNotificationChannel(CHANNEL_ID);
            if (notificationChannel == null){
                notificationChannel = new NotificationChannel(CHANNEL_ID, "Test Description.", NotificationManager.IMPORTANCE_HIGH);
                notificationChannel.setLightColor(Color.GREEN);
                notificationChannel.enableVibration(true);
                notificationManager.createNotificationChannel(notificationChannel);
            }
        }

        notificationManager.notify(0, builder.build());
    }

    private void getBranchData() {

        branches = new ArrayList<>();

        SaloonAPI branchAPI = retroFitAPI.retrofitAPI();
        Call<BranchData> data = branchAPI.getBranchData();

        data.enqueue(new Callback<BranchData>() {
            @Override
            public void onResponse(Call<BranchData> call, Response<BranchData> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful() && response.body() != null) {
                    BranchData branchData = response.body();
                    List<BranchList> branchesData = branchData.getListResult();

                    if (branchesData != null) {

                        for (BranchList branch : branchesData) {
                            branches.add(new BranchList(
                                    branch.getId(),
                                    branch.getName(),
                                    branch.getFilePath(),
                                    branch.getAddress(),
                                    branch.getStartTime(),
                                    branch.getCloseTime(),
                                    branch.getRoom(),
                                    branch.getMobileNumber(),
                                    branch.getIsActive()
                            ));
                        }
                        adopter = new SaloonAdapter(branches, SaloonActivity.this, "Book Now");
                        recyclerView.setAdapter(adopter);
                    }
                } else {
                    Log.d(TAG, "Unsuccessful response or empty body");
                }
            }

            @Override
            public void onFailure(Call<BranchData> call, Throwable t) {
                errorToast(t.getMessage(), SaloonActivity.this);
            }
        });
    }
    private void getBannersData() {
        String bannersUrl = "http://182.18.157.215/SaloonApp/API/api/Banner/null";
        String filePathUrl = "http://182.18.157.215/SaloonApp/Saloon_Repo/";

        SaloonAPI branchAPI = retroFitAPI.retrofitAPI();
        Call<BannerData> dataCall = branchAPI.getBannerData();

        dataCall.enqueue(new Callback<BannerData>() {
            @Override
            public void onResponse(Call<BannerData> call, Response<BannerData> response) {
                if (response.isSuccessful()) {
                    List<SlideModel> imageList = new ArrayList<>(); // Create image list

                    BannerData bannerData = response.body();
                    ArrayList<BannerData.BannerList> bannerList = bannerData.getBannersList();

                    for (BannerData.BannerList banners : bannerList) {

                        imageList.add(new SlideModel(filePathUrl + banners.getFilePath(), ScaleTypes.FIT));

                    }
                    ImageSlider imageSlider = findViewById(R.id.image_slider);
                    imageSlider.setImageList(imageList);
                }
            }

            @Override
            public void onFailure(Call<BannerData> call, Throwable t) {
                errorToast(t.getMessage(), SaloonActivity.this);
            }
        });
    }
    public static Bitmap blurBitmap(Context context, Bitmap bitmap, float radius) {
        Bitmap blurredBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);

        RenderScript renderScript = RenderScript.create(context);
        Allocation input = Allocation.createFromBitmap(renderScript, bitmap);
        Allocation output = Allocation.createFromBitmap(renderScript, blurredBitmap);

        ScriptIntrinsicBlur script = ScriptIntrinsicBlur.create(renderScript, Element.U8_4(renderScript));
        script.setInput(input);
        script.setRadius(radius);
        script.forEach(output);

        output.copyTo(blurredBitmap);

        renderScript.destroy();
        return blurredBitmap;
    }
    public void successToast(String message, Context context) {
        new SnToast.Standard()
                .context(context)
                .type(Type.SUCCESS)
                .message(message)
                .iconSize(25)
                .textSize(14)
                .animation(true)
                .cancelable(false)
                .duration(3000)
                .build();
    }
    public void errorToast(String message, Context context) {
        new SnToast.Standard()
                .context(context)
                .type(Type.ERROR)
                .message(message)
                .iconSize(25)
                .textSize(14)
                .animation(true)
                .cancelable(false)
                .duration(3000)
                .build();
    }
    public void warningToast(String message, Context context) {
        new SnToast.Standard()
                .context(context)
                .type(Type.WARNING)
                .message(message)
                .iconSize(25)
                .textSize(14)
                .animation(true)
                .cancelable(true)
                .duration(3000)
                .build();
    }

    public boolean checkPermission(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED){
            requestNotifyPermission();
            return false;
        }else {
            return true;
        }
    }
    public void requestNotifyPermission(){
        ActivityCompat.requestPermissions(SaloonActivity.this, new String[]{Manifest.permission.POST_NOTIFICATIONS}, 101);
    }
}


