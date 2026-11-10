# Hệ thống quản lý bệnh viện (Hospital Management System)

Ứng dụng **Java Console** quản lý bệnh nhân nội trú, ngoại trú và cấp cứu, lưu dữ liệu vào **MySQL** qua **JDBC**.
Bài tập nhóm môn *Lập trình hướng đối tượng với Java*.

> A Java console application for hospital patient management, built with OOP principles, JDBC and MySQL (DAO pattern).

## Công nghệ

| | |
|---|---|
| Ngôn ngữ | Java 21 |
| Cơ sở dữ liệu | MySQL |
| Kết nối | JDBC (MySQL Connector/J), PreparedStatement |
| IDE | Eclipse |

## Chức năng

- Thêm bệnh nhân theo 3 loại: nội trú, ngoại trú, cấp cứu
- Hiển thị danh sách và xuất báo cáo sức khỏe (nội trú, cấp cứu)
- Tính tổng chi phí toàn bệnh viện (mỗi loại có công thức riêng)
- Tìm kiếm bệnh nhân theo mã
- Kiểm tra dữ liệu nhập (sai kiểu, bỏ trống, ngoài khoảng giá trị, trùng mã) và yêu cầu nhập lại, không làm chương trình dừng đột ngột
- Lưu dữ liệu bền vững vào MySQL; nếu không kết nối được, chương trình vẫn chạy ở chế độ lưu trong bộ nhớ

## Kiến thức áp dụng

| Tính chất | Thể hiện trong mã nguồn |
|---|---|
| Đóng gói | Thuộc tính `private`, getter/setter có kiểm tra hợp lệ, ném `IllegalArgumentException` |
| Kế thừa | `BenhNhanNoiTru`, `BenhNhanNgoaiTru`, `BenhNhanCapCuu` kế thừa `BenhNhan` |
| Trừu tượng | `abstract class BenhNhan` với `tinhChiPhi()`; interface `IBaoCaoSucKhoe` |
| Đa hình | `@Override tinhChiPhi()`, duyệt `ArrayList<BenhNhan>`, `instanceof` |
| Truy cập dữ liệu | Lớp `BenhNhanDAO` chứa lệnh SQL (INSERT, SELECT), `KetNoiCSDL` tạo kết nối |

## Cấu trúc dự án

```
src/
 ├── entity/    BenhNhan, BenhNhanNoiTru, BenhNhanNgoaiTru, BenhNhanCapCuu, IBaoCaoSucKhoe
 ├── dao/       KetNoiCSDL, BenhNhanDAO
 ├── service/   QuanLyBenhVien
 └── main/      Main (menu)
lib/            mysql-connector-j (file .jar)
database.sql    lệnh SQL tạo cơ sở dữ liệu và bảng
```

## Cách chạy

1. Cài **JDK 21** và **MySQL**.
2. Chạy file `database.sql` (MySQL Workbench hoặc dòng lệnh) để tạo cơ sở dữ liệu `quanly_benhvien`.
3. Mở project bằng Eclipse (**File → Import → Existing Projects into Workspace**).
4. Thêm file `.jar` trong thư mục `lib` vào Build Path (nếu chưa có).
5. Mở `src/dao/KetNoiCSDL.java`, sửa `USER` và `PASSWORD` cho đúng với MySQL trên máy.
6. Chạy `main.Main`.

> Không có MySQL? Chương trình vẫn chạy ở chế độ lưu dữ liệu trong bộ nhớ.

## Kiểm thử

28 kịch bản kiểm thử thủ công (dữ liệu hợp lệ, giá trị biên, dữ liệu lỗi, lưu trữ CSDL). Ví dụ: 3 bệnh nhân mẫu có chi phí 2.800.000, 400.000 và 2.300.000 VND, tổng chi phí 5.500.000 VND.

<!-- Thêm ảnh minh họa vào thư mục docs/ rồi bỏ dấu chú thích:
![Menu](docs/menu.png)
![Danh sách bệnh nhân](docs/danh-sach.png)
![Dữ liệu trong MySQL](docs/mysql.png)
-->

## Tác giả

Bài tập nhóm 4 thành viên. Vai trò của mình: nhóm trưởng, lập trình chính (toàn bộ mã nguồn Java), viết báo cáo Chương 4, 5 và thực hiện demo.
