package proyectoFinalApi.proyectoFinalApi.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import proyectoFinalApi.proyectoFinalApi.daos.UsuarioDao;

@Repository
public interface UsuarioRepositorio extends JpaRepository<UsuarioDao, Long> {

    /**
     * Busca al usuario por su correo.
     * 
     * @param correoUsuario 
     * @return el usuario encontrado segun el correo.
     */
    UsuarioDao findByCorreoUsuario(String correoUsuario);

    /**
     * Verifica si un usuario ya existe en la bbdd segun el correo.
     * 
     * @param correoUsuario 
     * @return true si el usuario con ese correo existe, false si no existe.
     */
    boolean existsByCorreoUsuario(String correoUsuario); 
}
