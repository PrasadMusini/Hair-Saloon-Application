package com.example.practice.saloonapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.emreesen.sntoast.SnToast;
import com.emreesen.sntoast.Type;
import com.example.practice.R;
import com.example.practice.saloonapp.api.RetroFitAPI;
import com.example.practice.saloonapp.api.SaloonAPI;
import com.example.practice.saloonapp.models.BranchData;
import com.example.practice.saloonapp.models.BranchList;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SaloonBranches extends AppCompatActivity {

    public static final String TAG = "SaloonBranches";
    private List<BranchList> branches;
    RecyclerView branches_recyclerView;
    SaloonAdapter adopter;
    RetroFitAPI retroFitAPI;
    private ProgressBar progressBar;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    GoogleSignInClient signInClient;
    GoogleSignInOptions signInOptions;
    GoogleSignInAccount account;
    ImageView agentIconForLogout, branch_exitBtn;
    String loginMail;
    TextView welcomeText;
    private SaloonActivity saloonActivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saloon_branches);
        branches_recyclerView = findViewById(R.id.branches_recyclerView);
        progressBar = findViewById(R.id.branches_progressBar);
        agentIconForLogout = findViewById(R.id.agentIconForLogout);
        branch_exitBtn = findViewById(R.id.branch_exitBtn);
        welcomeText = findViewById(R.id.welcomeText);
        saloonActivity = new SaloonActivity();
        branches_recyclerView.setLayoutManager(new LinearLayoutManager(this));
        retroFitAPI = new RetroFitAPI();

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        Intent loginIntent = getIntent();
        if (loginIntent != null){
//            loginMail = loginIntent.getStringExtra("loginMail");
            Log.d(TAG, "onCreate: "+loginIntent.getStringExtra("loginMail"));
        }

        agentIconForLogout.setOnClickListener(view -> googleSignOut());

        welcomeText.setOnClickListener(view -> {
            saloonActivity.successToast("from saloon branches.", SaloonBranches.this);
        });

        signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        signInClient = GoogleSignIn.getClient(this, signInOptions);

        account = GoogleSignIn.getLastSignedInAccount(this);

        if (account != null) {
            loginMail = account.getEmail();
        }

        if (firebaseUser != null){
            loginMail = firebaseUser.getEmail();
        }

        branch_exitBtn.setOnClickListener(view -> finish());

        getBranchData();
    }

    private void googleSignOut() {
        if (firebaseUser != null){
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(SaloonBranches.this, Saloon_Agent_Login.class));
            finish();
        }else {
            signInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    startActivity(new Intent(SaloonBranches.this, Saloon_Agent_Login.class));
                    finish();
                }
            });
        }
    }

    private void getBranchData() {

        progressBar.setVisibility(View.VISIBLE);
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
                        adopter = new SaloonAdapter(branches, SaloonBranches.this, "Check Appointment", loginMail);
                        branches_recyclerView.setAdapter(adopter);
                    }
                } else {
                    Log.d("TAG", "Unsuccessful response or empty body");
                }
            }

            @Override
            public void onFailure(Call<BranchData> call, Throwable t) {
                Log.d("TAG", "onFailure");
            }
        });
    }
}


