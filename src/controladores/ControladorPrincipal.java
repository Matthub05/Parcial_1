package controladores;

import Excepciones.CampoNumericoInvalidoException;
import Excepciones.CamposVaciosException;
import Excepciones.IdEnUsoException;
import Excepciones.ElementoNoSeleccionadoException;
import conexion.ConexionDB;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.mariadb.jdbc.Connection;

/**
 *
 * @author Mateo
 */
public class ControladorPrincipal {
 
    private static Connection realizarConexion() {
        try {
            ConexionDB conexionD = new ConexionDB();
            return conexionD.getConexion();
        } catch (Exception ex) {
            System.err.println("Conexión fallida");
        }
        return null;
    }
    
    public void insertarTabla(String id, String nombre, String especie, String raza, String edad, String nombrePropietario, String cedula) throws SQLException {
        if (id.isBlank()
                || nombre.isBlank()
                || especie.isBlank()
                || raza.isBlank()
                || edad.isBlank()
                || nombrePropietario.isBlank()
                || cedula.isBlank()) {
            throw new CamposVaciosException();
        }

        ResultSet consultarId = consultarId(id);
        if (consultarId.next()) {
            throw new IdEnUsoException();
        }
        
        if (noEsNumero(edad) || Integer.parseInt(edad) < 0) {
            throw new CampoNumericoInvalidoException();
        }

        try {
            PreparedStatement ps = realizarConexion().prepareStatement("INSERT INTO `mascota` (`id`, `nombre`, `especie`, `raza`, `edad`, `nombrePropietario`, `cedulaPropietario`) VALUES (?, ?, ?, ?, ?, ?, ?)");
            ps.setString(1, id);
            ps.setString(2, nombre);
            ps.setString(3, especie);
            ps.setString(4, raza);
            ps.setString(5, edad);
            ps.setString(6, nombrePropietario);
            ps.setString(7, cedula);

            ps.execute();

        } catch (SQLException ex) {
            System.err.print(ex);
        }
    }

    public ResultSet listarTabla() {
        try {
            PreparedStatement ps = realizarConexion().prepareStatement("SELECT * FROM mascota");

            return ps.executeQuery();
        } catch (SQLException ex) {
            System.err.print(ex);
        }
        return null;
    }

    public ResultSet buscarCoincidencias(String where) {
        try {
            PreparedStatement ps = realizarConexion().prepareStatement("SELECT * FROM mascota WHERE id LIKE CONCAT('%',?,'%')");
            ps.setString(1, where);

            return ps.executeQuery();
        } catch (SQLException ex) {
            System.err.print(ex);
        }
        return null;
    }

    public ResultSet consultarId(String id) {
        try {
            PreparedStatement ps = realizarConexion().prepareStatement("SELECT * FROM mascota WHERE id = ?");
            ps.setString(1, id);

            return ps.executeQuery();
        } catch (SQLException ex) {
            System.err.print(ex);
        }
        return null;
    }

    public void actualizarTabla(String id, String nombre, String especie, String raza, String edad, String nombrePropietario, String cedula) {
        if (id.isBlank()) {
            throw new ElementoNoSeleccionadoException();
        }

        if (nombre.isBlank()
                || especie.isBlank()
                || raza.isBlank()
                || edad.isBlank()
                || nombrePropietario.isBlank()
                || cedula.isBlank()) {
            throw new CamposVaciosException();
        }

        if (noEsNumero(edad) || Integer.parseInt(edad) < 0) {
            throw new CampoNumericoInvalidoException();
        }

        try {
            PreparedStatement ps = realizarConexion().prepareStatement("UPDATE mascota SET nombre = ?, especie = ?, raza = ?, edad = ?, nombrePropietario = ?, cedulaPropietario = ? WHERE id = ?");
            ps.setString(1, nombre);
            ps.setString(2, especie);
            ps.setString(3, raza);
            ps.setString(4, edad);
            ps.setString(5, nombrePropietario);
            ps.setString(6, cedula);
            ps.setString(7, id);
            
            ps.execute();

        } catch (SQLException ex) {
            System.err.print(ex);
        }
    }

    public void eliminarTabla(String id) {
        if (id.isBlank()) {
            throw new ElementoNoSeleccionadoException();
        }

        try {
            PreparedStatement ps = realizarConexion().prepareStatement("DELETE FROM mascota WHERE id = ?");
            ps.setString(1, id);

            ps.execute();

        } catch (SQLException ex) {
            System.err.print(ex);
        }
    }
    
    private boolean noEsNumero(String entrada) {
        Integer entero;
        try {
            entero = Integer.parseInt(entrada);
            return false;
        } catch (Exception e) {
            return true;
        }
    }
}
