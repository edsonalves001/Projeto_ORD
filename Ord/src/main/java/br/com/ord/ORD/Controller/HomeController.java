package br.com.ord.ORD.Controller;

import br.com.ord.ORD.repository.TopicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    @Autowired
    TopicoRepository topicoRepository;

    @GetMapping("/home")
    public String OrdHome(Model model) {
        model.addAttribute("topicos", topicoRepository.findAll());

        return "home";
    }

    @PostMapping("/home/topico")
    public String OrdAtividades(@RequestParam String topico, Model model) {
        model.addAttribute("topico", topicoRepository.findById(topico));

        return "home";
    }
}
