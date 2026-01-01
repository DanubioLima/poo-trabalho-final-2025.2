package model;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe principal do modelo que gerencia a rede social.
 * Utiliza atributos de classe (estáticos) e coleções.
 */
public class RedeSocial {
    private static List<Usuario> usuarios;
    private static List<Post> posts;
    
    // Inicialização estática dos atributos de classe
    static {
        usuarios = new ArrayList<>();
        posts = new ArrayList<>();
    }
    
    /**
     * Adiciona um usuário à rede social.
     * @param usuario Usuário a ser adicionado
     */
    public static void adicionarUsuario(Usuario usuario) {
        if (usuario != null && !usuarios.contains(usuario)) {
            usuarios.add(usuario);
        }
    }
    
    /**
     * Busca um usuário por email.
     * @param email Email do usuário
     * @return Usuário encontrado ou null
     */
    public static Usuario buscarUsuarioPorEmail(String email) {
        for (Usuario usuario : usuarios) {
            if (usuario.getEmail().equals(email)) {
                return usuario;
            }
        }
        return null;
    }
    
    /**
     * Busca um usuário por ID.
     * @param id ID do usuário (UUID)
     * @return Usuário encontrado ou null
     */
    public static Usuario buscarUsuarioPorId(String id) {
        for (Usuario usuario : usuarios) {
            if (usuario.getId().equals(id)) {
                return usuario;
            }
        }
        return null;
    }
    
    /**
     * Adiciona um post à rede social.
     * @param post Post a ser adicionado
     */
    public static void adicionarPost(Post post) {
        if (post != null) {
            posts.add(0, post); // Adiciona no início para mostrar mais recentes primeiro
        }
    }
    
    /**
     * Obtém todos os posts ordenados por data (mais recentes primeiro).
     * @return Lista de posts
     */
    public static List<Post> obterTodosPosts() {
        return new ArrayList<>(posts); // Retorna cópia para encapsulamento
    }
    
    /**
     * Obtém todos os usuários cadastrados.
     * @return Lista de usuários
     */
    public static List<Usuario> obterTodosUsuarios() {
        return new ArrayList<>(usuarios); // Retorna cópia para encapsulamento
    }
    
    /**
     * Limpa todos os dados (útil para testes).
     */
    public static void limparDados() {
        usuarios.clear();
        posts.clear();
    }
    
    /**
     * Define a lista de usuários (útil ao carregar dados salvos).
     * @param usuariosList Lista de usuários
     */
    public static void setUsuarios(List<Usuario> usuariosList) {
        usuarios = usuariosList != null ? new ArrayList<>(usuariosList) : new ArrayList<>();
    }
    
    /**
     * Define a lista de posts (útil ao carregar dados salvos).
     * @param postsList Lista de posts
     */
    public static void setPosts(List<Post> postsList) {
        posts = postsList != null ? new ArrayList<>(postsList) : new ArrayList<>();
    }
    
    /**
     * Obtém a lista de usuários (para serialização).
     * @return Lista de usuários
     */
    public static List<Usuario> getUsuarios() {
        return usuarios;
    }
    
    /**
     * Obtém a lista de posts (para serialização).
     * @return Lista de posts
     */
    public static List<Post> getPosts() {
        return posts;
    }
}

