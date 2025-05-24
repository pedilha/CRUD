package org.example.crud.model;

public class Aluno {
    private long matricula;
    private String nome;
    private boolean maioridade;
    private Curso curso;
    private String sexo;

    // Construtor completo (inclui sexo)
    public Aluno(long matricula,
                 String nome,
                 boolean maioridade,
                 Curso curso,
                 String sexo) {
        this.matricula  = matricula;
        this.nome       = nome;
        this.maioridade = maioridade;
        this.curso      = curso;
        this.sexo       = sexo;
    }

    // Construtor sem sexo (se necessário)
    public Aluno(String nome,
                 boolean maioridade,
                 Curso curso,
                 long matricula) {
        this(matricula, nome, maioridade, curso, null);
    }

    // Construtor padrão
    public Aluno() { }

    // getters & setters
    public long getMatricula() {
        return matricula;
    }

    public void setMatricula(long matricula) {
        this.matricula = matricula;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public boolean isMaioridade() {
        return maioridade;
    }

    public void setMaioridade(boolean maioridade) {
        this.maioridade = maioridade;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }
}
