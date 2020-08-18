package com.wahyu.waitinglistapps.Model;

/**
 * Created by wahyu_septiadi on 15, August 2020.
 * Visit My GitHub --> https://github.com/WahyuSeptiadi
 */

public class PatientModel {
    private String idAntrian;
    private String idDokter;
    private String namaDokter;
    private String imageDoctor;
    private String spesialis;
    private String imageURL;
    private String idPasien;
    private String namaPasien;
    private String alamatPasien;
    private String jenisPasien;
    private String keluhanPasien;
    private String penyakitPasien;
    private String umurPasien;
    private String tanggalDaftar;
    private String waktuDaftar;
    private String waktuSelesai;
    private String status;

    public PatientModel() {
    }

    public String getIdAntrian() {
        return idAntrian;
    }

    public void setIdAntrian(String idAntrian) {
        this.idAntrian = idAntrian;
    }

    public String getSpesialis() {
        return spesialis;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setSpesialis(String spesialis) {
        this.spesialis = spesialis;
    }

    public String getImageDoctor() {
        return imageDoctor;
    }

    public void setImageDoctor(String imageDoctor) {
        this.imageDoctor = imageDoctor;
    }

    public String getTanggalDaftar() {
        return tanggalDaftar;
    }

    public void setTanggalDaftar(String tanggalDaftar) {
        this.tanggalDaftar = tanggalDaftar;
    }

    public String getIdDokter() {
        return idDokter;
    }

    public void setIdDokter(String idDokter) {
        this.idDokter = idDokter;
    }

    public String getNamaDokter() {
        return namaDokter;
    }

    public void setNamaDokter(String namaDokter) {
        this.namaDokter = namaDokter;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getIdPasien() {
        return idPasien;
    }

    public void setIdPasien(String idPasien) {
        this.idPasien = idPasien;
    }

    public String getNamaPasien() {
        return namaPasien;
    }

    public void setNamaPasien(String namaPasien) {
        this.namaPasien = namaPasien;
    }

    public String getAlamatPasien() {
        return alamatPasien;
    }

    public void setAlamatPasien(String alamatPasien) {
        this.alamatPasien = alamatPasien;
    }

    public String getJenisPasien() {
        return jenisPasien;
    }

    public void setJenisPasien(String jenisPasien) {
        this.jenisPasien = jenisPasien;
    }

    public String getKeluhanPasien() {
        return keluhanPasien;
    }

    public void setKeluhanPasien(String keluhanPasien) {
        this.keluhanPasien = keluhanPasien;
    }

    public String getPenyakitPasien() {
        return penyakitPasien;
    }

    public void setPenyakitPasien(String penyakitPasien) {
        this.penyakitPasien = penyakitPasien;
    }

    public String getUmurPasien() {
        return umurPasien;
    }

    public void setUmurPasien(String umurPasien) {
        this.umurPasien = umurPasien;
    }

    public String getWaktuDaftar() {
        return waktuDaftar;
    }

    public void setWaktuDaftar(String waktuDaftar) {
        this.waktuDaftar = waktuDaftar;
    }

    public String getWaktuSelesai() {
        return waktuSelesai;
    }

    public void setWaktuSelesai(String waktuSelesai) {
        this.waktuSelesai = waktuSelesai;
    }
}
