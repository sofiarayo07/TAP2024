package vistas;


import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Memorama extends Stage {

    private Scene escena;
    private HBox hbprincipal, hbsecundario, hbjugador1, hbjugador2;
    private VBox vbprincipal, vbjugadores;
    private GridPane gpCartas;
    private Label lbnpares, lbjugador1,lblpuntos1, lbjugador2,lblpuntos2, lbTimer;
    private TextField txtpares;
    private Button btnresolver;

    public Memorama(){
        crearGUI();
        this.setTitle("Memorama");
        this.setScene(escena);
        this.show();

    }

    private void crearGUI() {
        btnresolver=new Button("Resolver");
        lbnpares=new Label("No. de pares");
        txtpares=new TextField("0");
        lbTimer = new Label("00:00");
        hbprincipal=new HBox(lbnpares,txtpares,btnresolver, lbTimer);


        lbjugador1 = new Label("Jugador 1: ");
        lblpuntos1=new Label(" 0 ");
        lbjugador2 = new Label("Jugador 2 :");
        lblpuntos2=new Label(" 0 ");
        hbjugador1 = new HBox(lbjugador1, lblpuntos1);
        hbjugador2 = new HBox(lbjugador2,lblpuntos2);
        vbjugadores= new VBox(hbjugador1,hbjugador2);
        gpCartas=new GridPane();
        hbsecundario= new HBox(gpCartas,vbjugadores);
        vbprincipal= new VBox(hbprincipal,hbsecundario);

        escena=new Scene(vbprincipal, 200,200);
        escena.getStylesheets()
                .add(getClass().getResource("/estilos/memorama.css").toString());


    }

}
