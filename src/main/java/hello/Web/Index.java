package hello.Web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller

public class Index {
    @GetMapping("index")
    public String showIndex() {
        return "index";
    }
    
    @GetMapping("signin")
    public String viewLoginPage() {
        // custom logic before showing login page...
         
        return "signin";
    }
    
    @GetMapping("403")
    public String page403() {
        // custom logic before showing login page...
         
        return "403";
    }
}
