package BUS;

import DAO.HoaDonDAO;
import DTO.HoaDonDTO;
import java.util.ArrayList;

public class HoaDonBUS {

    private final HoaDonDAO hdDAO = HoaDonDAO.getInstance();
    private ArrayList<HoaDonDTO> listHD = new ArrayList<>();

    public HoaDonBUS() {
        listHD = hdDAO.selectALL();
    }

    public ArrayList<HoaDonDTO> getAll() {
        return listHD;
    }

    public String generateMaHD() {

        if (listHD == null || listHD.isEmpty()) {
            return "HD001";
        }

        int max = 0;

        for (HoaDonDTO hd : listHD) {

            try {

                String blocks = hd.getMaHD().substring(2);
                int currentID = Integer.parseInt(blocks);

                if (currentID > max) {
                    max = currentID;
                }

            } catch (Exception e) {
                continue;
            }
        }

        return String.format("HD%03d", max + 1);
    }

    // Thêm
    public boolean add(HoaDonDTO hd) {

        hd.setMaHD(generateMaHD());

        boolean check = hdDAO.insert(hd) != 0;

        if (check) {
            listHD.add(hd);
        }

        return check;
    }

    // Xóa
    public boolean delete(HoaDonDTO hd) {

        boolean check = hdDAO.delete(hd) != 0;

        if (check) {
            listHD.remove(hd);
        }

        return check;
    }

    // Sửa
    public boolean update(HoaDonDTO hd) {

        boolean check = hdDAO.update(hd) != 0;

        if (check) {

            int index = getIndexById(hd.getMaHD());

            if (index != -1) {
                listHD.set(index, hd);
            }
        }

        return check;
    }

    // Tìm index theo mã
    public int getIndexById(String maHD) {

        for (int i = 0; i < listHD.size(); i++) {

            if (listHD.get(i).getMaHD().equals(maHD)) {
                return i;
            }
        }

        return -1;
    }

    // Lấy theo index
    public HoaDonDTO getByIndex(int index) {

        if (index < 0 || index >= listHD.size()) {
            return null;
        }

        return listHD.get(index);
    }

    // Search
    public ArrayList<HoaDonDTO> search(String text) {

        text = text.toLowerCase();
        ArrayList<HoaDonDTO> result = new ArrayList<>();

        for (HoaDonDTO hd : listHD) {

            if (hd.getMaHD().toLowerCase().contains(text)) {
                result.add(hd);
            }
        }

        return result;
    }

    // Kiểm tra trùng
    public boolean checkDup(String maHD) {

        for (HoaDonDTO hd : listHD) {

            if (hd.getMaHD().equalsIgnoreCase(maHD.trim())) {
                return false;
            }
        }

        return true;
    }

    public String getMaHD(String maHD) {

        int index = getIndexById(maHD);

        if (index == -1) {
            return null;
        }

        return listHD.get(index).getMaHD();
    }

    // Mảng cho combobox
    public String[] getArr() {

        String[] result = new String[listHD.size()];

        for (int i = 0; i < listHD.size(); i++) {

            result[i] = listHD.get(i).getMaHD();
        }

        return result;
    }
    public String getTenHoaDon(String maHD) {

        int index = getIndexById(maHD);

        if (index == -1) {
            return null;
        }

        return listHD.get(index).getMaHD();
    }
}