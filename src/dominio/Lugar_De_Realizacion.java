package dominio;

public class Lugar_De_Realizacion {
	
	private Integer id;
	private String nombre;
	private Deporte deporte;
	private Usuario usuario;
	private String descripcion;
	
	public Lugar_De_Realizacion(Integer id, String nombre, Deporte deporte, Usuario usuario, String descripcion) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.deporte = deporte;
		this.usuario = usuario;
		this.descripcion = descripcion;
	}
	
	public Lugar_De_Realizacion(String nombre) {
		super();
		this.nombre = nombre;
	}
	
	public Lugar_De_Realizacion() {
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

	public Deporte getDeporte() {
		return deporte;
	}

	public void setDeporte(Deporte deporte) {
		this.deporte = deporte;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	@Override
	public String toString()
	{
		return this.getNombre();
	}

}
