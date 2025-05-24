package org.example.crud.model;

import org.example.crud.dao.AlunoDAO;

public class AlunoDelete {
    public static void main(String[] args){

        AlunoDAO alunoDAO = new AlunoDAO();
        alunoDAO.delete(5);
        System.out.println("Aluno deletado com sucesso!!");
    }
}
