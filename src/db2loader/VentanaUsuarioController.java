package db2loader;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

public class VentanaUsuarioController implements Initializable {

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void salirVentana(ActionEvent event) {
        System.out.println("cerrar ventana");
        System.exit(0);
    }

    @FXML
    public void ventanaInsertar(ActionEvent event) {
        System.out.println("Crea la  ventana insertar");
        programaPrincipal.crearVentanaInsertar();
    }

    public void setProgramaPrincipal(Db2loader programa) {
        this.programaPrincipal = programa;
    }

    private Db2loader programaPrincipal;

    @FXML
    private TextField txtlocalHost;
    private TextField txtPuerto;
}
