package main;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.Locale;
import java.util.Scanner;

import entity.BenhNhan;
import entity.BenhNhanCapCuu;
import entity.BenhNhanNgoaiTru;
import entity.BenhNhanNoiTru;
import entity.IBaoCaoSucKhoe;
import service.QuanLyBenhVien;

public class Main {
    private static Scanner sc = new Scanner(System.in);
    private static QuanLyBenhVien ql = new QuanLyBenhVien();

    private static final DateTimeFormatter NGAY =
            DateTimeFormatter.ofPattern("dd/MM/uuuu").withResolverStyle(ResolverStyle.STRICT);

    public static void main(String[] args) {
        int chon;
        do {
            hienThiMenu();
            chon = docSoNguyen("Chọn chức năng: ");
            switch (chon) {
                case 1:
                    chucNangThem();
                    break;
                case 2:
                    ql.hienThiDanhSach();
                    break;
                case 3:
                    System.out.println("TỔNG CHI PHÍ TOÀN BỆNH VIỆN: "
                            + String.format(Locale.US, "%,.0f VND", ql.tinhTongChiPhi()));
                    break;
                case 4:
                    chucNangTimKiem();
                    break;
                case 0:
                    System.out.println("Đã thoát chương trình. Tạm biệt!");
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ, vui lòng chọn lại.");
            }
        } while (chon != 0);
    }

    private static void hienThiMenu() {
        System.out.println();
        System.out.println("========== QUẢN LÝ BỆNH VIỆN ==========");
        System.out.println("1. Thêm bệnh nhân");
        System.out.println("2. Hiển thị danh sách bệnh nhân");
        System.out.println("3. Tính tổng chi phí toàn bệnh viện");
        System.out.println("4. Tìm kiếm bệnh nhân theo mã");
        System.out.println("0. Thoát");
        System.out.println("=======================================");
    }

    // ================= CHỨC NĂNG =================
    private static void chucNangThem() {
        System.out.println("--- Chọn loại bệnh nhân ---");
        System.out.println("1. Nội trú");
        System.out.println("2. Ngoại trú");
        System.out.println("3. Cấp cứu");
        int loai = docSoNguyen("Loại: ");
        if (loai < 1 || loai > 3) {
            System.out.println("Loại bệnh nhân không hợp lệ.");
            return;
        }
        BenhNhan bn = nhapBenhNhan(loai);
        if (ql.themBenhNhan(bn)) {
            System.out.println("Thêm bệnh nhân thành công!");
        } else {
            System.out.println("Thêm thất bại: mã bệnh nhân đã tồn tại.");
        }
    }

    private static void chucNangTimKiem() {
        String ma = docChuoi("Nhập mã bệnh nhân cần tìm: ");
        BenhNhan bn = ql.timKiemTheoMa(ma);
        if (bn == null) {
            System.out.println("Không tìm thấy bệnh nhân có mã: " + ma);
            return;
        }
        System.out.println("Đã tìm thấy:");
        System.out.println(bn);
        if (bn instanceof IBaoCaoSucKhoe) {
            System.out.println(((IBaoCaoSucKhoe) bn).xuatBaoCao());
        }
    }

    // ================= NHẬP DỮ LIỆU =================
    /**
     * Tạo đối tượng rỗng rồi gán từng thuộc tính qua setter.
     * Setter ném IllegalArgumentException nếu sai -> nhập lại đúng trường đó.
     */
    private static BenhNhan nhapBenhNhan(int loai) {
        BenhNhan bn;
        BenhNhanNoiTru nt = null;
        BenhNhanNgoaiTru ng = null;
        BenhNhanCapCuu cc = null;
        if (loai == 1) { nt = new BenhNhanNoiTru();  bn = nt; }
        else if (loai == 2) { ng = new BenhNhanNgoaiTru(); bn = ng; }
        else { cc = new BenhNhanCapCuu(); bn = cc; }

        final BenhNhan b = bn;
        // --- thông tin chung ---
        thuLai(() -> {
            String ma = docChuoi("Mã bệnh nhân: ");
            if (ql.daTonTai(ma))
                throw new IllegalArgumentException("Mã '" + ma + "' đã tồn tại.");
            b.setMaBenhNhan(ma);
        });
        thuLai(() -> b.setHoTen(docChuoi("Họ tên: ")));
        thuLai(() -> b.setNgaySinh(docNgay("Ngày sinh (dd/MM/yyyy): ")));
        thuLai(() -> b.setSoDienThoai(docChuoi("Số điện thoại: ")));
        thuLai(() -> b.setDiaChi(docChuoi("Địa chỉ: ")));

        // --- thông tin riêng ---
        if (nt != null) {
            final BenhNhanNoiTru x = nt;
            thuLai(() -> x.setSoNgayNamVien(docSoNguyen("Số ngày nằm viện: ")));
            thuLai(() -> x.setPhiGiuongMotNgay(docSoThuc("Phí giường/ngày (VND): ")));
            thuLai(() -> x.setChiPhiThuoc(docSoThuc("Chi phí thuốc (VND): ")));
        } else if (ng != null) {
            final BenhNhanNgoaiTru x = ng;
            thuLai(() -> x.setSoLanKham(docSoNguyen("Số lần khám: ")));
            thuLai(() -> x.setPhiKhamMotLan(docSoThuc("Phí khám/lần (VND): ")));
            thuLai(() -> x.setChiPhiXetNghiem(docSoThuc("Chi phí xét nghiệm (VND, 0 nếu không): ")));
        } else {
            final BenhNhanCapCuu x = cc;
            thuLai(() -> x.setMucDoCapCuu(docSoNguyen("Mức độ cấp cứu (1-3): ")));
            thuLai(() -> x.setPhiXuLyCapCuu(docSoThuc("Phí xử lý cấp cứu (VND): ")));
            thuLai(() -> x.setChiPhiPhauThuat(docSoThuc("Chi phí phẫu thuật (VND, 0 nếu không): ")));
        }
        return bn;
    }

    /** Lặp lại hành động nhập cho tới khi không còn ném IllegalArgumentException. */
    private static void thuLai(Runnable hanhDong) {
        while (true) {
            try {
                hanhDong.run();
                return;
            } catch (IllegalArgumentException e) {
                System.out.println("Lỗi: " + e.getMessage() + " Vui lòng nhập lại.");
            }
        }
    }

    private static String docChuoi(String msg) {
        while (true) {
            System.out.print(msg);
            String s = sc.nextLine().trim();
            if (!s.isEmpty()) return s;
            System.out.println("Không được để trống. Vui lòng nhập lại.");
        }
    }

    private static int docSoNguyen(String msg) {
        while (true) {
            System.out.print(msg);
            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Vui lòng nhập một số nguyên hợp lệ.");
            }
        }
    }

    private static double docSoThuc(String msg) {
        while (true) {
            System.out.print(msg);
            try {
                double v = Double.parseDouble(sc.nextLine().trim());
                if (Double.isNaN(v) || Double.isInfinite(v))
                    throw new NumberFormatException();
                return v;
            } catch (NumberFormatException e) {
                System.out.println("Vui lòng nhập một số hợp lệ.");
            }
        }
    }

    private static LocalDate docNgay(String msg) {
        while (true) {
            System.out.print(msg);
            try {
                return LocalDate.parse(sc.nextLine().trim(), NGAY);
            } catch (DateTimeParseException e) {
                System.out.println("Ngày không hợp lệ. Định dạng đúng: dd/MM/yyyy (ví dụ 25/12/1999).");
            }
        }
    }
}
