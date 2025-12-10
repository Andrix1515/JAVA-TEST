package db;

import model.SurveyResponse;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
    
    private static final String URL = "jdbc:mysql://localhost:3306/salud_mental_db";
    private static final String USER = "root";
    private static final String PASSWORD = "PON TU CONTRASEÑA"; // Cambiar según configuración
    
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
    
    public void crearTabla() {
        String sql = "CREATE TABLE IF NOT EXISTS estudiantes_respuestas (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "p1 INTEGER, p2 INTEGER, p3 INTEGER, p4 INTEGER, p5 INTEGER, p6 INTEGER, p7 INTEGER, p8 INTEGER, p9 INTEGER, p10 INTEGER, " +
                "p11 INTEGER, p12 INTEGER, p13 INTEGER, p14 INTEGER, p15 INTEGER, p16 INTEGER, p17 INTEGER, p18 INTEGER, p19 INTEGER, p20 INTEGER, " +
                "p21 INTEGER, p22 INTEGER, p23 INTEGER, p24 INTEGER, p25 INTEGER, p26 INTEGER, p27 INTEGER, p28 INTEGER, p29 INTEGER, p30 INTEGER, " +
                "p31 INTEGER, p32 INTEGER, p33 INTEGER, p34 INTEGER, p35 INTEGER, p36 INTEGER, p37 INTEGER, p38 INTEGER, p39 INTEGER, p40 INTEGER, " +
                "p41 INTEGER, p42 INTEGER, p43 INTEGER, p44 INTEGER, p45 INTEGER, p46 INTEGER, p47 INTEGER, p48 INTEGER, p49 INTEGER, " +
                "riesgo_global REAL, clasificacion TEXT)";
        
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Tabla creada exitosamente");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void guardarRespuesta(SurveyResponse response) {
        StringBuilder sql = new StringBuilder("INSERT INTO estudiantes_respuestas (");
        for (int i = 1; i <= 49; i++) {
            sql.append("p").append(i).append(", ");
        }
        sql.append("riesgo_global, clasificacion) VALUES (");
        for (int i = 0; i < 49; i++) {
            sql.append("?, ");
        }
        sql.append("?, ?)");
        
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {
            
            int[] resp = response.getRespuestas();
            for (int i = 0; i < 49; i++) {
                pstmt.setInt(i + 1, resp[i]);
            }
            pstmt.setDouble(50, response.getRiesgoGlobal());
            pstmt.setString(51, response.getClasificacion());
            
            pstmt.executeUpdate();
            System.out.println("Respuesta guardada exitosamente");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public List<SurveyResponse> obtenerTodasRespuestas() {
        List<SurveyResponse> lista = new ArrayList<>();
        String sql = "SELECT * FROM estudiantes_respuestas";
        
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                int[] respuestas = new int[49];
                for (int i = 0; i < 49; i++) {
                    respuestas[i] = rs.getInt("p" + (i + 1));
                }
                
                SurveyResponse response = new SurveyResponse(respuestas);
                response.setId(rs.getInt("id"));
                response.setRiesgoGlobal(rs.getDouble("riesgo_global"));
                response.setClasificacion(rs.getString("clasificacion"));
                
                lista.add(response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return lista;
    }
}