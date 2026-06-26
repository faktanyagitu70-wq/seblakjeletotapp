package com.seblakjeletot.app.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.seblakjeletot.app.R;
import com.seblakjeletot.app.model.Menu;

import java.util.ArrayList;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuViewHolder> {

    private ArrayList<Menu> menuList;
    private OnMenuClickListener listener;

    public interface OnMenuClickListener {
        void onMenuClick(Menu menu);
    }

    public MenuAdapter(ArrayList<Menu> menuList, OnMenuClickListener listener) {
        this.menuList = menuList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_menu, parent, false);

        return new MenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuViewHolder holder, int position) {
        Menu menu = menuList.get(position);

        holder.tvItemNamaMenu.setText(menu.getNamaMenu());
        holder.tvItemKategori.setText(menu.getKategori());
        holder.tvItemDeskripsi.setText(menu.getDeskripsi());
        holder.tvItemHarga.setText(formatRupiah(menu.getHarga()));
        holder.ivItemMenu.setImageResource(getGambarMenu(menu.getIdMenu()));

        holder.btnPilihMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onMenuClick(menu);
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onMenuClick(menu);
            }
        });
    }

    @Override
    public int getItemCount() {
        return menuList.size();
    }

    private int getGambarMenu(int idMenu) {
        if (idMenu == 1) {
            return R.drawable.seblak_original;
        } else if (idMenu == 2) {
            return R.drawable.seblak_ceker;
        } else if (idMenu == 3) {
            return R.drawable.seblak_bakso_sosis;
        } else if (idMenu == 4) {
            return R.drawable.seblak_tulang;
        } else if (idMenu == 5) {
            return R.drawable.seblak_komplit;
        } else if (idMenu == 6) {
            return R.drawable.seblak_komplit_jeletot;
        } else if (idMenu == 7) {
            return R.drawable.seblak_seafood;
        } else if (idMenu == 8) {
            return R.drawable.es_teh;
        } else if (idMenu == 9) {
            return R.drawable.es_jeruk;
        } else {
            return R.drawable.seblak_original;
        }
    }

    public static class MenuViewHolder extends RecyclerView.ViewHolder {

        ImageView ivItemMenu;
        TextView tvItemNamaMenu, tvItemKategori, tvItemDeskripsi, tvItemHarga;
        Button btnPilihMenu;

        public MenuViewHolder(@NonNull View itemView) {
            super(itemView);

            ivItemMenu = itemView.findViewById(R.id.ivItemMenu);
            tvItemNamaMenu = itemView.findViewById(R.id.tvItemNamaMenu);
            tvItemKategori = itemView.findViewById(R.id.tvItemKategori);
            tvItemDeskripsi = itemView.findViewById(R.id.tvItemDeskripsi);
            tvItemHarga = itemView.findViewById(R.id.tvItemHarga);
            btnPilihMenu = itemView.findViewById(R.id.btnPilihMenu);
        }
    }

    private String formatRupiah(int harga) {
        return "Rp" + String.format("%,d", harga).replace(",", ".");
    }
}