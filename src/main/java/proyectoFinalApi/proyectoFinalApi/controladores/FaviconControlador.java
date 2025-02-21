package proyectoFinalApi.proyectoFinalApi.controladores;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class FaviconControlador {

    @GetMapping("favicon.ico")
    public ResponseEntity<Void> handleFavicon() {
    	// Devuelve 404 pero sin la página de error
        return ResponseEntity.notFound().build(); 
    }
}
