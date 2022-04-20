package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;
import org.springframework.web.servlet.handler.MappedInterceptor;

import hello.User.User;

@Configuration
public class AppConfig {
	
	@Autowired
	LoggingInterceptor loggingInterceptor;

	@Bean
	public MappedInterceptor dbEditor() {
		return new MappedInterceptor(new String[]{"/**"}, loggingInterceptor);
	}
	
    @Bean
    public RepositoryRestConfigurer repositoryRestConfigurer() {

        return new RepositoryRestConfigurerAdapter() {
            @Override
            public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
                config.exposeIdsFor(
                		User.class);
            }
        };

    }
    
}
