package br.com.jbends.cripto.modelo;

import java.util.ArrayList;
import java.util.List;

public class Gerador {
	//private int quantidadeDeCaracteres;
	
	public String GeradorAleatorio(int quantidadeDeCaracteres) {
		List<String> caracteres = new ArrayList<String>();
		
		for (char i='a'; i <= 'z'; i++) {
			String caracter = String.valueOf(i);
			caracteres.add(caracter);
		}
		
		for (int j = 0; j<=9; j++) {
			String caracter = String.valueOf(j);
			caracteres.add(caracter);
		}
		
		caracteres.add("*");
		caracteres.add("#");
		caracteres.add("$");
		caracteres.add("&");
		
		StringBuilder senha = new StringBuilder();
		
		for (int i = 0; i < quantidadeDeCaracteres; i++) {
			int posicao = (int) (Math.random() * caracteres.size());
			senha.append(caracteres.get(posicao));
		}
		
		return senha.toString();
	}
	
	public String criptografar(String senha) {
		String salGerado = BCrypt.gensalt();
		return BCrypt.hashpw(senha, salGerado);
	}
	
	public boolean descriptografar(String senhaDoBanco, String senha) {
		boolean valida = BCrypt.checkpw(senha, senhaDoBanco);
		if(valida) {
			return true;
		} else {
			return false;
		}
		
	}
}
