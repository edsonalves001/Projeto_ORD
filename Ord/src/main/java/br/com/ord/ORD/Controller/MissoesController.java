package br.com.ord.ORD.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MissoesController {
    @GetMapping("/missoes")
    public String missoes(){
        return "missoes";
    }
}
