package br.com.ord.ORD.Controller;
import br.com.ord.ORD.model.Usuario;
import br.com.ord.ORD.repository.UsuarioRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("/login")
    public String OrdLogin(Model model) {
         return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String email, @RequestParam String senha, HttpSession session, Model model) {

        //Caso tenha mais de um erro na hora de inserir os dados, ele junta tudo num arraylist
        List<String> erros = new ArrayList<>();

        //Caso o email esteja vazio ou inserido de forma invalida
        if(email == null || email.trim().isEmpty()){
            erros.add("O email não pode estar vazio");
        }else if(!email.contains("@")){
            erros.add("Email inválido");
        }

        //Caso a senha esteja nula ou vazia
        if(senha == null || senha.trim().isEmpty()){
            erros.add("A senha não pode estar vazia");
        }
        Usuario usuario = usuarioRepository.findByEmail(email);

        //Aqui ele procura no banco para ver se existe algum usuario com esse email,
        //se não tiver, ele envia a mensagem abaixo para o array de erros
        if(usuario == null){
            erros.add("Não existe conta com esse email");
        }

        if(!erros.isEmpty()){
            model.addAttribute("erros",erros);
            model.addAttribute("email",email);
            return "login";
        }

        //Validação da senha, caso não tenha, ele informa o erro e deixa o email
        //no input, sem apaga-lo completamente
        if(!usuario.getSenha().equals(senha)){
            erros.add("Senha incorreta");
            model.addAttribute("erros", erros);
            model.addAttribute("email", email);
            return "login";
        }

        session.setAttribute("usuarioId", usuario.getId());
        return "redirect:/home";
    }

/* Não está sendo usado no momento
@DeleteMapping("/delete")
    public String deletarUsuario(String usu_id){
        usuarioRepository.deleteById(usu_id);
        return "redirect:/login";
}*/
}
