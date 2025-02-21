package proyectoFinalApi.proyectoFinalApi.controladores;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpSession;
import proyectoFinalApi.proyectoFinalApi.dtos.RegistroUsuarioDto;
import proyectoFinalApi.proyectoFinalApi.dtos.SubirNoticiaDto;
import proyectoFinalApi.proyectoFinalApi.servicios.NoticiaServicio;

//Permite hacer peticiones desde el frontend
@CrossOrigin(origins = "http://localhost:1180", allowCredentials = "true")
@RestController
@SessionAttributes("idUsuario")  // Se mantiene la sesión
@RequestMapping("/api/noticias")
public class SubirNoticiaControlador {

    @Autowired
    private NoticiaServicio noticiaServicio;

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
    
    /*public ResponseEntity<String> recibirNoticia(
            @RequestParam("titularNoticia") String titularNoticia,
            @RequestParam("categoriaNoticia") String categoriaNoticia,
            @RequestParam("fotoNoticia") MultipartFile fotoNoticia,
            HttpSession session) {

        try {
            if (session == null || session.getAttribute("idUsuario") == null) {
                System.out.println(" X ERROR: No se encontró una sesión activa o el id del usuario.");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No hay sesión activa.");
            }

            Long idUsuarioNoticia = (Long) session.getAttribute("idUsuario");
            System.out.println(" El id del usuario recibido desde la sesión: " + idUsuarioNoticia);

            byte[] fotoNoticiaByte = fotoNoticia.getBytes();

            SubirNoticiaDto subirNoticiaDto = new SubirNoticiaDto();
            subirNoticiaDto.setTitularNoticia(titularNoticia);
            subirNoticiaDto.setFotoNoticia(fotoNoticiaByte);
            subirNoticiaDto.setCategoriaNoticia(categoriaNoticia);
            subirNoticiaDto.setIdUsuarioNoticia(idUsuarioNoticia);

            boolean resultado = noticiaServicio.guardarNoticia(subirNoticiaDto);

            if (resultado) {
                return ResponseEntity.status(HttpStatus.CREATED).body("Noticia creada correctamente.");
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al crear la noticia.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error en el servidor.");
        }
    }*/
}
