package gestores;

import java.sql.SQLException;

import dao.Encuentro_DAO;
import dao.Encuentro_DAO_PostgreSQL;
import dominio.Encuentro;
import dominio.Forma_De_Puntuacion;
import dominio.Lugar_De_Realizacion;
import dominio.Participante;
import excepciones.BaseDeDatosException;

public class Gestor_Encuentro 
{
	private Encuentro_DAO encuentroDAO;
	
	public Gestor_Encuentro()
	{
		super();
		this.encuentroDAO = new Encuentro_DAO_PostgreSQL();
	}
	
	public Encuentro crearEncuentro(Participante participante1, Participante participante2, Lugar_De_Realizacion lugar) throws SQLException, BaseDeDatosException
	{
		Encuentro e = new Encuentro();
		e.setParticipante1(participante1);
		e.setParticipante2(participante2);
		e.setLugarDeRealizacion(lugar);
		return e;
	}
}
