/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Lectura;

import DB.GestorDb2;
import Logica.Tabla;
import db2loader.Db2loader;
import db2loader.VentanaEsperaController;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public final class Lector extends BufferedReader {

    private Tabla _tabla; //Logica de la tabla
    private String[] _datos;
    private List<Object> _listaLimpia;
    private GestorDb2 gestor;
    private HiloEspera _espera;
    private String _hilera;

//    public Lector(FileReader in) {
//        super(in);
//        try {
//            this.gestor = GestorDb2.getInstancia();
//        } catch (Exception ex) {
//            Logger.getLogger(Lector.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
    public Lector(File in, Tabla _tabla, String _hilera) throws FileNotFoundException {
        super(new FileReader(in));
        try {
            this.gestor = GestorDb2.getInstancia();
            this._hilera = _hilera;
        } catch (Exception ex) {
            Logger.getLogger(Lector.class.getName()).log(Level.SEVERE, null, ex);
        }
        this._tabla = _tabla;
    }

    public void leer() throws IOException {
        String lineaActual;
        String str = "";
        int lineNum = 1;
        while ((lineaActual = readLine()) != null) {

            str += lineaActual + '\n';
            lineNum++;
        }

        System.out.println(str);

    }

    public void carga(String separador, int inicio, int fin) throws IOException {
        String lineaActual;
        String str = "";
        int lineNum = 1;
        while ((lineaActual = readLine()) != null || lineNum < fin) {
            if (inicio <= lineNum) {
                _datos = lineaActual.split(separador);
                if (_datos.length != _tabla.getOrden().size()) {
                    error(lineNum);
                    return;
                }
                str += lineaActual + '\n';
            }
            lineNum++;
        }
        System.out.println(str);
    }

    public void carga(String separador, int inicio) throws IOException {
        String lineaActual;
        String str = "";
        int lineNum = 1;
        while ((lineaActual = readLine()) != null) {
            if (inicio <= lineNum) {
                _datos = lineaActual.split(separador);
                if (_datos.length != _tabla.getOrden().size()) {
                    error(lineNum);
                    return;
                }
                str += lineaActual + '\n';
            }
            lineNum++;
        }
        System.out.println(str);
    }

    public synchronized void carga() throws Exception {
        String lineaActual;
        int lineNum = 0;

        while ((lineaActual = readLine()) != null) {
            lineNum++;
            _datos = lineaActual.split(_hilera);
            //Ojo acá, no se puede hacer el listaLimpia si los dos arreglos no son del mismo tamaño, porque tira una excepción que tenemos que controlar desde antes.
//            if (_datos.length != _tabla.getOrden().size()) // misma cantidad de atributos
//            {
//                _listaLimpia = _tabla.listaLimpia(_datos);
//            }

            if (_datos.length != _tabla.getOrden().size() || _listaLimpia == null || //parseo de Datos
                    !_tabla.lengthCheck(_listaLimpia)) // tamaño de los datos
            {
                try {
                    aumentaErrores();
                } catch (Exception e) {
                    
                }
            } else {
                try {
                    int n = gestor.insertaRegistro(_listaLimpia);
                    aumentaLinea(lineNum);
                } catch (SQLException ex) {
                    Logger.getLogger(Lector.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }

        gestor.commit();

        Db2loader.getControlEspera()
                .finalizado();
        success(
                "Se ha leido: " + lineNum + " líneas");
    }

    private void error(int linea) {
        JOptionPane.showMessageDialog(null, "El formato No se Cumple Linea: " + linea, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void success(String msg) {
        JOptionPane.showMessageDialog(null, msg, "Información", JOptionPane.INFORMATION_MESSAGE);
    }

    private synchronized void aumentaErrores() {
        int errores = Integer.parseInt(Db2loader.getControlEspera().getTxtErrores().getText());
        Db2loader.getControlEspera().getTxtErrores().setText(String.valueOf(++errores));
    }

    private synchronized void aumentaLinea(int lineNum) {
        Db2loader.getControlEspera().getTxtLinea().setText(String.valueOf(lineNum));
    }

    @Override
    public String toString() {
        return "";
    }

//    @Override
//    public void run() {
//        try {
//            carga();
//        } catch (Exception ex) {
//            Logger.getLogger(Lector.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
}
