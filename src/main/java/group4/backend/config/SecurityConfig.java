package group4.backend.config;


import group4.backend.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
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
public class SecurityConfig {



    private final UserDetailsService userDetailsService;


    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authenticationProvider;

    /**
     * This method will be called automatically by the framework to find the authentication to use.
     *
     * @param http HttpSecurity setting builder
     * @throws Exception When security configuration fails
     */
    @Bean
    public SecurityFilterChain configureAuthorizationFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests((auth) -> auth.requestMatchers("/api/authenticate/**").permitAll())
                //.authorizeHttpRequests((auth) -> auth.requestMatchers("/api/booking/**").permitAll())
                .authorizeHttpRequests((auth) -> auth.requestMatchers("/api/authenticate/login").permitAll())
                .authorizeHttpRequests((auth) -> auth.requestMatchers("/api/authenticate/register").permitAll())
                .authorizeHttpRequests((auth) -> auth.requestMatchers(HttpMethod.GET, "/api/source").permitAll())
                .authorizeHttpRequests((auth) -> auth.requestMatchers(HttpMethod.GET, "/api/source_extra_features/**").permitAll())
                .authorizeHttpRequests((auth) -> auth.requestMatchers("/api/rooms").permitAll())
                .authorizeHttpRequests((auth) -> auth.requestMatchers("/api/rooms/**").permitAll())
                .authorizeHttpRequests((auth) -> auth.requestMatchers("/api/booking/**").permitAll())
                .authorizeHttpRequests((auth) -> auth.requestMatchers(HttpMethod.OPTIONS).permitAll())
                .authorizeHttpRequests((auth) -> auth.anyRequest().authenticated())
                .sessionManagement((session) ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)).
                authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

}
