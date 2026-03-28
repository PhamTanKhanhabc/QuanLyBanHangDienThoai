package BUS;

import DAO.HangSanXuatDAO;
import DTO.HangSanXuatDTO;
import java.util.ArrayList;

public class HangSanXuatBUS {

    private final HangSanXuatDAO hsxDAO = new HangSanXuatDAO();
    private ArrayList<HangSanXuatDTO> listHSX = new ArrayList<>();

    public HangSanXuatBUS() {
        listHSX = hsxDAO.selectALL();
    }

    public ArrayList<HangSanXuatDTO> getAll() {
        return listHSX;
    }

    public int generateMaHang() {
        int lastMa = hsxDAO.getLastMaHang();
        if (lastMa == 0) {
            return 1;
        }
        return lastMa + 1;
    }

    public boolean add(HangSanXuatDTO hsx) {
        hsx.setMaHang(generateMaHang());
        boolean check = hsxDAO.insert(hsx) != 0;
        if (check) {
            listHSX.add(hsx);
        }
        return check;
    }

    public boolean delete(HangSanXuatDTO hsx) {
        boolean check = hsxDAO.delete(hsx) != 0;
        if (check) {
            listHSX.remove(hsx);
        }
        return check;
    }

    public boolean update(HangSanXuatDTO hsx) {
        boolean check = hsxDAO.update(hsx) != 0;
        if (check) {
            int index = getIndexById(hsx.getMaHang());
            if (index != -1) {
                listHSX.set(index, hsx);
            }
        }
        return check;
    }

    public int getIndexById(int maHang) {
        for (int i = 0; i < listHSX.size(); i++) {
            if (listHSX.get(i).getMaHang() == maHang) {
                return i;
            }
        }
        return -1;
    }

    public HangSanXuatDTO getByIndex(int index) {
        if (index < 0 || index >= listHSX.size()) {
            return null;
        }
        return listHSX.get(index);
    }

    public ArrayList<HangSanXuatDTO> search(String text) {
        text = text.toLowerCase();
        ArrayList<HangSanXuatDTO> result = new ArrayList<>();

        for (HangSanXuatDTO hsx : listHSX) {
            if (String.valueOf(hsx.getMaHang()).contains(text)
                    || hsx.getTenHang().toLowerCase().contains(text)) {
                result.add(hsx);
            }
        }
        return result;
    }

    public boolean checkDup(String tenHang) {
        for (HangSanXuatDTO hsx : listHSX) {
            if (hsx.getTenHang().equalsIgnoreCase(tenHang.trim())) {
                return false;
            }
        }
        return true;
    }

    public String getTenHang(int maHang) {
        int index = getIndexById(maHang);
        if (index == -1) {
            return null;
        }
        return listHSX.get(index).getTenHang();
    }

    public String[] getArr() {
        String[] result = new String[listHSX.size()];
        for (int i = 0; i < listHSX.size(); i++) {
            result[i] = listHSX.get(i).getTenHang();
        }
        return result;
    }

    public int getMaByTen(String tenHang) {
        for (HangSanXuatDTO h : listHSX) {   // sửa dùng listHSX
            if (h.getTenHang().equalsIgnoreCase(tenHang)) {
                return h.getMaHang();
            }
        }
        return -1;
    }
    public ArrayList<HangSanXuatDTO> search(String text, String type) {
        text = text.toLowerCase();
        ArrayList<HangSanXuatDTO> result = new ArrayList<>();

        for (HangSanXuatDTO hsx : listHSX) {

            switch (type) {

                case "Mã":
                    if (String.valueOf(hsx.getMaHang()).toLowerCase().contains(text)) {
                        result.add(hsx);
                    }
                    break;

                case "Tên":
                    if (hsx.getTenHang().toLowerCase().contains(text)) {
                        result.add(hsx);
                    }
                    break;

                default:
                    if (String.valueOf(hsx.getMaHang()).toLowerCase().contains(text)
                            || hsx.getTenHang().toLowerCase().contains(text)) {
                        result.add(hsx);
                    }
                    break;
            }
        }

        return result;
    }
}