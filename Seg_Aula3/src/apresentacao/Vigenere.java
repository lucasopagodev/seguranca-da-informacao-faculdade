package apresentacao;

import java.io.BufferedReader;

import java.io.InputStreamReader;

public class Vigenere {
	public static void main(String[] args) {
		// Declaração de variáveis
		BufferedReader leitor = new BufferedReader(new InputStreamReader(System.in));
		
		String opcao = "";
		String texto = "";
		String senha = "";
		String resultado = "";
		
		try {
			do {
				// Entrada de dados
				System.out.print("Digite C para criptografar, ou D para decriptogradar, ou S para sair: ");
				opcao = leitor.readLine();
				
				System.out.print("Digite o texto: ");
				texto = leitor.readLine();
				
				System.out.print("Digite a senha: ");
				senha = leitor.readLine();
				
				// Processamento
				if (opcao.equalsIgnoreCase("C")) {
					resultado = encrypt(senha, texto);
				} else if (opcao.equalsIgnoreCase("D")) {
					resultado = decrypt(senha, texto);
				} else {
					resultado = "";
				}
				
				// Saída de dados
				System.out.println(resultado);
			} while (! opcao.equalsIgnoreCase("S"));
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	private static String encrypt(String senha, String texto) {
		String criptograma = "";
		
		for (int i = 0 ; i < texto.length() ; i++) {
			// Comentário de como fazer as operações de XOR
			// texto = Aula de Criptografia do Junior
			// senha = ceub
			// Preparação das strings
			// texto = Aula de Criptografia do Junior
			// senha = ceubceubceubceubceubceubceubce
// posições do texto = 0123456789...
// posições da senha = 01230123012301230123...
			int caractereResultadoDoXOR = (texto.toCharArray()[i] ^ senha.toCharArray()[i % senha.length()]);
			String hexaDecimal = Integer.toHexString(caractereResultadoDoXOR);
			
			criptograma += (hexaDecimal.length() == 1 ? "0" : "") + hexaDecimal;
		}
		
		return criptograma;
	}
	
	private static String decrypt(String senha, String texto) {
		String mensagem = "";
		int contadorSenha = 0;
		
		for (int i = 0 ; i < texto.length() - 1 ; i+=2) {
			// Comentário de como fazer as operações de XOR
			// texto = 0007ee130609
			// senha = ceub
			// Preparação das strings
			// texto = 0007ee130609
			// senha = c e u b c e 
// posições do texto = 0123456789...
// posições da senha = 0 1 2 3 0 1
			String hexaDecimal = texto.substring(i, i + 2);
			char caractereResultadoDoXOR = (char) (Integer.parseInt(hexaDecimal, 16) ^
												   senha.toCharArray()[contadorSenha % senha.length()]);
			
			mensagem += caractereResultadoDoXOR;
			
			contadorSenha++;
		}
		
		return mensagem;
	}
}
