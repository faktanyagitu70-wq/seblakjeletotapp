package com.seblakjeletot.app.model;

import java.util.ArrayList;

public class Pesanan {

    private int idPesanan;
    private String namaCustomer;
    private String noHp;
    private String alamat;
    private String metodePengambilan;
    private String metodePembayaran;
    private int totalHarga;
    private ArrayList<CartItem> items;
    private String statusPesanan;
    private String tanggal;

    public Pesanan() {
    }

    public Pesanan(String namaCustomer,
                   String noHp,
                   String alamat,
                   String metodePengambilan,
                   String metodePembayaran,
                   int totalHarga,
                   ArrayList<CartItem> items,
                   String statusPesanan,
                   String tanggal) {

        this.namaCustomer = namaCustomer;
        this.noHp = noHp;
        this.alamat = alamat;
        this.metodePengambilan = metodePengambilan;
        this.metodePembayaran = metodePembayaran;
        this.totalHarga = totalHarga;
        this.items = items;
        this.statusPesanan = statusPesanan;
        this.tanggal = tanggal;
    }

    public int getIdPesanan() {
        return idPesanan;
    }

    public void setIdPesanan(int idPesanan) {
        this.idPesanan = idPesanan;
    }

    public String getNamaCustomer() {
        return namaCustomer;
    }

    public void setNamaCustomer(String namaCustomer) {
        this.namaCustomer = namaCustomer;
    }

    public String getNoHp() {
        return noHp;
    }

    public void setNoHp(String noHp) {
        this.noHp = noHp;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getMetodePengambilan() {
        return metodePengambilan;
    }

    public void setMetodePengambilan(String metodePengambilan) {
        this.metodePengambilan = metodePengambilan;
    }

    public String getMetodePembayaran() {
        return metodePembayaran;
    }

    public void setMetodePembayaran(String metodePembayaran) {
        this.metodePembayaran = metodePembayaran;
    }

    public int getTotalHarga() {
        return totalHarga;
    }

    public void setTotalHarga(int totalHarga) {
        this.totalHarga = totalHarga;
    }

    public ArrayList<CartItem> getItems() {
        return items;
    }

    public void setItems(ArrayList<CartItem> items) {
        this.items = items;
    }

    public String getStatusPesanan() {
        return statusPesanan;
    }

    public void setStatusPesanan(String statusPesanan) {
        this.statusPesanan = statusPesanan;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }
}