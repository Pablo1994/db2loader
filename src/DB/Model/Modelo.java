package DB.Model;

import DB.GestorDb2;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Modelo {

    private static Modelo instancia;
    private GestorDb2 _gestor;
    private ResultSet _consulta;

    public static Modelo getInstancia() throws Exception {
        if (instancia == null) {
            instancia = new Modelo();
        }
        instancia._gestor = GestorDb2.getInstancia();
        return instancia;
    }

    public List<String> listaTablasActuales() throws SQLException {
        List<String> tablas = new ArrayList<>();
        _consulta = _gestor.tablasActuales();
        while (_consulta.next()) {
            tablas.add(_consulta.getString("TABLE_NAME"));
        }
        return tablas;
    }

    public void actualizar(String db, String pt, String usuario, String clave, String h) {
        try {
            //actualiza los parametros de conexion a la BD
            _gestor.actualizar(db, pt, usuario, clave, h);
            //intenta conectarse a la BD
            _gestor.conexion();
        } catch (Exception e) {
        }
    }
    
    public String resultado(){
      return  _gestor.getResultadoConexion();
    }

}
