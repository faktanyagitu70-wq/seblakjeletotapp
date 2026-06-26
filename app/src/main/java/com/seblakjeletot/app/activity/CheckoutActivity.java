package com.seblakjeletot.app.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.seblakjeletot.app.R;
import com.seblakjeletot.app.database.DatabaseHelper;
import com.seblakjeletot.app.model.CartItem;
import com.seblakjeletot.app.model.Pesanan;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class CheckoutActivity extends Activity {

    private EditText etNamaCustomer, etNoHp, etAlamat;

    private RadioGroup radioGroupPengambilan;
    private RadioGroup radioGroupPembayaran;
    private RadioGroup radioGroupKurir;

    private LinearLayout layoutKurirPengantaran;

    private TextView tvRingkasanPesanan;
    private TextView tvTotalBayar;

    private TextView tvJudulInfoPembayaran;
    private TextView tvInfoPembayaran;
    private TextView tvCatatanPembayaran;
    private TextView tvInfoKurir;

    private ImageView ivQris;

    private Button btnBuatPesanan;

    private String metodePengambilan = "Ambil di Tempat";
    private String metodePembayaran = "Bayar di Tempat";
    private String layananPengantaran = "Grab";

    private DatabaseHelper databaseHelper;

    // GANTI DENGAN NOMOR WHATSAPP BISNIS KAMU
    // Contoh: 081234567890 menjadi 6281234567890
    private static final String NOMOR_WHATSAPP_BISNIS = "6281286297142";

    // GANTI DENGAN DATA REKENING BISNIS KAMU
    private static final String NAMA_BANK = "BCA";
    private static final String NOMOR_REKENING = "1234567890";
    private static final String ATAS_NAMA = "Seblak Jeletot";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        databaseHelper = new DatabaseHelper(this);

        etNamaCustomer = findViewById(R.id.etNamaCustomer);
        etNoHp = findViewById(R.id.etNoHp);
        etAlamat = findViewById(R.id.etAlamat);

        radioGroupPengambilan = findViewById(R.id.radioGroupPengambilan);
        radioGroupPembayaran = findViewById(R.id.radioGroupPembayaran);
        radioGroupKurir = findViewById(R.id.radioGroupKurir);

        layoutKurirPengantaran = findViewById(R.id.layoutKurirPengantaran);

        tvRingkasanPesanan = findViewById(R.id.tvRingkasanPesanan);
        tvTotalBayar = findViewById(R.id.tvTotalBayar);

        tvJudulInfoPembayaran = findViewById(R.id.tvJudulInfoPembayaran);
        tvInfoPembayaran = findViewById(R.id.tvInfoPembayaran);
        tvCatatanPembayaran = findViewById(R.id.tvCatatanPembayaran);
        tvInfoKurir = findViewById(R.id.tvInfoKurir);

        ivQris = findViewById(R.id.ivQris);

        btnBuatPesanan = findViewById(R.id.btnBuatPesanan);

        tampilkanRingkasan();
        tampilkanInfoPembayaran();
        tampilkanInfoKurir();

        radioGroupPengambilan.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.rbAmbilTempat) {
                    metodePengambilan = "Ambil di Tempat";

                    etAlamat.setVisibility(View.GONE);

                    if (layoutKurirPengantaran != null) {
                        layoutKurirPengantaran.setVisibility(View.GONE);
                    }

                } else if (checkedId == R.id.rbAntar) {
                    metodePengambilan = "Diantar";

                    etAlamat.setVisibility(View.VISIBLE);

                    if (layoutKurirPengantaran != null) {
                        layoutKurirPengantaran.setVisibility(View.VISIBLE);
                    }
                }
            }
        });

        if (radioGroupKurir != null) {
            radioGroupKurir.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {

                    if (checkedId == R.id.rbGrab) {
                        layananPengantaran = "Grab";
                    } else if (checkedId == R.id.rbGojek) {
                        layananPengantaran = "Gojek";
                    } else if (checkedId == R.id.rbShopeeFood) {
                        layananPengantaran = "ShopeeFood";
                    } else if (checkedId == R.id.rbKurirPenjual) {
                        layananPengantaran = "Kurir Penjual";
                    }

                    tampilkanInfoKurir();
                }
            });
        }

        radioGroupPembayaran.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                if (checkedId == R.id.rbBayarTempat) {
                    metodePembayaran = "Bayar di Tempat";
                } else if (checkedId == R.id.rbTransfer) {
                    metodePembayaran = "Transfer Bank";
                } else if (checkedId == R.id.rbQris) {
                    metodePembayaran = "QRIS";
                }

                tampilkanInfoPembayaran();
            }
        });

        btnBuatPesanan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prosesCheckout();
            }
        });
    }

    private void tampilkanRingkasan() {
        StringBuilder ringkasan = new StringBuilder();

        if (CartActivity.cartItems.isEmpty()) {
            ringkasan.append("Keranjang masih kosong.");
        } else {
            for (CartItem item : CartActivity.cartItems) {
                ringkasan.append("- ")
                        .append(item.getMenu().getNamaMenu())
                        .append(" x")
                        .append(item.getJumlah())
                        .append(" | Level ")
                        .append(item.getLevelPedas())
                        .append(" | ")
                        .append(formatRupiah(item.getSubtotal()));

                if (item.getCatatan() != null && !item.getCatatan().trim().isEmpty()) {
                    ringkasan.append("\n  Catatan: ").append(item.getCatatan());
                }

                ringkasan.append("\n");
            }
        }

        tvRingkasanPesanan.setText(ringkasan.toString());
        tvTotalBayar.setText(formatRupiah(CartActivity.hitungTotalHarga()));
    }

    private void tampilkanInfoPembayaran() {
        int total = CartActivity.hitungTotalHarga();

        if ("Bayar di Tempat".equals(metodePembayaran)) {

            tvJudulInfoPembayaran.setText("Bayar di Tempat");
            tvInfoPembayaran.setText(
                    "Pembayaran dilakukan langsung saat pesanan diambil atau diterima.\n\n" +
                            "Total yang harus dibayar: " + formatRupiah(total)
            );
            tvCatatanPembayaran.setText("Siapkan uang tunai sesuai total pesanan.");

            if (ivQris != null) {
                ivQris.setVisibility(View.GONE);
            }

        } else if ("Transfer Bank".equals(metodePembayaran)) {

            tvJudulInfoPembayaran.setText("Transfer Bank");
            tvInfoPembayaran.setText(
                    "Silakan transfer pembayaran ke rekening berikut:\n\n" +
                            "Bank: " + NAMA_BANK + "\n" +
                            "No. Rekening: " + NOMOR_REKENING + "\n" +
                            "Atas Nama: " + ATAS_NAMA + "\n\n" +
                            "Total Transfer: " + formatRupiah(total)
            );
            tvCatatanPembayaran.setText("Setelah transfer, kirim bukti pembayaran melalui WhatsApp.");

            if (ivQris != null) {
                ivQris.setVisibility(View.GONE);
            }

        } else if ("QRIS".equals(metodePembayaran)) {

            tvJudulInfoPembayaran.setText("QRIS");
            tvInfoPembayaran.setText(
                    "Silakan lakukan pembayaran melalui QRIS Seblak Jeletot.\n\n" +
                            "Total QRIS: " + formatRupiah(total) + "\n\n" +
                            "Scan kode QRIS di bawah ini, lalu kirim bukti pembayaran melalui WhatsApp."
            );
            tvCatatanPembayaran.setText("Jika sudah membayar via QRIS, kirim screenshot bukti pembayaran melalui WhatsApp.");

            if (ivQris != null) {
                ivQris.setVisibility(View.VISIBLE);
            }
        }
    }

    private void tampilkanInfoKurir() {
        if (tvInfoKurir == null) {
            return;
        }

        if ("Grab".equals(layananPengantaran)) {
            tvInfoKurir.setText("Pesanan akan dikirim menggunakan Grab. Ongkir mengikuti aplikasi Grab.");
        } else if ("Gojek".equals(layananPengantaran)) {
            tvInfoKurir.setText("Pesanan akan dikirim menggunakan Gojek. Ongkir mengikuti aplikasi Gojek.");
        } else if ("ShopeeFood".equals(layananPengantaran)) {
            tvInfoKurir.setText("Pesanan akan dikirim menggunakan ShopeeFood jika toko tersedia pada layanan tersebut.");
        } else if ("Kurir Penjual".equals(layananPengantaran)) {
            tvInfoKurir.setText("Pesanan akan diantar oleh kurir dari pihak Seblak Jeletot jika kurir tersedia.");
        }
    }

    private void prosesCheckout() {
        String nama = etNamaCustomer.getText().toString().trim();
        String noHp = etNoHp.getText().toString().trim();
        String alamatAsli = etAlamat.getText().toString().trim();

        if (nama.isEmpty()) {
            etNamaCustomer.setError("Nama wajib diisi");
            return;
        }

        if (noHp.isEmpty()) {
            etNoHp.setError("Nomor HP wajib diisi");
            return;
        }

        if (CartActivity.cartItems.isEmpty()) {
            Toast.makeText(this, "Keranjang masih kosong", Toast.LENGTH_SHORT).show();
            return;
        }

        if ("Diantar".equals(metodePengambilan) && alamatAsli.isEmpty()) {
            etAlamat.setError("Alamat wajib diisi jika pesanan diantar");
            return;
        }

        String alamatFinal = alamatAsli;

        if ("Diantar".equals(metodePengambilan)) {
            alamatFinal =
                    "Layanan Pengantaran: " + layananPengantaran +
                            "\nAlamat: " + alamatAsli;
        }

        String tanggal = new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault()).format(new Date());

        Pesanan pesanan = new Pesanan(
                nama,
                noHp,
                alamatFinal,
                metodePengambilan,
                metodePembayaran,
                CartActivity.hitungTotalHarga(),
                new ArrayList<>(CartActivity.cartItems),
                "Pesanan Diterima",
                tanggal
        );

        try {
            databaseHelper.simpanPesanan(pesanan);
        } catch (Exception e) {
            e.printStackTrace();
        }

        HistoryActivity.riwayatPesanan.add(pesanan);

        String pesanWhatsApp = buatPesanWhatsApp(pesanan, alamatAsli);

        CartActivity.cartItems.clear();

        Toast.makeText(this, "Pesanan berhasil dibuat", Toast.LENGTH_SHORT).show();

        bukaWhatsApp(pesanWhatsApp);
    }

    private String buatPesanWhatsApp(Pesanan pesanan, String alamatAsli) {
        StringBuilder pesan = new StringBuilder();

        pesan.append("Halo Seblak Jeletot, saya mau pesan.\n\n");

        pesan.append("Data Pelanggan:\n");
        pesan.append("Nama: ").append(pesanan.getNamaCustomer()).append("\n");
        pesan.append("No HP: ").append(pesanan.getNoHp()).append("\n");
        pesan.append("Tanggal: ").append(pesanan.getTanggal()).append("\n\n");

        pesan.append("Detail Pesanan:\n");

        for (CartItem item : pesanan.getItems()) {
            pesan.append("- ")
                    .append(item.getMenu().getNamaMenu())
                    .append(" x")
                    .append(item.getJumlah())
                    .append("\n")
                    .append("  Level Pedas: ")
                    .append(item.getLevelPedas())
                    .append("\n")
                    .append("  Subtotal: ")
                    .append(formatRupiah(item.getSubtotal()))
                    .append("\n");

            if (item.getCatatan() != null && !item.getCatatan().trim().isEmpty()) {
                pesan.append("  Catatan: ").append(item.getCatatan()).append("\n");
            }

            pesan.append("\n");
        }

        pesan.append("Pengambilan:\n");
        pesan.append("Metode: ").append(pesanan.getMetodePengambilan()).append("\n");

        if ("Diantar".equals(pesanan.getMetodePengambilan())) {
            pesan.append("Layanan Pengantaran: ").append(layananPengantaran).append("\n");
            pesan.append("Alamat Pengiriman: ").append(alamatAsli).append("\n");
            pesan.append("Catatan Pengantaran: Ongkir mengikuti layanan pengantaran yang dipilih.\n");
        }

        pesan.append("\nPembayaran:\n");
        pesan.append("Metode: ").append(pesanan.getMetodePembayaran()).append("\n");

        if ("Transfer Bank".equals(pesanan.getMetodePembayaran())) {
            pesan.append("Bank: ").append(NAMA_BANK).append("\n");
            pesan.append("No. Rekening: ").append(NOMOR_REKENING).append("\n");
            pesan.append("Atas Nama: ").append(ATAS_NAMA).append("\n");
            pesan.append("Catatan: Bukti transfer akan dikirim melalui WhatsApp.\n");
        } else if ("QRIS".equals(pesanan.getMetodePembayaran())) {
            pesan.append("Catatan: Customer memilih QRIS dan akan mengirim bukti pembayaran melalui WhatsApp.\n");
        } else {
            pesan.append("Catatan: Pembayaran dilakukan saat pesanan diambil atau diterima.\n");
        }

        pesan.append("\nTotal Bayar: ").append(formatRupiah(pesanan.getTotalHarga())).append("\n\n");
        pesan.append("Mohon konfirmasi pesanannya. Terima kasih.");

        return pesan.toString();
    }

    private void bukaWhatsApp(String pesan) {
        try {
            String url = "https://wa.me/" + NOMOR_WHATSAPP_BISNIS + "?text=" + Uri.encode(pesan);

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            intent.setPackage("com.whatsapp");

            startActivity(intent);
            finish();

        } catch (Exception e) {
            try {
                String url = "https://wa.me/" + NOMOR_WHATSAPP_BISNIS + "?text=" + Uri.encode(pesan);

                Intent intentBusiness = new Intent(Intent.ACTION_VIEW);
                intentBusiness.setData(Uri.parse(url));
                intentBusiness.setPackage("com.whatsapp.w4b");

                startActivity(intentBusiness);
                finish();

            } catch (Exception ex) {
                Toast.makeText(this, "WhatsApp tidak ditemukan. Silakan buka WhatsApp secara manual.", Toast.LENGTH_LONG).show();

                Intent historyIntent = new Intent(CheckoutActivity.this, HistoryActivity.class);
                startActivity(historyIntent);
                finish();
            }
        }
    }

    private String formatRupiah(int harga) {
        return "Rp" + String.format("%,d", harga).replace(",", ".");
    }
}