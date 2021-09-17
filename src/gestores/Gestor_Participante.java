package gestores;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import dao.Participante_DAO;
import dao.Participante_DAO_PostgreSQL;
import dominio.Competencia;
import dominio.Participante;
import dominio.Competencia.Estado_Competencia;
import excepciones.BaseDeDatosException;
import excepciones.CamposIncorrectosException;

public class Gestor_Participante 
{
	
	private Participante_DAO participanteDAO;
	private Participante p;
	private Gestor_Competencia gestorCompetencia;
	private Competencia c;
	
	
	public Gestor_Participante()
	{
		this.participanteDAO = new Participante_DAO_PostgreSQL();
		this.gestorCompetencia = new Gestor_Competencia();
		
	}
	
	public void crearParticipante(String nombreParticipante, String emailParticipante,
			Integer idCompetencia) throws CamposIncorrectosException, SQLException, BaseDeDatosException
	{
		long now = System.currentTimeMillis();
		c = gestorCompetencia.buscarPorId(idCompetencia);
		this.validarDatos(nombreParticipante, emailParticipante, c);
		this.actualizarModelo(nombreParticipante, emailParticipante);
		gestorCompetencia.agregarParticipante(p, c);
		System.out.println("Latencia: " + (System.currentTimeMillis() -now)+ "ms \n");
	}
	
	public void validarDatos(String nombreParticipante, String emailParticipante, Competencia c) throws CamposIncorrectosException
	{
		
		StringBuilder mensajeAMostrar = new StringBuilder();
		List<String> lista_de_campos_erroneos = new ArrayList<String>();
		List<Participante> listaDeParticipantes = new ArrayList<Participante>();
		
		/*busco directamente todos los participantes de esa competencia para validar el nombre y el email
		 * (para no hacer dos consultas, traigo todos los participantes de esa competencia y filtro en memoria)*/
		
		listaDeParticipantes = this.buscarPorIdCompetencia(c.getId());
		
		if(!c.getEstado().equals(Estado_Competencia.CREADA) && !c.getEstado().equals(Estado_Competencia.PLANIFICADA))
		{
			lista_de_campos_erroneos.add("Estado");
			mensajeAMostrar.append("\n"+"- La competencia no se encuentra en estado “Creada” o “Planificada." +"\n"+ "No se pueden agregar participantes a la competencia."+"\n");
		}
		
		
		if(nombreParticipante.isEmpty())
		{
			lista_de_campos_erroneos.add("Nombre");
			mensajeAMostrar.append("\n"+"- Nombre del Participante. (Campo incompleto-Dato Obligatorio)"+"\n");
		}
		
		if(!nombreParticipante.isEmpty()) {
			
			if(this.validarNombreParticipante(nombreParticipante, c.getId())) {
				lista_de_campos_erroneos.add("Nombre");
				mensajeAMostrar.append("\n"+"- El nombre del Participante ya existe."+"\n");
			}
		}
		
		
		if(emailParticipante.isEmpty())
		{
			lista_de_campos_erroneos.add("Email");
			mensajeAMostrar.append("\n"+"- Correo Electrónico del Participante. (Campo incompleto-Dato Obligatorio)"+"\n");
		}
		if(!emailParticipante.isEmpty()) {
					
					Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
					
					Matcher mather = pattern.matcher(emailParticipante);
					
					if(this.validarEmailParticipante(emailParticipante, c.getId())) {
						lista_de_campos_erroneos.add("Email");
						mensajeAMostrar.append("\n"+"- El Email ya se encuentra asociado a otro participante."+"\n");
					}
		
					/*VALIDA QUE EL FORMATO DEL EMAIL SEA EL CORRECTO*/
					
					if (mather.find() == false) {
						
						lista_de_campos_erroneos.add("Email");
						mensajeAMostrar.append("\n"+"- Formato inválido para Correo Electrónico."+"\n");
					}
					
				}
		
		if(mensajeAMostrar.length() != 0)
		{
			throw new CamposIncorrectosException(mensajeAMostrar.toString(), lista_de_campos_erroneos);
		}
	}
	
	public void actualizarModelo(String nombreParticipante, String emailParticipante)
	{
		this.p = new Participante();
		p.setNombre(nombreParticipante);
		p.setEmail(emailParticipante);
	}
	
	public Participante crearParticipanteDummy()
	{
		Participante dummy = new Participante();
		dummy.setId(-1);
		dummy.setNombre("dummy");
		dummy.setEmail("dummy@dummy.com");
		return dummy;
	}
	
	public List<Participante> buscarPorIdCompetencia(Integer id) 
	{
		return participanteDAO.buscarPorIdCompetencia(id);
	}
	
	public boolean validarNombreParticipante(String nombre, Integer idCompetencia) {
		return participanteDAO.existeNombreParticipante(nombre,idCompetencia);
	}
	
	public boolean validarEmailParticipante(String email, Integer idCompetencia) {
		return participanteDAO.existeEmailParticipante(email,idCompetencia);
	}
	

}
