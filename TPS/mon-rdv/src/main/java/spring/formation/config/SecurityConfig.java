package spring.formation.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import spring.formation.config.jwt.JwtHeaderAuthorizationFilter;

@Configuration
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
	
	@Value("${monCross.origin}")
	private String monCrossOrigin;
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http, JwtHeaderAuthorizationFilter jwtFilter) throws Exception {
		// Méthode d'authentification par HTTP Basic
		http.httpBasic(Customizer.withDefaults());

		http.authorizeHttpRequests(auth -> {
			auth.requestMatchers("/api/patient/inscription").permitAll();
			auth.requestMatchers("/api/utilisateur/connexion").permitAll();
			auth.requestMatchers("/api/utilisateur/**").hasRole("ADMIN");
			auth.requestMatchers("/api/consultation/**").hasAnyRole("ADMIN", "SECRETAIRE");
			auth.requestMatchers("/api/**").authenticated();
			auth.requestMatchers("/**").permitAll();
		});

		// Désactiver la protection CSRF
		http.csrf(c -> c.ignoringRequestMatchers("/api/**"));

		// Configurer les CORS (Cross-Origine Resources Sharing)
		http.cors(c -> {
			CorsConfigurationSource source = request -> {
				CorsConfiguration config = new CorsConfiguration();

				// On autorise tout le monde
				config.setAllowedOrigins(List.of("*"));

				// On autorise toutes les commandes HTTP (GET, POST, PUT, ...)
				config.setAllowedMethods(List.of("*"));

				// On autorise toutes les en-têtes HTTP
				config.setAllowedHeaders(List.of("*"));

				return config;
			};

			c.configurationSource(source);
		});
		
		// Positionner le filtre JWT AVANT le filter
		// UsernamePasswordAuthenticationFilter
		http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

		// Désactiver la session utilisateur par cookie, puisque c'est plus utilisé avec
		// JWT
		http.sessionManagement(manager -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
				

		return http.build();
	}
	
	// Grace à ce Bean, on pourra injecter un AuthenticationManager directement
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		// Pas d'encadage sur les mots de passe - PAS BIEN
//		return NoOpPasswordEncoder.getInstance();

		// Encodage Blowfish
		return new BCryptPasswordEncoder();
	}

//	@Bean
//	public UserDetailsService inMemory(PasswordEncoder passwordEncoder) {
//		InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
//		manager.createUser(
//				User.withUsername("patient01").password(passwordEncoder.encode("123456")).roles("PATIENT").build());
//		manager.createUser(
//				User.withUsername("praticien01").password(passwordEncoder.encode("123456")).roles("PRATICIEN").build());
//		manager.createUser(
//				User.withUsername("admin").password(passwordEncoder.encode("123456")).roles("ADMIN").build());
//		manager.createUser(User.withUsername("secretaire01").password(passwordEncoder.encode("123456"))
//				.roles("SECRETAIRE").build());
//		return manager;
//	}
}
