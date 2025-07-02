package elp.edu.pe.horario.shared.config;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import javax.sql.DataSource;
import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DBConnectionConfigTest {

    @Autowired
    private DataSource dataSource;

    @Test
    public void probarConexion() throws Exception {
        try (Connection conexion = dataSource.getConnection()) {
            assertNotNull(conexion);
            System.out.println("Conexión exitosa a la base de datos.");
        }
    }
}


