package com.wahyu.waitinglistapps.View.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.wahyu.waitinglistapps.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class DoctorDetailsActivity extends AppCompatActivity {

    private String getDoctorId, getName, getImageURL, getSpesialis, getWorkday, getTimeStart, getTimeFinish, getPatientLimit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_details);

        TextView titleBar = findViewById(R.id.titlebar_doctordetails);
        CircleImageView civProfileDoctor = findViewById(R.id.civ_profile_doctordetails);

        TextView tvSpesialis = findViewById(R.id.tv_spesialis_doctordetails);
        TextView tvWorkDay = findViewById(R.id.tv_workday_doctordetails);
        TextView tvTimeStart = findViewById(R.id.tv_worktimestart_doctordetails);
        TextView tvTimeFinish = findViewById(R.id.tv_worktimefinish_doctordetails);
        TextView tvPatientLimit = findViewById(R.id.tv_patientlimit_doctordetails);
        TextView btnShowpatient = findViewById(R.id.tv_btnshowpatient);

        getDataFromIntentList();

        if (getName != null && getImageURL != null && getSpesialis != null && getWorkday != null &&
                getTimeStart != null && getTimeFinish != null && getPatientLimit != null) {

            titleBar.setText(getName);
            if (getImageURL.substring(0, 4).equals("http")) {
                Picasso.get().load(getImageURL).into(civProfileDoctor);
            } else {
                Picasso.get().load(R.drawable.icon_default_profile).into(civProfileDoctor);
            }
            tvSpesialis.setText(getSpesialis);
            tvWorkDay.setText(getWorkday);
            tvTimeStart.setText(getTimeStart);
            tvTimeFinish.setText(getTimeFinish);
            tvPatientLimit.setText(getPatientLimit);
        }
    }

    private void getDataFromIntentList() {
        Intent data = getIntent();
        getDoctorId = data.getStringExtra("id");
        getName = data.getStringExtra("name");
        getImageURL = data.getStringExtra("imgprofile");
        getSpesialis = data.getStringExtra("spesialis");
        getWorkday = data.getStringExtra("workday");
        getTimeStart = data.getStringExtra("timestart");
        getTimeFinish = data.getStringExtra("timefinish");
        getPatientLimit = data.getStringExtra("limit");
    }
}