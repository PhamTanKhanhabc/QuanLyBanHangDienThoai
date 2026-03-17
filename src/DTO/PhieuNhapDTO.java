package DTO;

import java.sql.Timestamp;
import java.util.Objects;

public class PhieuNhapDTO {
    private String maPHN;
    private String maNV;
    private String maNCC;
    private Timestamp ngay;
    private double tongTien;
    private int trangThai;

    public PhieuNhapDTO() {
        this.trangThai = 1;
    }

    public PhieuNhapDTO(String maPHN, String maNV, String maNCC,
                        Timestamp ngay, double tongTien, int trangThai) {
        this.maPHN = maPHN;
        this.maNV = maNV;
        this.maNCC = maNCC;
        this.ngay = ngay;
        this.tongTien = tongTien;
        this.trangThai = trangThai;
    }

    public String getMaPHN() {
        return maPHN;
    }

    public String getMaNV() {
        return maNV;
    }

    public String getMaNCC() {
        return maNCC;
    }

    public Timestamp getNgay() {
        return ngay;
    }

    public double getTongTien() {
        return tongTien;
    }

    public void setMaPHN(String maPHN) {
        this.maPHN = maPHN;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public void setMaNCC(String maNCC) {
        this.maNCC = maNCC;
    }

    public void setNgay(Timestamp ngay) {
        this.ngay = ngay;
    }

    public void setTongTien(double tongTien) {
        this.tongTien = tongTien;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PhieuNhapDTO phieuNhap = (PhieuNhapDTO) o;
        return Objects.equals(maPHN, phieuNhap.maPHN);
    }

    @Override
    public int hashCode() {
        return Objects.hash(maPHN);
    }

    @Override
    public String toString() {
        return "PhieuNhapDTO{" +
                "maPHN='" + maPHN + '\'' +
                ", maNV='" + maNV + '\'' +
                ", maNCC='" + maNCC + '\'' +
                ", ngay=" + ngay +
                ", tongTien=" + tongTien +
                ", trangThai=" + trangThai +
                '}';
    }
}