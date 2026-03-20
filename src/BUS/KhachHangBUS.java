package BUS;

import DAO.KhachHangDAO;
import DTO.KhachHangDTO;
import java.util.ArrayList;

public class KhachHangBUS {

    private final KhachHangDAO khDAO = KhachHangDAO.getInstance();
    private ArrayList<KhachHangDTO> listKH = new ArrayList<>();

    public KhachHangBUS() {
        listKH = khDAO.selectALL();
    }

    public ArrayList<KhachHangDTO> getAll() {
        return listKH;
    }

    // Thêm
    public boolean add(KhachHangDTO kh) {

        if (!checkDup(kh.getMaKH())) {
            return false;
        }

        boolean check = khDAO.insert(kh) != 0;

        if (check) {
            listKH.add(kh);
        }

        return check;
    }

    // Xóa
    public boolean delete(KhachHangDTO kh) {

        boolean check = khDAO.delete(kh) != 0;

        if (check) {
            listKH.remove(kh);
        }

        return check;
    }

    // Sửa
    public boolean update(KhachHangDTO kh) {

        boolean check = khDAO.update(kh) != 0;

        if (check) {

            int index = getIndexById(kh.getMaKH());

            if (index != -1) {
                listKH.set(index, kh);
            }
        }

        return check;
    }

    // Tìm index theo mã
    public int getIndexById(String maKH) {

        for (int i = 0; i < listKH.size(); i++) {

            if (listKH.get(i).getMaKH().equalsIgnoreCase(maKH)) {
                return i;
            }
        }

        return -1;
    }

    // Lấy theo index
    public KhachHangDTO getByIndex(int index) {

        if (index < 0 || index >= listKH.size()) {
            return null;
        }

        return listKH.get(index);
    }

    // Search
    public ArrayList<KhachHangDTO> search(String text) {

        text = text.toLowerCase();

        ArrayList<KhachHangDTO> result = new ArrayList<>();

        for (KhachHangDTO kh : listKH) {

            if (kh.getMaKH().toLowerCase().contains(text)
                    || kh.getHo().toLowerCase().contains(text)
                    || kh.getTen().toLowerCase().contains(text)
                    || kh.getDiaChi().toLowerCase().contains(text)) {

                result.add(kh);
            }
        }

        return result;
    }

    // Kiểm tra trùng
    public boolean checkDup(String maKH) {

        for (KhachHangDTO kh : listKH) {

            if (kh.getMaKH().equalsIgnoreCase(maKH.trim())) {
                return false;
            }
        }

        return true;
    }

    // Lấy tên khách hàng
    public String getTen(String maKH) {

        int index = getIndexById(maKH);

        if (index == -1) {
            return null;
        }

        KhachHangDTO kh = listKH.get(index);

        return kh.getHo() + " " + kh.getTen();
    }

    // Lấy object theo mã
    public KhachHangDTO getById(String maKH) {

        int index = getIndexById(maKH);

        if (index == -1) {
            return null;
        }

        return listKH.get(index);
    }

    // Mảng cho combobox
    public String[] getArr() {

        String[] result = new String[listKH.size()];

        for (int i = 0; i < listKH.size(); i++) {

            KhachHangDTO kh = listKH.get(i);

            result[i] = kh.getMaKH()
                    + " - "
                    + kh.getHo()
                    + " "
                    + kh.getTen();
        }

        return result;
    }
    public int getSoLuongKH() {
        return getAll().size();
    }
}
