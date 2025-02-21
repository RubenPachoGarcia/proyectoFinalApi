package proyectoFinalApi.proyectoFinalApi.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import proyectoFinalApi.proyectoFinalApi.daos.NoticiaDao;
import proyectoFinalApi.proyectoFinalApi.daos.UsuarioDao;
import proyectoFinalApi.proyectoFinalApi.dtos.RegistroUsuarioDto;
import proyectoFinalApi.proyectoFinalApi.dtos.SubirNoticiaDto;
import proyectoFinalApi.proyectoFinalApi.repositorios.NoticiaRepositorio;

@Service
public class NoticiaServicio {

    @Autowired
    private NoticiaRepositorio noticiaRepositorio;

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
    
    /*
	 * try { // Convertir el DTO a DAO (esto incluye la conversión de la imagen a
	 * byte[]) NoticiaDao noticiaDao = new NoticiaDao();
	 * noticiaDao.setTitularNoticia(subirNoticiaDto.getTitularNoticia());
	 * noticiaDao.setFotoNoticia(subirNoticiaDto.getFotoNoticia()); // Guardamos la
	 * imagen como byte[]
	 * noticiaDao.setCategoriaNoticia(subirNoticiaDto.getCategoriaNoticia());
	 * noticiaDao.setIdUsuarioNoticia(subirNoticiaDto.getIdUsuarioNoticia()); //
	 * Asignamos el ID del usuario
	 * 
	 * // Guardar la noticia en la base de datos
	 * noticiaRepositorio.save(noticiaDao);
	 * 
	 * return true; } catch (Exception e) { e.printStackTrace(); return false; }
	 */


