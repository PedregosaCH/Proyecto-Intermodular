package dam.code.dao.impl;

import dam.code.config.DatabaseConfig;
import dam.code.dao.TaskDAO;
import dam.code.exceptions.TaskException;
import dam.code.models.Task;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TaskDAOImpl implements TaskDAO {
    @Override
    public void registrar(Task task) throws TaskException {
        String sql = "INSERT INTO tasks (titulo, director, duracion, fecha_publicacion) VALUES (?, ?, ?, ?)";

        try (Connection conexion = DatabaseConfig.getConnection();
             PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, task.getTaskName());
            ps.setString(2, task.getTaskDescription());
            ps.setDate(3, Date.valueOf(task.getCreationDate()));
            ps.setDate(4, Date.valueOf(task.getExpirationDate()));

            ps.executeUpdate();

        } catch (SQLException e) {
            throw new TaskException(e.getMessage());
        }
    }

    @Override
    public List<Task> listar() throws TaskException {
        List<Task> tasks = new ArrayList<>();
        String sql = "SELECT * FROM tasks";

        try (Connection con = DatabaseConfig.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                tasks.add(new Task(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("descripccion"),
                        rs.getDate("fecha_creacion").toLocalDate(),
                        rs.getDate("fecha_expiracion").toLocalDate()
                ));
            }

        } catch (SQLException e) {
            throw new TaskException(e.getMessage());
        }

        return tasks;
    }

    /**
     * Este tambien ha de ser cambiado a los collaborators
     */
    @Override
    public List<Task> obtenerTaskPorUsuario(int idUsuario) throws TaskException {
        List<Task> task = new ArrayList<>();
        String sql = """
                SELECT 
                    p.id, 
                    p.titulo, 
                    p.director, 
                    p.duracion, 
                    p.fecha_publicacion,
                    COUNT(v.task_id) AS visualizaciones
                FROM tasks AS p
                INNER JOIN visualizaciones AS v
                    ON p.id = v.task_id 
                    WHERE v.id_usuario = ?
                    GROUP BY p.id, p.titulo, p.director, p.duracion, p.fecha_publicacion 
                """;

        try (Connection conexion = DatabaseConfig.getConnection();
             PreparedStatement ps = conexion.prepareStatement(sql);){

            ps.setInt(1, idUsuario);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Task tasks =  new Task(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("descripccion"),
                        rs.getDate("fecha_creacion").toLocalDate(),
                        rs.getDate("fecha_expiracion").toLocalDate()
                );
                tasks.setVisualizaciones(rs.getInt("visualizaciones"));
                tasks.add(tasks);
            }
        } catch (SQLException e){
            throw new TaskException(e.getMessage());
        }

        return tasks;
    }

    /**
     * todo esto se tiene que modificar para que sean los collaborators
     */
    @Override
    public void visualizar(int user_id, int tasks_id) throws TaskException {
        String sql = "INSERT INTO visualizaciones (user_id, tasks_id) VALUES (?, ?)";

        try (Connection con = DatabaseConfig.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)){

            ps.setInt(1, user_id);
            ps.setInt(2, tasks_id);

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new TaskException(e.getMessage());
        }
    }
}
