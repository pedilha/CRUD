package org.example.crud;

import org.example.crud.dao.AlunoDAO;
import org.example.crud.model.Aluno;
import org.example.crud.model.Cursos;

import java.util.List;
import java.util.Optional;

public class Principal {
    public static void main(String[] args){
       AlunoDAO alunoDAO = new AlunoDAO();

        /* Aluno aluno = new Aluno();
        aluno.setNome("Patrick estrela");
        aluno.setMaioridade(true);
        aluno.setCurso(Cursos.ADS);
        aluno.setSexo("masculino");

        Aluno novoAluno = alunoDAO.create(aluno);
        System.out.println("Matricula do novo aluno: "+ novoAluno.getMatricula());
        */
       /* List<Aluno> lista = alunoDAO.findall();
        for (Aluno a : lista){
            System.out.println("Matriula: "+a.getMatricula());
            System.out.print("Nome: "+a.getNome());
            System.out.println(a.isMaioridade()?" - Adulto":" - Adolescente:");
            System.out.println("Curso: "+a.getCurso().nomeCurso());
            System.out.println("Sexo: "+a.getSexo());
            System.out.println("============================================");
        }
        */
        /*
        Optional<Aluno> aluno = alunoDAO.findById(1L);
        aluno.ifPresent(a -> {
            System.err.println("Matriula: "+a.getMatricula());
            System.err.print("Nome: "+a.getNome());
            System.err.println(a.isMaioridade()?" - Adulto":" - Adolescente:");
            System.err.println("Curso: "+a.getCurso().nomeCurso());
            System.err.println("Sexo: "+a.getSexo());
        }); */
        /*List<Aluno> lista = alunoDAO.findByCurso(Cursos.ADS);
        for (Aluno a : lista){
            System.out.println("Matriula: "+a.getMatricula());
            System.out.print("Nome: "+a.getNome());
            System.out.println(a.isMaioridade()?" - Adulto":" - Adolescente:");
            System.out.println("Curso: "+a.getCurso().nomeCurso());
            System.out.println("Sexo: "+a.getSexo());
            System.out.println("============================================");
        }*/

    }
}
