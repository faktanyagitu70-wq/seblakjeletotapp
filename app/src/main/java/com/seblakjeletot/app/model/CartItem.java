package com.seblakjeletot.app.model;

public class CartItem {

    private Menu menu;
    private int jumlah;
    private int levelPedas;
    private String catatan;

    private String suhuMinuman;
    private String ukuranMinuman;

    public CartItem(Menu menu, int jumlah, int levelPedas, String catatan) {
        this.menu = menu;
        this.jumlah = jumlah;
        this.levelPedas = levelPedas;
        this.catatan = catatan;
        this.suhuMinuman = "";
        this.ukuranMinuman = "";
    }

    public CartItem(Menu menu, int jumlah, String suhuMinuman, String ukuranMinuman, String catatan) {
        this.menu = menu;
        this.jumlah = jumlah;
        this.levelPedas = -1;
        this.suhuMinuman = suhuMinuman;
        this.ukuranMinuman = ukuranMinuman;
        this.catatan = catatan;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public int getJumlah() {
        return jumlah;
    }

    public void setJumlah(int jumlah) {
        this.jumlah = jumlah;
    }

    public int getLevelPedas() {
        return levelPedas;
    }

    public void setLevelPedas(int levelPedas) {
        this.levelPedas = levelPedas;
    }

    public String getCatatan() {
        return catatan;
    }

    public void setCatatan(String catatan) {
        this.catatan = catatan;
    }

    public String getSuhuMinuman() {
        return suhuMinuman;
    }

    public void setSuhuMinuman(String suhuMinuman) {
        this.suhuMinuman = suhuMinuman;
    }

    public String getUkuranMinuman() {
        return ukuranMinuman;
    }

    public void setUkuranMinuman(String ukuranMinuman) {
        this.ukuranMinuman = ukuranMinuman;
    }

    public int getSubtotal() {
        return menu.getHarga() * jumlah;
    }

    public boolean isMinuman() {
        if (levelPedas == -1) {
            return true;
        }

        if (menu != null && menu.getKategori() != null) {
            return "Minuman".equalsIgnoreCase(menu.getKategori());
        }

        return false;
    }

    public String getDetailPilihan() {
        if (isMinuman()) {
            if (suhuMinuman != null && !suhuMinuman.trim().isEmpty()
                    && ukuranMinuman != null && !ukuranMinuman.trim().isEmpty()) {
                return "Suhu: " + suhuMinuman + " | Ukuran: " + ukuranMinuman;
            }

            if (catatan != null && catatan.startsWith("Suhu:")) {
                return catatan;
            }

            return "Minuman";
        }

        return "Level Pedas: " + levelPedas;
    }
}