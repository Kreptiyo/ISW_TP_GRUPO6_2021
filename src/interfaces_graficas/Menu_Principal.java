package interfaces_graficas;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import java.awt.SystemColor;
import java.awt.Font;
import java.awt.Color;
import javax.swing.border.MatteBorder;

public class Menu_Principal extends JPanel 
{

	private JButton btnDarDeBajaUsuario;
	private JButton btnSalir;
	private JPanel panelMenu;
	private JButton btnListarTodasLasCompetencias;
	private JButton btnListarMisCompetenciasDeportivas;
	private JButton btnListarLugaresDeRealizacion;
	private JButton btnModificarDatosDeUsuario;
	private JLabel lblMenuPrincipal;
	private JLabel lblImagenFondo;
	private Integer cuil_usuario_logeado;

	public Menu_Principal(JFrame pantallaPrincipal, Integer cuil_usuario_logeado) 
	{
		super();
		this.cuil_usuario_logeado = cuil_usuario_logeado;
		this.armarPanel(pantallaPrincipal);
	}
	
	
	public void armarPanel(JFrame pantallaPrincipal) 
	{
		setLayout(null);
		btnDarDeBajaUsuario = new JButton("Dar de Baja Usuario");
		btnDarDeBajaUsuario.setEnabled(false);
		btnDarDeBajaUsuario.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnDarDeBajaUsuario.setBounds(10, 666, 170, 30);
		add(btnDarDeBajaUsuario);
		
		btnSalir = new JButton("Salir");
		btnSalir.addActionListener(e -> System.exit(0));
		btnSalir.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnSalir.setBounds(907, 665, 89, 30);
		add(btnSalir);
		
		panelMenu = new JPanel();
		panelMenu.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		panelMenu.setBackground(SystemColor.scrollbar);
		panelMenu.setBounds(279, 110, 450, 360);
		add(panelMenu);
		panelMenu.setLayout(null);
		
		btnListarTodasLasCompetencias = new JButton("Listar Todas Las Competencias");
		btnListarTodasLasCompetencias.setEnabled(false);
		btnListarTodasLasCompetencias.setBounds(85, 88, 280, 40);
		btnListarTodasLasCompetencias.setFont(new Font("Tahoma", Font.BOLD, 12));
		panelMenu.add(btnListarTodasLasCompetencias);
		
		btnListarMisCompetenciasDeportivas = new JButton("Listar Mis Competencias Deportivas");
		btnListarMisCompetenciasDeportivas.addActionListener(e->
		{
			this.setVisible(false);
			JPanel panelListarCompetencias = new CU03_Listar_Competencias_Deportivas(pantallaPrincipal, cuil_usuario_logeado);
			panelListarCompetencias.setVisible(true);
			pantallaPrincipal.setContentPane(panelListarCompetencias);
			pantallaPrincipal.setTitle("LISTAR MIS COMPETENCIAS DEPORTIVAS");
		});
		btnListarMisCompetenciasDeportivas.setBounds(85, 138, 280, 40);
		btnListarMisCompetenciasDeportivas.setFont(new Font("Tahoma", Font.BOLD, 12));
		panelMenu.add(btnListarMisCompetenciasDeportivas);
		
		btnListarLugaresDeRealizacion = new JButton("Listar Lugares de Realizaci\u00F3n");
		btnListarLugaresDeRealizacion.setEnabled(false);
		btnListarLugaresDeRealizacion.setBounds(85, 184, 280, 40);
		btnListarLugaresDeRealizacion.setFont(new Font("Tahoma", Font.BOLD, 12));
		panelMenu.add(btnListarLugaresDeRealizacion);
		
		btnModificarDatosDeUsuario = new JButton("Modificar Datos de Usuario");
		btnModificarDatosDeUsuario.setEnabled(false);
		btnModificarDatosDeUsuario.setBounds(85, 230, 280, 40);
		btnModificarDatosDeUsuario.setFont(new Font("Tahoma", Font.BOLD, 12));
		panelMenu.add(btnModificarDatosDeUsuario);
		
		lblMenuPrincipal = new JLabel("Men\u00FA Principal");
		lblMenuPrincipal.setForeground(new Color(0, 0, 255));
		lblMenuPrincipal.setFont(new Font("Tahoma", Font.BOLD, 18));
		lblMenuPrincipal.setBounds(155, 11, 140, 35);
		panelMenu.add(lblMenuPrincipal);
		
		lblImagenFondo = new JLabel("");
		lblImagenFondo.setIcon(new ImageIcon("2.jpg"));
		lblImagenFondo.setBounds(0, 0, 1008, 707);
		add(lblImagenFondo);
		
		this.setVisible(true);

	}
}
