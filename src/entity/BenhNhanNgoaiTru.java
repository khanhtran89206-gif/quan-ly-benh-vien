package entity;

import java.time.LocalDate;

public class BenhNhanNgoaiTru extends BenhNhan {
    private int soLanKham;
    private double phiKhamMotLan;
    private double chiPhiXetNghiem;

    public BenhNhanNgoaiTru() {
        super();
    }

    public BenhNhanNgoaiTru(String ma, String hoTen, LocalDate ngaySinh, String sdt, String diaChi,
                            int soLanKham, double phiKhamMotLan, double chiPhiXetNghiem) {
        super(ma, hoTen, ngaySinh, sdt, diaChi);
        setSoLanKham(soLanKham);
        setPhiKhamMotLan(phiKhamMotLan);
        setChiPhiXetNghiem(chiPhiXetNghiem);
    }

    public int getSoLanKham() { return soLanKham; }
    public double getPhiKhamMotLan() { return phiKhamMotLan; }
    public double getChiPhiXetNghiem() { return chiPhiXetNghiem; }

    public void setSoLanKham(int soLanKham) {
        if (soLanKham <= 0)
            throw new IllegalArgumentException("Số lần khám phải lớn hơn 0.");
        this.soLanKham = soLanKham;
    }

    public void setPhiKhamMotLan(double phiKhamMotLan) {
        if (phiKhamMotLan < 0)
            throw new IllegalArgumentException("Phí khám/lần không được âm.");
        this.phiKhamMotLan = phiKhamMotLan;
    }

    public void setChiPhiXetNghiem(double chiPhiXetNghiem) {
        if (chiPhiXetNghiem < 0)
            throw new IllegalArgumentException("Chi phí xét nghiệm không được âm.");
        this.chiPhiXetNghiem = chiPhiXetNghiem;
    }

    @Override
    public double tinhChiPhi() {
        return soLanKham * phiKhamMotLan + chiPhiXetNghiem;
    }

    @Override
    public String toString() {
        return "[NGOẠI TRÚ] " + super.toString()
                + String.format(" | Số lần khám: %d | Phí khám: %s | Xét nghiệm: %s | CHI PHÍ: %s",
                soLanKham, dinhDangTien(phiKhamMotLan), dinhDangTien(chiPhiXetNghiem),
                dinhDangTien(tinhChiPhi()));
    }
}
