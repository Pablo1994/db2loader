package db2loader;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;

public class VentanaEsperaController implements Initializable {

    @FXML
    private  TextField _txtLinea;
    @FXML
    private  TextField _txtErrores;
    @FXML
    private  TextField _txtTiempo;
     @FXML
    private Button _aceptar;
       @FXML
    private ProgressBar _progreso;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
       _txtLinea.setText("0");
       _txtErrores.setText("0");
       _txtTiempo.setText("0");
     _aceptar.setDisable(true);
    }

    public VentanaEsperaController() {
        _espera_insta=this;
    }


    public void setProgramaPrincipal(Db2loader programa) {
        this.programaPrincipal = programa;
    }

    public TextField getTxtLinea() {
        return _txtLinea;
    }

    public void setTxtLinea(TextField _txtLinea) {
        this._txtLinea = _txtLinea;
    }

    public TextField getTxtErrores() {
        return _txtErrores;
    }

    public void setTxtErrores(TextField _txtErrores) {
        this._txtErrores = _txtErrores;
    }

    public TextField getTxtTiempo() {
        return _txtTiempo;
    }

    public void setTxtTiempo(TextField _txtTiempo) {
        this._txtTiempo = _txtTiempo;
    }

    public static VentanaEsperaController getEspera_insta() {
        return _espera_insta;
    }
      @FXML
    public void salir() {
        programaPrincipal.salir();
    }
    
       @FXML
    public void fin() throws IOException {
        programaPrincipal.crearBienvenida();
    }

    public Button getAceptar() {
        return _aceptar;
    }

    public void setAceptar(Button _aceptar) {
        this._aceptar = _aceptar;
    }
    
    public void finalizado(){
        _progreso.setProgress(100);
        _aceptar.setDisable(false);
    }
    
    
    private Db2loader programaPrincipal;
    private static VentanaEsperaController _espera_insta;

}
