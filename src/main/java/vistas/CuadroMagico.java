package vistas;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class CuadroMagico extends Stage {
    //variables
    private Scene escena;
    //constructor
    public CuadroMagico(){
        this.setTitle("Cuadro Mágico");
        this.setScene(new Scene(new Button("Da Click")));
        this.show();
    }

    private void  CrearGUI(){
        escena= new Scene(new Button("Da Click"));
    }
}
