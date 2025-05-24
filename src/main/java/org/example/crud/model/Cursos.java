package org.example.crud.model;

public enum Cursos {
    ADS("Análise e desenvolvimento de sistemas"),
    ECMP("Engenharia de computação"),
    CCMP("Ciencias da computação"),
    EQ("Engenharia quimica"),
    OUTROS("Outros");

    private String nomeCurso;
    private Cursos(String nomeCurso){this.nomeCurso = nomeCurso; }
    public String nomeCurso(){return this.nomeCurso; }
}
