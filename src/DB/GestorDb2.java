package DB;
//jdbc:db2://localhost:50000/sample
import java.sql.DriverManager;
import java.sql.Statement;

public class GestorDb2 extends Gestor {

    public GestorDb2() throws Exception {
        super("sample", "50000", "usuario1", "usuario1", "localhost", "jdbc:db2:");
        getConnection();
    }

    public GestorDb2(String database, String puerto, String user, String password, String host, String url) {
        super(database, puerto, user, password, host, url);
    }

    @Override
    protected void getConnection() throws Exception {
        //com.ibm.db2.jcc.DB2Driver
//        Class.forName("com.ibm.db2.jcc.DB2Driver");
        connection = DriverManager.getConnection(url + database, user, password);
        connection.setAutoCommit(false); // set auto-commit false
        statement = (Statement) connection.createStatement();
    }

    public static void main(String[] args) throws Exception {
        GestorDb2 d = new GestorDb2();
    }

}
