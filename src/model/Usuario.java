package model;

import util.GeradorID;
import java.io.Serializable;

/**
 * Classe que representa um usuário da rede social.
 * Totalmente encapsulada com atributos privados e métodos de acesso.
 */
public class Usuario implements Serializable {
    private static final long serialVersionUID = 1L;
    private String id;
    private String nome;
    private String email;
    private String senha;
    
    /**
     * Construtor completo de Usuario.
     * @param nome Nome do usuário
     * @param email Email do usuário
     * @param senha Senha do usuário
     */
    public Usuario(String nome, String email, String senha) {
        this.id = GeradorID.gerarIDUsuario();
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }
    
    /**
     * Construtor sobrecarregado para carregar usuário existente.
     * @param id ID do usuário (UUID)
     * @param nome Nome do usuário
     * @param email Email do usuário
     * @param senha Senha do usuário
     */
    public Usuario(String id, String nome, String email, String senha) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
    }
    
    // Getters (encapsulamento)
    public String getId() {
        return id;
    }
    
    public String getNome() {
        return nome;
    }
    
    public String getEmail() {
        return email;
    }
    
    public String getSenha() {
        return senha;
    }
    
    // Setters (encapsulamento)
    public void setId(String id) {
        this.id = id;
    }
    
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public void setSenha(String senha) {
        this.senha = senha;
    }
    
    /**
     * Verifica se a senha fornecida corresponde à senha do usuário.
     * @param senhaFornecida Senha a ser verificada
     * @return true se a senha estiver correta, false caso contrário
     */
    public boolean verificarSenha(String senhaFornecida) {
        return this.senha.equals(senhaFornecida);
    }
    
    /**
     * Sobrescrita do método toString().
     */
    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
    
    /**
     * Sobrescrita do método equals().
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Usuario usuario = (Usuario) obj;
        return id != null && id.equals(usuario.id) || email.equals(usuario.email);
    }
    
    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : email.hashCode();
    }
}

