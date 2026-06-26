package com.seblakjeletot.app.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.seblakjeletot.app.R;

public class HomeActivity extends Activity {

    private Button btnLihatMenu;
    private Button btnRiwayat;
    private TextView tvJumlahCart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        btnLihatMenu = findViewById(R.id.btnLihatMenu);
        btnRiwayat = findViewById(R.id.btnRiwayat);
        tvJumlahCart = findViewById(R.id.tvJumlahCart);

        if (btnLihatMenu == null) {
            Toast.makeText(this, "Error: btnLihatMenu tidak ditemukan di activity_home.xml", Toast.LENGTH_LONG).show();
            return;
        }

        if (btnRiwayat == null) {
            Toast.makeText(this, "Error: btnRiwayat tidak ditemukan di activity_home.xml", Toast.LENGTH_LONG).show();
            return;
        }

        btnLihatMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, MenuActivity.class);
                startActivity(intent);
            }
        });

        btnRiwayat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, HistoryActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (tvJumlahCart != null) {
            int jumlahItem = CartActivity.cartItems.size();
            tvJumlahCart.setText("Keranjang: " + jumlahItem + " item");
        }
    }
}