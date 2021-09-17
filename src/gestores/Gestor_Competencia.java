package gestores;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JTable;
import dao.Competencia_DAO;
import dao.Competencia_DAO_PostgreSQL;
import dominio.Competencia;
import dominio.Disponibilidad;
import dominio.Eliminacion_Doble;
import dominio.Eliminacion_Simple;
import dominio.Fixture;
import dominio.Forma_De_Puntuacion;
import dominio.Liga;
import dominio.Lugar_De_Realizacion;
import dominio.Modalidad;
import dominio.Participante;
import dominio.Puntuacion_Por_Puntos;
import dominio.Puntuacion_Por_Resultado_Final;
import dominio.Puntuacion_Por_Sets;
import dominio.Competencia.Estado_Competencia;
import excepciones.BaseDeDatosException;
import excepciones.CamposIncorrectosException;

public class Gestor_Competencia 
{
	
		private Competencia_DAO competenciaDAO;
		private Gestor_Lugar_De_Realizacion gestorLugar;
		private Gestor_Deporte gestorDeporte;
		private Gestor_Usuario gestorUsuario;
		
		public Gestor_Competencia() 
		{
			super();
			this.competenciaDAO = new Competencia_DAO_PostgreSQL();
			this.gestorLugar = new Gestor_Lugar_De_Realizacion();
			this.gestorDeporte = new Gestor_Deporte();
			this.gestorUsuario = new Gestor_Usuario();
		}
		
		public Competencia crearCompetencia(String nombreCompetencia, String nombreDeporte, String reglamento, JTable tablaLugares, 
				String nombreModalidad, Integer puntosPorPartidoGanado, boolean empate, Integer puntosPorPartidoEmpatado,
				Integer puntosPorPresentarse, boolean sets, Integer cantidadDeSets, boolean puntuacion, 
				Integer tantosOtorgadosPorAusenciaDelRival, boolean resultado_final, Integer cuil_usuario_logeado) throws CamposIncorrectosException, SQLException, BaseDeDatosException
		{
			this.validarDatos(nombreCompetencia, nombreDeporte, reglamento, tablaLugares, nombreModalidad, puntosPorPartidoGanado, empate,
					puntosPorPartidoEmpatado, puntosPorPresentarse, sets, cantidadDeSets, puntuacion, tantosOtorgadosPorAusenciaDelRival, resultado_final);
			Competencia c = new Competencia();
			this.actualizarModelo(c, nombreCompetencia, nombreDeporte, reglamento, tablaLugares, nombreModalidad, puntosPorPartidoGanado, empate, puntosPorPartidoEmpatado, puntosPorPresentarse, sets, cantidadDeSets, puntuacion, tantosOtorgadosPorAusenciaDelRival, resultado_final, cuil_usuario_logeado);
			return competenciaDAO.saveOrUpdate(c);
		}
		
