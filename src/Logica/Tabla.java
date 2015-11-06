package Logica;

import java.util.ArrayList;
import java.util.List;

public class Tabla {

    private List<Atributo> _atributos;
    private List<Atributo> _orden;
    private String _nombre;

    public Tabla(String _nombre) {
        this._nombre = _nombre;
        this._atributos = new ArrayList<>();
        _orden = new ArrayList<>();
    }

    public List<Atributo> getAtributos() {
        return _atributos;
    }

    public void setAtributos(List<Atributo> _atributos) {
        this._atributos = _atributos;
    }

    public String getNombre() {
        return _nombre;
    }

    public void setNombre(String _nombre) {
        this._nombre = _nombre;
    }

    public Tipos toType(String tipo) {
        switch (tipo.toUpperCase().replaceAll("\\s+", "")) {
            case "VARCHAR":
                return Tipos.VARCHAR;
            case "INTEGER":
                return Tipos.INTEGER;
            case "FLOAT":
                return Tipos.FLOAT;
            case "DOUBLE":
                return Tipos.DOUBLE;
            case "TIMESTAMP":
                return Tipos.TIMESTAMP;
            case "DATE":
                return Tipos.DATE;
            case "BIGINT":
                return Tipos.BIGINT;
            case "BINARY":
                return Tipos.BINARY;
            case "BLOB":
                return Tipos.BLOB;
            case "CHARACTER":
                return Tipos.CHARACTER;
            case "CLOB":
                return Tipos.CLOB;
            case "DBCLOB":
                return Tipos.DBCLOB;
            case "DECIMAL":
                return Tipos.DECIMAL;
            case "GRAPHIC":
                return Tipos.GRAPHIC;
            case "REAL":
                return Tipos.REAL;
            case "SMALLINT":
                return Tipos.SMALLINT;
            case "TIME":
                return Tipos.TIME;
            case "VARBINARY":
                return Tipos.VARBINARY;
            case "VARGRAPHIC":
                return Tipos.VARGRAPHIC;
            case "CHAR":
                return Tipos.CHAR;
            default:
                return null;
        }
    }

    public void agregarAtributo(String _nombre, String _tipo, int tamano) {
        _atributos.add(new Atributo(_nombre, toType(_tipo), tamano));
    }

    public List<Atributo> getOrden() {
        return _orden;
    }

    public void setOrden(List<String> orden) {
        _orden = new ArrayList<>();
        orden.stream().forEach((s) -> {
            _orden.add(findAtr(s));
        });
    }

    public Atributo findAtr(String str) {
        for (Atributo a : _atributos) {
            if (str.equals(a.getNombre())) {
                return a;
            }
        }
        return null;
    }

    public List<Object> listaLimpia(String[] datos) {
        List<Object> limpia = new ArrayList<>();
        for (int i = 0; i < datos.length; i++) {
            if (_orden.get(i).getParse(datos[i]) != null) {
                limpia.add(_orden.get(i).getParse(datos[i]));
            } else {
                return null;
            }
        }
        return limpia;
    }

    public boolean lengthCheck(List<Object> lista) {
        for (int i = 0; i < lista.size(); i++) {
            if (_orden.get(i).getTamano() < lista.get(i).toString().length()) {
                return false;
            }
        }
        return true;
    }

}
