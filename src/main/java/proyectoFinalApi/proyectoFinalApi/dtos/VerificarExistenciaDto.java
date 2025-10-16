package proyectoFinalApi.proyectoFinalApi.dtos;

/**
 * Clase que representa los datos del verificar la existencia de correo y telefono en la base de datos.
 */
public class VerificarExistenciaDto {
   
	/** Atributos para verificar la existencia de correo y telefono en la base de datos*/
	private String correoUsuario;
    private String telefonoUsuario;

    /** Getters y setters
	 * Get-lectura
	 * Set-escritura */
    public String getCorreoUsuario() {
        return correoUsuario;
    }

    public void setCorreoUsuario(String correoUsuario) {
        this.correoUsuario = correoUsuario;
    }

    public String getTelefonoUsuario() {
        return telefonoUsuario;
    }

    public void setTelefonoUsuario(String telefonoUsuario) {
        this.telefonoUsuario = telefonoUsuario;
    }
}
