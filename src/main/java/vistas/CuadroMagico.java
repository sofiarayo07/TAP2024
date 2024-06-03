package vistas;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.RandomAccessFile;

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
        CrearGUI();
        this.setTitle("Cuadro Mágico");
        this.setScene(escena);
        this.show();
        escena.getStylesheets()
                .add(getClass().getResource("/estilos/cuadroMagico.css").toString());

        btnhacer.setOnAction(e -> generarCuadroMagico());
    }

    private void  CrearGUI(){
        txttamaño= new TextField("3");
        btnhacer= new Button("Hacer");
        gpcuadro= new GridPane();
        hbpeincipal= new HBox(txttamaño, btnhacer);
        vbprincipal= new VBox(hbpeincipal, gpcuadro);
        vbprincipal.setSpacing(5);

        escena= new Scene(vbprincipal,200,200);

    }

    private void generarCuadroMagico() {
        int n = Integer.parseInt(txttamaño.getText());
        if (n % 2 == 0 || n < 3) {
            // Mostrar mensaje de error si el tamaño es par o menor que 3
            System.out.println("ERROR, ingresa valor impar.");
            return;
        }

        gpcuadro.getChildren().clear(); // Limpiar cuadro anterior si existe

        try (RandomAccessFile file = new RandomAccessFile("cuadro_magico.txt", "rw")) {
            // Limpiar archivo si existe
            file.setLength(0);

            file.writeInt(n);

           // int magicConstant = n * ((n * n) + 1) / 2;

            //int num = 1;
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    int row = i + 1;
                    int col = j + 1;

                    int val = n * ((row + col - 1 + (n / 2)) % n) + ((row + 2 * col - 2) % n) + 1;

                    file.writeInt(val);
                    mostrarMensaje(val + " ");

                    // Agregar el número al GridPane
                    TextField textField = new TextField(String.valueOf(val));
                    textField.setEditable(false);
                    gpcuadro.add(textField, j, i); // Agregar en la columna j y fila i
                }
                mostrarMensaje("\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void mostrarMensaje(String mensaje) {
        System.out.print(mensaje);
    }
}