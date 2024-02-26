package vistas;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Calculadora extends Stage {
    private Scene escena;
    private VBox vContenedor;
    private GridPane gdpTeclado;
    private TextField txtPantalla;
    private Button[][] arBotones = new Button[4][4];
    private char[] arEtiquetas = {'7', '8', '9', '/', '4', '5', '6', '*', '1', '2', '3', '-', '0', '.', '=', '+'};
    private StringBuilder primernum = new StringBuilder();
    private StringBuilder segundonum = new StringBuilder();
    private char operador = ' ';
    private boolean operadorPresionado = false;


    public Calculadora() {
        CrearUI();
        this.setTitle("Mi primer Calculadora :)");
        this.setScene(escena);
        this.show();
    }


    private void CrearUI() {
        txtPantalla = new TextField("0");
        txtPantalla.setEditable(false);
        gdpTeclado = new GridPane();
        Button btnClear = new Button("Clear");
        btnClear.setOnAction(event -> {
            txtPantalla.clear();
            primernum.setLength(0);
            segundonum.setLength(0);
            operador = ' ';
            operadorPresionado = false;
        });
        CrearTeclado();
        vContenedor = new VBox(txtPantalla, gdpTeclado, btnClear);
        vContenedor.setSpacing(5);
        escena = new Scene(vContenedor, 200, 200);
        escena.getStylesheets()
                .add(getClass().getResource("/estilos/calculadora.css").toString());
    }


    private void CrearTeclado() {
        int pos = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                arBotones[i][j] = new Button(arEtiquetas[pos] + "");
                arBotones[i][j].setPrefSize(50, 50);
                int finalPos = pos;
                arBotones[i][j].setOnAction(actionEvent -> {
                    char simbolo = arEtiquetas[finalPos];
                    if (Character.isDigit(simbolo) || simbolo == '.') {
                        if (!operadorPresionado) {
                            primernum.append(simbolo);
                            txtPantalla.setText(primernum.toString());
                        } else {
                            segundonum.append(simbolo);
                            txtPantalla.setText(primernum + " " + operador + " " + segundonum);
                        }
                    } else if (simbolo == '=') {
                        Operaciones();

                    } else {
                        if (operador != ' ') {
                            Operaciones();
                        }
                        operador = simbolo;
                        operadorPresionado = true;
                        txtPantalla.setText(primernum + " " + operador);
                    }
                });

                gdpTeclado.add(arBotones[i][j], j, i);
                if (arEtiquetas[pos] == '+' || arEtiquetas[pos] == '*' || arEtiquetas[pos] == '/') {
                    arBotones[i][j].setId("color-operador");
                }
                pos++;
            }
        }

    }

    private void Operaciones() {
        if (operador == ' ' || segundonum.length() == 0 || primernum.length() == 0) {
            txtPantalla.setText("Syntax Error");
            return;
        }
        double num1 = Double.parseDouble(primernum.toString());
        double num2 = Double.parseDouble(segundonum.toString());
        double resultado = 0;
        switch (operador) {
            case '+':
                resultado = num1 + num2;
                break;
            case '-':
                resultado = num1 - num2;
                break;
            case '*':
                resultado = num1 * num2;
                break;
            case '/':
                if (num1 != 0 && num2 != 0) {
                    resultado = num1 / num2;
                } else {
                    txtPantalla.setText("Syntax Error");
                    return;
                }
                break;
        }
        txtPantalla.setText(String.valueOf(resultado));
        primernum.setLength(0);
        segundonum.setLength(0);
        operador = ' ';
        operadorPresionado = false;
    }
}