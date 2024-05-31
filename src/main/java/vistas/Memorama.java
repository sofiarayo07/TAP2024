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

    public Memorama() {
        crearUI();
        this.setTitle("Memorama");
        this.setScene(escena);
        this.show();
        escena.getStylesheets().add(getClass().getResource("/estilos/memorama.css").toString());
    }

    private void crearUI() {
        // Creación de elementos de la interfaz gráfica
        // (código omitido para brevedad)
    }

    private void iniciarJuego() {
        // Iniciar el juego: inicialización de variables, configuración del temporizador, etc.
        // (código omitido para brevedad)
    }

    private void inicializarCartas() {
        // Inicialización de las cartas del juego
        // (código omitido para brevedad)
    }

    private void revolverCartas() {
        // Revolver las cartas en el tablero
        // (código omitido para brevedad)
    }

    private void compararCartas() {
        // Comparar las cartas volteadas para ver si coinciden o no
        Button carta1 = cartasVolteadas.get(0);
        Button carta2 = cartasVolteadas.get(1);

        int indice1 = GridPane.getColumnIndex(carta1) + GridPane.getRowIndex(carta1) * numeroPares;
        int indice2 = GridPane.getColumnIndex(carta2) + GridPane.getRowIndex(carta2) * numeroPares;

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
                // Permitir al jugador continuar
                timer.play();
            }
        } else {
            // Voltear las cartas después de un breve retraso y permitir al jugador continuar
            Timeline voltearCartas = new Timeline(
                    new KeyFrame(Duration.seconds(1), e -> {
                        carta1.setGraphic(null);
                        carta2.setGraphic(null);
                        enJuego = true;
                        timer.play();
                    })
            );
            voltearCartas.setCycleCount(1);
            voltearCartas.play();
        }

        cartasVolteadas.clear();
    }

    private void voltearCarta(Button carta) {
        // Voltear una carta cuando se hace clic en ella
        // (código omitido para brevedad)
    }

    private void actualizarTimer() {
        // Actualizar el temporizador cada segundo
        // (código omitido para brevedad)
    }

    private void cambiarTurno() {
        // Cambiar el turno del jugador
        // (código omitido para brevedad)
    }

    private void actualizarEstilos(Label lblPuntosActual, Label lbJugadorActual, Label lblPuntosSiguiente, Label lbJugadorSiguiente) {
        // Actualizar los estilos de los jugadores para indicar quién está en turno
        // (código omitido para brevedad)
    }

    private void mostrarMensajeFinJuego(String ganador) {
        // Mostrar un mensaje indicando el ganador y la cantidad de pares encontrados por cada jugador
        // (código omitido para brevedad)
    }

    private void mostrarMensajeError(String mensaje) {
        // Mostrar un mensaje de error en caso de que haya un problema durante el juego
        // (código omitido para brevedad)
    }
}
