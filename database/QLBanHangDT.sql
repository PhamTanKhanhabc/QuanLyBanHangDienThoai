CREATE DATABASE QLBanHangDT;
GO
USE QLBanHangDT;
GO

CREATE TABLE HangSanXuat (
    MaHang INT NOT NULL,
    TenHang NVARCHAR(255) NOT NULL,
    DiaChi NVARCHAR(255),
    TrangThai BIT DEFAULT 1
);
INSERT INTO HangSanXuat (MaHang, TenHang, DiaChi, TrangThai) VALUES
(1, N'Apple', N'Mỹ', 1),
(2, N'Samsung', N'Hàn Quốc', 1),
(3, N'Xiaomi', N'Trung Quốc', 1),
(4, N'Oppo', N'Trung Quốc', 1),
(5, N'Sony', N'Nhật Bản', 1),
(6, N'Asus', N'Đài Loan', 1),
(7, N'Lenovo', N'Trung Quốc', 1),
(8, N'Huawei', N'Trung Quốc', 1),
(9, N'Vivo', N'Trung Quốc', 1),
(10, N'Nokia', N'Phần Lan', 1),
(11, N'Realme', N'Trung Quốc', 1),
(12, N'Vsmart', N'Việt Nam', 1),
(13, N'Bphone', N'Việt Nam', 1);

CREATE TABLE LoaiSanPham (
    MaLoai INT NOT NULL,
    TenLoai NVARCHAR(255) NOT NULL,
    TrangThai BIT DEFAULT 1
);
INSERT INTO LoaiSanPham (MaLoai, TenLoai, TrangThai) VALUES
(1, N'Điện thoại', 1),
(2, N'Đồng hồ thông minh', 1),
(3, N'Máy tính bảng', 1),
(4, N'Phụ Kiện', 1);

CREATE TABLE VaiTro (
    MaVaiTro VARCHAR(20) NOT NULL,
    TenVaiTro NVARCHAR(50),
    TrangThai BIT DEFAULT 1
);
INSERT INTO VaiTro (MaVaiTro, TenVaiTro, TrangThai) VALUES
('VT01', N'Quản lý', 1),
('VT02', N'Nhân viên', 1),
('VT03', N'Kế toán', 1);

CREATE TABLE NhanVien (
    MaNV VARCHAR(20) NOT NULL,
    Ho NVARCHAR(50),
    Ten NVARCHAR(20),
    NgaySinh DATE,
    DiaChi NVARCHAR(100),
    DienThoai NVARCHAR(10),
    LuongThang DECIMAL(18, 2),
    TrangThai BIT DEFAULT 1
);
INSERT INTO NhanVien 
(MaNV, Ho, Ten, NgaySinh, DiaChi, DienThoai, LuongThang, TrangThai) 
VALUES
('NV01', N'Nguyễn Văn', N'An', '1998-05-12', N'Hà Nội', '0901234567', 12000000, 1),
('NV02', N'Trần Minh', N'Bình', '1995-08-20', N'Hồ Chí Minh', '0912345678', 15000000, 1),
('NV03', N'Lê Thị Ngọc', N'Chi', '2000-03-15', N'Đà Nẵng', '0923456789', 10000000, 1);

CREATE TABLE KhachHang(
	MaKH varchar(20) NOT NULL,
	Ho NVARCHAR(50),
	Ten NVARCHAR(50),
	DiaChi NVARCHAR(200),
    TrangThai BIT DEFAULT 1
);
INSERT INTO KhachHang (MaKH, Ho, Ten, DiaChi, TrangThai)
VALUES 
('KH001', N'Nguyễn Hoàng', N'Nam', N'Quận 1, TP.HCM', 1),
('KH002', N'Lê Thị Ngọc', N'Mai', N'Quận 3, TP.HCM', 1),
('KH003', N'Trần Anh', N'Tuấn', N'Quận Tân Bình, TP.HCM', 1),
('KH004', N'Phạm Minh', N'Đức', N'Quận Đống Đa, Hà Nội', 1),
('KH005', N'Vũ Thị', N'Lan', N'Quận Hai Bà Trưng, Hà Nội', 1);

