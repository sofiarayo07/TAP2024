package com.example.tap2024;

import com.example.tap2024.modelos.Conexion;
import componentes.Hilo;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import vistas.*;

import java.io.IOException;

public class HelloApplication extends Application {

    private MenuBar mnbPrincipal;
    private Menu menParcial1, menParcial2, menSalir;
    private MenuItem mitCalculadora,mitCuadroMagico, mitMemorama, mitSalir, mitEmpleado, mitPista, mitImpresora;
    private BorderPane bdpPanel;
    @Override
    public void start(Stage stage) throws IOException {
        CrearMenu();
       // FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        //Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        bdpPanel=new BorderPane();
        bdpPanel.setTop(mnbPrincipal);
        Scene scene=new Scene(bdpPanel);
        scene.getStylesheets()
                .add(getClass().getResource("/estilos/main.css").toString());
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
        stage.setMaximized(true);

        Conexion.crearConexion();


        //new Calculadora();
    }

    private void CrearMenu() {
        /* MENU PRIMER COMPETENCIA */
        mitCalculadora= new MenuItem("Calculadora");
        mitCalculadora.setOnAction(event->new Calculadora());

        mitMemorama= new MenuItem("Memorama");
        mitMemorama.setOnAction(event->new Memorama());

        mitEmpleado= new MenuItem("Empleado Taqueria");
        mitEmpleado.setOnAction(event->new EmpleadoTaqueria());

        mitCuadroMagico=new MenuItem("Cuadro Magico");
        mitCuadroMagico.setOnAction(event->new CuadroMagico());

        menParcial1=new Menu("Primera Competencia");
        menParcial1.getItems().addAll(mitCalculadora);
        menParcial1.getItems().addAll(mitCuadroMagico);
        menParcial1.getItems().addAll(mitMemorama);
        menParcial1.getItems().addAll(mitEmpleado);


        /* MENU SEGUNDO PARCIAL */
        menParcial2=new Menu("Segunda Competencia");

        mitPista=new MenuItem("Manejo de hilos");
        mitPista.setOnAction(event -> new Pista());

        mitImpresora=new MenuItem("Simulador de Impresora");
        mitImpresora.setOnAction(event->new SimuladorImpresion());

        menParcial2=new Menu("Segunda Competencia");
        menParcial2.getItems().addAll(mitImpresora);

        /* MENU SALIR */
        mitSalir=new MenuItem("Salir");
        menSalir= new Menu("Salir");
        menSalir.getItems().add(mitSalir);
        mitSalir.setOnAction(actionEvent -> System.exit(0));

        mnbPrincipal=new MenuBar();
        mnbPrincipal.getMenus().addAll(menParcial1, menParcial2,menSalir);
    }

    public static void main(String[] args) {
        launch();
    }
}