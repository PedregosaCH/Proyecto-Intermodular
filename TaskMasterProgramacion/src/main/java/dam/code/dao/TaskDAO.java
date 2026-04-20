package dam.code.dao;

import dam.code.exceptions.TaskException;
import dam.code.models.Task;

import java.util.List;

public interface TaskDAO {
    void registrar (Task task) throws TaskException;
    List<Task> listar () throws TaskException;
    List<Task> obtenerTaskPorUsuario (int user_id) throws TaskException;
    void participar (int user_id, int task_id) throws TaskException;
}
