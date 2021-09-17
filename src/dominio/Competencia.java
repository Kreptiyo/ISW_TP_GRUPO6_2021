package dominio;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import dominio.Competencia.Estado_Competencia;

public class Competencia 
{
	
	private Integer id;
	private String nombre;
	private Forma_De_Puntuacion forma_de_puntuacion;
	private Modalidad modalidad;
	private Estado_Competencia estado;
	private String reglamento;
	private Deporte deporte;
	private LinkedList<Participante> lista_de_participantes;
	private Fixture fixture;
	private Usuario usuario_creador;
	private LocalDate fecha_baja_logica;
	private LocalTime hora_baja_logica;
	private List<Lugar_De_Realizacion> lugares_de_realizacion;
	private List<Disponibilidad> disponibilidades;
	private String string_de_prueba;
	
	
	public enum Estado_Competencia 
	{
		CREADA,PLANIFICADA,EN_DISPUTA,FINALIZADA;
	}
	
	public Competencia(Integer id, String nombre, Forma_De_Puntuacion forma_de_puntuacion, Modalidad modalidad,
			Estado_Competencia estado, String reglamento, LinkedList<Participante> lista_de_participantes,
			Fixture fixture, Usuario usuario_creador, LocalDate fecha_baja_logica, LocalTime hora_baja_logica,
			List<Lugar_De_Realizacion> lugares_de_realizacion, List<Disponibilidad> disponibilidades) 
	{
		super();
		this.id = id;
		this.nombre = nombre;
		this.forma_de_puntuacion = forma_de_puntuacion;
		this.modalidad = modalidad;
		this.estado = estado;
		this.reglamento = reglamento;
		this.lista_de_participantes = lista_de_participantes;
		this.fixture = fixture;
		this.usuario_creador = usuario_creador;
		this.fecha_baja_logica = fecha_baja_logica;
		this.hora_baja_logica = hora_baja_logica;
		this.lugares_de_realizacion = lugares_de_realizacion;
		this.disponibilidades = disponibilidades;
		this.fecha_baja_logica = null;
		this.hora_baja_logica = null;
		this.lista_de_participantes = new LinkedList<Participante>();
	}
	
	

	public Competencia(String nombre, Deporte deporte, Modalidad modalidad, Forma_De_Puntuacion forma_de_puntuacion,
			Estado_Competencia estado, String reglamento, List<Disponibilidad> disponibilidades) 
	{
		super();
		this.nombre = nombre;
		this.forma_de_puntuacion = forma_de_puntuacion;
		this.modalidad = modalidad;
		this.estado = estado;
		this.reglamento = reglamento;
		this.deporte = deporte;
		this.disponibilidades = disponibilidades;
		this.fecha_baja_logica = null;
		this.hora_baja_logica = null;
		this.lista_de_participantes = new LinkedList<Participante>();	
	}

	public Competencia() 
	{
		super();
		this.lista_de_participantes = new LinkedList<Participante>();
		this.setFixture(null);
	}


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Forma_De_Puntuacion getForma_de_puntuacion() {
		return forma_de_puntuacion;
	}

	public void setForma_de_puntuacion(Forma_De_Puntuacion forma_de_puntuacion) {
		this.forma_de_puntuacion = forma_de_puntuacion;
	}

	public Modalidad getModalidad() {
		return modalidad;
	}

	public void setModalidad(Modalidad modalidad) {
		this.modalidad = modalidad;
	}

	public Estado_Competencia getEstado() {
		return estado;
	}

	public void setEstado(Estado_Competencia estado) {
		this.estado = estado;
	}

	public String getReglamento() {
		return reglamento;
	}

	public void setReglamento(String reglamento) {
		this.reglamento = reglamento;
	}

	public LinkedList<Participante> getLista_de_participantes() {
		return lista_de_participantes;
	}

	public void setLista_de_participantes(LinkedList<Participante> lista_de_participantes) {
		this.lista_de_participantes = lista_de_participantes;
	}

	public Fixture getFixture() {
		return fixture;
	}

	public void setFixture(Fixture fixture) {
		this.fixture = fixture;
	}

	public Usuario getUsuario_creador() {
		return usuario_creador;
	}

	public void setUsuario_creador(Usuario usuario_creador) {
		this.usuario_creador = usuario_creador;
	}

	public LocalDate getFecha_baja_logica() {
		return fecha_baja_logica;
	}

	public void setFecha_baja_logica(LocalDate fecha_baja_logica) {
		this.fecha_baja_logica = fecha_baja_logica;
	}

	public LocalTime getHora_baja_logica() {
		return hora_baja_logica;
	}

	public void setHora_baja_logica(LocalTime hora_baja_logica) {
		this.hora_baja_logica = hora_baja_logica;
	}

	public List<Lugar_De_Realizacion> getLugares_de_realizacion() {
		return lugares_de_realizacion;
	}

	public void setLugares_de_realizacion(List<Lugar_De_Realizacion> lugares_de_realizacion) {
		this.lugares_de_realizacion = lugares_de_realizacion;
	}

	public Deporte getDeporte() {
		return deporte;
	}

	public void setDeporte(Deporte deporte) {
		this.deporte = deporte;
	}

	public List<Disponibilidad> getDisponibilidades() {
		return disponibilidades;
	}

	public void setDisponibilidades(List<Disponibilidad> disponibilidades) {
		this.disponibilidades = disponibilidades;
	}

	
}
