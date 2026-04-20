package main;

import dao.UsuarioDAO;
import modelo.Usuario;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        UsuarioDAO usuarioDAO = new UsuarioDAO();

        // INSERTAR
        Usuario nuevoUsuario = new Usuario(
                "Pedro Prueba",
                "pedro@restaurante.com",
                "123456",
                "Empleado"
        );

        boolean insertado = usuarioDAO.insertarUsuario(nuevoUsuario);
        if (insertado) {
            System.out.println("Usuario insertado correctamente.");
        } else {
            System.out.println("No se pudo insertar el usuario.");
        }

        // LISTAR
        System.out.println("\nLISTA DE USUARIOS:");
        List<Usuario> listaUsuarios = usuarioDAO.listarUsuarios();
        for (Usuario usuario : listaUsuarios) {
            System.out.println(usuario);
        }

        // BUSCAR POR ID
        System.out.println("\nBUSCAR USUARIO CON ID 1:");
        Usuario usuarioEncontrado = usuarioDAO.buscarUsuarioPorId(1);
        if (usuarioEncontrado != null) {
            System.out.println(usuarioEncontrado);
        } else {
            System.out.println("No se encontró el usuario.");
        }

        // ACTUALIZAR
        if (usuarioEncontrado != null) {
            usuarioEncontrado.setNombre("Carlos Administrador");
            usuarioEncontrado.setRol("Administrador General");

            boolean actualizado = usuarioDAO.actualizarUsuario(usuarioEncontrado);
            if (actualizado) {
                System.out.println("\nUsuario actualizado correctamente.");
            } else {
                System.out.println("\nNo se pudo actualizar el usuario.");
            }
        }

        // LISTAR NUEVAMENTE
        System.out.println("\nLISTA DE USUARIOS DESPUÉS DE ACTUALIZAR:");
        listaUsuarios = usuarioDAO.listarUsuarios();
        for (Usuario usuario : listaUsuarios) {
            System.out.println(usuario);
        }

        // ELIMINAR
        boolean eliminado = usuarioDAO.eliminarUsuario(2);
        if (eliminado) {
            System.out.println("\nUsuario eliminado correctamente.");
        } else {
            System.out.println("\nNo se pudo eliminar el usuario o no existe.");
        }

        // LISTAR FINAL
        System.out.println("\nLISTA FINAL DE USUARIOS:");
        listaUsuarios = usuarioDAO.listarUsuarios();
        for (Usuario usuario : listaUsuarios) {
            System.out.println(usuario);
        }
    }
}
