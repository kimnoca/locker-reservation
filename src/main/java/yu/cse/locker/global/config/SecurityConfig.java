package yu.cse.locker.global.config;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import yu.cse.locker.global.auth.JwtAccessDeniedHandler;
import yu.cse.locker.global.auth.JwtAuthenticationEntryPoint;
import yu.cse.locker.global.auth.JwtSecurityConfig;
import yu.cse.locker.global.auth.TokenProvider;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig { // TODO : spring security version 대응 필요함

    private final TokenProvider tokenProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)

                .and()
                .headers()
                .frameOptions()
                .sameOrigin()

                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()
                .authorizeHttpRequests()
                .requestMatchers("/api/user/signup").permitAll()
                .requestMatchers("/api/user/login").permitAll()
                .requestMatchers("/api/user/certification").permitAll()
                .requestMatchers("/api/user/certification-check").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/locker/**").permitAll()
                .anyRequest().authenticated()

                .and()
                .apply(new JwtSecurityConfig(tokenProvider));

        httpSecurity.cors();

        return httpSecurity.build();
    }

//    @Bean //  Deprecated 대처용
//    public SecurityFilterChain filterChainNew(HttpSecurity http) throws Exception {
//        http
//                .authorizeHttpRequests(authorize -> authorize
//                        .requestMatchers("/api/user/**").permitAll()
//                        .anyRequest().authenticated()
//                );
//
//        return http.build();
//    }

}
