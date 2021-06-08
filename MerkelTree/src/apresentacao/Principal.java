package apresentacao;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.util.ArrayList;

public class Principal {
	// Propriedades da classe
	private static ArrayList<String> merkelTree = new ArrayList<String>();
	
	// Métodos da classe
	public static void main(String[] args) {
		// Declaração de variáveis
		BufferedReader leitor = new BufferedReader(new InputStreamReader(System.in));
		String hash = "";
		
		try {
			// Entrada de dados
			/*
			do {
				System.out.print("Digite uma transação: ");
				merkelTree.add(leitor.readLine());
				
				System.out.print("Digite <S> para continuar: ");
			} while (leitor.readLine().equalsIgnoreCase("S"));
			*/
			for (int i = 0 ; i < 1000000 ; i++) {
				merkelTree.add("Transação de número: " + i);
			}
			
			// Processamento
			hash = calcularHashRaiz(merkelTree);
			
			// Saída de dados
			System.out.println(hash);
		} catch (Exception erro) {
			System.out.println(erro);
		}
	}
	
	private static String calcularHashRaiz(ArrayList<String> merkelTree) throws Exception {
		String retorno = "";
		ArrayList<String> temporario = new ArrayList<String>();
		String hash1 = "";
		String hash2 = "";
		
		for (int i = 0 ; i < merkelTree.size() ; i+=2) {
			hash1 = calcularHash(merkelTree.get(i));
			if ((i + 1) == merkelTree.size()) {
				hash2 = "";
			} else {
				hash2 = calcularHash(merkelTree.get(i + 1));
			}
			
			temporario.add(calcularHash(hash1 + hash2));
		}
		
		if (temporario.size() == 1) {
			retorno = temporario.get(0);
		} else {
			retorno = calcularHashRaiz(temporario);
		}
		
		return retorno;
	}
	
	private static String calcularHash(String texto) throws Exception {
		String retorno = "";
		
		MessageDigest objHash = MessageDigest.getInstance("SHA-256");
		objHash.reset();
		objHash.update(texto.getBytes());
		byte[] resultado = objHash.digest();
		
		for (int i = 0 ; i < resultado.length ; i++) {
			String temp = Integer.toHexString(0xFF & resultado[i]);
			retorno += (((temp.length() == 1) ? "0" : "") + temp);
		}
		
		return retorno;
	}
}
