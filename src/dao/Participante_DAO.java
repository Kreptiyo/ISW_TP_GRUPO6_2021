package dao;


import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import dominio.Participante;
import excepciones.BaseDeDatosException;

public interface Participante_DAO 
{

	public LinkedList<Participante> buscarPorIdCompetencia(Integer id);
	public void saveOrUpdate(Participante p,Integer idCompetencia) throws BaseDeDatosException, SQLException;
	public boolean existeNombreParticipante(String nombre, Integer idCompetencia);
	public boolean existeEmailParticipante(String email, Integer idCompetencia);
	Participante buscarPorId(Integer id);
	
}
