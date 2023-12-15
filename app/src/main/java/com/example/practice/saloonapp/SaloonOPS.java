package com.example.practice.saloonapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.practice.R;
import com.example.practice.saloonapp.api.RetroFitAPI;
import com.example.practice.saloonapp.fragments.Accepted_Ops;
import com.example.practice.saloonapp.fragments.All_Ops;
import com.example.practice.saloonapp.fragments.Rejected_Ops;
import com.example.practice.saloonapp.fragments.Requested_Ops;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;

public class SaloonOPS extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    public static final String TAG = "SaloonOPS";
    ImageView back_arrow, agent_logOut;
    LinearLayout op_datePicker;
    ShapeableImageView opBranchImage;
    TabLayout tabLayout;
    TextView op_date;
    TextView op_branchName;
    static TextView no_ops;
    All_Ops allOps;
    Requested_Ops requestedOps;
    Accepted_Ops accepted_ops;
    Rejected_Ops rejected_ops;
    FrameLayout frameLayout_id;
    RetroFitAPI retroFitAPI;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    GoogleSignInClient signInClient;
    GoogleSignInOptions signInOptions;
    GoogleSignInAccount account;
    private int branchId;
    private String branchName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saloon_ops);

        String branchImage = "http://182.18.157.215/SaloonApp/Saloon_Repo/FileRepository/Branch1.jpg";

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        tabLayout = findViewById(R.id.tabLayout);
        back_arrow = findViewById(R.id.back_arrow);
        opBranchImage = findViewById(R.id.opBranchImage);
        op_datePicker = findViewById(R.id.datePicker_id);
        op_date = findViewById(R.id.op_date);
        op_branchName = findViewById(R.id.op_branchName);
        no_ops = findViewById(R.id.no_ops);
        frameLayout_id = findViewById(R.id.frameLayout_id);
        agent_logOut = findViewById(R.id.agent_logOut);
        allOps = new All_Ops();
        requestedOps = new Requested_Ops();
        accepted_ops = new Accepted_Ops();
        rejected_ops = new Rejected_Ops();

        retroFitAPI = new RetroFitAPI();


        signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        signInClient = GoogleSignIn.getClient(this, signInOptions);


        account = GoogleSignIn.getLastSignedInAccount(this);

        op_date.setText(currentDate());

        Intent intent = getIntent();
        if (intent != null) {

            if (intent.hasExtra("branchId")) {
                branchId = intent.getIntExtra("branchId", 1);
            }
            if (intent.hasExtra("branchName")) {
                branchName = intent.getStringExtra("branchName");
            }
            if (intent.hasExtra("branchImage")) {
                branchImage = intent.getStringExtra("branchImage");
            }
            op_branchName.setText(branchName);

            Log.d(TAG, "branchId: " + branchId + " ,branchName:" + branchName);


            Glide.with(this)
                    .asBitmap()
                    .load(branchImage) // Load the image from the URL
                    .listener(new RequestListener<Bitmap>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                            // Blur the loaded bitmap
                            Bitmap blurredBitmap = SaloonActivity.blurBitmap(getApplicationContext(), resource, 1f); // Adjust blur radius as needed
                            opBranchImage.setImageBitmap(blurredBitmap); // Set the blurred bitmap to the opBranchImage ImageView
                            return true;
                        }
                    })
                    .submit();
        }

        back_arrow.setOnClickListener(view -> {
            finish();
//            startActivity(new Intent(this, SaloonActivity.class));
        });
        loadFragments(new All_Ops(branchId));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Fragment fragment;

                switch (tab.getPosition()) {
                    case 0:
                        fragment = new All_Ops(branchId);
                        break;
                    case 1:
                        fragment = new Requested_Ops(branchId);
                        break;
                    case 2:
                        fragment = new Accepted_Ops(branchId);
                        break;
                    default:
                        fragment = new Rejected_Ops(branchId);
                }
                loadFragments(fragment);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        op_datePicker.setOnClickListener(view -> {
            showDatePickerDialog();
        });
    }

    private String currentDate() {
        Calendar now = Calendar.getInstance();
        int year = now.get(Calendar.YEAR);
        int month = now.get(Calendar.MONTH);
        int day = now.get(Calendar.DAY_OF_MONTH);

        return day + "-" + (month + 1) + "-" + year;
    }

    private void loadFragments(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frameLayout_id, fragment, getTags(fragment))
                .commit();
    }

    private String getTags(Fragment fragment) {

        if (fragment instanceof All_Ops) {
            return "All_OpsTag";
        } else if (fragment instanceof Requested_Ops) {
            return "Requested_OpsTag";
        } else if (fragment instanceof Accepted_Ops) {
            return "Accepted_OpsTag";
        } else if (fragment instanceof Rejected_Ops) {
            return "Rejected_OpsTag";
        } else {
            return "All_OpsTag";
        }

    }

    private String tabNames(int position) {

        switch (position) {
            case 0:
                return "All";
            case 1:
                return "Requested";
            case 2:
                return "Accepted";
            default:
                return "Rejected";
        }
    }

    public void displayNoRecordsText(boolean status) {
        no_ops.setVisibility(status ? View.VISIBLE : View.GONE);
    }

    private void showDatePickerDialog() {

        Calendar now = Calendar.getInstance();

        DatePickerDialog dpd = DatePickerDialog.newInstance(
                SaloonOPS.this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.setCancelColor(Color.RED);
//        dpd.setMaxDate(now);
        dpd.show(getSupportFragmentManager(), "DatePickerDialog");
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String selectedDate = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
        String displayedDate = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;
        op_date.setText(displayedDate);

        Fragment allOpsFragment = getSupportFragmentManager().findFragmentByTag("All_OpsTag");
        Fragment requestedOpsFragment = getSupportFragmentManager().findFragmentByTag("Requested_OpsTag");
        Fragment acceptedOpsFragment = getSupportFragmentManager().findFragmentByTag("Accepted_OpsTag");
        Fragment rejectedOpsFragment = getSupportFragmentManager().findFragmentByTag("Rejected_OpsTag");


        if (allOpsFragment instanceof All_Ops) {
            ((All_Ops) allOpsFragment).updateRecordsByDate(selectedDate);
            Log.d("TAG11", "onDateSet: 1");
        }
        if (requestedOpsFragment instanceof Requested_Ops) {
            ((Requested_Ops) requestedOpsFragment).updateRecordsByDate_requestedOp(selectedDate);
            Log.d("TAG11", "onDateSet: 2");
        }
        if (acceptedOpsFragment instanceof Accepted_Ops) {
            ((Accepted_Ops) acceptedOpsFragment).updateRecordsByDate_acceptedOp(selectedDate);
            Log.d("TAG11", "onDateSet: 3");
        }

        if (rejectedOpsFragment instanceof Rejected_Ops) {
            ((Rejected_Ops) rejectedOpsFragment).updateRecordsByDate_rejectedOp(selectedDate);
            Log.d("TAG11", "onDateSet: 4");
        }
    }
}