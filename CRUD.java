import java.util.*;
import java.sql.*;
import java.sql.Date;

public class CRUD {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Connection con = null;

        try {
            // Establish database connection
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/supra";
            String username = "root";
            String password = "0800";
            con = DriverManager.getConnection(url, username, password);

            int n = 1;
            while (n != 0) {
                System.out.println("1: Insert Data");
                System.out.println("2: Delete Data");
                System.out.println("3: Update Data");
                System.out.println("4: Display Full Table Data");
                System.out.println("5: Display Particular Data");
                System.out.println("6: Exit");
                System.out.print("Enter Choice: ");
                int choice = sc.nextInt();

                switch (choice) {
                    // insert
                    case 1:
                        try {
                            String q = "INSERT INTO student (std_id, name, dob) VALUES(?,?,?)";
                            PreparedStatement prst = con.prepareStatement(q);
                            System.out.print("How many data you want to insert: ");
                            int count = sc.nextInt();
                            for (int i = 0; i < count; i++) {
                                System.out.println("Enter id(number)...");
                                int a = sc.nextInt();
                                sc.nextLine(); // Consume the newline character
                                System.out.println("Enter Name...");
                                String b = sc.nextLine();
                                System.out.println("Enter DOB...");
                                String c = sc.nextLine();
                                prst.setInt(1, a);
                                prst.setString(2, b);
                                prst.setString(3, c);
                                prst.executeUpdate();
                            }
                            System.out.println("Data insertion success..");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;
                    // delete
                    case 2:
                        try {
                            System.out.print("Enter id to delete OR press 0 to delete all: ");
                            int a = sc.nextInt();
                            if(a==0){
                                String q = "DELETE FROM student";
                                Statement stmt = con.createStatement();
                                stmt.executeUpdate(q);
                            }else{
                                String q = "DELETE FROM student WHERE std_id = ?";
                                PreparedStatement prst = con.prepareStatement(q);
                                prst.setInt(1, a);
                                prst.executeUpdate();
                            }
                        } catch (Exception e) {
                            e.getStackTrace();
                        }
                        System.out.println("Data delete success..");
                        break;
                    // update
                    case 3:
                        try {
                            String q = "update student set name = ?, dob = ? where std_id = ?";
                            PreparedStatement prst = con.prepareStatement(q);
                            System.out.print("Enter id to update: ");
                            int a = sc.nextInt();
                            sc.nextLine();
                            System.out.print("Enter updated name: ");
                            String b = sc.nextLine();
                            System.out.print("Enter updated dob: ");
                            String c = sc.nextLine();
                            prst.setInt(3, a);
                            prst.setString(1, b);
                            prst.setString(2, c);
                            prst.executeUpdate();
                        } catch (Exception e) {
                            e.getStackTrace();
                        }
                        System.out.println("Data update success...");
                        break;
                        //get full table
                    case 4:
                        try {
                            String q = "select * from student";
                            // fire query
                            Statement stmt = con.createStatement();
                            // store data as table
                            ResultSet set = stmt.executeQuery(q);
                            // iterate through the result set
                            while (set.next()) {
                                int id = set.getInt(1);
                                String name = set.getString(2);
                                Date dob = set.getDate(3);
                                System.out.println("ID: " + id + " ,Name: " + name + " ,DOB: " + dob);
                            }

                        } catch (Exception e) {
                            e.getStackTrace();
                        }
                        break;
                        //get particular
                    case 5:
                        try {
                            String q = "select * from student where std_id = ?";
                            PreparedStatement prst = con.prepareStatement(q);
                            System.out.print("Enter id to display data: ");
                            int a = sc.nextInt();
                            prst.setInt(1, a);
                            ResultSet set = prst.executeQuery();
                    
                            if (set.next()) {
                                int id = set.getInt(1);
                                String name = set.getString(2);
                                Date dob = set.getDate(3);
                                System.out.println("ID: " + id + " ,Name: " + name + " ,DOB: " + dob);
                            } else {
                                System.out.println("No data found for the given ID.");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        break;                    
                    case 6:
                        n = 0;
                        System.out.println("Exit success, happy coding!..");
                        break;
                    default:
                        System.out.println("Invalid Input, Enter again..");
                }
                System.out.println();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // free resources
        sc.close();
    }
}
