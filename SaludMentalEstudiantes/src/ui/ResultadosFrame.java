package ui;

import logic.RecommendationEngine;
import model.SurveyResponse;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.util.List;

public class ResultadosFrame extends JFrame {
    
    public ResultadosFrame(SurveyResponse response) {
        setTitle("Resultados de tu Evaluación de Salud Mental");
        setSize(1000, 750);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
        initComponents(response);
    }
    
    private void initComponents(SurveyResponse response) {
        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(new Color(245, 245, 250));
        
        // ===== PANEL SUPERIOR: Evaluación Global =====
        JPanel riesgoPanel = crearPanelRiesgoGlobal(response);
        mainPanel.add(riesgoPanel, BorderLayout.NORTH);
        
        // ===== PANEL CENTRAL: Dimensiones (2 columnas) =====
        JPanel dimensionesPanel = crearPanelDimensiones(response);
        mainPanel.add(dimensionesPanel, BorderLayout.CENTER);
        
        // ===== PANEL INFERIOR: Recomendaciones =====
        JPanel recPanel = crearPanelRecomendaciones(response);
        mainPanel.add(recPanel, BorderLayout.SOUTH);
        
        // Botones de acción
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        btnPanel.setBackground(new Color(245, 245, 250));
        
        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.setPreferredSize(new Dimension(120, 35));
        btnCerrar.addActionListener(e -> dispose());
        
        btnPanel.add(btnCerrar);
        mainPanel.add(btnPanel, BorderLayout.AFTER_LAST_LINE);
        
        add(mainPanel);
    }
    
