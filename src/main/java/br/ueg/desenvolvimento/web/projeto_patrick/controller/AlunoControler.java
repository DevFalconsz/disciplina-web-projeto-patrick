package br.ueg.desenvolvimento.web.projeto_patrick.controller;
import java.util.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AlunoControler {
    static List alunos = new ArrayList<>();
    static {
        alunos.add(Map.of("nome", "João", "email", "joao@localhost"));
        alunos.add(Map.of("nome", "Maria", "email", "maria@localhost"));
        alunos.add(Map.of("nome", "José", "email", "jose@localhost"));
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/alunos")
    public String getHome(Model model) {
        model.addAttribute("alunos", alunos);
        return "alunos";
    }

    @GetMapping("/alunos/cadastrar")
    public String getForm(){
        return "aluno-create";
    }

    @PostMapping("/alunos/cadastrar")
    public String postForm( @RequestParam String nome, @RequestParam String email){
        System.out.println("Nome " + nome);
        System.out.println("Email: " + email);
        // Adiciona um novo aluno na lista
        alunos.add(Map.of("nome", nome, "email", email));
        return "redirect:/alunos";
    }
}