package db2loader;

import java.io.File;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * @author Josue
 */
public class Db2loader extends Application {

    @Override
    public void start(Stage principal) throws Exception {
        this.stagePrincipal = principal;
        crearVentanaUsuario();
    }

    public void crearVentanaUsuario() {
        try {
            FXMLLoader loader = new FXMLLoader(Db2loader.class.getResource("VentanaUsuario.fxml"));
            rootPane = (AnchorPane) loader.load();
            Scene scene = new Scene(rootPane);
            stagePrincipal.setTitle("Datos Conexion");
            stagePrincipal.setScene(scene);
            controlUsuario = loader.getController();
            controlUsuario.setProgramaPrincipal(this);
            stagePrincipal.show();
        } catch (Exception e) {
        }
    }

    public void crearVentanaInsertar() {
        try {
            FXMLLoader loader = new FXMLLoader(Db2loader.class.getResource("VentanaInsertar.fxml"));
            rootPane = (AnchorPane) loader.load();
            Scene scene = new Scene(rootPane);
            stagePrincipal.setTitle("Insertar Datos");
            stagePrincipal.setScene(scene);
            controlInsertar = loader.getController();
            controlInsertar.setProgramaPrincipal(this);
            stagePrincipal.show();
        } catch (Exception e) {
        }
    }

    public File buscarArchivo() {

        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter filtro
                = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(filtro);
        fileChooser.setTitle("Archivo a Cargar");
        File nuevo = fileChooser.showOpenDialog(stagePrincipal);
        if (nuevo != null) {
            return nuevo;
        }
        return nuevo;
    }

    public Stage getStagePrincipal() {
        return stagePrincipal;
    }

    public static void main(String[] args) {
        launch(args);
    }

    private Stage stagePrincipal;
//    private Stage usuario;
    private VentanaUsuarioController controlUsuario;
    private VentanaInsertarController controlInsertar;
    private AnchorPane rootPane;
}
