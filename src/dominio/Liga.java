package dominio;

public class Liga extends Modalidad {
	
	private Integer puntos_por_partido_ganado;
	private Integer puntos_por_partido_empatado;
	private Integer puntos_por_presentarse;
	
	
	public Liga(Integer puntos_por_partido_ganado, Integer puntos_por_partido_empatado,
			Integer puntos_por_presentarse) 
	{
		super();
		this.puntos_por_partido_ganado = puntos_por_partido_ganado;
		this.puntos_por_partido_empatado = puntos_por_partido_empatado;
		this.puntos_por_presentarse = puntos_por_presentarse;
	}
	
	public Liga(Integer puntos_por_partido_ganado, Integer puntos_por_presentarse) 
	{
		super();
		this.puntos_por_partido_ganado = puntos_por_partido_ganado;
		this.puntos_por_presentarse = puntos_por_presentarse;
	}


	public Integer getPuntos_por_partido_ganado() 
	{
		return puntos_por_partido_ganado;
	}


	public void setPuntos_por_partido_ganado(Integer puntos_por_partido_ganado) 
	{
		this.puntos_por_partido_ganado = puntos_por_partido_ganado;
	}


	public Integer getPuntos_por_partido_empatado() 
	{
		return puntos_por_partido_empatado;
	}


	public void setPuntos_por_partido_empatado(Integer puntos_por_partido_empatado) 
	{
		this.puntos_por_partido_empatado = puntos_por_partido_empatado;
	}


	public int getPuntos_por_presentarse() 
	{
		return puntos_por_presentarse;
	}


	public void setPuntos_por_presentarse(int puntos_por_presentarse) 
	{
		this.puntos_por_presentarse = puntos_por_presentarse;
	}

}
