package com.example.pemesananmakanan;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class AdapterMakanan extends RecyclerView.Adapter<AdapterMakanan.ViewHolderMakanan> {
    private ArrayList<Makanan> daftarMakanan;
    private TextView txtJumlahItem;
    private TextView txtTotalHarga;
    private RecyclerView recyclerView;

    private static int totalJumlahItem = 0;
    private static int totalHarga = 0;

    public AdapterMakanan(ArrayList<Makanan> daftarMakanan, TextView txtJumlahItem, TextView txtTotalHarga, RecyclerView recyclerView) {
        this.daftarMakanan = daftarMakanan;
        this.txtJumlahItem = txtJumlahItem;
        this.txtTotalHarga = txtTotalHarga;
        this.recyclerView = recyclerView;
    }

    @NonNull
    @Override
    public ViewHolderMakanan onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_makanan, parent, false);
        return new ViewHolderMakanan(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderMakanan holder, int position) {
        Makanan makananSaatIni = daftarMakanan.get(position);

        holder.imgMakanan.setImageResource(makananSaatIni.getImgResource());
        holder.txtNamaMakanan.setText(makananSaatIni.getNama());
        holder.txtDeskripsiMakanan.setText(makananSaatIni.getDeskripsi());
        holder.txtHargaMakanan.setText(String.valueOf(makananSaatIni.getHarga()));

        holder.etJumlah.setText(String.valueOf(makananSaatIni.getJumlah()));

        holder.btnKurang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int jumlah = Integer.parseInt(holder.etJumlah.getText().toString());
                if (jumlah > 0) {
                    jumlah--;
                    holder.etJumlah.setText(String.valueOf(jumlah));
                    updateSubtotal(holder, makananSaatIni, jumlah);
                    makananSaatIni.setJumlah(jumlah);
                    updateJumlahItemDanHarga();
                }
            }
        });

        holder.btnTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int jumlah = Integer.parseInt(holder.etJumlah.getText().toString());
                jumlah++;
                holder.etJumlah.setText(String.valueOf(jumlah));
                updateSubtotal(holder, makananSaatIni, jumlah);
                makananSaatIni.setJumlah(jumlah);
                updateJumlahItemDanHarga();
            }
        });

        updateSubtotal(holder, makananSaatIni, makananSaatIni.getJumlah());
    }

    private void updateSubtotal(ViewHolderMakanan holder, Makanan makanan, int jumlah) {
        int subtotal = makanan.getHarga() * jumlah;
        holder.txtSubtotal.setText(String.valueOf(subtotal));
    }

    private void updateJumlahItemDanHarga() {
        totalJumlahItem = 0;
        totalHarga = 0;

        for (Makanan makanan : daftarMakanan) {
            ViewHolderMakanan holder = (ViewHolderMakanan) recyclerView.findViewHolderForAdapterPosition(daftarMakanan.indexOf(makanan));
            int jumlah = Integer.parseInt(holder.etJumlah.getText().toString());

            totalJumlahItem += jumlah;
            totalHarga += (makanan.getHarga() * jumlah);
        }

        if (txtJumlahItem != null && txtTotalHarga != null) {
            txtJumlahItem.setText(totalJumlahItem + " Item");
            txtTotalHarga.setText(String.valueOf(totalHarga));
        }
    }

    @Override
    public int getItemCount() {
        return daftarMakanan.size();
    }

    public static class ViewHolderMakanan extends RecyclerView.ViewHolder {
        public ImageView imgMakanan;
        public TextView txtNamaMakanan;
        public TextView txtDeskripsiMakanan;
        public TextView txtHargaMakanan;
        public Button btnKurang;
        public EditText etJumlah;
        public Button btnTambah;
        public TextView txtSubtotal;

        public ViewHolderMakanan(@NonNull View itemView) {
            super(itemView);
            imgMakanan = itemView.findViewById(R.id.imgMakanan);
            txtNamaMakanan = itemView.findViewById(R.id.txtNamaMakanan);
            txtDeskripsiMakanan = itemView.findViewById(R.id.txtDeskripsiMakanan);
            txtHargaMakanan = itemView.findViewById(R.id.txtHargaMakanan);
            btnKurang = itemView.findViewById(R.id.btnKurang);
            etJumlah = itemView.findViewById(R.id.etJumlah);
            btnTambah = itemView.findViewById(R.id.btnTambah);
            txtSubtotal = itemView.findViewById(R.id.txtSubtotal);
        }
    }
}
