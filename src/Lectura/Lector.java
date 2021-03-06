/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Lectura;

import DB.GestorDb2;
import Logica.Atributo;
import Logica.Tabla;
import db2loader.Db2loader;
import db2loader.VentanaEsperaController;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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
    private String outFile;
    private String errFile;
    private String nombreArchivo;
    private int errores;
    private int inserciones;
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

    public Lector(File in, String out, String err, Tabla _tabla, String _hilera) throws FileNotFoundException {
        super(new FileReader(in));
        try {
            this.gestor = GestorDb2.getInstancia();
            this._hilera = _hilera;
            this.outFile = out;
            this.errFile = err;
            this.nombreArchivo = in.getName();
            File fOut = new File(out);
            File fErr = new File(err);
            System.setOut(new PrintStream(fOut));
            System.setErr(new PrintStream(fErr));
        } catch (Exception ex) {
            Logger.getLogger(Lector.class.getName()).log(Level.SEVERE, null, ex);
            System.out.print(ex.getMessage());
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

        //System.out.println(str);
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
        //System.out.println(str);
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
        //System.out.println(str);
    }

//    public synchronized void carga() throws Exception {
//        String lineaActual;
//        int lineNum = 0;
//
//        while ((lineaActual = readLine()) != null) {
//            lineNum++;
//            _datos = lineaActual.split(_hilera);
//            //Ojo acá, no se puede hacer el listaLimpia si los dos arreglos no son del mismo tamaño, porque tira una excepción que tenemos que controlar desde antes.
////            if (_datos.length != _tabla.getOrden().size()) // misma cantidad de atributos
////            {
////                _listaLimpia = _tabla.listaLimpia(_datos);
////            }
//
//            if (_datos.length != _tabla.getOrden().size() || _listaLimpia == null || //parseo de Datos
//                    !_tabla.lengthCheck(_listaLimpia)) // tamaño de los datos
//            {
//                try {
//                    aumentaErrores(); 
//                } catch (Exception e) {
//                    
//                }
//            } else {
//                try {
//                    int n = gestor.insertaRegistro(_listaLimpia);
//                    aumentaLinea(lineNum);
//                } catch (SQLException ex) {
//                    Logger.getLogger(Lector.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
//
//        }
////gestor.exceuteBatch();
//        gestor.commit();
//        
//        Db2loader.getControlEspera()
//                .finalizado();
//        success(
//                "Se ha leido: " + lineNum + " líneas");
//    }
    public synchronized void carga() throws Exception {
        String lineaActual;
        int lineNum = 0;
        Date d = new Date();
        DateFormat df = new SimpleDateFormat("EEE dd MMM,yyyy HH:mm:ss");
        String inicio = df.format(d);

        System.out.println("Carga del archivo: " + this.nombreArchivo + "\n");
        System.out.println("Archivo de log: " + this.outFile);
        System.out.println("Archivo de errores: " + this.errFile + "\n\n");

        System.out.println("Tabla " + _tabla.getNombre() + "\n");
        System.out.println("Columna\t\t\t\t\tTipo");
        System.out.println("-------\t\t\t\t\t----");
        _tabla.getAtributos().stream().forEach((a) -> {
            System.out.println(a.getNombre() + "\t\t\t\t\t" + a.getTipo());
        });
        System.out.println("-----------------");
        while ((lineaActual = readLine()) != null) {
            VentanaEsperaController.getEspera_insta().getTxtLinea().setText(String.valueOf(lineNum));
            lineNum++;
            _datos = lineaActual.split(_hilera);
            if (_datos.length == _tabla.getOrden().size()) // misma cantidad de atributos
            {
                _listaLimpia = _tabla.listaLimpia(_datos);
            }
            String str = "";
            for (int i = 0; i < _datos.length; i++) {
                if (i < _datos.length - 1) {
                    str += "'" + _datos[i] + "'" + ", ";
                } else {
                    str += "'" + _datos[i] + "'";
                }
            }

            if (_listaLimpia == null || //parseo de Datos
                    !_tabla.lengthCheck(_listaLimpia)) // tamaño de los datos
            {
                aumentaErrores();
                error(lineNum);

                System.err.println(str);
            } else {
                try {
                    int n = gestor.insertaRegistro(_listaLimpia);
                    aumentaLinea(lineNum);
                    int[] ins = {};
                    if (lineNum%10000==0) {
                        try {
                            gestor.exceuteBatch();    
                            //gestor.commit();
                        } catch (SQLException ex) {
                            ex.forEach(e -> {
                                if (errores > 1) {
                                    System.err.println(e.getMessage());
                                }
                                aumentaErrores();
                            });
                            disminuyeErrores();
                        }
                    }
                } catch (SQLException ex) {
                    aumentaErrores();
                    System.err.println(str);
                    Logger.getLogger(Lector.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        int[] ins = {};
        try {
            ins = gestor.exceuteBatch();
        } catch (SQLException ex) {
            ex.forEach(e -> {
                if(errores>1){
                    System.err.println(e.getMessage());   
                }
                aumentaErrores();
            });
            disminuyeErrores();
        }
        //gestor.commit();

        Db2loader.getControlEspera()
                .finalizado();
        Date dfin = new Date();
        String tFinal = df.format(dfin);
        
        long diff = d.getTime() - dfin.getTime();
        long diffSeconds = diff / 1000 % 60;
        long diffMinutes = diff / (60 * 1000) % 60;
        long diffHours = diff / (60 * 60 * 1000);
        
        String elapsedTime = diffHours+":"+-diffMinutes+":"+-diffSeconds;

        System.out.println(
                "Se han leido: " + lineNum + " líneas");
        System.out.println(
                "Registros insertados: " + inserciones);
        System.out.println(
                "Errores: " + errores + "\n");
        System.out.println(
                "Iniciado en: " + inicio);
        System.out.println(
                "Finalizado en: " + tFinal);
        System.out.println("Tiempo Transcurrido: "+elapsedTime);
        VentanaEsperaController.getEspera_insta().getTxtTiempo().setText(elapsedTime);
        //success("Se ha leido: " + lineNum + " líneas");

    }

    private void error(int linea) {
        System.out.println("El formato no se cumple. Línea: " + linea);
        //JOptionPane.showMessageDialog(null, "El formato No se Cumple Linea: " + linea, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void success(String msg) {
        JOptionPane.showMessageDialog(null, msg, "Información", JOptionPane.INFORMATION_MESSAGE);
    }

    private synchronized void aumentaErrores() {
        errores = Integer.parseInt(Db2loader.getControlEspera().getTxtErrores().getText());
        Db2loader.getControlEspera().getTxtErrores().setText(String.valueOf(++errores));
    }

    private synchronized void disminuyeErrores() {
        errores = Integer.parseInt(Db2loader.getControlEspera().getTxtErrores().getText());
        Db2loader.getControlEspera().getTxtErrores().setText(String.valueOf(--errores));
    }

    private synchronized void aumentaLinea(int lineNum) {
        inserciones += 1;
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
