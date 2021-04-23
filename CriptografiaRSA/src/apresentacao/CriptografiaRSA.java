package apresentacao;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.EncodedKeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.Cipher;

public class CriptografiaRSA {
	public static void main(String[] args) {
		// Declaração de variáveis
		BufferedReader leitor = new BufferedReader(new InputStreamReader(System.in));
		
		String chavePublica = "";
		String chaveSecreta = "";
		String textoAberto = "";
		String criptograma = "";

		// Processamento
		try {
			KeyPair objParDeChaves = gerarParDeChaves();
			System.out.println("Chave pública: " + new String(Base64.getEncoder().encode(objParDeChaves.getPublic().getEncoded()), "UTF-8"));
			System.out.println("Chave secreta: " + new String(Base64.getEncoder().encode(objParDeChaves.getPrivate().getEncoded()), "UTF-8"));
			
			System.out.println("======================================================================");
			
			System.out.print("Digite o texto aberto: ");
			textoAberto = leitor.readLine();
			
			System.out.print("Digite a chave: ");
			chavePublica = leitor.readLine();
			
			criptograma = encriptar(textoAberto, getChavePublica(chavePublica));
			System.out.println(criptograma);
			
			System.out.println("======================================================================");
			
			System.out.print("Digite o criptograma: ");
			criptograma = leitor.readLine();
			
			System.out.print("Digite a chave: ");
			chaveSecreta = leitor.readLine();
			
			textoAberto = decriptar(criptograma, getChaveSecreta(chaveSecreta));
			System.out.println(textoAberto);
		} catch (Exception erro) {
			System.out.println(erro);
		}
		
	}
	
	private static KeyPair gerarParDeChaves() throws Exception {
		KeyPairGenerator geradorDeChaves = KeyPairGenerator.getInstance("RSA");
		geradorDeChaves.initialize(1024);
		
		return geradorDeChaves.generateKeyPair();
	}
	
	private static PublicKey getChavePublica(String chavePublica) throws Exception {
		EncodedKeySpec especificacaoChavePublica = new X509EncodedKeySpec(Base64.getDecoder().decode(chavePublica));
		
		return KeyFactory.getInstance("RSA").generatePublic(especificacaoChavePublica);
	}
	
	private static PrivateKey getChaveSecreta(String chaveSecreta) throws Exception {
		EncodedKeySpec especificacaoChaveSecreta = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(chaveSecreta));
		
		return KeyFactory.getInstance("RSA").generatePrivate(especificacaoChaveSecreta);
	}
	
	private static String encriptar(String texto, Key chave) throws Exception {
		Cipher cifra = Cipher.getInstance("RSA");
		cifra.init(Cipher.ENCRYPT_MODE, chave);
		
		return Base64.getEncoder().encodeToString(cifra.doFinal(texto.getBytes("UTF-8")));
	}
	
	private static String decriptar(String criptograma, Key chave) throws Exception {
		Cipher cifra = Cipher.getInstance("RSA");
		cifra.init(Cipher.DECRYPT_MODE, chave);
		
		return new String(cifra.doFinal(Base64.getDecoder().decode(criptograma)), "UTF-8");
	}
}