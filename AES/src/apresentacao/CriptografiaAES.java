package apresentacao;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class CriptografiaAES extends JFrame {
	// Propriedades da classe
	private static final long serialVersionUID = 1L;

	private JPanel objPainel = new JPanel();
	
	private JLabel lblTexto = new JLabel("Texto Aberto");
	private JTextArea txtTexto = new JTextArea();
	private JScrollPane jspTexto = new JScrollPane(txtTexto);
	
	private JLabel lblSenha = new JLabel("Senha");
	private JTextField txtSenha = new JTextField();
	
	private JLabel lblCriptograma = new JLabel("Criptograma");
	private JTextArea txtCriptograma = new JTextArea();
	private JScrollPane jspCriptograma = new JScrollPane(txtCriptograma);
	
	private JButton btnEncriptar = new JButton("Encriptar");
	private JButton btnDecriptar = new JButton("Decriptar");
	private JButton btnLimpar = new JButton("Limpar");
	private JButton btnSair = new JButton("Sair");
	
	// Método principal de execução do programa
	public static void main(String[] args) {
		new CriptografiaAES().setVisible(true);
	}
	
	// Método construtor da classe
	public CriptografiaAES() {
		setSize(800, 600);
		setTitle("Criptografia Advanced Encryption Stardard (AES)");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		
		objPainel.setLayout(null);
		setContentPane(objPainel);
		
		lblTexto.setBounds(20, 20, 200, 20);
		objPainel.add(lblTexto);
		jspTexto.setBounds(20, 40, 750, 150);
		objPainel.add(jspTexto);
		txtTexto.setLineWrap(true);
		
		lblSenha.setBounds(20, 210, 200, 20);
		objPainel.add(lblSenha);
		txtSenha.setBounds(18, 230, 300, 20);
		objPainel.add(txtSenha);
		
		lblCriptograma.setBounds(20, 270, 200, 20);
		objPainel.add(lblCriptograma);
		jspCriptograma.setBounds(20, 290, 750, 150);
		objPainel.add(jspCriptograma);
		txtCriptograma.setLineWrap(true);
		
		btnEncriptar.setBounds(350, 500, 100, 30);
		btnEncriptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					txtCriptograma.setText(encriptar(txtTexto.getText(), txtSenha.getText()));
				} catch (Exception erro) {
					JOptionPane.showMessageDialog(null, erro);
				}
			}
		});
		objPainel.add(btnEncriptar);
		
		btnDecriptar.setBounds(450, 500, 100, 30);
		btnDecriptar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					txtTexto.setText(decriptar(txtCriptograma.getText(), txtSenha.getText()));
				} catch (Exception erro) {
					JOptionPane.showMessageDialog(null, erro);
				}
			}
		});
		objPainel.add(btnDecriptar);
		
		btnLimpar.setBounds(550, 500, 100, 30);
		btnLimpar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtTexto.setText("");
				txtSenha.setText("");
				txtCriptograma.setText("");
			}
		});
		objPainel.add(btnLimpar);
		
		btnSair.setBounds(650, 500, 100, 30);
		btnSair.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		objPainel.add(btnSair);
	}
	
	private static String encriptar(String texto, String senha) throws Exception {
		SecretKey objChaveSecreta = new SecretKeySpec(senha.substring(0, 16).getBytes("UTF-8"), "AES");
		IvParameterSpec objIv = new IvParameterSpec("JuniorEhBonitao!".getBytes("UTF-8"));
		Cipher cifra = Cipher.getInstance("AES/CBC/PKCS5Padding");
		
		cifra.init(Cipher.ENCRYPT_MODE, objChaveSecreta, objIv);
		byte[] arrayDeBytes = cifra.doFinal(texto.getBytes("UTF-8"));
		
		return Base64.getEncoder().encodeToString(arrayDeBytes);
	}
	
	private static String decriptar(String criptograma, String senha) throws Exception {
		SecretKey objChaveSecreta = new SecretKeySpec(senha.substring(0, 16).getBytes("UTF-8"), "AES");
		IvParameterSpec objIv = new IvParameterSpec("JuniorEhBonitao!".getBytes("UTF-8"));
		Cipher cifra = Cipher.getInstance("AES/CBC/PKCS5Padding");
		
		cifra.init(Cipher.DECRYPT_MODE, objChaveSecreta, objIv);
		byte[] arrayDeBytes = cifra.doFinal(Base64.getDecoder().decode(criptograma));
		
		return new String(arrayDeBytes, "UTF-8");
	}
}
