package dao;

import dominio.Competencia;
import dominio.Competencia.Estado_Competencia;
import dominio.Disponibilidad;
import dominio.Eliminacion_Doble;
import dominio.Eliminacion_Simple;
import dominio.Forma_De_Puntuacion;
import dominio.Liga;
import dominio.Lugar_De_Realizacion;
import dominio.Modalidad;
import dominio.Participante;
import dominio.Puntuacion_Por_Puntos;
import dominio.Puntuacion_Por_Resultado_Final;
import dominio.Puntuacion_Por_Sets;
import excepciones.BaseDeDatosException;
import gestores.Gestor_Conexion;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class Competencia_DAO_PostgreSQL implements Competencia_DAO
{
	private Connection conn = Gestor_Conexion.getConnection();
	
	private static final String SELECT_ALL_COMPETENCIA =
			"SELECT * FROM dds.competencia where usuario = ?";
	
	private static final String LISTAR_MIS_COMPETENCIAS =
			"SELECT ID, ID_DEPORTE, ID_MODALIDAD, NOMBRE, ESTADO FROM dds.competencia where USUARIO = ?";
	
	private static final String INSERT_POSEE =
			"INSERT INTO dds.posee (ID_COMPETENCIA, ID_LUGAR_DE_REALIZACION, DISPONIBILIDAD) VALUES (?,?,?)";
	
	private static final String INSERT_COMPETENCIA =
			"INSERT INTO dds.competencia (ID_DEPORTE, ID_MODALIDAD, ID_FORMA_DE_PUNTUACION, USUARIO, NOMBRE, ESTADO, REGLAMENTO, FECHA_BAJA_LOGICA, HORA_BAJA_LOGICA) VALUES (?,?,?,?,?,?,?,?,?) RETURNING ID";
	private static final String UPDATE_COMPETENCIA =
			"UPDATE dds.competencia SET ID_DEPORTE = ?, ID_MODALIDAD = ?, ID_FORMA_DE_PUNTUACION = ?, USUARIO = ?, NOMBRE = ?, ESTADO = ?, REGLAMENTO = ?, FECHA_BAJA_LOGICA = ?, HORA_BAJA_LOGICA = ? "+
			"WHERE ID = ?";
	private static final String INSERT_FORMA_DE_PUNTUACION =
			"INSERT INTO dds.forma_de_puntuacion DEFAULT VALUES RETURNING ID";
	
	private static final String INSERT_PUNTUACION_POR_RESULTADO_FINAL =
			"INSERT INTO dds.puntuacion_por_resultado_final (ID) values (?)";
	
	private static final String INSERT_PUNTUACION_POR_SETS =
			"INSERT INTO dds.puntuacion_por_sets (ID,CANT_MAX_SETS) values (?,?)";
	
	private static final String INSERT_PUNTUACION_POR_PUNTOS =
			"INSERT INTO dds.puntuacion_por_puntos (ID, CANT_TANTOS_OTORGAR_POR_AUSENCIA) values (?,?)";
			
	private static final String INSERT_MODALIDAD =
			"INSERT INTO dds.modalidad DEFAULT VALUES RETURNING ID";
	
	private static final String INSERT_MODALIDAD_LIGA =
			"INSERT INTO dds.liga (ID, PUNTOS_POR_PARTIDO_GANADO, PUNTOS_POR_PARTIDO_EMPATADO, PUNTOS_POR_PRESENTARSE) values (?,?,?,?)";
	
	private static final String INSERT_MODALIDAD_ELIMINACION_SIMPLE =
			"INSERT INTO dds.eliminacion_simple (ID) values (?)";
	
	private static final String INSERT_MODALIDAD_ELIMINACION_DOBLE =
			"INSERT INTO dds.eliminacion_doble (ID) values (?)";
	
	private static final String EXISTE_NOMBRE_COMPETENCIA =
			"SELECT "+
		     "CASE WHEN EXISTS "+
		     "( "+
		     "SELECT NOMBRE FROM dds.competencia WHERE nombre = ? "+
		     ") "+
		     "THEN TRUE "+
		     "ELSE FALSE "+
		     "END";
	
	private static final String SELECT_POSEE_POR_ID =
			"SELECT * FROM dds.posee WHERE id_competencia = ?";
	
	private static final String FORMA_DE_PUNTUACION_POR_ID = 
			"select fp.id as id_forma_de_puntuacion, fn.id as id_final, st.id as id_sets, pts.id as id_puntos, st.cant_max_sets, pts.cant_tantos_otorgar_por_ausencia "+
			"from dds.forma_de_puntuacion fp "+
			"left outer join dds.puntuacion_por_resultado_final fn on fp.id = fn.id "+
			"left outer join dds.puntuacion_por_sets st on fp.id = st.id "+
			"left outer join dds.puntuacion_por_puntos pts on fp.id = pts.id "+
			"where fp.id = ?";
	
	private static final String MODALIDAD_POR_ID =
			"select m.id as id_modalidad, l.id as id_liga, s.id as id_simple, d.id as id_doble, l.puntos_por_partido_ganado, l.puntos_por_partido_empatado, l.puntos_por_presentarse "+
			"from dds.modalidad m "+
			"left outer join dds.liga l on m.id = l.id "+
			"left outer join dds.eliminacion_simple s on m.id = s.id "+
			"left outer join dds.eliminacion_doble d on m.id = d.id "+
			"where m.id = ?";
	
	@Override
	public Competencia saveOrUpdate(Competencia c) throws BaseDeDatosException, SQLException
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Participante_DAO participanteDao = new Participante_DAO_PostgreSQL();
		Fixture_DAO fixtureDao = new Fixture_DAO_PostgreSQL();
		try 
		{
				if(c.getId() != null && c.getId() > 0)
				{
					conn.setAutoCommit(false);
					pstmt = conn.prepareStatement(UPDATE_COMPETENCIA);
					pstmt.setInt(1, c.getDeporte().getId());
					pstmt.setInt(2, c.getModalidad().getId());
					pstmt.setInt(3, c.getForma_de_puntuacion().getId());
					pstmt.setInt(4, c.getUsuario_creador().getCuil());
					pstmt.setString(5, c.getNombre());
					if(c.getFixture() == null)
					{
						fixtureDao.eliminarFixturePorIdCompetencia(c.getId(), conn);
					}
					else
					{
						if(c.getEstado().equals(Estado_Competencia.PLANIFICADA))
						{
							fixtureDao.eliminarFixturePorIdCompetencia(c.getId(), conn);
						}
						fixtureDao.saveOrUpdate(c.getFixture(), c.getId(), conn);
					}
					pstmt.setString(6, c.getEstado().toString());
					if(!c.getReglamento().equals("")) 
					{
						pstmt.setString(7, c.getReglamento());
					}
					else 
					{
						pstmt.setString(7, null);
					}
					if(c.getFecha_baja_logica() != null)
					{
						Date fecha_baja_logica = java.sql.Date.valueOf(c.getFecha_baja_logica());
						pstmt.setDate(8, fecha_baja_logica);
					}
					else
					{
						pstmt.setDate(8, null);
					}
					if(c.getHora_baja_logica() != null)
					{
						Time hora_baja_logica = java.sql.Time.valueOf(c.getHora_baja_logica());
						pstmt.setTime(9, hora_baja_logica);
					}
					else
					{
						pstmt.setTime(9, null);
					}
					
					for(Participante p: (c.getLista_de_participantes())) 
					{
						
						/*si no tiene id de participante es porque es uno nuevo, y tiene que hacer el save del mismo*/
						
						if(p.getId()==null) 
						{
							participanteDao.saveOrUpdate(p, c.getId());
						}
						
					}
					pstmt.setInt(10, c.getId());
					pstmt.executeUpdate();
					conn.commit();
				}
				else
				{
					conn.setAutoCommit(false);
					c.getModalidad().setId(this.crearModalidad(c.getModalidad()));
					c.getForma_de_puntuacion().setId(this.crearFormaDePuntuacion(c.getForma_de_puntuacion()));
					pstmt = conn.prepareStatement(INSERT_COMPETENCIA);
					pstmt.setInt(1, c.getDeporte().getId());
					pstmt.setInt(2, c.getModalidad().getId());
					pstmt.setInt(3, c.getForma_de_puntuacion().getId());
					pstmt.setInt(4, c.getUsuario_creador().getCuil());
					pstmt.setString(5, c.getNombre());
					pstmt.setString(6, c.getEstado().toString());
					if(!c.getReglamento().equals("")) 
					{
						pstmt.setString(7, c.getReglamento());
					}
					else 
					{
						pstmt.setString(7, null);
					}
					if(c.getFecha_baja_logica() != null)
					{
						Date fecha_baja_logica = java.sql.Date.valueOf(c.getFecha_baja_logica());
						pstmt.setDate(8, fecha_baja_logica);
					}
					else
					{
						pstmt.setDate(8, null);
					}
					if(c.getHora_baja_logica() != null)
					{
						Time hora_baja_logica = java.sql.Time.valueOf(c.getHora_baja_logica());
						pstmt.setTime(9, hora_baja_logica);
					}
					else
					{
						pstmt.setTime(9, null);
					}
					rs = pstmt.executeQuery();
					while(rs.next()) 
					{
						c.setId(rs.getInt("ID"));
					}
					this.crearDisponibilidades(c.getDisponibilidades(), c.getId());
					conn.commit();
				}					
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
		return c;
		
	}
	
	@Override
	public int crearFormaDePuntuacion(Forma_De_Puntuacion p) 
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int id = 0;
		try {
				pstmt = conn.prepareStatement(INSERT_FORMA_DE_PUNTUACION);
				rs = pstmt.executeQuery();
				while(rs.next()) 
				{
					id = rs.getInt("ID");
				}
				if(p.getClass().equals(Puntuacion_Por_Resultado_Final.class))
				{
					pstmt = conn.prepareStatement(INSERT_PUNTUACION_POR_RESULTADO_FINAL);
					pstmt.setInt(1, id);
					pstmt.executeUpdate();
				}
				if(p.getClass().equals(Puntuacion_Por_Sets.class))
				{
					pstmt = conn.prepareStatement(INSERT_PUNTUACION_POR_SETS);
					pstmt.setInt(1, id);
					pstmt.setInt(2, ((Puntuacion_Por_Sets) p).getCant_max_sets());
					pstmt.executeUpdate();
				}
				if(p.getClass().equals(Puntuacion_Por_Puntos.class))
				{
					pstmt = conn.prepareStatement(INSERT_PUNTUACION_POR_PUNTOS);
					pstmt.setInt(1, id);
					pstmt.setInt(2, ((Puntuacion_Por_Puntos) p).getCant_tantos_otorgar_por_ausencia());
					pstmt.executeUpdate();
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
				if(pstmt!=null) pstmt.close();				
			}
			catch(SQLException e) 
			{
				e.printStackTrace();
			}
		}
		return id;
	}
	
	@Override
	public int crearModalidad(Modalidad m) 
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int id = 0;
		try {
				pstmt = conn.prepareStatement(INSERT_MODALIDAD);
				rs = pstmt.executeQuery();
				while(rs.next()) 
				{
					id = rs.getInt("ID");
				}
				if(m.getClass().equals(Liga.class))
				{
					pstmt = conn.prepareStatement(INSERT_MODALIDAD_LIGA);
					pstmt.setInt(1, id);
					pstmt.setInt(2, ((Liga) m).getPuntos_por_partido_ganado());
					if(((Liga) m).getPuntos_por_partido_empatado() != 0)
					{
						pstmt.setInt(3, ((Liga) m).getPuntos_por_partido_empatado());
					}
					else
					{
						pstmt.setInt(3, 0);
					}
					pstmt.setInt(4, ((Liga) m).getPuntos_por_presentarse());
					pstmt.executeUpdate();
				}
				if(m.getClass().equals(Eliminacion_Simple.class))
				{
					pstmt = conn.prepareStatement(INSERT_MODALIDAD_ELIMINACION_SIMPLE);
					pstmt.setInt(1, id);
					pstmt.executeUpdate();
				}
				if(m.getClass().equals(Eliminacion_Doble.class))
				{
					pstmt = conn.prepareStatement(INSERT_MODALIDAD_ELIMINACION_DOBLE);
					pstmt.setInt(1, id);
					pstmt.executeUpdate();
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
		return id;
	}
	
	@Override
	public List<Competencia> buscarTodas(Integer cuil_usuario_logeado) 
	{
		List<Competencia> lista = new ArrayList<Competencia>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Deporte_DAO deporteDAO = new Deporte_DAO_PostgreSQL();
		Lugar_De_Realizacion_DAO lugarDAO = new Lugar_De_Realizacion_DAO_PostgreSQL();
		Participante_DAO participanteDAO = new Participante_DAO_PostgreSQL();
		Usuario_DAO usuarioDAO = new Usuario_DAO_PostgreSQL();
		Fixture_DAO fixtureDAO = new Fixture_DAO_PostgreSQL();
		
		try 
		{
			pstmt= conn.prepareStatement(SELECT_ALL_COMPETENCIA);
			pstmt.setInt(1, cuil_usuario_logeado);
			rs = pstmt.executeQuery();
			while(rs.next()) 
			{
				Competencia c = new Competencia();
				c.setId(rs.getInt("ID"));
				c.setDeporte(deporteDAO.buscarPorId(rs.getInt("ID_DEPORTE")));
				c.setForma_de_puntuacion(this.formaDePuntuacionPorId(rs.getInt("ID_FORMA_DE_PUNTUACION")));
				c.setModalidad(this.modalidadPorId(rs.getInt("ID_MODALIDAD")));
				c.setUsuario_creador(usuarioDAO.buscarPorCuil(rs.getInt("USUARIO")));
				c.setNombre(rs.getString("NOMBRE"));
				switch(rs.getString("ESTADO"))
				{
				case "CREADA":
					c.setEstado(Estado_Competencia.CREADA);
					break;
				case "PLANIFICADA":
					c.setEstado(Estado_Competencia.PLANIFICADA);
					break;
				case "EN_DISPUTA":
					c.setEstado(Estado_Competencia.EN_DISPUTA);
					break;
				case "FINALIZADA":
					c.setEstado(Estado_Competencia.FINALIZADA);
					break;
				}
				if(rs.getString("REGLAMENTO") == null)
				{
					c.setReglamento("");
				}
				else
				{
					c.setReglamento(rs.getString("REGLAMENTO"));
				}
				if(rs.getDate("FECHA_BAJA_LOGICA") != null)
				{
					c.setFecha_baja_logica(rs.getDate("FECHA_BAJA_LOGICA").toLocalDate());
				}
				if(rs.getTime("HORA_BAJA_LOGICA") != null)
				{
					c.setHora_baja_logica(rs.getTime("HORA_BAJA_LOGICA").toLocalTime());
				}
				c.setLugares_de_realizacion(lugarDAO.buscarLugaresPorDeporte(c.getDeporte().getNombre()));
				c.setDisponibilidades(this.listarDisponibilidadesPorIdCompetencia(c.getId()));
				c.setLista_de_participantes(participanteDAO.buscarPorIdCompetencia(c.getId()));
				c.setFixture(fixtureDAO.buscarPorIdCompetencia(c.getId()));
				if(c.getFixture().getId() == -1)
				{
					c.setFixture(null);
				}
				lista.add(c);
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
		return lista;
	}
	
	@Override
	public List<Competencia> listarMisCompetencias(Integer cuil_usuario_logeado) 
	{
		List<Competencia> lista = new ArrayList<Competencia>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Deporte_DAO deporteDAO = new Deporte_DAO_PostgreSQL();
		
		try 
		{
			pstmt= conn.prepareStatement(LISTAR_MIS_COMPETENCIAS);
			pstmt.setInt(1, cuil_usuario_logeado);
			rs = pstmt.executeQuery();
			while(rs.next()) 
			{
				Competencia c = new Competencia();
				c.setId(rs.getInt("ID"));
				c.setDeporte(deporteDAO.buscarPorId(rs.getInt("ID_DEPORTE")));
				c.setModalidad(this.modalidadPorId(rs.getInt("ID_MODALIDAD")));
				c.setNombre(rs.getString("NOMBRE"));
				switch(rs.getString("ESTADO"))
				{
				case "CREADA":
					c.setEstado(Estado_Competencia.CREADA);
					break;
				case "PLANIFICADA":
					c.setEstado(Estado_Competencia.PLANIFICADA);
					break;
				case "EN_DISPUTA":
					c.setEstado(Estado_Competencia.EN_DISPUTA);
					break;
				case "FINALIZADA":
					c.setEstado(Estado_Competencia.FINALIZADA);
					break;
				}
				lista.add(c);
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
		return lista;
	}
	
	@Override
	public void crearDisponibilidades(List<Disponibilidad> lista, int idCompetencia) {
		
		PreparedStatement pstmt = null;
		try 
		{
			
			for(Disponibilidad d: lista) {
				pstmt= conn.prepareStatement(INSERT_POSEE);
				pstmt.setInt(1, idCompetencia);
				pstmt.setInt(2, d.getLugar_de_realizacion().getId());
				pstmt.setInt(3, d.getCantidad());
			    pstmt.executeUpdate();
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
				if(pstmt!=null) pstmt.close();				
			}
			catch(SQLException e) 
			{
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public boolean existeNombreDeCompetencia(String nombre)
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		boolean resultado = false;
		try 
		{
			pstmt = conn.prepareStatement(EXISTE_NOMBRE_COMPETENCIA);
			pstmt.setString(1, nombre);
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
	public Forma_De_Puntuacion formaDePuntuacionPorId(int id) 
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Forma_De_Puntuacion formaDePuntuacion = null;
		try {
				pstmt = conn.prepareStatement(FORMA_DE_PUNTUACION_POR_ID);
				pstmt.setInt(1, id);
				rs = pstmt.executeQuery();
				while(rs.next()) 
				{
					if(rs.getInt("ID_FINAL") != 0)
					{
						formaDePuntuacion = new Puntuacion_Por_Resultado_Final();
						formaDePuntuacion.setId(rs.getInt("ID_FORMA_DE_PUNTUACION"));
					}
					if(rs.getInt("ID_SETS") != 0)
					{
						formaDePuntuacion = new Puntuacion_Por_Sets(rs.getInt("CANT_MAX_SETS"));
						formaDePuntuacion.setId(rs.getInt("ID_FORMA_DE_PUNTUACION"));
					}
					if(rs.getInt("ID_PUNTOS") != 0)
					{
						formaDePuntuacion = new Puntuacion_Por_Puntos(rs.getInt("CANT_TANTOS_OTORGAR_POR_AUSENCIA"));
						formaDePuntuacion.setId(rs.getInt("ID_FORMA_DE_PUNTUACION"));
					}
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
		return formaDePuntuacion;
	}
	
	@Override
	public Modalidad modalidadPorId(int id) 
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Modalidad modalidad = null;
		try {
				pstmt = conn.prepareStatement(MODALIDAD_POR_ID);
				pstmt.setInt(1, id);
				rs = pstmt.executeQuery();
				while(rs.next()) 
				{
					if(rs.getInt("ID_LIGA") != 0)
					{
						if(rs.getInt("PUNTOS_POR_PARTIDO_EMPATADO") != 0)
						{
							modalidad = new Liga(rs.getInt("PUNTOS_POR_PARTIDO_GANADO"), rs.getInt("PUNTOS_POR_PARTIDO_EMPATADO"), rs.getInt("PUNTOS_POR_PRESENTARSE"));
							modalidad.setId(rs.getInt("ID_MODALIDAD"));
						}
						else
						{
							modalidad = new Liga(rs.getInt("PUNTOS_POR_PARTIDO_GANADO"), 0, rs.getInt("PUNTOS_POR_PRESENTARSE"));
							modalidad.setId(rs.getInt("ID_MODALIDAD"));
						}
					}
					if(rs.getInt("ID_SIMPLE") != 0)
					{
						modalidad = new Eliminacion_Simple();
						modalidad.setId(rs.getInt("ID_MODALIDAD"));
					}
					if(rs.getInt("ID_DOBLE") != 0)
					{
						modalidad = new Eliminacion_Doble();
						modalidad.setId(rs.getInt("ID_MODALIDAD"));
					}
					
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
		return modalidad;
	}
	
	@Override
	public List<Disponibilidad> listarDisponibilidadesPorIdCompetencia(int id) 
	{
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		List<Disponibilidad> lista = new ArrayList<Disponibilidad>();
		Lugar_De_Realizacion_DAO lugarDao = new Lugar_De_Realizacion_DAO_PostgreSQL();
		try {
				pstmt = conn.prepareStatement(SELECT_POSEE_POR_ID);
				pstmt.setInt(1, id);
				rs = pstmt.executeQuery();
				while(rs.next()) 
				{
					Disponibilidad d = new Disponibilidad();
					d.setIdCompetencia(id);
					d.setLugar_de_realizacion(lugarDao.buscarPorId(rs.getInt("ID_LUGAR_DE_REALIZACION")));
					d.setCantidad(rs.getInt("DISPONIBILIDAD"));
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
			}
			catch(SQLException e) 
			{
				e.printStackTrace();
			}
		}
		return lista;
	}

	@Override
	public Competencia buscarPorId(Integer id) {
		{
			Competencia c = new Competencia();
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			Deporte_DAO deporteDAO = new Deporte_DAO_PostgreSQL();
			Lugar_De_Realizacion_DAO lugarDAO = new Lugar_De_Realizacion_DAO_PostgreSQL();
			Participante_DAO participanteDAO = new Participante_DAO_PostgreSQL();
			Usuario_DAO usuarioDAO = new Usuario_DAO_PostgreSQL();
			Fixture_DAO fixtureDAO = new Fixture_DAO_PostgreSQL();
			
			try 
			{
				pstmt= conn.prepareStatement("SELECT * FROM dds.competencia WHERE ID = "+ id);
				rs = pstmt.executeQuery();

				while(rs.next()) 
				{
					c.setId(rs.getInt("ID"));
					c.setDeporte(deporteDAO.buscarPorId(rs.getInt("ID_DEPORTE")));
					c.setForma_de_puntuacion(this.formaDePuntuacionPorId(rs.getInt("ID_FORMA_DE_PUNTUACION")));
					c.setModalidad(this.modalidadPorId(rs.getInt("ID_MODALIDAD")));
					c.setUsuario_creador(usuarioDAO.buscarPorCuil(rs.getInt("USUARIO")));
					c.setNombre(rs.getString("NOMBRE"));
					switch(rs.getString("ESTADO"))
					{
					case "CREADA":
						c.setEstado(Estado_Competencia.CREADA);
						break;
					case "PLANIFICADA":
						c.setEstado(Estado_Competencia.PLANIFICADA);
						break;
					case "EN_DISPUTA":
						c.setEstado(Estado_Competencia.EN_DISPUTA);
						break;
					case "FINALIZADA":
						c.setEstado(Estado_Competencia.FINALIZADA);
						break;
					}
					if(rs.getString("REGLAMENTO") == null)
					{
						c.setReglamento("");
					}
					else
					{
						c.setReglamento(rs.getString("REGLAMENTO"));
					}
					if(rs.getDate("FECHA_BAJA_LOGICA") != null)
					{
						c.setFecha_baja_logica(rs.getDate("FECHA_BAJA_LOGICA").toLocalDate());
					}
					if(rs.getTime("HORA_BAJA_LOGICA") != null)
					{
						c.setHora_baja_logica(rs.getTime("HORA_BAJA_LOGICA").toLocalTime());
					}
					c.setLugares_de_realizacion(lugarDAO.buscarLugaresPorDeporte(c.getDeporte().getNombre()));
					c.setDisponibilidades(this.listarDisponibilidadesPorIdCompetencia(c.getId()));
					c.setLista_de_participantes(participanteDAO.buscarPorIdCompetencia(c.getId()));
					c.setFixture(fixtureDAO.buscarPorIdCompetencia(c.getId()));
					if(c.getFixture().getId() == -1)
					{
						c.setFixture(null);
					}
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
			return c;
		}
	}
	
	


}
