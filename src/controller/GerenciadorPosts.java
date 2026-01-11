package controller;

import model.Post;
import model.Usuario;
import model.Comentario;
import model.RedeSocial;
import util.Excecoes;

/**
 * Classe responsável pelo gerenciamento de posts.
 * Lógica de criação e interação com posts.
 */
public class GerenciadorPosts {
    
    /**
     * Cria um novo post.
     * @param conteudo Conteúdo do post
     * @param autor Usuário autor do post
     * @return Post criado
     * @throws Excecoes.DadosInvalidosException Se os dados forem inválidos
     */
    public static Post criarPost(String conteudo, Usuario autor) 
            throws Excecoes.DadosInvalidosException {
        
        if (autor == null) {
            throw new Excecoes.DadosInvalidosException("Autor não pode ser nulo.");
        }
        
        if (conteudo == null || conteudo.trim().isEmpty()) {
            throw new Excecoes.DadosInvalidosException("Conteúdo do post não pode estar vazio.");
        }
        
        if (conteudo.length() > 500) {
            throw new Excecoes.DadosInvalidosException("Post não pode ter mais de 500 caracteres.");
        }
        
        Post novoPost = new Post(conteudo.trim(), autor);
        RedeSocial.adicionarPost(novoPost);
        
        return novoPost;
    }
    
    /**
     * Adiciona uma curtida a um post.
     * @param post Post a ser curtido
     * @param usuario Usuário que está curtindo
     * @return true se a curtida foi adicionada, false se já havia curtido
     */
    public static boolean curtirPost(Post post, Usuario usuario) {
        if (post == null || usuario == null) {
            return false;
        }
        return post.curtir(usuario);
    }
    
    /**
     * Remove uma curtida de um post.
     * @param post Post a ser descurtido
     * @param usuario Usuário que está descurtindo
     * @return true se a curtida foi removida, false se não havia curtido
     */
    public static boolean descurtirPost(Post post, Usuario usuario) {
        if (post == null || usuario == null) {
            return false;
        }
        return post.descurtir(usuario);
    }
    
    /**
     * Adiciona um comentário a um post.
     * @param post Post a ser comentado
     * @param conteudo Conteúdo do comentário
     * @param autor Usuário autor do comentário
     * @return Comentário criado
     * @throws Excecoes.DadosInvalidosException Se os dados forem inválidos
     */
    public static Comentario comentarPost(Post post, String conteudo, Usuario autor) 
            throws Excecoes.DadosInvalidosException {
        
        if (post == null) {
            throw new Excecoes.DadosInvalidosException("Post não pode ser nulo.");
        }
        
        if (autor == null) {
            throw new Excecoes.DadosInvalidosException("Autor não pode ser nulo.");
        }
        
        if (conteudo == null || conteudo.trim().isEmpty()) {
            throw new Excecoes.DadosInvalidosException("Comentário não pode estar vazio.");
        }
        
        if (conteudo.length() > 300) {
            throw new Excecoes.DadosInvalidosException("Comentário não pode ter mais de 300 caracteres.");
        }
        
        Comentario novoComentario = new Comentario(conteudo.trim(), autor);
        post.adicionarComentario(novoComentario);
        
        return novoComentario;
    }
    
    /**
     * Obtém todos os posts ordenados por data.
     * @return Lista de posts
     */
    public static java.util.List<Post> obterFeed() {
        return RedeSocial.obterTodosPosts();
    }
    
    /**
     * Obtém todos os posts de um usuário específico.
     * @param usuario Usuário cujos posts serão retornados
     * @return Lista de posts do usuário
     */
    public static java.util.List<Post> obterPostsDoUsuario(Usuario usuario) {
        return RedeSocial.obterPostsDoUsuario(usuario);
    }
    
    /**
     * Calcula o total de curtidas de todos os posts de um usuário.
     * @param usuario Usuário cujos posts serão analisados
     * @return Total de curtidas
     */
    public static int calcularTotalCurtidas(Usuario usuario) {
        java.util.List<Post> postsDoUsuario = obterPostsDoUsuario(usuario);
        int total = 0;
        for (Post post : postsDoUsuario) {
            total += post.getNumeroCurtidas();
        }
        return total;
    }
    
    /**
     * Calcula o total de comentários de todos os posts de um usuário.
     * @param usuario Usuário cujos posts serão analisados
     * @return Total de comentários
     */
    public static int calcularTotalComentarios(Usuario usuario) {
        java.util.List<Post> postsDoUsuario = obterPostsDoUsuario(usuario);
        int total = 0;
        for (Post post : postsDoUsuario) {
            total += post.getNumeroComentarios();
        }
        return total;
    }
}