CREATE TABLE NhaCungCap(
    maNCC VARCHAR(20) NOT NULL,
    tenNCC NVARCHAR(50),
    sdt VARCHAR(20),
    diachi NVARCHAR(255),
    TrangThai BIT DEFAULT 1
);
INSERT INTO NhaCungCap (maNCC, tenNCC, sdt, diachi, TrangThai) VALUES 
('NCC01', N'Công Ty Samsung Vina', '02839157310', N'Số 2, Hải Triều, Q.1, TP.HCM', 1),
('NCC02', N'Apple Việt Nam LLC', '18001127', N'Deutsches Haus, Lê Duẩn, Q.1, TP.HCM', 1),
('NCC03', N'Nhà Phân Phối Digiworld', '02839290059', N'195 Điện Biên Phủ, Q.3, TP.HCM', 1),
('NCC04', N'OPPO Việt Nam', '1800577776', N'Tòa nhà E-Town, Tân Bình, TP.HCM', 1),
('NCC05', N'Phân Phối FPT Trading', '02473006666', N'Duy Tân, Cầu Giấy, Hà Nội', 1);

CREATE TABLE SanPham (
    MaSp VARCHAR(20) NOT NULL,
    TenSp NVARCHAR(255) NOT NULL,
    HinhAnh VARCHAR(255) DEFAULT NULL,
    SoLuongTon INT DEFAULT 0,
    DonGia DECIMAL(18,2) NOT NULL,
    DonViTinh NVARCHAR(50),
    MaLoai INT NOT NULL,
    MaHang INT NOT NULL,
    TrangThai BIT DEFAULT 1
);
INSERT INTO SanPham 
(MaSp, TenSp, HinhAnh, SoLuongTon, DonGia, DonViTinh, MaLoai, MaHang, TrangThai) 
VALUES
('SP001', N'iPhone 14 Pro Max', 'iphone14promax.jpg', 15, 29990000, N'Chiếc', 1, 1, 1),
('SP002', N'Samsung Galaxy S23 Ultra', 's23ultra.jpg', 20, 26990000, N'Chiếc', 1, 2, 1),
('SP003', N'Xiaomi Redmi Note 13', 'redminote13.jpg', 35, 5990000, N'Chiếc', 1, 3, 1),
('SP004', N'Oppo Reno 10', 'opporeno10.jpg', 25, 8990000, N'Chiếc', 1, 4, 1),
('SP005', N'Apple Watch Series 9', 'applewatch9.jpg', 18, 11990000, N'Chiếc', 2, 1, 1),
('SP006', N'Samsung Galaxy Watch 6', 'galaxywatch6.jpg', 22, 7990000, N'Chiếc', 2, 2, 1),
('SP007', N'iPad Pro M2 11 inch', 'ipadprom2.jpg', 12, 23990000, N'Chiếc', 3, 1, 1),
('SP008', N'Samsung Galaxy Tab S9', 'tabs9.jpg', 15, 18990000, N'Chiếc', 3, 2, 1),
('SP009', N'Tai nghe AirPods Pro 2', 'airpodspro2.jpg', 40, 5990000, N'Chiếc', 4, 1, 1),
('SP010', N'Sạc nhanh Samsung 25W', 'samsung25w.jpg', 50, 690000, N'Chiếc', 4, 2, 1);
-- Thêm sản phẩm Apple (Apple Việt Nam, Digiworld, FPT)
INSERT INTO SanPham (MaSp, TenSp, HinhAnh, SoLuongTon, DonGia, DonViTinh, MaLoai, MaHang, TrangThai) VALUES
('SP011', N'iPhone 15 Pro 256GB', 'iphone15pro.jpg', 30, 28990000, N'Chiếc', 1, 1, 1),
('SP012', N'iPhone 13 128GB', 'iphone13.jpg', 40, 15990000, N'Chiếc', 1, 1, 1),
('SP013', N'Apple Watch Ultra 2', 'applewatchultra2.jpg', 15, 21990000, N'Chiếc', 2, 1, 1),
('SP014', N'iPad Air 5 M1', 'ipadair5.jpg', 25, 14990000, N'Chiếc', 3, 1, 1),
('SP015', N'Tai nghe AirPods 3', 'airpods3.jpg', 50, 4290000, N'Chiếc', 4, 1, 1),
('SP016', N'Củ sạc nhanh Apple 20W Type-C', 'apple20w.jpg', 100, 550000, N'Chiếc', 4, 1, 1);

