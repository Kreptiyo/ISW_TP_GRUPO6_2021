package dao;

import java.util.List;

import dominio.Lugar_De_Realizacion;

public interface Lugar_De_Realizacion_DAO {
	
	public List<Lugar_De_Realizacion> buscarLugaresPorDeporte(String deporte);
	public List<Lugar_De_Realizacion> buscarTodos();
	public Lugar_De_Realizacion buscarPorNombre(String nombreLugar);
	public Lugar_De_Realizacion buscarPorId(Integer idLugar);
	
}
