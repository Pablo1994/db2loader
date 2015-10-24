/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

/**
 *
 * @author Josue
 */
public class FXMLDocumentController implements Initializable {

    private Modelo modelo;
    private static File _file;

    @FXML
    private Label label;
    @FXML
    private Button button;
    @FXML
    private ComboBox _comboTablas;
    @FXML TextField _textRuta;

    public FXMLDocumentController() throws Exception {
        modelo = Modelo.getInstancia();
    }

    /*  Acciones */
    @FXML
    private void handleButtonAction(ActionEvent event) {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        File file = fileChooser.showOpenDialog(Db2loader.stage);
    }

    @FXML
    private void clickTextAction(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Archivo a Cargar");
        File nuevo= fileChooser.showOpenDialog(Db2loader.stage);
        if(nuevo.isFile()){
        _file=nuevo;
        _textRuta.setText(_file.getAbsolutePath());
        }
        
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            cargarTablas();
        } catch (Exception ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void cargarTablas() throws Exception {
        List<String> tablas = modelo.listaTablasActuales();
        _comboTablas.getItems().clear();
        tablas.stream().forEach((tabla) -> {
            _comboTablas.getItems().add(tabla);
        });
        _comboTablas.getSelectionModel().select(tablas.get(0));
    }

}
