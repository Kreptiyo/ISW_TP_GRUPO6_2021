package dominio;

public class Encuentro 
{
	private Integer id;
	private Integer id_sig_encuentro_ganador;
	private Integer id_sig_encuentro_perdedor;
	private Participante participante1;
	private Participante participante2;
	private Resultado resultado;
	private Lugar_De_Realizacion lugarDeRealizacion;
	private boolean ausencia_participante1;
	private boolean ausencia_participante2;
	private Participante ganador;
	
	public Encuentro()
	{
		super();
		this.id = -1;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getId_sig_encuentro_ganador() {
		return id_sig_encuentro_ganador;
	}
	public void setId_sig_encuentro_ganador(Integer id_sig_encuentro_ganador) {
		this.id_sig_encuentro_ganador = id_sig_encuentro_ganador;
	}
	public Integer getId_sig_encuentro_perdedor() {
		return id_sig_encuentro_perdedor;
	}
	public void setId_sig_encuentro_perdedor(Integer id_sig_encuentro_perdedor) {
		this.id_sig_encuentro_perdedor = id_sig_encuentro_perdedor;
	}
	public Participante getParticipante1() {
		return participante1;
	}
	public void setParticipante1(Participante participante1) {
		this.participante1 = participante1;
	}
	public Participante getParticipante2() {
		return participante2;
	}
	public void setParticipante2(Participante participante2) {
		this.participante2 = participante2;
	}
	public Resultado getResultado() {
		return resultado;
	}
	public void setResultado(Resultado resultado) {
		this.resultado = resultado;
	}
	public Lugar_De_Realizacion getLugarDeRealizacion() {
		return lugarDeRealizacion;
	}
	public void setLugarDeRealizacion(Lugar_De_Realizacion lugarDeRealizacion) {
		this.lugarDeRealizacion = lugarDeRealizacion;
	}
	public boolean isAusencia_participante1() {
		return ausencia_participante1;
	}
	public void setAusencia_participante1(boolean ausencia_participante1) {
		this.ausencia_participante1 = ausencia_participante1;
	}
	public boolean isAusencia_participante2() {
		return ausencia_participante2;
	}
	public void setAusencia_participante2(boolean ausencia_participante2) {
		this.ausencia_participante2 = ausencia_participante2;
	}
	public Participante getGanador() {
		return ganador;
	}
	public void setGanador(Participante ganador) {
		this.ganador = ganador;
	}
	
	
}
