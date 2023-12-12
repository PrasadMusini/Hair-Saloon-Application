package com.example.practice.saloonapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.practice.R;

import androidx.annotation.NonNull;

import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;

public class SplashScreen extends AppCompatActivity {

    private TextView animated_text;
    private String text = "Hello World!";
    private int currentIndex = 0;
    private boolean increasing = true;
    public static final String TAG = "TAG90";
    private InterstitialAd mInterstitialAd;
    private InterstitialAd mInterstitialAd2;
    ImageView adsBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        animated_text = findViewById(R.id.animated_text);
        adsBtn = findViewById(R.id.adsBtn);
        animateText();

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {}
        });

        loadAd();

        adsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadAd();
                showAd();
            }
        });


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loadAd();
                showAd();
                startActivity(new Intent(SplashScreen.this, SaloonActivity.class));
            }
        }, 3000);
    }

    private void loadAd() {

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {}
        });
        AdRequest adRequest = new AdRequest.Builder().build();

        InterstitialAd.load(this,"ca-app-pub-3940256099942544/1033173712", adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        mInterstitialAd = interstitialAd;
                        mInterstitialAd2 = interstitialAd;
                        Log.i(TAG, "onAdLoaded");


                        mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback(){
                            @Override
                            public void onAdClicked() {
                                // Called when a click is recorded for an ad.
                                Log.d(TAG, "Ad was clicked.");
                            }

                            @Override
                            public void onAdDismissedFullScreenContent() {
                                // Called when ad is dismissed.
                                // Set the ad reference to null so you don't show the ad a second time.
                                Log.d(TAG, "Ad dismissed fullscreen content.");
                                mInterstitialAd = null;

                                startActivity(new Intent(SplashScreen.this, SaloonActivity.class));
                            }

                            @Override
                            public void onAdFailedToShowFullScreenContent(AdError adError) {
                                // Called when ad fails to show.
                                Log.e(TAG, "Ad failed to show fullscreen content.");
                                mInterstitialAd = null;
                            }

                            @Override
                            public void onAdImpression() {
                                // Called when an impression is recorded for an ad.
                                Log.d(TAG, "Ad recorded an impression.");
                            }

                            @Override
                            public void onAdShowedFullScreenContent() {
                                // Called when ad is shown.
                                Log.d(TAG, "Ad showed fullscreen content.");
                            }
                        });


                    }

                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error
                        Log.d(TAG, loadAdError.toString());
                        mInterstitialAd = null;
                    }
                });
    }

    private void showAd() {

        if (mInterstitialAd != null) {
            mInterstitialAd.show(SplashScreen.this);
        } else {
            Log.d("TAG", "The interstitial ad wasn't ready yet.");
            mInterstitialAd = mInterstitialAd2;
        }

    }


    private void animateText() {

        Handler handler = new Handler();

        handler.post(new Runnable() {
            @Override
            public void run() {
                if (increasing) {
                    animated_text.setText(text.substring(0, currentIndex + 1));
                    currentIndex++;
                    if (currentIndex == text.length()) {
                        increasing = false;
                    }
                } else {
                    animated_text.setText(text.substring(0, currentIndex));
                    currentIndex--;
                    if (currentIndex == 0) {
                        increasing = true;
                    }
                }
                handler.postDelayed(this, 200);
            }
        });
    }
}

