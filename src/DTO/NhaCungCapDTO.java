package DTO;

import java.util.Objects;

public class NhaCungCapDTO {
    private String maNCC;
    private String tenNCC;
    private String sdt;
    private String diaChi;
    private int trangThai;

    public NhaCungCapDTO(){
        this.trangThai = 1;
    }

    public NhaCungCapDTO(String maNCC, String tenNCC, String sdt, String diaChi, int trangThai){
        this.maNCC = maNCC;
        this.tenNCC = tenNCC;
        this.sdt = sdt;
        this.diaChi = diaChi;
        this.trangThai = trangThai;
    }

    //getter
    public String getMaNCC(){
        return this.maNCC;
    }

    public String getTenNCC(){
        return this.tenNCC;
    }

    public String getSDT(){
        return this.sdt;
    }

    public String getDiaChi(){
        return this.diaChi;
    }

    //setter
    public void setMaNCC(String maNCC){
        this.maNCC = maNCC;
    }

    public void setTenNCC(String tenNCC){
        this.tenNCC = tenNCC;
    }

    public void setSDT(String sdt){
        this.sdt = sdt;
    }

    public void setDiaChi(String diaChi){
        this.diaChi = diaChi;
    }
    
    public int getTrangThai(){
        return trangThai;
    }

    public void setTrangThai(int trangThai){
        this.trangThai = trangThai;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NhaCungCapDTO that = (NhaCungCapDTO) o;

        return Objects.equals(maNCC, that.maNCC);
    }

    @Override
    public int hashCode() {
        return Objects.hash(maNCC);
    }

    @Override
    public String toString(){
        return "NhaCungCapDTO{" +
                "maNCC='" + maNCC + '\'' +
                ", tenNCC='" + tenNCC + '\'' +
                ", sdt='" + sdt + '\'' +
                ", diaChi='" + diaChi + '\'' +
                ", trangThai=" + trangThai +
                '}';
    }
}