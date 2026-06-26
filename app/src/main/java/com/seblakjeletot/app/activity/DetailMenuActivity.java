package com.seblakjeletot.app.activity;

import android.app.Activity;
import android.content.Intent;
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
import com.seblakjeletot.app.model.CartItem;
import com.seblakjeletot.app.model.Menu;

public class DetailMenuActivity extends Activity {

    private ImageView ivDetailMenu;
    private TextView tvNamaMenu, tvDeskripsiMenu, tvHargaMenu, tvJumlah, tvInfoUkuran;

    private LinearLayout layoutLevelPedas, layoutMinuman;

    private RadioGroup radioGroupLevelPedas;
    private RadioGroup radioGroupSuhu;
    private RadioGroup radioGroupUkuran;

    private EditText etCatatan;
    private Button btnMinus, btnPlus, btnTambahKeranjang;

    private int idMenu;
    private String namaMenu;
    private String kategori;
    private String deskripsi;
    private int hargaDasar;
    private int hargaFinal;

    private int jumlah = 1;
    private int levelPedas = 0;

    private String suhuMinuman = "Dingin";
    private String ukuranMinuman = "Kecil";
    private int tambahanHargaUkuran = 0;

    private boolean menuMinuman = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_menu);

        ivDetailMenu = findViewById(R.id.ivDetailMenu);
        tvNamaMenu = findViewById(R.id.tvNamaMenu);
        tvDeskripsiMenu = findViewById(R.id.tvDeskripsiMenu);
        tvHargaMenu = findViewById(R.id.tvHargaMenu);
        tvJumlah = findViewById(R.id.tvJumlah);
        tvInfoUkuran = findViewById(R.id.tvInfoUkuran);

        layoutLevelPedas = findViewById(R.id.layoutLevelPedas);
        layoutMinuman = findViewById(R.id.layoutMinuman);

        radioGroupLevelPedas = findViewById(R.id.radioGroupLevelPedas);
        radioGroupSuhu = findViewById(R.id.radioGroupSuhu);
        radioGroupUkuran = findViewById(R.id.radioGroupUkuran);

        etCatatan = findViewById(R.id.etCatatan);
        btnMinus = findViewById(R.id.btnMinus);
        btnPlus = findViewById(R.id.btnPlus);
        btnTambahKeranjang = findViewById(R.id.btnTambahKeranjang);

        ambilDataMenu();
        tampilkanDataMenu();
        aturPilihanMenu();
        aturTombolJumlah();
        aturTombolKeranjang();
    }

    private void ambilDataMenu() {
        idMenu = getIntent().getIntExtra("idMenu", 0);
        if (idMenu == 0) {
            idMenu = getIntent().getIntExtra("id_menu", 0);
        }

        namaMenu = getIntent().getStringExtra("namaMenu");
        if (namaMenu == null) {
            namaMenu = getIntent().getStringExtra("nama_menu");
        }

        kategori = getIntent().getStringExtra("kategori");

        deskripsi = getIntent().getStringExtra("deskripsi");

        hargaDasar = getIntent().getIntExtra("harga", 0);
        hargaFinal = hargaDasar;

        if (namaMenu == null) {
            namaMenu = "Menu";
        }

        if (deskripsi == null) {
            deskripsi = "";
        }

        if (kategori == null || kategori.trim().isEmpty()) {
            if (namaMenu.toLowerCase().contains("teh")
                    || namaMenu.toLowerCase().contains("jeruk")
                    || namaMenu.toLowerCase().contains("minuman")
                    || namaMenu.toLowerCase().contains("air")) {
                kategori = "Minuman";
            } else {
                kategori = "Seblak";
            }
        }

        menuMinuman = "Minuman".equalsIgnoreCase(kategori);
    }

    private void tampilkanDataMenu() {
        tvNamaMenu.setText(namaMenu);
        tvDeskripsiMenu.setText(deskripsi);
        tvHargaMenu.setText(formatRupiah(hargaFinal));
        tvJumlah.setText(String.valueOf(jumlah));

        ivDetailMenu.setImageResource(getGambarMenu(idMenu));

        if (menuMinuman) {
            layoutLevelPedas.setVisibility(View.GONE);
            layoutMinuman.setVisibility(View.VISIBLE);
        } else {
            layoutLevelPedas.setVisibility(View.VISIBLE);
            layoutMinuman.setVisibility(View.GONE);
        }
    }

    private void aturPilihanMenu() {
        radioGroupLevelPedas.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rbLevel0) {
                    levelPedas = 0;
                } else if (checkedId == R.id.rbLevel1) {
                    levelPedas = 1;
                } else if (checkedId == R.id.rbLevel2) {
                    levelPedas = 2;
                } else if (checkedId == R.id.rbLevel3) {
                    levelPedas = 3;
                } else if (checkedId == R.id.rbLevel4) {
                    levelPedas = 4;
                } else if (checkedId == R.id.rbLevel5) {
                    levelPedas = 5;
                }
            }
        });

        radioGroupSuhu.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rbDingin) {
                    suhuMinuman = "Dingin";
                } else if (checkedId == R.id.rbHangat) {
                    suhuMinuman = "Hangat";
                }
            }
        });

        radioGroupUkuran.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rbKecil) {
                    ukuranMinuman = "Kecil";
                    tambahanHargaUkuran = 0;
                    tvInfoUkuran.setText("Ukuran kecil menggunakan harga dasar.");
                } else if (checkedId == R.id.rbSedang) {
                    ukuranMinuman = "Sedang";
                    tambahanHargaUkuran = 2000;
                    tvInfoUkuran.setText("Ukuran sedang dikenakan tambahan Rp2.000.");
                } else if (checkedId == R.id.rbBesar) {
                    ukuranMinuman = "Besar";
                    tambahanHargaUkuran = 4000;
                    tvInfoUkuran.setText("Ukuran besar dikenakan tambahan Rp4.000.");
                }

                hargaFinal = hargaDasar + tambahanHargaUkuran;
                tvHargaMenu.setText(formatRupiah(hargaFinal));
            }
        });
    }

    private void aturTombolJumlah() {
        btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (jumlah > 1) {
                    jumlah--;
                    tvJumlah.setText(String.valueOf(jumlah));
                }
            }
        });

        btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jumlah++;
                tvJumlah.setText(String.valueOf(jumlah));
            }
        });
    }

    private void aturTombolKeranjang() {
        btnTambahKeranjang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String catatan = etCatatan.getText().toString().trim();

                Menu menuDipilih = new Menu(
                        idMenu,
                        namaMenu,
                        kategori,
                        deskripsi,
                        hargaFinal
                );

                CartItem cartItem;

                if (menuMinuman) {
                    cartItem = new CartItem(
                            menuDipilih,
                            jumlah,
                            suhuMinuman,
                            ukuranMinuman,
                            catatan
                    );
                } else {
                    cartItem = new CartItem(
                            menuDipilih,
                            jumlah,
                            levelPedas,
                            catatan
                    );
                }

                CartActivity.cartItems.add(cartItem);

                Toast.makeText(
                        DetailMenuActivity.this,
                        "Menu ditambahkan ke keranjang",
                        Toast.LENGTH_SHORT
                ).show();

                Intent intent = new Intent(DetailMenuActivity.this, CartActivity.class);
                startActivity(intent);
                finish();
            }
        });
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

    private String formatRupiah(int harga) {
        return "Rp" + String.format("%,d", harga).replace(",", ".");
    }
}