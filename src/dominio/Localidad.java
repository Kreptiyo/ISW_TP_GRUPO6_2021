package dominio;

public class Localidad 
{
	private Integer id;
	private Provincia provincia;
	private String nombre;
	private Integer codigo_postal;
	
	public Localidad() 
	{
		super();
	}

	public Integer getId() 
	{
		return id;
	}

	public void setId(Integer id) 
	{
		this.id = id;
	}

	public Provincia getProvincia() 
	{
		return provincia;
	}

	public void setProvincia(Provincia provincia) 
	{
		this.provincia = provincia;
	}

	public String getNombre() 
	{
		return nombre;
	}

	public void setNombre(String nombre) 
	{
		this.nombre = nombre;
	}

	public Integer getCodigo_postal() 
	{
		return codigo_postal;
	}

	public void setCodigo_postal(Integer codigo_postal) 
	{
		this.codigo_postal = codigo_postal;
	}
	
	
}
