package DTO;

import java.util.Objects;

public class ChiTietPhieuNhapDTO {
    private String maPHN;
    private String maSP;
    private int soLuong;
    private double donGia;
    private double thanhTien;

    public ChiTietPhieuNhapDTO() {
    }

    public ChiTietPhieuNhapDTO(String maPHN, String maSP, int soLuong, double donGia, double thanhTien) {
        this.maPHN = maPHN;
        this.maSP = maSP;
        this.soLuong = soLuong;
        this.donGia = donGia;
        this.thanhTien = thanhTien;
    }

    public String getMaPHN() {
        return maPHN;
    }

    public String getMaSP() {
        return maSP;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public double getDonGia() {
        return donGia;
    }

    public double getThanhTien() {
        return thanhTien;
    }

    public void setMaPHN(String maPHN) {
        this.maPHN = maPHN;
    }

    public void setMaSP(String maSP) {
        this.maSP = maSP;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public void setDonGia(double donGia) {
        this.donGia = donGia;
    }

    public void setThanhTien(double thanhTien) {
        this.thanhTien = thanhTien;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChiTietPhieuNhapDTO that = (ChiTietPhieuNhapDTO) o;
        return Objects.equals(maPHN, that.maPHN) && Objects.equals(maSP, that.maSP);
    }

    @Override
    public int hashCode() {
        return Objects.hash(maPHN, maSP);
    }

    @Override
    public String toString() {
        return "ChiTietPhieuNhap {" +
                "maPHN='" + maPHN + '\'' +
                ", maSP='" + maSP + '\'' +
                ", soLuong=" + soLuong +
                ", donGia=" + donGia +
                ", thanhTien=" + thanhTien +
                '}';
    }
}