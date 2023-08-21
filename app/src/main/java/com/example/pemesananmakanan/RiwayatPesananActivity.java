package com.example.pemesananmakanan;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Date;

public class RiwayatPesananActivity extends AppCompatActivity {

    private RecyclerView recyclerViewRiwayat;
    private AdapterRiwayat adapterRiwayat;
    private ArrayList<RiwayatPesanan> daftarRiwayat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riwayat_pesanan);

        recyclerViewRiwayat = findViewById(R.id.recyclerViewRiwayat);
        recyclerViewRiwayat.setLayoutManager(new LinearLayoutManager(this));

        daftarRiwayat = new ArrayList<>();
        mengambilDataDariSharedPreferences();

        adapterRiwayat = new AdapterRiwayat(daftarRiwayat);
        recyclerViewRiwayat.setAdapter(adapterRiwayat);

        Button btnKembaliKeMenu = findViewById(R.id.btnKembaliKeMenu);
        btnKembaliKeMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RiwayatPesananActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void mengambilDataDariSharedPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("riwayat_pesanan", MODE_PRIVATE);
        int jumlahTransaksi = sharedPreferences.getInt("jumlah_transaksi", 0);

        for (int i = 0; i < jumlahTransaksi; i++) {
            int totalHarga = sharedPreferences.getInt("total_harga_" + i, 0);
            int jumlahItem = sharedPreferences.getInt("jumlah_item_" + i, 0);
            String metodePembayaran = sharedPreferences.getString("metode_pembayaran_" + i, "");
            long waktuTransaksiMillis = sharedPreferences.getLong("waktu_transaksi_" + i, 0);
            Date waktuTransaksi = new Date(waktuTransaksiMillis);

            RiwayatPesanan riwayatPesanan = new RiwayatPesanan(jumlahItem, totalHarga, metodePembayaran, waktuTransaksi);
            daftarRiwayat.add(riwayatPesanan);
        }
    }

    private class AdapterRiwayat extends RecyclerView.Adapter<AdapterRiwayat.ViewHolder> {
        private ArrayList<RiwayatPesanan> dataRiwayat;

        public AdapterRiwayat(ArrayList<RiwayatPesanan> dataRiwayat) {
            this.dataRiwayat = dataRiwayat;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.item_riwayat, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            RiwayatPesanan riwayatPesanan = dataRiwayat.get(position);

            SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
            String formattedDate = sdf.format(riwayatPesanan.getWaktuTransaksi());

            holder.txtTotalHarga.setText(String.valueOf(riwayatPesanan.getTotalHarga()));
            holder.txtMetodePembayaran.setText(riwayatPesanan.getMetodePembayaran());
            holder.txtJumlahItem.setText(riwayatPesanan.getJumlahItem() + " Item");
            holder.txtWaktuTransaksi.setText(formattedDate);
        }

        @Override
        public int getItemCount() {
            return dataRiwayat.size();
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
}
