package proyectoFinalApi.proyectoFinalApi.daos;

import java.util.Arrays;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/** Clase entidad que representa una noticia */
@Entity
@Table(name = "noticias", schema = "proyecto")
public class NoticiaDao {

	/** Atributos de la noticia*/
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_noticia", updatable = false)
	private long idNoticia;

	@Column(name = "titular_noticia", length = 50)
	private String titularNoticia;

	@Column(name = "foto_noticia", columnDefinition = "bytea")
	private byte[] fotoNoticia;

	@Column(name = "categoria_noticia", length = 20)
	private String categoriaNoticia;

	@Column(name = "id_usuario_noticia", length = 20)
	private long idUsuarioNoticia;

	/** Constructor con los campos de la noticia*/	
	public NoticiaDao(long idNoticia, String titularNoticia, byte[] fotoNoticia, String categoriaNoticia,
			long idUsuarioNoticia) {
		super();
		this.idNoticia = idNoticia;
		this.titularNoticia = titularNoticia;
		this.fotoNoticia = fotoNoticia;
		this.categoriaNoticia = categoriaNoticia;
		this.idUsuarioNoticia = idUsuarioNoticia;
	}
	
	/** Constructor vacio */
	public NoticiaDao() {
		super();
	}

	/** Getters y setters
	 * Get-lectura
	 * Set-escritura */
	public long getIdNoticia() {
		return idNoticia;
	}

	public void setIdNoticia(long idNoticia) {
		this.idNoticia = idNoticia;
	}

	public String getTitularNoticia() {
		return titularNoticia;
	}

	public void setTitularNoticia(String titularNoticia) {
		this.titularNoticia = titularNoticia;
	}

	public byte[] getFotoNoticia() {
		return fotoNoticia;
	}

	public void setFotoNoticia(byte[] fotoNoticia) {
		this.fotoNoticia = fotoNoticia;
	}

	public String getCategoriaNoticia() {
		return categoriaNoticia;
	}

	public void setCategoriaNoticia(String categoriaNoticia) {
		this.categoriaNoticia = categoriaNoticia;
	}

	public long getIdUsuarioNoticia() {
		return idUsuarioNoticia;
	}

	public void setIdUsuarioNoticia(long idUsuarioNoticia) {
		this.idUsuarioNoticia = idUsuarioNoticia;
	}

	/** Metodo toString */	
	@Override
	public String toString() {
		return "NoticiaDao [idNoticia=" + idNoticia + ", titularNoticia=" + titularNoticia + ", fotoNoticia="
				+ Arrays.toString(fotoNoticia) + ", categoriaNoticia=" + categoriaNoticia + ", idUsuarioNoticia="
				+ idUsuarioNoticia + "]";
	}
}
