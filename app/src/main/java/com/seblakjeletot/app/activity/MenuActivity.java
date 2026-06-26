package com.seblakjeletot.app.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.seblakjeletot.app.R;
import com.seblakjeletot.app.adapter.MenuAdapter;
import com.seblakjeletot.app.database.DatabaseHelper;
import com.seblakjeletot.app.model.Menu;

import java.util.ArrayList;

public class MenuActivity extends Activity {

    private RecyclerView rvMenu;
    private ArrayList<Menu> menuList;
    private MenuAdapter menuAdapter;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        rvMenu = findViewById(R.id.rvMenu);
        databaseHelper = new DatabaseHelper(this);
        menuList = databaseHelper.getAllMenu();

        menuAdapter = new MenuAdapter(menuList, new MenuAdapter.OnMenuClickListener() {
            @Override
            public void onMenuClick(Menu menu) {
                Intent intent = new Intent(MenuActivity.this, DetailMenuActivity.class);
                intent.putExtra("id_menu", menu.getIdMenu());
                intent.putExtra("nama_menu", menu.getNamaMenu());
                intent.putExtra("kategori", menu.getKategori());
                intent.putExtra("deskripsi", menu.getDeskripsi());
                intent.putExtra("harga", menu.getHarga());
                startActivity(intent);
            }
        });

        rvMenu.setLayoutManager(new LinearLayoutManager(this));
        rvMenu.setAdapter(menuAdapter);
    }
}
