package ui;

import db.DatabaseManager;
import logic.RiskCalculator;
import model.SurveyResponse;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SurveyForm extends JFrame {
    
    private JSlider[] sliders;
    private JLabel[] labelValues;
    private List<String> preguntas;
    private DatabaseManager dbManager;
    private int currentQuestion = 0;
    private JLabel lblPregunta;
    private JPanel questionPanel;
    private JLabel lblProgress;
    
    public SurveyForm(DatabaseManager dbManager) {
        this.dbManager = dbManager;
        this.preguntas = cargarPreguntas();
        this.sliders = new JSlider[49];
        this.labelValues = new JLabel[49];
        
        setTitle("Evaluación de Salud Mental - Encuesta Interactiva");
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
        initComponents();
    }
    
    private List<String> cargarPreguntas() {
        List<String> pregs = new ArrayList<>();
        // Las preguntas extraídas del CSV
        pregs.add("¿Consideras que llevas muchos cursos este ciclo?");
        pregs.add("¿Dedicas muchas horas diarias a estudiar fuera de clases?");
        pregs.add("¿Sientes que la carga académica es abrumadora?");
        pregs.add("¿Tienes trabajos o proyectos acumulados sin terminar?");
        pregs.add("¿Postergas tus tareas académicas?");
        pregs.add("¿Te sientes presionado por los plazos de entrega?");
        pregs.add("¿Sientes que puedes cumplir con todas tus responsabilidades académicas?");
        pregs.add("¿Te cuesta organizarte con tus tareas y estudios?");
        pregs.add("¿Consideras que tus cursos son muy difíciles?");
        pregs.add("¿Faltas a clases por sentirte sobrepasado?");
        pregs.add("¿Te sientes nervioso o estresado?");
        pregs.add("¿Sientes que las cosas están fuera de tu control?");
        pregs.add("¿Te sientes agobiado por tus responsabilidades?");
        pregs.add("¿Experimentas dolores de cabeza relacionados con el estrés?");
        pregs.add("¿Sientes tensión muscular o dolor corporal?");
        pregs.add("¿Te irritas o enojas fácilmente?");
        pregs.add("¿El estrés académico afecta tu vida personal o familiar?");
        pregs.add("¿Tienes pensamientos recurrentes sobre tus preocupaciones académicas?");
        pregs.add("¿Recurres a comida, alcohol o sustancias para manejar el estrés?");
        pregs.add("¿Te sientes inquieto o con dificultad para relajarte?");
        pregs.add("¿Te preocupas excesivamente por diferentes cosas?");
        pregs.add("¿Has tenido ataques de pánico o ansiedad intensa?");
        pregs.add("¿Evitas situaciones académicas por miedo o ansiedad?");
        pregs.add("¿Sientes palpitaciones en situaciones académicas?");
        pregs.add("¿Sudas excesivamente en presentaciones o exámenes?");
        pregs.add("¿La ansiedad te impide concentrarte?");
        pregs.add("¿Te sientes constantemente en alerta?");
        pregs.add("¿Tienes pensamientos negativos sobre tu futuro académico?");
        pregs.add("¿Te cuesta tomar decisiones por miedo a equivocarte?");
        pregs.add("¿Duermes pocas horas por noche?");
        pregs.add("¿Tienes dificultad para conciliar el sueño?");
        pregs.add("¿Te despiertas frecuentemente durante la noche?");
        pregs.add("¿Te sientes cansado al despertar?");
        pregs.add("¿Usas dispositivos electrónicos antes de dormir?");
        pregs.add("¿Necesitas tomar siestas durante el día?");
        pregs.add("¿Tienes pesadillas sobre la universidad?");
        pregs.add("¿Necesitas cafeína para mantenerte despierto?");
        pregs.add("¿Tu horario de sueño es irregular?");
        pregs.add("¿Te quedas dormido en clases?");
        pregs.add("¿Te sientes triste o deprimido?");
        pregs.add("¿Has perdido interés en actividades que antes disfrutabas?");
        pregs.add("¿Te sientes solo o aislado?");
        pregs.add("¿Tienes dificultad para concentrarte en general?");
        pregs.add("¿Te sientes sin energía o fatigado?");
        pregs.add("¿Tienes pensamientos negativos sobre ti mismo?");
        pregs.add("¿Has perdido o ganado peso de manera significativa?");
        pregs.add("¿Te cuesta disfrutar momentos agradables?");
        pregs.add("¿Sientes que tienes apoyo emocional de familia o amigos?");
        pregs.add("¿Consideras necesario buscar ayuda psicológica?");
        return pregs;
    }
    
    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Panel superior con progreso
        JPanel topPanel = new JPanel(new BorderLayout());
        lblProgress = new JLabel("Pregunta 1 de 49");
        lblProgress.setFont(new Font("Arial", Font.BOLD, 12));
        topPanel.add(lblProgress, BorderLayout.WEST);
        
        JProgressBar progress = new JProgressBar(0, 49);
        progress.setValue(1);
        topPanel.add(progress, BorderLayout.CENTER);
        mainPanel.add(topPanel, BorderLayout.NORTH);
        
        // Panel de pregunta y respuesta
        questionPanel = new JPanel(new BorderLayout(10, 10));
        questionPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        lblPregunta = new JLabel();
        lblPregunta.setFont(new Font("Arial", Font.PLAIN, 14));
        lblPregunta.setVerticalAlignment(SwingConstants.TOP);
        lblPregunta.setPreferredSize(new Dimension(600, 80));
        questionPanel.add(lblPregunta, BorderLayout.NORTH);
        
        // Panel para slider y etiquetas
        JPanel sliderPanel = new JPanel();
        sliderPanel.setLayout(new BoxLayout(sliderPanel, BoxLayout.Y_AXIS));
        
        sliders[0] = new JSlider(1, 5, 1);
        sliders[0].setMajorTickSpacing(1);
        sliders[0].setPaintTicks(true);
        sliders[0].setPaintLabels(true);
        sliders[0].addChangeListener(e -> {
            labelValues[0].setText(getValueLabel(sliders[0].getValue()));
        });
        
        labelValues[0] = new JLabel(getValueLabel(1));
        labelValues[0].setFont(new Font("Arial", Font.BOLD, 14));
        labelValues[0].setForeground(new Color(0, 100, 200));
        
        sliderPanel.add(new JLabel("Nivel de acuerdo:"));
        sliderPanel.add(sliders[0]);
        sliderPanel.add(labelValues[0]);
        
        questionPanel.add(sliderPanel, BorderLayout.CENTER);
        mainPanel.add(questionPanel, BorderLayout.CENTER);
        
        // Panel de botones con tamaño prefijado
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        btnPanel.setBackground(new Color(240, 240, 250));
        btnPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(200, 200, 200)));
        btnPanel.setPreferredSize(new Dimension(0, 60));
        
        JButton btnAnterior = new JButton("← Anterior");
        btnAnterior.setPreferredSize(new Dimension(120, 35));
        btnAnterior.setFont(new Font("Arial", Font.BOLD, 12));
        btnAnterior.addActionListener(e -> preguntaAnterior());
        
        JButton btnSiguiente = new JButton("Siguiente →");
        btnSiguiente.setPreferredSize(new Dimension(120, 35));
        btnSiguiente.setFont(new Font("Arial", Font.BOLD, 12));
        btnSiguiente.addActionListener(e -> preguntaSiguiente());
        
        btnPanel.add(btnAnterior);
        btnPanel.add(btnSiguiente);
        
        // Panel inferior con botones y leyenda
        JPanel footerPanel = new JPanel(new BorderLayout(10, 10));
        footerPanel.setBackground(new Color(240, 240, 250));
        footerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        footerPanel.add(btnPanel, BorderLayout.NORTH);
        
        // Leyenda de respuestas
        JPanel legendPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        legendPanel.setBackground(new Color(245, 245, 250));
        legendPanel.setBorder(BorderFactory.createTitledBorder("Escala"));
        legendPanel.add(new JLabel("1: Nada/Nunca | 2: Poco | 3: Moderado | 4: Bastante | 5: Mucho"));
        footerPanel.add(legendPanel, BorderLayout.CENTER);
        
        mainPanel.add(footerPanel, BorderLayout.SOUTH);
        
        add(mainPanel);
        mostrarPregunta(0);
    }
    
    private void mostrarPregunta(int index) {
        currentQuestion = index;
        
        // Crear slider si no existe
        if (sliders[index] == null) {
            sliders[index] = new JSlider(1, 5, 1);
            sliders[index].setMajorTickSpacing(1);
            sliders[index].setPaintTicks(true);
            sliders[index].setPaintLabels(true);
            
            labelValues[index] = new JLabel(getValueLabel(1));
            labelValues[index].setFont(new Font("Arial", Font.BOLD, 14));
            labelValues[index].setForeground(new Color(0, 100, 200));
            
            int finalIndex = index;
            sliders[index].addChangeListener(e -> {
                labelValues[finalIndex].setText(getValueLabel(sliders[finalIndex].getValue()));
            });
        }
        
        // Actualizar pregunta
        lblPregunta.setText("<html><b>P" + (index + 1) + ":</b> " + preguntas.get(index) + "</html>");
        lblProgress.setText("Pregunta " + (index + 1) + " de 49");
        
        // Actualizar panel
        questionPanel.removeAll();
        questionPanel.add(lblPregunta, BorderLayout.NORTH);
        
        JPanel sliderPanel = new JPanel();
        sliderPanel.setLayout(new BoxLayout(sliderPanel, BoxLayout.Y_AXIS));
        sliderPanel.add(new JLabel("Nivel de acuerdo:"));
        sliderPanel.add(sliders[index]);
        sliderPanel.add(labelValues[index]);
        
        questionPanel.add(sliderPanel, BorderLayout.CENTER);
        questionPanel.revalidate();
        questionPanel.repaint();
    }
    
    private void preguntaAnterior() {
        if (currentQuestion > 0) {
            mostrarPregunta(currentQuestion - 1);
        } else {
            JOptionPane.showMessageDialog(this, "Esta es la primera pregunta", "Información", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void preguntaSiguiente() {
        if (currentQuestion < 48) {
            mostrarPregunta(currentQuestion + 1);
        } else {
            // Última pregunta - calcular
            int[] respuestas = new int[49];
            for (int i = 0; i < 49; i++) {
                respuestas[i] = sliders[i].getValue();
            }
            
            SurveyResponse response = new SurveyResponse(respuestas);
            RiskCalculator calculator = new RiskCalculator();
            calculator.calcularTodosPuntajes(response);
            
            dbManager.guardarRespuesta(response);
            
            new ResultadosFrame(response).setVisible(true);
            dispose();
        }
    }
    
    private String getValueLabel(int value) {
        return switch (value) {
            case 1 -> "Nada / Nunca";
            case 2 -> "Poco";
            case 3 -> "Moderado";
            case 4 -> "Bastante";
            case 5 -> "Mucho";
            default -> "";
        };
    }
}