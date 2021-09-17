package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import dominio.Usuario;
import dominio.Localidad;
import dominio.Pais;
import dominio.Provincia;
import gestores.Gestor_Conexion;

public class Usuario_DAO_PostgreSQL implements Usuario_DAO
{
	private Connection conn = Gestor_Conexion.getConnection();
	
	private static final String BUSCAR_USUARIO_POR_CUIL =
			"SELECT * FROM dds.usuario where cuil = ?";
	
	private static final String BUSCAR_LOCALIDAD_POR_ID =
			"SELECT * FROM dds.localidad where id = ?";
	
	private static final String BUSCAR_PROVINCIA_POR_ID =
			"SELECT * FROM dds.provincia where id = ?";
	
	private static final String BUSCAR_PAIS_POR_ID =
			"SELECT * FROM dds.pais where id = ?";
	
	public Usuario buscarPorCuil(Integer cuil)
	{
		Usuario u = new Usuario();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try 
		{
			pstmt = conn.prepareStatement(BUSCAR_USUARIO_POR_CUIL);
			pstmt.setInt(1, cuil);
			rs = pstmt.executeQuery();

			while(rs.next()) 
			{
				u.setCuil(cuil);
				u.setLocalidad(this.buscarLocalidadPorId(rs.getInt("ID_LOCALIDAD")));
				u.setDni(rs.getInt("DNI"));
				u.setTipo_documento(rs.getString("TIPO_DOCUMENTO"));
				u.setNombre(rs.getString("NOMBRE"));
				u.setApellido(rs.getString("APELLIDO"));
				u.setEmail(rs.getString("EMAIL"));
				u.setContraseña(rs.getString("CONTRASENIA"));
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
		
		return u;
	}
	
	public Localidad buscarLocalidadPorId(Integer id)
	{
		Localidad l = new Localidad();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try 
		{
			pstmt = conn.prepareStatement(BUSCAR_LOCALIDAD_POR_ID);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();

			while(rs.next()) 
			{
				l.setId(id);
				l.setProvincia(this.buscarProvinciaPorId(rs.getInt("ID_PROVINCIA")));
				l.setNombre(rs.getString("NOMBRE"));
				l.setCodigo_postal(rs.getInt("CODIGO_POSTAL"));
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
	
	public Provincia buscarProvinciaPorId(Integer id)
	{
		Provincia p = new Provincia();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try 
		{
			pstmt = conn.prepareStatement(BUSCAR_PROVINCIA_POR_ID);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();

			while(rs.next()) 
			{
				p.setId(id);
				p.setPais(this.buscarPaisPorId(rs.getInt("ID_PAIS")));
				p.setNombre(rs.getString("NOMBRE"));
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
		
		return p;
	}
	
	public Pais buscarPaisPorId(Integer id)
	{
		Pais p = new Pais();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try 
		{
			pstmt = conn.prepareStatement(BUSCAR_PAIS_POR_ID);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();

			while(rs.next()) 
			{
				p.setId(id);
				p.setNombre(rs.getString("NOMBRE"));
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
		
		return p;
	}
}
