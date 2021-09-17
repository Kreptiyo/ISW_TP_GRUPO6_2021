package gestores;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import base_de_datos.DB;

public class Gestor_Conexion 
{
	private static Connection conn = null;
	
	private Gestor_Conexion()
	{
		 String url = "jdbc:postgresql://localhost:5432/dds?useSSL=false";
		 String driver = "org.postgresql.Driver";
		 String usuario = "postgres";
		 String password = "dreamon44";
		 try
		 {
			 Class.forName(driver);
		     conn = DriverManager.getConnection(url, usuario, password);
		 }
		 catch(ClassNotFoundException | SQLException e)
		 {
		     e.printStackTrace();
		 }
	}
	
	public static Connection getConnection()
	{
		if(conn == null)
		{
			new Gestor_Conexion();
			DB.verificarCrearTablas(conn);
		}
		
		return conn;
	}
}
