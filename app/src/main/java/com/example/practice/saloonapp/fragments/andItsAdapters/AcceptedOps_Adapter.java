package com.example.practice.saloonapp.fragments.andItsAdapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.practice.R;
import com.example.practice.saloonapp.SaloonOPS;
import com.example.practice.saloonapp.models.SaloonOPItem;

import java.util.ArrayList;
import java.util.List;

public class AcceptedOps_Adapter extends RecyclerView.Adapter<AcceptedOps_Adapter.ViewChild> {


    public static final String TAG = "AcceptedOps_Adapter";
    private Context context;
    private ArrayList<SaloonOPItem> users;
    private SaloonOPS saloonOPS;

    public AcceptedOps_Adapter(Context context, ArrayList<SaloonOPItem> users, SaloonOPS saloonOPS) {
        this.context = context;
        this.users = users;
        this.saloonOPS = saloonOPS;
    }

    @NonNull
    @Override
    public ViewChild onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.saloon_accepted_ops_design, parent, false);
        return new AcceptedOps_Adapter.ViewChild(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewChild holder, int position) {

        SaloonOPItem user = users.get(position);
        holder.op_userName.setText(user.getCustomerName());
        holder.op_userEmail.setText(user.getEmail());
        holder.op_userGender.setText(user.getGender());
        holder.op_userPurpose.setText(user.getPurposeOfVisit());
        holder.op_userTime.setText(user.getSlotDuration());
        holder.op_userPhone.setText(user.getPhoneNumber());

    }
    public void updateData(List<SaloonOPItem> newData) {
        users.clear();
        users.addAll(newData);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (users.size() == 0) {
//            saloonOPS.displayNoRecordsText(true);
            saloonOPS.displayNoRecordsText(true);
            Log.d(TAG, "onBindViewHolder: empty");
        } else {
            Log.d(TAG, "onBindViewHolder: present");
//            saloonOPS.displayNoRecordsText(false);
            saloonOPS.displayNoRecordsText(false);
        }
        return users.size();
    }

    class ViewChild extends RecyclerView.ViewHolder{

        private TextView op_userName, op_userGender, op_userPurpose, op_userEmail, op_userTime, op_userPhone;

        public ViewChild(@NonNull View itemView) {
            super(itemView);
            op_userName = itemView.findViewById(R.id.op_userName);
            op_userGender = itemView.findViewById(R.id.op_userGender);
            op_userPurpose = itemView.findViewById(R.id.op_userPurpose);
            op_userEmail = itemView.findViewById(R.id.op_userEmail);
            op_userTime = itemView.findViewById(R.id.op_userTime);
            op_userPhone = itemView.findViewById(R.id.op_userPhone);
        }
    }
}