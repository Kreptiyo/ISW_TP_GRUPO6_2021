package interfaces_graficas;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.Font;
import java.awt.Frame;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import dominio.Lugar_De_Realizacion;
import gestores.Gestor_Lugar_De_Realizacion;
import java.awt.SystemColor;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComboBox;

public class CU04_AgregarDisponibilidad extends JDialog {
	
	private JLabel lblLugar;
	private JTextField txtDisponibilidad;
	private JComboBox<Lugar_De_Realizacion> comboBoxLugares;
	private JLabel lblDisponibilidad;
	private JLabel lblAyuda;
	private JLabel lblAyudaContinuacion;
	private JButton btnAceptar;
	private JButton btnCancelar;
	private List<Lugar_De_Realizacion> listaDeLugaresParaDeporteSeleccionado;
	private Gestor_Lugar_De_Realizacion gestorLugar;
	
	public CU04_AgregarDisponibilidad(Frame padre, boolean modal, CU04_Alta_De_Competencia p, List<String> listaDeLugaresYaIngresados) 
	{
		super(padre,modal);
		this.gestorLugar = new Gestor_Lugar_De_Realizacion();
		this.armarPanel(p,listaDeLugaresYaIngresados);
	}

	private void armarPanel(CU04_Alta_De_Competencia panel, List<String> listaDeLugaresIngresados) 
	{
		
		this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		
		this.setTitle("Agregar Lugar de Realización");
		
		/*Obtengo el nombre del deporte que selecciono para buscar los lugares que tienen asociado ese deporte*/
		String nombreDeporte = panel.getComboBoxDeporte().getSelectedItem().toString();
		
		listaDeLugaresParaDeporteSeleccionado = gestorLugar.buscarLugaresPorDeporte(nombreDeporte);
		
		getContentPane().setBackground(SystemColor.scrollbar);
		setBounds(100, 100, 550, 350);
		getContentPane().setLayout(null);
		
		lblLugar = new JLabel("Lugar (*)");
		lblLugar.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblLugar.setBounds(147, 79, 100, 25);
		getContentPane().add(lblLugar);
		
		comboBoxLugares = new JComboBox<Lugar_De_Realizacion>();
		comboBoxLugares.setBounds(274, 80, 145, 25);
		comboBoxLugares.addItem(new Lugar_De_Realizacion("Seleccionar"));
		for(Lugar_De_Realizacion l: listaDeLugaresParaDeporteSeleccionado) {
			
			comboBoxLugares.addItem(l);
			
		}
		getContentPane().add(comboBoxLugares);
		
		lblDisponibilidad = new JLabel("Disponibilidad (*)");
		lblDisponibilidad.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblDisponibilidad.setBounds(147, 120, 117, 25);
		getContentPane().add(lblDisponibilidad);
		
		txtDisponibilidad = new JTextField();
		txtDisponibilidad.setColumns(10);
		txtDisponibilidad.setBounds(274, 120, 75, 25);
		txtDisponibilidad.addKeyListener(new KeyAdapter() 
		{
			@Override
			public void keyTyped(KeyEvent e) 
			{
				if(txtDisponibilidad.getText().length()>1) {
					e.consume();
				}
				
				char input = e.getKeyChar();
				if((input < '0' || input > '9') && input != '\b')
				{
					e.consume();
				}
			}
		});
		getContentPane().add(txtDisponibilidad);
		
		lblAyuda = new JLabel("(Ayuda: La disponibilidad es la cantidad de encuentros");
		lblAyuda.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblAyuda.setBounds(142, 156, 284, 14);
		getContentPane().add(lblAyuda);
		
		lblAyudaContinuacion = new JLabel("  que se pueden llevar a cabo en el lugar en una fecha.)");
		lblAyudaContinuacion.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblAyudaContinuacion.setBounds(142, 170, 314, 14);
		getContentPane().add(lblAyudaContinuacion);
		
		btnAceptar = new JButton("Aceptar");
		btnAceptar.addActionListener(e->{
			
			StringBuilder msgAMostrar = new StringBuilder();
			
			if(((Lugar_De_Realizacion) this.comboBoxLugares.getSelectedItem()).getNombre().equals("Seleccionar") || this.txtDisponibilidad.getText().isEmpty()) 
			{
				
				msgAMostrar.append("-Debe ingresar datos en los campos obligatorios"+"\n");
			}
			if(!this.txtDisponibilidad.getText().isEmpty()) {
				if(Integer.parseInt(this.txtDisponibilidad.getText()) > 99) 
				{
					
					msgAMostrar.append("-Debe ingresar una disponibilidad menor a 100"+"\n");
				}
			}
			if(listaDeLugaresIngresados.contains(this.comboBoxLugares.getSelectedItem().toString())) 
			{
				msgAMostrar.append("-El lugar ya fue agregado."+"\n"+" Por favor, seleccione otro lugar o quite el mismo de la tabla."+"\n");
			}
			if(msgAMostrar.length() != 0) 
			{
				this.mostrarMensajeError("Error: Los siguientes campos son invalidos", msgAMostrar.toString());
			}
			else 
			{
				this.setVisible(false);
			}
		});
		btnAceptar.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnAceptar.setBounds(172, 270, 89, 30);
		getContentPane().add(btnAceptar);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(e->{
			txtDisponibilidad.setText("");
			comboBoxLugares.setSelectedIndex(0);
			setVisible(false);
			
		});
		btnCancelar.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnCancelar.setBounds(271, 270, 89, 30);
		getContentPane().add(btnCancelar);

	}
	
	public void mostrarMensajeError(String titulo,String detalle) 
	{
		JFrame padre= (JFrame) SwingUtilities.getWindowAncestor(this);
		JOptionPane.showMessageDialog(padre,
			    detalle,titulo,
			    JOptionPane.ERROR_MESSAGE);
	}
	
	public List<Lugar_De_Realizacion> getListaDeLugares() {
		return this.listaDeLugaresParaDeporteSeleccionado;
	}
	
	public Lugar_De_Realizacion getLugar() {
		return (Lugar_De_Realizacion) this.comboBoxLugares.getSelectedItem();
	}
	
	public String getDisponibilidad() {
		return this.txtDisponibilidad.getText().toString();
	}
}
