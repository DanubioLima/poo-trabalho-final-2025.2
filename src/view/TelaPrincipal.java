package view;

import controller.ControladorPrincipal;
import controller.GerenciadorPosts;
import model.Post;
import model.Usuario;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Tela principal da aplicação com feed de posts.
 */
public class TelaPrincipal extends JFrame {
    private Usuario usuarioLogado;
    private JPanel painelFeed;
    private JButton botaoNovoPost;
    private JButton botaoAtualizar;
    private JButton botaoRelatorios;
    private JButton botaoLogout;
    private JLabel labelBemVindo;
    
    public TelaPrincipal(Usuario usuario) {
        this.usuarioLogado = usuario;
        configurarJanela();
        criarComponentes();
        adicionarComponentes();
        configurarEventos();
        atualizarFeed();
    }
    
    private void configurarJanela() {
        setTitle("Rede Social - Feed");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
    }
    
    private void criarComponentes() {
        labelBemVindo = new JLabel("Bem-vindo, " + usuarioLogado.getNome() + "!");
        labelBemVindo.setFont(new Font("Arial", Font.BOLD, 16));
        
        botaoNovoPost = new JButton("Novo Post");
        botaoAtualizar = new JButton("Atualizar Feed");
        botaoRelatorios = new JButton("Relatórios");
        botaoLogout = new JButton("Sair");
        
        painelFeed = new JPanel();
        painelFeed.setLayout(new BoxLayout(painelFeed, BoxLayout.Y_AXIS));
    }
    
    private void adicionarComponentes() {
        // Painel superior (barra de navegação)
        JPanel painelSuperior = new JPanel(new BorderLayout());
        painelSuperior.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        painelSuperior.setBackground(new Color(240, 240, 240));
        
        painelSuperior.add(labelBemVindo, BorderLayout.WEST);
        
        JPanel painelBotoes = new JPanel(new FlowLayout());
        painelBotoes.add(botaoNovoPost);
        painelBotoes.add(botaoAtualizar);
        painelBotoes.add(botaoRelatorios);
        painelBotoes.add(botaoLogout);
        painelSuperior.add(painelBotoes, BorderLayout.EAST);
        
        // Painel do feed (scrollável)
        JScrollPane scrollFeed = new JScrollPane(painelFeed);
        scrollFeed.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollFeed.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollFeed.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        add(painelSuperior, BorderLayout.NORTH);
        add(scrollFeed, BorderLayout.CENTER);
    }
    
    private void configurarEventos() {
        botaoNovoPost.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                criarNovoPost();
            }
        });
        
        botaoAtualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                atualizarFeed();
            }
        });
        
        botaoRelatorios.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exibirRelatorios();
            }
        });
        
        botaoLogout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ControladorPrincipal.realizarLogout();
            }
        });
    }
    
    private void criarNovoPost() {
        DialogNovoPost dialog = new DialogNovoPost(this, usuarioLogado, new Runnable() {
            @Override
            public void run() {
                atualizarFeed();
            }
        });
        dialog.setVisible(true);
    }
    
    private void exibirRelatorios() {
        TelaRelatorios telaRelatorios = new TelaRelatorios(usuarioLogado);
        telaRelatorios.setVisible(true);
    }
    
    /**
     * Atualiza o feed de posts.
     */
    private void atualizarFeed() {
        // Limpar feed atual
        painelFeed.removeAll();
        
        // Obter todos os posts
        java.util.List<Post> posts = GerenciadorPosts.obterFeed();
        
        if (posts.isEmpty()) {
            // Mensagem quando não há posts
            JLabel labelVazio = new JLabel("Nenhum post ainda. Seja o primeiro a postar!");
            labelVazio.setHorizontalAlignment(SwingConstants.CENTER);
            labelVazio.setFont(new Font("Arial", Font.ITALIC, 14));
            labelVazio.setBorder(BorderFactory.createEmptyBorder(50, 10, 50, 10));
            painelFeed.add(labelVazio);
        } else {
            for (Post post : posts) {
                PainelPost painelPost = new PainelPost(post, usuarioLogado, new Runnable() {
                    @Override
                    public void run() {
                        atualizarFeed();
                    }
                });
                painelFeed.add(painelPost);
            }
        }
        
        // Atualizar interface
        painelFeed.revalidate();
        painelFeed.repaint();
    }
}

