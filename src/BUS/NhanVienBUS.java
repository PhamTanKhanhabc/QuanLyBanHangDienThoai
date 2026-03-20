package BUS;

import DAO.NhanVienDAO;
import DTO.NhanVienDTO;
import java.util.ArrayList;

public class NhanVienBUS {

    private final NhanVienDAO dao = NhanVienDAO.getInstance();
    private ArrayList<NhanVienDTO> list = new ArrayList<>();

    public NhanVienBUS() {
        list = dao.selectALL();
    }

    public ArrayList<NhanVienDTO> getAll() {
        return list;
    }

    public void refresh() {
        list = dao.selectALL();
    }

    public boolean add(NhanVienDTO dto) {
        if (!checkDup(dto.getMaNV())) {
            return false; // trùng mã
        }

        boolean rs = dao.insert(dto) != 0;
        if (rs) {
            list.add(dto);
        }
        return rs;
    }

    public boolean delete(NhanVienDTO dto) {
        boolean rs = dao.delete(dto) != 0;
        if (rs) {
            int index = getIndexById(dto.getMaNV());
            if (index != -1) {
                list.remove(index);
            }
        }
        return rs;
    }

    public boolean update(NhanVienDTO dto) {
        boolean rs = dao.update(dto) != 0;
        if (rs) {
            int index = getIndexById(dto.getMaNV());
            if (index != -1) {
                list.set(index, dto);
            }
        }
        return rs;
    }

    public int getIndexById(String maNV) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getMaNV().equalsIgnoreCase(maNV)) {
                return i;
            }
        }
        return -1;
    }

    public boolean checkDup(String maNV) {
        for (NhanVienDTO dto : list) {
            if (dto.getMaNV().equalsIgnoreCase(maNV)) {
                return false;
            }
        }
        return true;
    }

    public ArrayList<NhanVienDTO> search(String text) {
        text = text.toLowerCase();
        ArrayList<NhanVienDTO> result = new ArrayList<>();

        for (NhanVienDTO dto : list) {
            if (dto.getMaNV().toLowerCase().contains(text)
                    || dto.getHo().toLowerCase().contains(text)
                    || dto.getTen().toLowerCase().contains(text)
                    || dto.getDienThoai().contains(text)
                    || String.valueOf(dto.getLuongThang()).contains(text)) {
                result.add(dto);
            }
        }
        return result;
    }

    public String getHoTen(String maNV) {
        int index = getIndexById(maNV);
        if (index == -1) {
            return null;
        }
        NhanVienDTO nv = list.get(index);
        return nv.getHo() + " " + nv.getTen();
    }

    public String[] getArr() {
        String[] result = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            result[i] = list.get(i).getMaNV() + " - "
                    + list.get(i).getHo() + " "
                    + list.get(i).getTen();
        }
        return result;
    }
    public int getSoLuongNV() {
        return getAll().size();
    }
}
