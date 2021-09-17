package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dominio.Deporte;
import gestores.Gestor_Conexion;

public class Deporte_DAO_PostgreSQL implements Deporte_DAO
{
	private Connection conn = Gestor_Conexion.getConnection();
	
	private static final String SELECT_ALL_DEPORTE =
			"SELECT * FROM dds.deporte order by id";
	
	private static final String SELECT_POR_NOMBRE =
			"SELECT * FROM dds.deporte where nombre = ?";
	
	private static final String SELECT_POR_ID =
			"SELECT * FROM dds.deporte where id = ?";
	
	@Override
	public List<Deporte> buscarTodos() 
	{
		List<Deporte> lista = new ArrayList<Deporte>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try 
		{
			pstmt= conn.prepareStatement(SELECT_ALL_DEPORTE);
			rs = pstmt.executeQuery();
			while(rs.next()) 
			{
				Deporte d = new Deporte();
				d.setId((rs.getInt("ID")));
				d.setNombre(rs.getString("NOMBRE"));
				lista.add(d);
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
	public Deporte buscarPorNombre(String nombre) 
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Deporte d = new Deporte();
		try 
		{
			pstmt = conn.prepareStatement(SELECT_POR_NOMBRE);
			pstmt.setString(1, nombre);
			rs = pstmt.executeQuery();
			while(rs.next()) 
			{
				d.setId((rs.getInt("ID")));
				d.setNombre(rs.getString("NOMBRE"));
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
		return d;
	}
	
	@Override
	public Deporte buscarPorId(int id) 
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Deporte d = new Deporte();
		try 
		{
			pstmt = conn.prepareStatement(SELECT_POR_ID);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();
			while(rs.next()) 
			{
				d.setId((rs.getInt("ID")));
				d.setNombre(rs.getString("NOMBRE"));
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
		return d;
	}

}
