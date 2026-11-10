package dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.time.LocalDate;
import java.util.ArrayList;

import entity.BenhNhan;
import entity.BenhNhanCapCuu;
import entity.BenhNhanNgoaiTru;
import entity.BenhNhanNoiTru;

/** Lớp truy cập dữ liệu (DAO): chứa toàn bộ lệnh SQL của bảng benh_nhan. */
public class BenhNhanDAO {

    private static final String SQL_INSERT =
            "INSERT INTO benh_nhan (ma_benh_nhan, ho_ten, ngay_sinh, so_dien_thoai, dia_chi, loai, "
            + "so_ngay_nam_vien, phi_giuong_mot_ngay, chi_phi_thuoc, "
            + "so_lan_kham, phi_kham_mot_lan, chi_phi_xet_nghiem, "
            + "muc_do_cap_cuu, phi_xu_ly_cap_cuu, chi_phi_phau_thuat) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String SQL_SELECT_ALL =
            "SELECT * FROM benh_nhan ORDER BY id";

    /** INSERT: lưu một bệnh nhân (đa hình: kiểm tra loại bằng instanceof). */
    public void them(BenhNhan bn) throws SQLException {
        try (Connection con = KetNoiCSDL.layKetNoi();
             PreparedStatement ps = con.prepareStatement(SQL_INSERT)) {

            ps.setString(1, bn.getMaBenhNhan());
            ps.setString(2, bn.getHoTen());
            ps.setDate(3, Date.valueOf(bn.getNgaySinh()));
            ps.setString(4, bn.getSoDienThoai());
            ps.setString(5, bn.getDiaChi());

            // Cột riêng của từng loại: mặc định NULL
            for (int i = 7; i <= 15; i++) {
                ps.setNull(i, (i == 7 || i == 10 || i == 13) ? Types.INTEGER : Types.DECIMAL);
            }

            if (bn instanceof BenhNhanNoiTru) {
                BenhNhanNoiTru nt = (BenhNhanNoiTru) bn;
                ps.setString(6, "NOI_TRU");
                ps.setInt(7, nt.getSoNgayNamVien());
                ps.setBigDecimal(8, BigDecimal.valueOf(nt.getPhiGiuongMotNgay()));
                ps.setBigDecimal(9, BigDecimal.valueOf(nt.getChiPhiThuoc()));
            } else if (bn instanceof BenhNhanNgoaiTru) {
                BenhNhanNgoaiTru ng = (BenhNhanNgoaiTru) bn;
                ps.setString(6, "NGOAI_TRU");
                ps.setInt(10, ng.getSoLanKham());
                ps.setBigDecimal(11, BigDecimal.valueOf(ng.getPhiKhamMotLan()));
                ps.setBigDecimal(12, BigDecimal.valueOf(ng.getChiPhiXetNghiem()));
            } else if (bn instanceof BenhNhanCapCuu) {
                BenhNhanCapCuu cc = (BenhNhanCapCuu) bn;
                ps.setString(6, "CAP_CUU");
                ps.setInt(13, cc.getMucDoCapCuu());
                ps.setBigDecimal(14, BigDecimal.valueOf(cc.getPhiXuLyCapCuu()));
                ps.setBigDecimal(15, BigDecimal.valueOf(cc.getChiPhiPhauThuat()));
            }
            ps.executeUpdate();
        }
    }

    /** SELECT: đọc toàn bộ bệnh nhân và dựng lại đúng lớp con theo cột loai. */
    public ArrayList<BenhNhan> layTatCa() throws SQLException {
        ArrayList<BenhNhan> ds = new ArrayList<>();
        try (Connection con = KetNoiCSDL.layKetNoi();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(SQL_SELECT_ALL)) {

            while (rs.next()) {
                String ma = rs.getString("ma_benh_nhan");
                String hoTen = rs.getString("ho_ten");
                LocalDate ngaySinh = rs.getDate("ngay_sinh").toLocalDate();
                String sdt = rs.getString("so_dien_thoai");
                String diaChi = rs.getString("dia_chi");

                switch (rs.getString("loai")) {
                    case "NOI_TRU":
                        ds.add(new BenhNhanNoiTru(ma, hoTen, ngaySinh, sdt, diaChi,
                                rs.getInt("so_ngay_nam_vien"),
                                rs.getDouble("phi_giuong_mot_ngay"),
                                rs.getDouble("chi_phi_thuoc")));
                        break;
                    case "NGOAI_TRU":
                        ds.add(new BenhNhanNgoaiTru(ma, hoTen, ngaySinh, sdt, diaChi,
                                rs.getInt("so_lan_kham"),
                                rs.getDouble("phi_kham_mot_lan"),
                                rs.getDouble("chi_phi_xet_nghiem")));
                        break;
                    case "CAP_CUU":
                        ds.add(new BenhNhanCapCuu(ma, hoTen, ngaySinh, sdt, diaChi,
                                rs.getInt("muc_do_cap_cuu"),
                                rs.getDouble("phi_xu_ly_cap_cuu"),
                                rs.getDouble("chi_phi_phau_thuat")));
                        break;
                    default:
                        break;
                }
            }
        }
        return ds;
    }
}
