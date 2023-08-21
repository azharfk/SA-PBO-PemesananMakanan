package com.example.pemesananmakanan;

import java.util.Date;

public class RiwayatPesanan {
    private int jumlahItem;
    private int totalHarga;
    private String metodePembayaran;
    private Date waktuTransaksi;

    public RiwayatPesanan(int jumlahItem, int totalHarga, String metodePembayaran, Date waktuTransaksi) {
        this.jumlahItem = jumlahItem;
        this.totalHarga = totalHarga;
        this.metodePembayaran = metodePembayaran;
        this.waktuTransaksi = waktuTransaksi;
    }

    public int getJumlahItem() {
        return jumlahItem;
    }

    public void setJumlahItem(int jumlahItem) {
        this.jumlahItem = jumlahItem;
    }

    public int getTotalHarga() {
        return totalHarga;
    }

    public void setTotalHarga(int totalHarga) {
        this.totalHarga = totalHarga;
    }

    public String getMetodePembayaran() {
        return metodePembayaran;
    }

    public void setMetodePembayaran(String metodePembayaran) {
        this.metodePembayaran = metodePembayaran;
    }

    public Date getWaktuTransaksi() {
        return waktuTransaksi;
    }

    public void setWaktuTransaksi(Date waktuTransaksi) {
        this.waktuTransaksi = waktuTransaksi;
    }
}
