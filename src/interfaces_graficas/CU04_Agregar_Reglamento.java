package interfaces_graficas;


import javax.swing.JButton;


import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import java.awt.Font;
import java.awt.Frame;
import java.awt.SystemColor;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.SoftBevelBorder;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.border.BevelBorder;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class CU04_Agregar_Reglamento extends JDialog {
	
	private JTextArea txtAreaDeTexto;
	private JButton btnAceptar;
	private JButton btnCancelar;
	
	
	/*Recibo como parametro el reglamento que ingreso anteriormente, para que pueda visualizarlo y si quiere, modificarlo*/
	
	public CU04_Agregar_Reglamento(Frame padre, boolean modal, String reglamento_ingresado_anteriormente) {
		super(padre, modal);
		this.armarPanel(padre, modal, reglamento_ingresado_anteriormente);
			
		}
	
	public void armarPanel(Frame padre, boolean modal, String reglamento_ingresado_anteriormente) {
		
		this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		
		getContentPane().setBackground(SystemColor.scrollbar);
		setBounds(100, 100, 550, 350);
		getContentPane().setLayout(null);
		
		btnAceptar = new JButton("Aceptar");
		btnAceptar.addActionListener(e->  {
				this.setVisible(false);
		});
		btnAceptar.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnAceptar.setBounds(172, 270, 89, 30);
		getContentPane().add(btnAceptar);
		
		txtAreaDeTexto = new JTextArea();
		txtAreaDeTexto.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				if (txtAreaDeTexto.getText().length()> 199) 

			         e.consume(); 
			    } 
		});
		txtAreaDeTexto.setFont(new Font("Tahoma", Font.BOLD, 12));
		txtAreaDeTexto.setText(""+reglamento_ingresado_anteriormente);
		JScrollPane scrollPane = new JScrollPane(txtAreaDeTexto);
		scrollPane.setBounds(20, 21, 494, 217);
		getContentPane().add(scrollPane);
		
		/*EN CANCELAR VERIFICO SI EL REGLAMENTO CAMBIO CON RESPECTO AL INGRESADO ANTERIORMENTE,
		 * EN CASO DE QUE HAYA UN CAMBIO O NO, EL REGLAMENTO QUEDA IGUAL AL INGRESADO ANTERIORMENTE
		 * HICE ESTO PARA QUE EL USUARIO PUEDA VISUALIZAR LO QUE INGRESO ANTES*/
		
		/*Lo que no pude hacer es que cuando estas en la pantalla de reglamento y presionas para cerrar
		 * la ventana, guarda lo que hayas escrito*/
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(e-> {
			if((txtAreaDeTexto.getText().toString().length()) > (reglamento_ingresado_anteriormente.length())
					|| (txtAreaDeTexto.getText().toString().length()) < (reglamento_ingresado_anteriormente.length())) {
				txtAreaDeTexto.setText(""+reglamento_ingresado_anteriormente);
			}
			if(txtAreaDeTexto.getText().toString().equals(reglamento_ingresado_anteriormente)){
				txtAreaDeTexto.setText(""+reglamento_ingresado_anteriormente);
			}
			this.setVisible(false);
		});
		btnCancelar.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnCancelar.setBounds(271, 270, 89, 30);
		getContentPane().add(btnCancelar);
		
		JPanel panel = new JPanel();
		panel.setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		panel.setBounds(10, 11, 514, 237);
		getContentPane().add(panel);
		
		this.setTitle("Ingrese el Reglamento");
	}
	
	public void mostrarMensajeError(String titulo,String detalle) 
	{
		JFrame padre= (JFrame) SwingUtilities.getWindowAncestor(this);
		JOptionPane.showMessageDialog(padre,
			    detalle,titulo,
			    JOptionPane.ERROR_MESSAGE);
	}
	
	public String getReglamento() {
		return this.txtAreaDeTexto.getText();
	}
}
