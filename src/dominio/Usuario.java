package dominio;

public class Usuario 
{
	private Integer cuil;
	private Localidad localidad;
	private Integer dni;
	private String tipo_documento;
	private String nombre;
	private String apellido;
	private String email;
	private String contraseña;
	
	
	public Usuario() 
	{
		super();
	}


	public Integer getCuil() 
	{
		return cuil;
	}


	public void setCuil(Integer cuil) 
	{
		this.cuil = cuil;
	}


	public Localidad getLocalidad() 
	{
		return localidad;
	}


	public void setLocalidad(Localidad localidad) 
	{
		this.localidad = localidad;
	}


	public Integer getDni() 
	{
		return dni;
	}


	public void setDni(Integer dni) 
	{
		this.dni = dni;
	}


	public String getTipo_documento() 
	{
		return tipo_documento;
	}


	public void setTipo_documento(String tipo_documento) 
	{
		this.tipo_documento = tipo_documento;
	}


	public String getNombre() 
	{
		return nombre;
	}


	public void setNombre(String nombre) 
	{
		this.nombre = nombre;
	}


	public String getApellido() 
	{
		return apellido;
	}


	public void setApellido(String apellido) 
	{
		this.apellido = apellido;
	}


	public String getEmail() 
	{
		return email;
	}


	public void setEmail(String email) 
	{
		this.email = email;
	}


	public String getContraseña() 
	{
		return contraseña;
	}


	public void setContraseña(String contraseña) 
	{
		this.contraseña = contraseña;
	}
	
	
}
