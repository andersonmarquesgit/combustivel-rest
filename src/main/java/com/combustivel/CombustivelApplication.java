package com.combustivel;

import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.combustivel.api.entity.User;
import com.combustivel.api.enums.ProfileEnum;
import com.combustivel.api.repository.UserRepository;

@SpringBootApplication
public class CombustivelApplication {

	public static void main(String[] args) {
		SpringApplication.run(CombustivelApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	// Criando usuário ao iniciar a aplicação
	@Bean
	CommandLineRunner init(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		return args -> {
			initUsers(userRepository, passwordEncoder);
		};
	}

	private void initUsers(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		User admin = new User();
		admin.setEmail("admin@indra.com");
		admin.setPassword(passwordEncoder.encode("123456"));
		admin.setProfile(ProfileEnum.ROLE_ADMIN);
		
		User userScientist = new User();
		userScientist.setEmail("analyst@indra.com");
		userScientist.setPassword(passwordEncoder.encode("123456"));
		userScientist.setProfile(ProfileEnum.ROLE_ANALYST);
		

		User find = userRepository.findByEmail("admin@indra.com");
		User find1 = userRepository.findByEmail("analyst@indra.com");
		if (find == null && find1 == null) {
			userRepository.save(admin);
			userRepository.save(userScientist);
		}
	}
	
}
