/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package db2loader;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

/**
 * FXML Controller class
 *
 * @author Josue
 */
public class VentanaBienvenidaController implements Initializable {

    @FXML
    private Button _inicia;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void setProgramaPrincipal(Db2loader programa) {
        this.programaPrincipal = programa;
    }

    @FXML
    public void iniciar() {
        programaPrincipal.crearVentanaUsuario();
    }

    @FXML
    public void salir() {
        programaPrincipal.salir();
    }
    private Db2loader programaPrincipal;
}
