package app;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import dominio.Usuario;
import gestores.Gestor_Usuario;
import interfaces_graficas.CU20_Ver_Competencia;
import interfaces_graficas.Menu_Principal;

public class Aplicacion extends JFrame 
{
	
	public Aplicacion() 
	{
		JMenuBar menuBar;
		JMenu menuArchivo;
		JMenu menuAyuda;
		JMenuItem menuItemSalir;
		menuBar = new JMenuBar();

		menuArchivo = new JMenu("Archivo");
		menuAyuda = new JMenu("Ayuda");
		
		menuItemSalir = new JMenuItem("Salir");
		menuItemSalir.addActionListener( e -> System.exit(0));
		menuArchivo.add(menuItemSalir);
		
		menuBar.add(menuArchivo);
		menuBar.add(menuAyuda);
		this.setJMenuBar(menuBar);
	}
	
	public static void main(String[] args) 
	{
		
		JFrame app = new Aplicacion();
		
		Integer id_usuario_logeado = 1;
		
		app.setTitle("MENU PRINCIPAL");
		
		JPanel panel1 = new Menu_Principal(app, id_usuario_logeado);
		
		app.setContentPane(panel1);
		app.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		app.setSize(1024, 768);
		app.setResizable(false);
		app.setLocationRelativeTo(null);
		app.setVisible(true);
		  
		  
		
	}
	
}
