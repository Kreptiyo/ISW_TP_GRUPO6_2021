package dominio;

public class Disponibilidad {
	
	private Integer idCompetencia;
	private Lugar_De_Realizacion lugar_de_realizacion;
	private Integer cantidad;
	


	public Disponibilidad() {
		super();
	}


	public Disponibilidad(Lugar_De_Realizacion lugar_de_realizacion, Integer cantidad) {
		super();
		this.lugar_de_realizacion = lugar_de_realizacion;
		this.cantidad = cantidad;
	}

	public Disponibilidad(Integer idCompetencia, Lugar_De_Realizacion lugar_de_realizacion, Integer cantidad) {
		super();
		this.idCompetencia = idCompetencia;
		this.lugar_de_realizacion = lugar_de_realizacion;
		this.cantidad = cantidad;
	}


	public Integer getIdCompetencia() {
		return idCompetencia;
	}


	public void setIdCompetencia(Integer idCompetencia) {
		this.idCompetencia = idCompetencia;
	}


	public Lugar_De_Realizacion getLugar_de_realizacion() {
		return lugar_de_realizacion;
	}


	public void setLugar_de_realizacion(Lugar_De_Realizacion lugar_de_realizacion) {
		this.lugar_de_realizacion = lugar_de_realizacion;
	}


	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}	

}
