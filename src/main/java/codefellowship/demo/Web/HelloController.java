package codefellowship.demo.Web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {
    @GetMapping ("/")
    public String helloPage (){
        return "splash";
    }

@GetMapping("/access-denied")
    public String accessDenied(){
        return "err";
}


}
