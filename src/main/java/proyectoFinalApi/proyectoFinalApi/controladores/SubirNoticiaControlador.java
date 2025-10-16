package proyectoFinalApi.proyectoFinalApi.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;
import proyectoFinalApi.proyectoFinalApi.dtos.SubirNoticiaDto;
import proyectoFinalApi.proyectoFinalApi.servicios.NoticiaServicio;

/**
 * Controlador para la gestión de noticias.
 */
@CrossOrigin(origins = "http://localhost:8080", allowCredentials = "true")
@RestController
@SessionAttributes("idUsuario")  
@RequestMapping("/api/noticias")
public class SubirNoticiaControlador {

    @Autowired
    private NoticiaServicio noticiaServicio;

    /**
     * Recibe y almacena una nueva noticia.
     * @param subirNoticiaDto DTO con la información de la noticia.
     * @return Respuesta con el estado de la recepcion de la noticia.
     */
    @PostMapping("/recibir")
    public ResponseEntity<String> recibirNoticia(@RequestBody SubirNoticiaDto subirNoticiaDto) {
        try {
            if (subirNoticiaDto.getTitularNoticia() == null || subirNoticiaDto.getTitularNoticia().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El titular es obligatorio.");
            }

            noticiaServicio.guardarNoticia(subirNoticiaDto);
            return ResponseEntity.status(HttpStatus.CREATED).body("Noticia registrada correctamente.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("ERROR: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("ERROR interno del servidor.");
        }
    }
}
