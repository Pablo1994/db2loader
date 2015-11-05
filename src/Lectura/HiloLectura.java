package Lectura;

import Logica.Tabla;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HiloLectura extends Thread {

    private Lector _lector;
    private String _separador;

    public HiloLectura(File in, Tabla _tabla, String _separador) throws FileNotFoundException {
        this._lector = new Lector(in, _tabla);
        this._separador = _separador;
    }

    @Override
    public void run() {
        try {
            callLector();
        } catch (Exception ex) {
            Logger.getLogger(HiloLectura.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void callLector() throws Exception {
        _lector.carga(_separador);
    }

}
