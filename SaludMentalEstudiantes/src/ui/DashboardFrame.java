package ui;

import db.DatabaseManager;
import logic.RiskCalculator;
import model.SurveyResponse;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class DashboardFrame extends JFrame {
    
    private DatabaseManager dbManager;
    
    public DashboardFrame(DatabaseManager dbManager) {
        this.dbManager = dbManager;
        
        setTitle("Dashboard Poblacional - Estadísticas");
        setSize(900, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
        initComponents();
    }
    
    private void initComponents() {
        List<SurveyResponse> respuestas = dbManager.obtenerTodasRespuestas();

        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(new Color(245, 245, 250));

        if (respuestas.isEmpty()) {
            JLabel lblVacio = new JLabel("No hay datos disponibles. Realiza evaluaciones primero.",
                    SwingConstants.CENTER);
            lblVacio.setFont(new Font("Arial", Font.BOLD, 16));
            mainPanel.add(lblVacio, BorderLayout.CENTER);
        } else {
            // Recalcular puntajes si es necesario
            RiskCalculator calculator = new RiskCalculator();
            for (SurveyResponse r : respuestas) {
                calculator.calcularTodosPuntajes(r);
            }

            // Panel superior: Estadísticas generales
            JPanel statsPanel = crearPanelEstadisticas(respuestas);
            mainPanel.add(statsPanel, BorderLayout.NORTH);

            // Panel central: Dimensiones y distribución
            JPanel centerPanel = new JPanel(new GridLayout(1, 2, 15, 15));
            centerPanel.setBackground(new Color(245, 245, 250));

            // Panel 1: Distribución de riesgo
            centerPanel.add(crearPanelDistribucion(respuestas));

            // Panel 2: Promedios por dimensión
            centerPanel.add(crearPanelPromedios(respuestas));

            mainPanel.add(centerPanel, BorderLayout.CENTER);

            // Panel inferior: Recomendaciones
            JPanel recPanel = crearPanelRecomendaciones(respuestas);
            mainPanel.add(recPanel, BorderLayout.SOUTH);
        }

        // Panel de botones inferior
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        btnPanel.setBackground(new Color(245, 245, 250));
        JButton btnVolver = new JButton("Volver al Menú");
        btnVolver.setPreferredSize(new Dimension(120, 35));
        btnVolver.addActionListener(e -> dispose());
        btnPanel.add(btnVolver);
        mainPanel.add(btnPanel, BorderLayout.AFTER_LAST_LINE);

        add(mainPanel);
    }
    
    private JPanel crearPanelEstadisticas(List<SurveyResponse> respuestas) {
        JPanel panel = new JPanel(new GridLayout(5, 1));
        panel.setBorder(BorderFactory.createTitledBorder("Estadísticas Generales"));
        
        double promRiesgo = respuestas.stream()
                .mapToDouble(SurveyResponse::getRiesgoGlobal)
                .average().orElse(0);
        
        panel.add(new JLabel("Total de estudiantes: " + respuestas.size()));
        panel.add(new JLabel("Riesgo promedio: " + String.format("%.2f", promRiesgo)));
        
        long bajo = respuestas.stream().filter(r -> r.getClasificacion().contains("Bajo")).count();
        long medio = respuestas.stream().filter(r -> r.getClasificacion().contains("Medio")).count();
        long alto = respuestas.stream().filter(r -> r.getClasificacion().contains("Alto")).count();
        
        panel.add(new JLabel("Riesgo bajo: " + bajo + " (" + (bajo*100/respuestas.size()) + "%)"));
        panel.add(new JLabel("Riesgo medio: " + medio + " (" + (medio*100/respuestas.size()) + "%)"));
        panel.add(new JLabel("Riesgo alto: " + alto + " (" + (alto*100/respuestas.size()) + "%)"));
        
        return panel;
    }
    
    private JPanel crearPanelDistribucion(List<SurveyResponse> respuestas) {
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                
                long bajo = respuestas.stream().filter(r -> r.getClasificacion().contains("Bajo")).count();
                long medio = respuestas.stream().filter(r -> r.getClasificacion().contains("Medio")).count();
                long alto = respuestas.stream().filter(r -> r.getClasificacion().contains("Alto")).count();
                
                int total = respuestas.size();
                int width = getWidth() - 40;
                int height = getHeight() - 60;
                
                int bajoWidth = (int)((bajo * width) / total);
                int medioWidth = (int)((medio * width) / total);
                int altoWidth = (int)((alto * width) / total);
                
                g.setColor(new Color(0, 150, 0));
                g.fillRect(20, height/2, bajoWidth, 40);
                g.drawString("Bajo: " + bajo, 25, height/2 + 25);
                
                g.setColor(new Color(255, 140, 0));
                g.fillRect(20 + bajoWidth, height/2, medioWidth, 40);
                g.drawString("Medio: " + medio, 25 + bajoWidth, height/2 + 25);
                
                g.setColor(new Color(200, 0, 0));
                g.fillRect(20 + bajoWidth + medioWidth, height/2, altoWidth, 40);
                g.drawString("Alto: " + alto, 25 + bajoWidth + medioWidth, height/2 + 25);
            }
        };
        panel.setBorder(BorderFactory.createTitledBorder("Distribución de Riesgo"));
        return panel;
    }
    

    
    private JPanel crearPanelPromedios(List<SurveyResponse> respuestas) {
        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createTitledBorder("Promedios por Dimensión"));
        panel.setBackground(Color.WHITE);

        double promEstres = respuestas.stream().mapToDouble(SurveyResponse::getEstresAcademico).average().orElse(0);
        double promAnsiedad = respuestas.stream().mapToDouble(SurveyResponse::getAnsiedad).average().orElse(0);
        double promSueno = respuestas.stream().mapToDouble(SurveyResponse::getSueno).average().orElse(0);
        double promApoyo = respuestas.stream().mapToDouble(SurveyResponse::getApoyoEmocional).average().orElse(0);
        double promHabitos = respuestas.stream().mapToDouble(SurveyResponse::getHabitosEstudio).average().orElse(0);
        double promMotivacion = respuestas.stream().mapToDouble(SurveyResponse::getMotivacionBurnout).average().orElse(0);
        double promAyuda = respuestas.stream().mapToDouble(SurveyResponse::getNecesidadAyuda).average().orElse(0);

        // Fila 1
        panel.add(crearPanelDimension("Estrés Académico", promEstres, "Carga de cursos, procrastinación", 0xFF6B6B));
        panel.add(crearPanelDimension("Ansiedad", promAnsiedad, "Nerviosismo, inquietud", 0xFF8C42));

        // Fila 2
        panel.add(crearPanelDimension("Calidad del Sueño", promSueno, "Horas dormidas, continuidad", 0x4ECDC4));
        panel.add(crearPanelDimension("Apoyo Emocional", promApoyo, "Relaciones con familia", 0x45B7D1));

        // Fila 3
        panel.add(crearPanelDimension("Hábitos de Estudio", promHabitos, "Organización, procrastinación", 0x96CEB4));
        panel.add(crearPanelDimension("Motivación", promMotivacion, "Burnout, energía", 0xFFEAA7));

        // Fila 4
        panel.add(crearPanelDimension("Necesidad de Ayuda", promAyuda, "Búsqueda de apoyo profesional", 0xDDA0DD));

        return panel;
    }

    private JPanel crearPanelDimension(String titulo, double valor, String descripcion, int color) {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
                BorderFactory.createEmptyBorder(8, 8, 8, 8)
        ));

        // Encabezado con color
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(color));
        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 11));
        lblTitulo.setForeground(Color.WHITE);
        headerPanel.add(lblTitulo);
        panel.add(headerPanel, BorderLayout.NORTH);

        // Contenido
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(Color.WHITE);

        JLabel lblValor = new JLabel(String.format("%.2f", valor), SwingConstants.CENTER);
        lblValor.setFont(new Font("Arial", Font.BOLD, 20));
        lblValor.setForeground(new Color(color));

        // Barra de progreso
        JProgressBar barra = new JProgressBar(1, 5);
        barra.setValue((int) Math.round(valor));
        barra.setForeground(new Color(color));
        barra.setBackground(new Color(240, 240, 240));

        contentPanel.add(lblValor, BorderLayout.NORTH);
        contentPanel.add(barra, BorderLayout.CENTER);

        panel.add(contentPanel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel crearPanelRecomendaciones(List<SurveyResponse> respuestas) {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(100, 100, 200), 2),
                "RECOMENDACIONES GENERALES PARA LA POBLACIÓN",
                javax.swing.border.TitledBorder.LEFT,
                javax.swing.border.TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 13),
                new Color(100, 100, 200)
        ));
        panel.setBackground(Color.WHITE);
        panel.setPreferredSize(new Dimension(0, 200));

        // Calcular promedios para recomendaciones
        double promEstres = respuestas.stream().mapToDouble(SurveyResponse::getEstresAcademico).average().orElse(0);
        double promAnsiedad = respuestas.stream().mapToDouble(SurveyResponse::getAnsiedad).average().orElse(0);
        double promSueno = respuestas.stream().mapToDouble(SurveyResponse::getSueno).average().orElse(0);
        double promApoyo = respuestas.stream().mapToDouble(SurveyResponse::getApoyoEmocional).average().orElse(0);
        double promAyuda = respuestas.stream().mapToDouble(SurveyResponse::getNecesidadAyuda).average().orElse(0);

        // Panel scrolleable para recomendaciones
        JPanel recContentPanel = new JPanel();
        recContentPanel.setLayout(new BoxLayout(recContentPanel, BoxLayout.Y_AXIS));
        recContentPanel.setBackground(Color.WHITE);

        // Recomendaciones basadas en promedios poblacionales
        if (promEstres > 3.5) {
            recContentPanel.add(crearPanelRecomendacion("📚 ESTRÉS ACADÉMICO ELEVADO: Promover técnicas de gestión del tiempo y reducción de carga académica."));
        }
        if (promAnsiedad > 3.5) {
            recContentPanel.add(crearPanelRecomendacion("🧘 ANSIEDAD PROMEDIO ALTA: Implementar programas de mindfulness y apoyo psicológico grupal."));
        }
        if (promSueno < 3.0) {
            recContentPanel.add(crearPanelRecomendacion("😴 CALIDAD DEL SUEÑO DEFICIENTE: Educar sobre higiene del sueño y horarios regulares."));
        }
        if (promApoyo < 2.5) {
            recContentPanel.add(crearPanelRecomendacion("🤝 APOYO EMOCIONAL BAJO: Fortalecer redes de apoyo estudiantil y familiar."));
        }
        if (promAyuda > 3.0) {
            recContentPanel.add(crearPanelRecomendacion("⚠️ NECESIDAD DE AYUDA PROFESIONAL: Aumentar disponibilidad de servicios de consejería."));
        }

        // Si no hay recomendaciones específicas
        if (recContentPanel.getComponentCount() == 0) {
            recContentPanel.add(crearPanelRecomendacion("✅ La población muestra indicadores saludables. Continuar con programas preventivos."));
        }

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
                BorderFactory.createEmptyBorder(8, 8, 8, 8)
        ));
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));

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
}
