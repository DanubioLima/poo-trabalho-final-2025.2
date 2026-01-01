package view;

import controller.GerenciadorPosts;
import controller.ControladorPrincipal;
import model.Post;
import model.Usuario;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.format.DateTimeFormatter;

/**
 * Componente Swing reutilizável para exibir um post na timeline.
 * Mostra informações do post, autor, curtidas e permite interação.
 */
public class PainelPost extends JPanel {
    private Post post;
    private Usuario usuarioLogado;
    private JLabel labelAutor;
    private JLabel labelConteudo;
    private JLabel labelData;
    private JLabel labelCurtidas;
    private JButton botaoCurtir;
    private JButton botaoComentar;
    private Runnable callbackAtualizacao;
    
    public PainelPost(Post post, Usuario usuarioLogado, Runnable callbackAtualizacao) {
        this.post = post;
        this.usuarioLogado = usuarioLogado;
        this.callbackAtualizacao = callbackAtualizacao;
        configurarPainel();
        criarComponentes();
        adicionarComponentes();
        configurarEventos();
        atualizarVisualizacao();
    }
    
    private void configurarPainel() {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(""),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        setBackground(Color.WHITE);
    }
    
    private void criarComponentes() {
        labelAutor = new JLabel();
        labelAutor.setFont(new Font("Arial", Font.BOLD, 14));
        
        labelConteudo = new JLabel();
        labelConteudo.setFont(new Font("Arial", Font.PLAIN, 12));
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        labelData = new JLabel(post.getDataCriacao().format(formatter));
        labelData.setFont(new Font("Arial", Font.ITALIC, 10));
        labelData.setForeground(Color.GRAY);
        
        labelCurtidas = new JLabel();
        labelCurtidas.setFont(new Font("Arial", Font.PLAIN, 11));
        
        botaoCurtir = new JButton();
        botaoComentar = new JButton("Comentar");
    }
    
    private void adicionarComponentes() {
        // Painel superior (autor e data)
        JPanel painelSuperior = new JPanel(new BorderLayout());
        painelSuperior.add(labelAutor, BorderLayout.WEST);
        painelSuperior.add(labelData, BorderLayout.EAST);
        
        // Painel do conteúdo
        JPanel painelConteudo = new JPanel(new BorderLayout());
        painelConteudo.add(labelConteudo, BorderLayout.NORTH);
        painelConteudo.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        
        // Painel inferior (curtidas e botões)
        JPanel painelInferior = new JPanel(new BorderLayout());
        painelInferior.add(labelCurtidas, BorderLayout.WEST);
        
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painelBotoes.add(botaoCurtir);
        painelBotoes.add(botaoComentar);
        painelInferior.add(painelBotoes, BorderLayout.EAST);
        
        add(painelSuperior, BorderLayout.NORTH);
        add(painelConteudo, BorderLayout.CENTER);
        add(painelInferior, BorderLayout.SOUTH);
    }
    
    private void configurarEventos() {
        botaoCurtir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                curtirPost();
            }
        });
        
        botaoComentar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                comentarPost();
            }
        });
    }
    
    private void atualizarVisualizacao() {
        labelAutor.setText(post.getAutor().getNome());
        labelConteudo.setText("<html><body style='width: 400px'>" + 
                             post.getConteudo().replace("\n", "<br>") + 
                             "</body></html>");
        
        int numCurtidas = post.getNumeroCurtidas();
        labelCurtidas.setText(numCurtidas + " curtida(s)");
        
        // Atualizar botão de curtir
        if (post.foiCurtidoPor(usuarioLogado)) {
            botaoCurtir.setText("Descurtir");
            botaoCurtir.setForeground(Color.RED);
        } else {
            botaoCurtir.setText("Curtir");
            botaoCurtir.setForeground(Color.BLACK);
        }
    }
    
    private void curtirPost() {
        if (post.foiCurtidoPor(usuarioLogado)) {
            GerenciadorPosts.descurtirPost(post, usuarioLogado);
        } else {
            GerenciadorPosts.curtirPost(post, usuarioLogado);
        }
        
        ControladorPrincipal.salvarDados();
        atualizarVisualizacao();
        
        if (callbackAtualizacao != null) {
            callbackAtualizacao.run();
        }
    }
    
    private void comentarPost() {
        // TODO: Implementar diálogo de comentário
        JOptionPane.showMessageDialog(this, 
            "Funcionalidade de comentários será implementada", 
            "Em Desenvolvimento", 
            JOptionPane.INFORMATION_MESSAGE);
    }
}

