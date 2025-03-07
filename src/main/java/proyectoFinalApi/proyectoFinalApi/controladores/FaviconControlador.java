package proyectoFinalApi.proyectoFinalApi.controladores;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador para manejar solicitudes al favicon.
 * Evita que el servidor devuelva una página de error cuando el navegador solicita "favicon.ico".
 */
@RestController
@RequestMapping("/")
public class FaviconControlador {

    /**
     * Maneja solicitudes a "favicon.ico" devolviendo un 404.
     * @return ResponseEntity con un estado 404 (No encontrado).
     */
    @GetMapping("favicon.ico")
    public ResponseEntity<Void> handleFavicon() {
        return ResponseEntity.notFound().build();
    }
}
