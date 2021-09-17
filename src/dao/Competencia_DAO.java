package dao;

import java.sql.SQLException;
import java.util.List;
import dominio.Competencia;
import dominio.Disponibilidad;
import dominio.Forma_De_Puntuacion;
import dominio.Modalidad;
import excepciones.BaseDeDatosException;

public interface Competencia_DAO 
{
	
	public Competencia saveOrUpdate(Competencia c) throws BaseDeDatosException, SQLException;
	public List<Competencia> buscarTodas(Integer cuil_usuario_logeado);
	public List<Competencia> listarMisCompetencias(Integer cuil_usuario_logeado);
	public int crearFormaDePuntuacion(Forma_De_Puntuacion p);
	public int crearModalidad(Modalidad m);
	public void crearDisponibilidades(List<Disponibilidad> lista, int idCompetencia);
	public boolean existeNombreDeCompetencia(String nombre);
	public Forma_De_Puntuacion formaDePuntuacionPorId(int id);
	public Modalidad modalidadPorId(int id);
	public List<Disponibilidad> listarDisponibilidadesPorIdCompetencia(int id);
	public Competencia buscarPorId(Integer id);

}