		public void validarDatos(String nombreCompetencia, String nombreDeporte, String reglamento, JTable tablaLugares, 
				String nombreModalidad, Integer puntosPorPartidoGanado, boolean empate, Integer puntosPorPartidoEmpatado,
				Integer puntosPorPresentarse, boolean sets, Integer cantidadDeSets, boolean puntuacion, 
				Integer tantosOtorgadosPorAusenciaDelRival, boolean resultado_final) throws CamposIncorrectosException
		{
				StringBuilder mensajeAMostrar = new StringBuilder();
				List<String> lista_de_campos_erroneos = new ArrayList<String>();
				if(nombreCompetencia.isEmpty())
				{
					lista_de_campos_erroneos.add("Nombre");
					mensajeAMostrar.append("\n"+"- Nombre de la Competencia. (Campo incompleto-Dato Obligatorio)"+"\n");
				}
				else
				{
					if(this.existeNombreDeCompetencia(nombreCompetencia))
					{
						lista_de_campos_erroneos.add("Nombre");
						mensajeAMostrar.append("\n"+"- El nombre de la competencia ya existe."+"\n");
					}
				}
				if(nombreDeporte.equals("Seleccionar"))
				{
					lista_de_campos_erroneos.add("Deporte");
					mensajeAMostrar.append("\n"+"- Deporte. (Campo incompleto-Dato Obligatorio)"+"\n");
				}
				if(tablaLugares.getRowCount() == 0) 
				{
					mensajeAMostrar.append("\n"+"- Lugar de Realización. (No agregó Lugares-Datos Obligatorios)"+"\n");
				}
				if(nombreModalidad.equals("Seleccionar"))
				{
					lista_de_campos_erroneos.add("Modalidad");
					mensajeAMostrar.append("\n"+"- Modalidad. (Campo incompleto-Dato Obligatorio)"+"\n");
				}
				//ACA VERIFICO SI SE REALIZA UNA LIGA CON O SIN EMPATE Y EN BASE A ESO ME GUIO COMO REALIZAR LAS VERIFICACIONES
				if(nombreModalidad.equals("Liga")) 
				{
					//LIGA CON EMPATE
					if(empate)
					{
						//SIN CANTIDAD DE PUNTOS POR EMPATE
						if(puntosPorPartidoEmpatado == null)
						{
								if(puntosPorPartidoGanado == null)
								{
									lista_de_campos_erroneos.add("PuntosPG");
									mensajeAMostrar.append("\n"+"- Puntos por Partido Ganado. (Campo incompleto-Dato Obligatorio)"+"\n");
								}
								if(puntosPorPresentarse == null)
								{
									lista_de_campos_erroneos.add("PuntosPP");
									mensajeAMostrar.append("\n"+"- Puntos por Partido Presentarse. (Campo incompleto-Dato Obligatorio)"+"\n");
								}
								lista_de_campos_erroneos.add("PuntosPE");
								mensajeAMostrar.append("\n"+"- Puntos por Partido Empatado. (Campo incompleto-Dato Obligatorio)"+"\n");
							if(!(puntosPorPartidoGanado == null) && !(puntosPorPresentarse == null))
							{
								if(puntosPorPresentarse >= puntosPorPartidoGanado) 
								{
									
									lista_de_campos_erroneos.add("PuntosPP");
									mensajeAMostrar.append("\n"+"- La cantidad de puntos por presentarse" +"\n"+
															   				 "debe ser menor a la cantidad de puntos"+"\n"+
																			 "por partido ganado."+"\n");
								}
							}
						}
						//CON CANTIDAD DE PUNTOS POR EMPATE
						else
						{
							if(puntosPorPartidoGanado == null)
							{
								lista_de_campos_erroneos.add("PuntosPG");
								mensajeAMostrar.append("\n"+"- Puntos por Partido Ganado. (Campo incompleto-Dato Obligatorio)"+"\n");
							}
							if(puntosPorPresentarse == null)
							{
								lista_de_campos_erroneos.add("PuntosPP");
								mensajeAMostrar.append("\n"+"- Puntos por Partido Presentarse. (Campo incompleto-Dato Obligatorio)"+"\n");
								if(!(puntosPorPartidoGanado == null))
								{
									if(puntosPorPartidoGanado < puntosPorPartidoEmpatado) 
									{
										
										lista_de_campos_erroneos.add("PuntosPG");
										mensajeAMostrar.append("\n"+"- La cantidad de puntos por partido ganado" +"\n"+
																			     "debe ser mayor que la cantidad de puntos"+"\n"+
												  							     "por partido empatado."+"\n");
									}
								}
							}
							if(!(puntosPorPartidoGanado == null) && !(puntosPorPresentarse == null))
							{
								if(puntosPorPartidoGanado < puntosPorPartidoEmpatado) 
								{
									
									lista_de_campos_erroneos.add("PuntosPG");
									mensajeAMostrar.append("\n"+"- La cantidad de puntos por partido ganado" +"\n"+
										     								 "debe ser mayor que la cantidad de puntos"+"\n"+
										     								 "por partido empatado."+"\n");
								}
								if(puntosPorPresentarse >= puntosPorPartidoGanado) 
								{
									
									lista_de_campos_erroneos.add("PuntosPP");
									mensajeAMostrar.append("\n"+"- La cantidad de puntos por presentarse" +"\n"+
																			 "debe ser menor a la cantidad de puntos"+"\n"+
																			 "por partido ganado."+"\n");
								}
							}
						}
					}
					//LIGA SIN EMPATE
					else
					{
							if(puntosPorPartidoGanado == null)
							{
								lista_de_campos_erroneos.add("PuntosPG");
								mensajeAMostrar.append("\n"+"- Puntos por Partido Ganado. (Campo incompleto-Dato Obligatorio)"+"\n");
							}
							if(puntosPorPresentarse == null)
							{
								lista_de_campos_erroneos.add("PuntosPP");
								mensajeAMostrar.append("\n"+"- Puntos por Partido Presentarse. (Campo incompleto-Dato Obligatorio)"+"\n");
							}
							if(!(puntosPorPartidoGanado == null) && !(puntosPorPresentarse == null))
							{
								if(puntosPorPresentarse >= puntosPorPartidoGanado) 
								{
								
									lista_de_campos_erroneos.add("PuntosPP");
									mensajeAMostrar.append("\n"+"- La cantidad de puntos por presentarse" +"\n"+
											"no pueden ser mayor o igual a la cantidad de puntos"+"\n"+
											"por partido ganado. Por favor ingrese una nueva cantidad."+"\n");
								}
						}
					}
				}
				if(!sets && !puntuacion && !resultado_final)
				{
					lista_de_campos_erroneos.add("Forma de Puntuacion");
					mensajeAMostrar.append("\n"+"- Forma de Puntuación. (Campo incompleto-Dato Obligatorio)"+"\n");
				}
				else
				{
					if(sets && cantidadDeSets == null)
					{
						lista_de_campos_erroneos.add("Cantidad Sets");
						mensajeAMostrar.append("\n"+"- Cantidad de Sets. (Campo incompleto-Dato Obligatorio)"+"\n");
					}
					if(sets && !(cantidadDeSets == null))
					{
						if(cantidadDeSets > 10) 
						{
							
							lista_de_campos_erroneos.add("Cantidad Sets");
							mensajeAMostrar.append("\n"+"- La cantidad de Sets debe ser menor a 10."+"\n");
						}
						if((cantidadDeSets % 2) == 0) 
						{
							
							lista_de_campos_erroneos.add("Cantidad Sets");
							mensajeAMostrar.append("\n"+"- La cantidad de Sets debe ser un número impar."+"\n");
						}
					}
					if(puntuacion && tantosOtorgadosPorAusenciaDelRival == null)
					{
						lista_de_campos_erroneos.add("Tantos por Ausencia");
						mensajeAMostrar.append("\n"+"- Tantos Otorgados por Ausencia del Rival. (Campo incompleto-Dato Obligatorio)"+"\n");
					}
				}
					
				if(mensajeAMostrar.length() != 0)
				{
					throw new CamposIncorrectosException(mensajeAMostrar.toString(), lista_de_campos_erroneos);
				}
		}
		
