package ui;

import logic.RiskCalculator;
import model.SurveyResponse;
import db.DatabaseManager;

import javax.swing.*;
import java.awt.*;

public class MainMenu extends JFrame {
    
    private DatabaseManager dbManager;
    
    public MainMenu() {
        dbManager = new DatabaseManager();
        dbManager.crearTabla();
        
        setTitle("Sistema de Evaluación de Salud Mental - ODS 3");
        setSize(700, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        
        initComponents();
    }
    
    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout(20, 20));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        mainPanel.setBackground(new Color(240, 245, 250));
        
        // ===== PANEL SUPERIOR: Título y descripción =====
        JPanel headerPanel = new JPanel(new GridLayout(3, 1, 0, 10));
        headerPanel.setBackground(new Color(240, 245, 250));
        
        JLabel titulo = new JLabel("Evaluación de Salud Mental");
        titulo.setFont(new Font("Arial", Font.BOLD, 26));
        titulo.setForeground(new Color(30, 60, 120));
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        
        JLabel subtitulo = new JLabel("Análisis de Bienestar Estudiantil - ODS 3");
        subtitulo.setFont(new Font("Arial", Font.PLAIN, 14));
        subtitulo.setForeground(new Color(100, 100, 120));
        subtitulo.setHorizontalAlignment(SwingConstants.CENTER);
        
        JLabel descripcion = new JLabel(
                "<html><p style='text-align: center;'>Esta herramienta evalúa tu bienestar mental basándose en " +
                "estrés académico, ansiedad, sueño y apoyo emocional.<br>" +
                "Responde honestamente para recibir recomendaciones personalizadas.</p></html>");
        descripcion.setFont(new Font("Arial", Font.PLAIN, 11));
        descripcion.setForeground(new Color(80, 80, 100));
        
        headerPanel.add(titulo);
        headerPanel.add(subtitulo);
        headerPanel.add(descripcion);
        
        mainPanel.add(headerPanel, BorderLayout.NORTH);
        
        // ===== PANEL CENTRAL: Botones de acción =====
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBackground(new Color(240, 245, 250));
        
        JPanel btnNuevaEncuesta = crearBotonAccion(
                "Nueva Evaluación Individual",
                "Responder la encuesta y obtener análisis personalizado",
                new Color(52, 168, 224),
                e -> abrirNuevaEncuesta()
        );
        
        JPanel btnDashboard = crearBotonAccion(
                "Ver Dashboard",
                "Análisis estadístico de todas las evaluaciones",
                new Color(102, 187, 106),
                e -> abrirDashboard()
        );
        
        JPanel btnDemo = crearBotonAccion(
                "Exportar Evaluaciones",
                "Genera un reporte de todas las evaluaciones realizadas",
                new Color(255, 167, 38),
                e -> exportarEvaluaciones()
        );
        
        buttonPanel.add(btnNuevaEncuesta);
        buttonPanel.add(Box.createVerticalStrut(15));
        buttonPanel.add(btnDashboard);
        buttonPanel.add(Box.createVerticalStrut(15));
        buttonPanel.add(btnDemo);
        
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        
        // ===== PANEL INFERIOR: Información =====
        JPanel footerPanel = new JPanel(new BorderLayout());
        footerPanel.setBackground(new Color(240, 245, 250));
        footerPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(200, 200, 200)));
        
        JLabel footerText = new JLabel("Proyecto de Evaluación de Salud Mental | Universidad", 
                SwingConstants.CENTER);
        footerText.setFont(new Font("Arial", Font.PLAIN, 10));
        footerText.setForeground(new Color(140, 140, 160));
        
        JButton btnSalir = new JButton("Salir");
        btnSalir.setPreferredSize(new Dimension(100, 30));
        btnSalir.addActionListener(e -> System.exit(0));
        
        JPanel bottonButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottonButtonPanel.setBackground(new Color(240, 245, 250));
        bottonButtonPanel.add(btnSalir);
        
        footerPanel.add(footerText, BorderLayout.WEST);
        footerPanel.add(bottonButtonPanel, BorderLayout.EAST);
        
        mainPanel.add(footerPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
    }
    
    private JPanel crearBotonAccion(String titulo, String descripcion, Color color, java.awt.event.ActionListener listener) {
        JPanel panelBoton = new JPanel(new BorderLayout(12, 0));
        panelBoton.setBackground(Color.WHITE);
        panelBoton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 230), 1),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        panelBoton.setMaximumSize(new Dimension(Integer.MAX_VALUE, 90));
        panelBoton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Indicador de color
        JPanel colorPanel = new JPanel();
        colorPanel.setBackground(color);
        colorPanel.setPreferredSize(new Dimension(5, 0));
        panelBoton.add(colorPanel, BorderLayout.WEST);
        
        // Contenido
        JPanel contentPanel = new JPanel(new GridLayout(2, 1, 0, 5));
        contentPanel.setBackground(Color.WHITE);
        
        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 14));
        lblTitulo.setForeground(color);
        
        JLabel lblDescripcion = new JLabel(descripcion);
        lblDescripcion.setFont(new Font("Arial", Font.PLAIN, 11));
        lblDescripcion.setForeground(new Color(120, 120, 140));
        
        contentPanel.add(lblTitulo);
        contentPanel.add(lblDescripcion);
        panelBoton.add(contentPanel, BorderLayout.CENTER);
        
        // Efecto hover
        panelBoton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                panelBoton.setBackground(new Color(250, 250, 255));
                panelBoton.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(color, 2),
                        BorderFactory.createEmptyBorder(15, 15, 15, 15)
                ));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                panelBoton.setBackground(Color.WHITE);
                panelBoton.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(new Color(220, 220, 230), 1),
                        BorderFactory.createEmptyBorder(15, 15, 15, 15)
                ));
            }
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                listener.actionPerformed(null);
            }
        });
        
        return panelBoton;
    }
    
    private void abrirNuevaEncuesta() {
        new SurveyForm(dbManager).setVisible(true);
    }
    
    private void abrirDashboard() {
        new DashboardFrame(dbManager).setVisible(true);
    }
    
    private void cargarDatosDemo() {
        int cantidad = 10;
        RiskCalculator calculator = new RiskCalculator();
        
        for (int i = 0; i < cantidad; i++) {
            int[] respuestas = new int[49];
            for (int j = 0; j < 49; j++) {
                respuestas[j] = 1 + (int)(Math.random() * 5);
            }
            
            SurveyResponse response = new SurveyResponse(respuestas);
            calculator.calcularTodosPuntajes(response);
            dbManager.guardarRespuesta(response);
        }
        
        JOptionPane.showMessageDialog(this, 
                cantidad + " evaluaciones de prueba cargadas correctamente.\n" +
                "Ahora puedes ver el Dashboard para ver estadísticas.",
                "Éxito", 
                JOptionPane.INFORMATION_MESSAGE);
        SwingUtilities.invokeLater(() -> new MainMenu().setVisible(true));
    }
    
    private void exportarEvaluaciones() {
        JOptionPane.showMessageDialog(this,
                "Esta función permite exportar todas las evaluaciones realizadas.\n\n" +
                "Mejora futura: Exportar a PDF o Excel.\n\n" +
                "Por ahora, usa el Dashboard para ver las estadísticas.",
                "Exportar Evaluaciones",
                JOptionPane.INFORMATION_MESSAGE);
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainMenu().setVisible(true));
    }
}
