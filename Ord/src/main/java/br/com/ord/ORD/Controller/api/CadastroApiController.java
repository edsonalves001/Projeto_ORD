package br.com.ord.ORD.Controller.api;
import br.com.ord.ORD.model.Usuario;
import br.com.ord.ORD.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/cadastro")
public class CadastroApiController {
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private JavaMailSender emailSender;
    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody Map<String,Object> dados){
        List<String> erros = new ArrayList<>();
        String nome = (String) dados.get("nome");
        String email = (String) dados.get("email");
        String senha = (String) dados.get("senha");
        String senha2 = (String) dados.get("senha2");
        String emailResponsavel = (String) dados.get("emailResponsavel");
        Integer idade = null;

        try {
            if(dados.get("idade") != null && !dados.get("idade").toString().isBlank()) {
                idade = Integer.valueOf(dados.get("idade").toString());
            }
        } catch (Exception e) {
            erros.add("Idade inválida");
        }

        if(nome == null || nome.trim().isEmpty()){
            erros.add("O nome não pode estar vazio");
        }

        if(nome != null && nome.matches("\\d+")){
            erros.add("O nome não pode conter apenas números");
        }

        if(email == null || email.trim().isEmpty()){
            erros.add("O email não pode estar vazio");
        }

        Usuario usuarioExistente = usuarioRepository.findByEmail(email);

        if(usuarioExistente != null){
            erros.add("Este email já está cadastrado");
        }

        if(idade == null){
            erros.add("A idade é obrigatória");
        }

        if(senha == null || senha.isEmpty()){
            erros.add("A senha não pode estar vazia");
        } else {
            if(senha.length() < 8){
                erros.add("A senha deve ter pelo menos 8 caracteres");
            }
            if(!senha.matches(".*[A-Z].*")){
                erros.add("A senha precisa ter letra maiúscula");
            }
            if(!senha.matches(".*[a-z].*")){
                erros.add("A senha precisa ter letra minúscula");
            }
            if(!senha.matches(".*\\d.*")){
                erros.add("A senha precisa ter número");
            }
            if(!senha.matches(".*[@#$%^&+=!].*")){
                erros.add("A senha precisa ter caractere especial");
            }
        }
        if(!senha.equals(senha2)){
            erros.add("As senhas não coincidem");
        }
        boolean menorDeIdade = false;
        if(idade < 18){
            menorDeIdade = true;
        }

        if(menorDeIdade && (emailResponsavel == null || emailResponsavel.isBlank())){
            erros.add("Menores de idade precisam informar o email do responsável");
        }

        if(!erros.isEmpty()){
            return ResponseEntity.badRequest().body(
                    Map.of("sucesso", false, "erros", erros));
        }

        Usuario usu = new Usuario();

        usu.setNome(nome);
        usu.setEmail(email);
        //Meio tarde, mas agora a senha está criptografada
        usu.setSenha(encoder.encode(senha));
        usu.setIdade(idade);
        usu.setCaminhoIconPerfil("../assets/PerfilMascotes/Foto_Inicial_Ordo.webp");
        usu.setScore(0);
        usu.setEmailResponsavel(emailResponsavel);
        usu.setVerificado(false);
        usu.setAtividades(new ArrayList<>());
        usu.setCaminhoWallpaper("../assets/PerfilWallpapers/Wallpaper_Default_Ord.webp");
        usu.setVidas(3);
        usu.setAmigosUsuario(new ArrayList<>());
        usu.setStreak(0);
        String token = UUID.randomUUID().toString();
        System.out.println("SALVANDO USUARIO");
        usu.setTokenVerificacao(token);
        usuarioRepository.save(usu);
        String destinoEmail = menorDeIdade ? emailResponsavel : email;
        String link = "http://localhost:8080/api/verificacao/verificar?token=" + token;
        SimpleMailMessage mensagem = new SimpleMailMessage();
        mensagem.setTo(destinoEmail);
        mensagem.setSubject("Verificação de Cadastro");
        mensagem.setText("Olá " + nome + "\n\nClique no link abaixo:\n\n" + link);
        emailSender.send(mensagem);
        System.out.println("RETORNANDO JSON");
        return ResponseEntity.ok(Map.of("sucesso", true, "mensagem", "Foi enviado um email de verificação para " + destinoEmail));
    }
}