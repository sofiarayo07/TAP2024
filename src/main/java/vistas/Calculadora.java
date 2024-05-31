package vistas;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayDeque;
import java.util.Deque;

public class Calculadora extends Stage {
    private Scene escena;
    private VBox vContenedor;
    private GridPane gdpTeclado;
    private TextField txtPantalla;
    private Button[][] arBotones = new Button[4][4];
    private char[] arEtiquetas = {'7', '8', '9', '/', '4', '5', '6', '*', '1', '2', '3', '-', '0', '.', '=', '+'};
    private StringBuilder expresion = new StringBuilder();
    private Deque<Double> numeros = new ArrayDeque<>();
    private Deque<Character> operadores = new ArrayDeque<>();
    private boolean resultadoMostrado = false;

    public Calculadora() {
        CrearUI();
        this.setTitle("Mi Calculadora");
        this.setScene(escena);
        this.show();
        escena.getStylesheets()
                .add(getClass().getResource("/estilos/calculadora.css").toString());
    }

    private void CrearUI() {
        txtPantalla = new TextField("0");
        txtPantalla.setEditable(false);
        gdpTeclado = new GridPane();
        Button btnClear = new Button("Clear");
        btnClear.setOnAction(event -> {
            txtPantalla.clear();
            expresion.setLength(0);
            numeros.clear();
            operadores.clear();
            resultadoMostrado = false;
            escena.getStylesheets()
                    .add(getClass().getResource("/estilos/calculadora.css").toString());

        });
        CrearTeclado();
        vContenedor = new VBox(txtPantalla, gdpTeclado, btnClear);
        vContenedor.setSpacing(5);
        escena = new Scene(vContenedor, 200, 200);

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
                    if (resultadoMostrado && Character.isDigit(simbolo)) {
                        txtPantalla.clear();
                        expresion.setLength(0);
                        numeros.clear();
                        operadores.clear();
                        resultadoMostrado = false;
                    }
                    if (expresion.length() >= 15) {
                        txtPantalla.setText("Syntax Error");
                    } else if (simbolo == '=') {
                        EvaluarExpresion();
                    } else if (simbolo == '.' && expresion.length() > 0 && expresion.charAt(expresion.length() - 1) == '.') {
                        txtPantalla.setText("Syntax Error");
                    } else if (simbolo == '.' && expresion.length() == 0) {
                        txtPantalla.setText("Syntax Error");
                    } else if (simbolo == '.' && expresion.length() > 1 && !Character.isDigit(expresion.charAt(expresion.length() - 1))) {
                        txtPantalla.setText("Syntax Error");
                    } else if (!validarExpresion(simbolo)) { // Validar expresión
                        txtPantalla.setText("Syntax Error");
                    } else {
                        expresion.append(simbolo);
                        txtPantalla.setText(expresion.toString());
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

    private boolean validarExpresion(char simbolo) {
        if (expresion.length() == 0 && (simbolo == '+' || simbolo == '*' || simbolo == '/')) {
            return false;
        }
        if (simbolo == '*' && (expresion.length() == 0 || expresion.charAt(expresion.length() - 1) == '/' || expresion.charAt(expresion.length() - 1) == '.')) {
            return false;
        }
        if (simbolo == '-' && (expresion.length() == 0 || expresion.charAt(expresion.length() - 1) == '/' || expresion.charAt(expresion.length() - 1) == '.')) {
            return false;
        }
        if (simbolo == '+' && (expresion.length() == 0 || expresion.charAt(expresion.length() - 1) == '/' || expresion.charAt(expresion.length() - 1) == '.')) {
            return false;
        }
        if (simbolo == '/' && (expresion.length() == 0 || expresion.charAt(expresion.length() - 1) == '/' || expresion.charAt(expresion.length() - 1) == '.')) {
            return false;
        }
        if (simbolo == '.' && (expresion.length() == 0 || !Character.isDigit(expresion.charAt(expresion.length() - 1)))) {
            return false;
        }
        if (!Character.isDigit(simbolo) && !Character.isDigit(expresion.charAt(expresion.length() - 1)) && simbolo != '.') {
            return false;
        }
        return true;
    }

    private void EvaluarExpresion() {
        if (expresion.length() == 0 || !Character.isDigit(expresion.charAt(expresion.length() - 1))) {
            txtPantalla.setText("Syntax Error");
            return;
        }

        StringBuilder numBuilder = new StringBuilder();
        for (char c : expresion.toString().toCharArray()) {
            if (Character.isDigit(c) || c == '.') {
                numBuilder.append(c);
            } else {
                if (numBuilder.length() > 0) {
                    double num;
                    // Validar si la cadena es convertible a double antes de convertirla
                    if (!esConvertibleADouble(numBuilder.toString())) {
                        txtPantalla.setText("Syntax Error");
                        return;
                    }
                    num = Double.parseDouble(numBuilder.toString());
                    numeros.push(num);
                    numBuilder.setLength(0);
                }
                if (c != '=') {
                    operadores.push(c);
                }
            }
        }
        if (numBuilder.length() > 0) {
            double num;
            if (!esConvertibleADouble(numBuilder.toString())) {
                txtPantalla.setText("Syntax Error");
                return;
            }
            num = Double.parseDouble(numBuilder.toString());
            numeros.push(num);
        }

        while (!operadores.isEmpty()) {
            char operador = operadores.pop();
            double num2 = numeros.pop();
            double num1 = numeros.pop();
            switch (operador) {
                case '+':
                    numeros.push(num1 + num2);
                    break;
                case '-':
                    numeros.push(num1 - num2);
                    break;
                case '*':
                    numeros.push(num1 * num2);
                    break;
                case '/':
                    if (num2 != 0) {
                        numeros.push(num1 / num2);
                    } else {
                        txtPantalla.setText("Syntax Error: no se puede dividir por 0");
                        return;
                    }
                    break;
            }
        }

        txtPantalla.setText(numeros.pop().toString());
        expresion.setLength(0);
        resultadoMostrado = true;
    }

    private boolean esConvertibleADouble(String numero) {
        if (numero == null || numero.isEmpty()) {
            return false;
        }
        int puntos = 0;
        for (int i = 0; i < numero.length(); i++) {
            if (!Character.isDigit(numero.charAt(i))) {
                if (numero.charAt(i) == '.') {
                    puntos++;
                    if (puntos > 1) {
                        return false;
                    }
                } else {
                    return false;
                }
            }
        }
        return true;
    }
}


/*
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayDeque;
import java.util.Deque;

public class Calculadora extends Stage {
    private Scene escena;
    private VBox vContenedor;
    private GridPane gdpTeclado;
    private TextField txtPantalla;
    private Button[][] arBotones = new Button[4][4];
    private char[] arEtiquetas = {'7', '8', '9', '/', '4', '5', '6', '*', '1', '2', '3', '-', '0', '.', '=', '+'};
    private StringBuilder expresion = new StringBuilder();
    private Deque<Double> numeros = new ArrayDeque<>();
    private Deque<Character> operadores = new ArrayDeque<>();
    private boolean resultadoMostrado = false;

    public Calculadora() {
        CrearUI();
        this.setTitle("Mi Calculadora");
        this.setScene(escena);
        this.show();
        escena.getStylesheets().add(getClass().getResource("/estilos/calculadora.css").toString());
    }

    private void CrearUI() {
        txtPantalla = new TextField("0");
        txtPantalla.setEditable(false);
        gdpTeclado = new GridPane();
        Button btnClear = new Button("Clear");
        btnClear.setOnAction(event -> {
            txtPantalla.clear();
            expresion.setLength(0);
            numeros.clear();
            operadores.clear();
            resultadoMostrado = false;
        });
        CrearTeclado();
        vContenedor = new VBox(txtPantalla, gdpTeclado, btnClear);
        vContenedor.setSpacing(5);
        escena = new Scene(vContenedor, 200, 200);
    }

    private void CrearTeclado() {
        int pos = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                arBotones[i][j] = new Button(arEtiquetas[pos] + "");
                arBotones[i][j].setPrefSize(50, 50);
                int finalPos = pos;
                arBotones[i][j].setOnAction(actionEvent -> {
                    try {
                        char simbolo = arEtiquetas[finalPos];
                        if (resultadoMostrado && Character.isDigit(simbolo)) {
                            txtPantalla.clear();
                            expresion.setLength(0);
                            numeros.clear();
                            operadores.clear();
                            resultadoMostrado = false;
                        }
                        if (expresion.length() >= 15) {
                            throw new IllegalArgumentException("Syntax Error");
                        } else if (simbolo == '=') {
                            EvaluarExpresion();
                        } else if (simbolo == '.' && expresion.length() > 0 && expresion.charAt(expresion.length() - 1) == '.') {
                            throw new IllegalArgumentException("Syntax Error");
                        } else if (simbolo == '.' && expresion.length() == 0) {
                            throw new IllegalArgumentException("Syntax Error");
                        } else if (simbolo == '.' && expresion.length() > 1 && !Character.isDigit(expresion.charAt(expresion.length() - 1))) {
                            throw new IllegalArgumentException("Syntax Error");
                        } else if (!validarExpresion(simbolo)) {
                            throw new IllegalArgumentException("Syntax Error");
                        } else {
                            expresion.append(simbolo);
                            txtPantalla.setText(expresion.toString());
                        }
                    } catch (IllegalArgumentException e) {
                        txtPantalla.setText(e.getMessage());
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

    private boolean validarExpresion(char simbolo) {
        if (expresion.length() == 0 && (simbolo == '+' || simbolo == '*' || simbolo == '/')) {
            return false;
        }
        if (simbolo == '*' && (expresion.length() == 0 || expresion.charAt(expresion.length() - 1) == '/' || expresion.charAt(expresion.length() - 1) == '.')) {
            return false;
        }
        if (simbolo == '-' && (expresion.length() == 0 || expresion.charAt(expresion.length() - 1) == '/' || expresion.charAt(expresion.length() - 1) == '.')) {
            return false;
        }
        if (simbolo == '+' && (expresion.length() == 0 || expresion.charAt(expresion.length() - 1) == '/' || expresion.charAt(expresion.length() - 1) == '.')) {
            return false;
        }
        if (simbolo == '/' && (expresion.length() == 0 || expresion.charAt(expresion.length() - 1) == '/' || expresion.charAt(expresion.length() - 1) == '.')) {
            return false;
        }
        if (simbolo == '.' && (expresion.length() == 0 || !Character.isDigit(expresion.charAt(expresion.length() - 1)))) {
            return false;
        }
        if (!Character.isDigit(simbolo) && !Character.isDigit(expresion.charAt(expresion.length() - 1)) && simbolo != '.') {
            return false;
        }
        return true;
    }

    private void EvaluarExpresion() {
        try {
            if (expresion.length() == 0 || !Character.isDigit(expresion.charAt(expresion.length() - 1))) {
                throw new IllegalArgumentException("Syntax Error");
            }

            StringBuilder numBuilder = new StringBuilder();
            for (char c : expresion.toString().toCharArray()) {
                if (Character.isDigit(c) || c == '.') {
                    numBuilder.append(c);
                } else {
                    if (numBuilder.length() > 0) {
                        double num;
                        if (!esConvertibleADouble(numBuilder.toString())) {
                            throw new IllegalArgumentException("Syntax Error");
                        }
                        num = Double.parseDouble(numBuilder.toString());
                        numeros.push(num);
                        numBuilder.setLength(0);
                    }
                    if (c != '=') {
                        operadores.push(c);
                    }
                }
            }
            if (numBuilder.length() > 0) {
                double num;
                if (!esConvertibleADouble(numBuilder.toString())) {
                    throw new IllegalArgumentException("Syntax Error");
                }
                num = Double.parseDouble(numBuilder.toString());
                numeros.push(num);
            }

            while (!operadores.isEmpty()) {
                char operador = operadores.pop();
                double num2 = numeros.pop();
                double num1 = numeros.pop();
                switch (operador) {
                    case '+':
                        numeros.push(num1 + num2);
                        break;
                    case '-':
                        numeros.push(num1 - num2);
                        break;
                    case '*':
                        numeros.push(num1 * num2);
                        break;
                    case '/':
                        if (num2 != 0) {
                            numeros.push(num1 / num2);
                        } else {
                            throw new ArithmeticException("Syntax Error: no se puede dividir por 0");
                        }
                        break;
                }
            }

            txtPantalla.setText(numeros.pop().toString());
            expresion.setLength(0);
            resultadoMostrado = true;
        } catch (IllegalArgumentException | ArithmeticException e) {
            txtPantalla.setText(e.getMessage());
        }
    }

    private boolean esConvertibleADouble(String numero) {
        if (numero == null || numero.isEmpty()) {
            return false;
        }
        int puntos = 0;
        for (int i = 0; i < numero.length(); i++) {
            if (!Character.isDigit(numero.charAt(i))) {
                if (numero.charAt(i) == '.') {
                    puntos++;
                    if (puntos > 1) {
                        return false;
                    }
                } else {
                    return false;
                }
            }
        }
        return true;
    }
}
*/