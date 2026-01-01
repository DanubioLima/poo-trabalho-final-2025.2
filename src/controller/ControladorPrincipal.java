package controller;

import model.Usuario;
import view.TelaLogin;
import view.TelaPrincipal;
import util.Excecoes;
import javax.swing.*;

/**
 * Controlador principal que gerencia a navegação entre telas.
 * Coordena a comunicação entre View e Model.
 */
public class ControladorPrincipal {
    private static Usuario usuarioLogado;
    private static JFrame telaAtual;
    
    /**
     * Inicializa a aplicação carregando dados e exibindo tela de login.
     */
    public static void iniciar() {
        try {
            // Carregar dados salvos
            GerenciadorPersistencia.carregarDados();
        } catch (Excecoes.PersistenciaException e) {
            // Se não houver dados salvos, continua normalmente
            System.out.println("Nenhum dado salvo encontrado. Iniciando com dados vazios.");
        }
        
        // Exibir tela de login
        exibirTelaLogin();
    }
    
    /**
     * Exibe a tela de login.
     */
    public static void exibirTelaLogin() {
        if (telaAtual != null) {
            telaAtual.dispose();
        }
        telaAtual = new TelaLogin();
        telaAtual.setVisible(true);
    }
    
    /**
     * Exibe a tela de cadastro.
     */
    public static void exibirTelaCadastro() {
        // A tela de cadastro será aberta como diálogo pela TelaLogin
    }
    
    /**
     * Realiza o login do usuário.
     * @param email Email do usuário
     * @param senha Senha do usuário
     * @return true se o login foi bem-sucedido
     */
    public static boolean realizarLogin(String email, String senha) {
        try {
            usuarioLogado = GerenciadorUsuarios.autenticar(email, senha);
            exibirTelaPrincipal();
            return true;
        } catch (Excecoes.AutenticacaoException e) {
            JOptionPane.showMessageDialog(null, 
                e.getMessage(), 
                "Erro de Autenticação", 
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    /**
     * Realiza o cadastro de um novo usuário.
     * @param nome Nome do usuário
     * @param email Email do usuário
     * @param senha Senha do usuário
     * @return true se o cadastro foi bem-sucedido
     */
    public static boolean realizarCadastro(String nome, String email, String senha) {
        try {
            GerenciadorUsuarios.cadastrarUsuario(nome, email, senha);
            salvarDados();
            JOptionPane.showMessageDialog(null, 
                "Cadastro realizado com sucesso!", 
                "Sucesso", 
                JOptionPane.INFORMATION_MESSAGE);
            return true;
        } catch (Excecoes.DadosInvalidosException e) {
            JOptionPane.showMessageDialog(null, 
                e.getMessage(), 
                "Erro no Cadastro", 
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    
    /**
     * Exibe a tela principal após login.
     */
    public static void exibirTelaPrincipal() {
        if (telaAtual != null) {
            telaAtual.dispose();
        }
        telaAtual = new TelaPrincipal(usuarioLogado);
        telaAtual.setVisible(true);
    }
    
    /**
     * Realiza o logout do usuário.
     */
    public static void realizarLogout() {
        salvarDados();
        usuarioLogado = null;
        exibirTelaLogin();
    }
    
    /**
     * Obtém o usuário atualmente logado.
     * @return Usuário logado
     */
    public static Usuario getUsuarioLogado() {
        return usuarioLogado;
    }
    
    /**
     * Salva os dados da aplicação.
     */
    public static void salvarDados() {
        try {
            GerenciadorPersistencia.salvarDados();
        } catch (Excecoes.PersistenciaException e) {
            JOptionPane.showMessageDialog(null, 
                "Erro ao salvar dados: " + e.getMessage(), 
                "Erro", 
                JOptionPane.ERROR_MESSAGE);
        }
    }
}

