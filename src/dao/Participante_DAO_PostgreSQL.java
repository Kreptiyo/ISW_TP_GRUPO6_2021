package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import dominio.Participante;
import excepciones.BaseDeDatosException;
import gestores.Gestor_Conexion;


public class Participante_DAO_PostgreSQL implements Participante_DAO
{
	
	private Connection conn = Gestor_Conexion.getConnection();
	
	private static final String EXISTE_NOMBRE_PARTICIPANTE =
			"SELECT "+
		     "CASE WHEN EXISTS "+
		     "( "+
		     "SELECT NOMBRE FROM dds.participante WHERE nombre = ?"
		     + " AND id_competencia = ?"+
		     ") "+
		     "THEN TRUE "+
		     "ELSE FALSE "+
		     "END";
	
	private static final String EXISTE_EMAIL_PARTICIPANTE =
			"SELECT "+
		     "CASE WHEN EXISTS "+
		     "( "+
		     "SELECT NOMBRE FROM dds.participante WHERE email = ?"
		     + " AND id_competencia = ?"+
		     ") "+
		     "THEN TRUE "+
		     "ELSE FALSE "+
		     "END";
	
	private static final String INSERT_PARTICIPANTE =
			"INSERT INTO dds.participante (ID_COMPETENCIA, NOMBRE, EMAIL) VALUES (?,?,?)";
	
	private static final String BUSCAR_POR_ID =
			"SELECT * FROM dds.participante where ID = ?";
	
	public void saveOrUpdate(Participante p, Integer idCompetencia) throws BaseDeDatosException, SQLException 
	{
		PreparedStatement pstmt = null;
		try {
				conn.setAutoCommit(false);
				pstmt= conn.prepareStatement(INSERT_PARTICIPANTE);
				pstmt.setInt(1, idCompetencia);
				pstmt.setString(2, p.getNombre().toString());
				pstmt.setString(3, p.getEmail().toString());
				pstmt.executeUpdate();
				conn.commit();
				
		} 
		catch (SQLException e) 
		{
			conn.rollback();
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

	@Override
	public LinkedList<Participante> buscarPorIdCompetencia(Integer id) {
		
		LinkedList<Participante> lista = new LinkedList<Participante>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try 
		{
			pstmt= conn.prepareStatement("SELECT * FROM dds.participante WHERE id_competencia = "+id);
			rs = pstmt.executeQuery();
			while(rs.next()) 
			{
				Participante p = new Participante();
				p.setId(rs.getInt("ID"));
				p.setNombre(rs.getString("NOMBRE"));
				p.setEmail(rs.getString("EMAIL"));
				lista.add(p);
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
				//if(conn!=null) conn.close();				
			}
			catch(SQLException e) 
			{
				e.printStackTrace();
			}
		}	
		return lista;
	}

	@Override
	public Participante buscarPorId(Integer id) 
	{
		Participante p = new Participante();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try 
		{
			pstmt = conn.prepareStatement(BUSCAR_POR_ID);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			while(rs.next()) 
			{
				p.setId(rs.getInt("ID"));
				p.setNombre(rs.getString("NOMBRE"));
				p.setEmail(rs.getString("EMAIL"));
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
				//if(conn!=null) conn.close();				
			}
			catch(SQLException e) 
			{
				e.printStackTrace();
			}
		}	
		return p;
	}
	
	@Override
	public boolean existeNombreParticipante(String nombre, Integer idCompetencia) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean resultado = false;
		try 
		{
			pstmt = conn.prepareStatement(EXISTE_NOMBRE_PARTICIPANTE);
			pstmt.setString(1, nombre);
			pstmt.setInt(2, idCompetencia);
			rs = pstmt.executeQuery();
			while(rs.next()) 
			{
				resultado = rs.getBoolean("CASE");
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
		return resultado;
	}
	
	@Override
	public boolean existeEmailParticipante(String email, Integer idCompetencia) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean resultado = false;
		try 
		{
			pstmt = conn.prepareStatement(EXISTE_EMAIL_PARTICIPANTE);
			pstmt.setString(1, email);
			pstmt.setInt(2, idCompetencia);
			rs = pstmt.executeQuery();
			while(rs.next()) 
			{
				resultado = rs.getBoolean("CASE");
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
		return resultado;
	}

}
