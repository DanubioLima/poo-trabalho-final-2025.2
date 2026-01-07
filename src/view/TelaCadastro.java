package view;

import controller.ControladorPrincipal;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Tela de cadastro de novos usuários.
 * Interface gráfica para registro na rede social.
 */
public class TelaCadastro extends JDialog {
    private JTextField campoNome;
    private JTextField campoEmail;
    private JPasswordField campoSenha;
    private JPasswordField campoConfirmarSenha;
    private JButton botaoCadastrar;
    private JButton botaoCancelar;
    private JFrame parent;
    
    public TelaCadastro(JFrame parent) {
        super(parent, "Cadastro", true);
        this.parent = parent;
        configurarJanela();
        criarComponentes();
        adicionarComponentes();
        configurarEventos();
    }
    
    private void configurarJanela() {
        setSize(400, 350);
        setLocationRelativeTo(parent);
        setResizable(false);
        setLayout(new BorderLayout());
    }
    
    private void criarComponentes() {
        campoNome = new JTextField(20);
        campoEmail = new JTextField(20);
        campoSenha = new JPasswordField(20);
        campoConfirmarSenha = new JPasswordField(20);
        botaoCadastrar = new JButton("Cadastrar");
        botaoCancelar = new JButton("Cancelar");
    }
    
    private void adicionarComponentes() {
        JPanel painelPrincipal = new JPanel(new GridBagLayout());
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        
        // Título
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        JLabel titulo = new JLabel("Criar Nova Conta");
        titulo.setFont(new Font("Arial", Font.BOLD, 18));
        painelPrincipal.add(titulo, gbc);
        
        // Nome
        gbc.gridwidth = 1;
        gbc.gridy = 1;
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.EAST;
        painelPrincipal.add(new JLabel("Nome:"), gbc);
        
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        painelPrincipal.add(campoNome, gbc);
        
        // Email
        gbc.gridy = 2;
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.EAST;
        painelPrincipal.add(new JLabel("Email:"), gbc);
        
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        painelPrincipal.add(campoEmail, gbc);
        
        // Senha
        gbc.gridy = 3;
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.EAST;
        painelPrincipal.add(new JLabel("Senha:"), gbc);
        
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        painelPrincipal.add(campoSenha, gbc);
        
        // Confirmar Senha
        gbc.gridy = 4;
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.EAST;
        painelPrincipal.add(new JLabel("Confirmar Senha:"), gbc);
        
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        painelPrincipal.add(campoConfirmarSenha, gbc);
        
        // Botões
        gbc.gridy = 5;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        
        JPanel painelBotoes = new JPanel(new FlowLayout());
        painelBotoes.add(botaoCadastrar);
        painelBotoes.add(botaoCancelar);
        painelPrincipal.add(painelBotoes, gbc);
        
        add(painelPrincipal, BorderLayout.CENTER);
    }
    
    private void configurarEventos() {
        botaoCadastrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                realizarCadastro();
            }
        });
        
        botaoCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }
    
    private void realizarCadastro() {
        String nome = campoNome.getText().trim();
        String email = campoEmail.getText().trim();
        String senha = new String(campoSenha.getPassword());
        String confirmarSenha = new String(campoConfirmarSenha.getPassword());
        
        if (nome.isEmpty() || email.isEmpty() || senha.isEmpty() || confirmarSenha.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Por favor, preencha todos os campos.", 
                "Aviso", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (!senha.equals(confirmarSenha)) {
            JOptionPane.showMessageDialog(this, 
                "As senhas não coincidem.", 
                "Aviso", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (ControladorPrincipal.realizarCadastro(nome, email, senha)) {
            dispose();
        }
    }
}

