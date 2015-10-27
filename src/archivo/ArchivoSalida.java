package archivo;

import java.io.File;
import java.io.FileWriter;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class ArchivoSalida {

    public boolean hayTexto(String cadena) {
        return !cadena.matches("[[ ]*[\n]*[\t]]*");
    }

    public void guardarArchivoTexto(String cadena) {

        JFileChooser elegirArchivo = new JFileChooser();
//        filtro para ver solo archivos .txt
        elegirArchivo.addChoosableFileFilter(new FileNameExtensionFilter("Archivos *.txt", "txt"));
        int seleccion = elegirArchivo.showSaveDialog(null);
        try {
            //comprueba si ha presionado el boton de aceptar
            if (seleccion == JFileChooser.APPROVE_OPTION) {
                File elegido = elegirArchivo.getSelectedFile();

                if (existeFichero(elegido)) {
                    System.out.println("Ya existe ese archivo");
                } else {
                    System.out.println("Nombre Valido");
                }
                if (elegido != null) {

                    FileWriter escritura = new FileWriter(elegido + ".log");
                    if (hayTexto(cadena)) {
                        escritura.append(cadena);
                        escritura.close();
                    }
                }
            }
        } catch (Exception e) {
        }
    }

    public void guardarArchivoError(File elegido, String cadena) {

        try {
            String nombre = elegido + "_bad.log";
            FileWriter escritura = new FileWriter(nombre);
            if (hayTexto(cadena)) {
                escritura.append(cadena);
                escritura.close();
            }
        } catch (Exception e) {
        }
    }

    private boolean existeFichero(File buscar) {
        return buscar.exists();
    }

}
