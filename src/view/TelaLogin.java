package view;

import controller.ControladorPrincipal;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Tela de login da aplicação.
 * Interface gráfica para autenticação de usuários.
 */
public class TelaLogin extends JFrame {
    private JTextField campoEmail;
    private JPasswordField campoSenha;
    private JButton botaoLogin;
    private JButton botaoCadastro;
    
    public TelaLogin() {
        configurarJanela();
        criarComponentes();
        adicionarComponentes();
        configurarEventos();
    }
    
    private void configurarJanela() {
        setTitle("Rede Social - Login");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout());
    }
    
    private void criarComponentes() {
        campoEmail = new JTextField(20);
        campoSenha = new JPasswordField(20);
        botaoLogin = new JButton("Entrar");
        botaoCadastro = new JButton("Cadastrar");
    }
    
    private void adicionarComponentes() {
        // Painel principal
        JPanel painelPrincipal = new JPanel(new GridBagLayout());
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        
        // Título
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        JLabel titulo = new JLabel("Rede Social");
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        painelPrincipal.add(titulo, gbc);
        
        // Email
        gbc.gridwidth = 1;
        gbc.gridy = 1;
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.EAST;
        painelPrincipal.add(new JLabel("Email:"), gbc);
        
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        painelPrincipal.add(campoEmail, gbc);
        
        // Senha
        gbc.gridy = 2;
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.EAST;
        painelPrincipal.add(new JLabel("Senha:"), gbc);
        
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        painelPrincipal.add(campoSenha, gbc);
        
        // Botões
        gbc.gridy = 3;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        
        JPanel painelBotoes = new JPanel(new FlowLayout());
        painelBotoes.add(botaoLogin);
        painelBotoes.add(botaoCadastro);
        painelPrincipal.add(painelBotoes, gbc);
        
        add(painelPrincipal, BorderLayout.CENTER);
    }
    
    private void configurarEventos() {
        // Enter no campo de senha também faz login
        campoSenha.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                realizarLogin();
            }
        });
        
        botaoLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                realizarLogin();
            }
        });
        
        botaoCadastro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exibirTelaCadastro();
            }
        });
    }
    
    private void realizarLogin() {
        String email = campoEmail.getText().trim();
        String senha = new String(campoSenha.getPassword());
        
        if (email.isEmpty() || senha.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Por favor, preencha todos os campos.", 
                "Aviso", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        ControladorPrincipal.realizarLogin(email, senha);
    }
    
    private void exibirTelaCadastro() {
        TelaCadastro telaCadastro = new TelaCadastro(this);
        telaCadastro.setVisible(true);
    }
}

