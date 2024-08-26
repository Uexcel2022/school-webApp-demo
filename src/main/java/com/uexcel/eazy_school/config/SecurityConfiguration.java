package com.uexcel.eazy_school.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;


import java.util.function.Function;

@Configuration
public class SecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req->req
                        .requestMatchers("**:8080","/","/home").permitAll()
                        .requestMatchers("/dashboard").authenticated()
                        .requestMatchers("/about").permitAll()
                        .requestMatchers("/contact").permitAll()
                        .requestMatchers("/courses").permitAll()
                        .requestMatchers("/saveMsg").permitAll()
                        .requestMatchers("/holidays").permitAll()
                        .requestMatchers("/assets/**").permitAll()
                        .requestMatchers("/login").permitAll()

                        .anyRequest().authenticated())

                .formLogin(fl->fl.loginPage("/login")
                        .defaultSuccessUrl("/dashboard",true)
                        .failureUrl("/login?error=true").permitAll()
                )
                .logout(lo->lo.logoutSuccessUrl("/login?logout=true").invalidateHttpSession(true).permitAll())
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
        return new InMemoryUserDetailsManager(
                buildUser("admin","1234",new String[]{"USER","ADMIN"} ),
                buildUser("excel","12345",new String[]{"USER"} )
        );
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private UserDetails buildUser(String username, String password, String[] authorities) {
        return User.builder().passwordEncoder(passwordEncoder)
                        .username(username)
                        .password(password)
                        .authorities(authorities)
                        .build();
    }

    Function<String, String> passwordEncoder = password -> bCryptPasswordEncoder().encode(password);
}
