package com.hramyko.finalapp.web.config;
import com.hramyko.finalapp.persistence.entity.User;
import com.hramyko.finalapp.service.security.UserDetailsServiceImpl;
import com.hramyko.finalapp.service.security.jwt.JwtConfigurer;
import com.hramyko.finalapp.service.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.InMemoryOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@PropertySource("classpath:application.properties")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final JwtTokenProvider jwtTokenProvider;
    private static final List<String> clients = List.of("google");
    private final Environment env;
    private static String CLIENT_PROPERTY_KEY
            = "spring.security.oauth2.client.registration.";
    @Value("${security.oauth2.client.clientId}")
    private String clientId;
    @Value("${security.oauth2.client.clientSecret}")
    private String clientSecret;

    @Autowired
    public SecurityConfig(@Qualifier("userDetServImpl") UserDetailsService userDetailsService, JwtTokenProvider jwtTokenProvider,
                          Environment env) {
        this.userDetailsService = userDetailsService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.env = env;
    }

    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        ClientRegistration googleClient =
                CommonOAuth2Provider.GOOGLE.getBuilder("google")
                        .clientId("405181929366-bo1nfl31ivd25rhfohqlmckt1s0rgkij.apps.googleusercontent.com")
                        .clientSecret("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")
                        .build();

        return new InMemoryClientRegistrationRepository(googleClient);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        http
//                .httpBasic().disable()
//                .csrf().disable()
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                .authorizeRequests()
//                .antMatchers("api/registration/**").not().fullyAuthenticated()
//                .antMatchers("api/users/**", "api/games/**", "api/posts/**", "api/game_objects/**",
//                        "api/comments/**", "api/waiting_list/**", "api/my/logout").permitAll()
//                .and()
//                .apply(new JwtConfigurer(jwtTokenProvider));
//        http
//                .csrf().disable()
//                .authorizeRequests()
//                a.antMatchers("api/registration/**").not().fullyAuthenticated()
////                .antMatchers("api/users/**", "api/gmes/**", "api/posts/**", "api/game_objects/**",
//                        "api/comments/**", "api/waiting_list/**", "api/my/logout").authenticated()
//                .and().httpBasic()
//                .and().logout().logoutUrl("api/my/logout").invalidateHttpSession(true).deleteCookies("JSESSIONID");

        http.authorizeRequests()
                .antMatchers("api/registration/**").not().fullyAuthenticated()
                .antMatchers("api/users/**", "api/games/**", "api/posts/**", "api/game_objects/**",
                        "api/comments/**", "api/waiting_list/**", "api/my/logout").authenticated()
                .and()
                .oauth2Login()
                .clientRegistrationRepository(clientRegistrationRepository())
                .authorizedClientService(authorizedClientService())
                .and()
                .csrf().disable();
    }

    @Bean
    public OAuth2AuthorizedClientService authorizedClientService() {
        return new InMemoryOAuth2AuthorizedClientService(clientRegistrationRepository());
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    protected DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        return daoAuthenticationProvider;
    }

    @Bean
    protected PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }
}