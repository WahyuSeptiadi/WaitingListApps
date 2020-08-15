package com.wahyu.waitinglistapps.View.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;
import com.wahyu.waitinglistapps.R;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

public class RegisPatientActivity extends AppCompatActivity {

    EditText et_patientname, et_patientsick, et_patientcomplain, et_patientage, et_patientaddress, et_patientgender, et_picdoctor;
    CardView btn_regis, btn_searchdoctor;
    CircleImageView civ_profilepatient;
    String imagePasien, namaPasien, nama_dokter, id_dokter;

    private DatabaseReference reference;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regis_patient);

        mAuth = FirebaseAuth.getInstance();

        et_patientname = findViewById(R.id.et_patientname);
        et_patientsick = findViewById(R.id.et_patientsick);
        et_patientcomplain = findViewById(R.id.et_patientcomplains);
        et_patientage = findViewById(R.id.et_patientage);
        et_patientaddress = findViewById(R.id.et_patientaddress);
        et_patientgender = findViewById(R.id.et_patientgender);
        et_picdoctor = findViewById(R.id.et_pickdoctor);
        civ_profilepatient = findViewById(R.id.civ_imageProfilePatient);
        btn_regis = findViewById(R.id.cv_btnpatientregis);
        btn_searchdoctor = findViewById(R.id.cv_btnpickdoctor);

        Intent data = getIntent();
        id_dokter = data.getStringExtra("id_doctor");
        nama_dokter = data.getStringExtra("name_doctor");
        imagePasien = data.getStringExtra("imagepasien");
        namaPasien = data.getStringExtra("namapasien");

        if (namaPasien != null && imagePasien != null) {
            if (imagePasien.substring(0, 4).equals("http")) {
                Picasso.get().load(imagePasien).into(civ_profilepatient);
            } else {
                Picasso.get().load(R.drawable.icon_default_profile).into(civ_profilepatient);
            }
            et_patientname.setText(namaPasien);
            et_picdoctor.setText(nama_dokter);
        }
        btn_searchdoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toListDoctor = new Intent(RegisPatientActivity.this, DoctorListActivity.class);
                toListDoctor.putExtra("Daftar", "Daftar");
                startActivity(toListDoctor);
            }
        });

        btn_regis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String penyakit = et_patientsick.getText().toString();
                String keluhan = et_patientcomplain.getText().toString();
                String umur = et_patientage.getText().toString();
                String jenis = et_patientgender.getText().toString();
                String alamat = et_patientaddress.getText().toString();
                daftarPatient(penyakit, keluhan, umur, jenis, alamat);
                Intent toListPatient = new Intent(RegisPatientActivity.this, PatientListActivity.class);
                startActivity(toListPatient);
                finish();
            }
        });

        reference = FirebaseDatabase.getInstance().getReference("WaitingList");

    }

    private void daftarPatient(String penyakit, String keluhan, String umur, String jenisKelamin, String alamat) {
        String idUser = mAuth.getCurrentUser().getUid();
        HashMap<String, Object> daftarPatient = new HashMap<>();
        daftarPatient.put("id", idUser);
        daftarPatient.put("namePasien", namaPasien);
        daftarPatient.put("namaDokter", nama_dokter);
        daftarPatient.put("idDokter", id_dokter);
        daftarPatient.put("penyakitPasien", penyakit);
        daftarPatient.put("keluhanPasien", keluhan);
        daftarPatient.put("umurPasien", umur);
        daftarPatient.put("jenisPasien", jenisKelamin);
        daftarPatient.put("alamatPasien", alamat);
        daftarPatient.put("imageURL", imagePasien);

        reference.child(id_dokter).setValue(daftarPatient);
        Toast.makeText(this, "Data Berhasil Masuk", Toast.LENGTH_SHORT).show();
    }
}