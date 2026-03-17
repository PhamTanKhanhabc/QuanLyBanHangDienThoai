package BUS;

import DAO.NhaCungCapDAO;
import DTO.NhaCungCapDTO;
import java.util.ArrayList;

public class NhaCungCapBUS {

    private final NhaCungCapDAO nccDAO = NhaCungCapDAO.getInstance();
    private ArrayList<NhaCungCapDTO> listNCC = new ArrayList<>();

    public NhaCungCapBUS() {
        listNCC = nccDAO.selectALL();
    }

    public ArrayList<NhaCungCapDTO> getAll() {
        return listNCC;
    }

    public String generateID() {
        if (listNCC.isEmpty()) {
            return "NCC01";
        }

        int maxID = 0;

        for (NhaCungCapDTO ncc : listNCC) {
            try {
                int currentID = Integer.parseInt(ncc.getMaNCC().replaceAll("[^0-9]", ""));
                if (currentID > maxID) {
                    maxID = currentID;
                }
            } catch (Exception e) {
            }
        }

        return String.format("NCC%02d", maxID + 1);
    }

    public boolean add(NhaCungCapDTO ncc) {
        ncc.setMaNCC(generateID());
        boolean check = nccDAO.insert(ncc) != 0;

        if (check) {
            listNCC.add(ncc);
        }

        return check;
    }

    public boolean delete(NhaCungCapDTO ncc) {
        boolean check = nccDAO.delete(ncc) != 0;

        if (check) {
            int index = getIndexById(ncc.getMaNCC());
            if (index != -1) {
                listNCC.remove(index);
            }
        }

        return check;
    }

    public boolean update(NhaCungCapDTO ncc) {
        boolean check = nccDAO.update(ncc) != 0;

        if (check) {
            int index = getIndexById(ncc.getMaNCC());
            if (index != -1) {
                listNCC.set(index, ncc);
            }
        }

        return check;
    }

    public int getIndexById(String maNCC) {
        for (int i = 0; i < listNCC.size(); i++) {
            if (listNCC.get(i).getMaNCC().equals(maNCC)) {
                return i;
            }
        }

        return -1;
    }

    public NhaCungCapDTO getByIndex(int index) {
        if (index < 0 || index >= listNCC.size()) {
            return null;
        }

        return listNCC.get(index);
    }

    public ArrayList<NhaCungCapDTO> search(String text) {
        text = text.toLowerCase();
        ArrayList<NhaCungCapDTO> result = new ArrayList<>();

        for (NhaCungCapDTO ncc : listNCC) {
            if (ncc.getMaNCC().toLowerCase().contains(text)
                    || ncc.getTenNCC().toLowerCase().contains(text)
                    || ncc.getSDT().contains(text)) {
                result.add(ncc);
            }
        }

        return result;
    }

    public boolean checkDup(String tenNCC) {
        for (NhaCungCapDTO ncc : listNCC) {
            if (ncc.getTenNCC().equalsIgnoreCase(tenNCC.trim())) {
                return false;
            }
        }

        return true;
    }

    public String getTenNCC(String maNCC) {
        int index = getIndexById(maNCC);

        if (index == -1) {
            return null;
        }

        return listNCC.get(index).getTenNCC();
    }

    public String[] getArr() {
        String[] result = new String[listNCC.size()];

        for (int i = 0; i < listNCC.size(); i++) {
            result[i] = listNCC.get(i).getTenNCC();
        }

        return result;
    }
}