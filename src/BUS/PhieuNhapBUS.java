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
        if (listPN.isEmpty()) {
            return "PN101";
        }

        int maxID = 0;

        for (PhieuNhapDTO pn : listPN) {
            try {
                int currentID = Integer.parseInt(pn.getMaPHN().replaceAll("[^0-9]", ""));
                if (currentID > maxID) {
                    maxID = currentID;
                }
            } catch (Exception e) {
            }
        }

        return String.format("PN%03d", maxID + 1);
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

    public ArrayList<PhieuNhapDTO> search(String text) {
        text = text.toLowerCase();
        ArrayList<PhieuNhapDTO> result = new ArrayList<>();

        for (PhieuNhapDTO pn : listPN) {
            if (pn.getMaPHN().toLowerCase().contains(text)
                    || pn.getMaNCC().toLowerCase().contains(text)) {
                result.add(pn);
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
}