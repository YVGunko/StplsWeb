package hello;

import java.util.Date;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration;
import org.springframework.boot.autoconfigure.websocket.servlet.WebSocketServletAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.filter.CommonsRequestLoggingFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication(exclude = {
        MultipartAutoConfiguration.class,
        WebSocketServletAutoConfiguration.class
})
@EnableTransactionManagement
@EnableAsync
@EnableWebMvc

public class Application implements CommandLineRunner{


    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
    
   @Override
    public void run(String... strings) throws Exception {
	   System.out.println("***** serverqr started at : " + new Date());
   	}
    
    
    @Bean
    public CommonsRequestLoggingFilter logFilter() {
        CommonsRequestLoggingFilter filter
          = new CommonsRequestLoggingFilter();
        filter.setIncludeQueryString(true);
        filter.setIncludePayload(true);
        filter.setMaxPayloadLength(10000);
        filter.setIncludeHeaders(false);
        filter.setAfterMessagePrefix("REQUEST DATA : ");
        return filter;
    }
    
    /*@Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
        
        mailSender.setUsername("zakazstilplast@gmail.com");
        mailSender.setPassword("atacxuxgdolpohun");
        
        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");
        
        return mailSender;
    }*/
	/*@Value("${http.port}")
	private int httpPort;



	private static final int HTTP_PORT = 4242;
	private static final int HTTPS_PORT = 4243;
	private static final String HTTP = "http";
	private static final String USER_CONSTRAINT = "CONFIDENTIAL";

	@Bean
	public ServletWebServerFactory servletContainer() {
	    TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory() {
	        @Override
	        protected void postProcessContext(Context context) {
	            SecurityConstraint securityConstraint = new SecurityConstraint();
	            securityConstraint.setUserConstraint(USER_CONSTRAINT);
	            SecurityCollection collection = new SecurityCollection();
	            collection.addPattern("/*");
	            securityConstraint.addCollection(collection);
	            context.addConstraint(securityConstraint);
	        }
	    };
	    tomcat.addAdditionalTomcatConnectors(redirectConnector());
	    return tomcat;
	}

	/*private Connector redirectConnector() {
	    Connector connector = new Connector(
	            TomcatServletWebServerFactory.DEFAULT_PROTOCOL);
	    connector.setScheme(HTTP);
	    connector.setPort(HTTP_PORT);
	    connector.setSecure(false);
	    connector.setRedirectPort(HTTPS_PORT);
	    return connector;
	}*/
}
