package BUS;

import DAO.ChiTietPhieuNhapDAO;
import DTO.ChiTietPhieuNhapDTO;
import java.util.ArrayList;

public class ChiTietPhieuNhapBUS {

    private final ChiTietPhieuNhapDAO ctpnDAO = ChiTietPhieuNhapDAO.getInstance();
    private ArrayList<ChiTietPhieuNhapDTO> listCTPN = new ArrayList<>();

    public ChiTietPhieuNhapBUS() {
        listCTPN = ctpnDAO.selectAll();
    }
    
    public boolean deleteAllByMaPHN(String maPHN) {
        return ctpnDAO.deleteAllByMaPHN(maPHN);
    }

    public ArrayList<ChiTietPhieuNhapDTO> getAll() {
        return listCTPN;
    }

    public ArrayList<ChiTietPhieuNhapDTO> getAllByMaPN(String maPN) {
        ArrayList<ChiTietPhieuNhapDTO> result = new ArrayList<>();
        for (ChiTietPhieuNhapDTO ct : listCTPN) {
            if (ct.getMaPHN().equalsIgnoreCase(maPN)) {
                result.add(ct);
            }
        }
        return result;
    }

    public boolean add(ChiTietPhieuNhapDTO ct) {
        boolean check = ctpnDAO.insert(ct) != 0;
        if (check) {
            listCTPN.add(ct);
        }
        return check;
    }

    public boolean delete(ChiTietPhieuNhapDTO ct) {
        boolean check = ctpnDAO.delete(ct) != 0;
        if (check) {
            listCTPN.remove(ct);
        }
        return check;
    }

    public boolean update(ChiTietPhieuNhapDTO ct) {
        boolean check = ctpnDAO.update(ct) != 0;
        if (check) {
            int index = getIndexById(ct.getMaPHN(), ct.getMaSP());
            if (index != -1) {
                listCTPN.set(index, ct);
            }
        }
        return check;
    }

    public int getIndexById(String maPN, String maSP) {
        for (int i = 0; i < listCTPN.size(); i++) {
            if (listCTPN.get(i).getMaPHN().equalsIgnoreCase(maPN) &&
                listCTPN.get(i).getMaSP().equalsIgnoreCase(maSP)) {
                return i;
            }
        }
        return -1;
    }

    public ChiTietPhieuNhapDTO getByIndex(int index) {
        if (index < 0 || index >= listCTPN.size()) {
            return null;
        }
        return listCTPN.get(index);
    }

    public ArrayList<ChiTietPhieuNhapDTO> search(String text) {
        text = text.toLowerCase();
        ArrayList<ChiTietPhieuNhapDTO> result = new ArrayList<>();
        for (ChiTietPhieuNhapDTO ct : listCTPN) {
            if (ct.getMaPHN().toLowerCase().contains(text)
                    || ct.getMaSP().toLowerCase().contains(text)) {
                result.add(ct);
            }
        }
        return result;
    }

    public boolean checkDup(String maPN, String maSP) {
        for (ChiTietPhieuNhapDTO ct : listCTPN) {
            if (ct.getMaPHN().equalsIgnoreCase(maPN) &&
                ct.getMaSP().equalsIgnoreCase(maSP.trim())) {
                return false;
            }
        }
        return true;
    }

    public String getTen(String maPN, String maSP) {
        int index = getIndexById(maPN, maSP);
        if (index == -1) {
            return null;
        }
        return listCTPN.get(index).getMaPHN() + " - " + listCTPN.get(index).getMaSP();
    }

    public String[] getArr() {
        String[] result = new String[listCTPN.size()];
        for (int i = 0; i < listCTPN.size(); i++) {
            result[i] = listCTPN.get(i).getMaPHN()
                    + "-"
                    + listCTPN.get(i).getMaSP();
        }
        return result;
    }
}