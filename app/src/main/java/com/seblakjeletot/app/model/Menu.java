package com.seblakjeletot.app.model;

public class Menu {

    private int idMenu;
    private String namaMenu;
    private String kategori;
    private String deskripsi;
    private int harga;

    public Menu() {
    }

    public Menu(int idMenu, String namaMenu, String kategori, String deskripsi, int harga) {
        this.idMenu = idMenu;
        this.namaMenu = namaMenu;
        this.kategori = kategori;
        this.deskripsi = deskripsi;
        this.harga = harga;
    }

    public int getIdMenu() {
        return idMenu;
    }

    public void setIdMenu(int idMenu) {
        this.idMenu = idMenu;
    }

    public String getNamaMenu() {
        return namaMenu;
    }

    public void setNamaMenu(String namaMenu) {
        this.namaMenu = namaMenu;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public int getHarga() {
        return harga;
    }

    public void setHarga(int harga) {
        this.harga = harga;
    }
}
