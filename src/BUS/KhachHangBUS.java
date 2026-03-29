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
    public String add(KhachHangDTO kh) {
        // 1. Kiểm tra dữ liệu rỗng / sai định dạng
        String error = validate(kh);
        if (!error.isEmpty()) {
            return error; // Báo lỗi ngay, không thêm vào DB
        }

        // 2. Kiểm tra trùng mã
        if (!checkDup(kh.getMaKH())) {
            return "Lỗi: Mã khách hàng đã tồn tại!";
        }

        // 3. Thực hiện thêm
        boolean check = khDAO.insert(kh) != 0;
        if (check) {
            listKH.add(kh);
            return "Thêm thành công!";
        }
        return "Lỗi cơ sở dữ liệu!";
    }

    // Xóa
   public String delete(KhachHangDTO kh) {
        // Kiểm tra an toàn
        if (kh == null || kh.getMaKH() == null || kh.getMaKH().trim().isEmpty()) {
            return "Lỗi: Không xác định được khách hàng cần xóa!";
        }

        // Thực hiện xóa dưới Database (chuyển TrangThai = 0)
        boolean check = khDAO.delete(kh) != 0;
        
        if (check) {
            // Xóa phần tử khỏi mảng trên RAM (nhờ hàm equals trong DTO nên Java tự biết xóa dòng nào)
            listKH.remove(kh);
            return "Xóa khách hàng thành công!";
        }
        
        return "Lỗi: Xóa thất bại (Có thể mã khách hàng không tồn tại)!";
    }

    // Sửa
   public String update(KhachHangDTO kh) {
        // 1. Kiểm tra dữ liệu rỗng / sai định dạng
        String error = validate(kh);
        if (!error.isEmpty()) {
            return error;
        }

        // 2. Thực hiện cập nhật
        boolean check = khDAO.update(kh) != 0;
        if (check) {
            int index = getIndexById(kh.getMaKH());
            if (index != -1) {
                listKH.set(index, kh);
            }
            return "Cập nhật thành công!";
        }
        return "Lỗi cập nhật hoặc mã KH không tồn tại!";
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

        text = text.toLowerCase().trim();

        ArrayList<KhachHangDTO> result = new ArrayList<>();

        for (KhachHangDTO kh : listKH) {

            if (kh.getMaKH().toLowerCase().contains(text)
                    || kh.getHo().toLowerCase().contains(text)
                    || kh.getTen().toLowerCase().contains(text)
                    || kh.getDiaChi().toLowerCase().contains(text)
                    || kh.getSoDT().toLowerCase().contains(text)) {

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
                    + kh.getTen()
                    +"-"
                    + kh.getSoDT();
        }

        return result;
    }
    public String getMaByInfo(String info) {
    if (info == null || info.trim().isEmpty()) {
        return null;
    }
    
    String searchInfo = info.trim().toLowerCase();

    for (KhachHangDTO kh : listKH) {
        String hoTen = (kh.getHo() + " " + kh.getTen()).toLowerCase();
        // Kiểm tra nếu thông tin nhập vào khớp với Họ Tên hoặc đúng là Mã KH
        if (hoTen.equals(searchInfo) || kh.getMaKH().toLowerCase().equals(searchInfo) || kh.getSoDT().equals(searchInfo)) {
            return kh.getMaKH();
        }
    }
    return null; // Không tìm thấy
}
    public String validate(KhachHangDTO kh){
        if(kh.getMaKH()==null || kh.getMaKH().trim().isEmpty()){
            return "Loi:Ma khach hang khong duoc de trong";}
        if(kh.getHo()==null|kh.getHo().trim().isEmpty()){
            return "Loi: Ho khach hang khong duoc de trong";}
        if(kh.getTen()==null||kh.getTen().trim().isEmpty()){
            return "Loi: Ten khach hang khong duoc de trong";}
        if(kh.getDiaChi()==null || kh.getDiaChi().trim().isEmpty()){
            kh.setDiaChi("Chua cung cap");}
        if(kh.getSoDT()==null || kh.getSoDT().trim().isEmpty()){
            return "Loi: So dien thoai khong duoc de trong ";
        }
        
        String phoneRegex ="^0\\d{9}$";
        if(!kh.getSoDT().trim().matches(phoneRegex)){
            return "Loi: Nhap so dien thoai hop le";
        }
        return "";
    }
    public String getNextID() {
    String lastID = khDAO.getLastMaKH(); // Lấy mã cuối cùng thực tế trong DB
    if (lastID == null || lastID.trim().isEmpty()) {
        return "KH001";
    }
    // Ví dụ: KH006 -> lấy số 6, tăng lên thành 7
    int number = Integer.parseInt(lastID.substring(2));
    return String.format("KH%03d", number + 1); // Trả về KH007
}
    public int getSoLuongKH() {
        return getAll().size();
    }
    // Hàm sinh mã Khách hàng tự động (Nằm trong KhachHangBUS)
    public String generateMaKH() {
        // Giả sử danh sách khách hàng của bạn tên là listKH
        if (listKH == null || listKH.isEmpty()) {
            return "KH001";
        }

        int max = 0;

        for (DTO.KhachHangDTO kh : listKH) {
            try {
                // Cắt bỏ 2 chữ "KH" đầu tiên để lấy phần số
                String blocks = kh.getMaKH().substring(2);
                int currentID = Integer.parseInt(blocks);

                if (currentID > max) {
                    max = currentID;
                }
            } catch (Exception e) {
                continue;
            }
        }

        return String.format("KH%03d", max + 1);
    }
}