public class Broker{

    public static void main(String args[]) throws Exception{

        //Handles data base connection
        //JDBC.connect();

        //Writes to database
        //JDBC.WriteStringToDataBase("INSERT INTO plugnplay VALUES(DEFAULT, '50', '75', CURRENT_TIMESTAMP);");

        SerialTest serialReader = new SerialTest();
        serialReader.initialize();
        System.out.println("Started");
    }
}