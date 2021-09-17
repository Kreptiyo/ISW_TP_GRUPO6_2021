package base_de_datos;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DB 
{
	private static boolean _TABLAS_CREADAS = false;
	
	private static final String TABLA_CREATE_PAIS = 
			"CREATE TABLE IF NOT EXISTS dds.pais ( "+
			"		  ID SERIAL, "+
			"		  NOMBRE VARCHAR(14) UNIQUE, "+
			"		  PRIMARY KEY (ID)) ";
	
	private static final String TABLA_CREATE_PROVINCIA = 
			"CREATE TABLE IF NOT EXISTS dds.provincia( "+
			"		  ID SERIAL, "+
			"		  ID_PAIS INTEGER, "+
			"         NOMBRE VARCHAR(20) UNIQUE, "+
			"         PRIMARY KEY(ID), "+
			"	      FOREIGN KEY (ID_PAIS) REFERENCES dds.pais(ID)) ";
	
	private static final String TABLA_CREATE_LOCALIDAD = 
			"CREATE TABLE IF NOT EXISTS dds.localidad ( "+
			"		  ID SERIAL, "+
			"		  ID_PROVINCIA INTEGER, "+
			"		  NOMBRE VARCHAR(20) UNIQUE, "+
			"		  CODIGO_POSTAL INTEGER UNIQUE, "+
			"		  PRIMARY KEY (ID), "+
			"		  FOREIGN KEY (ID_PROVINCIA) REFERENCES dds.provincia(ID)) ";

	private static final String TABLA_CREATE_USUARIO = 
			"CREATE TABLE IF NOT EXISTS dds.usuario ( "+
			"		  CUIL INTEGER, "+
			"         ID_LOCALIDAD INTEGER, "+		
			"		  DNI INTEGER, "+
			"		  TIPO_DOCUMENTO VARCHAR(14) NULL, "+
			"		  NOMBRE VARCHAR(20) NULL, "+
			"		  APELLIDO VARCHAR(20) NULL, "+
            "         EMAIL VARCHAR(255) UNIQUE, "+
            "         CONTRASENIA VARCHAR(20) NULL, "+
			"		  PRIMARY KEY (CUIL), "+
            "         FOREIGN KEY (ID_LOCALIDAD) REFERENCES dds.localidad(ID)) ";
	
	private static final String TABLA_CREATE_HISTORIAL_ENTRADAS = 
			"CREATE TABLE IF NOT EXISTS dds.historial_entradas ( "+
			"		  CUIL_USUARIO INTEGER, "+
			"		  FECHA DATE, "+
			"		  HORA TIME, "+
			"		  PRIMARY KEY (CUIL_USUARIO), "+
            "         FOREIGN KEY (CUIL_USUARIO) REFERENCES dds.usuario(CUIL)) ";
	
	private static final String TABLA_CREATE_FORMA_DE_PUNTUACION = 
			"CREATE TABLE IF NOT EXISTS dds.forma_de_puntuacion ( "+
			"		  ID SERIAL, "+
			"		  PRIMARY KEY (ID)) ";
	
	private static final String TABLA_CREATE_PUNTUACION_POR_RESULTADO_FINAL = 
			"CREATE TABLE IF NOT EXISTS dds.puntuacion_por_resultado_final ( "+
			"		  ID INTEGER, "+
			"		  PRIMARY KEY (ID), "+
            "         FOREIGN KEY (ID) REFERENCES dds.forma_de_puntuacion(ID)) ";

	private static final String TABLA_CREATE_PUNTUACION_POR_SETS = 
			"CREATE TABLE IF NOT EXISTS dds.puntuacion_por_sets ( "+
			"		  ID INTEGER, "+
            "         CANT_MAX_SETS INTEGER NULL, "+
			"		  PRIMARY KEY (ID), "+
            "         FOREIGN KEY (ID) REFERENCES dds.forma_de_puntuacion(ID)) ";

	private static final String TABLA_CREATE_PUNTUACION_POR_PUNTOS = 
			"CREATE TABLE IF NOT EXISTS dds.puntuacion_por_puntos ( "+
			"		  ID INTEGER, "+
            "         CANT_TANTOS_OTORGAR_POR_AUSENCIA INTEGER NULL, "+
			"		  PRIMARY KEY (ID), "+
            "         FOREIGN KEY (ID) REFERENCES dds.forma_de_puntuacion(ID)) ";

	private static final String TABLA_CREATE_DEPORTE = 
			"CREATE TABLE IF NOT EXISTS dds.deporte ( "+
			"		  ID SERIAL, "+
            "         NOMBRE VARCHAR(20) UNIQUE, "+
			"		  PRIMARY KEY (ID)) ";

	private static final String TABLA_CREATE_COMPETENCIA = 
			"CREATE TABLE IF NOT EXISTS dds.competencia ( "+
			"		  ID SERIAL, "+
            "         ID_DEPORTE INTEGER, "+
 			"         ID_FORMA_DE_PUNTUACION INTEGER, "+
            "         ID_MODALIDAD INTEGER, "+
			"         USUARIO INTEGER, "+
            "         NOMBRE VARCHAR(20) UNIQUE, "+
			"         ESTADO VARCHAR(14) NULL, "+
			"         REGLAMENTO VARCHAR(200) NULL, "+
			"         FECHA_BAJA_LOGICA DATE, "+
			"         HORA_BAJA_LOGICA TIME, "+
			"		  PRIMARY KEY (ID), "+
            "         FOREIGN KEY (USUARIO) REFERENCES dds.usuario(CUIL), "+
			"         FOREIGN KEY (ID_DEPORTE) REFERENCES dds.deporte(ID), "+
			"         FOREIGN KEY (ID_MODALIDAD) REFERENCES dds.modalidad(ID), "+
            "         FOREIGN KEY (ID_FORMA_DE_PUNTUACION) REFERENCES dds.forma_de_puntuacion(ID)) ";

	private static final String TABLA_CREATE_MODALIDAD = 
			"CREATE TABLE IF NOT EXISTS dds.modalidad ( "+
			"		  ID SERIAL, "+
			"		  PRIMARY KEY (ID)) ";

	private static final String TABLA_CREATE_LIGA = 
			"CREATE TABLE IF NOT EXISTS dds.liga ( "+
			"		  ID INTEGER, "+
            "         PUNTOS_POR_PARTIDO_GANADO INTEGER, "+
            "         PUNTOS_POR_PARTIDO_EMPATADO INTEGER, "+
            "         PUNTOS_POR_PRESENTARSE INTEGER, "+
			"		  PRIMARY KEY (ID), "+
            "         FOREIGN KEY (ID) REFERENCES dds.modalidad(ID)) ";

	private static final String TABLA_CREATE_ELIMINACION_SIMPLE = 
			"CREATE TABLE IF NOT EXISTS dds.eliminacion_simple ( "+
			"		  ID INTEGER, "+
			"		  PRIMARY KEY (ID), "+
            "         FOREIGN KEY (ID) REFERENCES dds.modalidad(ID)) ";

	private static final String TABLA_CREATE_ELIMINACION_DOBLE = 
			"CREATE TABLE IF NOT EXISTS dds.eliminacion_doble ( "+
			"		  ID INTEGER, "+
			"		  PRIMARY KEY (ID), "+
            "         FOREIGN KEY (ID) REFERENCES dds.modalidad(ID)) ";

	private static final String TABLA_CREATE_LUGAR_DE_REALIZACION = 
			"CREATE TABLE IF NOT EXISTS dds.lugar_de_realizacion ( "+
			"		  ID SERIAL, "+
			"		  ID_USUARIO INTEGER, "+
			"		  NOMBRE VARCHAR(20) NULL, "+
			"		  DESCRIPCION VARCHAR(20) NULL, "+
			"		  PRIMARY KEY (ID), "+
            "         FOREIGN KEY (ID_USUARIO) REFERENCES dds.usuario(CUIL)) ";

	private static final String TABLA_CREATE_ASOCIADO_CON = 
			"CREATE TABLE IF NOT EXISTS dds.asociado_con ( "+
			"		  ID_DEPORTE INTEGER, "+
			"		  ID_LUGAR_DE_REALIZACION INTEGER, "+
			"		  PRIMARY KEY (ID_DEPORTE, ID_LUGAR_DE_REALIZACION), "+
            "         FOREIGN KEY (ID_DEPORTE) REFERENCES dds.deporte(ID), "+
			"		  FOREIGN KEY (ID_LUGAR_DE_REALIZACION) REFERENCES dds.lugar_de_realizacion(ID)) ";

	private static final String TABLA_CREATE_POSEE = 
			"CREATE TABLE IF NOT EXISTS dds.posee ( "+
			"		  ID_COMPETENCIA INTEGER, "+
			"		  ID_LUGAR_DE_REALIZACION INTEGER, "+
			"		  DISPONIBILIDAD INTEGER, "+
			"		  PRIMARY KEY (ID_COMPETENCIA, ID_LUGAR_DE_REALIZACION), "+
            "         FOREIGN KEY (ID_COMPETENCIA) REFERENCES dds.competencia(ID), "+
			"		  FOREIGN KEY (ID_LUGAR_DE_REALIZACION) REFERENCES dds.lugar_de_realizacion(ID)) ";

	private static final String TABLA_CREATE_FIXTURE = 
			"CREATE TABLE IF NOT EXISTS dds.fixture ( "+
			"		  ID SERIAL, "+
			"		  ID_COMPETENCIA INTEGER, "+
			"		  FECHA_GENERACION_FIXTURE DATE, "+
			"		  HORA_GENERACION_FIXTURE TIME, "+
			"		  PRIMARY KEY (ID), "+
            "         FOREIGN KEY (ID_COMPETENCIA) REFERENCES dds.competencia(ID)) ";

	private static final String TABLA_CREATE_FECHA = 
			"CREATE TABLE IF NOT EXISTS dds.fecha ( "+
			"		  ID SERIAL, "+
			"		  ID_FIXTURE INTEGER, "+
			"		  NUMERO INTEGER, "+
			"		  PRIMARY KEY (ID), "+
            "         FOREIGN KEY (ID_FIXTURE) REFERENCES dds.fixture(ID) ON DELETE CASCADE) ";

	private static final String TABLA_CREATE_PARTICIPANTE = 
			"CREATE TABLE IF NOT EXISTS dds.participante ( "+
			"		  ID SERIAL, "+
			"		  ID_COMPETENCIA INTEGER, "+
			"		  NOMBRE VARCHAR(30), "+
			"		  EMAIL VARCHAR(255), "+
			"		  PRIMARY KEY (ID), "+
            "         FOREIGN KEY (ID_COMPETENCIA) REFERENCES dds.competencia(ID)) ";

	private static final String TABLA_CREATE_TABLA_DE_POSICIONES = 
			"CREATE TABLE IF NOT EXISTS dds.tabla_de_posiciones ( "+
			"		  ID_PARTICIPANTE INTEGER, "+
			"		  ID_COMPETENCIA INTEGER, "+
			"		  ID SERIAL, "+
			"		  PUNTOS_TOTALES INTEGER, "+
			"		  PARTIDOS_GANADOS INTEGER, "+
			"		  PARTIDOS_EMPATADOS INTEGER, "+
			"		  PARTIDOS_PERDIDOS INTEGER, "+
			"		  TANTOS_A_FAVOR INTEGER, "+
			"		  TANTOS_EN_CONTRA INTEGER, "+
			"		  DIFERENCIA_DE_TANTOS INTEGER, "+
			"		  PRIMARY KEY (ID, ID_PARTICIPANTE, ID_COMPETENCIA), "+
            "         FOREIGN KEY (ID_PARTICIPANTE) REFERENCES dds.participante(ID), "+
            "         FOREIGN KEY (ID_COMPETENCIA) REFERENCES dds.competencia(ID)) ";

	private static final String TABLA_CREATE_RESULTADO = 
			"CREATE TABLE IF NOT EXISTS dds.resultado ( "+
			"		  ID SERIAL, "+
			"		  PRIMARY KEY (ID)) ";

	private static final String TABLA_CREATE_RESULTADO_POR_PUNTOS = 
			"CREATE TABLE IF NOT EXISTS dds.resultado_por_puntos ( "+
			"		  ID INTEGER, "+
			"		  TANTOS_PARTICIPANTE_1 INTEGER, "+
			"		  TANTOS_PARTICIPANTE_2 INTEGER, "+
			"		  PRIMARY KEY (ID), "+
            "         FOREIGN KEY (ID) REFERENCES dds.resultado(ID)) ";

	private static final String TABLA_CREATE_RESULTADO_POR_SETS = 
			"CREATE TABLE IF NOT EXISTS dds.resultado_por_sets ( "+
			"		  ID INTEGER, "+
			"		  PRIMARY KEY (ID), "+
            "         FOREIGN KEY (ID) REFERENCES dds.resultado(ID)) ";

	private static final String TABLA_CREATE_SET = 
			"CREATE TABLE IF NOT EXISTS dds.set ( "+
			"		  ID INTEGER, "+
			"		  GANADOR INTEGER NULL, "+
			"		  PUNTOS_PARTICIPANTE_1 INTEGER, "+
			"		  PUNTOS_PARTICIPANTE_2 INTEGER, "+
            "         FOREIGN KEY (ID) REFERENCES dds.resultado(ID), "+
            "         FOREIGN KEY (GANADOR) REFERENCES dds.participante(ID)) ";

	private static final String TABLA_CREATE_RESULTADO_FINAL = 
			"CREATE TABLE IF NOT EXISTS dds.resultado_final ( "+
			"		  ID INTEGER, "+
			"		  RESULTADO INTEGER, "+
			"		  PRIMARY KEY (ID), "+
            "         FOREIGN KEY (ID) REFERENCES dds.resultado(ID)) ";

	private static final String TABLA_CREATE_ENCUENTRO = 
			"CREATE TABLE IF NOT EXISTS dds.encuentro ( "+
			"		  ID SERIAL, "+
			"		  FECHA_ENCUENTRO INTEGER, "+
			"		  ID_SIG_ENCUENTRO_GANADOR INTEGER NULL, "+
			"		  ID_SIG_ENCUENTRO_PERDEDOR INTEGER NULL, "+
			"		  ID_PARTICIPANTE1 INTEGER NULL, "+
			"		  ID_PARTICIPANTE2 INTEGER NULL, "+
			"		  RESULTADO INTEGER, "+
			"		  LUGAR_DE_REALIZACION INTEGER, "+
			"		  GANADOR INTEGER NULL, "+
			"		  AUSENCIA_PARTICIPANTE_1 BOOLEAN, "+
			"		  AUSENCIA_PARTICIPANTE_2 BOOLEAN, "+
			"		  PRIMARY KEY (ID), "+
            "         FOREIGN KEY (FECHA_ENCUENTRO) REFERENCES dds.fecha(ID) ON DELETE CASCADE, "+
            "         FOREIGN KEY (ID_SIG_ENCUENTRO_GANADOR) REFERENCES dds.encuentro(ID), "+
            "         FOREIGN KEY (ID_SIG_ENCUENTRO_PERDEDOR) REFERENCES dds.encuentro(ID), "+
            "         FOREIGN KEY (ID_PARTICIPANTE1) REFERENCES dds.participante(ID), "+
            "         FOREIGN KEY (ID_PARTICIPANTE2) REFERENCES dds.participante(ID), "+
            "         FOREIGN KEY (RESULTADO) REFERENCES dds.resultado(ID), "+
            "         FOREIGN KEY (GANADOR) REFERENCES dds.participante(ID)) ";
			
	private static final String INSERT_PAIS =
			"INSERT INTO dds.pais (NOMBRE)" + 
			"VALUES ('Argentina')" +
			"ON CONFLICT (NOMBRE) DO NOTHING";
	
	private static final String INSERT_PROVINCIA =
			"INSERT INTO dds.provincia (ID_PAIS, NOMBRE)" + 
			"VALUES (1,'Santa Fe')" +
			"ON CONFLICT (NOMBRE) DO NOTHING";
	
	private static final String INSERT_LOCALIDAD =
			"INSERT INTO dds.localidad (ID_PROVINCIA, NOMBRE, CODIGO_POSTAL)" + 
			"VALUES (1,'Santa Fe',3000)" +
			"ON CONFLICT (NOMBRE) DO NOTHING";
	
	private static final String INSERT_USUARIO =
			"INSERT INTO dds.usuario (CUIL,ID_LOCALIDAD,DNI,TIPO_DOCUMENTO,NOMBRE,APELLIDO,EMAIL,CONTRASENIA)" + 
			"VALUES (1,1,12345678,'DNI','Tp','Disenio','disenioDeSistemas@gmail.com','tpdisenio')" +
			"ON CONFLICT (CUIL) DO NOTHING";
	
	private static final String INSERT_Deporte_1 =
			"INSERT INTO dds.deporte (NOMBRE)" + 
			"VALUES ('Fútbol')" +
			"ON CONFLICT (NOMBRE) DO NOTHING";
	
	private static final String INSERT_Deporte_2 =
			"INSERT INTO dds.deporte (NOMBRE)" + 
			"VALUES ('Tenis')" +
			"ON CONFLICT (NOMBRE) DO NOTHING";
	
	private static final String INSERT_Deporte_3 =
			"INSERT INTO dds.deporte (NOMBRE)" + 
			"VALUES ('Vóley')" +
			"ON CONFLICT (NOMBRE) DO NOTHING";
	
	private static final String INSERT_Deporte_4 =
			"INSERT INTO dds.deporte (NOMBRE)" + 
			"VALUES ('Básquet')" +
			"ON CONFLICT (NOMBRE) DO NOTHING";
	/*
	private static final String INSERT_Lugar_1 =
			"INSERT INTO dds.lugar_de_realizacion (ID_USUARIO, NOMBRE ,DESCRIPCION)" + 
			"VALUES (1,'Lugar 1', null)";
	
	private static final String INSERT_Lugar_2 =
			"INSERT INTO dds.lugar_de_realizacion (ID_USUARIO, NOMBRE,DESCRIPCION)" + 
			"VALUES (1,'Lugar 2', null)";
	
	private static final String INSERT_Lugar_3 =
			"INSERT INTO dds.lugar_de_realizacion (ID_USUARIO, NOMBRE,DESCRIPCION)" + 
			"VALUES (1,'Lugar 3', null)";
	
	private static final String INSERT_Lugar_4 =
			"INSERT INTO dds.lugar_de_realizacion (ID_USUARIO, NOMBRE,DESCRIPCION)" + 
			"VALUES (1,'Lugar 4', null)";
	*/
	private static final String INSERT_Asociado_con_1 =
			"INSERT INTO dds.asociado_con (ID_DEPORTE, ID_LUGAR_DE_REALIZACION)" + 
			"VALUES (1,1)" +
			"ON CONFLICT (ID_DEPORTE, ID_LUGAR_DE_REALIZACION) DO NOTHING";
	
	private static final String INSERT_Asociado_con_2 =
			"INSERT INTO dds.asociado_con (ID_DEPORTE, ID_LUGAR_DE_REALIZACION)" + 
			"VALUES (2,2)" +
			"ON CONFLICT (ID_DEPORTE, ID_LUGAR_DE_REALIZACION) DO NOTHING";
	
	private static final String INSERT_Asociado_con_3 =
			"INSERT INTO dds.asociado_con (ID_DEPORTE, ID_LUGAR_DE_REALIZACION)" + 
			"VALUES (3,3)" +
			"ON CONFLICT (ID_DEPORTE, ID_LUGAR_DE_REALIZACION) DO NOTHING";
	
	private static final String INSERT_Asociado_con_4 =
			"INSERT INTO dds.asociado_con (ID_DEPORTE, ID_LUGAR_DE_REALIZACION)" + 
			"VALUES (4,4)" +
			"ON CONFLICT (ID_DEPORTE, ID_LUGAR_DE_REALIZACION) DO NOTHING";
	
	
	public static void verificarCrearTablas(Connection conn) 
	{
		if(!_TABLAS_CREADAS) 
		{
			Statement stmt = null;
			try 
			{
				stmt = conn.createStatement();
				stmt.execute(TABLA_CREATE_PAIS);
				stmt.execute(TABLA_CREATE_PROVINCIA);
				stmt.execute(TABLA_CREATE_LOCALIDAD);
				stmt.execute(TABLA_CREATE_USUARIO);
				stmt.execute(TABLA_CREATE_HISTORIAL_ENTRADAS);
				stmt.execute(TABLA_CREATE_FORMA_DE_PUNTUACION);
				stmt.execute(TABLA_CREATE_PUNTUACION_POR_RESULTADO_FINAL);
				stmt.execute(TABLA_CREATE_PUNTUACION_POR_SETS);
				stmt.execute(TABLA_CREATE_PUNTUACION_POR_PUNTOS);
				stmt.execute(TABLA_CREATE_DEPORTE);
				stmt.execute(TABLA_CREATE_MODALIDAD);
				stmt.execute(TABLA_CREATE_LIGA);
				stmt.execute(TABLA_CREATE_ELIMINACION_SIMPLE);
				stmt.execute(TABLA_CREATE_ELIMINACION_DOBLE);
				stmt.execute(TABLA_CREATE_COMPETENCIA);
				stmt.execute(TABLA_CREATE_LUGAR_DE_REALIZACION);
				stmt.execute(TABLA_CREATE_ASOCIADO_CON);
				stmt.execute(TABLA_CREATE_POSEE);
				stmt.execute(TABLA_CREATE_FIXTURE);
				stmt.execute(TABLA_CREATE_FECHA);
				stmt.execute(TABLA_CREATE_PARTICIPANTE);
				stmt.execute(TABLA_CREATE_TABLA_DE_POSICIONES);
				stmt.execute(TABLA_CREATE_RESULTADO);
				stmt.execute(TABLA_CREATE_RESULTADO_POR_PUNTOS);
				stmt.execute(TABLA_CREATE_RESULTADO_POR_SETS);
				stmt.execute(TABLA_CREATE_SET);
				stmt.execute(TABLA_CREATE_RESULTADO_FINAL);
				stmt.execute(TABLA_CREATE_ENCUENTRO);
				stmt.execute(INSERT_PAIS);
				stmt.execute(INSERT_PROVINCIA);
				stmt.execute(INSERT_LOCALIDAD);
				stmt.execute(INSERT_USUARIO);
				stmt.execute(INSERT_Deporte_1);
				stmt.execute(INSERT_Deporte_2);
				stmt.execute(INSERT_Deporte_3);
				stmt.execute(INSERT_Deporte_4);
				/*
				stmt.execute(INSERT_Lugar_1);
				stmt.execute(INSERT_Lugar_2);
				stmt.execute(INSERT_Lugar_3);
				stmt.execute(INSERT_Lugar_4);
				*/
				stmt.execute(INSERT_Asociado_con_1);
				stmt.execute(INSERT_Asociado_con_2);
				stmt.execute(INSERT_Asociado_con_3);
				stmt.execute(INSERT_Asociado_con_4);
				_TABLAS_CREADAS = true;
			}
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
			finally 
			{
					try 
					{
						if(stmt!=null) stmt.close();
					} 
					catch (SQLException e) 
					{
						e.printStackTrace();
					}
			}
		}
	}
	
}
