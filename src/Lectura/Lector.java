/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Lectura;

import DB.GestorDb2;
import Logica.Tabla;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class Lector extends BufferedReader {

    private Tabla _tabla; //Logica de la tabla
    private String[] _datos;
    GestorDb2 gestor;

    public Lector(FileReader in) {
        super(in);
        try {
            this.gestor = GestorDb2.getInstancia();
        } catch (Exception ex) {
            Logger.getLogger(Lector.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Lector(File in, Tabla _tabla) throws FileNotFoundException {
        super(new FileReader(in));
        try {
            this.gestor = GestorDb2.getInstancia();
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

    public void carga(String separador) throws IOException {
        String lineaActual;
        String str = "";
        int lineNum = 0;
        while ((lineaActual = readLine()) != null) {
            lineNum++;
            _datos = lineaActual.split(separador);
            if (_datos.length != _tabla.getOrden().size() || _tabla.listaLimpia(_datos) == null) {
                error(lineNum);
                return;
            } else {
                try {
                    int n = gestor.insertaRegistro(_tabla.listaLimpia(_datos));
                    gestor.commit();
                    JOptionPane.showMessageDialog(null, "Registros actualizados: "+n, "Éxito", JOptionPane.PLAIN_MESSAGE);
                } catch (SQLException ex) {
                    Logger.getLogger(Lector.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            str += lineaActual + '\n';
        }
        System.out.println(str);
    }

    private void error(int linea) {
        JOptionPane.showMessageDialog(null, "El formato No se Cumple Linea: " + linea, "Error", JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public String toString() {
        return "";
    }

}
