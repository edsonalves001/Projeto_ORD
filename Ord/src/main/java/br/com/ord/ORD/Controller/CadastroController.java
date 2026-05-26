package br.com.ord.ORD.Controller;

import br.com.ord.ORD.model.Usuario;
import br.com.ord.ORD.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
public class CadastroController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private JavaMailSender emailSender;

    @GetMapping("/cadastro")
    public String cadastroPage() {
        return "cadastro";
    }

    @PostMapping("/cadastro")
    public String cadastro(@RequestParam String nome, @RequestParam String email, @RequestParam String senha, @RequestParam String senha2, @RequestParam Integer idade, @RequestParam(required = false) String emailResponsavel, RedirectAttributes redirectAttributes, Model model) {
        //Mesma logica da lista de erro que está no login
        List<String> erros = new ArrayList<>();
        Usuario usu = new Usuario();

        if(nome == null || nome.trim().isEmpty()){
            erros.add("O nome não pode estar vazio");
        } else if(nome.matches("\\d+")){
            erros.add("O nome não pode conter apenas números");
        }


        if(email == null || email.trim().isEmpty()){
            erros.add("O email não pode estar vazio");
        }
        Usuario usuarioExistente = usuarioRepository.findByEmail(email);

        if(usuarioExistente != null){
            redirectAttributes.addFlashAttribute("emailJaExiste", true);
            redirectAttributes.addFlashAttribute("emailExistente", email);
            return "redirect:/login";
        }

        if(idade == null){
            erros.add("A idade é obrigatória");
        }

        if(senha == null || senha.isEmpty()){
            erros.add("A senha não pode estar vazia");
        }else {
            if(senha.length() < 8){
                erros.add("A senha deve ter pelo menos 8 caracteres");
            }
            boolean temMaiuscula = senha.matches(".*[A-Z].*");
            boolean temMinuscula = senha.matches(".*[a-z].*");
            boolean temNumero = senha.matches(".*\\d.*");
            boolean temEspecial = senha.matches(".*[@#$%^&+=!].*");
            if(!temMaiuscula){
                erros.add("A senha precisa ter letra maiúscula");
            }
            if(!temMinuscula){
                erros.add("A senha precisa ter letra minúscula");
            }

            if(!temNumero){
                erros.add("A senha precisa ter número");
            }

            if(!temEspecial){
                erros.add("A senha precisa ter caractere especial");
            }
        }
        if(!senha.equals(senha2)){
            erros.add("As senhas não coincidem");
        }
        boolean menorDeIdade = idade < 18;
        if(menorDeIdade){
            if(emailResponsavel == null || emailResponsavel.trim().isEmpty()){
                erros.add("Menores de idade precisam informar o email do responsável");
            }
        }
        //Mantem os dados no lugar
        if(!erros.isEmpty()){
            model.addAttribute("erros", erros);
            model.addAttribute("abrirModalResponsavel", menorDeIdade);
            model.addAttribute("nome", nome);
            model.addAttribute("idade", idade);
            model.addAttribute("email", email);
            model.addAttribute("senha", senha);
            model.addAttribute("senha2", senha2);
            model.addAttribute("emailResponsavel", emailResponsavel);

            return "cadastro";
        }
        usu.setNome(nome);
        usu.setEmail(email);
        usu.setSenha(senha);
        usu.setIdade(idade);
        usu.setCaminhoIconPerfil("../assets/PerfilMascotes/Foto_Inicial_Ordo.webp");
        String token = UUID.randomUUID().toString();
        usu.setTokenVerificacao(token);
        usu.setScore(0);
        usu.setEmailResponsavel(emailResponsavel);
        usu.setVerificado(false);
        List<String> lista = new ArrayList<>();
        usu.setAtividades(lista);
        usu.setCaminhoWallpaper("../assets/PerfilWallpapers/Wallpaper_Default_Ord.webp");
        usu.setVidas(3);
        List<String> amigos = new ArrayList<>();
        usu.setAmigosUsuario(amigos);
        usu.setStreak(0);
        usuarioRepository.save(usu);
        String destinoEmail = menorDeIdade ? emailResponsavel : email;
        String link = "http://localhost:8080/verificar?token=" + token;
        SimpleMailMessage mensagem = new SimpleMailMessage();
        mensagem.setTo(destinoEmail);
        mensagem.setSubject("Verificação de Cadastro");
        mensagem.setText("Olá " + nome + ",\n\n" + "Clique no link abaixo para verificar sua conta:\n\n" + link);
        emailSender.send(mensagem);
        redirectAttributes.addFlashAttribute("mensagem", "Foi enviado um email de verificação para " + destinoEmail);
        return "redirect:/cadastro";
    }

    @GetMapping("/verificar")
    public String verificarEmail(@RequestParam String token, RedirectAttributes redirectAttributes){
        Usuario usu = usuarioRepository.findByTokenVerificacao(token);
        if(usu == null){
            redirectAttributes.addFlashAttribute("erro", "Token inválido");
            return "redirect:/cadastro";
        }
        usu.setVerificado(true);
        usuarioRepository.save(usu);
        redirectAttributes.addFlashAttribute("logadoEmail", usu.getEmail());
        redirectAttributes.addFlashAttribute("logadoSenha", usu.getSenha());
        redirectAttributes.addFlashAttribute("sucesso", "Conta verificada com sucesso");
        return "redirect:/login";
    }
}