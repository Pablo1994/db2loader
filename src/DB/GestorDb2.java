package DB;
//jdbc:db2://localhost:50000/sample

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class GestorDb2 extends Gestor {

    private static GestorDb2 instancia;
    private static final String _getTables = "SELECT TABLE_NAME FROM CAT WHERE TABLE_TYPE='TABLE' AND TABLE_SCHEMA=?",
            _getTableAtributtes = "SELECT DISTINCT(NAME), COLTYPE, LENGTH FROM SYSIBM.SYSCOLUMNS WHERE TBNAME = ?";

    public GestorDb2() throws Exception {
        super("sample", "50000", "db2admin", "manager", "localhost", "jdbc:db2:");
        getConnection();
    }

    public GestorDb2(String database, String puerto, String user, String password, String host, String url) throws Exception {
        super(database, puerto, user, password, host, url);
        getConnection();
    }

    public void actualizar(String db, String pt, String usuario, String clave, String h) throws Exception {
        getInstancia().database = db;
        getInstancia().puerto = pt;
        getInstancia().user = usuario;
        getInstancia().password = clave;
        getInstancia().host = h;
    }

    public boolean conexion() {
        return getConnection();
    }

    @Override
    protected boolean getConnection() {
        try {
            connection = DriverManager.getConnection(url + "//" + host + ":" + puerto + "/" + database + ""
                    + ":user=" + user + ";password=" + password + ";"
                    + "traceLevel="
                    + (com.ibm.db2.jcc.DB2BaseDataSource.TRACE_ALL) + ";");
            connection.setAutoCommit(false); // set auto-commit false
            statement = (Statement) connection.createStatement();
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    public ResultSet tablasActuales() throws SQLException {
        prepare = connection.prepareStatement(_getTables);
        prepare.setString(1, user.toUpperCase());
        return prepare.executeQuery();
    }

    public ResultSet tablaAtributos(String tabla) throws SQLException {
        prepare = connection.prepareStatement(_getTableAtributtes);
        prepare.setString(1, tabla.toUpperCase());
        return prepare.executeQuery();
    }

    public static GestorDb2 getInstancia() throws Exception {
        if (instancia == null || instancia.connection.isClosed()) {
            instancia = new GestorDb2();
        }
        return instancia;
    }

//    public static void main(String[] args) throws Exception {
//        GestorDb2 d = new GestorDb2();
//        ResultSet result = d.tablasActuales();
//        while (result.next()) {
//            System.out.println(result.getString("TABLE_NAME"));
//        }
//        d.close(true);
//    }
    
}
