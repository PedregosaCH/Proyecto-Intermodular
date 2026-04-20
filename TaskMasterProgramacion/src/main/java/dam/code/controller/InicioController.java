package dam.code.controller;

import dam.code.exceptions.TaskException;
import dam.code.exceptions.UserException;
import dam.code.models.Sesion;
import dam.code.models.User;
import dam.code.service.TaskService;
import dam.code.service.UserService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;

public class InicioController {
    @FXML private TextField txtEmail;
    @FXML
    private PasswordField txtPassword;

    @FXML private Label lblMensaje;

    private UserService service;

    public void setUserService(UserService service) {
        this.service = service;
    }

    @FXML
    public void iniciarSesion() {
        if(!validarCampos()) {
            lblMensaje.setText("Todos los campos son obligatorios");
            lblMensaje.setStyle("-fx-text-fill: red");
            return;
        }

        String dni = txtEmail.getText();
        String password = txtPassword.getText();
        try {
            User user = service.login(dni, password);

            Sesion.setUser(user);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Visualizaciones_view.fxml"));
            Parent root = loader.load();
            TaskController controller = loader.getController();
            controller.setUser(user);
            controller.setTaskService(new TaskService());

            Stage stage = (Stage) txtEmail.getScene().getWindow();
            stage.setResizable(false);
            stage.setWidth(800);
            stage.setHeight(600);
            stage.setScene(new Scene(root));
        } catch (TaskException | IOException | UserException e){
            mostrarError(e.getMessage());
        }
    }

    public void registro() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Registro_view.fxml"));
            Parent root = loader.load();
            RegistroController controller = loader.getController();
            controller.setUserServices(service);
            Stage stage = (Stage) txtEmail.getScene().getWindow();
            stage.setResizable(false);
            stage.setWidth(400);
            stage.setHeight(600);
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            mostrarError(e.getMessage());
        }
    }

    private void mostrarError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(message);
        alert.showAndWait();

    }

    private boolean validarCampos() {
        return !txtEmail.getText().isEmpty() && !txtPassword.getText().isEmpty();
    }
}
