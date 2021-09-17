package gestores;

import java.util.List;

import dao.Lugar_De_Realizacion_DAO;
import dao.Lugar_De_Realizacion_DAO_PostgreSQL;
import dominio.Lugar_De_Realizacion;

public class Gestor_Lugar_De_Realizacion 
{
	
	private Lugar_De_Realizacion_DAO LugarDAO = new Lugar_De_Realizacion_DAO_PostgreSQL();
	
	public List<Lugar_De_Realizacion> buscarLugaresPorDeporte(String deporte) 
	{
		return LugarDAO.buscarLugaresPorDeporte(deporte);
	}
	
	public List<Lugar_De_Realizacion> buscarTodos() 
	{
		return LugarDAO.buscarTodos();
	}
	
	public Lugar_De_Realizacion buscarPorNombre(String nombreLugar) 
	{
		return LugarDAO.buscarPorNombre(nombreLugar);
	}

	public Lugar_De_Realizacion buscarPorId(Integer idLugar) 
	{
		return LugarDAO.buscarPorId(idLugar);
	}
}
