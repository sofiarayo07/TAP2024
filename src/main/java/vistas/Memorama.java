package vistas;


import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Timer;


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
        hbprincipal=new HBox(lbnpares,txtpares,btnresolver, lbTimer); //


        lbjugador1 = new Label("Jugador 1: ");
        lblpuntos1=new Label(" 0 ");
        lbjugador2 = new Label("Jugador 2 :");
        lblpuntos2=new Label(" 0 ");
        gpCartas=new GridPane();
        hbjugador1 = new HBox(lbjugador1, lblpuntos1);
        hbjugador2 = new HBox(lbjugador2,lblpuntos2);
        vbjugadores= new VBox(hbjugador1,hbjugador2);
        //HBox.setHgrow(gpCartas, Priority.ALWAYS);

        hbsecundario= new HBox(gpCartas, vbjugadores);
        vbprincipal= new VBox(hbprincipal,hbsecundario);

        escena=new Scene(vbprincipal, 200,200);
        escena.getStylesheets()
                .add(getClass().getResource("/estilos/memorama.css").toString());


    }


    private void RevolcerCartas(){
        String[] arImagenes={"gato1.jpg","gato2.jpg","gato3.jpg","gato4.jpg"};
        ImageView imvCarta;
        int posx=0;
        int posy=0;
        int cont=0;
        Button [][] arBtnCartas= new Button[2][4];
        for (int i=0; i<arImagenes.length; ){
            posx=(int)(Math.random()*2);
            posy=(int)(Math.random()*4);
            if (arBtnCartas[posx][posy]==null){
                arBtnCartas[posx][posy]=new Button();
                imvCarta=new ImageView(getClass().getResource("/imagenes/"+arImagenes[i]).toString());
                arBtnCartas[posx][posy].setGraphic(imvCarta);
                arBtnCartas[posx][posy].setPrefSize(100,150);
                gpCartas.add(arBtnCartas[posx][posy],posy,posx);
                cont++;
                if (cont==2) {
                    i++ ;//cuando la imagen sean dos, se incrementa i para posicionarse a la siguiente imagen
                    cont=0;
                }
            }

        }
    }

}