--sản phẩm Samsung (Samsung Vina)
INSERT INTO SanPham (MaSp, TenSp, HinhAnh, SoLuongTon, DonGia, DonViTinh, MaLoai, MaHang, TrangThai) VALUES
('SP017', N'Samsung Galaxy Z Fold5 512GB', 'zfold5.jpg', 10, 40990000, N'Chiếc', 1, 2, 1),
('SP018', N'Samsung Galaxy Z Flip5 256GB', 'zflip5.jpg', 20, 25990000, N'Chiếc', 1, 2, 1),
('SP019', N'Samsung Galaxy A54 5G', 'galaxya54.jpg', 45, 9490000, N'Chiếc', 1, 2, 1),
('SP020', N'Samsung Galaxy Tab S9 FE', 'tabs9fe.jpg', 20, 9990000, N'Chiếc', 3, 2, 1),
('SP021', N'Tai nghe Galaxy Buds2 Pro', 'buds2pro.jpg', 30, 3990000, N'Chiếc', 4, 2, 1);

--sản phẩm Xiaomi (Digiworld)
INSERT INTO SanPham (MaSp, TenSp, HinhAnh, SoLuongTon, DonGia, DonViTinh, MaLoai, MaHang, TrangThai) VALUES
('SP022', N'Xiaomi 13T Pro', 'xiaomi13tpro.jpg', 15, 15990000, N'Chiếc', 1, 3, 1),
('SP023', N'Xiaomi Redmi Note 12 Pro', 'redminote12pro.jpg', 40, 7190000, N'Chiếc', 1, 3, 1),
('SP024', N'Vòng đeo tay Xiaomi Smart Band 8', 'miband8.jpg', 60, 890000, N'Chiếc', 2, 3, 1),
('SP025', N'Sạc dự phòng Xiaomi 10000mAh', 'xiaomipowerbank.jpg', 80, 450000, N'Chiếc', 4, 3, 1);

--sản phẩm OPPO (OPPO Việt Nam)
INSERT INTO SanPham (MaSp, TenSp, HinhAnh, SoLuongTon, DonGia, DonViTinh, MaLoai, MaHang, TrangThai) VALUES
('SP026', N'Oppo Find N3 Flip', 'oppofindn3flip.jpg', 12, 22990000, N'Chiếc', 1, 4, 1),
('SP027', N'Oppo A98 5G', 'oppoa98.jpg', 35, 7490000, N'Chiếc', 1, 4, 1),
('SP028', N'Tai nghe Oppo Enco Air3', 'oppoencoair3.jpg', 40, 1190000, N'Chiếc', 4, 4, 1);

--sản phẩm đa hãng (FPT Trading, Digiworld)
INSERT INTO SanPham (MaSp, TenSp, HinhAnh, SoLuongTon, DonGia, DonViTinh, MaLoai, MaHang, TrangThai) VALUES
('SP029', N'Sony Xperia 1 V', 'xperia1v.jpg', 5, 35990000, N'Chiếc', 1, 5, 1),
('SP030', N'Tai nghe chụp tai Sony WH-1000XM5', 'sonywh1000xm5.jpg', 18, 7990000, N'Chiếc', 4, 5, 1),
('SP031', N'ASUS ROG Phone 7', 'rogphone7.jpg', 8, 23990000, N'Chiếc', 1, 6, 1),
('SP032', N'Máy tính bảng Lenovo Tab P11 Pro', 'lenovotabp11.jpg', 12, 10990000, N'Chiếc', 3, 7, 1),
('SP033', N'Vivo V29 5G', 'vivov29.jpg', 25, 12990000, N'Chiếc', 1, 9, 1),
('SP034', N'Nokia G22', 'nokiag22.jpg', 50, 3990000, N'Chiếc', 1, 10, 1),
('SP035', N'Realme 11 Pro+', 'realme11proplus.jpg', 20, 10490000, N'Chiếc', 1, 11, 1);

CREATE TABLE TaiKhoan (
    MaTK VARCHAR(20) NOT NULL,
    TenDangNhap VARCHAR(50) NOT NULL,
    MatKhau VARCHAR(50) NOT NULL,
    MaNV VARCHAR(20) NOT NULL,
    MaVaiTro VARCHAR(20) NOT NULL,
    TrangThai BIT DEFAULT 1
);
INSERT INTO TaiKhoan (MaTK, TenDangNhap, MatKhau, MaNV, MaVaiTro, TrangThai)
VALUES
('TK01', 'admin', '123456', 'NV01', 'VT01', 1),
('TK02', 'nhanvien1', '123456', 'NV02', 'VT02', 1),
('TK03', 'ketoan1', '123456', 'NV03', 'VT03', 1);

