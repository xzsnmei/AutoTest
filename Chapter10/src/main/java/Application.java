import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.course")
public class Application {
    public static void main(String[] args) {
        //固定写法
        SpringApplication.run(Application.class, args);
    }
}
