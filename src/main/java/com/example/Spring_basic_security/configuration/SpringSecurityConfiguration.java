package com.example.Spring_basic_security.configuration;

import com.example.Spring_basic_security.filter.AuthFilter;
import com.example.Spring_basic_security.model.RequestEntity;
import com.example.Spring_basic_security.service.RequestEntityUserDetailsService;
import com.example.Spring_basic_security.transfer.AuthRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.nio.file.AccessDeniedException;

import static org.springframework.security.config.Customizer.withDefaults;

/*
the Lambda DSL (Domain-Specific Language) is introduced in Spring Security version 5.2 and is the preferred way to
configure Spring Security. It allows configuring HTTP security using lambdas, which provides a more concise and clear
syntax compared to the previous configuration style used by older Spring security versions.

the DSL allows you to configure security rules using a fluent and declarative style, making the code more readable and
maintainable. It aims to simplify the process of configuring security by providing specific methods and constructs that
align with common security scenarios and patterns.

The DSL in Spring Security typically consists of method chains and lambda expressions that allow you to express security
configurations in a structured and intuitive manner. For example, you can use method chains like authorizeHttpRequests()
requestMatchers().permitAll() to define authorization rules for specific URL patterns.

The DSL provides various configuration options, such as specifying access rules, defining authentication mechanisms,
enabling or disabling features like CSRF protection or form-based login, and more. It allows you to configure security
at different levels, such as global configuration, per-request configuration, or specific configuration for different
URL patterns.

By using the DSL, you can effectively express the desired security behavior of your application without getting into the
low-level details of security infrastructure. It promotes a more concise, readable, and maintainable approach to
configuring security rules.

It's important to consult the official Spring Security documentation for the version you are using to understand the
available DSL methods and constructs specific to that version. The DSL may evolve over different versions, so it's
crucial to refer to the appropriate documentation for accurate usage and configuration guidance.
 */


@Configuration
@EnableWebSecurity
//@EnableMethodSecurity
public class SpringSecurityConfiguration  {

    @Autowired
    private AuthFilter authFilter;

    @Bean
    public UserDetailsService userDetailsService(){
//        UserDetails admin = User.withUsername("rohi").password(passwordEncoder.encode("rohi")).roles("ADMIN").build();
//        UserDetails user = User.withUsername("John").password(passwordEncoder.encode("pwd")).roles("USER").build();
//        return new InMemoryUserDetailsManager(admin,user);
        return new RequestEntityUserDetailsService();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        SecurityFilterChain securityFilterChain=null;

        try{
        securityFilterChain= http.csrf((csrf) -> csrf.disable()).authorizeHttpRequests((authorizeHttpRequests) ->
                            authorizeHttpRequests.requestMatchers("/users/welcome", "/users/new", "/users/authenticate").permitAll().
                                    requestMatchers("/users/getAll").hasRole("ADMIN")
                                    .requestMatchers("/users/**").hasRole("USER")).sessionManagement(httpSecuritySessionManagementConfigurer -> httpSecuritySessionManagementConfigurer
                            .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                    .authenticationProvider(authenticationProvider()).addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)
                    .build();

        }
        catch(AccessDeniedException e){
            e.printStackTrace();
        }

     return securityFilterChain;

    }
    /*
    In the above code, the filter object is built by sequentially configuring various features of Spring Security.
    http.csrf((csrf) -> csrf.disable()): This line configures CSRF protection by invoking the disable() method on the
    csrf object obtained from the csrf() configuration. It disables CSRF protection.

    authorizeHttpRequests((authorizeHttpRequests) -> ... ): This method starts the configuration for authorizing HTTP
    requests using the Lambda DSL. It specifies the authorization rules for different URL patterns.

    authorizeHttpRequests.requestMatchers("/users/welcome","/users/getAll").permitAll(): This line specifies that
    requests matching the URLs "/users/welcome" and "/users/getAll" should be permitted for all users without requiring
    authentication.

    authorizeHttpRequests.requestMatchers("/users/**").authenticated(): This line specifies that requests matching any
    URL under "/users/" (e.g., "/users/1", "/users/2/details") should require authentication. Only authenticated users
    will be allowed access to these URLs.

    .formLogin(withDefaults()): This line configures form-based authentication. The withDefaults() method sets up the
    default behavior for form-based login, including the login page URL, form submission URL, and handling of successful
    and failed login attempts.

    .build(): This method builds the final configuration and returns a SecurityFilterChain object representing the
     configured security filters.

 The reason why formLogin() is placed outside the authorizeHttpRequests() configuration is that form-based authentication
 is a separate feature and does not fall under the scope of URL authorization. It handles the login functionality,
 such as rendering the login page, processing login form submissions, and handling authentication success or failure.

By placing formLogin() outside the authorizeHttpRequests() configuration, you ensure that the form-based authentication
feature is applied separately after the URL authorization rules have been defined.

Overall, the sequential configuration of different features allows you to customize and define the behavior of Spring
Security according to your application's requirements.

The formLogin() method is used to enable form-based authentication with default settings. However, it only applies to
 URLs that have NOT been explicitly configured with permitAll() access.
     */


    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }


}

