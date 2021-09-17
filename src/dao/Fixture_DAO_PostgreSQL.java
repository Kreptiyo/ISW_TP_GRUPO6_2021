package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import dominio.Competencia;
import dominio.Encuentro;
import dominio.Fecha;
import dominio.Fixture;
import dominio.Participante;
import dominio.Competencia.Estado_Competencia;
import excepciones.BaseDeDatosException;
import gestores.Gestor_Conexion;

public class Fixture_DAO_PostgreSQL implements Fixture_DAO
{
	private Connection conn = Gestor_Conexion.getConnection();
	
	private static final String INSERT_FIXTURE =
			"INSERT INTO dds.fixture (ID_COMPETENCIA, FECHA_GENERACION_FIXTURE, HORA_GENERACION_FIXTURE) VALUES (?,?,?) RETURNING ID";
	
	private static final String INSERT_FECHA =
			"INSERT INTO dds.fecha (ID_FIXTURE, NUMERO) VALUES (?,?) RETURNING ID";
	
	private static final String BUSCAR_FIXTURE_POR_ID_COMPETENCIA =
			"SELECT * FROM dds.fixture where ID_COMPETENCIA = ?";
	
	private static final String BUSCAR_FECHA_POR_ID_FIXTURE =
			"SELECT * FROM dds.fecha where ID_FIXTURE = ?";
	
	private static final String ELIMINAR_FIXTURE_POR_ID_COMPETENCIA =
			"DELETE FROM dds.fixture where ID_COMPETENCIA = ?";
	
	
	@Override
	public Fixture saveOrUpdate(Fixture f, Integer idCompetencia, Connection conexion) throws SQLException, BaseDeDatosException
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int idFixture = 0;
		List<Fecha> listaDeFechasRondasGanadores = f.getListaDeFechasRondasGanadores();
		try 
		{
					pstmt = conexion.prepareStatement(INSERT_FIXTURE);
					pstmt.setInt(1, idCompetencia);
					if(f.getFecha_generacion_fixture() != null)
					{
						Date fecha_baja_logica = java.sql.Date.valueOf(f.getFecha_generacion_fixture());
						pstmt.setDate(2, fecha_baja_logica);
					}
					else
					{
						pstmt.setDate(2, null);
					}
					if(f.getHora_generacion_fixture() != null)
					{
						Time hora_baja_logica = java.sql.Time.valueOf(f.getHora_generacion_fixture());
						pstmt.setTime(3, hora_baja_logica);
					}
					else
					{
						pstmt.setTime(3, null);
					}
					rs = pstmt.executeQuery();
					while(rs.next()) 
					{
						idFixture = rs.getInt("ID");
					}
					f.setId(idFixture);
					for(Fecha unaFecha: listaDeFechasRondasGanadores)
					{
						this.insertarFecha(unaFecha, idFixture, conexion);
					}			
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
			throw new BaseDeDatosException(e.getMessage());
		}
		finally 
		{
			try 
			{
				if(pstmt!=null) pstmt.close();				
			}
			catch(SQLException e) 
			{
				e.printStackTrace();
			}
		}
		return f;
	}
	
	@Override
	public Fixture buscarPorIdCompetencia(Integer id_competencia)
	{
		Fixture f = new Fixture();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try 
		{
			pstmt = conn.prepareStatement(BUSCAR_FIXTURE_POR_ID_COMPETENCIA);
			pstmt.setInt(1, id_competencia);
			rs = pstmt.executeQuery();
			while(rs.next()) 
			{
				f.setId(rs.getInt("ID"));
				f.setFecha_generacion_fixture(rs.getDate("FECHA_GENERACION_FIXTURE").toLocalDate());
				f.setHora_generacion_fixture(rs.getTime("HORA_GENERACION_FIXTURE").toLocalTime());
				f.getListaDeFechasRondasGanadores().addAll(this.buscarFechasPorIdFixture(f.getId()));
			}			
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		finally 
		{
			try 
			{
				if(rs!=null) rs.close();
				if(pstmt!=null) pstmt.close();				
			}
			catch(SQLException e) 
			{
				e.printStackTrace();
			}
		}	
		return f;
	}
	
	@Override
	public List<Fecha> buscarFechasPorIdFixture(Integer id_fixture)
	{
		List<Fecha> lista_de_fechas = new ArrayList<Fecha>();
		Encuentro_DAO encuentroDAO = new Encuentro_DAO_PostgreSQL();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try 
		{
			pstmt = conn.prepareStatement(BUSCAR_FECHA_POR_ID_FIXTURE);
			pstmt.setInt(1, id_fixture);
			rs = pstmt.executeQuery();
			while(rs.next()) 
			{
				Fecha f = new Fecha();
				f.setId(rs.getInt("ID"));
				f.setNumero(rs.getInt("NUMERO"));
				f.getListaDeEncuentros().addAll(encuentroDAO.encuentrosPorIdFecha(f.getId()));
				lista_de_fechas.add(f);
			}			
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		finally 
		{
			try 
			{
				if(rs!=null) rs.close();
				if(pstmt!=null) pstmt.close();				
			}
			catch(SQLException e) 
			{
				e.printStackTrace();
			}
		}	
		return lista_de_fechas;
	}
	
	@Override
	public void eliminarFixturePorIdCompetencia(Integer idCompetencia, Connection conexion) 
	{
		PreparedStatement pstmt = null;
		try 
		{
				pstmt = conexion.prepareStatement(ELIMINAR_FIXTURE_POR_ID_COMPETENCIA);
				pstmt.setInt(1, idCompetencia);
				pstmt.executeUpdate();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		finally 
		{
			try 
			{
				if(pstmt!=null) pstmt.close();				
			}
			catch(SQLException e) 
			{
				e.printStackTrace();
			}
		}
	}
	
	public void insertarFecha(Fecha f, Integer idFixture, Connection conexion) throws SQLException, BaseDeDatosException
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Encuentro_DAO encuentroDAO = new Encuentro_DAO_PostgreSQL();
		int idFecha = 0;
		try 
		{
					pstmt = conexion.prepareStatement(INSERT_FECHA);
					pstmt.setInt(1, idFixture);
					pstmt.setInt(2, f.getNumero());
					rs = pstmt.executeQuery();
					while(rs.next()) 
					{
						idFecha = rs.getInt("ID");
					}
					f.setId(idFecha);
					for(Encuentro unEncuentro: f.getListaDeEncuentros())
					{
						encuentroDAO.saveOrUpdate(unEncuentro, idFecha, conexion);
					}		
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
			throw new BaseDeDatosException(e.getMessage());
		}
		finally 
		{
			try 
			{
				if(pstmt!=null) pstmt.close();				
			}
			catch(SQLException e) 
			{
				e.printStackTrace();
			}
		}
	}
	
}
