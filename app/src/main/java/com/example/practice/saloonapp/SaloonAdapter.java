package com.example.practice.saloonapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.emreesen.sntoast.SnToast;
import com.emreesen.sntoast.Type;
import com.example.practice.R;
import com.example.practice.saloonapp.models.BranchList;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.List;

public class SaloonAdapter extends RecyclerView.Adapter<SaloonAdapter.ViewChild> {


    public static final String TAG = "SaloonAdapter";
    public static final String ADMIN = "durgaprasad.musini@calibrage.in";
    public static final String AGENT = "prasadroyal421@gmail.com";
    public static final String HEAD_OFFICE = "Head Office";
    private boolean isAdmin = false;
    private boolean isAssociatedBranch = false;
    List<BranchList> items;
    Context context;
    String btn_text;
    String filePathUrl = "http://182.18.157.215/SaloonApp/Saloon_Repo/";
    String isHome = "Book Now";
    String loginMail = "";
    String[] adminList = {"durgaprasad.musini@calibrage.in", "admin@test.com", "test@test.com"};
    String[] agentList = {"test1@test.com", "test2@test.com", "prasadmusini99@gmail.com", "prasadroyal421@gmail.com"};
    String[] branchNameList = {"Head Office", "IndiraNagar", "Kukatpally", "JNTU"};

    public SaloonAdapter(List items, Context context, String btn_text) {
        this.items = items;
        this.context = context;
        this.btn_text = btn_text;
    }

    public SaloonAdapter(List items, Context context, String btn_text, String loginMail) {
        this.items = items;
        this.context = context;
        this.btn_text = btn_text;
        this.loginMail = loginMail;
    }

    @NonNull
    @Override
    public ViewChild onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.saloon_branches_design_layout, parent, false);
        return new ViewChild(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewChild holder, int position) {
        BranchList branch = (BranchList) items.get(position);
        holder.booking_btn_tv.setText(btn_text);

        Glide.with(context).load(filePathUrl + branch.getFilePath()).into(holder.branch_img);
        holder.branch_name.setText(branch.getName());
        holder.branch_address.setText(branch.getAddress());

        if (btn_text.equals(isHome)) {
            itIsSaloonAct(holder, branch);
        } else {
            itIsSaloonBranchesAct(holder, branch, loginMail);
        }
    }

    private void itIsSaloonBranchesAct(ViewChild holder, BranchList branch, String loginMail) {

        for (String admin : adminList) {
            if (admin.equals(loginMail)) {
                isAdmin = true;
            }
        }
        if (isAdmin) {
            adminAccess(holder, branch);
        } else {
            agentAccess(holder, branch, loginMail);
        }
    }

    private void agentAccess(ViewChild holder, BranchList branch, String loginMail) {
        for (int i = 0; i < agentList.length; i++) {
            if (loginMail.equals(agentList[i])) {
                agentAssociatedBranch(holder, branchNameList[i], branch);
            }
        }
    }

    private void agentAssociatedBranch(ViewChild holder, String branchName, BranchList branch) {


        holder.branchId.setOnClickListener(view -> {


            int currentPosition = holder.getAdapterPosition();
            BranchList selectedBranchData = items.get(currentPosition);
            String branchFrmData = selectedBranchData.getName();
            if (branchFrmData.equals(branchName)) {

                Intent opIntent = new Intent(context, SaloonOPS.class);
                opIntent.putExtra("branchName", branch.getName());
                opIntent.putExtra("branchAddress", branch.getAddress());
                opIntent.putExtra("branchImage", filePathUrl + branch.getFilePath());
                context.startActivity(opIntent);
            }else {
                warningToast("You are not allowed to access this branch");
            }
        });
    }

    private void adminAccess(ViewChild holder, BranchList branch) {

        holder.branchId.setOnClickListener(view -> {

            Intent opIntent = new Intent(context, SaloonOPS.class);
            opIntent.putExtra("branchId", branch.getId());
            opIntent.putExtra("branchName", branch.getName());
            opIntent.putExtra("branchAddress", branch.getAddress());
            opIntent.putExtra("branchImage", filePathUrl + branch.getFilePath());
            context.startActivity(opIntent);
        });
    }


    private void itIsAgent(BranchList branch) {
        if (!branch.getName().equals(HEAD_OFFICE)) {
            Intent opIntent = new Intent(context, SaloonOPS.class);
            opIntent.putExtra("branchId", branch.getId());
            opIntent.putExtra("branchName", branch.getName());
            opIntent.putExtra("branchAddress", branch.getAddress());
            opIntent.putExtra("branchImage", filePathUrl + branch.getFilePath());
            context.startActivity(opIntent);
        } else {
            warningToast("You are not allowed to access this branch");
        }

    }

    private void itIsSaloonAct(ViewChild holder, BranchList branch) {

        holder.branch_location.setOnClickListener(view -> {
            Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + branch.getAddress());
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            context.startActivity(mapIntent);
        });

        holder.booking_btn.setOnClickListener(view -> {
            Intent sendBranchData = new Intent(context, SaloonSlots.class);
            sendBranchData.putExtra("branchId", branch.getId());
            sendBranchData.putExtra("branchName", branch.getName());
            sendBranchData.putExtra("branchAddress", branch.getAddress());
            sendBranchData.putExtra("branchImage", filePathUrl + branch.getFilePath());
            context.startActivity(sendBranchData);
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    private void successToast(String message) {
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

    private void errorToast(String message) {
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
    private void warningToast(String message) {
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

    class ViewChild extends RecyclerView.ViewHolder {

        ShapeableImageView branch_img;
        TextView branch_name, branch_address, booking_btn_tv;
        ImageView branch_location;
        LinearLayout booking_btn, branchId;

        public ViewChild(@NonNull View itemView) {
            super(itemView);
            branch_img = itemView.findViewById(R.id.branch_img);
            branch_name = itemView.findViewById(R.id.branch_name);
            branch_address = itemView.findViewById(R.id.branch_address);
            branch_location = itemView.findViewById(R.id.branch_location);
            booking_btn = itemView.findViewById(R.id.booking_btn);
            branchId = itemView.findViewById(R.id.branchId);
            booking_btn_tv = itemView.findViewById(R.id.booking_btn_tv);
        }
    }
}


