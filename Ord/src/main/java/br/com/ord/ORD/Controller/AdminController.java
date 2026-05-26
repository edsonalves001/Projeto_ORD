package br.com.ord.ORD.Controller;

import br.com.ord.ORD.model.*;
import br.com.ord.ORD.repository.AtividadeRepository;
import br.com.ord.ORD.repository.TopicoRepository;
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
    @Autowired
    private TopicoRepository topicoRepository;

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
        List<Topico> topicos = topicoRepository.findAll();
        model.addAttribute("topicos", topicos);

        List<Nivel> niveis = new ArrayList<>();
        for (int i = 0; i < topicos.size(); i++) {
            niveis.addAll(topicos.get(i).getNiveis());
        }

        model.addAttribute("niveis",niveis);

        return "admin";
    }

    @PostMapping("/admin/topico")
    public String adminTopico(@RequestParam String nome, @RequestParam String icone, @RequestParam String descricao) {
        Topico topico = new Topico(nome, icone, descricao);
        topicoRepository.save(topico);

        return "redirect:/admin/dashboard";
    }

    @PostMapping("/admin/nivel")
    public String adminNivel(@RequestParam String topico, @RequestParam String nome, @RequestParam String descricao) {
        Nivel nivel = new Nivel(nome, descricao);
        topicoRepository.findAndPushNivelById(topico, nivel);

        return "redirect:/admin/dashboard";
    }

    @PostMapping("/admin/atividade")
    public String adminAtividade(@RequestParam String nvl, @RequestParam String nome, @RequestParam String dificuldade, @RequestParam int recompensa) {
        Atividade atividade = new Atividade(nome, dificuldade, recompensa, false, nvl);
        atividadeRepository.save(atividade);

        //AtividadesNivel atividadeId = new AtividadesNivel(atividade.getId());

        //topicoRepository.updateAllByNome(nvl, atividadeId.getIdAtividade());

        return "redirect:/admin/dashboard";
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

        Questao questao = new Questao("ensino",enunciado,tipo,false,alternativasList);
        atividadeRepository.findAndPushQuestoesById(atv,questao);

        return "redirect:/admin/dashboard";
    }

}