		public void actualizarModelo(Competencia c, String nombreCompetencia, String nombreDeporte, String reglamento, JTable tablaLugares, 
				String nombreModalidad, Integer puntosPorPartidoGanado, boolean empate, Integer puntosPorPartidoEmpatado,
				Integer puntosPorPresentarse, boolean sets, Integer cantidadDeSets, boolean puntuacion, 
				Integer tantosOtorgadosPorAusenciaDelRival, boolean resultado_final, Integer cuil_usuario_logeado)
		{
			c.setNombre(nombreCompetencia);
			c.setDeporte(gestorDeporte.deportePorNombre(nombreDeporte));
			c.setModalidad(this.crearModalidad(nombreModalidad, puntosPorPartidoGanado, empate, puntosPorPartidoEmpatado, puntosPorPresentarse));
			c.setForma_de_puntuacion(this.crearFormaDePuntuacion(sets, cantidadDeSets, puntuacion, tantosOtorgadosPorAusenciaDelRival, resultado_final));
			c.setUsuario_creador(gestorUsuario.buscarPorCuil(cuil_usuario_logeado));
			c.setReglamento(reglamento);
			c.setDisponibilidades(this.crearDisponibilidades(tablaLugares));
			c.setEstado(Estado_Competencia.CREADA);
		}
		
