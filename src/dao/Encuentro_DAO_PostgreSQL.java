package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dominio.Competencia;
import dominio.Encuentro;
import dominio.Competencia.Estado_Competencia;
import excepciones.BaseDeDatosException;
import gestores.Gestor_Conexion;

public class Encuentro_DAO_PostgreSQL implements Encuentro_DAO
{
	private Connection conn = Gestor_Conexion.getConnection();
	
	private static final String INSERT_ENCUENTRO =
			"INSERT INTO dds.encuentro (FECHA_ENCUENTRO, ID_PARTICIPANTE1, ID_PARTICIPANTE2, LUGAR_DE_REALIZACION) VALUES (?,?,?,?) RETURNING ID";
	
	private static final String BUSCAR_POR_ID_FECHA =
			"SELECT ID, ID_PARTICIPANTE1, ID_PARTICIPANTE2, LUGAR_DE_REALIZACION FROM dds.encuentro where FECHA_ENCUENTRO = ?";
	
	private static final String UPDATE_ENCUENTRO =
			"INSERT INTO dds.encuentro (FECHA_ENCUENTRO, ID_PARTICIPANTE1, ID_PARTICIPANTE2, LUGAR_DE_REALIZACION) VALUES (?,?,?,?) RETURNING ID";
	
	@Override
	public Encuentro saveOrUpdate(Encuentro encuentro, Integer idFecha, Connection conexion) throws SQLException, BaseDeDatosException
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int idEncuentro = 0;
		try 
		{
					if(encuentro.getId() != null && encuentro.getId() > 0)
					{
						//SIN IMPLEMENTAR
					}
					else
					{
						pstmt = conexion.prepareStatement(INSERT_ENCUENTRO);
						pstmt.setInt(1, idFecha);
						pstmt.setInt(2, encuentro.getParticipante1().getId());
						pstmt.setInt(3, encuentro.getParticipante2().getId());
						pstmt.setInt(4, encuentro.getLugarDeRealizacion().getId());
						rs = pstmt.executeQuery();
						while(rs.next()) 
						{
							idEncuentro = rs.getInt("ID");
						}
						encuentro.setId(idEncuentro);
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
		return encuentro;
	}
	
	@Override
	public List<Encuentro> encuentrosPorIdFecha(Integer id_fecha)
	{
		List<Encuentro> lista_de_encuentros = new ArrayList<Encuentro>();
		Lugar_De_Realizacion_DAO lugarDAO = new Lugar_De_Realizacion_DAO_PostgreSQL();
		Participante_DAO participanteDAO = new Participante_DAO_PostgreSQL();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try 
		{
			pstmt= conn.prepareStatement(BUSCAR_POR_ID_FECHA);
			pstmt.setInt(1, id_fecha);
			rs = pstmt.executeQuery();
			while(rs.next()) 
			{
				Encuentro e = new Encuentro();
				e.setId(rs.getInt("ID"));
				e.setParticipante1(participanteDAO.buscarPorId(rs.getInt("ID_PARTICIPANTE1")));
				e.setParticipante2(participanteDAO.buscarPorId(rs.getInt("ID_PARTICIPANTE2")));
				e.setLugarDeRealizacion(lugarDAO.buscarPorId(rs.getInt("LUGAR_DE_REALIZACION")));
				lista_de_encuentros.add(e);
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
		return lista_de_encuentros;
	}
}
