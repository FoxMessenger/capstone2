package com.trilogyed.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    DataSource dataSource;

    @Autowired
    public void configurationAuthentication(AuthenticationManagerBuilder authBuilder) throws Exception {
        PasswordEncoder encoder = new BCryptPasswordEncoder();

        authBuilder.jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery(
                        "select username, password, enabled from users where username = ?")
                .authoritiesByUsernameQuery(
                        "select username, authority from authorities where username = ?")
                .passwordEncoder(encoder);
    }

    public void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.httpBasic();

        httpSecurity.authorizeRequests()
                // CREATE NEW
                .mvcMatchers(HttpMethod.POST, "/admin/product", "/admin/invoice", "/admin/levelup", "/admin/customer").hasAuthority("ADMIN")

                // UPDATES
                .mvcMatchers(HttpMethod.PUT, "/admin/product/*", "/admin/invoice/*", "/admin/levelup/*", "/admin/customer/*").hasAuthority("ADMIN")

                // REMOVE
                .mvcMatchers(HttpMethod.DELETE, "/admin/product/*", "/admin/invoice/*", "/admin/levelup/*", "/admin/customer/*").hasAuthority("ADMIN")

                // GET
                .mvcMatchers(HttpMethod.GET, "/admin/product/*", "/admin/invoice/*", "/admin/levelup/*", "/admin/customer/*", "/admin/products", "/admin/customers", "/admin/invoices").hasAuthority("ADMIN");

        httpSecurity
                .logout()
                .clearAuthentication(true)
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/products") // note we go back to products after we logout
                .deleteCookies("JSESSIONID")
                .deleteCookies("XSRF-TOKEN")
                .invalidateHttpSession(true);

        httpSecurity
                .csrf()
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
    }
}
