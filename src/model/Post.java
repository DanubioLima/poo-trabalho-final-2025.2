package model;

import util.GeradorID;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe que representa um post na rede social.
 * Herda de Conteudo (demonstra herança).
 */
public class Post extends Conteudo {
    private static final long serialVersionUID = 1L;
    private List<Usuario> curtidas;
    private List<Comentario> comentarios;
    
    /**
     * Construtor de Post.
     * @param conteudo Texto do post
     * @param autor Usuário autor do post
     */
    public Post(String conteudo, Usuario autor) {
        super(conteudo, autor);
        this.id = GeradorID.gerarIDPost();
        this.curtidas = new ArrayList<>();
        this.comentarios = new ArrayList<>();
    }
    
    /**
     * Construtor sobrecarregado para carregar post existente.
     * @param id ID do post (UUID)
     * @param conteudo Texto do post
     * @param autor Usuário autor do post
     * @param dataCriacao Data de criação do post
     * @param curtidas Lista de usuários que curtiram
     * @param comentarios Lista de comentários
     */
    public Post(String id, String conteudo, Usuario autor, LocalDateTime dataCriacao,
                List<Usuario> curtidas, List<Comentario> comentarios) {
        super(conteudo, autor);
        this.id = id;
        this.dataCriacao = dataCriacao;
        this.curtidas = curtidas != null ? new ArrayList<>(curtidas) : new ArrayList<>();
        this.comentarios = comentarios != null ? new ArrayList<>(comentarios) : new ArrayList<>();
    }
    
    /**
     * Adiciona uma curtida ao post.
     * @param usuario Usuário que está curtindo
     * @return true se a curtida foi adicionada, false se o usuário já havia curtido
     */
    public boolean curtir(Usuario usuario) {
        if (!curtidas.contains(usuario)) {
            curtidas.add(usuario);
            return true;
        }
        return false;
    }
    
    /**
     * Remove uma curtida do post.
     * @param usuario Usuário que está descurtindo
     * @return true se a curtida foi removida, false se o usuário não havia curtido
     */
    public boolean descurtir(Usuario usuario) {
        return curtidas.remove(usuario);
    }
    
    /**
     * Verifica se um usuário curtiu o post.
     * @param usuario Usuário a verificar
     * @return true se o usuário curtiu, false caso contrário
     */
    public boolean foiCurtidoPor(Usuario usuario) {
        return curtidas.contains(usuario);
    }
    
    /**
     * Adiciona um comentário ao post.
     * @param comentario Comentário a ser adicionado
     */
    public void adicionarComentario(Comentario comentario) {
        comentarios.add(comentario);
    }
    
    // Getters
    public List<Usuario> getCurtidas() {
        return new ArrayList<>(curtidas); // Retorna cópia para encapsulamento
    }
    
    public List<Comentario> getComentarios() {
        return new ArrayList<>(comentarios); // Retorna cópia para encapsulamento
    }
    
    public int getNumeroCurtidas() {
        return curtidas.size();
    }
    
    public int getNumeroComentarios() {
        return comentarios.size();
    }
    
    /**
     * Implementação do método abstrato obterResumo().
     * Sobrescrita do método da classe pai.
     */
    @Override
    public String obterResumo() {
        if (conteudo.length() > 100) {
            return conteudo.substring(0, 100) + "...";
        }
        return conteudo;
    }
    
    /**
     * Sobrescrita do método toString().
     */
    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", autor=" + autor.getNome() +
                ", conteudo='" + conteudo + '\'' +
                ", curtidas=" + curtidas.size() +
                ", comentarios=" + comentarios.size() +
                ", dataCriacao=" + dataCriacao +
                '}';
    }
}

