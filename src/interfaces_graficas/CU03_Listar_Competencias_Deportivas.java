package interfaces_graficas;

import javax.swing.JPanel;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import java.util.LinkedList;
import java.util.List;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import dominio.Competencia;
import dominio.Deporte;
import gestores.Gestor_Competencia;
import gestores.Gestor_Deporte;
import modelos_de_tablas_interfaces_graficas.Modelo_Tabla_Listar_Competencias_CU03;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.ImageIcon;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class CU03_Listar_Competencias_Deportivas extends JPanel 
{
	
	private JTextField txtNombreCompetencia;
	private JLabel lblNombreDeLaCompetencia;
	private JLabel lblModalidaDeLaCompetencia;
	private JComboBox<String> comboBoxModalidad;
	private JComboBox<String> comboBoxDeporteAsociado;
	private JLabel lblDeporteAsociado;
	private JLabel lblEstado;
	private JComboBox<String> comboBoxEstado;
	private JButton btnBuscar;
	private JButton btnLimpiarFiltro;
	private Modelo_Tabla_Listar_Competencias_CU03 modeloTablaCompetencias;
	private JTable tablaCompetencias;
	private JButton btnVerCompetencia;
	private JButton btnCrearCompetencia;
	private JButton btnVolver;
	private JLabel lblImagenFondo;
	private List<Competencia> listaDeCompetencias;
	private List<Deporte> deportes;
	private Gestor_Competencia gestorCompetencia;
	private Gestor_Deporte gestorDeporte;
	private Integer cuil_usuario_logeado;
	
	
	
	public CU03_Listar_Competencias_Deportivas(JFrame pantallaPrincipal, Integer cuil_usuario_logeado) 
	{
		super();
		this.gestorCompetencia = new Gestor_Competencia();
		this.gestorDeporte = new Gestor_Deporte();
		long now = System.currentTimeMillis();
		this.cuil_usuario_logeado = cuil_usuario_logeado;
		this.listaDeCompetencias = gestorCompetencia.listarMisCompetencias(cuil_usuario_logeado);
		System.out.println("Latencia LISTAR COMPETENCIAS: " + (System.currentTimeMillis() -now)+ "ms \n");
		this.deportes = gestorDeporte.listarTodos();
		this.armarPanel(pantallaPrincipal);
	}

	public void armarPanel(JFrame pantallaPrincipal) 
	{
		setLayout(null);
		lblNombreDeLaCompetencia = new JLabel("Nombre de la Competencia");
		lblNombreDeLaCompetencia.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNombreDeLaCompetencia.setBounds(40, 40, 200, 25);
		add(lblNombreDeLaCompetencia);
		
		txtNombreCompetencia = new JTextField();
		txtNombreCompetencia.addKeyListener(new KeyAdapter() 
		{
			@Override
			public void keyTyped(KeyEvent e) 
			{
				
				if(txtNombreCompetencia.getText().length()>20) 
				{
					e.consume();
				}
				
				char c= e.getKeyChar();
				if(Character.isLowerCase(c)) 
				{
					String cad = (""+c).toUpperCase();
					c=cad.charAt(0);
					e.setKeyChar(c);
				}
				
			}
		});
		txtNombreCompetencia.setBounds(250, 41, 180, 25);
		add(txtNombreCompetencia);
		txtNombreCompetencia.setColumns(10);
		
		lblModalidaDeLaCompetencia = new JLabel("Modalidad de la Competencia");
		lblModalidaDeLaCompetencia.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblModalidaDeLaCompetencia.setBounds(40, 76, 200, 25);
		add(lblModalidaDeLaCompetencia);
		
		comboBoxModalidad = new JComboBox<String>();
		comboBoxModalidad.setBounds(250, 77, 180, 25);
		comboBoxModalidad.addItem("Seleccionar");
		comboBoxModalidad.addItem("Liga");
		comboBoxModalidad.addItem("Eliminación Simple");
		comboBoxModalidad.addItem("Eliminación Doble");
		add(comboBoxModalidad);
		
		comboBoxDeporteAsociado = new JComboBox<String>();
		comboBoxDeporteAsociado.setBounds(788, 41, 180, 25);
		comboBoxDeporteAsociado.addItem("Seleccionar");
		for(Deporte d: deportes) 
		{
			comboBoxDeporteAsociado.addItem(d.getNombre());
		}
		add(comboBoxDeporteAsociado);
		
		lblDeporteAsociado = new JLabel("Deporte Asociado a Competencia");
		lblDeporteAsociado.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblDeporteAsociado.setBounds(568, 40, 210, 25);
		add(lblDeporteAsociado);
		
		lblEstado = new JLabel("Estado de la Competencia");
		lblEstado.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblEstado.setBounds(568, 76, 210, 25);
		add(lblEstado);
		
		comboBoxEstado = new JComboBox<String>();
		comboBoxEstado.setBounds(788, 78, 180, 25);
		comboBoxEstado.addItem("Seleccionar");
		comboBoxEstado.addItem("CREADA");
		comboBoxEstado.addItem("PLANIFICADA");
		comboBoxEstado.addItem("EN DISPUTA");
		comboBoxEstado.addItem("FINALIZADA");
		add(comboBoxEstado);
		
		
		modeloTablaCompetencias = new Modelo_Tabla_Listar_Competencias_CU03(listaDeCompetencias);
		tablaCompetencias = new JTable();
		tablaCompetencias.setFont(new Font("Tahoma", Font.PLAIN, 12));
		tablaCompetencias.setModel(modeloTablaCompetencias);
		JScrollPane scrollPane = new JScrollPane(tablaCompetencias);
		tablaCompetencias.setFillsViewportHeight(true);
		scrollPane.setBounds(40, 190, 928, 370);
		TableRowSorter<TableModel> orden =  new TableRowSorter<TableModel>(modeloTablaCompetencias);
		tablaCompetencias.setRowSorter(orden);
		add(scrollPane);
		
		btnBuscar = new JButton("Buscar");
		btnBuscar.addActionListener(e->
		{
			
			
			if(txtNombreCompetencia.getText().isEmpty() && comboBoxDeporteAsociado.getSelectedIndex()==0 && comboBoxEstado.getSelectedIndex()==0
					&& comboBoxModalidad.getSelectedIndex()==0) {
				this.mostrarMensajeAdvertencia(pantallaPrincipal, "Campos incompletos", "Debe ingresar al menos un filtro de búsqueda.");
			}
			else {
				
				LinkedList<RowFilter> listaDeFiltros = new LinkedList<RowFilter>();
				
				if(!txtNombreCompetencia.getText().isEmpty()) {
					
					listaDeFiltros.add(RowFilter.regexFilter(txtNombreCompetencia.getText().toString(),0));
					
				}
				
				if(comboBoxDeporteAsociado.getSelectedIndex()!=0) {
					
					listaDeFiltros.add(RowFilter.regexFilter(comboBoxDeporteAsociado.getSelectedItem().toString(),1));
					
				}
				
				if(comboBoxModalidad.getSelectedIndex()!=0) {
					
					listaDeFiltros.add(RowFilter.regexFilter(comboBoxModalidad.getSelectedItem().toString(),2));
					
				}
				
				if(comboBoxEstado.getSelectedIndex()!=0) {
					
					listaDeFiltros.add(RowFilter.regexFilter(comboBoxEstado.getSelectedItem().toString(),3));
					
				}
				
				if(listaDeFiltros.size()!=0) {
					
					orden.setRowFilter(RowFilter.andFilter((Iterable)listaDeFiltros));
					
				}
				
			}
		});
		
		btnBuscar.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnBuscar.setBounds(389, 133, 100, 30);
		add(btnBuscar);
		
		btnLimpiarFiltro = new JButton("Limpiar Filtros");
		btnLimpiarFiltro.addActionListener(e->
		{
			
			this.txtNombreCompetencia.setText("");
			this.comboBoxDeporteAsociado.setSelectedIndex(0);
			this.comboBoxModalidad.setSelectedIndex(0);
			this.comboBoxEstado.setSelectedIndex(0);
			
			orden.setRowFilter(RowFilter.regexFilter("",0));
			
		});
		btnLimpiarFiltro.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnLimpiarFiltro.setBounds(499, 133, 120, 30);
		add(btnLimpiarFiltro);
		
		btnVerCompetencia = new JButton("Ver Competencia");
		btnVerCompetencia.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnVerCompetencia.setBounds(40, 571, 180, 30);
		btnVerCompetencia.addActionListener(e ->{
				if(tablaCompetencias.getSelectedRow() != -1) {
			    Integer id_Competencia = modeloTablaCompetencias.obtenerIdCompetencia(tablaCompetencias.getSelectedRow());
				setVisible(false);
				JPanel panelVerCompetencia = new CU20_Ver_Competencia(pantallaPrincipal, id_Competencia);
				pantallaPrincipal.setContentPane(panelVerCompetencia);
				pantallaPrincipal.setTitle("VER COMPETENCIA");
				}
				else {
					this.mostrarMensajeAdvertencia(pantallaPrincipal, "No seleccionó ninguna competencia.", "Debe seleccionar una competencia"+"\n"+"para ver los detalles de la misma.");
				}
		});
		add(btnVerCompetencia);
		
		btnCrearCompetencia = new JButton("Crear Competencia");
		btnCrearCompetencia.addActionListener(e->
		{
			this.setVisible(false);
			JPanel panelCrearCompetencia = new CU04_Alta_De_Competencia(pantallaPrincipal, cuil_usuario_logeado);
			pantallaPrincipal.setContentPane(panelCrearCompetencia);
			pantallaPrincipal.setTitle("DAR DE ALTA NUEVA COMPETENCIA");
		});
		btnCrearCompetencia.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnCrearCompetencia.setBounds(230, 571, 180, 30);
		add(btnCrearCompetencia);
		
		btnVolver = new JButton("Volver");
		btnVolver.addActionListener(e->
		{
			this.setVisible(false);
			JPanel panelMenuPrencipal = new Menu_Principal(pantallaPrincipal, cuil_usuario_logeado);
			pantallaPrincipal.setContentPane(panelMenuPrencipal);
			pantallaPrincipal.setTitle("MENU PRINCIPAL");
		});
		btnVolver.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnVolver.setBounds(907, 665, 89, 30);
		add(btnVolver);
		
		lblImagenFondo = new JLabel("");
		lblImagenFondo.setIcon(new ImageIcon("2.jpg"));
		lblImagenFondo.setBounds(0, 0, 1008, 707);
		add(lblImagenFondo);
		
		this.setVisible(true);

	}
	
	public void mostrarMensajeAdvertencia(JFrame padre ,String titulo,String detalle) 
	{
		JOptionPane.showMessageDialog(padre,
			    detalle,titulo,
			    JOptionPane.WARNING_MESSAGE);
	}
	
	public void mostrarMensajeInformativo(JFrame padre ,String titulo,String detalle) 
	{
		JOptionPane.showMessageDialog(padre,
			    detalle,titulo,
			    JOptionPane.INFORMATION_MESSAGE);
	}
}