CREATE TABLE ChuongTrinhKhuyenMai (
    MaCTKM        VARCHAR(20) NOT NULL,
    TenCTKM       NVARCHAR(255),
    LoaiKhuyenMai NVARCHAR(50),
    MoTa          NVARCHAR(500),
    NgayBatDau    DATE,
    NgayKetThuc   DATE,
    TrangThai BIT DEFAULT 1
);
INSERT INTO ChuongTrinhKhuyenMai 
(MaCTKM, TenCTKM, LoaiKhuyenMai, MoTa, NgayBatDau, NgayKetThuc, TrangThai)
VALUES
('CTKM01', N'Khuyến mãi Tết 2026', N'HoaDon', N'Giảm giá hóa đơn dịp Tết', '2026-01-01', '2026-01-31', 1),
('CTKM02', N'Sale iPhone', N'SanPham', N'Giảm giá các dòng iPhone', '2026-01-05', '2026-02-05', 1),
('CTKM03', N'Sale Samsung', N'SanPham', N'Ưu đãi Samsung Galaxy', '2026-01-10', '2026-02-10', 1);

CREATE TABLE ChiTietKhuyenMaiHoaDon (
    MaCTKM         VARCHAR(20) NOT NULL,
    GiaTriToiThieu DECIMAL(15,2) NOT NULL,
    PhanTramGiam   DECIMAL(5,2),
    SoTienGiam     DECIMAL(15,2),
    GiamToiDa      DECIMAL(15,2)
);
INSERT INTO ChiTietKhuyenMaiHoaDon
VALUES
('CTKM01', 10000000, 5,  NULL, 1000000),
('CTKM01', 20000000, 10, NULL, 2000000);

CREATE TABLE ChiTietKhuyenMaiSanPham (
    MaCTKM        VARCHAR(20) NOT NULL,
    MaSanPham     VARCHAR(20) NOT NULL,
    PhanTramGiam  DECIMAL(5,2),
    SoTienGiam    DECIMAL(15,2),
    SoLuongToiDa  INT
);
INSERT INTO ChiTietKhuyenMaiSanPham
VALUES
('CTKM02', 'SP001', 8,  NULL, 100),
('CTKM03', 'SP002', 12, NULL, 200),
('CTKM03', 'SP003', NULL, 500000, 300);

CREATE TABLE PhieuNhap (
    maPHN VARCHAR(20) NOT NULL,
    maNV VARCHAR(20) NOT NULL,
    maNCC VARCHAR(20) NOT NULL,
    ngay DATETIME,
    tongtien DECIMAL(18,2),
    TrangThai BIT DEFAULT 1
);
-- Dữ liệu 20 Phiếu Nhập mới
INSERT INTO PhieuNhap (maPHN, maNV, maNCC, ngay, tongtien, TrangThai) VALUES 
-- Nhà cung cấp NCC01 (Samsung Vina)
('PN001', 'NV01', 'NCC01', '2025-08-10 09:00:00', 300000000.00, 1),
('PN002', 'NV02', 'NCC01', '2025-09-15 14:30:00', 240000000.00, 1),
('PN003', 'NV03', 'NCC01', '2025-10-20 10:15:00', 395000000.00, 1),
('PN004', 'NV01', 'NCC01', '2025-11-05 16:00:00', 355000000.00, 1),

-- Nhà cung cấp NCC02 (Apple Việt Nam LLC)
('PN005', 'NV02', 'NCC02', '2025-08-12 08:45:00', 575000000.00, 1),
('PN006', 'NV03', 'NCC02', '2025-09-20 11:20:00', 360000000.00, 1),
('PN007', 'NV01', 'NCC02', '2025-10-25 15:10:00', 890000000.00, 1),
('PN008', 'NV02', 'NCC02', '2025-11-10 09:30:00', 420000000.00, 1),
('PN009', 'NV03', 'NCC02', '2025-12-05 14:00:00', 205000000.00, 1),

-- Nhà cung cấp NCC03 (Digiworld - Phân phối Xiaomi, Vivo, Lenovo)
('PN010', 'NV01', 'NCC03', '2025-08-15 10:00:00', 265000000.00, 1),
('PN011', 'NV02', 'NCC03', '2025-09-25 13:45:00', 270000000.00, 1),
('PN012', 'NV03', 'NCC03', '2025-10-30 16:30:00', 110000000.00, 1),
('PN013', 'NV01', 'NCC03', '2025-11-15 09:15:00', 290000000.00, 1),

