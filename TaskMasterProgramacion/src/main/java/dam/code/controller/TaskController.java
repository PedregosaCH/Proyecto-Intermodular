package dam.code.controller;

import dam.code.exceptions.TaskException;
import dam.code.models.Task;
import dam.code.models.User;
import dam.code.service.TaskService;
import dam.code.service.UserService;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class TaskController {

    private User user;
    private TaskService service;

    @FXML private Label lblUser;

    @FXML private TextField txtTaskName;
    @FXML private TextField txtDescription;
    @FXML private DatePicker txtCreationDate;
    @FXML private DatePicker txtExpirationDate;

    @FXML private TableView<Task> tablaTasks;
    @FXML private TableColumn<Task, Integer> colId;
    @FXML private TableColumn<Task, String> colTaskName;
    @FXML private TableColumn<Task, String> colDescription;
    @FXML private TableColumn<Task, LocalDate> colCreationDate;
    @FXML private TableColumn<Task, LocalDate> colExpirationDate;

    public void setUser(User user) {
        this.user = user;
        lblUser.setText("Usuario: " + user.getName());
    }

    public void setTaskService (TaskService service) throws TaskException {
        this.service = service;
        tablaTasks.setItems(service.obtenerTask());
    }

    @FXML
    private void initialize() {
        prefWidthColumns();

        colId.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getId()).asObject());
        colTaskName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTaskName()));
        colDescription.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTaskDescription()));
        colCreationDate.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getCreationDate()));
        colExpirationDate.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getExpirationDate()));

        txtCreationDate.setEditable(false);
        validarPicker();
    }

    private void validarPicker() {
        txtCreationDate.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate item, boolean empty) {
                super.updateItem(item, empty);
                if (item !=null && item.isAfter(LocalDate.now())) {
                    setDisable(true);
                    setStyle("-fx-background-color: #d0d0d0");
                }
            }
        });
    }

    private void prefWidthColumns(){
        tablaTasks.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);

        colId.prefWidthProperty().bind(tablaTasks.widthProperty().multiply(0.05));
        colTaskName.prefWidthProperty().bind(tablaTasks.widthProperty().multiply(0.35));
        colDescription.prefWidthProperty().bind(tablaTasks.widthProperty().multiply(0.30));
        colCreationDate.prefWidthProperty().bind(tablaTasks.widthProperty().multiply(0.15));
        colExpirationDate.prefWidthProperty().bind(tablaTasks.widthProperty().multiply(0.15));

        setVisualizacion();
    }

    /**
     * lo mismo para que sean colaboraciones y no visualizaciones
     */
    private void setVisualizacion() {
        tablaTasks.setRowFactory(tv -> {
            TableRow<Task> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2) {
                    Task task = row.getItem();
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Tarea");
                    alert.setHeaderText("Añadir Colaboracion");
                    alert.setContentText("Quieres participar en: " + task.getTaskName());
                    alert.showAndWait().ifPresent(response -> {
                        if (response == ButtonType.OK) {
                            try {
                                service.addVisualizacion(user.getId(), task);
                                tablaTasks.setItems(service.obtenerTasks());
                            } catch (TaskException e) {
                                mostrarError(e.getMessage());
                            }
                        }
                    });
                }
            });
            return row;
        });
    }

    @FXML
    public void addTask() {
        try {
            if (!validarCampos()) throw new TaskException("Todos los campos son obligatorios");
            Task task = new Task (
                    colTaskName.getText(),
                    colDescription.getText(),
                    LocalDate.parse(txtCreationDate.getValue().toString()),
                    LocalDate.parse(txtExpirationDate.getValue().toString())
            );
            service.agregarTask(task);
            tablaTasks.setItems(service.obtenerTask());
            limpiarCampos();
        } catch (TaskException | DateTimeParseException e) {
            mostrarError(e.getMessage());
        } catch (NumberFormatException e) {
            mostrarError("La duracion tiene que ser un numero valido");
        }
    }

    private void limpiarCampos() {
        txtTaskName.clear();
        txtDescription.clear();
        txtCreationDate.setValue(null);
        txtExpirationDate.setValue(null);
    }

    private boolean validarCampos() {
        return !txtTaskName.getText().isBlank()
                && !txtDescription.getText().isBlank()
                && txtCreationDate.getValue() != null
                && txtExpirationDate.getValue() != null;
    }

    /**
     *Se tiene que cambiar a los respectivos cosos en el "VisualizazionController"
     * y el view, hay que crear el temario correcto, sea pues, un controller de los collaborators
     * para que estos puedan participar en las tareas
     */
    @FXML
    public void visualizaciones() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Visualizaciones_view.fxml"));
            Parent root = loader.load();
            CollaboratorController controller = loader.getController();
            controller.setUser(user);
            controller.setTaskService(service);

            Stage stage = (Stage) txtTaskName.getScene().getWindow();
            stage.setResizable(false);
            stage.setWidth(800);
            stage.setHeight(600);
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            mostrarError (e.getMessage());
        }
    }

    @FXML
    public void cerrarSesion() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cerrar Sesion");
        alert.setHeaderText("Seguro que Quieres Cerrar Sesion?");
        alert.setContentText("Se Cerrara la Sesion Actual");
        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Inicio_view.fxml"));
                    Parent root = loader.load();
                    InicioController controller = loader.getController();
                    controller.setUserService(new UserService());

                    Stage stage = (Stage) txtTaskName.getScene().getWindow();
                    stage.setResizable(false);
                    stage.setWidth(400);
                    stage.setHeight(600);
                    stage.setScene(new Scene(root));
                } catch (Exception e) {
                    mostrarError(e.getMessage());
                }
            }
        });
    }

    private void mostrarError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
