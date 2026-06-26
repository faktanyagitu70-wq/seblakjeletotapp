package com.seblakjeletot.app.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.seblakjeletot.app.R;
import com.seblakjeletot.app.model.CartItem;

import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private ArrayList<CartItem> cartItems;
    private OnCartChangeListener listener;

    public interface OnCartChangeListener {
        void onCartChanged();
    }

    public CartAdapter(ArrayList<CartItem> cartItems, OnCartChangeListener listener) {
        this.cartItems = cartItems;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_cart, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        CartItem item = cartItems.get(position);

        holder.tvCartNamaMenu.setText(item.getMenu().getNamaMenu());
        holder.tvCartLevelPedas.setText("Level Pedas: " + item.getLevelPedas());
        holder.tvCartJumlah.setText(String.valueOf(item.getJumlah()));
        holder.tvCartHarga.setText("Harga: " + formatRupiah(item.getMenu().getHarga()));
        holder.tvCartSubtotal.setText("Subtotal: " + formatRupiah(item.getSubtotal()));

        if (item.getCatatan() == null || item.getCatatan().trim().isEmpty()) {
            holder.tvCartCatatan.setText("Catatan: -");
        } else {
            holder.tvCartCatatan.setText("Catatan: " + item.getCatatan());
        }

        holder.btnCartTambah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                item.setJumlah(item.getJumlah() + 1);
                notifyDataSetChanged();
                if (listener != null) {
                    listener.onCartChanged();
                }
            }
        });

        holder.btnCartKurang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (item.getJumlah() > 1) {
                    item.setJumlah(item.getJumlah() - 1);
                    notifyDataSetChanged();
                    if (listener != null) {
                        listener.onCartChanged();
                    }
                }
            }
        });

        holder.btnCartHapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int adapterPosition = holder.getAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    cartItems.remove(adapterPosition);
                    notifyItemRemoved(adapterPosition);
                    notifyItemRangeChanged(adapterPosition, cartItems.size());
                    if (listener != null) {
                        listener.onCartChanged();
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder {
        TextView tvCartNamaMenu, tvCartLevelPedas, tvCartCatatan;
        TextView tvCartHarga, tvCartJumlah, tvCartSubtotal;
        Button btnCartKurang, btnCartTambah, btnCartHapus;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCartNamaMenu = itemView.findViewById(R.id.tvCartNamaMenu);
            tvCartLevelPedas = itemView.findViewById(R.id.tvCartLevelPedas);
            tvCartCatatan = itemView.findViewById(R.id.tvCartCatatan);
            tvCartHarga = itemView.findViewById(R.id.tvCartHarga);
            tvCartJumlah = itemView.findViewById(R.id.tvCartJumlah);
            tvCartSubtotal = itemView.findViewById(R.id.tvCartSubtotal);
            btnCartKurang = itemView.findViewById(R.id.btnCartKurang);
            btnCartTambah = itemView.findViewById(R.id.btnCartTambah);
            btnCartHapus = itemView.findViewById(R.id.btnCartHapus);
        }
    }

    private String formatRupiah(int harga) {
        return "Rp" + String.format("%,d", harga).replace(",", ".");
    }
}
