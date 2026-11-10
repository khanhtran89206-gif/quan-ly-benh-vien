package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/** Tạo kết nối tới MySQL. Sửa USER/PASSWORD cho đúng với máy của bạn. */
public class KetNoiCSDL {
    private static final String URL =
            "jdbc:mysql://localhost:3306/quanly_benhvien"
            + "?useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Ho_Chi_Minh";
    private static final String USER = "root";
    private static final String PASSWORD = "";   // XAMPP: để trống. MySQL cài riêng: mật khẩu bạn đặt

    public static Connection layKetNoi() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