		public Modalidad crearModalidad(String nombreModalidad, Integer puntosPorPartidoGanado, boolean empate, Integer puntosPorPartidoEmpatado, Integer puntosPorPresentarse)
		{
			Modalidad m = null;
			
			if(nombreModalidad.equals("Liga"))
			{
				if(empate)
				{
					m = new Liga(puntosPorPartidoGanado, puntosPorPartidoEmpatado, puntosPorPresentarse);
				}
				else
				{
					m = new Liga(puntosPorPartidoGanado, 0, puntosPorPresentarse);
				}
			}
			if(nombreModalidad.equals("Eliminación Simple"))
			{
				m = new Eliminacion_Simple();
			}
			if(nombreModalidad.equals("Eliminación Doble"))
			{
				m = new Eliminacion_Doble();
			}
			
			return m;
		}
		
		public Forma_De_Puntuacion crearFormaDePuntuacion(boolean sets, Integer cantidadDeSets, boolean puntuacion, 
				Integer tantosOtorgadosPorAusenciaDelRival, boolean resultado_final)
		{
			Forma_De_Puntuacion f = null;
			if(sets)
			{
				f = new Puntuacion_Por_Sets(cantidadDeSets);
			}
			if(puntuacion)
			{
				f = new Puntuacion_Por_Puntos(tantosOtorgadosPorAusenciaDelRival);
			}
			if(resultado_final)
			{
				f = new Puntuacion_Por_Resultado_Final();
			}
			return f;
		}
		
		public List<Disponibilidad> crearDisponibilidades(JTable tablaLugares)
		{
			List<Disponibilidad> disponibilidades = new ArrayList<Disponibilidad>();
			for(int i = 0 ; i < tablaLugares.getRowCount() ; i++) 
			{
				disponibilidades.add(new Disponibilidad(gestorLugar.buscarPorId(Integer.parseInt(tablaLugares.getValueAt(i, 0).toString())), Integer.parseInt(tablaLugares.getValueAt(i, 2).toString())));
			}
			
			return disponibilidades;
		}
		
		public boolean existeNombreDeCompetencia(String nombre)
		{
			return competenciaDAO.existeNombreDeCompetencia(nombre);
		}
		
		public List<Competencia> listarTodas(Integer cuil_usuario_logeado) 
		{
			return competenciaDAO.buscarTodas(cuil_usuario_logeado);
		}
		
		public List<Competencia> listarMisCompetencias(Integer cuil_usuario_logeado) 
		{
			return competenciaDAO.listarMisCompetencias(cuil_usuario_logeado);
		}
		
		public Competencia buscarPorId(Integer id) 
		{
			return competenciaDAO.buscarPorId(id);
		}
		
		public void agregarParticipante(Participante p, Competencia c) throws BaseDeDatosException, SQLException 
		{
			c.getLista_de_participantes().add(p);
			if(c.getEstado().equals(Estado_Competencia.PLANIFICADA))
			{
				c.setEstado(Estado_Competencia.CREADA);
				c.setFixture(null);
			}
			competenciaDAO.saveOrUpdate(c);
		}
		
		public void setFixtureCompetencia(Fixture f, Competencia c) throws BaseDeDatosException, SQLException 
		{
			
			c.setFixture(f);
			if(f != null) 
			{
				if(c.getEstado().equals(Estado_Competencia.CREADA))
				{
					c.setEstado(Estado_Competencia.PLANIFICADA);
				}
			}
			competenciaDAO.saveOrUpdate(c);
		}
		
		public List<Disponibilidad> listaDeDisponibilidadesPorIdCompetencia(Integer id_competencia)
		{
			return this.competenciaDAO.listarDisponibilidadesPorIdCompetencia(id_competencia);
		}
		
}

