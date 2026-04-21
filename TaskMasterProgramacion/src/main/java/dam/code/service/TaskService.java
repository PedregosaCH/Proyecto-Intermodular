package dam.code.service;

import dam.code.dao.TaskDAO;
import dam.code.dao.impl.TaskDAOImpl;
import dam.code.exceptions.TaskException;
import dam.code.models.Task;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;

public class TaskService {
    private final TaskDAO taskDAO = new TaskDAOImpl();
    public ObservableList<Task> obtenerTasks() throws TaskException {
        return FXCollections.observableArrayList(TaskDAO.listar());
    }

    /**
     * esto se tiene que ajustar para que sean los participantes/collaborators
     */
    public void addVisualizacion(int idUsuario, Task task) throws TaskException {
        taskDAO.visualizar(idUsuario, task.getId());
    }

    public void agregarTask(Task task) throws TaskException {
        validarTask(task);

        taskDAO.registrar(task);
    }

    private void validarTask(Task task) throws TaskException {
        if (task.getTaskDescription().length() < 2) {
            throw new TaskException("La descripcción es muy corto");
        }
        if (task.getCreationDate().isBefore(LocalDate.now())) {
            throw new TaskException("No se puede iniciar una tarea antes de hoy");
        }
        if (task.getExpirationDate().isAfter(LocalDate.of(2100, 12, 31))){
            throw new TaskException("La fecha de publicacion no puede ser inferior a la creacion del cine");
        }
    }

    public ObservableList<Task> obtenerTasksPorUsuario(int userid) throws TaskException {
        return FXCollections.observableArrayList(taskDAO.obtenerTaskPorUsuario((userId)));
    }
}
