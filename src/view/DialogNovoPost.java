package view;

import controller.ControladorPrincipal;
import controller.GerenciadorPosts;
import model.Usuario;
import util.Excecoes;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Diálogo para criar um novo post.
 * Interface gráfica para publicação de conteúdo.
 */
public class DialogNovoPost extends JDialog {
    private JTextArea areaConteudo;
    private JButton botaoPublicar;
    private JButton botaoCancelar;
    private Usuario usuario;
    private Runnable callbackAtualizacao;
    
    public DialogNovoPost(JFrame parent, Usuario usuario, Runnable callbackAtualizacao) {
        super(parent, "Novo Post", true);
        this.usuario = usuario;
        this.callbackAtualizacao = callbackAtualizacao;
        configurarJanela();
        criarComponentes();
        adicionarComponentes();
        configurarEventos();
    }
    
    private void configurarJanela() {
        setSize(500, 300);
        setLocationRelativeTo(getParent());
        setResizable(false);
        setLayout(new BorderLayout());
    }
    
    private void criarComponentes() {
        areaConteudo = new JTextArea(10, 40);
        areaConteudo.setLineWrap(true);
        areaConteudo.setWrapStyleWord(true);
        botaoPublicar = new JButton("Publicar");
        botaoCancelar = new JButton("Cancelar");
    }
    
    private void adicionarComponentes() {
        // Painel do conteúdo
        JPanel painelConteudo = new JPanel(new BorderLayout());
        painelConteudo.setBorder(BorderFactory.createTitledBorder("O que você está pensando?"));
        painelConteudo.add(new JScrollPane(areaConteudo), BorderLayout.CENTER);
        
        // Painel de botões
        JPanel painelBotoes = new JPanel(new FlowLayout());
        painelBotoes.add(botaoPublicar);
        painelBotoes.add(botaoCancelar);
        
        add(painelConteudo, BorderLayout.CENTER);
        add(painelBotoes, BorderLayout.SOUTH);
    }
    
    private void configurarEventos() {
        botaoPublicar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                publicarPost();
            }
        });
        
        botaoCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }
    
    private void publicarPost() {
        String conteudo = areaConteudo.getText().trim();
        
        if (conteudo.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "O post não pode estar vazio.", 
                "Aviso", 
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            GerenciadorPosts.criarPost(conteudo, usuario);
            ControladorPrincipal.salvarDados();
            JOptionPane.showMessageDialog(this, 
                "Post publicado com sucesso!", 
                "Sucesso", 
                JOptionPane.INFORMATION_MESSAGE);
            
            if (callbackAtualizacao != null) {
                callbackAtualizacao.run();
            }
            
            dispose();
        } catch (Excecoes.DadosInvalidosException e) {
            JOptionPane.showMessageDialog(this, 
                e.getMessage(), 
                "Erro", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
}

