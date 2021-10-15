package br.com.jbends.cripto.testes;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import br.com.jbends.cripto.modelo.Conta;
import br.com.jbends.cripto.modelo.Senha;
import br.com.jbends.cripto.modelo.Usuario;

public class TesteInserirDados {
	public static void main(String[] args) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("contas");
		EntityManager em = emf.createEntityManager();
		
		Usuario user = em.getReference(Usuario.class, 1L);
		
		Conta conta = new Conta();
		conta.setNome("istari.clever.denker@gmail.com");
		conta.setUsuario(user);
		
		Senha senha = new Senha();
		senha.setSenhaDescripto(10);
		senha.setSenhaCripto(senha.getSenhaDescripto());
		senha.setConta(conta);
			
		em.getTransaction().begin();
		
		em.persist(user);
		em.persist(conta);
		em.persist(senha);
		
		em.getTransaction().commit();
	}
}
