package apresentacao;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.spec.EncodedKeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class RSA {
	// Propriedades da classe
	private static String chavePublica = "";
	private static String chaveSecreta = "";
	private static String chaveCompartilhada = "";
	
	private static String texto = "";
	private static String criptograma = "";
	
	// Método principal de execução
	public static void main(String[] args) {
		// Declaração de variáveis
		BufferedReader leitor = new BufferedReader(new InputStreamReader(System.in));
		int opcao = 0;
		
		// Laço de opções
		do {
			try {
				System.out.println("+========================+");
				System.out.println("|     Menu de Opções     |");
				System.out.println("+========================+");
				System.out.println("|1 - Gerar par de chaves |");
				System.out.println("|2 - Encriptar um texto  |");
				System.out.println("|3 - Decriptar uma cifra |");
				System.out.println("|4 - Sair                |");
				System.out.println("+========================+");
				opcao = Integer.parseInt(leitor.readLine());
				
				switch (opcao) {
					case 1:
						gerarParDeChaves();
						
						System.out.println("Chave pública: " + chavePublica);
						System.out.println("Chave secreta: " + chaveSecreta);
						
						break;
					case 2:
						System.out.print("Digite o texto: ");
						texto = leitor.readLine();
						
						System.out.print("Digite a chave pública: ");
						chavePublica = leitor.readLine();
						
						encriptarRSA(texto, chavePublica);
						
						System.out.println("Chave compartilhada: " + chaveCompartilhada);
						System.out.println("Criptograma: " + criptograma);
						
						break;
					case 3:
						System.out.print("Digite o criptograma: ");
						criptograma = leitor.readLine();
						
						System.out.print("Digite a chave secreta: ");
						chaveSecreta = leitor.readLine();
						
						System.out.print("Digite a chave compartilhada: ");
						chaveCompartilhada = leitor.readLine();
						
						decriptarRSA(criptograma, chaveSecreta, chaveCompartilhada);
						
						System.out.println("Texto: " + texto);
						
						break;
				}
			} catch (Exception erro) {
				System.out.println(erro);
			}
		} while (opcao != 4);
	}
	
	private static void gerarParDeChaves() throws Exception {
		KeyPairGenerator geradorDeParDeChaves = KeyPairGenerator.getInstance("RSA");
		geradorDeParDeChaves.initialize(2048);
		KeyPair parDeChaves = geradorDeParDeChaves.generateKeyPair();
		chavePublica = Base64.getEncoder().encodeToString(parDeChaves.getPublic().getEncoded());
		chaveSecreta = Base64.getEncoder().encodeToString(parDeChaves.getPrivate().getEncoded());
	}
	
	private static void encriptarRSA(String texto, String chavePublica) throws Exception {
		Cipher cifraRSA = Cipher.getInstance("RSA");
		
		// Geração da chave aleatória
		byte[] vetorDeBytes = new byte[117];
		new SecureRandom().nextBytes(vetorDeBytes);
		
		// Inicialização do RSA
		cifraRSA.init(Cipher.ENCRYPT_MODE, getChavePublica(chavePublica));
		
		// Encriptação da chave aleatória com o RSA, usando a chave pública
		chaveCompartilhada = Base64.getEncoder().encodeToString(cifraRSA.doFinal(vetorDeBytes));
		// Encriptação do texto com o AES, usando o hash da chave aleatória
		criptograma = encriptarAES(texto, new SecretKeySpec(calcularHash(vetorDeBytes), "AES"));
	}
	
	private static void decriptarRSA(String criptograma, String chaveSecreta, String chaveCompartilhada) throws Exception {
		Cipher cifraRSA = Cipher.getInstance("RSA");
		
		cifraRSA.init(Cipher.DECRYPT_MODE, getChaveSecreta(chaveSecreta));
		byte[] vetorDeBytes = cifraRSA.doFinal(Base64.getDecoder().decode(chaveCompartilhada));
		
		texto = decriptarAES(criptograma, new SecretKeySpec(calcularHash(vetorDeBytes), "AES"));
	}
	
	private static PublicKey getChavePublica(String chavePublica) throws Exception {
		PublicKey retorno = null;
		
		EncodedKeySpec especificacaoDaChavePublica = new X509EncodedKeySpec(Base64.getDecoder().decode(chavePublica));
		retorno = KeyFactory.getInstance("RSA").generatePublic(especificacaoDaChavePublica);
		
		return retorno;
	}
	
	private static PrivateKey getChaveSecreta(String chaveSecreta) throws Exception {
		PrivateKey retorno = null;
		
		EncodedKeySpec especificacaoDaChaveSecreta = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(chaveSecreta));
		retorno = KeyFactory.getInstance("RSA").generatePrivate(especificacaoDaChaveSecreta);
		
		return retorno;
	}
	
	private static String encriptarAES(String texto, SecretKey chaveCompartilhadaAES) throws Exception {
		Cipher cifraAES = Cipher.getInstance("AES/CBC/PKCS5Padding");
		IvParameterSpec objIv = new IvParameterSpec("012345Junior9876".getBytes());
		
		cifraAES.init(Cipher.ENCRYPT_MODE, chaveCompartilhadaAES, objIv);
		
		return Base64.getEncoder().encodeToString(cifraAES.doFinal(texto.getBytes("UTF-8")));
	}
	
	private static String decriptarAES(String criptograma, SecretKeySpec chaveCompartilhadaAES) throws Exception {
		Cipher cifraAES = Cipher.getInstance("AES/CBC/PKCS5Padding");
		IvParameterSpec objIv = new IvParameterSpec("012345Junior9876".getBytes());
		
		cifraAES.init(Cipher.DECRYPT_MODE, chaveCompartilhadaAES, objIv);
		
		return new String(cifraAES.doFinal(Base64.getDecoder().decode(criptograma)), "UTF-8");
	}
	
	private static byte[] calcularHash(byte[] vetorDeBytes) throws Exception {
		MessageDigest objHash = MessageDigest.getInstance("SHA-256");
		objHash.reset();
		objHash.update(vetorDeBytes);
		return objHash.digest();
	}
}