-- ===== Tạo cơ sở dữ liệu =====
CREATE DATABASE IF NOT EXISTS quanly_benhvien
    CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE quanly_benhvien;

-- ===== Bảng bệnh nhân (một bảng chung cho 3 loại, phân biệt bằng cột loai) =====
CREATE TABLE IF NOT EXISTS benh_nhan (
    id                   INT AUTO_INCREMENT PRIMARY KEY,
    ma_benh_nhan         VARCHAR(20)  NOT NULL UNIQUE,
    ho_ten               VARCHAR(100) NOT NULL,
    ngay_sinh            DATE         NOT NULL,
    so_dien_thoai        VARCHAR(11)  NOT NULL,
    dia_chi              VARCHAR(200) NOT NULL,
    loai                 ENUM('NOI_TRU','NGOAI_TRU','CAP_CUU') NOT NULL,

    -- Nội trú
    so_ngay_nam_vien     INT            NULL,
    phi_giuong_mot_ngay  DECIMAL(15,2)  NULL,
    chi_phi_thuoc        DECIMAL(15,2)  NULL,

    -- Ngoại trú
    so_lan_kham          INT            NULL,
    phi_kham_mot_lan     DECIMAL(15,2)  NULL,
    chi_phi_xet_nghiem   DECIMAL(15,2)  NULL,

    -- Cấp cứu
    muc_do_cap_cuu       TINYINT        NULL,
    phi_xu_ly_cap_cuu    DECIMAL(15,2)  NULL,
    chi_phi_phau_thuat   DECIMAL(15,2)  NULL,

    CONSTRAINT chk_muc_do CHECK (muc_do_cap_cuu IS NULL OR muc_do_cap_cuu BETWEEN 1 AND 3)
);
