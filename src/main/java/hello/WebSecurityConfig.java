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
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

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
/*
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().passwordEncoder(passwordEncoder)
				.withUser("user").password(passwordEncoder.encode("serverqr")).roles("USER")
				.and()
				.withUser("extUser")	.password(passwordEncoder.encode("macRoExternaL")).roles("EXTERNAL")
				.and()
				.withUser("intUser")	.password(passwordEncoder.encode("stInternaL")).roles("INTERNAL")
				.and()
				.withUser("admin").password(passwordEncoder.encode("serverQrp")).roles("USER", "INTERNAL", "EXTERNAL", "ADMIN");
	}
*/
/*	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
*/

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
				.antMatchers(HttpMethod.OPTIONS).permitAll()
				.antMatchers("/", "/favicon.ico").permitAll()
				//Доступ к регистрации только админ
                //.antMatchers("/admin/registration").hasRole("ADMIN")
				.antMatchers("/admin/**").hasRole("ADMIN")
				.antMatchers("/viewPrice").hasAnyRole("ADMIN","TOP","USER")
				.antMatchers("/viewPrice2").hasAnyRole("ADMIN","TOP")
				.antMatchers("/login/**").hasAnyRole("ADMIN", "TOP", "EXTERNAL", "INTERNAL")
			.and()
				.formLogin()
			.and()
				.httpBasic()
			.and()
				.logout().logoutSuccessUrl("/index.html").permitAll().and().csrf()
			.disable();
	}
    @Autowired
    protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }
}
