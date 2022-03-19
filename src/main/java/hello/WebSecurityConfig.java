package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Qualifier("userDetailsServiceImpl")
    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
				.antMatchers(HttpMethod.OPTIONS).permitAll()
				.antMatchers("/", "/favicon.ico").permitAll()
				//Доступ к регистрации только админ
				.antMatchers("/admin/**").hasRole("ADMIN")
				.antMatchers("/viewPrice").hasAnyRole("ADMIN","TOP","USER")
				.antMatchers("/viewPrice2").hasAnyRole("ADMIN","TOP")
				.antMatchers("/login/**").hasAnyRole("ADMIN", "TOP", "EXTERNAL")
				.and().exceptionHandling().accessDeniedPage("/403")
			.and()
				.formLogin()
				.failureHandler(authenticationFailureHandler())
			.and()
				.httpBasic()
			.and().logout().permitAll().and().csrf().disable()
			.logout()                                                                
            .logoutUrl("/logout")                                                 
            .logoutSuccessUrl("/index")                                                                       
            .invalidateHttpSession(true)                                                                                     
            .clearAuthentication(true).deleteCookies("SESSION");

	}
    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return new WebAuthenticationFailureHandler();
    }
    @Autowired
    protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }
}
