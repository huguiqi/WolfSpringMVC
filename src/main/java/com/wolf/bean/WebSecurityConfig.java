package com.wolf.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * Created by sam on 2017/7/15.
 */

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
//    private CsrfTokenRepository csrfTokenRepository() {
//        HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
//        repository.setSessionAttributeName("_csrf");
//        return repository;
//    }

//    @Bean
//    public UserDetailsService userDetailsService() {
//        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
//        manager.createUser(User.withUsername("user").password("123456").authorities("USER").roles("USER").build());
//        return manager;
//    }

    @Autowired
    public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication().withUser("bill").password("abc123").roles("USER");
//        auth.inMemoryAuthentication().withUser("admin").password("root123").roles("ADMIN");
//        auth.inMemoryAuthentication().withUser("dba").password("root123").roles("ADMIN",

        auth.inMemoryAuthentication().passwordEncoder(new WFMd5PasswordEncoder()).withUser("bill").password(new WFMd5PasswordEncoder().encode("abc123")).roles("USER");
        auth.inMemoryAuthentication().passwordEncoder(new WFMd5PasswordEncoder()).withUser("admin").password(new WFMd5PasswordEncoder().encode("root123")).roles("ADMIN");
        auth.inMemoryAuthentication().passwordEncoder(new WFMd5PasswordEncoder()).withUser("dba").password(new WFMd5PasswordEncoder().encode("root123")).roles("ADMIN","DBA");
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/","/auth/**").permitAll()
                .antMatchers("/main/common").access("hasRole('USER')")
                .antMatchers("/main/admin").access("hasRole('ADMIN')")
                .and().formLogin()
                .and().exceptionHandling().accessDeniedPage("/auth/denied");
//                .and().csrf().csrfTokenRepository(csrfTokenRepository())
//                .and().addFilterAfter(csrfHeaderFilter(), SessionManagementFilter.class);

    }

//    private Filter csrfHeaderFilter() {
//        return new OncePerRequestFilter() {
//
//            @Override
//            protected void doFilterInternal(HttpServletRequest request,
//                                            HttpServletResponse response,
//                                            FilterChain filterChain) throws ServletException, IOException {
//
//                CsrfToken csrf = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
//                if (csrf != null) {
//                    Cookie cookie = WebUtils.getCookie(request, "XSRF-TOKEN");
//                    String token = csrf.getToken();
//                    if (cookie == null || token != null
//                            && !token.equals(cookie.getValue())) {
//
//                        // Token is being added to the XSRF-TOKEN cookie.
//                        cookie = new Cookie("XSRF-TOKEN", token);
//                        cookie.setPath("/");
//                        response.addCookie(cookie);
//                    }
//                }
//                filterChain.doFilter(request, response);
//            }
//        };
//    }


}
