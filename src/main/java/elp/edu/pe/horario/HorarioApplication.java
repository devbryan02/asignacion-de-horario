package elp.edu.pe.horario;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication(
        excludeName = {
                "org.springframework.cloud.function.context.config.ContextFunctionCatalogAutoConfiguration"
        }
)
@EnableFeignClients
public class HorarioApplication {

    public static void main(String[] args) {
        SpringApplication.run(HorarioApplication.class, args);
    }

}
