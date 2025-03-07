package proyectoFinalApi.proyectoFinalApi.dtos;

/**
 * Clase que representa los datos del login.
 */
public class LoginUsuarioDto {

	/** Atributos del login*/
    private Long idUsuario;
    private String correoUsuario;
    private String contraseniaUsuario;
    private String esAdmin;
    
    /** Getters y setters
	 * Get-lectura
	 * Set-escritura */
  	public long getIdUsuario() {
  		return idUsuario;
  	}
  	public void setIdUsuario(long idUsuario) {
  		this.idUsuario = idUsuario;
  	}
  	public String getCorreoUsuario() {
  		return correoUsuario;
  	}
  	public void setCorreoUsuario(String correoUsuario) {
  		this.correoUsuario = correoUsuario;
  	}	
  	public String getContraseniaUsuario() {
  		return contraseniaUsuario;
  	}
  	public void setContraseniaUsuario(String contraseniaUsuario) {
  		this.contraseniaUsuario = contraseniaUsuario;
  	}
  	public String getEsAdmin() {
  		return esAdmin;
  	}
  	public void setEsAdmin(String esAdmin) {
  		this.esAdmin = esAdmin;
  	}
  }
