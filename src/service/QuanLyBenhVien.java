package service;

import java.sql.SQLException;
import java.util.ArrayList;

import dao.BenhNhanDAO;
import entity.BenhNhan;
import entity.IBaoCaoSucKhoe;

public class QuanLyBenhVien {
    private ArrayList<BenhNhan> dsBenhNhan;
    private BenhNhanDAO dao;   // null nếu không kết nối được CSDL (chạy chế độ RAM)

    public QuanLyBenhVien() {
        dsBenhNhan = new ArrayList<>();
        try {
            dao = new BenhNhanDAO();
            dsBenhNhan.addAll(dao.layTatCa());
            System.out.println("Đã kết nối CSDL, tải " + dsBenhNhan.size() + " bệnh nhân.");
        } catch (SQLException e) {
            dao = null;
            System.out.println("Không kết nối được CSDL (" + e.getMessage()
                    + "). Chương trình chạy ở chế độ lưu trong RAM.");
        }
    }

    /** Thêm bệnh nhân; trả về false nếu null, trùng mã hoặc lưu CSDL lỗi. */
    public boolean themBenhNhan(BenhNhan bn) {
        if (bn == null || daTonTai(bn.getMaBenhNhan())) {
            return false;
        }
        if (dao != null) {
            try {
                dao.them(bn);
            } catch (SQLException e) {
                System.out.println("Lỗi lưu CSDL: " + e.getMessage());
                return false;
            }
        }
        dsBenhNhan.add(bn);
        return true;
    }

    /** Duyệt danh sách: đa hình (toString/tinhChiPhi) + instanceof để xuất báo cáo. */
    public void hienThiDanhSach() {
        if (dsBenhNhan.isEmpty()) {
            System.out.println("Danh sách bệnh nhân đang trống.");
            return;
        }
        System.out.println("===== DANH SÁCH BỆNH NHÂN (" + dsBenhNhan.size() + ") =====");
        int stt = 1;
        for (BenhNhan bn : dsBenhNhan) {
            System.out.println(stt++ + ". " + bn);
            if (bn instanceof IBaoCaoSucKhoe) {
                System.out.println(((IBaoCaoSucKhoe) bn).xuatBaoCao());
            }
        }
    }

    public double tinhTongChiPhi() {
        double tong = 0;
        for (BenhNhan bn : dsBenhNhan) {
            tong += bn.tinhChiPhi();   // dynamic binding
        }
        return tong;
    }

    /** Trả về null nếu không tìm thấy. */
    public BenhNhan timKiemTheoMa(String ma) {
        if (ma == null) return null;
        for (BenhNhan bn : dsBenhNhan) {
            if (bn.getMaBenhNhan().equalsIgnoreCase(ma.trim())) {
                return bn;
            }
        }
        return null;
    }

    public boolean daTonTai(String ma) {
        return timKiemTheoMa(ma) != null;
    }

    public ArrayList<BenhNhan> getDanhSach() {
        return dsBenhNhan;
    }
}
