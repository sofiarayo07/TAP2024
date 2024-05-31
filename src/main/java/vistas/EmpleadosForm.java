package  vistas;

import com.example.tap2024.modelos.EmpleadosDAO;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
public class EmpleadosForm extends Stage {

    private TableView<EmpleadosDAO> tbvEmpleados;
    private EmpleadosDAO objEmp;
    String[] arrPromts = {"Nombre del empleado","RFC del empleado","Sueldo del empleado","Telefono del empelado","Direccion del empleado"};
    private Scene escena;
    private TextField[] artxtCampos = new TextField[5];
    private Button btnGuardar;
    private VBox vbxPrincipal;
    public EmpleadosForm(TableView<EmpleadosDAO> tbvEmp, EmpleadosDAO objEmp) {
        tbvEmpleados = tbvEmp;
        this.objEmp =(objEmp == null) ? new EmpleadosDAO() : objEmp;
        CrearUI();
        this.setTitle("Insertar Usuario");
        this.setScene(escena);
        this.show();
    }

    private void CrearUI() {
        vbxPrincipal = new VBox();
        vbxPrincipal.setPadding(new Insets(10));
        vbxPrincipal.setSpacing(10);
        vbxPrincipal.setAlignment(Pos.CENTER);
        for(int i = 0;i<artxtCampos.length;i++){
            artxtCampos[i] = new TextField();
            artxtCampos[i].setPromptText(arrPromts[i]);
            vbxPrincipal.getChildren().add(artxtCampos[i]);
        }
        LlenarForm();
        btnGuardar = new Button("Guardar");
        btnGuardar.setOnAction(event ->GuardarEmpleado());
        vbxPrincipal.getChildren().add(btnGuardar);
        escena = new Scene(vbxPrincipal,350,250);
    }

    private void LlenarForm() {
        artxtCampos[0].setText(objEmp.getNomEmpleado());
        artxtCampos[1].setText(objEmp.getRfcEmpleado());
        artxtCampos[2].setText(objEmp.getSalario()+"");
        artxtCampos[3].setText(objEmp.getTelefono());
        artxtCampos[4].setText(objEmp.getDireccion());
    }

    private void GuardarEmpleado() {
        objEmp.setNomEmpleado(artxtCampos[0].getText());
        objEmp.setRfcEmpleado(artxtCampos[1].getText());
        objEmp.setSalario(Float.parseFloat(artxtCampos[2].getText()));
        objEmp.setTelefono(artxtCampos[3].getText());
        objEmp.setDireccion(artxtCampos[4].getText());
        if (objEmp.getIdEmpleado() > 0)
            objEmp.ACTUALIZAR();
        else
            objEmp.INSERTAR();
        objEmp.INSERTAR();
        tbvEmpleados.setItems(objEmp.CONSULTAR());
        tbvEmpleados.refresh();

        artxtCampos[0].clear();
        artxtCampos[1].clear();
        artxtCampos[2].clear();
        artxtCampos[3].clear();
        artxtCampos[4].clear();
    }

}