-- Nhà cung cấp NCC04 (OPPO Việt Nam)
('PN014', 'NV02', 'NCC04', '2025-08-20 14:20:00', 350000000.00, 1),
('PN015', 'NV03', 'NCC04', '2025-10-05 08:30:00', 212000000.00, 1),
('PN016', 'NV01', 'NCC04', '2025-11-20 15:45:00', 135000000.00, 1),

-- Nhà cung cấp NCC05 (FPT Trading - Phân phối đa hãng: Sony, Asus, Nokia, Realme)
('PN017', 'NV02', 'NCC05', '2025-09-01 10:40:00', 240000000.00, 1),
('PN018', 'NV03', 'NCC05', '2025-10-10 13:15:00', 280000000.00, 1),
('PN019', 'NV01', 'NCC05', '2025-11-25 09:50:00', 230000000.00, 1),
('PN020', 'NV02', 'NCC05', '2025-12-15 16:10:00', 245000000.00, 1);

CREATE TABLE ChiTietPhieuNhap (
    maPHN VARCHAR(20) NOT NULL,
    maSP VARCHAR(20) NOT NULL,
    soLuong INT NOT NULL,
    dongia DECIMAL(18,2),
    thanhtien DECIMAL(18,2)
);

INSERT INTO ChiTietPhieuNhap (maPHN, maSP, soLuong, dongia, thanhtien) VALUES
-- NCC01 (Samsung Vina)
('PN001', 'SP002', 10, 20000000.00, 200000000.00), 
('PN001', 'SP006', 20, 5000000.00, 100000000.00),  
('PN002', 'SP008', 15, 15000000.00, 225000000.00), 
('PN002', 'SP010', 50, 300000.00, 15000000.00),    
('PN003', 'SP017', 5, 35000000.00, 175000000.00),  
('PN003', 'SP018', 10, 22000000.00, 220000000.00), 
('PN004', 'SP019', 20, 8000000.00, 160000000.00),  
('PN004', 'SP020', 15, 8000000.00, 120000000.00),  
('PN004', 'SP021', 30, 2500000.00, 75000000.00),   

-- NCC02 (Apple Việt Nam)
('PN005', 'SP001', 15, 25000000.00, 375000000.00), 
('PN005', 'SP005', 20, 10000000.00, 200000000.00), 
('PN006', 'SP007', 10, 20000000.00, 200000000.00), 
('PN006', 'SP009', 40, 4000000.00, 160000000.00),  
('PN007', 'SP011', 20, 25000000.00, 500000000.00), 
('PN007', 'SP012', 30, 13000000.00, 390000000.00),
('PN008', 'SP013', 10, 18000000.00, 180000000.00), 
('PN008', 'SP014', 20, 12000000.00, 240000000.00), 
('PN009', 'SP015', 50, 3500000.00, 175000000.00),  
('PN009', 'SP016', 100, 300000.00, 30000000.00),  

-- NCC03 (Digiworld)
('PN010', 'SP003', 30, 4500000.00, 135000000.00), 
('PN010', 'SP022', 10, 13000000.00, 130000000.00), 
('PN011', 'SP023', 40, 6000000.00, 240000000.00),  
('PN011', 'SP024', 50, 600000.00, 30000000.00),    
('PN012', 'SP025', 80, 250000.00, 20000000.00),    
('PN012', 'SP032', 10, 9000000.00, 90000000.00),   
('PN013', 'SP033', 20, 10000000.00, 200000000.00), 
('PN013', 'SP003', 20, 4500000.00, 90000000.00),   

-- NCC04 (OPPO Việt Nam)
('PN014', 'SP004', 20, 7500000.00, 150000000.00),  
('PN014', 'SP026', 10, 20000000.00, 200000000.00), 
('PN015', 'SP027', 30, 6000000.00, 180000000.00), 
('PN015', 'SP028', 40, 800000.00, 32000000.00),    
('PN016', 'SP004', 10, 7500000.00, 75000000.00),   
('PN016', 'SP027', 10, 6000000.00, 60000000.00),   

