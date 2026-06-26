package com.seblakjeletot.app.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.seblakjeletot.app.R;
import com.seblakjeletot.app.adapter.CartAdapter;
import com.seblakjeletot.app.model.CartItem;

import java.util.ArrayList;

public class CartActivity extends Activity {

    public static ArrayList<CartItem> cartItems = new ArrayList<>();

    private RecyclerView rvCart;
    private TextView tvCartKosong, tvTotalHarga;
    private Button btnCheckout, btnTambahMenu;
    private CartAdapter cartAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        rvCart = findViewById(R.id.rvCart);
        tvCartKosong = findViewById(R.id.tvCartKosong);
        tvTotalHarga = findViewById(R.id.tvTotalHarga);
        btnCheckout = findViewById(R.id.btnCheckout);
        btnTambahMenu = findViewById(R.id.btnTambahMenu);

        cartAdapter = new CartAdapter(cartItems, new CartAdapter.OnCartChangeListener() {
            @Override
            public void onCartChanged() {
                tampilkanKeranjang();
            }
        });

        rvCart.setLayoutManager(new LinearLayoutManager(this));
        rvCart.setAdapter(cartAdapter);

        tampilkanKeranjang();

        btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CartActivity.this, CheckoutActivity.class);
                startActivity(intent);
            }
        });

        btnTambahMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CartActivity.this, MenuActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        tampilkanKeranjang();
    }

    private void tampilkanKeranjang() {
        int totalHarga = hitungTotalHarga();
        tvTotalHarga.setText(formatRupiah(totalHarga));

        if (cartItems.isEmpty()) {
            tvCartKosong.setVisibility(View.VISIBLE);
            rvCart.setVisibility(View.GONE);
            btnCheckout.setEnabled(false);
        } else {
            tvCartKosong.setVisibility(View.GONE);
            rvCart.setVisibility(View.VISIBLE);
            btnCheckout.setEnabled(true);
        }

        if (cartAdapter != null) {
            cartAdapter.notifyDataSetChanged();
        }
    }

    public static int hitungTotalHarga() {
        int total = 0;
        for (CartItem item : cartItems) {
            total += item.getSubtotal();
        }
        return total;
    }

    private String formatRupiah(int harga) {
        return "Rp" + String.format("%,d", harga).replace(",", ".");
    }
}
