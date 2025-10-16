package proyectoFinalApi.proyectoFinalApi.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import proyectoFinalApi.proyectoFinalApi.daos.NoticiaDao;

/**
 * Repositorio para la entidad NoticiaDao.
 * Extiende JpaRepository para proporcionar métodos CRUD para la entidad NoticiaDao.
 */
@Repository
public interface NoticiaRepositorio extends JpaRepository<NoticiaDao, Long> {
	
}