-- NCC05 (FPT Trading)
('PN017', 'SP029', 5, 30000000.00, 150000000.00),  
('PN017', 'SP030', 15, 6000000.00, 90000000.00),   
('PN018', 'SP031', 8, 20000000.00, 160000000.00),  
('PN018', 'SP034', 40, 3000000.00, 120000000.00),  
('PN019', 'SP035', 20, 8500000.00, 170000000.00), 
('PN019', 'SP029', 2, 30000000.00, 60000000.00),   
('PN020', 'SP030', 10, 6000000.00, 60000000.00),  
('PN020', 'SP031', 5, 20000000.00, 100000000.00),  
('PN020', 'SP035', 10, 8500000.00, 85000000.00);   


CREATE TABLE HoaDon (
    MaHD VARCHAR(20) NOT NULL,
    MaNV VARCHAR(20) NOT NULL,      
    MaKH VARCHAR(20) NOT NULL,      
    NgayLapHD DATETIME,    
    TongTien DECIMAL(18, 2),
    TrangThai BIT DEFAULT 1
);
INSERT INTO HoaDon (MaHD, MaNV, MaKH, NgayLapHD, TongTien, TrangThai)
VALUES 
('HD001', 'NV01', 'KH001', '2026-01-10 09:00:00', 600000, 1), 
('HD002', 'NV01', 'KH002', '2026-01-11 14:30:00', 1200000, 1), 
('HD003', 'NV02', 'KH003', '2026-01-12 10:15:00', 450000, 1);

CREATE TABLE ChiTietHoaDon(
    MaHD VARCHAR(20) NOT NULL,
    MaSP VARCHAR(20) NOT NULL,
    SL INT,               
    DG_Ban DECIMAL(18, 2), 
    ThanhTien DECIMAL(18, 2)
);
INSERT INTO ChiTietHoaDon (MaHD, MaSP, SL, DG_Ban, ThanhTien)
VALUES 
('HD001', 'SP001', 2, 150000, 300000), 
('HD001', 'SP002', 1, 300000, 300000);
CREATE TABLE BaoHanh (
    MaBH VARCHAR(20) NOT NULL,
    TenBH NVARCHAR(255) NOT NULL,
    MaHD VARCHAR(20) NOT NULL,
    MaSP VARCHAR(20) NOT NULL,
    ThoiHan INT,
    NgayBatDau DATE,
    NgayKetThuc DATE,
    TrangThai BIT DEFAULT 1
);
INSERT INTO BaoHanh 
(MaBH, TenBH, MaHD, MaSP, ThoiHan, NgayBatDau, NgayKetThuc, TrangThai)
VALUES
('BH001', N'Bảo hành iPhone 14 Pro Max 12 tháng', 'HD001', 'SP001', 12, '2026-01-10', '2027-01-10', 1),
('BH002', N'Bảo hành Samsung Galaxy S23 Ultra 6 tháng', 'HD001', 'SP002', 6, '2026-01-10', '2026-07-10', 1);

/* ================== PRIMARY KEY ================== */

ALTER TABLE HangSanXuat
ADD CONSTRAINT PK_HangSanXuat PRIMARY KEY (MaHang);

ALTER TABLE LoaiSanPham
ADD CONSTRAINT PK_LoaiSanPham PRIMARY KEY (MaLoai);

ALTER TABLE VaiTro
ADD CONSTRAINT PK_VaiTro PRIMARY KEY (MaVaiTro);

ALTER TABLE NhanVien
ADD CONSTRAINT PK_NhanVien PRIMARY KEY (MaNV);

ALTER TABLE KhachHang
ADD CONSTRAINT PK_KhachHang PRIMARY KEY (MaKH);

ALTER TABLE NhaCungCap
ADD CONSTRAINT PK_NhaCungCap PRIMARY KEY (maNCC);

ALTER TABLE SanPham
ADD CONSTRAINT PK_SanPham PRIMARY KEY (MaSp);

ALTER TABLE TaiKhoan
ADD CONSTRAINT PK_TaiKhoan PRIMARY KEY (MaTK);

ALTER TABLE ChuongTrinhKhuyenMai
ADD CONSTRAINT PK_CTKM PRIMARY KEY (MaCTKM);

ALTER TABLE PhieuNhap
ADD CONSTRAINT PK_PhieuNhap PRIMARY KEY (maPHN);

ALTER TABLE HoaDon
ADD CONSTRAINT PK_HoaDon PRIMARY KEY (MaHD);

