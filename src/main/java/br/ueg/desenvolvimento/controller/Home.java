package br.ueg.desenvolvimento.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Home {
    
    @SuppressWarnings("unchecked")
    @GetMapping("/")
    public String getHome(Model model) {
        @SuppressWarnings("rawtypes")
        List alunos = new ArrayList<>();
        alunos.add(Map.of("nome", "João", "email", "joao@localhost"));
        model.addAttribute("alunos", alunos);
        return "home";
    }
}
