package org.example.crud.model;

import org.example.crud.dao.AlunoDAO;
import org.example.crud.dao.CursoDAO;
import org.example.crud.model.Curso;
import org.example.crud.model.Aluno;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class AlunoListagens {
    public static void main(String[] args) {
        AlunoDAO alunoDAO   = new AlunoDAO();
        CursoDAO cursoDAO   = new CursoDAO();
        Scanner sc          = new Scanner(System.in);

        System.out.println(
                "Digite [1] para listar todos\n" +
                        "Digite [2] para listar por matrícula\n" +
                        "Digite [3] para listar por curso"
        );
        int opc = sc.nextInt();
        sc.nextLine(); // consome o ENTER

        switch (opc) {
            case 1 -> {
                List<Aluno> todos = alunoDAO.findall();
                todos.forEach(AlunoListagens::printAluno);
            }
            case 2 -> {
                System.out.print("Informe a matrícula: ");
                long mat = sc.nextLong();
                sc.nextLine();
                Optional<Aluno> opt = alunoDAO.findById(mat);
                if (opt.isPresent()) {
                    printAluno(opt.get());
                } else {
                    System.out.println("Nenhum aluno encontrado para matrícula " + mat);
                }
            }
            case 3 -> {
                System.out.print("Informe a sigla do curso: ");
                String sigla = sc.nextLine().trim().toUpperCase();

                Optional<Curso> optCurso = cursoDAO.findBySigla(sigla);
                if (optCurso.isEmpty()) {
                    System.out.println("Curso não encontrado: " + sigla);
                } else {
                    Curso curso = optCurso.get();
                    List<Aluno> porCurso = alunoDAO.findByCurso(curso);
                    if (porCurso.isEmpty()) {
                        System.out.println("Nenhum aluno encontrado para o curso " + sigla);
                    } else {
                        porCurso.forEach(AlunoListagens::printAluno);
                    }
                }
            }
            default -> System.out.println("Opção inválida: " + opc);
        }

        sc.close();
    }

    private static void printAluno(Aluno a) {
        System.out.println("Matrícula: " + a.getMatricula());
        System.out.println("Nome:      " + a.getNome());
        System.out.println(a.isMaioridade() ? "Adulto" : "Adolescente");

        Curso c = a.getCurso();
        System.out.println("Curso:     "
                + c.getNome() + " (" + c.getSigla() + ") – Área: " + c.getArea());

        System.out.println("Sexo:      " + a.getSexo());
        System.out.println("============================================");
    }
}
