package entity;

import java.time.LocalDate;

public class BenhNhanNoiTru extends BenhNhan implements IBaoCaoSucKhoe {
    private int soNgayNamVien;
    private double phiGiuongMotNgay;
    private double chiPhiThuoc;

    public BenhNhanNoiTru() {
        super();
    }

    public BenhNhanNoiTru(String ma, String hoTen, LocalDate ngaySinh, String sdt, String diaChi,
                          int soNgayNamVien, double phiGiuongMotNgay, double chiPhiThuoc) {
        super(ma, hoTen, ngaySinh, sdt, diaChi);
        setSoNgayNamVien(soNgayNamVien);
        setPhiGiuongMotNgay(phiGiuongMotNgay);
        setChiPhiThuoc(chiPhiThuoc);
    }

    public int getSoNgayNamVien() { return soNgayNamVien; }
    public double getPhiGiuongMotNgay() { return phiGiuongMotNgay; }
    public double getChiPhiThuoc() { return chiPhiThuoc; }

    public void setSoNgayNamVien(int soNgayNamVien) {
        if (soNgayNamVien <= 0)
            throw new IllegalArgumentException("Số ngày nằm viện phải lớn hơn 0.");
        this.soNgayNamVien = soNgayNamVien;
    }

    public void setPhiGiuongMotNgay(double phiGiuongMotNgay) {
        if (phiGiuongMotNgay < 0)
            throw new IllegalArgumentException("Phí giường/ngày không được âm.");
        this.phiGiuongMotNgay = phiGiuongMotNgay;
    }

    public void setChiPhiThuoc(double chiPhiThuoc) {
        if (chiPhiThuoc < 0)
            throw new IllegalArgumentException("Chi phí thuốc không được âm.");
        this.chiPhiThuoc = chiPhiThuoc;
    }

    @Override
    public double tinhChiPhi() {
        return soNgayNamVien * phiGiuongMotNgay + chiPhiThuoc;
    }

    @Override
    public String xuatBaoCao() {
        return "  >> BÁO CÁO SỨC KHỎE (NỘI TRÚ): bệnh nhân " + getHoTen()
                + " đã nằm viện " + soNgayNamVien + " ngày, phí giường "
                + dinhDangTien(phiGiuongMotNgay) + "/ngày, chi phí thuốc "
                + dinhDangTien(chiPhiThuoc) + ". Cần theo dõi điều trị nội trú.";
    }

    @Override
    public String toString() {
        return "[NỘI TRÚ] " + super.toString()
                + String.format(" | Số ngày: %d | Phí giường: %s | Thuốc: %s | CHI PHÍ: %s",
                soNgayNamVien, dinhDangTien(phiGiuongMotNgay), dinhDangTien(chiPhiThuoc),
                dinhDangTien(tinhChiPhi()));
    }
}
