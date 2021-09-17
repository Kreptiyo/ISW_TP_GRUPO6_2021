package dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import dominio.Encuentro;
import excepciones.BaseDeDatosException;

public interface Encuentro_DAO 
{
	public Encuentro saveOrUpdate(Encuentro e, Integer idFecha, Connection conn) throws SQLException, BaseDeDatosException;
	public List<Encuentro> encuentrosPorIdFecha(Integer id_fecha);
}
