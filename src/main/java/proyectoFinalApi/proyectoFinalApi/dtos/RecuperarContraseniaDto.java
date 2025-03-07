package proyectoFinalApi.proyectoFinalApi.dtos;

/**
 * Clase que representa los datos del recuperar contraseña.
 */
public class RecuperarContraseniaDto {

	private String correoUsuario;
	private String nuevaContrasenia;
	private String confirmarNuevaContrasenia;
	
	public String getCorreoUsuario() {
		return correoUsuario;
	}
	public void setCorreoUsuario(String correoUsuario) {
		this.correoUsuario = correoUsuario;
	}
	public String getNuevaContrasenia() {
		return nuevaContrasenia;
	}
	public void setNuevaContrasenia(String nuevaContrasenia) {
		this.nuevaContrasenia = nuevaContrasenia;
	}
	public String getConfirmarNuevaContrasenia() {
		return confirmarNuevaContrasenia;
	}
	public void setConfirmarNuevaContrasenia(String confirmarNuevaContrasenia) {
		this.confirmarNuevaContrasenia = confirmarNuevaContrasenia;
	}	
}
