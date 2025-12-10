package logic;

import model.SurveyResponse;
import java.util.ArrayList;
import java.util.List;

public class RecommendationEngine {
    
    public List<String> generarRecomendaciones(SurveyResponse r) {
        List<String> recomendaciones = new ArrayList<>();
        
        // Regla 1: Ansiedad alta
        if (r.getAnsiedad() > 3.5) {
            recomendaciones.add("🧘 ANSIEDAD: Practica técnicas de respiración profunda (4-7-8), " +
                    "realiza pausas activas cada 50 minutos y considera llevar un diario emocional.");
        }
        
        // Regla 2: Estrés académico alto
        if (r.getEstresAcademico() > 4.0) {
            recomendaciones.add("📚 ESTRÉS ACADÉMICO: Organiza tu tiempo con técnicas como Pomodoro, " +
                    "prioriza tareas urgentes vs importantes y evita la multitarea.");
        }
        
        // Regla 3: Sueño deficiente
        if (r.getSueno() < 3.0) {
            recomendaciones.add("😴 SUEÑO: Establece horarios fijos para dormir y despertar, " +
                    "evita pantallas 1 hora antes de acostarte y crea un ambiente oscuro y fresco.");
        }
        
        // Regla 4: Bajo apoyo emocional
        if (r.getApoyoEmocional() < 2.5) {
            recomendaciones.add("🤝 APOYO SOCIAL: Fortalece tus redes de apoyo conectando con amigos o familia. " +
                    "Considera unirte a grupos estudiantiles o contactar servicios de bienestar universitario.");
        }
        
        // Regla 5: Burnout/desmotivación
        if (r.getMotivacionBurnout() > 3.5) {
            recomendaciones.add("🔋 MOTIVACIÓN: Toma descansos activos regulares, dedica tiempo a actividades " +
                    "placenteras no académicas y reconoce tus logros pequeños.");
        }
        
        // Regla 6: Hábitos de estudio deficientes
        if (r.getHabitosEstudio() > 3.5) {
            recomendaciones.add("📝 HÁBITOS: Reduce la procrastinación dividiendo tareas grandes en pasos pequeños. " +
                    "Usa listas de tareas y celebra cuando las completes.");
        }
        
        // Regla 7: Necesidad de ayuda profesional (PRIORITARIA)
        if (r.getNecesidadAyuda() > 3.0) {
            recomendaciones.add(0, "⚠️ RECOMENDACIÓN PRIORITARIA: Considera buscar apoyo profesional. " +
                    "Tu universidad probablemente ofrece servicios de consejería psicológica gratuitos o de bajo costo.");
        }
        
        // Si no hay recomendaciones específicas
        if (recomendaciones.isEmpty()) {
            recomendaciones.add("✅ Tus indicadores son saludables. Continúa con tus buenos hábitos de autocuidado.");
        }
        
        return recomendaciones;
    }
}