package Logica;

import java.util.ArrayList;
import java.util.List;

public class Tabla {

    private List<Atributo> _atributos;
    private String _nombre;

    public Tabla(String _nombre) {
        this._nombre = _nombre;
        this._atributos = new ArrayList<>();
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

    public void agregarAtributo(String _nombre, Tipos _tipo, int tamano) {
        _atributos.add(new Atributo(_nombre, _tipo, tamano));
    }

}
