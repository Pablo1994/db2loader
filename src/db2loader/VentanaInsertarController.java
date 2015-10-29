package db2loader;

import DB.Model.Modelo;
import Logica.Atributo;
import Logica.Tabla;
import java.io.File;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

/**
 *
 * @author Josue
 */
public class VentanaInsertarController implements Initializable {

    private final Modelo modelo;
    private static Tabla _tabla;
    private static File _file;

    @FXML
    private Label label;
    @FXML
    private Button button;
    @FXML
    private ComboBox _comboTablas;
    @FXML
    TextField _textRuta;
    @FXML
    TextField txtSeparador;
    @FXML
    private ListView _listaArtributos;
    @FXML
    private ListView _listaArtributosSeleccionados;

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
    private void buscarArchivo(ActionEvent event) {

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

    @FXML
    private void cambiarAtributos() throws SQLException {
        _listaArtributos.getItems().clear();
        _listaArtributosSeleccionados.getItems().clear();
        String actual = _comboTablas.getSelectionModel().getSelectedItem().toString();
        _tabla = modelo.builtTabla(actual);
        _tabla.getAtributos().stream().forEach((a) -> {
            _listaArtributos.getItems().add(a.getNombre() + " " + a.getTipo().toString());
        });
    }

    @FXML
    private void agregarAtributo() {
        for (Object l : _listaArtributosSeleccionados.getItems()) {
            if (l.equals(_listaArtributos.getSelectionModel().getSelectedItem().toString())) {
                return;
            }
        }
        _listaArtributosSeleccionados.getItems().add(_listaArtributos.getSelectionModel().getSelectedItem().toString());

    }

    private void cargarTablas() throws Exception {
        List<String> tablas = modelo.listaTablasActuales();
        _comboTablas.getItems().clear();
        tablas.stream().forEach((tabla) -> {
            _comboTablas.getItems().add(tabla);
        });

        _comboTablas.getSelectionModel().select(tablas.get(0));

        _tabla = modelo.builtTabla(tablas.get(0));

        _tabla.getAtributos().stream().forEach((a) -> {
            _listaArtributos.getItems().add(a.getNombre());
        });

//        ObservableList<String> items =FXCollections.observableArrayList (
//    "Single", "Double", "Suite", "Family App");
//     _listaArtributos.setItems(items);
    }

    public void setProgramaPrincipal(Db2loader programa) {
        this.programaPrincipal = programa;
    }

    @FXML
    public void cargarDatos() {
        if (_file != null) {
            System.out.println("Cargando Datos de Archivo");
            System.out.println("Separador: " + obtenerSeparador());
            System.out.println("Tabla Seleccionada: " + obtenerTablaSeleccionada());
        }
    }

    public char obtenerSeparador() {
        String separador = txtSeparador.getText();
        System.out.println("Separador: " + separador.charAt(0));
        return separador.charAt(0);

    }

    public String obtenerTablaSeleccionada() {
        String tabla = _comboTablas.getSelectionModel().getSelectedItem().toString();
        System.out.println("Tabla Seleccionada:  " + tabla);
        return tabla;
    }

    private Db2loader programaPrincipal;
}
