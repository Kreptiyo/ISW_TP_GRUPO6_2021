package dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import dominio.Fecha;
import dominio.Fixture;
import excepciones.BaseDeDatosException;

public interface Fixture_DAO 
{
	public Fixture saveOrUpdate(Fixture f, Integer idCompetencia, Connection conn) throws SQLException, BaseDeDatosException;
	public Fixture buscarPorIdCompetencia(Integer idCompetencia);
	public void eliminarFixturePorIdCompetencia(Integer idCompetencia, Connection conn);
	List<Fecha> buscarFechasPorIdFixture(Integer id_fixture);
}
