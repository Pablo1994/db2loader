package db2loader;

import DB.Model.Modelo;
import java.io.File;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

/**
 *
 * @author Josue
 */
public class VentanaInsertarController implements Initializable {

    private final Modelo modelo;
    private static File _file;

    @FXML
    private Label label;
    @FXML
    private Button button;
    @FXML
    private ComboBox _comboTablas;
    @FXML
    TextField _textRuta;
 
    @FXML private TableView tableView;
    
    public VentanaInsertarController() throws Exception {
        modelo = Modelo.getInstancia();
    }

    /*  Acciones */
    @FXML
    private void handleButtonAction(ActionEvent event) {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        File file = fileChooser.showOpenDialog(programaPrincipal.getStagePrincipal());
    }

    @FXML
    private void clickTextAction(ActionEvent event) {

        _file = programaPrincipal.buscarArchivo();
        if (_file != null) {
            _textRuta.setText(_file.getAbsolutePath());
        } else {
            _textRuta.setText("Ruta sin Especificar");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            cargarTablas();

        } catch (Exception ex) {
            Logger.getLogger(VentanaInsertarController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void cargarTablas() throws Exception {
        List<String> tablas = modelo.listaTablasActuales();
        _comboTablas.getItems().clear();
        tablas.stream().forEach((tabla) -> {
            _comboTablas.getItems().add(tabla);
        });
        _comboTablas.getSelectionModel().select(tablas.get(0));
        cargarAtributos(tablas.get(0));
    }

    private void cargarAtributos(String nombre) throws SQLException {

    }
    
    

    public void setProgramaPrincipal(Db2loader programa) {
        this.programaPrincipal = programa;
    }

    @FXML
    public void cargarArchivo() {
        System.out.println("Cargando Datos de Archivo");
        /*
         //carga el archivo
         if (_file!=null) {
         modelo.cargarArchivo(_file);
         }
         */
    }

    private Db2loader programaPrincipal;
}
