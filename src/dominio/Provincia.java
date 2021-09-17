package dominio;

public class Provincia 
{
	private Integer id;
	private Pais pais;
	private String nombre;
	
	
	public Provincia() 
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


	public Pais getPais() 
	{
		return pais;
	}


	public void setPais(Pais pais) 
	{
		this.pais = pais;
	}


	public String getNombre() 
	{
		return nombre;
	}


	public void setNombre(String nombre) 
	{
		this.nombre = nombre;
	}
	
	
}
