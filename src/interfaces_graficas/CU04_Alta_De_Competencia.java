package interfaces_graficas;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JCheckBox;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import dominio.Competencia;
import dominio.Deporte;
import dominio.Lugar_De_Realizacion;
import excepciones.BaseDeDatosException;
import excepciones.CamposIncorrectosException;
import gestores.Gestor_Competencia;
import gestores.Gestor_Deporte;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import javax.swing.UIManager;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.awt.event.ActionEvent;


public class CU04_Alta_De_Competencia extends JPanel 
{
	private JTextField txtNombreDeLaCompetencia;
	private JTextField txtPuntosPorPartidoGanado;
	private JTextField txtPuntosPorPresentarse;
	private JTextField txtPuntosPorEmpate;
	private JTextField txtCantidadDeSets;
	private JTextField txtTantosOtorgadosPorAusenciaDeRival;
	private ButtonGroup g1 = new ButtonGroup();
	private String reglamento;
	private JLabel lblNombreDeLaCompetencia;
	private JLabel lblDeporteAsociado;
	private JComboBox<String> comboBoxDeporte;
	private JButton btnAgregarReglamento;
	private JLabel lblOpcional;
	private JLabel lblModalidadDeLaCompetencia;	
	private JComboBox<String> comboBoxModalidadDeLaCompetencia;
	private JLabel lblPuntosPorPartidoGanado;
	private JLabel lblPuntosPorPresentarse;
	private JButton btnAyuda;
	private JLabel lblTantosAOtorgarPorAusenciaDeRival;
	private JCheckBox checkBoxPermitirEmpate; 
	private JLabel lblPuntosPorPartidoEmpatado;
	private JLabel lblFormaDePuntuacion;
	private JButton btnCancelar;
	private JButton btnAceptar;
	private JRadioButton rdbtnSets;
	private JRadioButton rdbtnPuntuacion;
	private JRadioButton rdbtnResultadoFinal;
	private JLabel lblCantidadDeSets;
	private JLabel lblImagenFondo;
	private JButton btnAgregar;
	private JButton btnQuitar;
	private DefaultTableModel modeloTablaLugares;
	private JTable tablaLugares;
	private JScrollPane scrollPane;
	private List<Lugar_De_Realizacion> listaDeLugares;
	private Gestor_Competencia gestorCompetencia;
	private Gestor_Deporte gestorDeporte;
	private List<Deporte> deportes;
	private Integer cuil_usuario_logeado;
	
	public CU04_Alta_De_Competencia(JFrame pantallaPrincipal, Integer cuil_usuario_logeado) 
	{
		super();
		this.cuil_usuario_logeado = cuil_usuario_logeado;
		this.gestorCompetencia = new Gestor_Competencia();
		this.gestorDeporte = new Gestor_Deporte();
		this.deportes = gestorDeporte.listarTodos();
		this.reglamento = "";
		this.armarPanel(pantallaPrincipal);
	}
	
