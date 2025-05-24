// src/main/java/org/example/crud/dao/ICursoDAO.java
package org.example.crud.dao;

import org.example.crud.model.Curso;
import org.example.crud.model.Area;
import java.util.List;
import java.util.Optional;

public interface ICursoDAO {
    void create(Curso curso);
    void update(Curso curso);
    void delete(String sigla);
    List<Curso> findAll();
    Optional<Curso> findById(Long id);
    List<Curso> findByArea(Area area);
    Optional<Curso> findBySigla(String sigla);
}
