package controller;

import model.Usuario;
import model.RedeSocial;
import util.Excecoes;
import java.util.regex.Pattern;

/**
 * Classe responsável pelo gerenciamento de usuários.
 * Lógica de autenticação e cadastro.
 */
public class GerenciadorUsuarios {
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
        "^[A-Za-z0-9+_.-]+@(.+)$"
    );
    
    /**
     * Realiza o cadastro de um novo usuário.
     * @param nome Nome do usuário
     * @param email Email do usuário
     * @param senha Senha do usuário
     * @return Usuário criado
     * @throws Excecoes.DadosInvalidosException Se os dados forem inválidos
     */
    public static Usuario cadastrarUsuario(String nome, String email, String senha) 
            throws Excecoes.DadosInvalidosException {
        
        // Validação de dados
        if (nome == null || nome.trim().isEmpty()) {
            throw new Excecoes.DadosInvalidosException("Nome não pode estar vazio.");
        }
        
        if (email == null || email.trim().isEmpty()) {
            throw new Excecoes.DadosInvalidosException("Email não pode estar vazio.");
        }
        
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            throw new Excecoes.DadosInvalidosException("Email inválido.");
        }
        
        if (senha == null || senha.length() < 4) {
            throw new Excecoes.DadosInvalidosException("Senha deve ter pelo menos 4 caracteres.");
        }
        
        // Verificar se email já está cadastrado
        if (RedeSocial.buscarUsuarioPorEmail(email) != null) {
            throw new Excecoes.DadosInvalidosException("Email já cadastrado.");
        }
        
        // Criar e adicionar usuário
        Usuario novoUsuario = new Usuario(nome.trim(), email.trim(), senha);
        RedeSocial.adicionarUsuario(novoUsuario);
        
        return novoUsuario;
    }
    
    /**
     * Realiza a autenticação de um usuário.
     * @param email Email do usuário
     * @param senha Senha do usuário
     * @return Usuário autenticado
     * @throws Excecoes.AutenticacaoException Se a autenticação falhar
     */
    public static Usuario autenticar(String email, String senha) 
            throws Excecoes.AutenticacaoException {
        
        if (email == null || email.trim().isEmpty()) {
            throw new Excecoes.AutenticacaoException("Email não pode estar vazio.");
        }
        
        if (senha == null || senha.isEmpty()) {
            throw new Excecoes.AutenticacaoException("Senha não pode estar vazia.");
        }
        
        Usuario usuario = RedeSocial.buscarUsuarioPorEmail(email.trim());
        
        if (usuario == null) {
            throw new Excecoes.AutenticacaoException("Usuário não encontrado.");
        }
        
        if (!usuario.verificarSenha(senha)) {
            throw new Excecoes.AutenticacaoException("Senha incorreta.");
        }
        
        return usuario;
    }
}

