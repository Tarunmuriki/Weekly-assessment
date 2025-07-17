import java.sql.*;
import java.util.Scanner;

public class EmployeeFetcher {

    private static final String DB_URL = "jdbc:mysql://bytexldb.com:5051/db_43pffu7nr?useSSL=false&allowPublicKeyRetrieval=true";
    private static final String USER = "user_43pffu7nr";
    private static final String PASSWORD = "p43pffu7nr";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Department ID: ");
        int departmentId = scanner.nextInt();

        String query = "SELECT * FROM employees WHERE department_id = ?";

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, departmentId);
            ResultSet rs = pstmt.executeQuery();

            System.out.printf("%-12s %-12s %-12s %-20s %-15s%n", "Employee ID", "First Name", "Last Name", "Position", "Department ID");
            System.out.println("-------------------------------------------------------------------------------");

            boolean found = false;
            while (rs.next()) {
                found = true;
                int id = rs.getInt("employee_id");
                String first = rs.getString("first_name");
                String last = rs.getString("last_name");
                String position = rs.getString("position");
                int dept = rs.getInt("department_id");

                System.out.printf("%-12d %-12s %-12s %-20s %-15d%n", id, first, last, position, dept);
            }

            if (!found) {
                System.out.println("No employees found in Department ID: " + departmentId);
            }

        } catch (SQLException e) {
            System.out.println("❌ Database error:");
            e.printStackTrace();
        }

        scanner.close();
    }
}
