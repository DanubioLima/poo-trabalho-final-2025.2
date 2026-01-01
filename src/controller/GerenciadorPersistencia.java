package controller;

import model.RedeSocial;
import model.Usuario;
import model.Post;
import util.Excecoes;
import java.io.*;
import java.util.List;

/**
 * Classe responsável pela persistência de dados usando serialização.
 * Trata exceções relacionadas à manipulação de arquivos.
 */
public class GerenciadorPersistencia {
    private static final String ARQUIVO_USUARIOS = "dados/usuarios.ser";
    private static final String ARQUIVO_POSTS = "dados/posts.ser";
    
    /**
     * Salva todos os dados da rede social em arquivos.
     * @throws Excecoes.PersistenciaException Se houver erro ao salvar
     */
    public static void salvarDados() throws Excecoes.PersistenciaException {
        try {
            // Criar diretório se não existir
            File diretorio = new File("dados");
            if (!diretorio.exists()) {
                diretorio.mkdirs();
            }
            
            // Salvar usuários
            salvarObjeto(RedeSocial.getUsuarios(), ARQUIVO_USUARIOS);
            
            // Salvar posts
            salvarObjeto(RedeSocial.getPosts(), ARQUIVO_POSTS);
            
        } catch (IOException e) {
            throw new Excecoes.PersistenciaException("Erro ao salvar dados: " + e.getMessage());
        }
    }
    
    /**
     * Carrega todos os dados da rede social de arquivos.
     * @throws Excecoes.PersistenciaException Se houver erro ao carregar
     */
    @SuppressWarnings("unchecked")
    public static void carregarDados() throws Excecoes.PersistenciaException {
        try {
            // Carregar usuários
            List<Usuario> usuarios = (List<Usuario>) carregarObjeto(ARQUIVO_USUARIOS);
            if (usuarios != null) {
                RedeSocial.setUsuarios(usuarios);
            }
            
            // Carregar posts
            List<Post> posts = (List<Post>) carregarObjeto(ARQUIVO_POSTS);
            if (posts != null) {
                RedeSocial.setPosts(posts);
            }
            
        } catch (IOException | ClassNotFoundException e) {
            throw new Excecoes.PersistenciaException("Erro ao carregar dados: " + e.getMessage());
        }
    }
    
    /**
     * Salva um objeto em arquivo usando serialização.
     */
    private static void salvarObjeto(Object objeto, String arquivo) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(arquivo))) {
            oos.writeObject(objeto);
        }
    }
    
    /**
     * Carrega um objeto de arquivo usando deserialização.
     */
    private static Object carregarObjeto(String arquivo) throws IOException, ClassNotFoundException {
        File file = new File(arquivo);
        if (!file.exists()) {
            return null;
        }
        
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(arquivo))) {
            return ois.readObject();
        }
    }
}

