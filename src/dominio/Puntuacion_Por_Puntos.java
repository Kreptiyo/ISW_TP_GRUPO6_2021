package dominio;

public class Puntuacion_Por_Puntos extends Forma_De_Puntuacion {
	
	private Integer cant_tantos_otorgar_por_ausencia;
	

	public Puntuacion_Por_Puntos(Integer cant_tantos_otorgar_por_ausencia) 
	{
		super();
		this.cant_tantos_otorgar_por_ausencia = cant_tantos_otorgar_por_ausencia;
	}

	public Integer getCant_tantos_otorgar_por_ausencia() 
	{
		return cant_tantos_otorgar_por_ausencia;
	}

	public void setCant_tantos_otorgar_por_ausencia(Integer cant_tantos_otorgar_por_ausencia) 
	{
		this.cant_tantos_otorgar_por_ausencia = cant_tantos_otorgar_por_ausencia;
	}

}
