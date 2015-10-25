package db2loader;

import DB.Model.Modelo;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class VentanaUsuarioController implements Initializable {

    public VentanaUsuarioController() throws Exception {
        modelo = Modelo.getInstancia();
    }

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

    @FXML
    public void aceptar(ActionEvent event) {
        System.out.println("Aceptar");

    }

    public void setProgramaPrincipal(Db2loader programa) {
        this.programaPrincipal = programa;
    }

    @FXML
    public void obtenerParametros() {
        String base, usuario, host, puerto, clave;
        base = txtDataB.getText();
        usuario = txtUser.getText();
        host = txtHost.getText();
        puerto = txtPuerto.getText();
        clave = txtPassword.getText();
        modelo.actualizar(base, puerto, usuario, clave, host);
        System.out.println("Datos:\n" + base + "  " + usuario + "  " + host + "  " + puerto + "  " + clave);
        labelResult.setText(modelo.resultado());
        labelResult.setVisible(true);

    }

    private Db2loader programaPrincipal;
    private Modelo modelo;

    @FXML
    private TextField txtDataB;
    @FXML
    private TextField txtUser;
    @FXML
    private TextField txtHost;

    @FXML
    private TextField txtPuerto;

    @FXML
    private TextField txtPassword;

    @FXML
    private Label labelResult;

}
