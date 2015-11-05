package DB;
//jdbc:db2://localhost:50000/sample

import Logica.Atributo;
import Logica.Tabla;
import db2loader.VentanaInsertarController;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GestorDb2 extends Gestor {

    private static GestorDb2 instancia;
    private static final String _getTables = "SELECT TABLE_NAME FROM CAT WHERE TABLE_TYPE='TABLE' AND TABLE_SCHEMA=?",
            _getTableAtributtes = "SELECT DISTINCT(NAME), COLTYPE, LENGTH FROM SYSIBM.SYSCOLUMNS WHERE TBNAME = ?";

    public GestorDb2() throws Exception {
        super("sample", "50000", "usuario1", "usuario1", "localhost", "jdbc:db2:");
        getConnection();
    }

    public GestorDb2(String database, String puerto, String user, String password, String host, String url) throws Exception {
        super(database, puerto, user, password, host, url);
        getConnection();
    }

    public void actualizar(String db, String pt, String usuario, String clave, String h) throws Exception {
        System.out.print("Actualizando datos conexión");
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

    public int insertaRegistro(List<Object> datos) throws SQLException {
        Tabla t = VentanaInsertarController.getTabla();
        String tabla = t.getNombre();
        String atributos = "";
        List<Atributo> l = t.getOrden();
        for (int i = 0; i < l.size(); i++) {
            Atributo a = l.get(i);
            if (i < l.size() - 1) {
                atributos += a.getNombre() + ", ";
            } else {
                atributos += a.getNombre();
            }
        }
        System.out.println(atributos);
        String data = "";
        for (int i = 0; i < datos.size(); i++) {
            if (i < datos.size() - 1) {
                data += "'" + datos.get(i) + "'" + ", ";
            } else {
                data += "'" + datos.get(i) + "'";
            }
        }
        System.out.println(data);
        String _insertRow = "INSERT INTO " + tabla + " (" + atributos + ") VALUES (" + builtValues(datos) + ")";
        return builtPrepare(datos,_insertRow);

    }

    private String builtValues(List<Object> datos) {
        String str = "";
        for (int i = 0; i < datos.size(); i++) {
            if (i < datos.size() - 1) {
                str += " ? , ";
            } else {
                str += " ? ";
            }
        }
        return str;
    }

    private int builtPrepare(List<Object> datos, String query) throws SQLException {
        prepare = connection.prepareStatement(query);
        for (int i = 0; i < datos.size(); i++) {
            if (datos.get(i) instanceof Integer) {
                prepare.setInt((i + 1), Integer.parseInt(datos.get(i).toString()));
            } else if (datos.get(i) instanceof Float) {
                prepare.setFloat((i + 1), Float.parseFloat(datos.get(i).toString()));
            } else if (datos.get(i) instanceof String) {
                prepare.setString((i + 1), datos.get(i).toString());
            }
        }
       return  prepare.executeUpdate();
    }

    public static GestorDb2 getInstancia() throws Exception {
        if (instancia == null || instancia.connection.isClosed()) {
            instancia = new GestorDb2();
        }
        return instancia;
    }

    public void guardar() throws SQLException {
        prepare = connection.prepareStatement("INSERT INTO TEMPERATURA (N20_49, N250_499) VALUES (?,?)");
        prepare.setString(1, "01");
        prepare.setString(2, "001");
//        prepare.setString(3, articulo);
//        prepare.setString(4, provedor);
//        prepare.setString(5, nombre);
//        prepare.setString(6, bodega);
//        prepare.setFloat(7, cantidad);
        prepare.executeUpdate();
//    this.update("INSERT INTO TEMPERATURA (N20_49, N250_499) VALUES ('01', '001')");
    }

//    public static void main(String[] args)  {
//        GestorDb2 d;
//        try {
//            d = new GestorDb2();
//             d.guardar();
//        } catch (Exception ex) {
//            Logger.getLogger(GestorDb2.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        ResultSet result = d.tablasActuales();
//        while (result.next()) {
//            System.out.println(result.getString("TABLE_NAME"));
//        }
//        d.close(true);
}
