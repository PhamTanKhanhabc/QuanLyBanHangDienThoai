package BUS;

import DAO.ChuongTrinhKhuyenMaiDAO;
import DTO.ChuongTrinhKhuyenMaiDTO;
import java.util.ArrayList;

public class ChuongTrinhKhuyenMaiBUS {

    private final ChuongTrinhKhuyenMaiDAO dao = new ChuongTrinhKhuyenMaiDAO();
    private ArrayList<ChuongTrinhKhuyenMaiDTO> listCTKM = new ArrayList<>();

    public ChuongTrinhKhuyenMaiBUS() {
        listCTKM = dao.selectALL();
    }

    public ArrayList<ChuongTrinhKhuyenMaiDTO> getAll() {
        listCTKM = dao.selectALL();
        return listCTKM;
    }

    public String generateMaCTKM() {
        String lastMa = dao.getLastMaCTKM();
        if (lastMa == null || lastMa.trim().isEmpty()) {
            return "CTKM01";
    }

        String soStr = lastMa.replaceAll("\\D+", "");
        int so = soStr.isEmpty() ? 0 : Integer.parseInt(soStr);
           return String.format("CTKM%02d", so + 1);
    }

    public boolean add(ChuongTrinhKhuyenMaiDTO t) {
        t.setMaCTKM(generateMaCTKM());
        boolean check = dao.insert(t) != 0;
        if (check) {
            listCTKM.add(t);
        }
        return check;
    }

    public boolean delete(ChuongTrinhKhuyenMaiDTO t) {
        boolean check = dao.delete(t) != 0;
        if (check) {
            listCTKM.remove(t);
        }
        return check;
    }

    public boolean update(ChuongTrinhKhuyenMaiDTO t) {
        boolean check = dao.update(t) != 0;
        if (check) {
            int index = getIndexById(t.getMaCTKM());
            if (index != -1) {
                listCTKM.set(index, t);
            }
        }
        return check;
    }

    public int getIndexById(String maCTKM) {
        for (int i = 0; i < listCTKM.size(); i++) {
            if (listCTKM.get(i).getMaCTKM().equals(maCTKM)) {
                return i;
            }
        }
        return -1;
    }

    public ChuongTrinhKhuyenMaiDTO getByIndex(int index) {
        if (index < 0 || index >= listCTKM.size()) {
            return null;
        }
        return listCTKM.get(index);
    }

    public ArrayList<ChuongTrinhKhuyenMaiDTO> search(String text) {
        text = text.toLowerCase();
        ArrayList<ChuongTrinhKhuyenMaiDTO> result = new ArrayList<>();

        for (ChuongTrinhKhuyenMaiDTO t : listCTKM) {
            if (t.getMaCTKM().toLowerCase().contains(text)
                    || t.getTenCTKM().toLowerCase().contains(text)) {
                result.add(t);
            }
        }

        return result;
    }

    public boolean checkDup(String tenCTKM) {
        for (ChuongTrinhKhuyenMaiDTO t : listCTKM) {
            if (t.getTenCTKM().equalsIgnoreCase(tenCTKM.trim())) {
                return false;
            }
        }
        return true;
    }

    public String getTenCTKM(String maCTKM) {
        int index = getIndexById(maCTKM);
        if (index == -1) {
            return null;
        }
        return listCTKM.get(index).getTenCTKM();
    }

    public String[] getArr() {
        String[] result = new String[listCTKM.size()];
        for (int i = 0; i < listCTKM.size(); i++) {
            result[i] = listCTKM.get(i).getTenCTKM();
        }
        return result;
    }

    public ArrayList<ChuongTrinhKhuyenMaiDTO> search(String text, String type) {
        text = text.toLowerCase();
        ArrayList<ChuongTrinhKhuyenMaiDTO> result = new ArrayList<>();

        for (ChuongTrinhKhuyenMaiDTO t : listCTKM) {

            switch (type) {

                case "Mã":
                    if (t.getMaCTKM().toLowerCase().contains(text)) {
                        result.add(t);
                    }
                    break;

                case "Tên":
                    if (t.getTenCTKM().toLowerCase().contains(text)) {
                        result.add(t);
                    }
                    break;

                default:
                    if (t.getMaCTKM().toLowerCase().contains(text)
                            || t.getTenCTKM().toLowerCase().contains(text)) {
                        result.add(t);
                    }
                    break;
            }
        }

        return result;
    }
}
