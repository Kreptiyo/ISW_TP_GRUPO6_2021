package gestores;


import java.util.List;

import dao.Deporte_DAO;
import dao.Deporte_DAO_PostgreSQL;
import dominio.Deporte;

public class Gestor_Deporte 
{

	private Deporte_DAO deporteDAO = new Deporte_DAO_PostgreSQL();
	
	public List<Deporte> listarTodos() 
	{
		return deporteDAO.buscarTodos();
	}
	
	public Deporte deportePorNombre(String nombre)
	{
		return deporteDAO.buscarPorNombre(nombre);
	}
	
	public Deporte deportePorId(int id)
	{
		return deporteDAO.buscarPorId(id);
	}
	
}
