package dominio;

public abstract class Modalidad {
	
	private Integer id;
	private Integer id_competencia;
	
	public Integer getId() 
	{
		return id;
	}
	
	public void setId(Integer id) 
	{
		this.id = id;
	}
	
	public Integer getId_competencia() 
	{
		return id_competencia;
	}
	
	public void setId_competencia(Integer id_competencia) 
	{
		this.id_competencia = id_competencia;
	}

}
