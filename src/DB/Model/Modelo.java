package DB.Model;

import DB.GestorDb2;

import archivo.ArchivoEntrada;
import java.io.File;
import Logica.Tabla;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Modelo {

    private static Modelo instancia;
    private GestorDb2 _gestor;
    private ResultSet _consulta;
    private ArchivoEntrada archivo;

    public static Modelo getInstancia() throws Exception {
        if (instancia == null) {
            instancia = new Modelo();
        }
        instancia._gestor = GestorDb2.getInstancia();
        instancia.archivo = new ArchivoEntrada();
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

        } catch (Exception e) {
        }
    }

    public boolean conexion() {
        return _gestor.conexion();
    }

    public void cargarArchivo(File arch) {
        archivo.cargarArchivoTexto(arch);
        System.out.println(archivo.mostarArchivoTexto());
    }


    public Tabla builtTabla(String nombre) throws SQLException {
        Tabla tabla = new Tabla(nombre);
        _consulta = _gestor.tablaAtributos(nombre);
        while (_consulta.next()) {
            tabla.agregarAtributo(_consulta.getString("NAME"), _consulta.getString("COLTYPE"), _consulta.getInt("LENGTH"));
        }
        return tabla;
    }

//    public String resultado() {
//        return _gestor.getResultadoConexion();
//    }
}
