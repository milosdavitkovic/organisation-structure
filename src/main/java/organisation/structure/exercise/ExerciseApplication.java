package organisation.structure.exercise;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class ExerciseApplication {

    public static void main(String[] args) {
        log.info("[Organization Analyzes] Starting the Organization Analyzes Application. Happy using!");
        SpringApplication.run(ExerciseApplication.class, args);
    }

}
