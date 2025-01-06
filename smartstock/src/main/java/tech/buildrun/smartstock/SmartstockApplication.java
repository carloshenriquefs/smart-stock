package tech.buildrun.smartstock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import tech.buildrun.smartstock.config.AppConfig;

@SpringBootApplication
@EnableFeignClients
public class SmartstockApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(SmartstockApplication.class, args);
    }

	@Autowired
	private AppConfig appConfig;

	@Override
	public void run(String... args) throws Exception {
		appConfig.getClientId();
		appConfig.getClientSecret();

	}
}
