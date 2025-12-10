package model;

public class SurveyResponse {
    private int id;
    private int[] respuestas; // 49 respuestas
    
    // Puntajes por categoría
    private double estresAcademico;
    private double ansiedad;
    private double sueno;
    private double habitosEstudio;
    private double motivacionBurnout;
    private double apoyoEmocional;
    private double necesidadAyuda;
    
    // Puntaje global y clasificación
    private double riesgoGlobal;
    private String clasificacion;
    
    public SurveyResponse(int[] respuestas) {
        this.respuestas = respuestas;
    }
    
    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public int[] getRespuestas() { return respuestas; }
    public void setRespuestas(int[] respuestas) { this.respuestas = respuestas; }
    
    public double getEstresAcademico() { return estresAcademico; }
    public void setEstresAcademico(double estresAcademico) { this.estresAcademico = estresAcademico; }
    
    public double getAnsiedad() { return ansiedad; }
    public void setAnsiedad(double ansiedad) { this.ansiedad = ansiedad; }
    
    public double getSueno() { return sueno; }
    public void setSueno(double sueno) { this.sueno = sueno; }
    
    public double getHabitosEstudio() { return habitosEstudio; }
    public void setHabitosEstudio(double habitosEstudio) { this.habitosEstudio = habitosEstudio; }
    
    public double getMotivacionBurnout() { return motivacionBurnout; }
    public void setMotivacionBurnout(double motivacionBurnout) { this.motivacionBurnout = motivacionBurnout; }
    
    public double getApoyoEmocional() { return apoyoEmocional; }
    public void setApoyoEmocional(double apoyoEmocional) { this.apoyoEmocional = apoyoEmocional; }
    
    public double getNecesidadAyuda() { return necesidadAyuda; }
    public void setNecesidadAyuda(double necesidadAyuda) { this.necesidadAyuda = necesidadAyuda; }
    
    public double getRiesgoGlobal() { return riesgoGlobal; }
    public void setRiesgoGlobal(double riesgoGlobal) { this.riesgoGlobal = riesgoGlobal; }
    
    public String getClasificacion() { return clasificacion; }
    public void setClasificacion(String clasificacion) { this.clasificacion = clasificacion; }
}