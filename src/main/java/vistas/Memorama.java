package vistas;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.kordamp.bootstrapfx.BootstrapFX;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Memorama extends Stage {

    private Scene escena;
    private HBox hbprincipal, hbsecundario, hbjugador1, hbjugador2;
    private VBox vbprincipal, vbjugadores;
    private GridPane gpCartas;
    private Label lbnpares, lbjugador1, lblpuntos1, lbjugador2, lblpuntos2, lbTimer;
    private TextField txtpares;
    private Button btnresolver;

    private int numeroPares;
    private int paresEncontradosJugador1;
    private int paresEncontradosJugador2;
    private int turnoActual;
    private int tiempoTurno;
    private Timeline timer;
    private boolean enJuego;
    private List<String> cartas;
    private List<Button> cartasVolteadas;
    private List<Button> botonesCartas;

    public Memorama() {
        crearUI();
        this.setTitle("Memorama");
        this.setScene(escena);
        this.show();
        escena.getStylesheets().add(getClass().getResource("/estilos/memorama.css").toString());
    }

    private void crearUI() {
        btnresolver = new Button("Iniciar juego");
        btnresolver.setOnAction(e -> iniciarJuego());

        lbnpares = new Label("No. de pares");
        lbnpares.getStyleClass().add("lbl-info");

        txtpares = new TextField();
        lbTimer = new Label("00:00");
        hbprincipal = new HBox(lbnpares, txtpares, btnresolver, lbTimer);

        lbjugador1 = new Label("Jugador 1: ");
        lblpuntos1 = new Label(" 0 ");
        lbjugador2 = new Label("Jugador 2 :");
        lblpuntos2 = new Label(" 0 ");
        gpCartas = new GridPane();
        hbjugador1 = new HBox(lbjugador1, lblpuntos1);
        hbjugador2 = new HBox(lbjugador2, lblpuntos2);
        vbjugadores = new VBox(hbjugador1, hbjugador2);

        hbsecundario = new HBox(gpCartas, vbjugadores);
        vbprincipal = new VBox(hbprincipal, hbsecundario);

        escena = new Scene(vbprincipal, 800, 600);
        escena.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
    }

    private void iniciarJuego() {
        numeroPares = Integer.parseInt(txtpares.getText());
        if (numeroPares < 3) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("El número de pares debe ser mayor o igual a 3.");
            alert.showAndWait();
            return;
        }
        paresEncontradosJugador1 = 0;
        paresEncontradosJugador2 = 0;
        lblpuntos1.setText(" 0 ");
        lblpuntos2.setText(" 0 ");
        turnoActual = 1;
        tiempoTurno = 10; // 10 segundos por turno
        enJuego = true;
        if (timer != null) {
            timer.stop();
        }
        timer = new Timeline(new KeyFrame(Duration.seconds(1), e -> actualizarTimer()));
        timer.setCycleCount(Animation.INDEFINITE);
        timer.play();

        inicializarCartas();
        revolverCartas();

        cambiarTurno();
    }

    private void inicializarCartas() {
        cartas = new ArrayList<>();
        cartasVolteadas = new ArrayList<>();
        botonesCartas = new ArrayList<>();

        for (int i = 1; i <= numeroPares; i++) {
            cartas.add("gato" + i + ".jpg");
            cartas.add("gato" + i + ".jpg");
        }
        Collections.shuffle(cartas);

        // Agregar imagen de reverso antes de agregar imágenes específicas
        for (int i = 0; i < numeroPares * 2; i++) {
            Button carta = new Button();
            carta.setPrefSize(150, 200); // Establecer tamaño antes de agregar imágenes
            carta.setStyle("-fx-background-image: url('https://i.pinimg.com/474x/ab/b2/f2/abb2f2c814090bda0b41bcd2d632c7be.jpg');" +
                    "-fx-background-size: cover;");
            carta.setOnAction(e -> voltearCarta(carta));
            botonesCartas.add(carta);
        }
    }


    private void revolverCartas() {
        gpCartas.getChildren().clear();
        int totalCartas = numeroPares * 2;
        List<Integer> posiciones = new ArrayList<>();
        for (int i = 0; i < totalCartas; i++) {
            posiciones.add(i);
        }
        Collections.shuffle(posiciones);

        for (int i = 0; i < totalCartas; i++) {
            Button carta = new Button();
            carta.setPrefSize(100,150);
            carta.setOnAction(e -> voltearCarta(carta));
            botonesCartas.add(carta);
        }

        for (int i = 0; i < totalCartas; i++) {
            int pos = posiciones.get(i);
            int row = pos / (totalCartas / 2);
            int col = pos % (totalCartas / 2);
            gpCartas.add(botonesCartas.get(i), col, row);
        }
    }

    private void compararCartas() {
        Button carta1 = cartasVolteadas.get(0);
        Button carta2 = cartasVolteadas.get(1);

        int indice1 = botonesCartas.indexOf(carta1);
        int indice2 = botonesCartas.indexOf(carta2);

        if (cartas.get(indice1).equals(cartas.get(indice2))) {
            carta1.setDisable(true);
            carta2.setDisable(true);
            if (turnoActual == 1) {
                paresEncontradosJugador1++;
                lblpuntos1.setText(" " + paresEncontradosJugador1 + " ");
            } else {
                paresEncontradosJugador2++;
                lblpuntos2.setText(" " + paresEncontradosJugador2 + " ");
            }
            if (paresEncontradosJugador1 + paresEncontradosJugador2 == numeroPares) {
                String ganador = paresEncontradosJugador1 > paresEncontradosJugador2 ? "1" : "2";
                mostrarMensajeFinJuego(ganador);
            } else {
                tiempoTurno = 30; // Reset timer for the player to continue
                timer.play();
                enJuego = true;
            }
        } else {
            Timeline voltearCartas = new Timeline(
                    new KeyFrame(Duration.seconds(1), e -> {
                        carta1.setGraphic(null);
                        carta2.setGraphic(null);
                        carta1.setDisable(false);
                        carta2.setDisable(false);
                        enJuego = true;
                        cambiarTurno(); // Cambio de turno cuando el jugador falle
                    })
            );
            voltearCartas.setCycleCount(1);
            voltearCartas.play();
        }

        cartasVolteadas.clear();
    }

    private void voltearCarta(Button carta) {
        if (!enJuego || carta.getGraphic() != null) {
            return;
        }

        int indice = botonesCartas.indexOf(carta);
        ImageView imvCarta = new ImageView(new Image(getClass().getResourceAsStream("/imagenes/" + cartas.get(indice))));
        imvCarta.setFitHeight(160);
        imvCarta.setFitWidth(110);
        carta.setGraphic(imvCarta);
        carta.setOpacity(1.0);
        carta.setDisable(true);

        cartasVolteadas.add(carta);

        if (cartasVolteadas.size() == 2) {
            timer.stop();
            enJuego = false;
            compararCartas();
        }
    }

    private void actualizarTimer() {
        lbTimer.setText(String.format("%02d:%02d", tiempoTurno / 60, tiempoTurno % 60));
        if (tiempoTurno == 0) {
            cambiarTurno();
        } else {
            tiempoTurno--;
        }
    }

    private void cambiarTurno() {
        if (turnoActual == 1) {
            lblpuntos1.getStyleClass().remove("lbl-success");
            lbjugador1.getStyleClass().remove("lbl-success");
            lblpuntos2.getStyleClass().remove("lbl-danger");
            lbjugador2.getStyleClass().remove("lbl-danger");

            lblpuntos2.getStyleClass().add("lbl-success");
            lbjugador2.getStyleClass().add("lbl-success");
            lblpuntos1.getStyleClass().add("lbl-danger");
            lbjugador1.getStyleClass().add("lbl-danger");

        } else {

            lblpuntos2.getStyleClass().remove("lbl-success");
            lbjugador2.getStyleClass().remove("lbl-success");
            lblpuntos1.getStyleClass().remove("lbl-danger");
            lbjugador1.getStyleClass().remove("lbl-danger");

            lblpuntos1.getStyleClass().add("lbl-success");
            lbjugador1.getStyleClass().add("lbl-success");
            lblpuntos2.getStyleClass().add("lbl-danger");
            lbjugador2.getStyleClass().add("lbl-danger");
        }
        tiempoTurno = 10;
        enJuego = true;
        timer.play();
        for (Button carta : cartasVolteadas) {
            carta.setGraphic(null);
        }
        cartasVolteadas.clear();
        turnoActual = turnoActual == 1 ? 2 : 1;
    }

    private void mostrarMensajeFinJuego(String ganador) {
        timer.stop();
        enJuego = false;
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Fin del juego");
        alert.setHeaderText(null);
        alert.setContentText("El jugador " + ganador + " ha ganado.\n" +
                "Pares encontrados por Jugador 1: " + paresEncontradosJugador1 + "\n" +
                "Pares encontrados por Jugador 2: " + paresEncontradosJugador2);
        alert.showAndWait();
    }
}
