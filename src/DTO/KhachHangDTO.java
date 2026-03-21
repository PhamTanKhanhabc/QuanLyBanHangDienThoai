/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DTO;
import java.util.Objects;

public class KhachHangDTO {
    private String maKH;
    private String ho;
    private String ten;
    private String diaChi;
    private int trangThai;
    private String soDT;

    // Constructor mặc định
    public KhachHangDTO() {
        maKH = "";
        ho = "";
        ten = "";
        diaChi = "";
        trangThai = 1;
        soDT="";
    }

    // Constructor đầy đủ tham số
    public KhachHangDTO(String maKH, String ho, String ten, String diaChi, int trangThai, String soDT) {
        this.maKH = maKH;
        this.ho = ho;
        this.ten = ten;
        this.diaChi = diaChi;
        this.trangThai = trangThai;
        this.soDT=soDT;
    }

    // Getters và Setters
    public String getMaKH() { return maKH; }
    public void setMaKH(String maKH) { this.maKH = maKH; }

    public String getHo() { return ho; }
    public void setHo(String ho) { this.ho = ho; }

    public String getTen() { return ten; }
    public void setTen(String ten) { this.ten = ten; }

    public String getDiaChi() { return diaChi; }
    public void setDiaChi(String diaChi) { this.diaChi = diaChi; }
    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }
    public String getSoDT(){return soDT;}
    public void setSoDT(String soDT){this.soDT=soDT;}


    // hashCode và equals
    @Override
    public int hashCode() {
        return Objects.hash(maKH);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        KhachHangDTO other = (KhachHangDTO) obj;
        return Objects.equals(maKH, other.maKH);
    }

    // Hàm toString
    @Override
public String toString() {
    return "KhachHangDTO{" +
            "maKH='" + maKH + '\'' +
            ", ho='" + ho + '\'' +
            ", ten='" + ten + '\'' +
            ", diaChi='" + diaChi + '\'' +
            ", soDT='" + soDT + '\'' + // Thêm dòng này
            ", trangThai=" + trangThai +
            '}';
}
}
