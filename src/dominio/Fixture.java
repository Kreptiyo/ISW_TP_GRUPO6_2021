package dominio;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Fixture 
{
	private Integer id;
	private LocalDate fecha_generacion_fixture;
	private LocalTime hora_generacion_fixture;
	private List<Fecha> listaDeFechasRondasGanadores;
	private List<Fecha> listaDeFechasRondasPerdedores;
	
	public Fixture()
	{
		super();
		this.id = -1;
		this.fecha_generacion_fixture = LocalDate.now();
		this.hora_generacion_fixture = LocalTime.now();
		this.listaDeFechasRondasGanadores = new ArrayList<Fecha>();
		this.listaDeFechasRondasPerdedores = new ArrayList<Fecha>();
	}
	
	public List<Encuentro> getProximosEncuentros() {
		
		List <Encuentro> resultado = new ArrayList<Encuentro>();
			
			for(Fecha f: this.listaDeFechasRondasGanadores) {
				
				if(this.fechaEnCurso(f)==true) {
					for(Encuentro e: f.getListaDeEncuentros()) {
						if(e.getResultado()==null) {
							resultado.add(e);
						}
					}
				}
				
			}
		return resultado;
	}
	
	private boolean fechaEnCurso(Fecha f) {
		
		/*se verifica si aun faltan resultados en los encuentros de la fecha, si es asi, es pq la fecha se esta disputando*/
		
		boolean resultado = false;
		int cantResultados = 0;
		int cantEncuentros = f.getListaDeEncuentros().size();
		
		for(Encuentro e: f.getListaDeEncuentros()) {
			
			if(e.getResultado() != null) {
				cantResultados++;
			}
			
		}
		
		if((cantEncuentros != cantResultados) && (cantResultados>0)) {
			resultado = true;
		}
		
		return resultado;
		
	}
	
	public Integer getId() 
	{
		return id;
	}
	public void setId(Integer id) 
	{
		this.id = id;
	}
	public LocalDate getFecha_generacion_fixture() 
	{
		return fecha_generacion_fixture;
	}
	public void setFecha_generacion_fixture(LocalDate fecha_generacion_fixture) 
	{
		this.fecha_generacion_fixture = fecha_generacion_fixture;
	}
	public LocalTime getHora_generacion_fixture() 
	{
		return hora_generacion_fixture;
	}
	public void setHora_generacion_fixture(LocalTime hora_generacion_fixture) 
	{
		this.hora_generacion_fixture = hora_generacion_fixture;
	}
	public List<Fecha> getListaDeFechasRondasGanadores() 
	{
		return listaDeFechasRondasGanadores;
	}
	public void setListaDeFechasRondasGanadores(List<Fecha> listaDeFechasRondasGanadores) 
	{
		this.listaDeFechasRondasGanadores = listaDeFechasRondasGanadores;
	}
	public List<Fecha> getListaDeFechasRondasPerdedores() 
	{
		return listaDeFechasRondasPerdedores;
	}
	public void setListaDeFechasRondasPerdedores(List<Fecha> listaDeFechasRondasPerdedores) 
	{
		this.listaDeFechasRondasPerdedores = listaDeFechasRondasPerdedores;
	}
	
	
}
