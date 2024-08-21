package org.security.practic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PracticSecurityApplication {

	public static void main(String[] args) {
		SpringApplication.run(PracticSecurityApplication.class, args);
	}


//	@Bean
//	public DataSource dataSource() {
//		DriverManagerDataSource dataSource = new DriverManagerDataSource();
//		dataSource.setDriverClassName("org.postgresql.Driver");
//		dataSource.setUrl("jdbc:postgresql://localhost:5432/security_practice");
//		dataSource.setUsername("postgres");
//		dataSource.setPassword("PosTer231");
//		return dataSource;
//	}

}
