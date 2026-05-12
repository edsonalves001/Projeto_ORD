package br.com.ord.ORD.Controller;

import br.com.ord.ORD.model.Usuario;
import br.com.ord.ORD.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
public class CadastroController {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("/cadastro")
    public String OrdCadastro(){

        return "cadastro";
    }

    @PostMapping("/cadastro")
    public String cadastro(@RequestParam String nome,@RequestParam String email, @RequestParam String senha, @RequestParam String senha2,
                           RedirectAttributes redirectAttributes ){
        Usuario usu = new Usuario();
        boolean podeSalvar = false;

        if(!email.isEmpty()){
            usu.setEmail(email);
            podeSalvar = true;
        }else{
            podeSalvar = false;
            redirectAttributes.addFlashAttribute("erroEmail","Coloque um email valido");
        }

        if(!nome.isEmpty() || !nome.matches("\\d+")){
            usu.setNome(nome);
            podeSalvar = true;
        }else if(nome.equals("Ronaldo")){
            podeSalvar = false;
            redirectAttributes.addFlashAttribute("erroN","Coloque um nome valido");
        }


        if(senha.isEmpty()){
            podeSalvar = false;
            redirectAttributes.addFlashAttribute("erroVazio","A senha não pode estar vazia");
        }else if(senha.length()<8){
            podeSalvar = false;
            redirectAttributes.addFlashAttribute("erroTamanho","Essa senha é muito pequena");
        }else{
            boolean senhaLetra = senha.matches("^[a-zA-Z]+$");
            boolean senhaNumero = senha.matches("\\d+");
            if(senhaLetra || senhaNumero){
                podeSalvar = false;
                redirectAttributes.addFlashAttribute("erroFormato","A letra tem que conter letra,numero e caracteres especiais @ # ! .");
            }else if(!senha.equals(senha2)){
                podeSalvar = false;
                redirectAttributes.addFlashAttribute("erroSenhasDiferentes","As senhas não estão iguais");
            }else{
                usu.setSenha(senha);
                podeSalvar = true;
            }
        }
        if(podeSalvar){
            usu.setCaminhoIconPerfil("../assets/Perfil_basico.png");
            usuarioRepository.save(usu);
            redirectAttributes.addFlashAttribute("logadoEmail", email);
            redirectAttributes.addFlashAttribute("logadoSenha", senha);
            return "redirect:/login";
        }

        return "redirect:/cadastro";
    }


}
