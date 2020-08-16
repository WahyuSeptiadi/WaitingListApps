package com.wahyu.waitinglistapps.View.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.wahyu.waitinglistapps.Adapter.PatientListAdapter;
import com.wahyu.waitinglistapps.Model.PatientModel;
import com.wahyu.waitinglistapps.R;

import java.util.ArrayList;

public class PatientListActivity extends AppCompatActivity {

    private RecyclerView rvPatientList;
    private ProgressBar progressBar;
    private TextView tvEmptyPatient;

    private PatientListAdapter patientListAdapter;
    private DatabaseReference reference;
    private ArrayList<PatientModel> patientModelArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_list);

        progressBar = findViewById(R.id.progressBar);
        tvEmptyPatient = findViewById(R.id.tv_emptyPatient);
        rvPatientList = findViewById(R.id.rv_patientlist);
        rvPatientList.setLayoutManager(new LinearLayoutManager(this));
        rvPatientList.smoothScrollToPosition(0);
        rvPatientList.setHasFixedSize(true);

        Intent data = getIntent();
        String dokterId = data.getStringExtra("id_dokter");

        //inisialisasi
        if (dokterId != null) {
            reference = FirebaseDatabase.getInstance().getReference("WaitingList").child(dokterId);
        }

        getAllPatient();

    }

    private void getAllPatient() {
        patientModelArrayList = new ArrayList<>();

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                patientModelArrayList.clear();
                if (!snapshot.exists()) {
                    tvEmptyPatient.setVisibility(View.VISIBLE);
                } else {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        PatientModel patientModel = dataSnapshot.getValue(PatientModel.class);
                        patientModelArrayList.add(patientModel);
                    }
                    patientListAdapter = new PatientListAdapter(PatientListActivity.this, patientModelArrayList);
                    rvPatientList.setAdapter(patientListAdapter);
                    patientListAdapter.notifyDataSetChanged();
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}