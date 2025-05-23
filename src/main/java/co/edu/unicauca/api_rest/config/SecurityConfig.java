package co.edu.unicauca.api_rest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // Deshabilita la protección CSRF para la consola H2 y tus APIs (solo para desarrollo)
            .csrf(csrf -> csrf
                .ignoringRequestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**"))
                .ignoringRequestMatchers(AntPathRequestMatcher.antMatcher("/api/**")) // <--- AÑADE ESTA LÍNEA
            )
            // Permite el contenido de frames (necesario para que la consola H2 se muestre)
            .headers(headers -> headers
                .frameOptions(frameOptions -> frameOptions.sameOrigin())
            )
            // Configura las reglas de autorización para las peticiones HTTP
            .authorizeHttpRequests(authorize -> authorize
                // Permite el acceso sin autenticación a la consola H2
                .requestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**")).permitAll()
                // Permite el acceso sin autenticación a todas tus rutas /api/** (solo para desarrollo)
                .requestMatchers(AntPathRequestMatcher.antMatcher("/api/**")).permitAll() // <--- AÑADE ESTA LÍNEA
                // Cualquier otra petición requiere autenticación (si la tienes activa más adelante)
                .anyRequest().authenticated()
            );
        return http.build();
    }
}