package br.com.ord.ORD.Controller;

import br.com.ord.ORD.model.Atividade;
import br.com.ord.ORD.model.Topico;
import br.com.ord.ORD.repository.AtividadeRepository;
import br.com.ord.ORD.repository.TopicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Controller
public class HomeController {

    @Autowired
    TopicoRepository topicoRepository;

    @Autowired
    AtividadeRepository atividadeRepository;

    @GetMapping("/home")
    public String OrdHome(Model model) {
        model.addAttribute("topicos", topicoRepository.findAll());

        return "home";
    }

    @PostMapping("/home")
    public String OrdAtividades(@RequestParam String topico, Model model) {
        Optional<Topico> topicoAtual = topicoRepository.findById(topico);
        model.addAttribute("topicoNome", topicoAtual.map(Topico::getNome).orElse("nenhum"));
        model.addAttribute("niveis", topicoAtual.map(Topico::getNiveis).orElse(null));
        model.addAttribute("topicos", topicoRepository.findAll());
        List<Atividade> atividades = atividadeRepository.findAll();
        model.addAttribute("atividades",atividades);

        return "home";
    }
}
