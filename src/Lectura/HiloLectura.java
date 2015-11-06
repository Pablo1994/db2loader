package Lectura;

import Logica.Tabla;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HiloLectura extends Thread {

    private Lector _lector;
 

    public HiloLectura(File in, Tabla _tabla, String _separador) throws FileNotFoundException {
        this._lector = new Lector(in, _tabla,_separador);

    }

//    @Override
//    public void run() {
//        try {
//            callLector();
//        } catch (Exception ex) {
//            Logger.getLogger(HiloLectura.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//
//    private void callLector() throws Exception {
//        _lector.run();
//    }

}
