package com.seblakjeletot.app.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.seblakjeletot.app.R;
import com.seblakjeletot.app.database.DatabaseHelper;
import com.seblakjeletot.app.model.CartItem;
import com.seblakjeletot.app.model.Pesanan;

import java.util.ArrayList;

public class HistoryActivity extends Activity {

    public static ArrayList<Pesanan> riwayatPesanan = new ArrayList<>();

    private TextView tvHistoryKosong;
    private LinearLayout layoutHistoryContainer;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        tvHistoryKosong = findViewById(R.id.tvHistoryKosong);
        layoutHistoryContainer = findViewById(R.id.layoutHistoryContainer);
        databaseHelper = new DatabaseHelper(this);

        tampilkanRiwayat();
    }

    @Override
    protected void onResume() {
        super.onResume();
        tampilkanRiwayat();
    }

    private void tampilkanRiwayat() {
        layoutHistoryContainer.removeAllViews();

        ArrayList<Pesanan> dataRiwayat = new ArrayList<>();

        try {
            dataRiwayat = databaseHelper.getRiwayatPesanan();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (dataRiwayat.isEmpty() && !riwayatPesanan.isEmpty()) {
            dataRiwayat = riwayatPesanan;
        }

        if (dataRiwayat.isEmpty()) {
            tvHistoryKosong.setVisibility(View.VISIBLE);
            return;
        }

        tvHistoryKosong.setVisibility(View.GONE);

        for (int i = 0; i < dataRiwayat.size(); i++) {
            Pesanan pesanan = dataRiwayat.get(i);

            if (pesanan.getStatusPesanan() == null || pesanan.getStatusPesanan().trim().isEmpty()) {
                pesanan.setStatusPesanan("Pesanan Diterima");
            }

            layoutHistoryContainer.addView(buatCardPesanan(pesanan));
        }
    }

    private View buatCardPesanan(final Pesanan pesanan) {
        LinearLayout card = new LinearLayout(this);
        card.setOrientation(LinearLayout.VERTICAL);
        card.setBackgroundResource(R.drawable.bg_card);
        card.setElevation(dp(4));
        card.setPadding(dp(16), dp(16), dp(16), dp(16));

        LinearLayout.LayoutParams cardParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        cardParams.setMargins(0, 0, 0, dp(14));
        card.setLayoutParams(cardParams);

        TextView tvTanggal = new TextView(this);
        tvTanggal.setText(pesanan.getTanggal());
        tvTanggal.setTextColor(getResources().getColor(R.color.text_soft));
        tvTanggal.setTextSize(13);
        card.addView(tvTanggal);

        TextView tvNama = new TextView(this);
        tvNama.setText(pesanan.getNamaCustomer());
        tvNama.setTextColor(getResources().getColor(R.color.primary_red));
        tvNama.setTextSize(20);
        tvNama.setTypeface(null, android.graphics.Typeface.BOLD);
        tvNama.setPadding(0, dp(6), 0, 0);
        card.addView(tvNama);

        TextView tvStatus = new TextView(this);
        tvStatus.setText(pesanan.getStatusPesanan());
        tvStatus.setTextColor(Color.WHITE);
        tvStatus.setTextSize(13);
        tvStatus.setTypeface(null, android.graphics.Typeface.BOLD);
        tvStatus.setPadding(dp(12), dp(7), dp(12), dp(7));
        tvStatus.setBackgroundResource(R.drawable.bg_button_secondary);

        LinearLayout.LayoutParams statusParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        statusParams.setMargins(0, dp(10), 0, 0);
        tvStatus.setLayoutParams(statusParams);
        card.addView(tvStatus);

        TextView tvInfo = new TextView(this);
        tvInfo.setText(
                "No HP: " + pesanan.getNoHp() + "\n" +
                        "Pengambilan: " + pesanan.getMetodePengambilan() + "\n" +
                        "Pembayaran: " + pesanan.getMetodePembayaran()
        );
        tvInfo.setTextColor(getResources().getColor(R.color.text_dark));
        tvInfo.setTextSize(14);
        tvInfo.setPadding(0, dp(12), 0, 0);
        card.addView(tvInfo);

        if ("Diantar".equals(pesanan.getMetodePengambilan())
                && pesanan.getAlamat() != null
                && !pesanan.getAlamat().trim().isEmpty()) {

            TextView tvAlamat = new TextView(this);
            tvAlamat.setText("Alamat: " + pesanan.getAlamat());
            tvAlamat.setTextColor(getResources().getColor(R.color.text_soft));
            tvAlamat.setTextSize(14);
            tvAlamat.setPadding(0, dp(6), 0, 0);
            card.addView(tvAlamat);
        }

        TextView tvDetailTitle = new TextView(this);
        tvDetailTitle.setText("Detail Pesanan");
        tvDetailTitle.setTextColor(getResources().getColor(R.color.text_dark));
        tvDetailTitle.setTextSize(15);
        tvDetailTitle.setTypeface(null, android.graphics.Typeface.BOLD);
        tvDetailTitle.setPadding(0, dp(14), 0, 0);
        card.addView(tvDetailTitle);

        TextView tvDetail = new TextView(this);
        tvDetail.setText(buatDetailPesanan(pesanan));
        tvDetail.setTextColor(getResources().getColor(R.color.text_soft));
        tvDetail.setTextSize(14);
        tvDetail.setPadding(0, dp(6), 0, 0);
        card.addView(tvDetail);

        TextView tvTotal = new TextView(this);
        tvTotal.setText("Total: " + formatRupiah(pesanan.getTotalHarga()));
        tvTotal.setTextColor(getResources().getColor(R.color.primary_red));
        tvTotal.setTextSize(20);
        tvTotal.setTypeface(null, android.graphics.Typeface.BOLD);
        tvTotal.setPadding(0, dp(12), 0, 0);
        card.addView(tvTotal);

        if (!"Selesai".equals(pesanan.getStatusPesanan())) {
            Button btnUpdateStatus = new Button(this);
            btnUpdateStatus.setText("Lanjutkan Status");
            btnUpdateStatus.setAllCaps(false);
            btnUpdateStatus.setTextColor(Color.WHITE);
            btnUpdateStatus.setTextSize(14);
            btnUpdateStatus.setTypeface(null, android.graphics.Typeface.BOLD);
            btnUpdateStatus.setBackgroundResource(R.drawable.bg_button_primary);

            LinearLayout.LayoutParams buttonParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    dp(52)
            );
            buttonParams.setMargins(0, dp(16), 0, 0);
            btnUpdateStatus.setLayoutParams(buttonParams);

            btnUpdateStatus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    updateStatusPesanan(pesanan);
                }
            });

            card.addView(btnUpdateStatus);
        }

        return card;
    }

    private void updateStatusPesanan(Pesanan pesanan) {
        String statusSekarang = pesanan.getStatusPesanan();

        if (statusSekarang == null || statusSekarang.trim().isEmpty()) {
            statusSekarang = "Pesanan Diterima";
        }

        String statusBaru;

        if ("Pesanan Diterima".equals(statusSekarang)) {
            statusBaru = "Sedang Dibuat";
        } else if ("Sedang Dibuat".equals(statusSekarang)) {
            if ("Diantar".equals(pesanan.getMetodePengambilan())) {
                statusBaru = "Sedang Diantar";
            } else {
                statusBaru = "Siap Diambil";
            }
        } else if ("Sedang Diantar".equals(statusSekarang) || "Siap Diambil".equals(statusSekarang)) {
            statusBaru = "Selesai";
        } else {
            statusBaru = "Selesai";
        }

        if (pesanan.getIdPesanan() > 0) {
            databaseHelper.updateStatusPesanan(pesanan.getIdPesanan(), statusBaru);
        }

        pesanan.setStatusPesanan(statusBaru);

        Toast.makeText(this, "Status pesanan: " + statusBaru, Toast.LENGTH_SHORT).show();

        tampilkanRiwayat();
    }

    private String buatDetailPesanan(Pesanan pesanan) {
        StringBuilder detail = new StringBuilder();

        if (pesanan.getItems() == null || pesanan.getItems().isEmpty()) {
            return "-";
        }

        for (CartItem item : pesanan.getItems()) {
            detail.append("- ")
                    .append(item.getMenu().getNamaMenu())
                    .append(" x")
                    .append(item.getJumlah())
                    .append(" | Level ")
                    .append(item.getLevelPedas())
                    .append(" | ")
                    .append(formatRupiah(item.getSubtotal()));

            if (item.getCatatan() != null && !item.getCatatan().trim().isEmpty()) {
                detail.append("\n  Catatan: ").append(item.getCatatan());
            }

            detail.append("\n");
        }

        return detail.toString();
    }

    private String formatRupiah(int harga) {
        return "Rp" + String.format("%,d", harga).replace(",", ".");
    }

    private int dp(int value) {
        return (int) (value * getResources().getDisplayMetrics().density);
    }
}