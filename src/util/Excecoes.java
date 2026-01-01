package util;

/**
 * Classe contendo exceções customizadas para o sistema.
 */
public class Excecoes {
    
    /**
     * Exceção lançada quando um usuário não é encontrado.
     */
    public static class UsuarioNaoEncontradoException extends Exception {
        public UsuarioNaoEncontradoException(String mensagem) {
            super(mensagem);
        }
    }
    
    /**
     * Exceção lançada quando dados inválidos são fornecidos.
     */
    public static class DadosInvalidosException extends Exception {
        public DadosInvalidosException(String mensagem) {
            super(mensagem);
        }
    }
    
    /**
     * Exceção lançada quando há erro na autenticação.
     */
    public static class AutenticacaoException extends Exception {
        public AutenticacaoException(String mensagem) {
            super(mensagem);
        }
    }
    
    /**
     * Exceção lançada quando há erro na persistência de dados.
     */
    public static class PersistenciaException extends Exception {
        public PersistenciaException(String mensagem) {
            super(mensagem);
        }
    }
}

