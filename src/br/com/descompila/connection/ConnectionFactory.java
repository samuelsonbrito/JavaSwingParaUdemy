/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.descompila.connection;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 *
 * @author samue
 */
public class ConnectionFactory {

    private static final String PROPERTIES_FILE = "/config/database.properties";
    private static String url;
    private static String usuario;
    private static String senha;
    
    static {
        try (InputStream arquivo = ConnectionFactory.class.getResourceAsStream(PROPERTIES_FILE)) {
            Properties properties = new Properties();
            if (arquivo == null) {
                throw new IOException("Arquivo de propriedades não encontrado: " + PROPERTIES_FILE);
            }
            properties.load(arquivo);

            url = properties.getProperty("db.url");
            usuario = properties.getProperty("db.user");
            senha = properties.getProperty("db.password");
        } catch (IOException e) {
            throw new ExceptionInInitializerError("Falha ao carregar configurações do banco de dados: " + e.getMessage());
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, usuario, senha);
    }
}
