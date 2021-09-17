package excepciones;

public class BaseDeDatosException extends Exception{
	
	private String nombreCampo;
	
	public BaseDeDatosException(String nc) {
		super(" Se ha producido un error en la base de datos: " + "\n" 
				+nc);
		this.nombreCampo = nc;
	}

	public String getNombreCampo() {
		return nombreCampo;
	}

	public void setNombreCampo(String nombreCampo) {
		this.nombreCampo = nombreCampo;
	}

}
