package org.example.crud.dao;

import org.example.crud.model.Aluno;
import org.example.crud.model.Curso;
import org.example.crud.model.Cursos;

import java.util.List;
import java.util.Optional;

public interface IAlunoDAO {
    Aluno create (Aluno aluno);
    Aluno update (Aluno aluno);
    void delete (long matricula);
    List<Aluno> findall();
    Optional<Aluno>  findById(long matricula);
    List<Aluno> findByCurso(Curso curso);

}
