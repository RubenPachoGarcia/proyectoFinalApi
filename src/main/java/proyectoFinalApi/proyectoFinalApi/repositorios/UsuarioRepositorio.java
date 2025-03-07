package proyectoFinalApi.proyectoFinalApi.repositorios;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import proyectoFinalApi.proyectoFinalApi.daos.UsuarioDao;

/**
 * Repositorio para la entidad UsuarioDao.
 * Extiende JpaRepository para proporcionar métodos CRUD para la entidad UsuarioDao.
 */
@Repository
public interface UsuarioRepositorio extends JpaRepository<UsuarioDao, Long> {
	
    /**
     * Busca al usuario por su correo.
     */
	Optional<UsuarioDao> findByCorreoUsuario(String correoUsuario);  
    void deleteByCorreoUsuario(String correoUsuario);
    
    /**
     * Verifica si un usuario ya existe en la bbdd segun el correo.
     */
    boolean existsByCorreoUsuario(String correoUsuario);

    /**
     * Actualiza la contraseña de un usuario según su correo.
     */
    @Modifying
    @Transactional
    @Query("UPDATE UsuarioDao u SET u.contraseniaUsuario = :nuevaContraseniaUsuario WHERE u.correoUsuario = :correoUsuario")
    void actualizarContrasenia(String correoUsuario, String nuevaContraseniaUsuario);

    /**
     * Método corregido para verificar si un correo existe.
     */
    default boolean existeCorreo(String correoUsuario) {
        return existsByCorreoUsuario(correoUsuario);
    }
}
