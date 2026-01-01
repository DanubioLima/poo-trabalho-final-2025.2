package model;

import java.time.LocalDateTime;
import java.io.Serializable;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Classe abstrata base para Post e Comentario.
 * Demonstra herança e polimorfismo.
 */
public abstract class Conteudo implements Serializable {
    private static final long serialVersionUID = 1L;
    protected String id;
    protected String conteudo;
    protected Usuario autor;
    protected transient LocalDateTime dataCriacao; // transient para serialização customizada
    
    /**
     * Construtor da classe abstrata Conteudo.
     * @param conteudo Texto do conteúdo
     * @param autor Usuário autor do conteúdo
     */
    public Conteudo(String conteudo, Usuario autor) {
        this.conteudo = conteudo;
        this.autor = autor;
        this.dataCriacao = LocalDateTime.now();
    }
    
    // Getters
    public String getId() {
        return id;
    }
    
    public String getConteudo() {
        return conteudo;
    }
    
    public Usuario getAutor() {
        return autor;
    }
    
    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }
    
    // Setters
    public void setId(String id) {
        this.id = id;
    }
    
    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }
    
    public void setAutor(Usuario autor) {
        this.autor = autor;
    }
    
    public void setDataCriacao(LocalDateTime dataCriacao) {
        this.dataCriacao = dataCriacao;
    }
    
    /**
     * Método abstrato para obter resumo do conteúdo.
     * Deve ser implementado pelas classes filhas (sobrescrita).
     */
    public abstract String obterResumo();
    
    /**
     * Sobrescrita do método toString().
     */
    @Override
    public abstract String toString();
    
    /**
     * Sobrescrita do método equals().
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Conteudo conteudo = (Conteudo) obj;
        return id != null && id.equals(conteudo.id);
    }
    
    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
    
    /**
     * Serialização customizada para LocalDateTime.
     */
    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        if (dataCriacao != null) {
            out.writeObject(dataCriacao.toString());
        } else {
            out.writeObject(null);
        }
    }
    
    /**
     * Deserialização customizada para LocalDateTime.
     */
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        String dataStr = (String) in.readObject();
        if (dataStr != null) {
            dataCriacao = LocalDateTime.parse(dataStr);
        }
    }
}

