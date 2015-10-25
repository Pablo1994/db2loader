package archivo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ArchivoEntrada {

    public ArchivoEntrada() {
    infoArchivoEntrada = new ArrayList<>();
    }
    
    

//    public static ArchivoEntrada getInstancia() {
//        if (instancia == null) {
//            instancia = new ArchivoEntrada();
//        }
//        instancia.infoArchivoEntrada= new ArrayList<>();
//        return instancia;
//    }

    public void cargarArchivoTexto(File fich) {
        try {
            try (FileReader fr = new FileReader(fich.getAbsolutePath())) {
                BufferedReader br = new BufferedReader(fr);
                cargarTexto(br);
            }
        } catch (Exception e) {
            System.err.println("Excepcion capturada " + fich.getAbsolutePath() + ": " + e);
        }
    }

    //cargar archivo txt
    private void cargarTexto(BufferedReader br) throws IOException {
        String linea;
        infoArchivoEntrada.clear();
        while ((linea = br.readLine()) != null) {
            infoArchivoEntrada.add(linea);
        }
    }

    //muestra el arreglo con la info del archivo seleccionado por el usuario
    public String mostarArchivoTexto() {
        StringBuilder s = new StringBuilder();
        infoArchivoEntrada.stream().forEach((infoArchivo1) -> {
            s.append(infoArchivo1).append("\n");
        });

        return s.toString();
    }

    private ArrayList<String> infoArchivoEntrada;
//    private static ArchivoEntrada instancia;

}
