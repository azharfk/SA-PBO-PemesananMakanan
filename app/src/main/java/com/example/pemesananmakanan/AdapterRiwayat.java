package com.example.pemesananmakanan;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class AdapterRiwayat extends RecyclerView.Adapter<AdapterRiwayat.ViewHolder> {

    private ArrayList<RiwayatPesanan> dataRiwayat;

    public AdapterRiwayat(ArrayList<RiwayatPesanan> dataRiwayat) {
        this.dataRiwayat = dataRiwayat;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_riwayat, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RiwayatPesanan riwayatPesanan = dataRiwayat.get(position);

        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
        String formattedDate = sdf.format(riwayatPesanan.getWaktuTransaksi());

        holder.txtTotalHarga.setText(String.valueOf(riwayatPesanan.getTotalHarga()));
        holder.txtMetodePembayaran.setText(riwayatPesanan.getMetodePembayaran());
        holder.txtJumlahItem.setText(riwayatPesanan.getJumlahItem() + " Item" );
        holder.txtWaktuTransaksi.setText(formattedDate);
    }

    @Override
    public int getItemCount() {
        return dataRiwayat.size();
    }

    public int getTotalJumlahItem() {
        int totalJumlahItem = 0;

        for (RiwayatPesanan riwayatPesanan : dataRiwayat) {
            totalJumlahItem += riwayatPesanan.getJumlahItem();
        }

        return totalJumlahItem;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtTotalHarga;
        private TextView txtMetodePembayaran;
        private TextView txtJumlahItem;
        private TextView txtWaktuTransaksi;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTotalHarga = itemView.findViewById(R.id.txtTotalHargaRiwayat);
            txtMetodePembayaran = itemView.findViewById(R.id.txtMetodePembayaranRiwayat);
            txtJumlahItem = itemView.findViewById(R.id.txtJumlahItemRiwayat);
            txtWaktuTransaksi = itemView.findViewById(R.id.txtWaktuTransaksiRiwayat);
        }
    }
}
