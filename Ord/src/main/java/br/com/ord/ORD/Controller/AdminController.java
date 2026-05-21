package br.com.ord.ORD.Controller;

import br.com.ord.ORD.model.Alternativa;
import br.com.ord.ORD.model.Atividade;
import br.com.ord.ORD.model.Questao;
import br.com.ord.ORD.model.Usuario;
import br.com.ord.ORD.repository.AtividadeRepository;
import br.com.ord.ORD.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class AdminController {

    @GetMapping("/admin")
    public String adminLoginPage() {
        return "adminLogin";
    }

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private AtividadeRepository atividadeRepository;

    @PostMapping("/admin")
    public String adminLogin(@RequestParam String nome, @RequestParam String senha) {
        Usuario admin = usuarioRepository.findByNome(nome);

        if(admin == null) {
            return "redirect:/admin";
        }

        if(!admin.getSenha().equals(senha)){
            return "redirect:/admin";
        }

        return "redirect:/admin/dashboard";
    }

    @GetMapping("/admin/dashboard")
    public String adminDashboard(Model model) {
        model.addAttribute("atividades", atividadeRepository.findAll());

        return "admin";
    }

    @PostMapping("/admin/atividade")
    public String adminAtividade(@RequestParam String nome, @RequestParam String dificuldade, @RequestParam int recompensa) {
        Atividade atividade = new Atividade(nome, dificuldade, recompensa, false);
        atividadeRepository.save(atividade);

        return "redirect:/admin";
    }

    @PostMapping("/admin/questao")
    public String adminQuestao(@RequestParam String atv ,@RequestParam String enunciado, @RequestParam String tipo, @RequestParam List<String> alternativas, @RequestParam int alternativaCorreta) {

        List<Alternativa> alternativasList = new ArrayList<>();
        for (int i = 0; i < alternativas.size(); i++) {
            if (alternativaCorreta-1 == i) {
                alternativasList.add(new Alternativa(alternativas.get(i),true));
            } else {
                alternativasList.add(new Alternativa(alternativas.get(i),false));
            }
        }

        Questao questao = new Questao(enunciado,tipo,false,alternativasList);
        atividadeRepository.findAndPsuhQuestoesById(atv,questao);

        return "redirect:/admin/dashboard";
    }

}