	public void armarPanel(JFrame pantallaPrincipal) 
	{
		setLayout(null);
		lblNombreDeLaCompetencia = new JLabel("Nombre de la Competencia (*)");
		lblNombreDeLaCompetencia.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNombreDeLaCompetencia.setBounds(40, 30, 211, 25);
		add(lblNombreDeLaCompetencia);
		
		txtNombreDeLaCompetencia = new JTextField(20);
		txtNombreDeLaCompetencia.addKeyListener(new KeyAdapter() 
		{
			@Override
			public void keyTyped(KeyEvent e) 
			{
				if(txtNombreDeLaCompetencia.getText().length()>19) 
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
		txtNombreDeLaCompetencia.setBounds(261, 30, 180, 25);
		add(txtNombreDeLaCompetencia);
		txtNombreDeLaCompetencia.setColumns(10);
		
		lblDeporteAsociado = new JLabel("Deporte Asociado (*)");
		lblDeporteAsociado.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblDeporteAsociado.setBounds(40, 66, 211, 25);
		add(lblDeporteAsociado);
		
		comboBoxDeporte = new JComboBox<String>();
		comboBoxDeporte.setBounds(261, 66, 180, 25);
		comboBoxDeporte.addItem("Seleccionar");
		for(Deporte d: deportes) 
		{
			comboBoxDeporte.addItem(d.getNombre());
		}
		comboBoxDeporte.addActionListener (new ActionListener () 
		{
		    public void actionPerformed(ActionEvent e) {
		    	modeloTablaLugares.setRowCount(0);
		    }
		});
		add(comboBoxDeporte);
		
		btnAgregarReglamento = new JButton("Agregar Reglamento");
		btnAgregarReglamento.addActionListener(e ->
		{
			
			CU04_Agregar_Reglamento pantallaAgregarReglamento = new CU04_Agregar_Reglamento(pantallaPrincipal,true,reglamento);
			pantallaAgregarReglamento.setLocationRelativeTo(null);
			pantallaAgregarReglamento.setVisible(true);
			reglamento = pantallaAgregarReglamento.getReglamento();
			
		});
		btnAgregarReglamento.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnAgregarReglamento.setBounds(502, 66, 170, 25);
		add(btnAgregarReglamento);
		
		lblOpcional = new JLabel("(Opcional)");
		lblOpcional.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblOpcional.setBounds(690, 71, 90, 14);
		add(lblOpcional);
		
		/*La tabla tiene un modelo personalizado, el mismo se encuentra dentro
		 * del paquete "modelos_de_tablas_interfaces_graficas"*/
		
		modeloTablaLugares = new DefaultTableModel(new Object[][] {}, new String[] {"ID Lugar", "Lugar de realización","Disponibilidad"}) 
		{
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int col) 
			{
				return (col!=0 && col!=1);
			}
		};

		tablaLugares = new JTable();
		tablaLugares.setFont(new Font("Tahoma", Font.PLAIN, 12));
		tablaLugares.setModel(modeloTablaLugares);
		scrollPane = new JScrollPane(tablaLugares);
		tablaLugares.setFillsViewportHeight(true);
		scrollPane.setBounds(40, 110, 827, 190);
		TableRowSorter<TableModel> orden =  new TableRowSorter<TableModel>(modeloTablaLugares);
		tablaLugares.setRowSorter(orden);
		//PONE INVISIBLE EL ID DEL LUGAR DE REALIZACION EN LA TABLA
		tablaLugares.getColumnModel().getColumn(0).setMinWidth(0);
		tablaLugares.getColumnModel().getColumn(0).setMaxWidth(0);
		tablaLugares.getColumnModel().getColumn(0).setWidth(0);
		add(scrollPane);
		
		
		btnAyuda = new JButton("Ayuda");
		btnAyuda.addActionListener(e ->
		{
			this.mostrarMensajeInformacion(pantallaPrincipal,"Ayuda", "Los Lugares de Realización son aquellos en los cuales se van a llevar a cabo los Encuentros." + "\n"
					+ "Para añadir un Lugar a la Competencia presione el botón Agregar." + "\n"
					+ "Para eliminar un Lugar, seleccione el mismo y presione el botón Quitar." + "\n");
		});
		btnAyuda.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnAyuda.setBounds(877, 192, 89, 30);
		add(btnAyuda);
		
		btnAgregar = new JButton("Agregar");
		btnAgregar.addActionListener(e->
		{
			List<String> listaDeLugaresYaAgregados = new ArrayList<String>();
			for(int i=0; i<tablaLugares.getRowCount();i++)
			{
				listaDeLugaresYaAgregados.add((String) tablaLugares.getValueAt(i, 1));
			}
			CU04_AgregarDisponibilidad pantallaLugares = new CU04_AgregarDisponibilidad(pantallaPrincipal,true,this,listaDeLugaresYaAgregados);
			pantallaLugares.setLocationRelativeTo(null);
			pantallaLugares.setVisible(true);
			listaDeLugares = pantallaLugares.getListaDeLugares();
			Lugar_De_Realizacion lugar = pantallaLugares.getLugar();
			String disponibilidad = pantallaLugares.getDisponibilidad();
			if(lugar != null && disponibilidad.length()!=0) 
			{
				modeloTablaLugares.addRow(new String[] {lugar.getId().toString(), lugar.getNombre(),disponibilidad});
			}
		});
		btnAgregar.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnAgregar.setBounds(877, 110, 89, 30);
		add(btnAgregar);
		
		btnQuitar = new JButton("Quitar");
		btnQuitar.addActionListener(e->{
			if(tablaLugares.getSelectedRow()!=-1) 
			{
				modeloTablaLugares.removeRow(tablaLugares.getSelectedRow());
			}
			else 
			{
				this.mostrarMensajeAdvertencia(pantallaPrincipal,"No seleccionó lugar.", "Debe seleccionar un Lugar antes de presionar Quitar.");
			}
		});
		btnQuitar.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnQuitar.setBounds(877, 151, 89, 30);
		add(btnQuitar);
		
		lblModalidadDeLaCompetencia = new JLabel("Modalidad de la Competencia (*)");
		lblModalidadDeLaCompetencia.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblModalidadDeLaCompetencia.setBounds(40, 320, 211, 25);
		add(lblModalidadDeLaCompetencia);
		
		comboBoxModalidadDeLaCompetencia = new JComboBox<String>();
		comboBoxModalidadDeLaCompetencia.setBounds(261, 321, 180, 25);
		comboBoxModalidadDeLaCompetencia.addItem("Seleccionar");
		comboBoxModalidadDeLaCompetencia.addItem("Liga");
		comboBoxModalidadDeLaCompetencia.addItem("Eliminación Simple");
		comboBoxModalidadDeLaCompetencia.addItem("Eliminación Doble");
		comboBoxModalidadDeLaCompetencia.addActionListener(e->{
			 this.accionCampoModalidad(e);
			});
		add(comboBoxModalidadDeLaCompetencia);
		
		lblPuntosPorPartidoGanado = new JLabel("Puntos por Partido Ganado (*)");
		lblPuntosPorPartidoGanado.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblPuntosPorPartidoGanado.setBounds(40, 356, 211, 25);
		add(lblPuntosPorPartidoGanado);
		
		txtPuntosPorPartidoGanado = new JTextField();
		txtPuntosPorPartidoGanado.setBounds(261, 357,90, 25);
		txtPuntosPorPartidoGanado.setEditable(false);
		txtPuntosPorPartidoGanado.addKeyListener(new KeyAdapter() 
		{
			@Override
			public void keyTyped(KeyEvent e) 
			{
				if(txtPuntosPorPartidoGanado.getText().length()>1) {
					e.consume();
				}
				
				char input = e.getKeyChar();
				if((input < '0' || input > '9') && input != '\b')
				{
					e.consume();
				}
			}
		});
		add(txtPuntosPorPartidoGanado);
		
		lblPuntosPorPresentarse = new JLabel("Puntos por Presentarse (*)");
		lblPuntosPorPresentarse.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblPuntosPorPresentarse.setBounds(40, 392, 211, 25);
		add(lblPuntosPorPresentarse);
		
		txtPuntosPorPresentarse = new JTextField();
		txtPuntosPorPresentarse.setColumns(10);
		txtPuntosPorPresentarse.setBounds(261, 392, 90, 25);
		txtPuntosPorPresentarse.setEditable(false);
		txtPuntosPorPresentarse.addKeyListener(new KeyAdapter() 
		{
			@Override
			public void keyTyped(KeyEvent e) 
			{
				if(txtPuntosPorPresentarse.getText().length()>1) {
					e.consume();
				}
				
				char input = e.getKeyChar();
				if((input < '0' || input > '9') && input != '\b')
				{
					e.consume();
				}
			}
		});
		add(txtPuntosPorPresentarse);
		
		checkBoxPermitirEmpate = new JCheckBox("Empate");
		checkBoxPermitirEmpate.setFont(new Font("Tahoma", Font.BOLD, 12));
		checkBoxPermitirEmpate.setContentAreaFilled(false);
		checkBoxPermitirEmpate.setBounds(445, 357, 90, 23);
		checkBoxPermitirEmpate.setEnabled(false);
		checkBoxPermitirEmpate.addActionListener(e ->
		{
			this.accionPermitirEmpate(e);
		});
		add(checkBoxPermitirEmpate);
		
		lblPuntosPorPartidoEmpatado = new JLabel("Puntos por Partido Empatado (*)");
		lblPuntosPorPartidoEmpatado.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblPuntosPorPartidoEmpatado.setBounds(541, 356, 216, 25);
		add(lblPuntosPorPartidoEmpatado);
		
		txtPuntosPorEmpate = new JTextField();
		txtPuntosPorEmpate.setColumns(10);
		txtPuntosPorEmpate.setBounds(767, 357, 90, 25);
		txtPuntosPorEmpate.setEditable(false);
		txtPuntosPorEmpate.addKeyListener(new KeyAdapter() 
		{
			@Override
			public void keyTyped(KeyEvent e) 
			{
				if(txtPuntosPorEmpate.getText().length()>1) 
				{
					e.consume();
				}
				
				char input = e.getKeyChar();
				if((input < '0' || input > '9') && input != '\b')
				{
					e.consume();
				}
			}
		});
		add(txtPuntosPorEmpate);
		
		lblFormaDePuntuacion = new JLabel("Seleccione una Forma de Puntuación: ");
		lblFormaDePuntuacion.setForeground(UIManager.getColor("Button.foreground"));
		lblFormaDePuntuacion.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblFormaDePuntuacion.setBounds(40, 447, 280, 25);
		add(lblFormaDePuntuacion);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(e->
		{
			int resultado = JOptionPane.showConfirmDialog(pantallaPrincipal, "Está seguro que desea cancelar?", "Confirmación", JOptionPane.YES_NO_OPTION);
			
			if(resultado == JOptionPane.YES_OPTION) 
			{
				this.setVisible(false);
				JPanel panelListarCompentencias = new CU03_Listar_Competencias_Deportivas(pantallaPrincipal, cuil_usuario_logeado);
				pantallaPrincipal.setContentPane(panelListarCompentencias);
				pantallaPrincipal.setTitle("LISTAR MIS COMPETENCIAS DEPORTIVAS");
			}

		});
		btnCancelar.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnCancelar.setBounds(907, 665, 89, 30);
		add(btnCancelar);
		btnAceptar = new JButton("Aceptar");
		btnAceptar.addActionListener(e->
		{
			try 
			{
				String nombreCompetencia = this.getTxtNombreDeLaCompetencia().getText();
				String nombreDeporte = this.getComboBoxDeporte().getSelectedItem().toString();
				String nombreModalidad = this.getComboBoxModalidadDeLaCompetencia().getSelectedItem().toString();
				Integer puntosPorPartidoGanado = null;
				if(!this.getTxtPuntosPorPartidoGanado().getText().isEmpty())
				{
					puntosPorPartidoGanado = Integer.parseInt(this.getTxtPuntosPorPartidoGanado().getText());
				}
				boolean empate = this.getCheckBoxPermitirEmpate().isSelected();
				Integer puntosPorPartidoEmpatado = null;
				if(!this.getTxtPuntosPorEmpate().getText().isEmpty())
				{
					puntosPorPartidoEmpatado = Integer.parseInt(this.getTxtPuntosPorEmpate().getText());
				}
				Integer puntosPorPresentarse = null;
				if(!this.getTxtPuntosPorPresentarse().getText().isEmpty())
				{
					puntosPorPresentarse = Integer.parseInt(this.getTxtPuntosPorPresentarse().getText());
				}
				boolean sets = this.getRdbtnSets().isSelected();
				Integer cantidadDeSets = null;
				if(!this.getTxtCantidadDeSets().getText().isEmpty())
				{
					cantidadDeSets = Integer.parseInt(this.getTxtCantidadDeSets().getText());
				}
				boolean puntuacion = this.getRdbtnPuntuacion().isSelected();
				Integer tantosOtorgadosPorAusenciaDelRival = null;
				if(!this.getTxtTantosOtorgadosPorAusenciaDeRival().getText().isEmpty())
				{
					tantosOtorgadosPorAusenciaDelRival = Integer.parseInt(this.getTxtTantosOtorgadosPorAusenciaDeRival().getText());
				}
				boolean resultado_final = this.getRdbtnResultadoFinal().isSelected();
				Competencia nuevaCompetencia = gestorCompetencia.crearCompetencia(nombreCompetencia, nombreDeporte, reglamento, tablaLugares,
						nombreModalidad, puntosPorPartidoGanado, empate, puntosPorPartidoEmpatado, puntosPorPresentarse,
						sets, cantidadDeSets, puntuacion, tantosOtorgadosPorAusenciaDelRival, resultado_final, cuil_usuario_logeado);
				this.accionMarcarCamposCorrectos();
				this.limpiarFormulario();
				this.mostrarMensajeInformacion(pantallaPrincipal,"Exito", "La competencia se ha creado exitosamente");
				this.setVisible(false);
				JPanel panelAgregarParticipante = new CU08_Listar_Participantes_Competencia(pantallaPrincipal, nuevaCompetencia.getId(), cuil_usuario_logeado);
				pantallaPrincipal.setContentPane(panelAgregarParticipante);
				pantallaPrincipal.setTitle("LISTAR PARTICIPANTES DE COMPETENCIA DEPORTIVA");
			} 
			catch (SQLException | BaseDeDatosException e1) 
			{
				e1.printStackTrace();
				this.mostrarMensajeError(pantallaPrincipal,"Error al registrar la competencia",e1.getMessage());
			}
			catch (CamposIncorrectosException e2)
			{
				e2.printStackTrace();
				this.accionMarcarCamposCorrectos();
				this.accionMarcarCamposErroneos(e2.getCamposErroneos());
				this.mostrarMensajeError(pantallaPrincipal,"Error al registrar la competencia",e2.getMessage());
			}
			
		});
		btnAceptar.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnAceptar.setBounds(806, 665, 89, 30);
		add(btnAceptar);
		
		rdbtnSets = new JRadioButton("Sets");
		rdbtnSets.setContentAreaFilled(false);
		rdbtnSets.setFont(new Font("Tahoma", Font.BOLD, 12));
		rdbtnSets.setBounds(218, 485, 150, 25);
		g1.add(rdbtnSets);
		rdbtnSets.addActionListener(e ->
		{
			txtCantidadDeSets.setEditable(true);
			txtCantidadDeSets.repaint();
			txtTantosOtorgadosPorAusenciaDeRival.setEditable(false);
			txtTantosOtorgadosPorAusenciaDeRival.repaint();
			txtTantosOtorgadosPorAusenciaDeRival.setText("");
		});
		add(rdbtnSets);
		
		rdbtnPuntuacion = new JRadioButton("Puntuación");
		rdbtnPuntuacion.setFont(new Font("Tahoma", Font.BOLD, 12));
		rdbtnPuntuacion.setContentAreaFilled(false);
		rdbtnPuntuacion.setBounds(218, 531, 150, 25);
		g1.add(rdbtnPuntuacion);
		rdbtnPuntuacion.addActionListener(e ->
		{
			txtTantosOtorgadosPorAusenciaDeRival.setEditable(true);
			txtTantosOtorgadosPorAusenciaDeRival.repaint();
			txtCantidadDeSets.setEditable(false);
			txtCantidadDeSets.repaint();
			txtCantidadDeSets.setText("");
		});
		add(rdbtnPuntuacion);
		
		rdbtnResultadoFinal = new JRadioButton("Resultado Final");
		rdbtnResultadoFinal.setFont(new Font("Tahoma", Font.BOLD, 12));
		rdbtnResultadoFinal.setContentAreaFilled(false);
		rdbtnResultadoFinal.setBounds(218, 578, 150, 25);
		g1.add(rdbtnResultadoFinal);
		rdbtnResultadoFinal.addActionListener(e ->
		{
			txtTantosOtorgadosPorAusenciaDeRival.setEditable(false);
			txtTantosOtorgadosPorAusenciaDeRival.repaint();
			txtTantosOtorgadosPorAusenciaDeRival.setText("");
			txtCantidadDeSets.setEditable(false);
			txtCantidadDeSets.repaint();
		});
		add(rdbtnResultadoFinal);
		
		lblCantidadDeSets = new JLabel("Cantidad de Sets (*)");
		lblCantidadDeSets.setHorizontalAlignment(SwingConstants.LEFT);
		lblCantidadDeSets.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblCantidadDeSets.setBounds(435, 485, 290, 25);
		add(lblCantidadDeSets);
		
		txtCantidadDeSets = new JTextField();
		txtCantidadDeSets.setBounds(767, 486,90, 25);
		add(txtCantidadDeSets);
		txtCantidadDeSets.setEditable(false);
		txtCantidadDeSets.addKeyListener(new KeyAdapter() 
		{
			@Override
			public void keyTyped(KeyEvent e) 
			{
				if(txtCantidadDeSets.getText().length()>1) {
					e.consume();
				}
				
				char input = e.getKeyChar();
				if((input < '0' || input > '9') && input != '\b')
				{
					e.consume();
				}
			}
		});
		txtCantidadDeSets.setColumns(10);
		
		lblTantosAOtorgarPorAusenciaDeRival = new JLabel("Tantos Otorgados por Ausencia del Rival (*)");
		lblTantosAOtorgarPorAusenciaDeRival.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblTantosAOtorgarPorAusenciaDeRival.setBounds(435, 531, 290, 25);
		add(lblTantosAOtorgarPorAusenciaDeRival);
		
		txtTantosOtorgadosPorAusenciaDeRival = new JTextField();
		txtTantosOtorgadosPorAusenciaDeRival.setColumns(10);
		txtTantosOtorgadosPorAusenciaDeRival.setBounds(767, 532, 90, 25);
		txtTantosOtorgadosPorAusenciaDeRival.setEditable(false);
		txtTantosOtorgadosPorAusenciaDeRival.addKeyListener(new KeyAdapter() 
		{
			@Override
			public void keyTyped(KeyEvent e) 
			{
				if(txtTantosOtorgadosPorAusenciaDeRival.getText().length()>1) {
					e.consume();
				}
				
				char input = e.getKeyChar();
				if((input < '0' || input > '9') && input != '\b')
				{
					e.consume();
				}
			}
		});
		add(txtTantosOtorgadosPorAusenciaDeRival);
		
		lblImagenFondo = new JLabel("");
		lblImagenFondo.setIcon(new ImageIcon("2.jpg"));
		lblImagenFondo.setBounds(0, 0, 1008, 707);
		add(lblImagenFondo);
		
		this.setVisible(true);

	}
	
	private void limpiarFormulario() 
	{
		this.txtNombreDeLaCompetencia.setText("");
		this.comboBoxDeporte.setSelectedIndex(0);
		this.reglamento = "";
		modeloTablaLugares.setRowCount(0);
		this.comboBoxModalidadDeLaCompetencia.setSelectedIndex(0);
		this.txtPuntosPorEmpate.setText("");
		this.txtPuntosPorPartidoGanado.setText("");
		this.txtPuntosPorPresentarse.setText("");
		this.txtCantidadDeSets.setText("");
		this.txtTantosOtorgadosPorAusenciaDeRival.setText("");
		this.g1.clearSelection();
		this.txtCantidadDeSets.setEditable(false);
		this.txtTantosOtorgadosPorAusenciaDeRival.setEditable(false);
		this.checkBoxPermitirEmpate.setSelected(false);

	}
	
	public void accionMarcarCamposCorrectos() 
	{
				this.lblNombreDeLaCompetencia.setForeground(Color.BLACK);
				this.lblDeporteAsociado.setForeground(Color.BLACK);
				this.lblModalidadDeLaCompetencia.setForeground(Color.BLACK);
				this.lblPuntosPorPartidoGanado.setForeground(Color.BLACK);
				this.lblPuntosPorPartidoEmpatado.setForeground(Color.BLACK);
				this.lblPuntosPorPresentarse.setForeground(Color.BLACK);
				this.lblFormaDePuntuacion.setForeground(Color.BLACK);
				this.lblCantidadDeSets.setForeground(Color.BLACK);
				this.lblTantosAOtorgarPorAusenciaDeRival.setForeground(Color.BLACK);
	}

	
	public void accionMarcarCamposErroneos(List<String> nombresDeCampos) 
	{
		
		for(String c: nombresDeCampos) 
		{
			switch (c) 
			{
			case "Nombre":
				this.lblNombreDeLaCompetencia.setForeground(Color.RED);
				break;
			case "Deporte":
				this.lblDeporteAsociado.setForeground(Color.RED);
				break;
			case "Modalidad":
				this.lblModalidadDeLaCompetencia.setForeground(Color.RED);
				break;
			case "PuntosPG":
				this.lblPuntosPorPartidoGanado.setForeground(Color.RED);
				break;
			case "PuntosPE":
				this.lblPuntosPorPartidoEmpatado.setForeground(Color.RED);
				break;
			case "PuntosPP":
				this.lblPuntosPorPresentarse.setForeground(Color.RED);
				break;
			case "Forma de Puntuacion":
				this.lblFormaDePuntuacion.setForeground(Color.RED);
				break;
			case "Cantidad Sets":
				this.lblCantidadDeSets.setForeground(Color.RED);
				break;
			case "Tantos por Ausencia":
				this.lblTantosAOtorgarPorAusenciaDeRival.setForeground(Color.RED);
				break;
			}
		}

	}
	
	
    private void accionPermitirEmpate(ActionEvent e) 
    {
    	
    	if(checkBoxPermitirEmpate.isSelected()==true)
    		txtPuntosPorEmpate.setEditable(true);
    	else
    		txtPuntosPorEmpate.setEditable(false);
    		txtPuntosPorEmpate.setText("");
    }
    
    /*esta funcion lo que hacer es que cuando le selecciona la modalidad que no sea liga muestre un 
     * mensaje de que no estan disponibles
     * si no selecciona nada, no permite rellenar los campos puntos por partido ganado, por presentarse y empate*/
    
    private void accionCampoModalidad(ActionEvent e) 
    {
    	if(comboBoxModalidadDeLaCompetencia.getSelectedItem().equals("Eliminación Simple") || comboBoxModalidadDeLaCompetencia.getSelectedItem().equals("Eliminación Doble"))
    	{
    		txtPuntosPorPartidoGanado.setEditable(false);
    		txtPuntosPorPresentarse.setEditable(false);
    		checkBoxPermitirEmpate.setEnabled(false);
    		checkBoxPermitirEmpate.setSelected(false);
    		txtPuntosPorEmpate.setEditable(false);
    		txtPuntosPorPartidoGanado.setText("");
    		txtPuntosPorPresentarse.setText("");
    		txtPuntosPorEmpate.setText("");
    	}
    	if(comboBoxModalidadDeLaCompetencia.getSelectedItem().equals("Seleccionar")) 
    	{
    		txtPuntosPorPartidoGanado.setEditable(false);
    		txtPuntosPorPresentarse.setEditable(false);
    		checkBoxPermitirEmpate.setEnabled(false);
    		checkBoxPermitirEmpate.setSelected(false);
    		txtPuntosPorEmpate.setEditable(false);
    		txtPuntosPorPartidoGanado.setText("");
    		txtPuntosPorPresentarse.setText("");
    		txtPuntosPorEmpate.setText("");
    	}
    	if(comboBoxModalidadDeLaCompetencia.getSelectedItem().equals("Liga")) 
    	{
    		txtPuntosPorPartidoGanado.setEditable(true);
    		txtPuntosPorPresentarse.setEditable(true);
    		checkBoxPermitirEmpate.setEnabled(true);
    	}
    }
	
	public void mostrarMensajeError(JFrame padre, String titulo,String detalle) 
	{
		JOptionPane.showMessageDialog(padre,
			    detalle,titulo,
			    JOptionPane.ERROR_MESSAGE);
	}
	
	public void mostrarMensajeAdvertencia(JFrame padre, String titulo,String detalle) 
	{
		JOptionPane.showMessageDialog(padre,
			    detalle,titulo,
			    JOptionPane.WARNING_MESSAGE);
	}
	public void mostrarMensajeInformacion(JFrame padre, String titulo,String detalle) 
	{
		JOptionPane.showMessageDialog(padre,
			    detalle,titulo,
			    JOptionPane.INFORMATION_MESSAGE);
	}
	
	public void marcarCamposErroneos(List<String> campos) 
	{
		for(String c: campos) 
		{
			switch (c) 
			{
			case "Nombre":
				this.lblNombreDeLaCompetencia.setForeground(Color.RED);
				break;
			default:
				break;
			}
		}
	}

	public JTextField getTxtNombreDeLaCompetencia() 
	{
		return txtNombreDeLaCompetencia;
	}

	public void setTxtNombreDeLaCompetencia(JTextField txtNombreDeLaCompetencia) 
	{
		this.txtNombreDeLaCompetencia = txtNombreDeLaCompetencia;
	}

	public JTextField getTxtPuntosPorPartidoGanado() 
	{
		return txtPuntosPorPartidoGanado;
	}

	public void setTxtPuntosPorPartidoGanado(JTextField txtPuntosPorPartidoGanado) 
	{
		this.txtPuntosPorPartidoGanado = txtPuntosPorPartidoGanado;
	}

	public JTextField getTxtPuntosPorPresentarse() 
	{
		return txtPuntosPorPresentarse;
	}

	public void setTxtPuntosPorPresentarse(JTextField txtPuntosPorPresentarse) 
	{
		this.txtPuntosPorPresentarse = txtPuntosPorPresentarse;
	}

	public JTextField getTxtPuntosPorEmpate() 
	{
		return txtPuntosPorEmpate;
	}

	public void setTxtPuntosPorEmpate(JTextField txtPuntosPorEmpate) 
	{
		this.txtPuntosPorEmpate = txtPuntosPorEmpate;
	}

	public JTextField getTxtCantidadDeSets() 
	{
		return txtCantidadDeSets;
	}

	public void setTxtCantidadDeSets(JTextField txtCantidadDeSets) 
	{
		this.txtCantidadDeSets = txtCantidadDeSets;
	}

	public JTextField getTxtTantosOtorgadosPorAusenciaDeRival() 
	{
		return txtTantosOtorgadosPorAusenciaDeRival;
	}

	public void setTxtTantosOtorgadosPorAusenciaDeRival(JTextField txtTantosOtorgadosPorAusenciaDeRival) 
	{
		this.txtTantosOtorgadosPorAusenciaDeRival = txtTantosOtorgadosPorAusenciaDeRival;
	}

	public ButtonGroup getG1() 
	{
		return g1;
	}

	public void setG1(ButtonGroup g1) 
	{
		this.g1 = g1;
	}

	public String getReglamento() 
	{
		return reglamento;
	}

	public void setReglamento(String reglamento) 
	{
		this.reglamento = reglamento;
	}

	public JLabel getLblNombreDeLaCompetencia() 
	{
		return lblNombreDeLaCompetencia;
	}

	public void setLblNombreDeLaCompetencia(JLabel lblNombreDeLaCompetencia) 
	{
		this.lblNombreDeLaCompetencia = lblNombreDeLaCompetencia;
	}

	public JLabel getLblDeporteAsociado() 
	{
		return lblDeporteAsociado;
	}

	public void setLblDeporteAsociado(JLabel lblDeporteAsociado) 
	{
		this.lblDeporteAsociado = lblDeporteAsociado;
	}

	public JComboBox<String> getComboBoxDeporte() 
	{
		return comboBoxDeporte;
	}

	public void setComboBoxDeporte(JComboBox<String> comboBoxDeporte) 
	{
		this.comboBoxDeporte = comboBoxDeporte;
	}

	public JButton getBtnAgregarReglamento() 
	{
		return btnAgregarReglamento;
	}

	public void setBtnAgregarReglamento(JButton btnAgregarReglamento) 
	{
		this.btnAgregarReglamento = btnAgregarReglamento;
	}

	public JLabel getLblOpcional() 
	{
		return lblOpcional;
	}

	public void setLblOpcional(JLabel lblOpcional) 
	{
		this.lblOpcional = lblOpcional;
	}

	public JLabel getLblModalidadDeLaCompetencia() 
	{
		return lblModalidadDeLaCompetencia;
	}

	public void setLblModalidadDeLaCompetencia(JLabel lblModalidadDeLaCompetencia) 
	{
		this.lblModalidadDeLaCompetencia = lblModalidadDeLaCompetencia;
	}

	public JComboBox<String> getComboBoxModalidadDeLaCompetencia() 
	{
		return comboBoxModalidadDeLaCompetencia;
	}

	public void setComboBoxModalidadDeLaCompetencia(JComboBox<String> comboBoxModalidadDeLaCompetencia) 
	{
		this.comboBoxModalidadDeLaCompetencia = comboBoxModalidadDeLaCompetencia;
	}

	public JLabel getLblPuntosPorPartidoGanado() 
	{
		return lblPuntosPorPartidoGanado;
	}

	public void setLblPuntosPorPartidoGanado(JLabel lblPuntosPorPartidoGanado) 
	{
		this.lblPuntosPorPartidoGanado = lblPuntosPorPartidoGanado;
	}

	public JLabel getLblPuntosPorPresentarse() 
	{
		return lblPuntosPorPresentarse;
	}

	public void setLblPuntosPorPresentarse(JLabel lblPuntosPorPresentarse) 
	{
		this.lblPuntosPorPresentarse = lblPuntosPorPresentarse;
	}

	public JButton getBtnAyuda() 
	{
		return btnAyuda;
	}

	public void setBtnAyuda(JButton btnAyuda) 
	{
		this.btnAyuda = btnAyuda;
	}

	public JLabel getLblTantosAOtorgarPorAusenciaDeRival() 
	{
		return lblTantosAOtorgarPorAusenciaDeRival;
	}

	public void setLblTantosAOtorgarPorAusenciaDeRival(JLabel lblTantosAOtorgarPorAusenciaDeRival) 
	{
		this.lblTantosAOtorgarPorAusenciaDeRival = lblTantosAOtorgarPorAusenciaDeRival;
	}

	public JCheckBox getCheckBoxPermitirEmpate() 
	{
		return checkBoxPermitirEmpate;
	}

	public void setCheckBoxPermitirEmpate(JCheckBox checkBoxPermitirEmpate) 
	{
		this.checkBoxPermitirEmpate = checkBoxPermitirEmpate;
	}

	public JLabel getLblPuntosPorPartidoEmpatado() 
	{
		return lblPuntosPorPartidoEmpatado;
	}

	public void setLblPuntosPorPartidoEmpatado(JLabel lblPuntosPorPartidoEmpatado) 
	{
		this.lblPuntosPorPartidoEmpatado = lblPuntosPorPartidoEmpatado;
	}

	public JLabel getLblFormaDePuntuacion() 
	{
		return lblFormaDePuntuacion;
	}

	public void setLblFormaDePuntuacion(JLabel lblFormaDePuntuacion) 
	{
		this.lblFormaDePuntuacion = lblFormaDePuntuacion;
	}

	public JButton getBtnCancelar() 
	{
		return btnCancelar;
	}

	public void setBtnCancelar(JButton btnCancelar) 
	{
		this.btnCancelar = btnCancelar;
	}

	public JButton getBtnAceptar() 
	{
		return btnAceptar;
	}

	public void setBtnAceptar(JButton btnAceptar) 
	{
		this.btnAceptar = btnAceptar;
	}

	public JRadioButton getRdbtnSets() 
	{
		return rdbtnSets;
	}

	public void setRdbtnSets(JRadioButton rdbtnSets) 
	{
		this.rdbtnSets = rdbtnSets;
	}

	public JRadioButton getRdbtnPuntuacion() 
	{
		return rdbtnPuntuacion;
	}

	public void setRdbtnPuntuacion(JRadioButton rdbtnPuntuacion) 
	{
		this.rdbtnPuntuacion = rdbtnPuntuacion;
	}

	public JRadioButton getRdbtnResultadoFinal() 
	{
		return rdbtnResultadoFinal;
	}

	public void setRdbtnResultadoFinal(JRadioButton rdbtnResultadoFinal) 
	{
		this.rdbtnResultadoFinal = rdbtnResultadoFinal;
	}

	public JLabel getLblCantidadDeSets() 
	{
		return lblCantidadDeSets;
	}

	public void setLblCantidadDeSets(JLabel lblCantidadDeSets) 
	{
		this.lblCantidadDeSets = lblCantidadDeSets;
	}

	public JLabel getLblImagenFondo() {
		return lblImagenFondo;
	}

	public void setLblImagenFondo(JLabel lblImagenFondo) {
		this.lblImagenFondo = lblImagenFondo;
	}

	public JButton getBtnAgregar() {
		return btnAgregar;
	}

	public void setBtnAgregar(JButton btnAgregar) {
		this.btnAgregar = btnAgregar;
	}

	public JButton getBtnQuitar() {
		return btnQuitar;
	}

	public void setBtnQuitar(JButton btnQuitar) {
		this.btnQuitar = btnQuitar;
	}

	public DefaultTableModel getModeloTablaLugares() {
		return modeloTablaLugares;
	}

	public void setModeloTablaLugares(DefaultTableModel modeloTablaLugares) {
		this.modeloTablaLugares = modeloTablaLugares;
	}

	public JTable getTablaLugares() {
		return tablaLugares;
	}

	public void setTablaLugares(JTable tablaLugares) {
		this.tablaLugares = tablaLugares;
	}

	public JScrollPane getScrollPane() {
		return scrollPane;
	}

	public void setScrollPane(JScrollPane scrollPane) {
		this.scrollPane = scrollPane;
	}

	public List<Lugar_De_Realizacion> getListaDeLugares() {
		return listaDeLugares;
	}

	public void setListaDeLugares(List<Lugar_De_Realizacion> listaDeLugares) {
		this.listaDeLugares = listaDeLugares;
	}

	
}
