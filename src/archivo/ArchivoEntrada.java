package archivo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ArchivoEntrada {

    public ArchivoEntrada() {
        arreglo1 = new ArrayList<>();
        arreglo2 = new ArrayList<>();
        arreglo3 = new ArrayList<>();
        arreglo4 = new ArrayList<>();
        arreglo5 = new ArrayList<>();

    }

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

    public void limpiar() {
        arreglo1.clear();
        arreglo2.clear();
        arreglo3.clear();
        arreglo4.clear();
        arreglo5.clear();
    }

    //cargar archivo txt
    private void cargarTexto(BufferedReader br) throws IOException {
        System.out.println("Inico paso de informacion al arreglo");
        String linea;
        limpiar();
        while ((linea = br.readLine()) != null) {

            if (arreglo1.size() < 0b10010010011111000000) {//600000
                arreglo1.add(linea);
            } else {
                if (arreglo2.size() < 0b10010010011111000000) {//600000
                    arreglo2.add(linea);
                } else {
                    if (arreglo3.size() < 0b10010010011111000000) {//600000
                        arreglo3.add(linea);
                    } else {
                        if (arreglo4.size() < 0b10010010011111000000) {//600000
                            arreglo4.add(linea);
                        } else {
                            if (arreglo5.size() < 0b10010010011111000000) {//600000
                                arreglo5.add(linea);
                            }
                        }
                    }
                }
            }
        }
        System.out.println("Arreglo cargado!!!!");
        System.out.println("Tamaño arreglo1: " + arreglo1.size());
        System.out.println("Tamaño arreglo2: " + arreglo2.size());
        System.out.println("Tamaño arreglo3: " + arreglo3.size());
        System.out.println("Tamaño arreglo4: " + arreglo4.size());
        System.out.println("Tamaño arreglo5: " + arreglo5.size());
        long total = arreglo1.size() + arreglo2.size() + arreglo3.size()
                + arreglo4.size() + arreglo5.size();
        System.out.println("Total: " + total);

    }

    //muestra el arreglo con la info del archivo seleccionado por el usuario
    public String mostarArchivoTexto() {
        StringBuilder s = new StringBuilder();

        arreglo1.stream().forEach((infoArchivo1) -> {
            s.append(infoArchivo1).append("\n");
        });

        return s.toString();
    }

    private ArrayList<String> arreglo1, arreglo2, arreglo3, arreglo4, arreglo5;
//    private static ArchivoEntrada instancia;

}
