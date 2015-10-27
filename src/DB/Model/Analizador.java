package DB.Model;

public class Analizador {

    public Analizador() {
        separador = ',';
    }

    public boolean esSalto(String palabra, int pos) {
        return palabra.charAt(pos) == '\n';
    }

    public boolean esSeparador(char s) {
        return s == separador;
    }

    public String buscaInsert(String hilera) {

        String nueva = "";
        String result = "";
//        System.out.println("Hilera: " + hilera);
        for (int i = 0; i < hilera.length(); i++) {
            if (esSeparador(hilera.charAt(i))) {
                if (nueva.length() > 0) {
//                    System.out.println(" Campo:  " + nueva);
                    result += nueva + ",";
                    nueva = "";
                }
            } else {
                nueva += hilera.charAt(i);
            }
        }
        if (nueva.length() > 0) {
//            System.out.println(" Campo:  " + nueva);
              result += nueva ;
        }
        return result;
    }

    public void setSeparador(char separador) {
        this.separador = separador;
    }

    /*
     public void buscaHilera(String hilera) {

     String nuevaHilera = "";
     for (int i = 0; i < hilera.length(); i++) {
     if (Analizador.esSalto(hilera, i)) {
     if (nuevaHilera.length() > 0) {
     arregloHileras.add(nuevaHilera);
     }
     nuevaHilera = "";
     } else {
     nuevaHilera += hilera.charAt(i);
     }
     }
     if (nuevaHilera.length() > 0) {
     arregloHileras.add(nuevaHilera);
     }
     }
     */
    private char separador;
}
