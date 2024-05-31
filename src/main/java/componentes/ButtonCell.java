package componentes;

import com.example.tap2024.modelos.EmpleadosDAO;
import javafx.scene.control.*;
import vistas.EmpleadosForm;

import java.util.Optional;

public class ButtonCell  extends TableCell<EmpleadosDAO, String> {
    Button btnCelda;
    int opc;
    EmpleadosDAO objEm;

    public ButtonCell(int opc) {
        this.opc= opc;
        String txtButton =(opc ==1) ? "Editar" : "Eliminar";
        btnCelda =new Button(txtButton);
        btnCelda.setOnAction(event -> AccionBoton(opc));
    }

    private void AccionBoton(int opc) {
        TableView<EmpleadosDAO> tbvEmpleados = ButtonCell.this.getTableView();
        objEm = tbvEmpleados.getItems().get(ButtonCell.this.getIndex());
        if (opc==1){
            //codigo de editar
            new EmpleadosForm(tbvEmpleados, objEm);
        }else {
            //codigo de eliminar
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Mensaje del Sistema");
            alert.setHeaderText("Confirmación de Acción");
            alert.setContentText("¿Deseas borrar el Empleado: "+ objEm.getNomEmpleado()+"?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                objEm.ELIMINAR();
                tbvEmpleados.setItems(objEm.CONSULTAR());
                tbvEmpleados.refresh();
            }
        }

    }


    @Override
    protected void updateItem(String item, boolean empty){
        super.updateItem(item, empty);
        if (!empty)
            this.setGraphic(btnCelda);
    }
}
