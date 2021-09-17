package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dominio.Deporte;
import dominio.Lugar_De_Realizacion;
import gestores.Gestor_Conexion;

public class Lugar_De_Realizacion_DAO_PostgreSQL implements Lugar_De_Realizacion_DAO
{
	private Connection conn = Gestor_Conexion.getConnection();
	
	private static final String SELECT_POR_NOMBRE =
			"SELECT * FROM dds.lugar_de_realizacion where nombre = ?";
	
	private static final String SELECT_POR_ID =
			"SELECT * FROM dds.lugar_de_realizacion where id = ?";
	
	@Override
	public List<Lugar_De_Realizacion> buscarLugaresPorDeporte(String deporte) 
	{
		List<Lugar_De_Realizacion> lista = new ArrayList<Lugar_De_Realizacion>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try 
		{
			pstmt= conn.prepareStatement(
					
					"SELECT D.ID AS ID_DEPORTE, D.NOMBRE AS NOMBRE_DEPORTE, LR.ID AS ID_LUGAR, LR.ID_USUARIO AS ID_USUARIO_LUGAR, "
					+ "LR.NOMBRE AS NOMBRE_LUGAR, LR.DESCRIPCION AS DESCRIPCION "
					+ "FROM dds.asociado_con A, dds.deporte D , dds.lugar_de_realizacion LR "
					+ "WHERE A.ID_DEPORTE = D.ID "
					+ "AND A.ID_LUGAR_DE_REALIZACION = LR.ID "
					+ "AND D.NOMBRE = "+"'"+deporte+"'"
					
					);
			rs = pstmt.executeQuery();
			while(rs.next()) 
			{
				Lugar_De_Realizacion l = new Lugar_De_Realizacion();
				l.setId((rs.getInt("ID_LUGAR")));
				l.setNombre(rs.getString("NOMBRE_LUGAR"));
				l.setDeporte(new Deporte(rs.getInt("ID_DEPORTE"), rs.getString("NOMBRE_DEPORTE")));
				l.setDescripcion(rs.getString("DESCRIPCION"));
				lista.add(l);
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
	public List<Lugar_De_Realizacion> buscarTodos() 
	{
		List<Lugar_De_Realizacion> lista = new ArrayList<Lugar_De_Realizacion>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try 
		{
			pstmt= conn.prepareStatement(
					
					"SELECT * FROM dds.lugar_de_realizacion"
					
					);
			rs = pstmt.executeQuery();
			while(rs.next()) 
			{
				Lugar_De_Realizacion l = new Lugar_De_Realizacion();
				l.setId((rs.getInt("ID")));
				l.setNombre(rs.getString("NOMBRE"));
				l.setDescripcion(rs.getString("DESCRIPCION"));
				lista.add(l);
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
	public Lugar_De_Realizacion buscarPorNombre(String nombreLugar) 
		{
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			Lugar_De_Realizacion l = new Lugar_De_Realizacion();
			Usuario_DAO u = new Usuario_DAO_PostgreSQL();
			try 
			{
				pstmt = conn.prepareStatement(SELECT_POR_NOMBRE);
				pstmt.setString(1, nombreLugar);
				rs = pstmt.executeQuery();
				while(rs.next()) 
				{
					l.setId((rs.getInt("ID")));
					l.setNombre(rs.getString("NOMBRE"));
					l.setDescripcion(rs.getString("DESCRIPCION"));
					l.setUsuario(u.buscarPorCuil(rs.getInt("ID_USUARIO")));
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
			return l;
	}

	@Override
	public Lugar_De_Realizacion buscarPorId(Integer idLugar) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Lugar_De_Realizacion l = new Lugar_De_Realizacion();
		Usuario_DAO u = new Usuario_DAO_PostgreSQL();
		try 
		{
			pstmt = conn.prepareStatement(SELECT_POR_ID);
			pstmt.setInt(1, idLugar);
			rs = pstmt.executeQuery();
			while(rs.next()) 
			{
				l.setId((rs.getInt("ID")));
				l.setNombre(rs.getString("NOMBRE"));
				l.setDescripcion(rs.getString("DESCRIPCION"));
				l.setUsuario(u.buscarPorCuil(rs.getInt("ID_USUARIO")));
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
		return l;
	}
	
}

