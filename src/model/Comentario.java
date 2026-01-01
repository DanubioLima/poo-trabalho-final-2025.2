package model;

import util.GeradorID;

/**
 * Classe que representa um comentário em um post.
 * Herda de Conteudo (demonstra herança).
 */
public class Comentario extends Conteudo {
    private static final long serialVersionUID = 1L;
    
    /**
     * Construtor de Comentario.
     * @param conteudo Texto do comentário
     * @param autor Usuário autor do comentário
     */
    public Comentario(String conteudo, Usuario autor) {
        super(conteudo, autor);
        this.id = GeradorID.gerarIDComentario();
    }
    
    /**
     * Construtor sobrecarregado para carregar comentário existente.
     * @param id ID do comentário (UUID)
     * @param conteudo Texto do comentário
     * @param autor Usuário autor do comentário
     * @param dataCriacao Data de criação do comentário
     */
    public Comentario(String id, String conteudo, Usuario autor, java.time.LocalDateTime dataCriacao) {
        super(conteudo, autor);
        this.id = id;
        this.dataCriacao = dataCriacao;
    }
    
    /**
     * Implementação do método abstrato obterResumo().
     * Sobrescrita do método da classe pai.
     */
    @Override
    public String obterResumo() {
        // TODO: Implementar lógica para retornar resumo do comentário
        if (conteudo.length() > 50) {
            return conteudo.substring(0, 50) + "...";
        }
        return conteudo;
    }
    
    /**
     * Sobrescrita do método toString().
     */
    @Override
    public String toString() {
        return "Comentario{" +
                "id=" + id +
                ", autor=" + autor.getNome() +
                ", conteudo='" + conteudo + '\'' +
                ", dataCriacao=" + dataCriacao +
                '}';
    }
}

