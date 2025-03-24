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
        model.addAttribute("mensagem", "Todos os alunos cadastrados");
        return "alunos";
    }

    @GetMapping("/alunos/cadastrar")
    public String getForm() {
        return "aluno-create";
    }

    @PostMapping("/alunos/cadastrar")
    public String postForm(@RequestParam String nome, @RequestParam String email) {
        System.out.println("Nome " + nome);
        System.out.println("Email: " + email);
        // Adiciona um novo aluno na lista
        alunos.add(Map.of("nome", nome, "email", email));
        return "redirect:/alunos";
    }

    @GetMapping("/alunos/update/{id}")
    public String getUpdate(@PathVariable int id, Model model) {
        model.addAttribute("aluno", alunos.get(id));
        model.addAttribute("id", id);
        return "aluno-update";
    }

    @PostMapping("/alunos/update")
    public String postUpdate(@RequestParam int id, @RequestParam String nome, @RequestParam String email) {
        alunos.set(id, Map.of("nome", nome, "email", email));
        return "redirect:/alunos";
    }

    @GetMapping("/alunos/delete/{id}")
    public String getDelete(@PathVariable int id, Model model) {
        model.addAttribute("aluno", alunos.get(id));
        model.addAttribute("id", id);
        return "aluno-delete";
    }

    @PostMapping("/alunos/delete")
    public String postDelete(@RequestParam int id) {
        alunos.remove(id);
        return "redirect:/alunos";
    }
}