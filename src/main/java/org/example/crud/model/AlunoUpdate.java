package org.example.crud.model;

import org.example.crud.dao.AlunoDAO;
import org.example.crud.dao.CursoDAO;
import org.example.crud.model.Aluno;
import org.example.crud.model.Curso;

import java.util.Optional;
import java.util.Scanner;

public class AlunoUpdate {
    public static void main(String[] args) {
        AlunoDAO   alunoDAO = new AlunoDAO();
        CursoDAO   cursoDAO = new CursoDAO();
        Scanner    sc       = new Scanner(System.in);

        System.out.print("Informe matrícula do aluno que deseja atualizar: ");
        long mat = Long.parseLong(sc.nextLine());

        Optional<Aluno> opt = alunoDAO.findById(mat);
        if (opt.isEmpty()) {
            System.out.println("Aluno não encontrado para matrícula " + mat);
            return;
        }

        Aluno a = opt.get();

        // --- Nome
        System.out.println("Nome atual: " + a.getNome());
        System.out.print("Novo nome (enter para manter): ");
        String novoNome = sc.nextLine();
        if (!novoNome.isBlank()) {
            a.setNome(novoNome);
        }

        // --- Maioridade
        System.out.println("Maioridade atual: " + (a.isMaioridade() ? "Adulto" : "Adolescente"));
        System.out.print("Deseja trocar (S/N)? ");
        if (sc.nextLine().equalsIgnoreCase("S")) {
            a.setMaioridade(!a.isMaioridade());
        }

        // --- Curso
        Curso atual = a.getCurso();
        System.out.println("Curso atual: "
                + atual.getNome()
                + " (" + atual.getSigla() + ")");
        System.out.print("Nova sigla de curso (enter para manter): ");
        String novaSigla = sc.nextLine().trim().toUpperCase();
        if (!novaSigla.isBlank()) {
            Optional<Curso> optC = cursoDAO.findBySigla(novaSigla);
            if (optC.isPresent()) {
                a.setCurso(optC.get());
            } else {
                System.out.println("Sigla inválida — mantendo o curso atual.");
            }
        }

        // --- Sexo
        System.out.println("Sexo atual: " + a.getSexo());
        System.out.print("Novo sexo (M/F/Outro, enter para manter): ");
        String novoSexo = sc.nextLine();
        if (!novoSexo.isBlank()) {
            a.setSexo(novoSexo);
        }

        // Persiste a alteração
        alunoDAO.update(a);
        System.out.println("Aluno atualizado com sucesso!");
        sc.close();
    }
}
