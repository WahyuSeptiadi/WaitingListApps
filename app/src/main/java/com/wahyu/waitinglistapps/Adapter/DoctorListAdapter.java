package com.wahyu.waitinglistapps.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.wahyu.waitinglistapps.Model.DoctorModel;
import com.wahyu.waitinglistapps.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by wahyu_septiadi on 15, August 2020.
 * Visit My GitHub --> https://github.com/WahyuSeptiadi
 */

public class DoctorListAdapter extends RecyclerView.Adapter<DoctorListAdapter.ViewHolder> {

    private Activity mActivity;
    private ArrayList<DoctorModel> doctorModelArrayList;

    public DoctorListAdapter(Activity mActivity, ArrayList<DoctorModel> doctorModelArrayList) {
        this.mActivity = mActivity;
        this.doctorModelArrayList = doctorModelArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mActivity).inflate(R.layout.list_item_doctor, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DoctorModel doctorModel = doctorModelArrayList.get(position);
        if (doctorModel.getImageURL().substring(0, 4).equals("http")) {
            Picasso.get().load(doctorModel.getImageURL()).into(holder.civProfileDoctor);
        } else {
            Picasso.get().load(R.drawable.icon_default_profile).into(holder.civProfileDoctor);
        }

        holder.tvNameDr.setText(doctorModel.getName());
        holder.tvSpesialis.setText(doctorModel.getSpesialis());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


    }

    @Override
    public int getItemCount() {
        return doctorModelArrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private CircleImageView civProfileDoctor;
        private TextView tvNameDr, tvSpesialis;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            civProfileDoctor = itemView.findViewById(R.id.civ_doctorlist);
            tvNameDr = itemView.findViewById(R.id.tv_name_doctorlist);
            tvSpesialis = itemView.findViewById(R.id.tv_spesialis_doctorlist);
        }
    }
}
