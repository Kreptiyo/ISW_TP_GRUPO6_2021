package excepciones;

import java.util.ArrayList;
import java.util.List;

public class CamposIncorrectosException extends Exception{
	
	private String nombreCampo;
	private List<String> camposErroneos;
	
	public CamposIncorrectosException(String nc, List<String> camposErroneos) {
		super(" Los siguientes campos son incorrectos: " + "\n" 
				+nc +"\n"+ "Por favor, reingrese los mismos.");
		this.nombreCampo = nc;
		this.camposErroneos = camposErroneos;
	}

	public String getNombreCampo() {
		return nombreCampo;
	}

	public void setNombreCampo(String nombreCampo) {
		this.nombreCampo = nombreCampo;
	}

	public List<String> getCamposErroneos() {
		return camposErroneos;
	}

	public void setCamposErroneos(List<String> camposErroneos) {
		this.camposErroneos = camposErroneos;
	}

}
