package br.com.ord.ORD.Controller;

import br.com.ord.ORD.model.Alternativa;
import br.com.ord.ORD.model.Atividade;
import br.com.ord.ORD.model.Questao;
import br.com.ord.ORD.model.Usuario;
import br.com.ord.ORD.repository.AtividadeRepository;
import br.com.ord.ORD.repository.UsuarioRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class AtividadeController {

    @Autowired
    AtividadeRepository atividadeRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @PostMapping("/atividade")
    public String atividade(@RequestParam String atividadeNome, @RequestParam(defaultValue="1") int questaoAtual,
                            @RequestParam(required = false) String acao, @RequestParam(required = false) String alternativaTexto, @RequestParam(defaultValue = "validar") String fase,
                            @ModelAttribute("usuarioLogado") Usuario usuarioLogado, Model model) {
        Atividade atividade = atividadeRepository.findByNome(atividadeNome);
        List<Questao> questoes = atividade.getQuestoes();
        Questao questaoAtualObj = questoes.get(questaoAtual-1);
        List<Alternativa> alternativas = questaoAtualObj.getAlternativas();
        int vidas = usuarioLogado.getVidas();

        String balaoMsg = questaoAtualObj.getEnsino();
        boolean feedback = false;
        boolean correto = false;

        if (vidas == 0) {
            return "redirect:/home?erro=sem-vidas";
        }

        if ("pular".equals(acao) || "avancar".equals(fase)) {
            questaoAtual++;
            if (questaoAtual > questoes.size()) {
                return "redirect:/home";
            }

            questaoAtualObj = questoes.get(questaoAtual - 1);
            balaoMsg = questaoAtualObj.getEnsino();
        } else if ("continuar".equals(acao) && "validar".equals(fase)) {
            feedback = true;

            for(Alternativa alternativa : alternativas) {
                if(alternativa.getTexto().equals(alternativaTexto) && alternativa.isCorreto()) {
                    correto = true;
                }
            }

            if (correto) {
                balaoMsg = "Isso mesmo! Continue assim!";
            } else {
                balaoMsg = "Ops, resposta errada. Não se preocupe você terá outra chance!";
                usuarioLogado.setVidas(vidas - 1);

                usuarioRepository.save(usuarioLogado);
            }
        }

        model.addAttribute("atividade", atividade);
        model.addAttribute("questoes", questoes);
        model.addAttribute("questaoAtual", questaoAtual);
        model.addAttribute("questoesTotal", questoes.size());
        model.addAttribute("balaoMsg", balaoMsg);
        model.addAttribute("feedback", feedback);
        model.addAttribute("vidas", vidas);

        return "atividade";
    }
}