    private JPanel crearPanelRiesgoGlobal(SurveyResponse response) {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(obtenerColorRiesgo(response.getClasificacion()));
        panel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 2));
        panel.setPreferredSize(new Dimension(0, 120));
        
        // Riesgo global
        JLabel lblRiesgo = new JLabel("RIESGO GLOBAL: " + String.format("%.2f", response.getRiesgoGlobal()), 
                SwingConstants.CENTER);
        lblRiesgo.setFont(new Font("Arial", Font.BOLD, 28));
        lblRiesgo.setForeground(Color.WHITE);
        
        // Clasificación
        JLabel lblClasificacion = new JLabel(response.getClasificacion(), SwingConstants.CENTER);
        lblClasificacion.setFont(new Font("Arial", Font.BOLD, 22));
        lblClasificacion.setForeground(Color.WHITE);
        
        JPanel textPanel = new JPanel(new GridLayout(2, 1));
        textPanel.setBackground(obtenerColorRiesgo(response.getClasificacion()));
        textPanel.add(lblRiesgo);
        textPanel.add(lblClasificacion);
        
        panel.add(textPanel, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel crearPanelDimensiones(SurveyResponse response) {
        JPanel panel = new JPanel(new GridLayout(2, 2, 15, 15));
        panel.setBackground(new Color(245, 245, 250));
        
        // Fila 1
        panel.add(crearPanelDimension("Estrés Académico", response.getEstresAcademico(), 
                "Carga de cursos, procrastinación, presión", 0xFF6B6B));
        panel.add(crearPanelDimension("Ansiedad", response.getAnsiedad(), 
                "Nerviosismo, inquietud, preocupación", 0xFF8C42));
        
        // Fila 2
        panel.add(crearPanelDimension("Calidad del Sueño", response.getSueno(), 
                "Horas dormidas, continuidad del sueño", 0x4ECDC4));
        panel.add(crearPanelDimension("Apoyo Emocional", response.getApoyoEmocional(), 
                "Relaciones con familia y amigos", 0x45B7D1));
        
        return panel;
    }
    
    private JPanel crearPanelDimension(String titulo, double valor, String descripcion, int color) {
        JPanel panel = new JPanel(new BorderLayout(10, 8));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(12, 12, 12, 12)
        ));
        
        // Encabezado con color
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(color));
        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 13));
        lblTitulo.setForeground(Color.WHITE);
        headerPanel.add(lblTitulo);
        panel.add(headerPanel, BorderLayout.NORTH);
        
        // Contenido
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(Color.WHITE);
        
        JLabel lblValor = new JLabel(String.format("%.2f", valor), SwingConstants.CENTER);
        lblValor.setFont(new Font("Arial", Font.BOLD, 26));
        lblValor.setForeground(new Color(color));
        
        JLabel lblDescripcion = new JLabel(descripcion);
        lblDescripcion.setFont(new Font("Arial", Font.PLAIN, 10));
        lblDescripcion.setForeground(new Color(100, 100, 100));
        
        // Barra de progreso
        JProgressBar barra = new JProgressBar(1, 5);
        barra.setValue((int) valor);
        barra.setForeground(new Color(color));
        barra.setBackground(new Color(240, 240, 240));
        
        contentPanel.add(lblValor, BorderLayout.NORTH);
        contentPanel.add(barra, BorderLayout.CENTER);
        contentPanel.add(lblDescripcion, BorderLayout.SOUTH);
        
        panel.add(contentPanel, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel crearPanelRecomendaciones(SurveyResponse response) {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(100, 100, 200), 2),
                "RECOMENDACIONES PERSONALIZADAS",
                javax.swing.border.TitledBorder.LEFT,
                javax.swing.border.TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 13),
                new Color(100, 100, 200)
        ));
        panel.setBackground(Color.WHITE);
        panel.setPreferredSize(new Dimension(0, 280));
        
        RecommendationEngine engine = new RecommendationEngine();
        List<String> recomendaciones = engine.generarRecomendaciones(response);
        
        // Panel scrolleable para recomendaciones
        JPanel recContentPanel = new JPanel();
        recContentPanel.setLayout(new BoxLayout(recContentPanel, BoxLayout.Y_AXIS));
        recContentPanel.setBackground(Color.WHITE);
        
        for (String rec : recomendaciones) {
            JPanel recPanel = crearPanelRecomendacion(rec);
            recContentPanel.add(recPanel);
            recContentPanel.add(Box.createVerticalStrut(10));
        }
        
        // Agregar componente elástico al final
        recContentPanel.add(Box.createVerticalGlue());
        
        JScrollPane scroll = new JScrollPane(recContentPanel);
        scroll.setBorder(null);
        scroll.setBackground(Color.WHITE);
        scroll.getViewport().setBackground(Color.WHITE);
        
        panel.add(scroll, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel crearPanelRecomendacion(String recomendacion) {
        JPanel panel = new JPanel(new BorderLayout(12, 0));
        panel.setBackground(new Color(250, 250, 255));
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 240), 1),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 80));
        
        // Icono o indicador de prioridad
        JLabel lblIndicador = new JLabel();
        if (recomendacion.contains("PRIORITARIA")) {
            lblIndicador.setText("⚠️");
            lblIndicador.setFont(new Font("Arial", Font.PLAIN, 24));
        } else {
            lblIndicador.setText("✓");
            lblIndicador.setFont(new Font("Arial", Font.BOLD, 20));
            lblIndicador.setForeground(new Color(0, 150, 0));
        }
        lblIndicador.setPreferredSize(new Dimension(40, 0));
        lblIndicador.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(lblIndicador, BorderLayout.WEST);
        
        // Texto de recomendación
        JTextArea textArea = new JTextArea(recomendacion);
        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setFont(new Font("Arial", Font.PLAIN, 11));
        textArea.setBackground(new Color(250, 250, 255));
        textArea.setForeground(new Color(40, 40, 60));
        textArea.setOpaque(false);
        
        panel.add(textArea, BorderLayout.CENTER);
        
        return panel;
    }
    
    private Color obtenerColorRiesgo(String clasificacion) {
        return switch (clasificacion) {
            case "RIESGO BAJO" -> new Color(76, 175, 80);        // Verde
            case "RIESGO MEDIO" -> new Color(255, 152, 0);       // Naranja
            case "RIESGO ALTO" -> new Color(244, 67, 54);        // Rojo
            default -> new Color(158, 158, 158);                 // Gris
        };
    }
}