package hello;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Component
public class LoggingInterceptor extends HandlerInterceptorAdapter {
	
	long start;
	
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
    		this.start = System.currentTimeMillis();
    		/*response.setHeader("Access-Control-Allow-Origin", "*");
    		response.setHeader("Access-Control-Allow-Methods", "*");
    		response.setHeader("Access-Control-Allow-Headers", "*");*/
		return true;
    }
    @Override
    public void afterCompletion(
			HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
    		System.out.println("at : " + new Date()+" was requested : " + request.getRequestURI() +" with responce: " + response.getStatus() + "; took: "+ (System.currentTimeMillis()-this.start));
    		if (ex!=null) System.out.println(ex.getMessage());
    }
}