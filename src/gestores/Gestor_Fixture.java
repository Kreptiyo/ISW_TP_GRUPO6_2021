package gestores;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import dao.Fixture_DAO;
import dao.Fixture_DAO_PostgreSQL;
import dominio.Competencia;
import dominio.Disponibilidad;
import dominio.Encuentro;
import dominio.Fecha;
import dominio.Fixture;
import dominio.Liga;
import dominio.Lugar_De_Realizacion;
import dominio.Participante;
import dominio.Competencia.Estado_Competencia;
import excepciones.CamposIncorrectosException;

public class Gestor_Fixture 
{
	private Fixture_DAO fixtureDAO;
	private Gestor_Competencia gestorCompetencia;
	private Gestor_Encuentro gestorEncuentro;
	private Gestor_Participante gestorParticipante;
	
	public Gestor_Fixture()
	{
		super();
		this.fixtureDAO = new Fixture_DAO_PostgreSQL();
		this.gestorCompetencia = new Gestor_Competencia();
		this.gestorEncuentro = new Gestor_Encuentro();
		this.gestorParticipante = new Gestor_Participante();
	}
	
	public void generarFixture(Integer id_competencia) throws Exception
	{
		long now = System.currentTimeMillis();
		StringBuilder mensajeAMostrar = new StringBuilder();
		Fixture fixture = new Fixture();
		Competencia c = this.gestorCompetencia.buscarPorId(id_competencia);
		List<Disponibilidad> disponibilidades = c.getDisponibilidades();
		List<Disponibilidad> disponibilidadesMemoria = new ArrayList<Disponibilidad>();
		Lugar_De_Realizacion lugar = null;
		LinkedList<Participante> lista_de_participantes = c.getLista_de_participantes();
		int cantidad_de_participantes = c.getLista_de_participantes().size();
		int plazas_totales_disponibles = 0;
		int nro_de_fechas = cantidad_de_participantes - 1;
		int nro_de_encuentros = cantidad_de_participantes / 2;
		
		for(Disponibilidad unaDisponibilidad: disponibilidades)
		{
			plazas_totales_disponibles += unaDisponibilidad.getCantidad();
		}
		
		//Validaciones
		if(!c.getModalidad().getClass().equals(Liga.class))
		{
			mensajeAMostrar.append("\n"+"- No se puede generar un fixture para una competencia que no sea de modalidad Liga."+"\n");
		}
		if(c.getEstado().equals(Estado_Competencia.EN_DISPUTA))
		{
			mensajeAMostrar.append("\n"+"- La competencia se encuentra en disputa."+"\n");
		}
		if(c.getEstado().equals(Estado_Competencia.FINALIZADA))
		{
			mensajeAMostrar.append("\n"+"- La competencia ha finalizado."+"\n");
		}
		if(lista_de_participantes.size() <= 1)
		{
			mensajeAMostrar.append("\n"+"- No hay suficientes participantes para generar el fixture."+"\n");
		}
		if((cantidad_de_participantes / 2) > plazas_totales_disponibles)
		{
			mensajeAMostrar.append("\n"+"- No hay lugares suficientes para llevar a cabo los encuentros."+"\n");
		}
		if(mensajeAMostrar.length() != 0)
		{
			throw new Exception(mensajeAMostrar.toString());
		}
			
		
		//AGREGAR PARTICIPANTE DUMMY PARA CANTIDAD DE PARTICIPANTES IMPARES
		if(cantidad_de_participantes % 2 != 0)
		{
			lista_de_participantes.add(gestorParticipante.crearParticipanteDummy());
		}
		
		//GENERAR FECHAS, ENCUENTROS Y SETEARLOS EN FIXTURE
		for(int i = 0 ; i < nro_de_fechas ; i++)
		{
			Fecha fecha = new Fecha();
			fecha.setNumero(i + 1);
			int primero = 0;
			int segundo = cantidad_de_participantes - 1;
			
			disponibilidadesMemoria.clear();
			for(Disponibilidad unaDisponibilidad: disponibilidades)
			{
				Disponibilidad d = new Disponibilidad();
				d.setLugar_de_realizacion(unaDisponibilidad.getLugar_de_realizacion());
				d.setCantidad(unaDisponibilidad.getCantidad());
				disponibilidadesMemoria.add(d);
			}
			
			for(int j = 0 ; j < nro_de_encuentros ; j++)
			{
				lugar = null;
				for(Disponibilidad unaDisponibilidad: disponibilidadesMemoria)
				{
					if(unaDisponibilidad.getCantidad() >= 1 && lugar == null)
					{
						unaDisponibilidad.setCantidad(unaDisponibilidad.getCantidad() - 1);
						lugar = unaDisponibilidad.getLugar_de_realizacion();
					}
				}
				Participante participante1 = lista_de_participantes.get(primero);
				Participante participante2 = lista_de_participantes.get(segundo);
				primero++;
				segundo--;
				fecha.getListaDeEncuentros().add(gestorEncuentro.crearEncuentro(participante1, participante2, lugar));
			}
			Participante aux1 = lista_de_participantes.removeFirst();
			Participante aux2 = lista_de_participantes.removeLast();
			lista_de_participantes.add(aux1);
			lista_de_participantes.add(aux2);
			fixture.getListaDeFechasRondasGanadores().add(fecha);
		}
		gestorCompetencia.setFixtureCompetencia(fixture, c);
		System.out.println("Latencia GESTOR FIXTURE: " + (System.currentTimeMillis() -now)+ "ms \n");
	}
}
