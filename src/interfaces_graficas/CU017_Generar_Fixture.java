package interfaces_graficas;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import dominio.Competencia;
import dominio.Competencia.Estado_Competencia;
import dominio.Liga;
import dominio.Lugar_De_Realizacion;
import gestores.Gestor_Competencia;
import gestores.Gestor_Fixture;

import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Frame;

import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import java.awt.SystemColor;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class CU017_Generar_Fixture extends JDialog 
{

	private final JPanel contentPanel = new JPanel();
	private Gestor_Fixture gestorFixture;

	public CU017_Generar_Fixture(Frame padre, boolean modal, Integer id_competencia)
	{
		super(padre, modal);
		this.gestorFixture = new Gestor_Fixture();
		this.armarPanel(padre, id_competencia);
	}


	private void armarPanel(Frame padre, Integer id_competencia)
	{
		setTitle("GENERAR FIXTURE");
		setBounds(100, 100, 382, 188);
		this.setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBackground(SystemColor.scrollbar);
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		{
			JLabel lblNewLabel = new JLabel("Desea Generar un Nuevo Fixture?");
			lblNewLabel.setBounds(92, 40, 211, 25);
			lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
			contentPanel.add(lblNewLabel);
		}
		
		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnCancelar.setBounds(191, 92, 89, 30);
		btnCancelar.addActionListener(e->{
			setVisible(false);
			
		});
		contentPanel.add(btnCancelar);
		
		JButton btnAceptar = new JButton("Aceptar");
		btnAceptar.addActionListener(e->{
			
			try 
			{
				gestorFixture.generarFixture(id_competencia);
				JOptionPane.showMessageDialog(padre, "Fixture generado correctamente", "Exito", JOptionPane.INFORMATION_MESSAGE);
				this.setVisible(false);
			} 
			catch (Exception e1) 
			{
				e1.printStackTrace();
				this.mostrarMensajeError("No se pudo generar el fixture", e1.getMessage());
			}
		
			
		});
		btnAceptar.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnAceptar.setBounds(92, 92, 89, 30);
		contentPanel.add(btnAceptar);
		
		this.setVisible(true);
	}
	
	public void mostrarMensajeError(String titulo,String detalle) 
	{
		JFrame padre= (JFrame) SwingUtilities.getWindowAncestor(this);
		JOptionPane.showMessageDialog(padre,
			    detalle,titulo,
			    JOptionPane.ERROR_MESSAGE);
	}
}
