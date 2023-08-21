package com.example.pemesananmakanan;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AdapterMakanan adapterMakanan;
    private ArrayList<Makanan> daftarMakanan;
    private TextView txtJumlahItem;
    private TextView txtJumlahHarga;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        txtJumlahItem = findViewById(R.id.txtJumlahItem);
        txtJumlahHarga = findViewById(R.id.txtTotalHarga);

        daftarMakanan = new ArrayList<>();
        daftarMakanan.add(new Makanan(R.drawable.sate, "Sate Ayam", "sate ayam dengan bumbu kacang", 20000));
        daftarMakanan.add(new Makanan(R.drawable.nasgor, "Nasi Goreng", "nasi goreng spesial dengan telur", 15000));
        daftarMakanan.add(new Makanan(R.drawable.esteh, "Es Teh", "es teh minuman menyegarkan", 10000));

        adapterMakanan = new AdapterMakanan(daftarMakanan, txtJumlahItem, txtJumlahHarga, recyclerView);
        recyclerView.setAdapter(adapterMakanan);

        Button btnBayarPesanan = findViewById(R.id.btnBayarPesanan);
        btnBayarPesanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DaftarPesananActivity.class);
                intent.putParcelableArrayListExtra("daftarMakanan", daftarMakanan);
                startActivity(intent);
            }
        });

        Button btnRiwayat = findViewById(R.id.btnRiwayat);
        btnRiwayat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int totalHargaItem = getTotalHargaItem();
                simpanDataKeSharedPreferences(totalHargaItem);
                Intent intent = new Intent(MainActivity.this, RiwayatPesananActivity.class);
                startActivity(intent);
            }
        });
    }

    private void simpanDataKeSharedPreferences(int totalHargaItem) {
        SharedPreferences sharedPreferences = getSharedPreferences("riwayat_pesanan", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt("total_harga", totalHargaItem);
        editor.putInt("jumlah_item", getTotalJumlahItem());
        //editor.putString("metode_pembayaran", metodePembayaran);
        editor.putLong("waktu_transaksi", System.currentTimeMillis());

        editor.apply();
    }

    private int getTotalHargaItem() {
        int totalHarga = 0;
        for (Makanan makanan : daftarMakanan) {
            totalHarga += makanan.getJumlah() * makanan.getHarga();
        }
        return totalHarga;
    }

    private int getTotalJumlahItem() {
        int totalJumlah = 0;
        for (Makanan makanan : daftarMakanan) {
            totalJumlah += makanan.getJumlah();
        }
        return totalJumlah;
    }
}