ALTER TABLE BaoHanh
ADD CONSTRAINT PK_BaoHanh PRIMARY KEY (MaBH);


/* ================== COMPOSITE PRIMARY KEY ================== */

ALTER TABLE ChiTietKhuyenMaiHoaDon
ADD CONSTRAINT PK_CTKM_HoaDon PRIMARY KEY (MaCTKM, GiaTriToiThieu);

ALTER TABLE ChiTietKhuyenMaiSanPham
ADD CONSTRAINT PK_CTKM_SanPham PRIMARY KEY (MaCTKM, MaSanPham);

ALTER TABLE ChiTietPhieuNhap
ADD CONSTRAINT PK_ChiTietPhieuNhap PRIMARY KEY (maPHN, maSP);

ALTER TABLE ChiTietHoaDon
ADD CONSTRAINT PK_ChiTietHoaDon PRIMARY KEY (MaHD, MaSP);

/* ================== FOREIGN KEY ================== */

ALTER TABLE SanPham
ADD CONSTRAINT FK_SanPham_Loai
FOREIGN KEY (MaLoai) REFERENCES LoaiSanPham(MaLoai);

ALTER TABLE SanPham
ADD CONSTRAINT FK_SanPham_Hang
FOREIGN KEY (MaHang) REFERENCES HangSanXuat(MaHang);

ALTER TABLE TaiKhoan
ADD CONSTRAINT FK_TaiKhoan_NhanVien
FOREIGN KEY (MaNV) REFERENCES NhanVien(MaNV);

ALTER TABLE TaiKhoan
ADD CONSTRAINT FK_TaiKhoan_VaiTro
FOREIGN KEY (MaVaiTro) REFERENCES VaiTro(MaVaiTro);

ALTER TABLE PhieuNhap
ADD CONSTRAINT FK_PhieuNhap_NhanVien
FOREIGN KEY (maNV) REFERENCES NhanVien(MaNV);

ALTER TABLE PhieuNhap
ADD CONSTRAINT FK_PhieuNhap_NhaCungCap
FOREIGN KEY (maNCC) REFERENCES NhaCungCap(maNCC);

ALTER TABLE ChiTietPhieuNhap
ADD CONSTRAINT FK_CTPN_PhieuNhap
FOREIGN KEY (maPHN) REFERENCES PhieuNhap(maPHN);

ALTER TABLE ChiTietPhieuNhap
ADD CONSTRAINT FK_CTPN_SanPham
FOREIGN KEY (maSP) REFERENCES SanPham(MaSp);

ALTER TABLE ChiTietKhuyenMaiHoaDon
ADD CONSTRAINT FK_CTKMHD_CTKM
FOREIGN KEY (MaCTKM) REFERENCES ChuongTrinhKhuyenMai(MaCTKM);

ALTER TABLE ChiTietKhuyenMaiSanPham
ADD CONSTRAINT FK_CTKMSP_CTKM
FOREIGN KEY (MaCTKM) REFERENCES ChuongTrinhKhuyenMai(MaCTKM);

ALTER TABLE ChiTietKhuyenMaiSanPham
ADD CONSTRAINT FK_CTKMSP_SanPham
FOREIGN KEY (MaSanPham) REFERENCES SanPham(MaSp);

ALTER TABLE HoaDon
ADD CONSTRAINT FK_HoaDon_NhanVien
FOREIGN KEY (MaNV) REFERENCES NhanVien(MaNV);

ALTER TABLE HoaDon
ADD CONSTRAINT FK_HoaDon_KhachHang
FOREIGN KEY (MaKH) REFERENCES KhachHang(MaKH);

ALTER TABLE ChiTietHoaDon
ADD CONSTRAINT FK_CTHD_HoaDon
FOREIGN KEY (MaHD) REFERENCES HoaDon(MaHD);

ALTER TABLE ChiTietHoaDon
ADD CONSTRAINT FK_CTHD_SanPham
FOREIGN KEY (MaSP) REFERENCES SanPham(MaSp);

ALTER TABLE BaoHanh
ADD CONSTRAINT FK_BaoHanh_HoaDon
FOREIGN KEY (MaHD) REFERENCES HoaDon(MaHD);

ALTER TABLE BaoHanh
ADD CONSTRAINT FK_BaoHanh_SanPham
FOREIGN KEY (MaSP) REFERENCES SanPham(MaSp);
