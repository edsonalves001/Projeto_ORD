package br.com.ord.ORD.Controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class LandingPageController {
    @GetMapping("/")
    public String OrdLanding() {
        return "index";
    }
}
