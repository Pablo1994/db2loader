package Lectura;

import db2loader.VentanaEsperaController;

public class HiloEspera extends Thread {
    
    private int lineaActual;
    private int errores;
    private VentanaEsperaController _ventanaEspera;

    public HiloEspera(int lineaActual, int errores) {
        this.lineaActual = lineaActual;
        this.errores = errores;
        _ventanaEspera = VentanaEsperaController.getEspera_insta();
    }
    
    public HiloEspera() {
        this.lineaActual = 0;
        this.errores = 0;
    }
    
    public int getLineaActual() {
        return lineaActual;
    }
    
    public void setLineaActual(int lineaActual) {
        this.lineaActual = lineaActual;
    }
    
    public int getErrores() {
        return errores;
    }
    
    public void setErrores(int errores) {
        this.errores = errores;
    }
    
    @Override
    public void run() {
        actualizadatos();
    }

    private void actualizadatos() {
       VentanaEsperaController.getEspera_insta().getTxtLinea().setText(lineaActual + "");
    }
    
}
