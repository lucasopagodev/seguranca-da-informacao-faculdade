package apresentacao;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigInteger;

public class DiffieHellman {
	private static final BigInteger p = new BigInteger("102031405123416071809152453627382938465749676859789");
	private static final BigInteger g = new BigInteger("1234567890123456789012345");
	
	public static void main(String[] args) {
		// Declaração de variáveis
		BufferedReader leitor = new BufferedReader(new InputStreamReader(System.in));
		
		BigInteger chavePrivada = null;
		BigInteger chavePublica = null;
		BigInteger chaveCompartilhada = null;
		
		// Processamento
		try {
			System.out.print("Informe o valor escolhido para a chave privada: ");
			chavePrivada = new BigInteger(leitor.readLine());
			
			chavePublica = g.modPow(chavePrivada, p);
			System.out.println("Sua chave pública é: " + chavePublica);
			
			System.out.print("Informe a chave pública da outra pessoa: ");
			chaveCompartilhada = (new BigInteger(leitor.readLine())).modPow(chavePrivada, p);
			
			System.out.println("A chave compartilhada para conversa segura é: " + chaveCompartilhada);
		} catch (Exception erro) {
			System.out.println(erro);
		}
	}
}