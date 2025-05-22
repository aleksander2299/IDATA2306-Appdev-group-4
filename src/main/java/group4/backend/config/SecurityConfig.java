package group4.backend.config;


import group4.backend.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {



    private final UserDetailsService userDetailsService;


    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authenticationProvider;

    /**
     * This method will be called automatically by the framework to find the authentication to use.
     *
     * @param http HttpSecurity setting builder
     * @throws Exception When security configuration fails
     * NOTE: AI was used to help fix errors with the deleting methods
     */
    @Bean
    public SecurityFilterChain configureAuthorizationFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests((auth) -> auth
                        // Will change however need to test this first
                        .requestMatchers("/api/authenticate/**").permitAll()
                        .requestMatchers("/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**").permitAll()
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()

                        .requestMatchers(HttpMethod.GET,"/api/rooms/search").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/rooms").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/rooms/{id}/**").permitAll()

                        .requestMatchers(HttpMethod.GET,"/api/source_extra_features/**").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/source/**").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/extra_features/**").permitAll()
                        .requestMatchers(HttpMethod.GET,"/api/providers/**").permitAll()

                        .requestMatchers(HttpMethod.DELETE, "/api/roomProvider/unlink/**").hasAnyRole("ADMIN", "PROVIDER")
                        .requestMatchers(HttpMethod.DELETE, "/api/roomProvider/*").hasAnyRole("ADMIN", "PROVIDER")

                        .requestMatchers("/api/**").authenticated()
                        .anyRequest().authenticated()
                )
                .sessionManagement((session) ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}
