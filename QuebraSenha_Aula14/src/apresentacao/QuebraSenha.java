package apresentacao;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashMap;

public class QuebraSenha {
	private static HashMap<String, String> usuarios = new HashMap<String, String>();
	private static ArrayList<String> dicionario = new ArrayList<String>();
	
	public static void main(String[] args) throws Exception {
		// Inicialização do dicionário
		dicionario.add("123456");
		dicionario.add("password");
		dicionario.add("12345");
		dicionario.add("12345678");
		dicionario.add("football");
		dicionario.add("qwerty");
		dicionario.add("1234567890");
		dicionario.add("1234567");
		dicionario.add("princess");
		dicionario.add("1234");
		dicionario.add("login");
		dicionario.add("welcome");
		dicionario.add("solo");
		dicionario.add("abc123");
		dicionario.add("admin");
		dicionario.add("heineken");
		dicionario.add("skol");
		dicionario.add("flamengo");
		dicionario.add("curintians");
		dicionario.add("corinthians");
		dicionario.add("Brasil");
		dicionario.add("Neymar");
		dicionario.add("França");
		
		// Inicialização do arquivo de senhas
		usuarios.put("fc5669b52ce4e283ad1d5d182de88ff9faec6672bace84ac2ce4c083f54fe2bc", "kali");
		usuarios.put("353b31cbc5fe9caf53063936395072f9369076a7d0c8ee534f834cb2693dd6e2", "junior");
		usuarios.put("8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92", "mane");
		usuarios.put("d58d736c7a967fb5f307951932734f8b0594725faa5011dbb66a8c538e635fb6", "fulano");
		usuarios.put("b7e94be513e96e8c45cd23d162275e5a12ebde9100a425c4ebcdd7fa4dcd897c", "beltrano");
		usuarios.put("280d44ab1e9f79b5cce2dd4f58f5fe91f0fbacdac9f7447dffc318ceb79f2d02", "cicrano");
		usuarios.put("0c08a9536b5dd78713f440acb930872fd69f7a71ad0cf9cdedc9628ddf9ac3d7", "gabriel");
		usuarios.put("65e84be33532fb784c48129675f9eff3a682b27168c0ea744b2cf58ee02337c5", "joao");
		usuarios.put("26df939ee38cc162bb98f4eb5a111fdb270db6bd1dc645e98871ac2d8449bd6c", "humberto");
		usuarios.put("d04a0747e946c6233ab5a91ceb3a59624cdf14d7fd05e9386d22580ec980455e", "maria");
		usuarios.put("756356fbfa52ca1d11812575fcb9238edb0cecd44785f2c73d4604c56954d0af", "fernanda");
		usuarios.put("8c6976e5b5410415bde908bd4dee15dfb167a9c873fc4bb8a81f6f2ab448a918", "mario");
		usuarios.put("d75d2785d90cab90245dc9e22a82c1a048673c4a2c54fa1754e9085f4f01d687", "sunda");
		usuarios.put("e79c15d596b9b9c1334150622ce1ecb016c61e2bf05b7864296a29f9e62ed863", "zulu");
	
		System.out.println("Início do ataque de dicionário !");
		
		for (String senha : dicionario) {
			if (usuarios.containsKey(calcularHash(senha))) {
				System.out.println("Usuário: " + usuarios.get(calcularHash(senha)) + " | Senha: " + senha);
			}
		}
		
		System.out.println("Fim do ataque de dicionário !\r\n\r\n");
		
		System.out.println("Início do ataque de força bruta numérica !");

		for (int i = 0 ; i < 100000000 ; i++) {
			if (usuarios.containsKey(calcularHash(""+ i))) {
				System.out.println("Usuário: " + usuarios.get(calcularHash(""+ i)) + " | Senha: " + i);
			}
		}
		
		System.out.println("Fim do ataque de força bruta numérica !");
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