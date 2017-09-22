package hu.bme.fitnessapplication.server.configuration.security;

import hu.bme.fitnessapplication.server.user.service.FitnessUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
@Configuration
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    private AuthenticationEntryPoint authEntryPoint;

    @Autowired
    private FitnessUserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .exceptionHandling().and()
            .anonymous().and()
            .servletApi().and()
            .authorizeRequests()

            // Allow anonymous resource requests
            .antMatchers("/").permitAll()
            .antMatchers("/favicon.ico").permitAll()

            // Allow public apis
            .antMatchers("/public/**").permitAll()

            //Allow swagger and springfox
            .antMatchers("/swagger-resources/**", "/swagger-ui.html", "/webjars/springfox-swagger-ui/**", "/v2/api-docs**", "/swagger**").permitAll()

            //Allow pre-flight CORS options requests
            .antMatchers(HttpMethod.OPTIONS).permitAll()

            // All other request need to be authenticated using HTTP Basic authentication
            .anyRequest().authenticated().and()
            .httpBasic()
            .authenticationEntryPoint(authEntryPoint);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}