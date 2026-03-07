package BUS;

import DAO.ChiTietHoaDonDAO;
import DTO.ChiTietHoaDonDTO;
import java.util.ArrayList;

public class ChiTietHoaDonBUS {

    private final ChiTietHoaDonDAO cthdDAO = ChiTietHoaDonDAO.getInstance();
    private ArrayList<ChiTietHoaDonDTO> listCTHD = new ArrayList<>();

    public ChiTietHoaDonBUS() {
        listCTHD = cthdDAO.selectAll();
    }

    public ArrayList<ChiTietHoaDonDTO> getAll() {
        return listCTHD;
    }

    // Lấy chi tiết theo mã hóa đơn
    public ArrayList<ChiTietHoaDonDTO> getAllByMaHD(String maHD) {
        ArrayList<ChiTietHoaDonDTO> result = new ArrayList<>();

        for (ChiTietHoaDonDTO cthd : listCTHD) {
            if (cthd.getMaHD().equalsIgnoreCase(maHD)) {
                result.add(cthd);
            }
        }

        return result;
    }

    // Thêm
    public boolean add(ChiTietHoaDonDTO cthd) {

        cthd.setThanhTien(cthd.getSoLuong() * cthd.getDonGia());

        boolean check = cthdDAO.insert(cthd) != 0;

        if (check) {
            listCTHD.add(cthd);
        }

        return check;
    }

    // Xóa
    public boolean delete(ChiTietHoaDonDTO cthd) {

        boolean check = cthdDAO.delete(cthd) != 0;

        if (check) {
            listCTHD.remove(cthd);
        }

        return check;
    }

    // Sửa
    public boolean update(ChiTietHoaDonDTO cthd) {

        cthd.setThanhTien(cthd.getSoLuong() * cthd.getDonGia());

        boolean check = cthdDAO.update(cthd) != 0;

        if (check) {

            int index = getIndexById(cthd.getMaHD(), cthd.getMaSP());

            if (index != -1) {
                listCTHD.set(index, cthd);
            }
        }

        return check;
    }

    // Tìm index theo khóa kép
    public int getIndexById(String maHD, String maSP) {

        for (int i = 0; i < listCTHD.size(); i++) {

            if (listCTHD.get(i).getMaHD().equalsIgnoreCase(maHD)
                    && listCTHD.get(i).getMaSP().equalsIgnoreCase(maSP)) {

                return i;
            }
        }

        return -1;
    }

    public ChiTietHoaDonDTO getByIndex(int index) {

        if (index < 0 || index >= listCTHD.size()) {
            return null;
        }

        return listCTHD.get(index);
    }

    // Search
    public ArrayList<ChiTietHoaDonDTO> search(String text) {

        text = text.toLowerCase();
        ArrayList<ChiTietHoaDonDTO> result = new ArrayList<>();

        for (ChiTietHoaDonDTO cthd : listCTHD) {

            if (cthd.getMaHD().toLowerCase().contains(text)
                    || cthd.getMaSP().toLowerCase().contains(text)) {

                result.add(cthd);
            }
        }

        return result;
    }

    // Kiểm tra trùng sản phẩm trong hóa đơn
    public boolean checkDup(String maHD, String maSP) {

        for (ChiTietHoaDonDTO cthd : listCTHD) {

            if (cthd.getMaHD().equalsIgnoreCase(maHD.trim())
                    && cthd.getMaSP().equalsIgnoreCase(maSP.trim())) {

                return false;
            }
        }

        return true;
    }

    // Lấy tên hiển thị
    public String getTen(String maHD, String maSP) {

        int index = getIndexById(maHD, maSP);

        if (index == -1) {
            return null;
        }

        return listCTHD.get(index).getMaHD() + " - " + listCTHD.get(index).getMaSP();
    }

    // Mảng cho combobox
    public String[] getArr() {

        String[] result = new String[listCTHD.size()];

        for (int i = 0; i < listCTHD.size(); i++) {

            result[i] = listCTHD.get(i).getMaHD() + "-" + listCTHD.get(i).getMaSP();
        }

        return result;
    }
}