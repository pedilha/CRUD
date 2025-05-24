package org.example.crud.model;

import org.example.crud.dao.AlunoDAO;
import java.util.Scanner;

public class AlunoDelete {
    public static void main(String[] args){
        AlunoDAO alunoDAO = new AlunoDAO();
        Scanner sc = new Scanner(System.in);

        System.out.print("Informe a matrícula do aluno a deletar: ");
        long matricula = sc.nextLong();
        sc.close();

        alunoDAO.delete(matricula);
        System.out.println("Aluno com matrícula " + matricula + " deletado com sucesso!");
    }
}
