package util;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeoutException;

public class ConnectionUtil {
    public static Connection getConnection() throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        Properties properties = new Properties();
        properties.load(ClassLoader.getSystemResourceAsStream("info.properties"));
        connectionFactory.setHost(properties.getProperty("host"));
        connectionFactory.setUsername(properties.getProperty("username"));
        connectionFactory.setPassword(properties.getProperty("password"));
        return connectionFactory.newConnection();
    }
}
