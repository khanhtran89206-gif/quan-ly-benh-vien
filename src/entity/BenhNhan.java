package entity;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * Lớp trừu tượng cha của mọi loại bệnh nhân.
 * Thuộc tính private, truy cập qua getter/setter có kiểm tra hợp lệ.
 */
public abstract class BenhNhan {
    private String maBenhNhan;
    private String hoTen;
    private LocalDate ngaySinh;
    private String soDienThoai;
    private String diaChi;

    public static final DateTimeFormatter DINH_DANG_NGAY =
            DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public BenhNhan() {
    }

    public BenhNhan(String ma, String hoTen, LocalDate ngaySinh, String sdt, String diaChi) {
        setMaBenhNhan(ma);
        setHoTen(hoTen);
        setNgaySinh(ngaySinh);
        setSoDienThoai(sdt);
        setDiaChi(diaChi);
    }

    // ===== Getter =====
    public String getMaBenhNhan() { return maBenhNhan; }
    public String getHoTen() { return hoTen; }
    public LocalDate getNgaySinh() { return ngaySinh; }
    public String getSoDienThoai() { return soDienThoai; }
    public String getDiaChi() { return diaChi; }

    // ===== Setter (có kiểm tra hợp lệ) =====
    public void setMaBenhNhan(String ma) {
        if (ma == null || ma.trim().isEmpty())
            throw new IllegalArgumentException("Mã bệnh nhân không được để trống.");
        this.maBenhNhan = ma.trim();
    }

    public void setHoTen(String hoTen) {
        if (hoTen == null || hoTen.trim().isEmpty())
            throw new IllegalArgumentException("Họ tên không được để trống.");
        this.hoTen = hoTen.trim();
    }

    public void setNgaySinh(LocalDate ngaySinh) {
        if (ngaySinh == null)
            throw new IllegalArgumentException("Ngày sinh không được để trống.");
        if (ngaySinh.isAfter(LocalDate.now()))
            throw new IllegalArgumentException("Ngày sinh không được ở tương lai.");
        this.ngaySinh = ngaySinh;
    }

    public void setSoDienThoai(String sdt) {
        if (sdt == null || !sdt.trim().matches("\\d{9,11}"))
            throw new IllegalArgumentException("Số điện thoại phải gồm 9-11 chữ số.");
        this.soDienThoai = sdt.trim();
    }

    public void setDiaChi(String diaChi) {
        if (diaChi == null || diaChi.trim().isEmpty())
            throw new IllegalArgumentException("Địa chỉ không được để trống.");
        this.diaChi = diaChi.trim();
    }

    // ===== Phương thức trừu tượng: mỗi lớp con tính theo công thức riêng =====
    public abstract double tinhChiPhi();

    // ===== Hỗ trợ định dạng =====
    protected static String dinhDangTien(double soTien) {
        return String.format(Locale.US, "%,.0f VND", soTien);
    }

    @Override
    public String toString() {
        return String.format("Mã: %s | Họ tên: %s | Ngày sinh: %s | SĐT: %s | Địa chỉ: %s",
                maBenhNhan, hoTen, ngaySinh.format(DINH_DANG_NGAY), soDienThoai, diaChi);
    }
}
