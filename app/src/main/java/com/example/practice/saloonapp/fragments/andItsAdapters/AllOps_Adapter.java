package com.example.practice.saloonapp.fragments.andItsAdapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.practice.R;
import com.example.practice.saloonapp.SaloonActivity;
import com.example.practice.saloonapp.SaloonOPS;
import com.example.practice.saloonapp.models.SaloonOPItem;

import java.util.ArrayList;
import java.util.List;

public class AllOps_Adapter extends RecyclerView.Adapter<AllOps_Adapter.ViewChild> {

    public static final String TAG = "AllOps_Adapter";
    private Context context;
    private ArrayList<SaloonOPItem> users;
    private SaloonOPS saloonOPS;
    private SaloonActivity saloonActivity;

    public AllOps_Adapter(Context context, ArrayList<SaloonOPItem> users, SaloonOPS saloonOPS) {
        this.context = context;
        this.users = users;
//        this.saloonOPS = new SaloonOPS();
        this.saloonOPS = saloonOPS;
        this.saloonActivity = new SaloonActivity();
    }

    @NonNull
    @Override
    public ViewChild onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.saloon_all_ops_design, parent, false);
        return new ViewChild(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewChild holder, int position) {

        SaloonOPItem slot = users.get(position);

        Log.d(TAG, "status: "+slot.getStatus());

        holder.op_userName.setText(slot.getCustomerName());
        holder.op_userEmail.setText(slot.getEmail());
        holder.op_userGender.setText(slot.getGender());
        holder.op_userPurpose.setText(slot.getPurposeOfVisit());
        holder.op_userTime.setText(slot.getSlotDuration());
        holder.op_userPhone.setText(slot.getPhoneNumber());

        if (slot.getStatus().equals("Submited")){

            // show buttons default
        }else{

            holder.acceptBtn.setVisibility(View.GONE);
            holder.rejectBtn.setVisibility(View.GONE);

            if (slot.getStatus().equals("Accepted")){
                holder.accepted_slot.setVisibility(View.VISIBLE);
            }else {
                holder.rejected_slot.setVisibility(View.VISIBLE);
            }
        }
        holder.acceptBtn.setOnClickListener(view -> clickedOnAcceptedButton(holder));
        holder.rejectBtn.setOnClickListener(view -> clickedOnRejectedButton(holder));
    }

    private void clickedOnAcceptedButton(ViewChild holder) {
        holder.acceptBtn.setVisibility(View.GONE);
        holder.rejectBtn.setVisibility(View.GONE);

        holder.accepted_slot.setVisibility(View.VISIBLE);
    }

    private void clickedOnRejectedButton(ViewChild holder) {
        holder.acceptBtn.setVisibility(View.GONE);
        holder.rejectBtn.setVisibility(View.GONE);

        holder.rejected_slot.setVisibility(View.VISIBLE);
    }


    public void updateData(List<SaloonOPItem> newData) {
        users.clear();
        users.addAll(newData);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (users.size() == 0) {
            saloonOPS.displayNoRecordsText(true);
//            Log.d(TAG, "onBindViewHolder: empty");
        } else {
            saloonOPS.displayNoRecordsText(false);
//            Log.d(TAG, "onBindViewHolder: present");
        }
        return users.size();
    }


    class ViewChild extends RecyclerView.ViewHolder{

        private TextView op_userName, op_userGender, op_userPurpose, op_userEmail, op_userTime, op_userPhone;
        private LinearLayout acceptBtn, rejectBtn, accepted_slot, rejected_slot;

        public ViewChild(@NonNull View itemView) {
            super(itemView);
            op_userName = itemView.findViewById(R.id.op_userName);
            op_userGender = itemView.findViewById(R.id.op_userGender);
            op_userPurpose = itemView.findViewById(R.id.op_userPurpose);
            op_userEmail = itemView.findViewById(R.id.op_userEmail);
            op_userTime = itemView.findViewById(R.id.op_userTime);
            op_userPhone = itemView.findViewById(R.id.op_userPhone);
            acceptBtn = itemView.findViewById(R.id.allOp_acceptBtn);
            rejectBtn = itemView.findViewById(R.id.allOp_rejectBtn);
            accepted_slot = itemView.findViewById(R.id.allOp_accepted_slot);
            rejected_slot = itemView.findViewById(R.id.allOp_rejected_slot);
        }
    }

}
