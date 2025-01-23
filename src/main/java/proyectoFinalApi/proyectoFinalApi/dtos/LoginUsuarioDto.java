package proyectoFinalApi.proyectoFinalApi.dtos;

/**
 * Clase que representa los datos del login.
 * Esta clase es utilizada para transferir los datos para la autenticación de un usuario
 */
public class LoginUsuarioDto {

    /** Id del usuario. */
    private Long idUsuario;
    
    /** Correo del usuario. */
    private String correoUsuario;
    
    /** Contraseña del usuario. */
    private String contraseniaUsuario;
    
    /** Rol del usuario */
    private String esAdmin;

    /**
     * Obtiene el id
     * @return El id del usuario.
     */
  	public long getIdUsuario() {
  		return idUsuario;
  	}
  	/**
     * Establece el id
     * @param idUsuario El id del usuario.
     */
  	public void setIdUsuario(long idUsuario) {
  		this.idUsuario = idUsuario;
  	}

  	
  	/**
     * Obtiene el correo
     * @return El correo del usuario.
     */
  	public String getCorreoUsuario() {
  		return correoUsuario;
  	}
  	/**
     * Establece el correo
     * @param correoUsuario El correo del usuario.
     */
  	public void setCorreoUsuario(String correoUsuario) {
  		this.correoUsuario = correoUsuario;
  	}
  	
  	
  	public String getContraseniaUsuario() {
  		return contraseniaUsuario;
  	}
  	/**
     * Establece la contrasenia
     * @param contraseniaUsuario La contrasenia del usuario.
     */
  	public void setContraseniaUsuario(String contraseniaUsuario) {
  		this.contraseniaUsuario = contraseniaUsuario;
  	}
  	
  	/**
     * Obtiene el rol
     * @return El rol del usuario.
     */
  	public String getEsAdmin() {
  		return esAdmin;
  	}
  	/**
     * Establece el rol
     * @param esAdmin El rol del usuario.
     */
  	public void setEsAdmin(String esAdmin) {
  		this.esAdmin = esAdmin;
  	}
  }
