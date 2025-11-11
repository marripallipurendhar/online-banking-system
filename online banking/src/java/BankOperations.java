import java.sql.*;

public class BankOperations {

    public static User login(String username, String password) {
        try (Connection con = DBConnection.getConnection()) {
            String sql = "SELECT * FROM users WHERE username=? AND password=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, username);
            pst.setString(2, password);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                return new User(rs.getInt("id"), rs.getString("username"), rs.getDouble("balance"));
            }
        } catch (Exception e) {
        }
        return null;
    }

    public static boolean register(String username, String password) {
        try (Connection con = DBConnection.getConnection()) {
            String sql = "INSERT INTO users(username, password, balance) VALUES (?, ?, 0)";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, username);
            pst.setString(2, password);
            return pst.executeUpdate() > 0;
        } catch (Exception e) {
        }
        return false;
    }

    public static void updateBalance(User user) {
        try (Connection con = DBConnection.getConnection()) {
            String sql = "UPDATE users SET balance=? WHERE id=?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setDouble(1, user.getBalance());
            pst.setInt(2, user.getId());
            pst.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
