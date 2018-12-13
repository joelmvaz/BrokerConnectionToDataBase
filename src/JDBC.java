import java.sql.*;

public class JDBC {
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_RESET = "\u001B[0m";
    private static Connection con = null;

    public static void connect() {
        //"jdbc:postgresql://localhost:5432/testdb"
        String host = "jdbc:postgresql://db.fe.up.pt:5432/ee11271";
        String username = "ee11271"; //"up201502825";
        String password = "wooper_30"; //"VkgSgU9P8";

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            con = DriverManager.getConnection(host, username, password);
            System.out.println(ANSI_BLUE + "-- DataBase connected " + host + "--" + ANSI_RESET);

            //Example Read
            //Example write

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    //Functions for database

    public static String ReadFromDataBase(String select, String line) throws SQLException {
        //String select = "SELECT n_tipo_peca FROM armazem WHERE tipo_peca = '2'";
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(select);
        String read = "";

        while (rs.next()) {
            read = rs.getString(line);
        }
        return read;
    }

    public static void WriteStringToDataBase(String write) {
        try {
            Statement myStmt = con.createStatement();
            myStmt.executeUpdate(write);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}