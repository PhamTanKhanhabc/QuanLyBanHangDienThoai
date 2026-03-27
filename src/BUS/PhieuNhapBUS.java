package BUS;

import DAO.PhieuNhapDAO;
import DTO.PhieuNhapDTO;
import java.util.ArrayList;

public class PhieuNhapBUS {

    private final PhieuNhapDAO pnDAO = PhieuNhapDAO.getInstance();
    private ArrayList<PhieuNhapDTO> listPN = new ArrayList<>();

    public PhieuNhapBUS() {
        listPN = pnDAO.selectALL();
    }

    public ArrayList<PhieuNhapDTO> getAll() {
        return listPN;
    }

    public String generateID() {
        String lastID = pnDAO.getLastMaPHN(); 
        
        if (lastID == null || lastID.isEmpty()) {
            return "PN001"; // Nếu Database rỗng hoàn toàn
        }
        
        try {
            // Tách phần số từ chuỗi mã (vd: "PN026" -> 26)
            int currentID = Integer.parseInt(lastID.replaceAll("[^0-9]", ""));
            
            // Trả về mã mới tăng lên 1 (vd: "PN027")
            return String.format("PN%03d", currentID + 1);
        } catch (Exception e) {
            return "PN001";
        }
    }

    public boolean add(PhieuNhapDTO pn) {
        pn.setMaPHN(generateID());

        boolean check = pnDAO.insert(pn) != 0;

        if (check) {
            listPN.add(pn);
        }

        return check;
    }

    public boolean delete(PhieuNhapDTO pn) {
        boolean check = pnDAO.delete(pn) != 0;

        if (check) {
            int index = getIndexById(pn.getMaPHN());
            if (index != -1) {
                listPN.remove(index);
            }
        }

        return check;
    }

    public boolean update(PhieuNhapDTO pn) {
        boolean check = pnDAO.update(pn) != 0;

        if (check) {
            int index = getIndexById(pn.getMaPHN());
            if (index != -1) {
                listPN.set(index, pn);
            }
        }

        return check;
    }

    public int getIndexById(String maPN) {
        for (int i = 0; i < listPN.size(); i++) {
            if (listPN.get(i).getMaPHN().equals(maPN)) {
                return i;
            }
        }
        return -1;
    }

    public PhieuNhapDTO getByIndex(int index) {
        if (index < 0 || index >= listPN.size()) {
            return null;
        }
        return listPN.get(index);
    }

    public ArrayList<PhieuNhapDTO> search(String text, String type) {
        text = text.toLowerCase();
        ArrayList<PhieuNhapDTO> result = new ArrayList<>();
        for (PhieuNhapDTO pn : listPN) {
            switch (type) {
                case "Mã phiếu nhập":
                    if (pn.getMaPHN().toLowerCase().contains(text)) result.add(pn);
                    break;
                case "Mã nhân viên":
                    if (pn.getMaNV().toLowerCase().contains(text)) result.add(pn);
                    break;
                case "Mã nhà cung cấp":
                    if (pn.getMaNCC().toLowerCase().contains(text)) result.add(pn);
                    break;
                default: // "Tất cả"
                    if (pn.getMaPHN().toLowerCase().contains(text)
                     || pn.getMaNV().toLowerCase().contains(text)
                     || pn.getMaNCC().toLowerCase().contains(text)) result.add(pn);
                    break;
            }
        }
        return result;
    }

    public boolean checkDup(String maPN) {
        for (PhieuNhapDTO pn : listPN) {
            if (pn.getMaPHN().equals(maPN)) {
                return false;
            }
        }
        return true;
    }

    public String getMaPN(String maPN) {
        int index = getIndexById(maPN);

        if (index == -1) {
            return null;
        }

        return listPN.get(index).getMaPHN();
    }

    public String[] getArr() {
        String[] result = new String[listPN.size()];

        for (int i = 0; i < listPN.size(); i++) {
            result[i] = listPN.get(i).getMaPHN();
        }

        return result;
    }
    
    public ArrayList<PhieuNhapDTO> getByDate (java.util.Date tuNgay, java.util.Date denNgay){
        ArrayList<PhieuNhapDTO> resultList = new ArrayList<PhieuNhapDTO>();
        ArrayList<PhieuNhapDTO> allList = pnDAO.selectALL();
        if (allList == null) return resultList;
        for (PhieuNhapDTO tmp : allList){
            java.util.Date ngayLap = tmp.getNgay();
            boolean isAfterOrEqualTuNgay = tuNgay == null || !ngayLap.before(tuNgay);
            boolean isBeforeOrEqualDenNgay = denNgay == null || !ngayLap.after(denNgay);

            if (isAfterOrEqualTuNgay && isBeforeOrEqualDenNgay) {
                resultList.add(tmp);
            }
        }
        return resultList;
    }
}