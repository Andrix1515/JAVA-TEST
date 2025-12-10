package logic;

import model.SurveyResponse;

public class RiskCalculator {
    
    // Índices corregidos de preguntas por categoría (0-indexed)
    // Las preguntas 1-10 miden estrés académico
    private static final int[] ESTRES_INDICES = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
    
    // Las preguntas 11-20 miden ansiedad
    private static final int[] ANSIEDAD_INDICES = {10, 11, 12, 13, 14, 15, 16, 17, 18, 19};
    
    // Las preguntas 21-28 miden calidad del sueño
    private static final int[] SUENO_INDICES = {20, 21, 22, 23, 24, 25, 26, 27};
    
    // Las preguntas 29-39 miden depresión y bienestar
    private static final int[] DEPRESION_INDICES = {28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38};
    
    // Preguntas 40-41 miden apoyo emocional y necesidad de ayuda
    private static final int[] APOYO_INDICES = {39, 40, 41};
    
    // Pregunta 42 mide necesidad de ayuda profesional
    private static final int[] NECESIDAD_INDICES = {48};
    
    // Hábitos de estudio
    private static final int[] HABITOS_INDICES = {4, 5, 7, 18, 37};
    
    // Motivación y burnout
    private static final int[] MOTIVACION_INDICES = {28, 31, 32, 35, 36};
    
    public void calcularTodosPuntajes(SurveyResponse response) {
        int[] resp = response.getRespuestas();
        
        // Calcular promedios por categoría
        response.setEstresAcademico(calcularPromedio(resp, ESTRES_INDICES));
        response.setAnsiedad(calcularPromedio(resp, ANSIEDAD_INDICES));
        response.setSueno(calcularPromedio(resp, SUENO_INDICES));
        response.setHabitosEstudio(calcularPromedio(resp, HABITOS_INDICES));
        response.setMotivacionBurnout(calcularPromedio(resp, MOTIVACION_INDICES));
        response.setApoyoEmocional(calcularPromedio(resp, APOYO_INDICES));
        response.setNecesidadAyuda(calcularPromedio(resp, NECESIDAD_INDICES));
        
        // Calcular riesgo global
        double riesgo = calcularRiesgoGlobal(response);
        response.setRiesgoGlobal(riesgo);
        
        // Clasificar
        response.setClasificacion(clasificarRiesgo(riesgo));
    }
    
    private double calcularPromedio(int[] respuestas, int[] indices) {
        double suma = 0;
        for (int idx : indices) {
            if (idx < respuestas.length) {
                suma += respuestas[idx];
            }
        }
        return suma / indices.length;
    }
    
    private double calcularRiesgoGlobal(SurveyResponse r) {
        // Fórmula ponderada: Estrés y Ansiedad son más importantes (35% c/u)
        // Sueño: 15%, Depresión: 10%, Apoyo: 5%
        double riesgo = r.getEstresAcademico() * 0.35
                      + r.getAnsiedad() * 0.35
                      + (6 - r.getSueno()) * 0.15    // Invertido: menor sueño = mayor riesgo
                      + (6 - r.getApoyoEmocional()) * 0.10;  // Invertido: menos apoyo = mayor riesgo
        
        return Math.round(riesgo * 100.0) / 100.0; // Redondear a 2 decimales
    }
    
    private String clasificarRiesgo(double score) {
        if (score >= 1.0 && score <= 2.0) {
            return "RIESGO BAJO";
        } else if (score > 2.0 && score <= 3.4) {
            return "RIESGO MEDIO";
        } else {
            return "RIESGO ALTO";
        }
    }
}