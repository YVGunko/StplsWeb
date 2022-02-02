package hello.Web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller

public class IndexAth {
    @GetMapping("/login/indexAth")
    public String showIndex() {
        return "indexAth";
    }
}
