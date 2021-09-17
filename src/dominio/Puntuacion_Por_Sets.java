package dominio;

public class Puntuacion_Por_Sets extends Forma_De_Puntuacion {

	private int cant_max_sets;

	public Puntuacion_Por_Sets(int cant_max_sets) 
	{
		super();
		this.cant_max_sets = cant_max_sets;
	}

	public int getCant_max_sets() 
	{
		return cant_max_sets;
	}

	public void setCant_max_sets(int cant_max_sets) 
	{
		this.cant_max_sets = cant_max_sets;
	}
	
}
