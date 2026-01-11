package view;

import controller.GerenciadorPosts;
import model.Post;
import model.Usuario;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Tela de relatórios do usuário logado.
 * Mostra estatísticas e posts do usuário.
 */
public class TelaRelatorios extends JFrame {
    private Usuario usuarioLogado;
    private JPanel painelPosts;
    private JPanel painelEstatisticas;
    private JButton botaoVoltar;
    
    public TelaRelatorios(Usuario usuario) {
        this.usuarioLogado = usuario;
        configurarJanela();
        criarComponentes();
        adicionarComponentes();
        configurarEventos();
        atualizarRelatorios();
    }
    
    private void configurarJanela() {
        setTitle("Relatórios - " + usuarioLogado.getNome());
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
    }
    
    private void criarComponentes() {
        botaoVoltar = new JButton("Voltar");
        painelPosts = new JPanel();
        painelPosts.setLayout(new BoxLayout(painelPosts, BoxLayout.Y_AXIS));
        painelEstatisticas = new JPanel();
    }
    
    private void adicionarComponentes() {
        // Painel superior com estatísticas
        atualizarPainelEstatisticas();
        
        // Painel do feed (scrollável)
        JScrollPane scrollFeed = new JScrollPane(painelPosts);
        scrollFeed.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollFeed.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollFeed.setBorder(BorderFactory.createTitledBorder("Meus Posts"));
        
        // Painel inferior com botão voltar
        JPanel painelInferior = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        painelInferior.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        painelInferior.add(botaoVoltar);
        
        add(painelEstatisticas, BorderLayout.NORTH);
        add(scrollFeed, BorderLayout.CENTER);
        add(painelInferior, BorderLayout.SOUTH);
    }
    
    private void atualizarPainelEstatisticas() {
        painelEstatisticas.removeAll();
        painelEstatisticas.setLayout(new GridBagLayout());
        painelEstatisticas.setBorder(BorderFactory.createTitledBorder("Estatísticas"));
        painelEstatisticas.setBackground(new Color(240, 240, 240));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        
        // Calcular estatísticas
        int totalCurtidas = GerenciadorPosts.calcularTotalCurtidas(usuarioLogado);
        int totalComentarios = GerenciadorPosts.calcularTotalComentarios(usuarioLogado);
        int totalPosts = GerenciadorPosts.obterPostsDoUsuario(usuarioLogado).size();
        
        // Título
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        JLabel titulo = new JLabel("Estatísticas dos Meus Posts");
        titulo.setFont(new Font("Arial", Font.BOLD, 16));
        painelEstatisticas.add(titulo, gbc);
        
        // Total de Posts
        gbc.gridwidth = 1;
        gbc.gridy = 1;
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.WEST;
        JLabel labelTotalPosts = new JLabel("Total de Posts:");
        labelTotalPosts.setFont(new Font("Arial", Font.PLAIN, 12));
        painelEstatisticas.add(labelTotalPosts, gbc);
        
        gbc.gridx = 1;
        JLabel valorTotalPosts = new JLabel(String.valueOf(totalPosts));
        valorTotalPosts.setFont(new Font("Arial", Font.BOLD, 12));
        painelEstatisticas.add(valorTotalPosts, gbc);
        
        // Total de Curtidas
        gbc.gridy = 2;
        gbc.gridx = 0;
        JLabel labelTotalCurtidas = new JLabel("Total de Curtidas:");
        labelTotalCurtidas.setFont(new Font("Arial", Font.PLAIN, 12));
        painelEstatisticas.add(labelTotalCurtidas, gbc);
        
        gbc.gridx = 1;
        JLabel valorTotalCurtidas = new JLabel(String.valueOf(totalCurtidas));
        valorTotalCurtidas.setFont(new Font("Arial", Font.BOLD, 12));
        valorTotalCurtidas.setForeground(new Color(0, 100, 0));
        painelEstatisticas.add(valorTotalCurtidas, gbc);
        
        // Total de Comentários
        gbc.gridy = 3;
        gbc.gridx = 0;
        JLabel labelTotalComentarios = new JLabel("Total de Comentários:");
        labelTotalComentarios.setFont(new Font("Arial", Font.PLAIN, 12));
        painelEstatisticas.add(labelTotalComentarios, gbc);
        
        gbc.gridx = 1;
        JLabel valorTotalComentarios = new JLabel(String.valueOf(totalComentarios));
        valorTotalComentarios.setFont(new Font("Arial", Font.BOLD, 12));
        valorTotalComentarios.setForeground(new Color(0, 0, 150));
        painelEstatisticas.add(valorTotalComentarios, gbc);
        
        painelEstatisticas.revalidate();
        painelEstatisticas.repaint();
    }
    
    private void configurarEventos() {
        botaoVoltar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }
    
    private void atualizarRelatorios() {
        // Atualizar estatísticas
        atualizarPainelEstatisticas();
        
        // Limpar painel atual
        painelPosts.removeAll();
        
        // Obter posts do usuário
        java.util.List<Post> postsDoUsuario = GerenciadorPosts.obterPostsDoUsuario(usuarioLogado);
        
        if (postsDoUsuario.isEmpty()) {
            // Mensagem quando não há posts
            JLabel labelVazio = new JLabel("Você ainda não fez nenhum post.");
            labelVazio.setHorizontalAlignment(SwingConstants.CENTER);
            labelVazio.setFont(new Font("Arial", Font.ITALIC, 14));
            labelVazio.setBorder(BorderFactory.createEmptyBorder(50, 10, 50, 10));
            painelPosts.add(labelVazio);
        } else {
            // Exibir posts usando PainelPost (reaproveitando componente)
            for (Post post : postsDoUsuario) {
                PainelPost painelPost = new PainelPost(post, usuarioLogado, new Runnable() {
                    @Override
                    public void run() {
                        atualizarRelatorios();
                    }
                });
                
                painelPost.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
                    BorderFactory.createEmptyBorder(10, 10, 10, 10)
                ));
                
                painelPost.setMaximumSize(new Dimension(Integer.MAX_VALUE, painelPost.getPreferredSize().height));
                painelPosts.add(painelPost);
                painelPosts.add(Box.createVerticalStrut(10));
            }
        }
        
        // Atualizar interface
        painelPosts.revalidate();
        painelPosts.repaint();
    }
}

