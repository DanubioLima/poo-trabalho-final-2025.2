package view;

import controller.GerenciadorPosts;
import controller.ControladorPrincipal;
import model.Post;
import model.Comentario;
import model.Usuario;
import util.Excecoes;
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
    private JPanel painelComentarios;
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
        
        painelComentarios = new JPanel();
        painelComentarios.setLayout(new BoxLayout(painelComentarios, BoxLayout.Y_AXIS));
        painelComentarios.setBackground(Color.WHITE);
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
        
        // Painel de comentários com scroll
        JScrollPane scrollComentarios = new JScrollPane(painelComentarios);
        scrollComentarios.setBorder(BorderFactory.createTitledBorder("Comentários"));
        scrollComentarios.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollComentarios.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollComentarios.setPreferredSize(new Dimension(0, 150));
        
        // Painel inferior (curtidas e botões)
        JPanel painelInferior = new JPanel(new BorderLayout());
        painelInferior.add(labelCurtidas, BorderLayout.WEST);
        
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.LEFT));
        painelBotoes.add(botaoCurtir);
        painelBotoes.add(botaoComentar);
        painelInferior.add(painelBotoes, BorderLayout.EAST);
        
        // Painel central combinado (conteúdo + comentários)
        JPanel painelCentral = new JPanel(new BorderLayout());
        painelCentral.add(painelConteudo, BorderLayout.NORTH);
        painelCentral.add(scrollComentarios, BorderLayout.CENTER);
        
        add(painelSuperior, BorderLayout.NORTH);
        add(painelCentral, BorderLayout.CENTER);
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
        int numComentarios = post.getNumeroComentarios();
        labelCurtidas.setText(numCurtidas + " curtida(s) | " + numComentarios + " comentário(s)");
        
        // Atualizar botão de curtir
        if (post.foiCurtidoPor(usuarioLogado)) {
            botaoCurtir.setText("Descurtir");
            botaoCurtir.setForeground(Color.RED);
        } else {
            botaoCurtir.setText("Curtir");
            botaoCurtir.setForeground(Color.BLACK);
        }
        
        // Atualizar painel de comentários
        atualizarComentarios();
    }
    
    private void atualizarComentarios() {
        painelComentarios.removeAll();
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        
        for (Comentario comentario : post.getComentarios()) {
            // Painel para cada comentário
            JPanel painelComentario = new JPanel(new BorderLayout());
            painelComentario.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
            ));
            painelComentario.setBackground(new Color(245, 245, 245));
            painelComentario.setMaximumSize(new Dimension(Integer.MAX_VALUE, 100));
            
            // Label do autor (negrito)
            JLabel labelAutorComentario = new JLabel(comentario.getAutor().getNome());
            labelAutorComentario.setFont(new Font("Arial", Font.BOLD, 11));
            
            // Label da data
            JLabel labelDataComentario = new JLabel(comentario.getDataCriacao().format(formatter));
            labelDataComentario.setFont(new Font("Arial", Font.ITALIC, 9));
            labelDataComentario.setForeground(Color.GRAY);
            
            // Painel superior do comentário (autor e data)
            JPanel painelSuperiorComentario = new JPanel(new BorderLayout());
            painelSuperiorComentario.setOpaque(false);
            painelSuperiorComentario.add(labelAutorComentario, BorderLayout.WEST);
            painelSuperiorComentario.add(labelDataComentario, BorderLayout.EAST);
            
            // Label do conteúdo do comentário
            JLabel labelConteudoComentario = new JLabel("<html><body style='width: 350px'>" + 
                                                        comentario.getConteudo().replace("\n", "<br>") + 
                                                        "</body></html>");
            labelConteudoComentario.setFont(new Font("Arial", Font.PLAIN, 11));
            
            painelComentario.add(painelSuperiorComentario, BorderLayout.NORTH);
            painelComentario.add(labelConteudoComentario, BorderLayout.CENTER);
            
            painelComentarios.add(painelComentario);
            painelComentarios.add(Box.createVerticalStrut(5));
        }
        
        // Se não houver comentários, mostrar mensagem
        if (post.getComentarios().isEmpty()) {
            JLabel labelSemComentarios = new JLabel("Nenhum comentário ainda.");
            labelSemComentarios.setFont(new Font("Arial", Font.ITALIC, 10));
            labelSemComentarios.setForeground(Color.GRAY);
            labelSemComentarios.setAlignmentX(Component.LEFT_ALIGNMENT);
            painelComentarios.add(labelSemComentarios);
        }
        
        painelComentarios.revalidate();
        painelComentarios.repaint();
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
        // Criar diálogo para comentário
        JDialog dialogComentario = new JDialog((JFrame) SwingUtilities.getWindowAncestor(this), 
                                                "Novo Comentário", true);
        dialogComentario.setSize(500, 250);
        dialogComentario.setLocationRelativeTo(this);
        dialogComentario.setLayout(new BorderLayout());
        
        // Área de texto para o comentário
        JTextArea areaComentario = new JTextArea(8, 40);
        areaComentario.setLineWrap(true);
        areaComentario.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(areaComentario);
        
        // Painel do conteúdo
        JPanel painelConteudo = new JPanel(new BorderLayout());
        painelConteudo.setBorder(BorderFactory.createTitledBorder("Escreva seu comentário:"));
        painelConteudo.add(scrollPane, BorderLayout.CENTER);
        
        // Botões
        JButton botaoComentar = new JButton("Comentar");
        JButton botaoCancelar = new JButton("Cancelar");
        
        JPanel painelBotoes = new JPanel(new FlowLayout());
        painelBotoes.add(botaoComentar);
        painelBotoes.add(botaoCancelar);
        
        // Adicionar componentes ao diálogo
        dialogComentario.add(painelConteudo, BorderLayout.CENTER);
        dialogComentario.add(painelBotoes, BorderLayout.SOUTH);
        
        // Evento do botão comentar
        botaoComentar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String conteudo = areaComentario.getText().trim();
                
                if (conteudo.isEmpty()) {
                    JOptionPane.showMessageDialog(dialogComentario, 
                        "O comentário não pode estar vazio.", 
                        "Aviso", 
                        JOptionPane.WARNING_MESSAGE);
                    return;
                }
                
                try {
                    GerenciadorPosts.comentarPost(post, conteudo, usuarioLogado);
                    ControladorPrincipal.salvarDados();
                    JOptionPane.showMessageDialog(dialogComentario, 
                        "Comentário adicionado com sucesso!", 
                        "Sucesso", 
                        JOptionPane.INFORMATION_MESSAGE);
                    
                    dialogComentario.dispose();
                    
                    // Atualizar visualização e callback
                    atualizarVisualizacao();
                    if (callbackAtualizacao != null) {
                        callbackAtualizacao.run();
                    }
                } catch (Excecoes.DadosInvalidosException ex) {
                    JOptionPane.showMessageDialog(dialogComentario, 
                        ex.getMessage(), 
                        "Erro", 
                        JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        // Evento do botão cancelar
        botaoCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialogComentario.dispose();
            }
        });
        
        // Mostrar diálogo
        dialogComentario.setVisible(true);
    }
}

