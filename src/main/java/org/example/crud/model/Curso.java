// src/main/java/org/example/crud/model/Curso.java
package org.example.crud.model;

public class Curso {
    private Long id;
    private String nome;
    private String sigla;
    private Area area;

    public Curso(Long id, String nome, String sigla, Area area) {
        this.id   = id;
        this.nome = nome;
        this.sigla= sigla;
        this.area = area;
    }
    public Curso(String nome, String sigla, Area area) {
        this(null, nome, sigla, area);
    }

    // getters e setters
    public Long   getId()   { return id; }
    public void   setId(Long id) { this.id = id; }

    public String getNome() { return nome; }
    public void   setNome(String nome) { this.nome = nome; }

    public String getSigla(){ return sigla; }
    public void   setSigla(String sigla){ this.sigla = sigla; }

    public Area   getArea() { return area; }
    public void   setArea(Area area) { this.area = area; }
}
