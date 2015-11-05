
package db2loader;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;


public class VentanaEsperaController implements Initializable {

 
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
        public void setProgramaPrincipal(Db2loader programa) {
        this.programaPrincipal = programa;
    }
     private Db2loader programaPrincipal;
    
}
