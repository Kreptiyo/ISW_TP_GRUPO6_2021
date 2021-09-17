package dominio;

public class Deporte {
	
	private Integer id;
	private String nombre;
	
	public Deporte(String nombre) {
		super();
		this.nombre = nombre;
	}
	
	public Deporte(Integer id, String nombre) {
		super();
		this.id = id;
		this.nombre = nombre;
	}
	
	public Deporte() {
		super();
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}
