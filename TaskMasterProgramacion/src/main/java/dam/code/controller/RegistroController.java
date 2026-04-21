package dam.code.controller;

import dam.code.exceptions.UserException;
import dam.code.models.User;
import dam.code.models.utils.Rol;
import dam.code.service.UserService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;

public class RegistroController {
    @FXML private TextField txtUserNickname;
    @FXML private TextField txtName;
    @FXML private TextField txtSurname;
    @FXML private TextField txtEmail;
    @FXML private TextField txtPhoneNumber;
    @FXML private TextField txtConfirmPassword;
    @FXML private PasswordField txtPassword;
    @FXML private PasswordField txtPasswordRepeat;

    @FXML private ComboBox<Rol> comboRol;

    @FXML private Label lblMensaje;

    private UserService service;

    public void setUserServices(UserService service) {
        this.service = service;
    }

    @FXML
    private void initialize() {
        comboRol.setItems(FXCollections.observableArrayList(Rol.values()));
    }

    @FXML
    private void registrarUser() {
        if (validarPassword()) {
            try {
                if (!validarCampos()) {
                    lblMensaje.setText("Todos los campos son obligatorios");
                    lblMensaje.setStyle("-fx-text-fill: red;");
                    return;
                }
                User user = new User(
                        txtUserNickname.getText(),
                        txtName.getText(),
                        txtSurname.getText(),
                        txtEmail.getText(),
                        txtPhoneNumber.getLength(),
                        comboRol.getValue()
                );
                service.registrar(user, txtPassword.getText());
                lblMensaje.setText("Usuario registrado con exito");
                lblMensaje.setStyle("-fx-text-fill: lightgreen;");
                limpiarCampos();
            } catch (UserException e) {
                mostrarError(e.getMessage());
            }
        } else {
            lblMensaje.setText("Las contraseñas no coinciden");
        }
    }

    private void limpiarCampos() {
        txtUserNickname.clear();
        txtName.clear();
        txtEmail.clear();
        txtPassword.clear();
        txtPasswordRepeat.clear();
        comboRol.setValue(null);
    }

    @FXML
    private void inicio() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Inicio_view.fxml"));
            Parent root = loader.load();
            InicioController controller = loader.getController();
            controller.setUserService(service);
            Stage stage = (Stage) txtEmail.getScene().getWindow();
            stage.setResizable(false);
            stage.setWidth(400);
            stage.setHeight(600);
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            mostrarError(e.getMessage());
        }
    }

    private boolean validarCampos() {
        return !txtSurname.getText().isEmpty()
                && !txtName.getText().isEmpty()
                && !txtEmail.getText().isEmpty()
                && !txtPassword.getText().isEmpty()
                && !txtPasswordRepeat.getText().isEmpty()
                && comboRol.getValue() != null;
    }

    private boolean validarPassword() {
        return txtPasswordRepeat.getText().equals(txtPassword.getText());
    }

    private void mostrarError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
