package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer;
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurerAdapter;
import org.springframework.web.servlet.handler.MappedInterceptor;

import hello.Box.Box;
import hello.BoxMoves.BoxMove;
import hello.Client.Client;
import hello.Department.Department;
import hello.Division.Division;
import hello.MasterData.Color;
import hello.MasterData.MasterData;
import hello.MasterData.Product;
import hello.Operation.Operation;
import hello.OutDoc.OutDoc;
import hello.OutDoorOrder.OutDoorOrder;
import hello.OutDoorOrderRow.OutDoorOrderRow;
import hello.PartBox.PartBox;
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
                config.exposeIdsFor(Client.class, Operation.class, Box.class, Department.class, 
                		MasterData.class, PartBox.class, BoxMove.class, OutDoc.class, Division.class,
                		User.class, OutDoorOrder.class, OutDoorOrderRow.class, Product.class, Color.class);
            }
        };

    }
    
}
