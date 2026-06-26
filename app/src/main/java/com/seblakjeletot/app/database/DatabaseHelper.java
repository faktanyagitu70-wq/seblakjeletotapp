package com.seblakjeletot.app.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.seblakjeletot.app.model.CartItem;
import com.seblakjeletot.app.model.Menu;
import com.seblakjeletot.app.model.Pesanan;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "seblak_jeletot.db";
    private static final int DATABASE_VERSION = 3;

    private static final String TABLE_MENU = "menu";
    private static final String COL_MENU_ID = "id_menu";
    private static final String COL_MENU_NAMA = "nama_menu";
    private static final String COL_MENU_KATEGORI = "kategori";
    private static final String COL_MENU_DESKRIPSI = "deskripsi";
    private static final String COL_MENU_HARGA = "harga";

    private static final String TABLE_PESANAN = "pesanan";
    private static final String COL_PESANAN_ID = "id_pesanan";
    private static final String COL_NAMA_CUSTOMER = "nama_customer";
    private static final String COL_NO_HP = "no_hp";
    private static final String COL_ALAMAT = "alamat";
    private static final String COL_METODE_PENGAMBILAN = "metode_pengambilan";
    private static final String COL_METODE_PEMBAYARAN = "metode_pembayaran";
    private static final String COL_TOTAL_HARGA = "total_harga";
    private static final String COL_STATUS_PESANAN = "status_pesanan";
    private static final String COL_TANGGAL = "tanggal";

    private static final String TABLE_DETAIL_PESANAN = "detail_pesanan";
    private static final String COL_DETAIL_ID = "id_detail";
    private static final String COL_DETAIL_ID_PESANAN = "id_pesanan";
    private static final String COL_DETAIL_NAMA_MENU = "nama_menu";
    private static final String COL_DETAIL_JUMLAH = "jumlah";
    private static final String COL_DETAIL_LEVEL_PEDAS = "level_pedas";
    private static final String COL_DETAIL_CATATAN = "catatan";
    private static final String COL_DETAIL_SUBTOTAL = "subtotal";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String createTableMenu = "CREATE TABLE " + TABLE_MENU + " (" +
                COL_MENU_ID + " INTEGER PRIMARY KEY, " +
                COL_MENU_NAMA + " TEXT NOT NULL, " +
                COL_MENU_KATEGORI + " TEXT NOT NULL, " +
                COL_MENU_DESKRIPSI + " TEXT, " +
                COL_MENU_HARGA + " INTEGER NOT NULL" +
                ")";

        String createTablePesanan = "CREATE TABLE " + TABLE_PESANAN + " (" +
                COL_PESANAN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_NAMA_CUSTOMER + " TEXT NOT NULL, " +
                COL_NO_HP + " TEXT NOT NULL, " +
                COL_ALAMAT + " TEXT, " +
                COL_METODE_PENGAMBILAN + " TEXT NOT NULL, " +
                COL_METODE_PEMBAYARAN + " TEXT NOT NULL, " +
                COL_TOTAL_HARGA + " INTEGER NOT NULL, " +
                COL_STATUS_PESANAN + " TEXT NOT NULL, " +
                COL_TANGGAL + " TEXT NOT NULL" +
                ")";

        String createTableDetailPesanan = "CREATE TABLE " + TABLE_DETAIL_PESANAN + " (" +
                COL_DETAIL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_DETAIL_ID_PESANAN + " INTEGER NOT NULL, " +
                COL_DETAIL_NAMA_MENU + " TEXT NOT NULL, " +
                COL_DETAIL_JUMLAH + " INTEGER NOT NULL, " +
                COL_DETAIL_LEVEL_PEDAS + " INTEGER NOT NULL, " +
                COL_DETAIL_CATATAN + " TEXT, " +
                COL_DETAIL_SUBTOTAL + " INTEGER NOT NULL" +
                ")";

        db.execSQL(createTableMenu);
        db.execSQL(createTablePesanan);
        db.execSQL(createTableDetailPesanan);

        insertMenuAwal(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DETAIL_PESANAN);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PESANAN);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MENU);
        onCreate(db);
    }

    private void insertMenuAwal(SQLiteDatabase db) {
        tambahMenuAwal(db, 1, "Seblak Original", "Seblak",
                "Seblak original dengan kerupuk, sayur, dan bumbu khas Jeletot.", 12000);

        tambahMenuAwal(db, 2, "Seblak Ceker", "Seblak",
                "Seblak pedas dengan tambahan ceker empuk.", 18000);

        tambahMenuAwal(db, 3, "Seblak Bakso Sosis", "Seblak",
                "Seblak dengan topping bakso dan sosis.", 17000);

        tambahMenuAwal(db, 4, "Seblak Tulang", "Seblak",
                "Seblak pedas dengan tambahan tulang ayam.", 18000);

        tambahMenuAwal(db, 5, "Seblak Komplit", "Seblak",
                "Seblak lengkap dengan telur, bakso, sosis, kerupuk, dan sayuran.", 22000);

        tambahMenuAwal(db, 6, "Seblak Komplit Jeletot", "Seblak",
                "Seblak super pedas dengan ceker, bakso, sosis, telur, kerupuk, dan topping lengkap.", 25000);

        tambahMenuAwal(db, 7, "Seblak Seafood", "Seblak",
                "Seblak dengan topping seafood dan kuah pedas khas Jeletot.", 28000);

        tambahMenuAwal(db, 8, "Es Teh", "Minuman",
                "Minuman es teh manis.", 5000);

        tambahMenuAwal(db, 9, "Es Jeruk", "Minuman",
                "Minuman es jeruk segar.", 6000);
    }

    private void tambahMenuAwal(SQLiteDatabase db,
                                int idMenu,
                                String namaMenu,
                                String kategori,
                                String deskripsi,
                                int harga) {

        ContentValues values = new ContentValues();
        values.put(COL_MENU_ID, idMenu);
        values.put(COL_MENU_NAMA, namaMenu);
        values.put(COL_MENU_KATEGORI, kategori);
        values.put(COL_MENU_DESKRIPSI, deskripsi);
        values.put(COL_MENU_HARGA, harga);

        db.insert(TABLE_MENU, null, values);
    }

    public ArrayList<Menu> getAllMenu() {
        ArrayList<Menu> menuList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_MENU, null);

        if (cursor.moveToFirst()) {
            do {
                int idMenu = cursor.getInt(cursor.getColumnIndexOrThrow(COL_MENU_ID));
                String namaMenu = cursor.getString(cursor.getColumnIndexOrThrow(COL_MENU_NAMA));
                String kategori = cursor.getString(cursor.getColumnIndexOrThrow(COL_MENU_KATEGORI));
                String deskripsi = cursor.getString(cursor.getColumnIndexOrThrow(COL_MENU_DESKRIPSI));
                int harga = cursor.getInt(cursor.getColumnIndexOrThrow(COL_MENU_HARGA));

                menuList.add(new Menu(idMenu, namaMenu, kategori, deskripsi, harga));

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return menuList;
    }

    public long simpanPesanan(Pesanan pesanan) {
        SQLiteDatabase db = this.getWritableDatabase();

        long idPesanan = -1;

        db.beginTransaction();

        try {
            ContentValues valuesPesanan = new ContentValues();
            valuesPesanan.put(COL_NAMA_CUSTOMER, pesanan.getNamaCustomer());
            valuesPesanan.put(COL_NO_HP, pesanan.getNoHp());
            valuesPesanan.put(COL_ALAMAT, pesanan.getAlamat());
            valuesPesanan.put(COL_METODE_PENGAMBILAN, pesanan.getMetodePengambilan());
            valuesPesanan.put(COL_METODE_PEMBAYARAN, pesanan.getMetodePembayaran());
            valuesPesanan.put(COL_TOTAL_HARGA, pesanan.getTotalHarga());
            valuesPesanan.put(COL_STATUS_PESANAN, pesanan.getStatusPesanan());
            valuesPesanan.put(COL_TANGGAL, pesanan.getTanggal());

            idPesanan = db.insert(TABLE_PESANAN, null, valuesPesanan);

            if (idPesanan != -1) {
                pesanan.setIdPesanan((int) idPesanan);

                for (CartItem item : pesanan.getItems()) {
                    ContentValues valuesDetail = new ContentValues();
                    valuesDetail.put(COL_DETAIL_ID_PESANAN, idPesanan);
                    valuesDetail.put(COL_DETAIL_NAMA_MENU, item.getMenu().getNamaMenu());
                    valuesDetail.put(COL_DETAIL_JUMLAH, item.getJumlah());
                    valuesDetail.put(COL_DETAIL_LEVEL_PEDAS, item.getLevelPedas());
                    valuesDetail.put(COL_DETAIL_CATATAN, item.getCatatan());
                    valuesDetail.put(COL_DETAIL_SUBTOTAL, item.getSubtotal());

                    db.insert(TABLE_DETAIL_PESANAN, null, valuesDetail);
                }
            }

            db.setTransactionSuccessful();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            db.close();
        }

        return idPesanan;
    }

    public ArrayList<Pesanan> getRiwayatPesanan() {
        ArrayList<Pesanan> riwayatList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursorPesanan = db.rawQuery(
                "SELECT * FROM " + TABLE_PESANAN + " ORDER BY " + COL_PESANAN_ID + " DESC",
                null
        );

        if (cursorPesanan.moveToFirst()) {
            do {
                int idPesanan = cursorPesanan.getInt(cursorPesanan.getColumnIndexOrThrow(COL_PESANAN_ID));
                String namaCustomer = cursorPesanan.getString(cursorPesanan.getColumnIndexOrThrow(COL_NAMA_CUSTOMER));
                String noHp = cursorPesanan.getString(cursorPesanan.getColumnIndexOrThrow(COL_NO_HP));
                String alamat = cursorPesanan.getString(cursorPesanan.getColumnIndexOrThrow(COL_ALAMAT));
                String metodePengambilan = cursorPesanan.getString(cursorPesanan.getColumnIndexOrThrow(COL_METODE_PENGAMBILAN));
                String metodePembayaran = cursorPesanan.getString(cursorPesanan.getColumnIndexOrThrow(COL_METODE_PEMBAYARAN));
                int totalHarga = cursorPesanan.getInt(cursorPesanan.getColumnIndexOrThrow(COL_TOTAL_HARGA));
                String statusPesanan = cursorPesanan.getString(cursorPesanan.getColumnIndexOrThrow(COL_STATUS_PESANAN));
                String tanggal = cursorPesanan.getString(cursorPesanan.getColumnIndexOrThrow(COL_TANGGAL));

                ArrayList<CartItem> itemPesanan = getDetailPesananById(db, idPesanan);

                Pesanan pesanan = new Pesanan(
                        namaCustomer,
                        noHp,
                        alamat,
                        metodePengambilan,
                        metodePembayaran,
                        totalHarga,
                        itemPesanan,
                        statusPesanan,
                        tanggal
                );

                pesanan.setIdPesanan(idPesanan);

                riwayatList.add(pesanan);

            } while (cursorPesanan.moveToNext());
        }

        cursorPesanan.close();
        db.close();

        return riwayatList;
    }

    private ArrayList<CartItem> getDetailPesananById(SQLiteDatabase db, int idPesanan) {
        ArrayList<CartItem> itemList = new ArrayList<>();

        Cursor cursorDetail = db.rawQuery(
                "SELECT * FROM " + TABLE_DETAIL_PESANAN +
                        " WHERE " + COL_DETAIL_ID_PESANAN + " = ?",
                new String[]{String.valueOf(idPesanan)}
        );

        if (cursorDetail.moveToFirst()) {
            do {
                String namaMenu = cursorDetail.getString(cursorDetail.getColumnIndexOrThrow(COL_DETAIL_NAMA_MENU));
                int jumlah = cursorDetail.getInt(cursorDetail.getColumnIndexOrThrow(COL_DETAIL_JUMLAH));
                int levelPedas = cursorDetail.getInt(cursorDetail.getColumnIndexOrThrow(COL_DETAIL_LEVEL_PEDAS));
                String catatan = cursorDetail.getString(cursorDetail.getColumnIndexOrThrow(COL_DETAIL_CATATAN));
                int subtotal = cursorDetail.getInt(cursorDetail.getColumnIndexOrThrow(COL_DETAIL_SUBTOTAL));

                int hargaSatuan = subtotal;

                if (jumlah > 0) {
                    hargaSatuan = subtotal / jumlah;
                }

                Menu menu = new Menu(0, namaMenu, "Pesanan", "", hargaSatuan);
                CartItem item = new CartItem(menu, jumlah, levelPedas, catatan);

                itemList.add(item);

            } while (cursorDetail.moveToNext());
        }

        cursorDetail.close();

        return itemList;
    }

    public boolean updateStatusPesanan(int idPesanan, String statusBaru) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COL_STATUS_PESANAN, statusBaru);

        int result = db.update(
                TABLE_PESANAN,
                values,
                COL_PESANAN_ID + " = ?",
                new String[]{String.valueOf(idPesanan)}
        );

        db.close();

        return result > 0;
    }

    public void hapusSemuaRiwayat() {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_DETAIL_PESANAN, null, null);
        db.delete(TABLE_PESANAN, null, null);

        db.close();
    }
}