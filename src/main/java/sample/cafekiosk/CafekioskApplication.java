package sample.cafekiosk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(scanBasePackages = "sample.cafekiosk")
public class CafekioskApplication {

	public static void main(String[] args) {
		SpringApplication.run(CafekioskApplication.class, args);
	}

}
