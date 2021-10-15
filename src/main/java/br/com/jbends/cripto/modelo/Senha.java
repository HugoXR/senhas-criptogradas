package br.com.jbends.cripto.modelo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Senha {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String senhaCripto;
	private String senhaDescripto;
	
	@JoinColumn(unique = true)
	@OneToOne
	private Conta conta;

	public String getSenhaDescripto() {
		return senhaDescripto;
	}
	
	public void setSenhaDescripto(int quantidadeCaracteres) {
		Gerador gerar = new Gerador();
		this.senhaDescripto = gerar.GeradorAleatorio(quantidadeCaracteres);
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSenhaCripto() {
		return senhaCripto;
	}

	public void setSenhaCripto(String senhaDescripto) {
		Gerador gerador = new Gerador();
		this.senhaCripto = gerador.criptografar(senhaDescripto);
	}

	public Conta getConta() {
		return conta;
	}

	public void setConta(Conta conta) {
		this.conta = conta;
	}
	
	
}
