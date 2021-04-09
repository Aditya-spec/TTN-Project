package com.Bootcamp.Project.Application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;


@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@EnableAsync
public class Application {

    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(Application.class, args);

 /* public void add(String propertyName, String[] values)
     { JsonArray array = new JsonArray(); for(int i = 0; i < values.length; i++){ array.add(values[i]); }
         JsonObject json = new JsonObject();
         json.addProperty("propName", propertyName);
         json.add("value" ,array); }
 */
    }
}
