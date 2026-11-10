package entity;

import java.time.LocalDate;

public class BenhNhanCapCuu extends BenhNhan implements IBaoCaoSucKhoe {
    private int mucDoCapCuu;          // 1 - 3
    private double phiXuLyCapCuu;
    private double chiPhiPhauThuat;   // 0 nếu không phẫu thuật

    public BenhNhanCapCuu() {
        super();
    }

    public BenhNhanCapCuu(String ma, String hoTen, LocalDate ngaySinh, String sdt, String diaChi,
                          int mucDoCapCuu, double phiXuLyCapCuu, double chiPhiPhauThuat) {
        super(ma, hoTen, ngaySinh, sdt, diaChi);
        setMucDoCapCuu(mucDoCapCuu);
        setPhiXuLyCapCuu(phiXuLyCapCuu);
        setChiPhiPhauThuat(chiPhiPhauThuat);
    }

    public int getMucDoCapCuu() { return mucDoCapCuu; }
    public double getPhiXuLyCapCuu() { return phiXuLyCapCuu; }
    public double getChiPhiPhauThuat() { return chiPhiPhauThuat; }

    public void setMucDoCapCuu(int mucDoCapCuu) {
        if (mucDoCapCuu < 1 || mucDoCapCuu > 3)
            throw new IllegalArgumentException("Mức độ cấp cứu chỉ nhận giá trị 1, 2 hoặc 3.");
        this.mucDoCapCuu = mucDoCapCuu;
    }

    public void setPhiXuLyCapCuu(double phiXuLyCapCuu) {
        if (phiXuLyCapCuu < 0)
            throw new IllegalArgumentException("Phí xử lý cấp cứu không được âm.");
        this.phiXuLyCapCuu = phiXuLyCapCuu;
    }

    public void setChiPhiPhauThuat(double chiPhiPhauThuat) {
        if (chiPhiPhauThuat < 0)
            throw new IllegalArgumentException("Chi phí phẫu thuật không được âm.");
        this.chiPhiPhauThuat = chiPhiPhauThuat;
    }

    @Override
    public double tinhChiPhi() {
        return phiXuLyCapCuu * mucDoCapCuu + chiPhiPhauThuat;
    }

    @Override
    public String xuatBaoCao() {
        String mucDo;
        switch (mucDoCapCuu) {
            case 1:  mucDo = "nhẹ"; break;
            case 2:  mucDo = "trung bình"; break;
            default: mucDo = "nguy kịch"; break;
        }
        String phauThuat = chiPhiPhauThuat > 0
                ? "có phẫu thuật (" + dinhDangTien(chiPhiPhauThuat) + ")"
                : "không phẫu thuật";
        return "  >> BÁO CÁO SỨC KHỎE (CẤP CỨU): bệnh nhân " + getHoTen()
                + " ở mức độ " + mucDoCapCuu + " (" + mucDo + "), " + phauThuat + ".";
    }

    @Override
    public String toString() {
        return "[CẤP CỨU] " + super.toString()
                + String.format(" | Mức độ: %d | Phí xử lý: %s | Phẫu thuật: %s | CHI PHÍ: %s",
                mucDoCapCuu, dinhDangTien(phiXuLyCapCuu), dinhDangTien(chiPhiPhauThuat),
                dinhDangTien(tinhChiPhi()));
    }
}
