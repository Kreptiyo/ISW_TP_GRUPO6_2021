package gestores;

import dao.Usuario_DAO;
import dao.Usuario_DAO_PostgreSQL;
import dominio.Usuario;

public class Gestor_Usuario 
{
	private Usuario_DAO usuarioDAO = new Usuario_DAO_PostgreSQL();
	
	
	public Usuario buscarPorCuil(Integer cuil)
	{
		return usuarioDAO.buscarPorCuil(cuil);
	}
}
