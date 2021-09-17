package interfaces_graficas;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JFrame;
import java.awt.Font;
import java.awt.SystemColor;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import dominio.Competencia;
import excepciones.BaseDeDatosException;
import excepciones.CamposIncorrectosException;
import gestores.Gestor_Participante;
import javax.swing.ImageIcon;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.util.List;
import javax.swing.border.MatteBorder;
import java.awt.Color;

public class CU09_Alta_De_Participante extends JPanel {
	
	private JTextField txtNombreParticipante;
	private JTextField txtEmail;
	private JButton btnCancelar;
	private JButton btnAceptar;
	private JLabel lblImagenFondo;
	private JPanel panelInterior;
	private JLabel lblNombreParticipante;
	private JLabel lblCorreoElectronico;
	private Competencia competencia;
	private Gestor_Participante gestorParticipante;
	private Integer cuil_usuario_logeado;
	
	public CU09_Alta_De_Participante(JFrame pantallaPrincipal, Integer idCompetencia, Integer cuil_usuario_logeado) 
	{
		super();
		this.cuil_usuario_logeado = cuil_usuario_logeado;
		this.gestorParticipante = new Gestor_Participante();
		this.armarPanel(pantallaPrincipal, idCompetencia);
	}

	public void armarPanel(JFrame pantallaPrincipal, Integer idCompetencia) 
	{
		setLayout(null);
		panelInterior = new JPanel();
		panelInterior.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		panelInterior.setBackground(SystemColor.scrollbar);
		panelInterior.setBounds(204, 193, 600, 320);
		add(panelInterior);
		panelInterior.setLayout(null);
		
		lblNombreParticipante = new JLabel("Nombre del Equipo o Participante (*)");
		lblNombreParticipante.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblNombreParticipante.setBounds(90, 122, 230, 25);
		panelInterior.add(lblNombreParticipante);
		
		lblCorreoElectronico = new JLabel("Correo Electr\u00F3nico de Contacto (*)");
		lblCorreoElectronico.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblCorreoElectronico.setBounds(90, 159, 230, 25);
		panelInterior.add(lblCorreoElectronico);
		
		txtNombreParticipante = new JTextField();
		txtNombreParticipante.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				
				if(txtNombreParticipante.getText().length()>29) {
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
		txtNombreParticipante.setBounds(330, 120, 180, 25);
		panelInterior.add(txtNombreParticipante);
		txtNombreParticipante.setColumns(10);
		
		txtEmail = new JTextField();
		txtEmail.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				
				if(txtEmail.getText().length()>39) {
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
		txtEmail.setBounds(330, 160, 180, 25);
		panelInterior.add(txtEmail);
		txtEmail.setColumns(10);
		
		btnAceptar = new JButton("Aceptar");
		btnAceptar.addActionListener(e->
		{
			try 
			{
				String nombreParticipante = this.getTxtNombreParticipante().getText();
				String emailParticipante = this.getTxtEmail().getText();
				this.gestorParticipante.crearParticipante(nombreParticipante, emailParticipante, idCompetencia);
				this.accionMarcarCamposCorrectos();
				this.limpiarFormulario();
				this.mostrarMensajeInformacion(pantallaPrincipal,"Exito", "La operación ha culminado con éxito");
			
			}
			catch (SQLException | BaseDeDatosException e1) 
			{
				e1.printStackTrace();
				this.mostrarMensajeError(pantallaPrincipal,"Error al registrar al participante",e1.getMessage());
			}
			catch (CamposIncorrectosException e2)
			{
				e2.printStackTrace();
				this.accionMarcarCamposCorrectos();
				this.accionMarcarCamposErroneos(e2.getCamposErroneos());
				this.mostrarMensajeError(pantallaPrincipal,"Error al registrar al participante",e2.getMessage());
			}
		});
		btnAceptar.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnAceptar.setBounds(806, 665, 89, 30);
		add(btnAceptar);
		
		btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(e->
		{
			int resultado = JOptionPane.showConfirmDialog(pantallaPrincipal, "Está seguro que desea cancelar?", "Confirmación", JOptionPane.YES_NO_OPTION);
			if(resultado == JOptionPane.YES_OPTION) 
			{
				this.setVisible(false);
				JPanel panelListarParticipantes = new CU08_Listar_Participantes_Competencia(pantallaPrincipal, idCompetencia, cuil_usuario_logeado);
				pantallaPrincipal.setContentPane(panelListarParticipantes);
				pantallaPrincipal.setTitle("LISTAR PARTICIPANTES DE COMPETENCIA DEPORTIVA");
			}

		});
		btnCancelar.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnCancelar.setBounds(907, 665, 89, 30);
		add(btnCancelar);
		
		lblImagenFondo = new JLabel("");
		lblImagenFondo.setIcon(new ImageIcon("2.jpg"));
		lblImagenFondo.setBounds(0, 0, 1008, 707);
		add(lblImagenFondo);

	}
	
	private void limpiarFormulario() 
	{
		this.txtNombreParticipante.setText("");
		this.txtEmail.setText("");

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
	
	public void accionMarcarCamposCorrectos() 
	{
				this.lblNombreParticipante.setForeground(Color.BLACK);
				this.lblCorreoElectronico.setForeground(Color.BLACK);
	}
	
	public void accionMarcarCamposErroneos(List<String> nombresDeCampos) 
	{
		
		for(String c: nombresDeCampos) 
		{
			switch (c) 
			{
			case "Nombre":
				this.lblNombreParticipante.setForeground(Color.RED);
				break;
			case "Email":
				this.lblCorreoElectronico.setForeground(Color.RED);
				break;
			}
		}

	}

	public JTextField getTxtNombreParticipante() {
		return txtNombreParticipante;
	}

	public void setTxtNombreParticipante(JTextField txtNombreParticipante) {
		this.txtNombreParticipante = txtNombreParticipante;
	}

	public JTextField getTxtEmail() {
		return txtEmail;
	}

	public void setTxtEmail(JTextField txtEmail) {
		this.txtEmail = txtEmail;
	}

	public JButton getBtnCancelar() {
		return btnCancelar;
	}

	public void setBtnCancelar(JButton btnCancelar) {
		this.btnCancelar = btnCancelar;
	}

	public JButton getBtnAceptar() {
		return btnAceptar;
	}

	public void setBtnAceptar(JButton btnAceptar) {
		this.btnAceptar = btnAceptar;
	}

	public JLabel getLblImagenFondo() {
		return lblImagenFondo;
	}

	public void setLblImagenFondo(JLabel lblImagenFondo) {
		this.lblImagenFondo = lblImagenFondo;
	}

	public JPanel getPanelInterior() {
		return panelInterior;
	}

	public void setPanelInterior(JPanel panelInterior) {
		this.panelInterior = panelInterior;
	}

	public JLabel getLblNombreParticipante() {
		return lblNombreParticipante;
	}

	public void setLblNombreParticipante(JLabel lblNombreParticipante) {
		this.lblNombreParticipante = lblNombreParticipante;
	}

	public JLabel getLblCorreoElectronico() {
		return lblCorreoElectronico;
	}

	public void setLblCorreoElectronico(JLabel lblCorreoElectronico) {
		this.lblCorreoElectronico = lblCorreoElectronico;
	}

	public Competencia getCompetencia() {
		return competencia;
	}

	public void setCompetencia(Competencia competencia) {
		this.competencia = competencia;
	}
	
	
}
