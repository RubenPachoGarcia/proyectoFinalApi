package proyectoFinalApi.proyectoFinalApi.controladores;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import proyectoFinalApi.proyectoFinalApi.daos.UsuarioDao;
import proyectoFinalApi.proyectoFinalApi.servicios.UsuarioServicio;

@RestController
@RequestMapping("/api/administrador")
public class EliminarUsuarioControlador {

    @Autowired
    private UsuarioServicio usuarioServicio;

    @GetMapping("/usuarios")
    public ResponseEntity<List<UsuarioDao>> obtenerUsuarios() {
        List<UsuarioDao> listaUsuarios = usuarioServicio.obtenerTodosLosUsuarios();
        if (listaUsuarios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        System.out.println("Usuarios obtenidos: " + listaUsuarios.size());
        return ResponseEntity.ok(listaUsuarios);
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> eliminarUsuario(@PathVariable Long idUsuario) {
        boolean usuarioEliminado = usuarioServicio.eliminarUsuario(idUsuario);
        if (usuarioEliminado) {
            return ResponseEntity.ok("Usuario eliminado correctamente.");
        } else {
            return ResponseEntity.status(404).body("Usuario no encontrado.");
        }
    }
}
