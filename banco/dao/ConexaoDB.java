package banco.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConexaoDB {
    
    private static Properties carregarPropriedades() {
        Properties props = new Properties();

        try (InputStream is = ConexaoDB.class.getClassLoader().getResourceAsStream("db.properties")) {
            if (is == null) {
                System.err.println("Aviso: Arquivo 'db.properties' não encontrado nos recursos. Usando contingência.");
                return null;
            }
            props.load(is);
        } catch (IOException e) {
            System.err.println("Erro ao carregar o arquivo db.properties: " + e.getMessage());
        }
        return props;
    }

    public static Connection getConnection() throws SQLException {
        Properties props = carregarPropriedades();
        
        String url;
        String user;
        String password;

        if (props != null && !props.isEmpty()) {
            url = props.getProperty("db.url");
            user = props.getProperty("db.user");
            password = props.getProperty("db.password");
        } else {
            // Linha de contingência caso queira testar com valores padrão locais temporariamente
            url = "";
            user = "";
            password = "";
        }

        try {
            Class.forName("org.postgresql.Driver");
            return DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver do PostgreSQL não foi localizado no projeto: " + e.getMessage());
        }
    }
}