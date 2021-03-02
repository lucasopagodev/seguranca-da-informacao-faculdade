package apresentacao;

import java.io.BufferedReader;
import java.io.InputStreamReader;


public class Principal {
private static char[] tabelaSubstituicao = new char[28];
	
	public static void main(String[] args) {
		//Estabelecimento das substituições
		tabelaSubstituicao[0] = 'a';
		tabelaSubstituicao[1] = 'b';
		tabelaSubstituicao[2] = 'c';
		tabelaSubstituicao[3] = 'd';
		tabelaSubstituicao[4] = 'e';
		tabelaSubstituicao[5] = 'f';
		tabelaSubstituicao[6] = 'g';
		tabelaSubstituicao[7] = 'h';
		tabelaSubstituicao[8] = 'i';
		tabelaSubstituicao[9] = 'j';
		tabelaSubstituicao[10] = 'k';
		tabelaSubstituicao[11] = 'l';
		tabelaSubstituicao[12] = 'm';
		tabelaSubstituicao[13] = 'n';
		tabelaSubstituicao[14] = 'o';
		tabelaSubstituicao[15] = 'p';
		tabelaSubstituicao[16] = 'q';
		tabelaSubstituicao[17] = 'r';
		tabelaSubstituicao[18] = 's';
		tabelaSubstituicao[19] = 't';
		tabelaSubstituicao[20] = 'u';
		tabelaSubstituicao[21] = 'v';
		tabelaSubstituicao[22] = 'w';
		tabelaSubstituicao[23] = 'x';
		tabelaSubstituicao[24] = 'y';
		tabelaSubstituicao[25] = 'z';
		tabelaSubstituicao[26] = ' ';
		tabelaSubstituicao[27] = 'A';
		
		//Declaração de variáveis
		BufferedReader leitor = new BufferedReader(new InputStreamReader(System.in));
		
		String entrada = "";
		String criptograma = "";
		
		try {
			//Entrada de dados
			System.out.print("Digite um texto: ");
			entrada = leitor.readLine();
			
			//Cifra
			criptograma = cifrarRot13(entrada);
			
			//Saída de dados
			System.out.println("Criptograma: " + criptograma);
		} catch (Exception erro) {
			System.out.println(erro);
		}
	}
	
	private static String cifrarRot13(String entrada) {
		String retorno = "";
		
		for (int i = 0 ; i < entrada.length() ; i++) {
			int posicao = descobrirPosicaoCaractere(entrada.substring(i, i + 1));
			
			if (posicao == -1) {
				retorno += entrada.substring(i, i + 1);
			} else {
				posicao += (tabelaSubstituicao.length / 2);
				if (posicao > (tabelaSubstituicao.length - 1)) {
					posicao -= tabelaSubstituicao.length;
				}
				retorno += tabelaSubstituicao[posicao];
			}
		}
		return retorno;
	}
	
	private static int descobrirPosicaoCaractere(String caractere) {
		int retorno = -1;
		
		for (int i = 0 ; i < tabelaSubstituicao.length ; i++) {
			if (tabelaSubstituicao[i] == caractere.toCharArray()[0]) {
				retorno = i;
				break;
			}
		}
		return retorno;
	}
}
