import controller.ControladorPrincipal;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * Classe principal da aplicação.
 * Inicia a rede social e configura o ambiente Swing.
 */
public class Main {
    public static void main(String[] args) {
        // Configurar look and feel do sistema
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("Erro ao configurar look and feel: " + e.getMessage());
        }
        
        // Iniciar aplicação na thread de eventos do Swing
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                ControladorPrincipal.iniciar();
            }
        });
    }
}

