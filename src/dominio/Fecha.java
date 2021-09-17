package dominio;

import java.util.ArrayList;
import java.util.List;

public class Fecha 
{
	private Integer id;
	private List<Encuentro> listaDeEncuentros;
	private Integer numero;
	
	public Fecha()
	{
		super();
		this.id = -1;
		this.listaDeEncuentros = new ArrayList<Encuentro>();
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public List<Encuentro> getListaDeEncuentros() {
		return listaDeEncuentros;
	}
	public void setListaDeEncuentros(List<Encuentro> listaDeEncuentros) {
		this.listaDeEncuentros = listaDeEncuentros;
	}
	public Integer getNumero() {
		return numero;
	}
	public void setNumero(Integer numero) {
		this.numero = numero;
	}
	
	
	
	
}
