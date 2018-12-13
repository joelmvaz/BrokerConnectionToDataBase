public class Main{
    public static void main(String args[]) throws Exception{
        Main m = new Main();
        JDBC.connect();

        //MDN sql prepare
        
        JDBC.WriteStringToDataBase("INSERT INTO plugnplay VALUES(DEFAULT, '50', '75', CURRENT_TIMESTAMP);");
    }
}