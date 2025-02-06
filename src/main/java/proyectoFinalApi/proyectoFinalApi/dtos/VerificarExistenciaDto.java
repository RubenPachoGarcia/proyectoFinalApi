package proyectoFinalApi.proyectoFinalApi.dtos;

public class VerificarExistenciaDto {
    
	//Atributos
	private String correoUsuario;
    private String telefonoUsuario;

    // Getters y Setters
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
