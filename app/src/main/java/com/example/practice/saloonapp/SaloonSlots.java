package com.example.practice.saloonapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.emreesen.sntoast.SnToast;
import com.emreesen.sntoast.Type;
import com.example.practice.MainActivity;
import com.example.practice.R;
import com.example.practice.saloonapp.api.RetroFitAPI;
import com.example.practice.saloonapp.api.SaloonAPI;
import com.example.practice.saloonapp.models.SlotOPPost;
import com.example.practice.saloonapp.models.SlotsOPSModel;
import com.google.android.material.imageview.ShapeableImageView;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SaloonSlots extends AppCompatActivity {


    Button slot1, slot2, slot3, slot4, slot5, slot6, slot7, slot8, slot9, bookAppointment_btn;
    private TextView slot_purpose, slot_date, noSlots_tv, branchName, branchAddress;
    private EditText slot_userName, slot_userNumber, slot_userEmail;
    private GridLayout gridLayout;
    private Button selectedButton;
    RadioGroup slot_gender;
    private ShapeableImageView branchImage;
    LinearLayout purpose_box, slot_datePicker, slotDesign;
    ArrayList<String> onPurpose = new ArrayList<>();
    boolean[] checkPurpose;
    private final String TAG = "SlotTAG";
    String[] purposes = {"Head Wash", "New Patch", "Hair Style", "Massage", "Services", "Others"};
    Calendar now, minDate, maxDate;
    int year, month, day, dayInMonth, currentDayInYear, bId, GenderTypeId;
    Calendar[] result;
    private String bName, bAddress, bImage, slotUserName, slotUserNumber, slotUserEmail,
            gender, DISPLAY_DATE, CURRENT_DATE, SELECTED_DATE, SLOT_TIME, SLOT_PURPOSE;
    RetroFitAPI retroFitAPI;
    ImageView homeIcon;
    private Handler handler = new Handler();
    private boolean shouldRepeat = false; // Flag to control repetition
    private ArrayList<String> slots;

    private SaloonActivity saloonActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saloon_slots);

        slot_purpose = findViewById(R.id.slot_purpose);
        purpose_box = findViewById(R.id.purpose_box);
        slot_datePicker = findViewById(R.id.slot_datePicker);
        slot_date = findViewById(R.id.slot_date);
        gridLayout = findViewById(R.id.gridLayout);
        slot1 = findViewById(R.id.slot1);
        slot2 = findViewById(R.id.slot2);
        slot3 = findViewById(R.id.slot3);
        slot4 = findViewById(R.id.slot4);
        slot5 = findViewById(R.id.slot5);
        slot6 = findViewById(R.id.slot6);
        slot7 = findViewById(R.id.slot7);
        slot8 = findViewById(R.id.slot8);
        slot9 = findViewById(R.id.slot9);
        slot_userName = findViewById(R.id.slot_userName);
        slot_userNumber = findViewById(R.id.slot_userNumber);
        slot_userEmail = findViewById(R.id.slot_userEmail);
        slot_gender = findViewById(R.id.slot_gender);
        noSlots_tv = findViewById(R.id.noSlots_tv);
        slotDesign = findViewById(R.id.slotDesign);
        branchName = findViewById(R.id.branchName);
        branchAddress = findViewById(R.id.branchAddress);
        branchImage = findViewById(R.id.branchImage);
        bookAppointment_btn = findViewById(R.id.bookAppointment_btn);
        homeIcon = findViewById(R.id.homeIcon);
        saloonActivity = new SaloonActivity();
        retroFitAPI = new RetroFitAPI();

        Intent branchData = getIntent();

        if (branchData != null) {
            bId = branchData.getIntExtra("branchId", 0);
            bName = branchData.getStringExtra("branchName");
            bAddress = branchData.getStringExtra("branchAddress");
            bImage = branchData.getStringExtra("branchImage");

            branchName.setText(bName);
            branchAddress.setText(bAddress);
            Glide.with(this).load(bImage).into(branchImage);
        }

        now = Calendar.getInstance();
        year = now.get(Calendar.YEAR);
        month = now.get(Calendar.MONTH);
        day = now.get(Calendar.DAY_OF_WEEK);
        currentDayInYear = now.get(Calendar.DAY_OF_YEAR);
        dayInMonth = now.get(Calendar.DAY_OF_MONTH);

        CURRENT_DATE = year + "-" + (month + 1) + "-" + dayInMonth;
        DISPLAY_DATE = dayInMonth + "-" + (month + 1) + "-" + year;

        //  get slots data
        getSlotsData(CURRENT_DATE);

        getDayFromSelectedDate(DISPLAY_DATE);

        slot_date.setText(DISPLAY_DATE);

        checkPurpose = new boolean[purposes.length];
        purpose_box.setOnClickListener(view -> purposeSelector());
        slot_datePicker.setOnClickListener(view -> displayDatePicker());

        homeIcon.setOnClickListener(view -> {
            getSlotsData(CURRENT_DATE);
        });
        bookAppointment_btn.setOnClickListener(view -> postAppointment());

        selectedSlotTimeNdChangeColor("null");
    }
    /* Post Info */
    /*{
        "Id": null,
        "BranchId": 1,
        "Date": "2023-12-4",
        "SlotTime":13,
        "CustomerName":"Test",
        "PhoneNumber": "9999999999",
        "Email": "test.test@mail.com",
        "GenderTypeId": 1,
        "StatusTypeId": 6,
        "PurposeOfVisitId": 7,
        "PurposeOfVisit": "PurposeOfVisit",
        "IsActive": true,
        "CreatedDate": "2023-11-28",
        "UpdatedDate": "2023-11-28",
        "UpdatedByUserId": null
}
SlotTime : 9 - 17 (9, 10, 11, 12, 13, 14, 15, 16, 17)
GenderTypeId : 1 - 3 (female, male, others)
StatusTypeId : 4 - 6 (requested, accepted, rejected)
PurposeOfVisitId : 7 - 10 (service, head wash, others, new patch)
*/

    private void postAppointment() {
        if (!slotUserInfoValidation()) {
            return;
        }
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://182.18.157.215/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        SaloonAPI call = retrofit.create(SaloonAPI.class);
        Call<SlotOPPost> postAppointment = call.postOP(new SlotOPPost(
                null,
                bId,
                SELECTED_DATE,
                SLOT_TIME,
                slotUserName,
                slotUserNumber,
                slotUserEmail,
                GenderTypeId,
                4,
                7,
                SLOT_PURPOSE,
                true,
                CURRENT_DATE,
                CURRENT_DATE,
                null
        ));

        postAppointment.enqueue(new Callback<SlotOPPost>() {
            @Override
            public void onResponse(Call<SlotOPPost> call, Response<SlotOPPost> response) {
                if (response.isSuccessful()) {
                    saloonActivity.successToast("Your slot is booked successfully.", SaloonSlots.this);
                    makeNotification();
                    finish();
                } else {
                    saloonActivity.errorToast("response is unsuccessful.", SaloonSlots.this);
                }
            }

            @Override
            public void onFailure(Call<SlotOPPost> call, Throwable t) {
                saloonActivity.errorToast("Something went wrong.", SaloonSlots.this);
            }
        });
    }

    private void makeNotification() {
        String CHANNEL_ID = "saloon_notification_channel";

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID);
        builder.setSmallIcon(R.drawable.saloon_app_logo)
                .setAutoCancel(true)
                .setContentTitle("Hair Saloon")
                .setContentText("Your slot is book successfully.")
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


    private void getSlotsData(String date) {
        slots = new ArrayList<>();
        String SLOTS_URL = "http://182.18.157.215/SaloonApp/API/api/Appointment/GetSlotsByDateAndBranch/" + date + "/" + bId;
        SaloonAPI slotApi = retroFitAPI.retrofitAPI();
        Call<SlotsOPSModel> slotCall = slotApi.getSlotsData(SLOTS_URL);
        slotCall.enqueue(new Callback<SlotsOPSModel>() {
            @Override
            public void onResponse(Call<SlotsOPSModel> call, Response<SlotsOPSModel> response) {

                if (response.isSuccessful()) {

                    if (response.body() != null) {

                        SlotsOPSModel slotsOPSModel = response.body();

                        // Enable all buttons by default
                        for (int i = 0; i < gridLayout.getChildCount(); i++) {
                            View child = gridLayout.getChildAt(i);
                            if (child instanceof Button) {
                                Button button = (Button) child;
                                button.setEnabled(true);
                                button.setBackgroundTintList(getColorStateList(R.color.white));
                            }
                        }

                        for (SlotsOPSModel.SlotsListItems item : slotsOPSModel.slotsListResult) {
                            String slot_time = item.getSlotTimeSpan();
                            int availableSlots = item.getAvailableSlots();

                            for (int i = 0; i < gridLayout.getChildCount(); i++) {
                                View child = gridLayout.getChildAt(i);
                                if (child instanceof Button) {
                                    Button button = (Button) child;
                                    String buttonText = button.getText().toString().trim();

                                    if (slot_time.equals(buttonText)) {
                                        if (availableSlots > 0) {
                                            button.setEnabled(true);  // Enable the button
                                            button.setBackgroundTintList(getColorStateList(R.color.white));
                                        } else {
                                            button.setEnabled(false);  // Disable the button
                                            button.setBackgroundTintList(getColorStateList(R.color.grey));
                                        }
                                        slots.add(buttonText);
                                    }
                                }
                            }
                        }
                    } else {
                        saloonActivity.errorToast("response is empty.", SaloonSlots.this);
                    }
                } else {
                    saloonActivity.errorToast("response is unsuccessful.", SaloonSlots.this);
                }
            }

            @Override
            public void onFailure(Call<SlotsOPSModel> call, Throwable t) {
                saloonActivity.errorToast(t.getCause().toString(), SaloonSlots.this);
            }
        });
    }

    private void selectedSlotTimeNdChangeColor(String slotTimeSpan) {
        for (int i = 0; i < gridLayout.getChildCount(); i++) {
            View child = gridLayout.getChildAt(i);
            if (child instanceof Button) {
                final Button button = (Button) child;
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // Reset colors for all buttons
                        for (int j = 0; j < gridLayout.getChildCount(); j++) {
                            View innerChild = gridLayout.getChildAt(j);
                            if (innerChild instanceof Button) {
                                if (innerChild.isEnabled()) {

                                    for (String btn : slots) {
                                        if (!btn.equals(((Button) innerChild).getText())) {
                                            ((Button) innerChild).setBackgroundTintList(getColorStateList(R.color.white));
                                        }
                                    }
                                }
                            }
                        }
                        String buttonText = button.getText().toString().trim();

                        // Change color for the selected button
                        if (button.isEnabled()) {

                            for (String btn : slots) {
                                if (!btn.equals(buttonText)) {
                                    button.setBackgroundTintList(getColorStateList(R.color.green));
                                }
                            }
                        }

                        // Get the selected button's text
                        String btnText = button.getText().toString();
                        SLOT_TIME = btnText.substring(0, 2).trim();
                        int slotTime = Integer.parseInt(SLOT_TIME);
                        if (0 < slotTime && slotTime < 6) {
                            SLOT_TIME = String.valueOf(slotTime + 12);
                        }
                    }
                });
            }
        }
    }

    private void followTimeAndDisableSlots(int dayOfWeek, int dayOfYear) {

        handler.removeCallbacksAndMessages(null); // Remove any existing callbacks
        handler.post(new Runnable() {
            @Override
            public void run() {

                LocalTime localTime = null;
                int hr = 0;
                int min = 0;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

                    localTime = LocalTime.now();
                    hr = localTime.getHour();
                    min = localTime.getMinute();
                }

                if (dayOfWeek == Calendar.TUESDAY) {
                    noSlots_tv.setText("Today is a holiday");
                    noSlots_tv.setVisibility(View.VISIBLE);
                    bookAppointment_btn.setEnabled(false);
                } else {
                    noSlots_tv.setVisibility(View.GONE);
                    bookAppointment_btn.setEnabled(true);
                }

                int time = hr * 100 + min;


                if (currentDayInYear == dayOfYear) {

//                    showLog("currentDayInYear: " + currentDayInYear + " , " + "dayOfYear: " + dayOfYear);
                    // Disable slots based on time ranges
                    if (915 <= time && time <= 1014) {
                        runOnUiThread(() -> gridLayout.removeView(slot1));
                    }
                    if (1015 <= time && time <= 1114) {
                        runOnUiThread(() -> {
                            gridLayout.removeView(slot1);
                            gridLayout.removeView(slot2);
                        });
                    }
                    if (1115 <= time && time <= 1214) {
                        runOnUiThread(() -> {
                            gridLayout.removeView(slot1);
                            gridLayout.removeView(slot2);
                            gridLayout.removeView(slot3);
                        });
                    }
                    if (1215 <= time && time <= 1344) {
                        runOnUiThread(() -> {
                            gridLayout.removeView(slot1);
                            gridLayout.removeView(slot2);
                            gridLayout.removeView(slot3);
                            gridLayout.removeView(slot4);
                        });
                    }
                    if (1345 <= time && time <= 1444) {
                        runOnUiThread(() -> {
                            gridLayout.removeView(slot1);
                            gridLayout.removeView(slot2);
                            gridLayout.removeView(slot3);
                            gridLayout.removeView(slot4);
                            gridLayout.removeView(slot5);
                        });
                    }
                    if (1445 <= time && time <= 1544) {
                        runOnUiThread(() -> {
                            gridLayout.removeView(slot1);
                            gridLayout.removeView(slot2);
                            gridLayout.removeView(slot3);
                            gridLayout.removeView(slot4);
                            gridLayout.removeView(slot5);
                            gridLayout.removeView(slot6);
                        });
                    }
                    if (1545 <= time && time <= 1644) {
                        runOnUiThread(() -> {
                            gridLayout.removeView(slot1);
                            gridLayout.removeView(slot2);
                            gridLayout.removeView(slot3);
                            gridLayout.removeView(slot4);
                            gridLayout.removeView(slot5);
                            gridLayout.removeView(slot6);
                            gridLayout.removeView(slot7);
                        });
                    }
                    if (1645 <= time && time <= 1744) {
                        runOnUiThread(() -> {
                            gridLayout.removeView(slot1);
                            gridLayout.removeView(slot2);
                            gridLayout.removeView(slot3);
                            gridLayout.removeView(slot4);
                            gridLayout.removeView(slot5);
                            gridLayout.removeView(slot6);
                            gridLayout.removeView(slot7);
                            gridLayout.removeView(slot8);
                        });
                    }
                    if (1745 <= time) {
                        runOnUiThread(() -> {

                            gridLayout.removeView(slot1);
                            gridLayout.removeView(slot2);
                            gridLayout.removeView(slot3);
                            gridLayout.removeView(slot4);
                            gridLayout.removeView(slot5);
                            gridLayout.removeView(slot6);
                            gridLayout.removeView(slot7);
                            gridLayout.removeView(slot8);
                            gridLayout.removeView(slot9);

//                        slotDesign.setVisibility(View.GONE);
                            noSlots_tv.setText("No Slots Available");
                            noSlots_tv.setVisibility(View.VISIBLE);
                        });


                        shouldRepeat = true; // Set to true to repeat this block
                    }

                } else {
                    // Your code inside the else block...

                    runOnUiThread(() -> {

                        gridLayout.removeView(slot1);
                        gridLayout.removeView(slot2);
                        gridLayout.removeView(slot3);
                        gridLayout.removeView(slot4);
                        gridLayout.removeView(slot5);
                        gridLayout.removeView(slot6);
                        gridLayout.removeView(slot7);
                        gridLayout.removeView(slot8);
                        gridLayout.removeView(slot9);

                        gridLayout.addView(slot1);
                        gridLayout.addView(slot2);
                        gridLayout.addView(slot3);
                        gridLayout.addView(slot4);
                        gridLayout.addView(slot5);
                        gridLayout.addView(slot6);
                        gridLayout.addView(slot7);
                        gridLayout.addView(slot8);
                        gridLayout.addView(slot9);
                    });

                    shouldRepeat = false; // Set to false to stop repetition
                }

                // Conditionally repeat this block based on shouldRepeat
                if (shouldRepeat) {
                    handler.postDelayed(this, 1000 * 60);
                }
            }
        });
    }

    private void getDayFromSelectedDate(String selectedDate) {

        SimpleDateFormat format = new SimpleDateFormat("d-M-yyyy", Locale.ENGLISH);

        try {
            Date date = format.parse(selectedDate);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);

            int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
            int dayOfYear = calendar.get(Calendar.DAY_OF_YEAR);

            followTimeAndDisableSlots(dayOfWeek, dayOfYear);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void displayDatePicker() {
        minDate = Calendar.getInstance();
        maxDate = Calendar.getInstance();

        minDate.set(now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH));
        maxDate.set(now.get(Calendar.YEAR), now.get(Calendar.MONTH) + 6, now.get(Calendar.DAY_OF_MONTH));

        DatePickerDialog dpd = DatePickerDialog.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                SELECTED_DATE = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                String displayDate = dayOfMonth + "-" + (monthOfYear + 1) + "-" + year;

                getSlotsData(SELECTED_DATE);
                getDayFromSelectedDate(displayDate);
                slot_date.setText(displayDate);
            }
        }, year, month, day);

        dpd.setMinDate(minDate);
        dpd.setMaxDate(maxDate);

        Calendar[] disabledDays = getNumberOfDaysInBetween(minDate, maxDate);

        dpd.setDisabledDays(disabledDays);

        dpd.show(getSupportFragmentManager(), "slotDatePicker");
    }

    private Calendar[] getNumberOfDaysInBetween(Calendar minDate, Calendar maxDate) {

        if (minDate.get(Calendar.YEAR) == maxDate.get(Calendar.YEAR)) {
            result = sameYear(minDate, maxDate);
        } else {
            result = differentYear(minDate, maxDate);
        }

        return result;
    }

    private Calendar[] differentYear(Calendar minDate, Calendar maxDate) {

        int daysInMinDate = minDate.get(Calendar.DAY_OF_YEAR); // Days in the starting year
        int daysInMaxDate = maxDate.get(Calendar.DAY_OF_YEAR); // Days in the ending year

        // Subtract days of the year from 365 to get the remaining days in the starting year
        int remainingDaysInMinDate = 365 - daysInMinDate;

        // Total number of days between the two dates
        int numDays = remainingDaysInMinDate + daysInMaxDate;

        Calendar[] disabledDays = new Calendar[numDays];
        int disabledIndex = 0;

        for (int i = 0; i < numDays; i++) {
            Calendar date = (Calendar) minDate.clone();
            date.add(Calendar.DAY_OF_YEAR, i);

            // Check if the date is a Sunday (Calendar.SUNDAY = 1)
            if (date.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY) {
                disabledDays[disabledIndex++] = date;
            }
        }

        // Resize the array to the actual number of disabled days
        Calendar[] result = new Calendar[disabledIndex];
        System.arraycopy(disabledDays, 0, result, 0, disabledIndex);
        return result;
    }

    private Calendar[] sameYear(Calendar minDate, Calendar maxDate) {
        showLog("sameYear");
        int numDays = 1 + maxDate.get(Calendar.DAY_OF_YEAR) - minDate.get(Calendar.DAY_OF_YEAR);
        int firstDayOfWeek = minDate.getFirstDayOfWeek();
        Calendar[] disabledDays = new Calendar[numDays];
        int disabledIndex = 0;

        for (int i = 0; i < numDays; i++) {
            Calendar date = (Calendar) minDate.clone();
            date.add(Calendar.DAY_OF_YEAR, i);

            // Check if the date is a TUESDAY (Calendar.TUESDAY = 1)
            if (date.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY) {
                disabledDays[disabledIndex++] = date;
            }
        }

        // Resize the array to the actual number of disabled days
        Calendar[] result = new Calendar[disabledIndex];
        System.arraycopy(disabledDays, 0, result, 0, disabledIndex);
        return result;
    }

    private void purposeSelector() {
        showLog("purposeSelector");
        AlertDialog.Builder builder = new AlertDialog.Builder(SaloonSlots.this);
        builder.setTitle("Select Purpose of visit")
                .setCancelable(false)
                .setMultiChoiceItems(purposes, checkPurpose, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int position, boolean isChecked) {
                        if (isChecked) {
                            onPurpose.add(purposes[position]);
                        } else {
                            onPurpose.remove(purposes[position]);
                        }
                        Collections.sort(onPurpose);
                    }
                })
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int position) {

                        StringBuilder strBuilder = new StringBuilder();

                        for (int i = 0; i < onPurpose.size(); i++) {
                            strBuilder.append(onPurpose.get(i));


                            if (i != onPurpose.size() - 1) {
                                strBuilder.append(", ");
                            }
                        }
                        SLOT_PURPOSE = strBuilder.toString();
                        slot_purpose.setText(strBuilder.toString());
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .setNeutralButton("Clear", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int position) {
                        for (int i = 0; i < checkPurpose.length; i++) {
                            checkPurpose[i] = false;

                            onPurpose.clear();
                            slot_purpose.setText("");
                        }
                    }
                });
        builder.show();
    }

    private boolean slotUserInfoValidation() {
        showLog("slotUserInfoValidation");
        if (TextUtils.isEmpty(SLOT_TIME)) {
            saloonActivity.warningToast("Please select your slot time.", SaloonSlots.this);
            return false;
        }

        if (TextUtils.isEmpty(slot_userName.getText())) {
            saloonActivity.warningToast("User name is required.", SaloonSlots.this);
            return false;
        } else {
            slotUserName = slot_userName.getText().toString().trim();
        }
        if (TextUtils.isEmpty(slot_userNumber.getText())) {
            saloonActivity.warningToast("User number is required.", SaloonSlots.this);
            return false;
        } else if (slot_userNumber.getText().length() != 10) {
            saloonActivity.warningToast("Phone number must have 10 digits.", SaloonSlots.this);
            return false;
        } else {
            slotUserNumber = slot_userNumber.getText().toString().trim();
        }

        if (TextUtils.isEmpty(slot_userEmail.getText())) {
            saloonActivity.warningToast("User email is required.", SaloonSlots.this);
            return false;
        } else if (!isValidEmail(String.valueOf(slot_userEmail.getText()))) {
            saloonActivity.warningToast("Invalid email.", SaloonSlots.this);
            return false;
        } else {
            slotUserEmail = slot_userEmail.getText().toString().trim();
        }

        int selectedRadioButtonId = slot_gender.getCheckedRadioButtonId();

        if (selectedRadioButtonId != -1) {
            RadioButton selectedRadioButton = findViewById(selectedRadioButtonId);
            // Get the text of the selected radio button
            gender = selectedRadioButton.getText().toString();
            if (gender.equals("Female")) {
                GenderTypeId = 1;
            } else if (gender.equals("Male")) {
                GenderTypeId = 2;
            } else {
                GenderTypeId = 3;
            }
        } else {
            saloonActivity.warningToast("Please select your identity", SaloonSlots.this);
            return false;
        }
        if (TextUtils.isEmpty(SLOT_PURPOSE)) {
            saloonActivity.warningToast("Please select your purpose of visit.", SaloonSlots.this);
            return false;
        }
        return true;
    }
    public static boolean isValidEmail(String email) {
        String emailRegex_pattern = "^[a-z]+[a-z0-9]*@[a-z]{2,}\\.[a-z]{2,3}$";
        boolean isValidMail = email.matches(emailRegex_pattern);
        return isValidMail;
    }


    private void showLog(String message) {
        Log.d(TAG, "showLog: " + message);
    }
}