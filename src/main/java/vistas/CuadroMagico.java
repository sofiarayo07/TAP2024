package vistas;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CuadroMagico extends Stage {
    //variables
    private Scene escena;
    private TextField txttamaño;
    private Button btnhacer;
    private GridPane gpcuadro;
    private HBox hbpeincipal;
    private VBox vbprincipal;

    //constructor
    public CuadroMagico(){
        this.setTitle("Cuadro Mágico");
        this.setScene(new Scene(new Button("Da Click")));
        this.show();

    }

    private void  CrearGUI(){
        txttamaño= new TextField("0");
        btnhacer= new Button("Hacer");
        gpcuadro= new GridPane();
        hbpeincipal= new HBox(txttamaño, btnhacer);
        vbprincipal= new VBox(hbpeincipal, gpcuadro);
        vbprincipal.setSpacing(5);

        escena= new Scene(new Button("Da Click"));


    }
}
