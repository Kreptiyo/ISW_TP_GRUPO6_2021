package dao;

import java.util.List;
import dominio.Deporte;

public interface Deporte_DAO 
{
	
	public List<Deporte> buscarTodos();
	
	public Deporte buscarPorNombre(String nombre);
	
	public Deporte buscarPorId(int id);
}
