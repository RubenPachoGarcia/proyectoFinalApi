package proyectoFinalApi.proyectoFinalApi.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import proyectoFinalApi.proyectoFinalApi.daos.UsuarioDao;
import proyectoFinalApi.proyectoFinalApi.dtos.RegistroUsuarioDto;
import proyectoFinalApi.proyectoFinalApi.repositorios.UsuarioRepositorio;

@Service
public class UsuarioServicio {

    @Autowired
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    private PasswordEncoder encriptacion;

    public ResponseEntity<String> validarDatos(String correoUsuario, String contraseniaUsuario) {
        UsuarioDao usuarioDao = usuarioRepositorio.findByCorreoUsuario(correoUsuario);

        if (usuarioDao == null) {
            return ResponseEntity.status(401).body("Datos incorrectos");
        }

        if (!encriptacion.matches(contraseniaUsuario, usuarioDao.getContraseniaUsuario())) {
            return ResponseEntity.status(401).body("Datos incorrectos");
        }

        return ResponseEntity.ok(usuarioDao.getEsAdmin());
    }

    public boolean correoUsuarioExiste(String correoUsuario) {
        return usuarioRepositorio.existsByCorreoUsuario(correoUsuario);
    }

    public void registroUsuario(RegistroUsuarioDto usuarioDto) {
        if (usuarioDto.getCorreoUsuario() == null || usuarioDto.getCorreoUsuario().isEmpty()) {
            throw new IllegalArgumentException("El correo es obligatorio.");
        }

        UsuarioDao usuarioDao = new UsuarioDao();
        usuarioDao.setNombreCompletoUsuario(usuarioDto.getNombreCompletoUsuario());
        usuarioDao.setCorreoUsuario(usuarioDto.getCorreoUsuario());
        usuarioDao.setTelefonoUsuario(usuarioDto.getTelefonoUsuario());
        usuarioDao.setFotoUsuario(usuarioDto.getFotoUsuario());
        usuarioDao.setContraseniaUsuario(encriptacion.encode(usuarioDto.getContraseniaUsuario()));
        usuarioDao.setEsAdmin("false");
        usuarioDao.setEsPremium("false");

        usuarioRepositorio.save(usuarioDao);
    }
}
