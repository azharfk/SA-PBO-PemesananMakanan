package com.example.pemesananmakanan;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.Date;

public class DaftarPesananActivity extends AppCompatActivity {

    private String selectedMetodePembayaran;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar_pesanan);

        ArrayList<Makanan> daftarMakanan = getIntent().getParcelableArrayListExtra("daftarMakanan");

        LinearLayout linearLayout = findViewById(R.id.linearLayout);
        TextView txtTotalHargaItem = findViewById(R.id.txtTotalHargaItem);

        int totalHargaItem = 0;

        for (Makanan makanan : daftarMakanan) {
            int jumlah = makanan.getJumlah();
            if (jumlah > 0) {
                int harga = makanan.getHarga();
                int subtotal = jumlah * harga;
                totalHargaItem += subtotal;

                View itemLayout = LayoutInflater.from(this).inflate(R.layout.item_pesanan, linearLayout, false);
                TextView namaItemTextView = itemLayout.findViewById(R.id.namaItem);
                TextView jumlahItemTextView = itemLayout.findViewById(R.id.jumlahItem);
                TextView hargaItemTextView = itemLayout.findViewById(R.id.hargaItem);
                TextView subtotalItemTextView = itemLayout.findViewById(R.id.subtotalItem);

                namaItemTextView.setText(makanan.getNama());
                jumlahItemTextView.setText(String.valueOf(jumlah));
                hargaItemTextView.setText(String.valueOf(harga));
                subtotalItemTextView.setText(String.valueOf(subtotal));

                linearLayout.addView(itemLayout);
            }
        }

        txtTotalHargaItem.setText(String.valueOf(totalHargaItem));

        RadioGroup radioGroup = findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton selectedRadioButton = findViewById(checkedId);
                selectedMetodePembayaran = selectedRadioButton.getText().toString();
            }
        });

        int totalHargaItemFinal = totalHargaItem;

        findViewById(R.id.btnRiwayat).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simpanDataKeSharedPreferences(totalHargaItemFinal);
                simpanTransaksiKeSharedPreferences(new Date());
                Intent intent = new Intent(DaftarPesananActivity.this, RiwayatPesananActivity.class);
                startActivity(intent);
            }
        });
    }

    private void simpanDataKeSharedPreferences(int totalHargaItem) {
        SharedPreferences sharedPreferences = getSharedPreferences("riwayat_pesanan", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt("total_harga", totalHargaItem);
        long waktuTransaksiMillis = System.currentTimeMillis();
        editor.putLong("waktu_transaksi", waktuTransaksiMillis);
        editor.putString("metode_pembayaran", selectedMetodePembayaran);

        editor.apply();
    }

    private void simpanTransaksiKeSharedPreferences(Date waktuTransaksi) {
        SharedPreferences sharedPreferences = getSharedPreferences("riwayat_pesanan", MODE_PRIVATE);
        int jumlahTransaksi = sharedPreferences.getInt("jumlah_transaksi", 0);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("total_harga_" + jumlahTransaksi, sharedPreferences.getInt("total_harga", 0));
        editor.putInt("jumlah_item_" + jumlahTransaksi, hitungTotalJumlahItem());
        editor.putString("metode_pembayaran_" + jumlahTransaksi, selectedMetodePembayaran);
        editor.putLong("waktu_transaksi_" + jumlahTransaksi, waktuTransaksi.getTime());

        jumlahTransaksi++;
        editor.putInt("jumlah_transaksi", jumlahTransaksi);

        editor.apply();
    }

    private int hitungTotalJumlahItem() {
        int totalJumlahItem = 0;
        ArrayList<Makanan> daftarMakanan = getIntent().getParcelableArrayListExtra("daftarMakanan");
        for (Makanan makanan : daftarMakanan) {
            totalJumlahItem += makanan.getJumlah();
        }
        return totalJumlahItem;
    }
}
