package org.example.crud.model;

import org.example.crud.dao.AlunoDAO;
import org.example.crud.dao.CursoDAO;
import org.example.crud.model.Aluno;
import org.example.crud.model.Curso;

import java.util.Optional;
import java.util.Scanner;

public class AlunoCreate {
    public static void main(String[] args) {
        AlunoDAO alunoDAO = new AlunoDAO();
        CursoDAO cursoDAO = new CursoDAO();
        Scanner sc = new Scanner(System.in);

        System.out.print("Nome: ");
        String nome = sc.nextLine();

        System.out.print("Maioridade (S/N): ");
        boolean maior = sc.nextLine().equalsIgnoreCase("S");

        System.out.print("Sexo (M/F/Outro): ");
        String sexo = sc.nextLine().trim();

        // Lê sigla e busca Curso
        System.out.print("Sigla do curso: ");
        String sigla = sc.nextLine().trim().toUpperCase();
        Optional<Curso> optCurso = cursoDAO.findBySigla(sigla);
        if (optCurso.isEmpty()) {
            System.out.println("Curso não encontrado para sigla: " + sigla);
            sc.close();
            return;
        }
        Curso curso = optCurso.get();

        // Cria e persiste
        Aluno novo = new Aluno(
                nome,
                maior,
                curso,
                0L      // matrícula 0 → DAO gera novo ID
        );
        novo.setSexo(sexo);

        alunoDAO.create(novo);
        System.out.println("Aluno criado com matrícula: " + novo.getMatricula());

        sc.close();
    }
}
