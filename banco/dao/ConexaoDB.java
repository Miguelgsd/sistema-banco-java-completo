package banco.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoDB {

    private static final String URL = System.getProperty("DB_URL");
    private static final String USUARIO = System.getProperty("DB_USER");
    private static final String SENHA = System.getProperty("DB_PASSWORD");

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
            
            return DriverManager.getConnection(URL, USUARIO, SENHA);
            
        } catch (ClassNotFoundException e) {
            throw new SQLException("Erro: Driver JDBC do PostgreSQL não foi encontrado nas bibliotecas.", e);
        }
    }
}