package br.com.jbends.cripto.testes;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class TesteCriarTabelas {
	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("contas");
		emf.createEntityManager();
		emf.close();
	}	
}
