package interfaces_graficas;

import javax.swing.JPanel;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JLabel;
import java.awt.Font;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JTextField;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import dominio.Competencia;
import dominio.Participante;
import gestores.Gestor_Competencia;
import gestores.Gestor_Participante;
import modelos_de_tablas_interfaces_graficas.Modelo_Tabla_Listar_Participantes_CU08;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.ImageIcon;

public class CU08_Listar_Participantes_Competencia extends JPanel 
{
	private JLabel lblCompetencia;
	private JTextField txtNombreCompetencia;
	private Modelo_Tabla_Listar_Participantes_CU08 modeloTablaParticipantes;
	private JTable tablaParticipantes;
	private JButton btnAgregarParticipante;
	private JButton btnModificarParticipante;
	private JButton btnEliminarParticipante;
	private JLabel lblImagenFondo;
	private JButton btnCancelar;
	private LinkedList<Participante> participantes;
	private Competencia competencia;
	private Gestor_Competencia gestor_Competencia;
	private Integer cuil_usuario_logeado;
	
	public CU08_Listar_Participantes_Competencia(JFrame pantallaPrincipal, Integer  idCompetencia, Integer cuil_usuario_logeado) 
	{
		super();
		this.cuil_usuario_logeado = cuil_usuario_logeado;
		this.gestor_Competencia = new Gestor_Competencia();
		this.competencia = gestor_Competencia.buscarPorId(idCompetencia);
		this.participantes = competencia.getLista_de_participantes();
		this.armarPanel(pantallaPrincipal, idCompetencia);
	}
	
	public void armarPanel(JFrame pantallaPrincipal, Integer  idCompetencia) 
	{
		setLayout(null);
		lblCompetencia = new JLabel("Competencia Deportiva:");
		lblCompetencia.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblCompetencia.setBounds(40, 30, 150, 25);
		add(lblCompetencia);
		
		txtNombreCompetencia = new JTextField();
		txtNombreCompetencia.setEditable(false);
		txtNombreCompetencia.setBounds(200, 31, 180, 25);
		add(txtNombreCompetencia);
		txtNombreCompetencia.setText(competencia.getNombre());
		txtNombreCompetencia.setColumns(10);
		
		modeloTablaParticipantes = new Modelo_Tabla_Listar_Participantes_CU08(participantes);
		tablaParticipantes = new JTable();
		tablaParticipantes.setFont(new Font("Tahoma", Font.PLAIN, 12));
		tablaParticipantes.setModel(modeloTablaParticipantes);
		JScrollPane scrollPane = new JScrollPane(tablaParticipantes);
		tablaParticipantes.setFillsViewportHeight(true);
		scrollPane.setBounds(40, 75, 748, 500);
		TableRowSorter<TableModel> orden =  new TableRowSorter<TableModel>(modeloTablaParticipantes);
		tablaParticipantes.setRowSorter(orden);
		add(scrollPane);
		
		btnAgregarParticipante = new JButton("Agregar Participante");
		btnAgregarParticipante.addActionListener(e->
		{
			this.setVisible(false);
			JPanel panelAgregarParticipante = new CU09_Alta_De_Participante(pantallaPrincipal, idCompetencia, cuil_usuario_logeado);
			pantallaPrincipal.setContentPane(panelAgregarParticipante);
			pantallaPrincipal.setTitle("DAR DE ALTA PARTICIPANTE");
		});
		btnAgregarParticipante.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnAgregarParticipante.setBounds(798, 75, 170, 35);
		add(btnAgregarParticipante);
		
		btnModificarParticipante = new JButton("Modificar Participante");
		btnModificarParticipante.setEnabled(false);
		btnModificarParticipante.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnModificarParticipante.setBounds(798, 121, 170, 35);
		add(btnModificarParticipante);
		
		btnEliminarParticipante = new JButton("Eliminar Participante");
		btnEliminarParticipante.setEnabled(false);
		btnEliminarParticipante.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnEliminarParticipante.setBounds(798, 167, 170, 35);
		add(btnEliminarParticipante);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(e->
		{
			this.setVisible(false);
			JPanel panelListarCompetencias = new CU03_Listar_Competencias_Deportivas(pantallaPrincipal, cuil_usuario_logeado);
			pantallaPrincipal.setContentPane(panelListarCompetencias);
			pantallaPrincipal.setTitle("LISTAR MIS COMPETENCIAS DEPORTIVAS");
		});
		btnCancelar.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnCancelar.setBounds(907, 665, 89, 30);
		add(btnCancelar);
		
		lblImagenFondo = new JLabel("");
		lblImagenFondo.setIcon(new ImageIcon("2.jpg"));
		lblImagenFondo.setBounds(0, 0, 1008, 707);
		add(lblImagenFondo);

	}
}
