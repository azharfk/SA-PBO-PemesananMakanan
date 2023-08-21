package com.example.pemesananmakanan;

import android.os.Parcel;
import android.os.Parcelable;

public class Makanan implements Parcelable {
    private int imgResource;
    private String nama;
    private String deskripsi;
    private int harga;
    private int jumlah;

    public Makanan(int imgResource, String nama, String deskripsi, int harga) {
        this.imgResource = imgResource;
        this.nama = nama;
        this.deskripsi = deskripsi;
        this.harga = harga;
        this.jumlah = 0;
    }

    public int getImgResource() {
        return imgResource;
    }

    public String getNama() {
        return nama;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public int getHarga() {
        return harga;
    }

    public int getJumlah() {
        return jumlah;
    }

    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
    }

    protected Makanan(Parcel in) {
        imgResource = in.readInt();
        nama = in.readString();
        deskripsi = in.readString();
        harga = in.readInt();
        jumlah = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(imgResource);
        dest.writeString(nama);
        dest.writeString(deskripsi);
        dest.writeInt(harga);
        dest.writeInt(jumlah);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Makanan> CREATOR = new Creator<Makanan>() {
        @Override
        public Makanan createFromParcel(Parcel in) {
            return new Makanan(in);
        }

        @Override
        public Makanan[] newArray(int size) {
            return new Makanan[size];
        }
    };
}
