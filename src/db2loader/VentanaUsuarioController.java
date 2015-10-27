package db2loader;

import DB.Model.Modelo;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class VentanaUsuarioController implements Initializable {

    public VentanaUsuarioController() throws Exception {
        modelo = Modelo.getInstancia();
        estado = false;/*********Aqui es FALSE*/
    }

    /*
     BooleanBinding booleanBind = Bindings.and(text1.textProperty().isEmpty(),
     text2.textProperty().isEmpty());
     button.disableProperty().bind(booleanBind);
     */
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
        if (estado == true) {
            System.out.println("Crea la  ventana insertar");
            programaPrincipal.crearVentanaInsertar();
        }

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
        if (modelo.conexion()) {
            labelResult.setText(" Estado: Conectado");
            estado = true;
        } else {
            labelResult.setText(" Estado: Error Conexion");
            estado = false;
        }

    }

    private Db2loader programaPrincipal;
    private final Modelo modelo;
    private boolean estado;

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
    @FXML
    private Button btnProbar;
    @FXML
    private Button btnAceptar;

}
