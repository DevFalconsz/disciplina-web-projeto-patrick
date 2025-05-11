package br.ueg.desenvolvimento.web.projeto_braully.repositories;

import br.ueg.desenvolvimento.web.projeto_braully.entities.Aluno;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AlunoRepository extends JpaRepository<Aluno, Integer> {

    List<Aluno> findByNomeContainingIgnoreCase(String nome);

}