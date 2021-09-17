package interfaces_graficas;

import javax.swing.JPanel;

import javax.swing.JScrollPane;
import javax.swing.JLabel;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextField;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import dominio.Competencia;
import dominio.Competencia.Estado_Competencia;
import dominio.Eliminacion_Doble;
import dominio.Eliminacion_Simple;
import dominio.Encuentro;
import dominio.Liga;
import dominio.Participante;
import gestores.Gestor_Competencia;
import modelos_de_tablas_interfaces_graficas.Modelo_Tabla_Participantes_CU20;
import modelos_de_tablas_interfaces_graficas.Modelo_Tabla_Proximos_Encuentros_CU20;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CU20_Ver_Competencia extends JPanel {
	
	private JTextField txtNombreCompetencia;
	private JLabel lblNombreDeLaCompetencia;
	private JTextField txtModalidad;
	private JLabel lblModalidad;
	private JTextField txtDeporte;
	private JLabel lblDeporteAsociado;
	private JTextField txtEstado;
	private JLabel lblEstado;
	private JTable tablaParticipantes;
	private Modelo_Tabla_Participantes_CU20 modeloTablaParticipantes;
	private JButton btnVolver;
	private JButton btnMostrarParticipantes;
	private JLabel lblProximosEncuentros;
	private JButton btnModificarCompetencia;
	private JTable tablaEncuentros;
	private JButton btnDarDeBajaCompetencia;
	private JButton btnMostrarTablaDePos;
	private JButton btnMostrarFixture;
	private JButton btnGenerarFixture;
	private JLabel lblImagenFondo;
	private Modelo_Tabla_Proximos_Encuentros_CU20 modeloTablaEncuentros;
	private Gestor_Competencia gestorCompetencia;
	private Competencia competencia;
	private List<Participante> participantes;
	private List<Encuentro> proximosEncuentros;

	public CU20_Ver_Competencia(JFrame pantallaPrincipal, Integer idCompetencia) {
		super();
		this.gestorCompetencia = new Gestor_Competencia();
		this.competencia = gestorCompetencia.buscarPorId(idCompetencia);
	
		this.proximosEncuentros = new ArrayList<Encuentro>();
		if(competencia.getEstado().equals(Estado_Competencia.PLANIFICADA) || competencia.getEstado().equals(Estado_Competencia.EN_DISPUTA)) 
		{
			
			this.proximosEncuentros = competencia.getFixture().getListaDeFechasRondasGanadores().get(0).getListaDeEncuentros();
			
		}
		this.participantes = new ArrayList<Participante>();
		this.participantes = competencia.getLista_de_participantes();
		this.armarPanel(pantallaPrincipal, idCompetencia);
	}
	
	public void armarPanel(JFrame pantallaPrincipal, Integer idCompetencia) {	
		
		setLayout(null);
		
		lblNombreDeLaCompetencia = new JLabel("Nombre de la Competencia");
		lblNombreDeLaCompetencia.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNombreDeLaCompetencia.setBounds(40, 40, 200, 25);
		add(lblNombreDeLaCompetencia);
		
		txtNombreCompetencia = new JTextField();
		txtNombreCompetencia.setEditable(false);
		txtNombreCompetencia.setBounds(250, 41, 180, 25);
		add(txtNombreCompetencia);
		txtNombreCompetencia.setText(competencia.getNombre());
		txtNombreCompetencia.setColumns(10);
		
		lblModalidad = new JLabel("Modalidad de la Competencia");
		lblModalidad.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblModalidad.setBounds(40, 76, 200, 25);
		add(lblModalidad);
		
		txtModalidad = new JTextField();
		txtModalidad.setEditable(false);
		txtModalidad.setColumns(10);
		txtModalidad.setBounds(250, 79, 180, 25);
		
    	if(competencia.getModalidad().getClass().equals(Eliminacion_Simple.class)) {
    		txtModalidad.setText("Eliminación Simple"); 
    	}
    	
    	if(competencia.getModalidad().getClass().equals(Eliminacion_Doble.class)) {
    		txtModalidad.setText("Eliminación Doble"); 
    	}
    	
    	if(competencia.getModalidad().getClass().equals(Liga.class)) {
    		txtModalidad.setText("Liga");  
    	}
		add(txtModalidad);
		
		lblDeporteAsociado = new JLabel("Deporte Asociado a Competencia");
		lblDeporteAsociado.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblDeporteAsociado.setBounds(568, 40, 210, 25);
		add(lblDeporteAsociado);
		
		txtDeporte = new JTextField();
		txtDeporte.setEditable(false);
		txtDeporte.setColumns(10);
		txtDeporte.setBounds(788, 41, 180, 25);
		txtDeporte.setText(competencia.getDeporte().getNombre());
		add(txtDeporte);
		
		lblEstado = new JLabel("Estado de la Competencia");
		lblEstado.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblEstado.setBounds(568, 82, 210, 25);
		add(lblEstado);
		
		txtEstado = new JTextField();
		txtEstado.setEditable(false);
		txtEstado.setColumns(10);
		txtEstado.setBounds(788, 79, 180, 25);
		txtEstado.setText(competencia.getEstado().name());
		add(txtEstado);
		
		modeloTablaParticipantes = new Modelo_Tabla_Participantes_CU20(participantes);
		tablaParticipantes = new JTable();
		tablaParticipantes.setFont(new Font("Tahoma", Font.PLAIN, 12));
		tablaParticipantes.setModel(modeloTablaParticipantes);
		JScrollPane scrollPane = new JScrollPane(tablaParticipantes);
		tablaParticipantes.setFillsViewportHeight(true);
		scrollPane.setBounds(40, 141, 928, 163);
		TableRowSorter<TableModel> orden =  new TableRowSorter<TableModel>(modeloTablaParticipantes);
		tablaParticipantes.setRowSorter(orden);
		add(scrollPane);
		
		
		btnMostrarParticipantes = new JButton("Mostrar Participantes");
		btnMostrarParticipantes.addActionListener(e->{
			
			this.setVisible(false);
			JPanel panelVerParticipantes = new CU08_Listar_Participantes_Competencia(pantallaPrincipal, competencia.getId(), competencia.getUsuario_creador().getCuil());
			pantallaPrincipal.setContentPane(panelVerParticipantes);
			pantallaPrincipal.setTitle("LISTAR PARTICIPANTES DE COMPETENCIA DEPORTIVA");
			
		});
		btnMostrarParticipantes.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnMostrarParticipantes.setBounds(788, 315, 180, 30);
		add(btnMostrarParticipantes);
		
		lblProximosEncuentros = new JLabel("Proximos Encuentros:");
		lblProximosEncuentros.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblProximosEncuentros.setBounds(40, 352, 142, 25);
		add(lblProximosEncuentros);

		
		modeloTablaEncuentros = new Modelo_Tabla_Proximos_Encuentros_CU20(proximosEncuentros);
		tablaEncuentros = new JTable();
		tablaEncuentros.setFont(new Font("Tahoma", Font.PLAIN, 12));
		tablaEncuentros.setModel(modeloTablaEncuentros);
		JScrollPane scrollPane2 = new JScrollPane(tablaEncuentros);
		tablaEncuentros.setFillsViewportHeight(true);
		scrollPane2.setBounds(40, 388, 928, 163);
		TableRowSorter<TableModel> orden2 =  new TableRowSorter<TableModel>(modeloTablaEncuentros);
		tablaEncuentros.setRowSorter(orden2);
		add(scrollPane2);
		
		
		btnMostrarTablaDePos = new JButton("Mostrar Tabla de Posiciones");
		btnMostrarTablaDePos.setEnabled(false);
		btnMostrarTablaDePos.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnMostrarTablaDePos.setBounds(758, 562, 210, 30);
		add(btnMostrarTablaDePos);
		
		btnMostrarFixture = new JButton("Mostrar Fixture");
		btnMostrarFixture.setEnabled(false);
		btnMostrarFixture.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnMostrarFixture.setBounds(40, 562, 150, 30);
		add(btnMostrarFixture);
		
		btnGenerarFixture = new JButton("Generar Fixture");
		btnGenerarFixture.addActionListener(e->{
			
			CU017_Generar_Fixture pantallaGenerarFixture = new CU017_Generar_Fixture(pantallaPrincipal, true, idCompetencia);
			pantallaGenerarFixture.setVisible(false);
			this.setVisible(false);
			CU20_Ver_Competencia recarga = new CU20_Ver_Competencia(pantallaPrincipal, idCompetencia);
			pantallaPrincipal.setContentPane(recarga);
			recarga.setVisible(true);
			
			
		});
		btnGenerarFixture.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnGenerarFixture.setBounds(200, 562, 150, 30);
		add(btnGenerarFixture);
		
		btnVolver = new JButton("Volver");
		btnVolver.addActionListener(e->{
			
			this.setVisible(false);
			JPanel panelListarMisCompetencias = new CU03_Listar_Competencias_Deportivas(pantallaPrincipal, competencia.getUsuario_creador().getCuil());
			pantallaPrincipal.setContentPane(panelListarMisCompetencias);
			pantallaPrincipal.setTitle("LISTAR MIS COMPETENCIAS DEPORTIVAS");
			
		});
		btnVolver.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnVolver.setBounds(907, 665, 89, 30);
		add(btnVolver);
		
		btnModificarCompetencia = new JButton("Modificar Competencia");
		btnModificarCompetencia.setEnabled(false);
		btnModificarCompetencia.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnModificarCompetencia.setBounds(10, 666, 180, 30);
		add(btnModificarCompetencia);
		
		btnDarDeBajaCompetencia = new JButton("Dar de Baja Competencia");
		btnDarDeBajaCompetencia.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnDarDeBajaCompetencia.setEnabled(false);
		btnDarDeBajaCompetencia.setBounds(200, 666, 200, 30);
		add(btnDarDeBajaCompetencia);
		
		lblImagenFondo = new JLabel("");
		lblImagenFondo.setIcon(new ImageIcon("2.jpg"));
		lblImagenFondo.setBounds(0, 0, 1008, 707);
		add(lblImagenFondo);

		this.setVisible(true);
	}
	
	public Modelo_Tabla_Proximos_Encuentros_CU20 getModeloTablaEncuentros() {
		return modeloTablaEncuentros;
	}

	public void setModeloTablaEncuentros(Modelo_Tabla_Proximos_Encuentros_CU20 modeloTablaEncuentros) {
		this.modeloTablaEncuentros = modeloTablaEncuentros;
	}
	
	
	
}
