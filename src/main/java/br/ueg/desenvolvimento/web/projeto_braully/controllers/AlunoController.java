package br.ueg.desenvolvimento.web.projeto_braully.controllers;

import br.ueg.desenvolvimento.web.projeto_braully.entities.Aluno;
import br.ueg.desenvolvimento.web.projeto_braully.entities.Disciplina;
import br.ueg.desenvolvimento.web.projeto_braully.entities.TelefoneAluno;
import br.ueg.desenvolvimento.web.projeto_braully.repositories.AlunoRepository;
import br.ueg.desenvolvimento.web.projeto_braully.repositories.DisciplinaRepository;
import br.ueg.desenvolvimento.web.projeto_braully.repositories.TelefoneAlunoRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AlunoController {

    static List alunos = new ArrayList<>();

    @Autowired
    private AlunoRepository alunoRepository;

    static {
        alunos.add(Map.of("nome", "João", "email", "joao@localhost"));
        alunos.add(Map.of("nome", "Maria", "email", "maria@localhost"));
    }

    @GetMapping("/alunos")
    public String getHome(Model model) {
        List alunosBd = alunoRepository.findAll();
        model.addAttribute("alunos", alunosBd);
        // model.addAttribute("alunos", alunos);
        model.addAttribute("mensagem", "Todos os alunos cadastrados");
        return "alunos.html";
    }

    @GetMapping("/")
    public String index() {
        return "index.html";
    }

    @GetMapping("/alunos/create")
    public String getCreate() {
        return "aluno-create.html";
    }

    @PostMapping("/alunos/create")
    public String postCreate(@RequestParam String nome, @RequestParam String email) {
        Aluno aluno = new Aluno(nome, email);
        alunoRepository.save(aluno);
        return "redirect:/alunos";
    }

    @Autowired
    private DisciplinaRepository disciplinaRepository;

    @GetMapping("/alunos/update/{id}")
    public String getUpdate(@PathVariable int id, Model model) {
        // model.addAttribute("aluno", alunos.get(id));
        model.addAttribute("aluno", alunoRepository.findById(id).get());
        model.addAttribute("id", id);
        model.addAttribute("todasDisciplinas", disciplinaRepository.findAll());
        return "aluno-update.html";
    }

    @PostMapping("/alunos/update")
    public String postUpdate(@RequestParam int id,
            @RequestParam String nome,
            @RequestParam String email,
            @RequestParam List<Integer> disciplinas) {
        // alunos.set(id, Map.of("nome", nome, "email", email));
        Aluno aluno = alunoRepository.findById(id).get();
        aluno.setNome(nome);
        aluno.setEmail(email);
        List<Disciplina> disciplinasBd = new ArrayList<>();
        for (Integer discId : disciplinas) {
            Disciplina disciplinaBd = disciplinaRepository.findById(discId).get();
            disciplinasBd.add(disciplinaBd);
        }
        aluno.setDisciplinas(disciplinasBd);
        alunoRepository.save(aluno);
        return "redirect:/alunos";
    }

    @GetMapping("/alunos/update/telefone/{id}")
    public String updateTelefone(@PathVariable Integer id, Model model) {
        Aluno aluno = alunoRepository.findById(id).orElse(null);
        model.addAttribute("aluno", aluno);
        return "aluno-telefone";
    }

    @Autowired
    TelefoneAlunoRepository telefoneAlunoRepository;

    @PostMapping("/alunos/update/telefone")
    public String postUpdateTelefone(@RequestParam int id,
            @RequestParam String novoTelefone) {
        Aluno aluno = alunoRepository.findById(id).get();
        TelefoneAluno telefone = new TelefoneAluno();
        telefone.setNumero(novoTelefone);
        telefone.setAluno(aluno);
        telefoneAlunoRepository.save(telefone);
        return "redirect:/alunos/update/telefone/" + id;
    }

    @GetMapping("/alunos/delete/telefone/{id}/{idTelefone}")
    public String deleteTelefone(@PathVariable int id,
            @PathVariable int idTelefone) {
        telefoneAlunoRepository.deleteById(idTelefone);
        return "redirect:/alunos/update/telefone/" + id;
    }

    @GetMapping("/alunos/delete/{id}")
    public String getDelete(@PathVariable int id, Model model) {
        // model.addAttribute("aluno", alunos.get(id));
        Aluno alunodb = alunoRepository.findById(id).get();
        model.addAttribute("aluno", alunodb);
        model.addAttribute("id", id);
        return "aluno-delete.html";
    }

    @PostMapping("/alunos/delete")
    public String postDelete(@RequestParam int id) {
        // alunos.remove(id);
        alunoRepository.deleteById(id);
        return "redirect:/alunos";
    }

    @GetMapping("/alunos/busca")
    public String getBusca(@RequestParam String nome, Model model) {
        // model.addAttribute("alunos", alunos);
        model.addAttribute("alunos", alunoRepository.findByNomeContainingIgnoreCase(nome));
        return "alunos.html";
    }
}
