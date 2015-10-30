package Logica;

public class Atributo {
    private String _nombre;
    private Tipos _tipo;
    private int _tamano;
    private boolean activo;

    public Atributo(String _nombre, Tipos _tipo, int _tamano) {
        this._nombre = _nombre;
        this._tipo = _tipo;
        this._tamano = _tamano;
        activo=false;
    }

    public String getNombre() {
        return _nombre;
    }

    public void setNombre(String _nombre) {
        this._nombre = _nombre;
    }

    public Tipos getTipo() {
        return _tipo;
    }

    public void setTipo(Tipos _tipo) {
        this._tipo = _tipo;
    }

    public int getTamano() {
        return _tamano;
    }

    public void setTamano(int tamano) {
        this._tamano = tamano;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
    
}