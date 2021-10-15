package br.com.jbends.cripto.view;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import br.com.jbends.cripto.modelo.Conta;
import br.com.jbends.cripto.modelo.Senha;
import br.com.jbends.cripto.modelo.Usuario;


public class CriptoFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private JLabel labelUsuario, labelConta;
	private JTextField textoConta;
	private JComboBox<Usuario> comboUsuario;
	private JButton botaoPesquisar, botaoLimpar, botaoSalvar;
	private JTable tabela;
	private DefaultTableModel modelo;
	
	public CriptoFrame() {
		super("Senhas");
		Container container = getContentPane();
		setLayout(null);
		
		labelUsuario = new JLabel("Nome Usuario");
		labelConta = new JLabel("Nome da Conta");
		
		labelUsuario.setBounds(10, 90, 240, 15);
		labelConta.setBounds(400, 90, 240, 15);
		
		labelUsuario.setForeground(Color.BLACK);
		labelConta.setForeground(Color.BLACK);
		
		container.add(labelUsuario);
		container.add(labelConta);
		
		comboUsuario = new JComboBox<Usuario>();
		
		comboUsuario.addItem(new Usuario());
		List<Usuario> usuarios = this.listarUsuario();
		for (Usuario usuario : usuarios) {
			comboUsuario.addItem(usuario);
		}
		
		textoConta = new JTextField();
		
		textoConta.setBounds(400, 105, 265, 20);
		comboUsuario.setBounds(10, 105, 265, 20);
		
		container.add(textoConta);
		container.add(comboUsuario);
		
		botaoPesquisar = new JButton("Pesquisar");
		botaoLimpar = new JButton("Limpar");
		botaoSalvar = new JButton("Salvar");
		
		botaoPesquisar.setBounds(10, 145, 120, 20);
		botaoLimpar.setBounds(150, 145, 120, 20);
		botaoSalvar.setBounds(400, 145, 120, 20);

		container.add(botaoPesquisar);
		container.add(botaoLimpar);
		container.add(botaoSalvar);
		
		tabela = new JTable();
		modelo = (DefaultTableModel) tabela.getModel();
		
		modelo.addColumn("Senha Criptografada");
		modelo.addColumn("Senha Descriptografada");
		modelo.addColumn("Nome da Conta");
		
		tabela.setBounds(10, 185, 760, 300);
		tabela.setTableHeader(null);
		container.add(tabela);
		
		setSize(800, 600);
		setVisible(true);
		setLocationRelativeTo(null);
		

		botaoLimpar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				limparTabela();
				limpar();
			}
		});
		
		botaoPesquisar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				limparTabela();
				pesquisar();
			}
		});
		
		botaoSalvar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				salvar();
			}
		});
		
	}
	
	private void limparTabela() {
		modelo.getDataVector().clear();
	}
	
	private List<Usuario> listarUsuario() {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("contas");
		EntityManager em = emf.createEntityManager();
		
		String jpql = "select u from Usuario u";
		TypedQuery<Usuario> query = em.createQuery(jpql, Usuario.class);
		List<Usuario> resultList = query.getResultList();
		
		return resultList;
		
	}
	
	private void preencherTabela(List<Conta> contas) {
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("contas");
		EntityManager em = emf.createEntityManager();
		
		for (Conta conta : contas) {
			String jpql = "select s from Senha s join s.conta c where c = :pConta";
			TypedQuery<Senha> query = em.createQuery(jpql, Senha.class);
			query.setParameter("pConta", conta);
			List<Senha> resultList = query.getResultList();
			try {
				for (Senha senha : resultList) {
					modelo.addRow(new Object[] { senha.getSenhaCripto(), senha.getSenhaDescripto(), senha.getConta().getNome() });
				}
			} catch (Exception e) {
				throw e;
			}
		}
	}
	
	private void limpar() {
		this.comboUsuario.setSelectedIndex(0);
	}
	
	private void pesquisar() {
		Usuario usuario = (Usuario) comboUsuario.getSelectedItem();
		if((usuario.toString() == null)) {
			JOptionPane.showMessageDialog(this, "Usuario não selecionado");
		} else {
			EntityManagerFactory emf = Persistence.createEntityManagerFactory("contas");
			EntityManager em = emf.createEntityManager();
			
			String jpql = "select c from Conta c join c.usuario u where u = :pUsuario";
			TypedQuery<Conta> query = em.createQuery(jpql, Conta.class);
			query.setParameter("pUsuario", usuario);
			List<Conta> resultList = query.getResultList();
			preencherTabela(resultList);
		}
	}
	
	private void salvar() {
		Usuario usuario = (Usuario) comboUsuario.getSelectedItem();
//		System.out.println();
		if (!textoConta.getText().equals("") && !(usuario.toString() == null)) {
			Conta conta = new Conta(usuario, textoConta.getText());	
			Senha senha = new Senha();
			senha.setSenhaDescripto(10);
			senha.setSenhaCripto(senha.getSenhaDescripto());
			senha.setConta(conta);
			
			EntityManagerFactory emf = Persistence.createEntityManagerFactory("contas");
			EntityManager em = emf.createEntityManager();
			
			em.getTransaction().begin();
			
			em.persist(conta);
			em.persist(senha);
			
			em.getTransaction().commit();
			
			JOptionPane.showMessageDialog(this, "Salvo com sucesso!");
			this.limpar();
		} else {
			JOptionPane.showMessageDialog(this, "Nome da conta e usuario não selecionado");
		}
	}


		
}
