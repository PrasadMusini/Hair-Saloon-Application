package com.example.practice.saloonapp;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.emreesen.sntoast.SnToast;
import com.emreesen.sntoast.Type;
import com.example.practice.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Saloon_Agent_Login extends AppCompatActivity {

    public static final String TAG = "Saloon_Agent_Login";
    LinearLayout exit_btn, google_login_btn;
    GoogleSignInClient signInClient;
    GoogleSignInOptions signInOptions;
    FirebaseAuth firebaseAuth;
    Button agent_login_btn;
    EditText agent_userName, agent_password;
    GoogleSignInAccount account;
    ProgressBar login_progressBar;
    private SaloonActivity saloonActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saloon_agent_login);

        firebaseAuth = FirebaseAuth.getInstance();
        exit_btn = findViewById(R.id.exit_btn);
        google_login_btn = findViewById(R.id.google_login_btn);
        agent_login_btn = findViewById(R.id.agent_login_btn);
        agent_userName = findViewById(R.id.agent_userName);
        agent_password = findViewById(R.id.agent_password);
        login_progressBar = findViewById(R.id.login_progressBar);
        saloonActivity = new SaloonActivity();

        // Check if a user is already signed in using Firebase
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        GoogleSignInAccount googleUser = GoogleSignIn.getLastSignedInAccount(this);

        if (firebaseUser != null || googleUser != null) {
            openOpsActivity();
        }

        signInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        signInClient = GoogleSignIn.getClient(this, signInOptions);

        google_login_btn.setOnClickListener(view -> googleAuth());

        exit_btn.setOnClickListener(view -> finish());

        agent_login_btn.setOnClickListener(view -> {
            String agentEmail = agent_userName.getText().toString().trim();
            String agentPassword = agent_password.getText().toString().trim();

            if (TextUtils.isEmpty(agentEmail)) {
                saloonActivity.warningToast("Please enter the username.", Saloon_Agent_Login.this);
            } else if (TextUtils.isEmpty(agentPassword)) {
                saloonActivity.warningToast("Need to fill the password field.", Saloon_Agent_Login.this);
            } else {
                agentLogin(agentEmail, agentPassword);
            }
        });

        changePasswordIcon();
    }

    private void agentLogin(String email, String password) {

        agent_login_btn.setText("");
        login_progressBar.setVisibility(View.VISIBLE);
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    agent_login_btn.setText("Login");
                    login_progressBar.setVisibility(View.GONE);
                    if (task.isSuccessful()) {
                        signInAndLeave(email);
                    } else {
                        saloonActivity.errorToast("Authentication failed.", Saloon_Agent_Login.this);
                    }
                });
    }

    private void googleAuth() {
        Intent googleAuth = signInClient.getSignInIntent();
        googleAuthLauncher.launch(googleAuth);
    }

    ActivityResultLauncher<Intent> googleAuthLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(result.getData());
                    try {
                        task.getResult(ApiException.class);
                        getDetailFromGoogleSignInAccount();
                    } catch (ApiException e) {
                        saloonActivity.errorToast("Error Occurred", Saloon_Agent_Login.this);
                    }
                }else {
                    saloonActivity.errorToast("Something went wrong.", Saloon_Agent_Login.this);
                }
            });

    private void changePasswordIcon() {
        agent_password.setOnTouchListener(new View.OnTouchListener() {
            final int DRAWABLE_RIGHT = 2;
            boolean isShowPassword = true;

            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (agent_password.getRight() - agent_password.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        // Clicked on the drawableEnd (showPassword icon)
                        // Perform the action here (e.g., change the icon)

                        if (isShowPassword) {
                            agent_password.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.hide_password, 0);
                            agent_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        } else {
                            agent_password.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.show_password, 0);
                            agent_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_TEXT);
                        }

                        isShowPassword = !isShowPassword; // Toggle the password visibility state
                        return true;
                    }
                }
                return false;
            }
        });
    }

    private void getDetailFromGoogleSignInAccount() {
        account = GoogleSignIn.getLastSignedInAccount(this);

        if (account != null) {
            String email = account.getEmail();

            signInAndLeave(email);
        }
    }

    private void signInAndLeave(String signInMail) {

        Intent intent = new Intent(this, SaloonBranches.class);
        intent.putExtra("loginMail",signInMail);

        startActivity(intent);
        finish();
    }


    private void openOpsActivity() {
        startActivity(new Intent(Saloon_Agent_Login.this, SaloonBranches.class));
        finish();
    }
}