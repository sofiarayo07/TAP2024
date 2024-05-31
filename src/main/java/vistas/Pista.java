package vistas;

import componentes.Hilo;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Pista extends Stage {
    ProgressBar[] pgbCorredores = new ProgressBar[5];
    private Label[] lblCorredores = new Label[5];
    private GridPane gdpPista;
    private Scene escena;
    private  String []strCorredores =  {"Juno","Sergio","Rafa","Alma","Jushua"};
    private Hilo[] theCorredores= new Hilo[5];

    public Pista(){
        crearUI();
        this.setTitle("Pista de Atletismo");
        this.setScene(escena);
        this.show();

    }

    private void crearUI() {
        gdpPista= new GridPane();
        for (int i=0; i< strCorredores.length ;i++){
            lblCorredores[i]=new Label(strCorredores[i]);
            pgbCorredores[i]= new ProgressBar(0);
            gdpPista.add(lblCorredores[i],0,i);
            gdpPista.add(lblCorredores[i],1,i);
            theCorredores[i]= new Hilo(strCorredores[i]);
            theCorredores[i].setPgbCarril(pgbCorredores[i]);
            theCorredores[i].start();
        }
        escena= new Scene(gdpPista,200,200);
    }

}
