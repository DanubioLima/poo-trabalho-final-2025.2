package util;

import java.util.UUID;

/**
 * Classe utilitária para gerar IDs únicos usando UUID.
 * Simplifica a geração de IDs sem necessidade de persistir contadores.
 */
public class GeradorID {
    
    /**
     * Gera um ID único para usuário usando UUID.
     * @return ID único de usuário (UUID como String)
     */
    public static String gerarIDUsuario() {
        return UUID.randomUUID().toString();
    }
    
    /**
     * Gera um ID único para post usando UUID.
     * @return ID único de post (UUID como String)
     */
    public static String gerarIDPost() {
        return UUID.randomUUID().toString();
    }
    
    /**
     * Gera um ID único para comentário usando UUID.
     * @return ID único de comentário (UUID como String)
     */
    public static String gerarIDComentario() {
        return UUID.randomUUID().toString();
    }
}
