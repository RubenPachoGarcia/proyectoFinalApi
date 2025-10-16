package proyectoFinalApi.proyectoFinalApi.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import proyectoFinalApi.proyectoFinalApi.daos.NoticiaDao;
import proyectoFinalApi.proyectoFinalApi.dtos.SubirNoticiaDto;
import proyectoFinalApi.proyectoFinalApi.repositorios.NoticiaRepositorio;

/**
 * Servicio que gestiona las operaciones relacionadas con las noticias.
 */
@Service
public class NoticiaServicio {

    @Autowired
    private NoticiaRepositorio noticiaRepositorio;

    /**
     * Guarda una nueva noticia en la base de datos.
     * 
     * @param subirNoticiaDto DTO que contiene los datos necesarios para crear la noticia.
     * @throws IllegalArgumentException si el titular de la noticia es nulo o vacío.
     */
    public void guardarNoticia(SubirNoticiaDto subirNoticiaDto) {
     
        if (subirNoticiaDto.getTitularNoticia() == null || subirNoticiaDto.getTitularNoticia().isEmpty()) {
            throw new IllegalArgumentException("El titular es obligatorio.");
        }

        NoticiaDao noticiaDao = new NoticiaDao();
        noticiaDao.setTitularNoticia(subirNoticiaDto.getTitularNoticia());
        noticiaDao.setCategoriaNoticia(subirNoticiaDto.getCategoriaNoticia());
        noticiaDao.setFotoNoticia(subirNoticiaDto.getFotoNoticia());
        noticiaDao.setIdUsuarioNoticia(subirNoticiaDto.getIdUsuarioNoticia());

        noticiaRepositorio.save(noticiaDao);
    }
}
