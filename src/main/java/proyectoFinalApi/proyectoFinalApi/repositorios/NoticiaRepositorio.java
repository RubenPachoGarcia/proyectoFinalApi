package proyectoFinalApi.proyectoFinalApi.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import proyectoFinalApi.proyectoFinalApi.daos.NoticiaDao;

@Repository
public interface NoticiaRepositorio extends JpaRepository<NoticiaDao, Long> {
    
}
