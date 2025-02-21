package proyectoFinalApi.proyectoFinalApi.dtos;

/**
 * Clase para comunicar datos entre la API y la capa de servicios RPG-16012025
 */
public class RegistroUsuarioDto {
    
	//Atributos
	private long idUsuario;
    private String nombreCompletoUsuario;
    private String correoUsuario;
    private String telefonoUsuario;
    private byte[] fotoUsuario; 
    private String contraseniaUsuario;
    private String confirmarContraseniaUsuario;
    private String esPremium;
    private String esAdmin;

    // Getters y Setters
    public long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombreCompletoUsuario() {
        return nombreCompletoUsuario;
    }

    public void setNombreCompletoUsuario(String nombreCompletoUsuario) {
        this.nombreCompletoUsuario = nombreCompletoUsuario;
    }

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

    public byte[] getFotoUsuario() {
        return fotoUsuario;
    }

    public void setFotoUsuario(byte[] fotoUsuario) {
        this.fotoUsuario = fotoUsuario;
    }

    public String getContraseniaUsuario() {
        return contraseniaUsuario;
    }

    public void setContraseniaUsuario(String contraseniaUsuario) {
        this.contraseniaUsuario = contraseniaUsuario;
    }

    public String getConfirmarContraseniaUsuario() {
        return confirmarContraseniaUsuario;
    }

    public void setConfirmarContraseniaUsuario(String confirmarContraseniaUsuario) {
        this.confirmarContraseniaUsuario = confirmarContraseniaUsuario;
    }

    public String getEsPremium() {
        return esPremium;
    }

    public void setEsPremium(String esPremium) {
        this.esPremium = esPremium;
    }

    public String getEsAdmin() {
        return esAdmin;
    }

    public void setEsAdmin(String esAdmin) {
        this.esAdmin = esAdmin;
    }
}
