package org.example.crud.dao;

import org.example.crud.config.ConnectionFactory;
import org.example.crud.model.Aluno;
import org.example.crud.model.Curso;
import org.example.crud.model.Area;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AlunoDAO implements IAlunoDAO {

    @Override
    public Aluno create(Aluno aluno) {
        String sql = """
            INSERT INTO aluno (nome, maioridade, curso_sigla, sexo)
            VALUES (?,?,?,?)
            """;
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, aluno.getNome());
            ps.setBoolean(2, aluno.isMaioridade());
            ps.setString(3, aluno.getCurso().getSigla());
            ps.setString(4, aluno.getSexo());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    aluno.setMatricula(rs.getLong(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao criar aluno", e);
        }
        return aluno;
    }

    @Override
    public Aluno update(Aluno aluno) {
        String sql = """
            UPDATE aluno
               SET nome        = ?,
                   maioridade  = ?,
                   curso_sigla = ?,
                   sexo        = ?
             WHERE matricula   = ?
            """;
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, aluno.getNome());
            ps.setBoolean(2, aluno.isMaioridade());
            ps.setString(3, aluno.getCurso().getSigla());
            ps.setString(4, aluno.getSexo());
            ps.setLong(5, aluno.getMatricula());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar aluno", e);
        }
        return aluno;
    }

    @Override
    public void delete(long matricula) {
        String sql = "DELETE FROM aluno WHERE matricula = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, matricula);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar aluno", e);
        }
    }

    @Override
    public List<Aluno> findall() {
        String sql = """
            SELECT a.matricula,
                   a.nome,
                   a.maioridade,
                   a.sexo,
                   c.id      AS curso_id,
                   c.nome    AS curso_nome,
                   c.sigla   AS curso_sigla,
                   c.area    AS curso_area
              FROM aluno a
              JOIN curso c
                ON a.curso_sigla = c.sigla
            """;
        List<Aluno> lista = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Curso curso = new Curso(
                        rs.getLong("curso_id"),
                        rs.getString("curso_nome"),
                        rs.getString("curso_sigla"),
                        Area.valueOf(rs.getString("curso_area"))
                );
                Aluno a = new Aluno(
                        rs.getLong("matricula"),
                        rs.getString("nome"),
                        rs.getBoolean("maioridade"),
                        curso,
                        rs.getString("sexo")
                );
                lista.add(a);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar alunos", e);
        }
        return lista;
    }

    @Override
    public Optional<Aluno> findById(long matricula) {
        String sql = """
            SELECT a.matricula,
                   a.nome,
                   a.maioridade,
                   a.sexo,
                   c.id      AS curso_id,
                   c.nome    AS curso_nome,
                   c.sigla   AS curso_sigla,
                   c.area    AS curso_area
              FROM aluno a
              JOIN curso c
                ON a.curso_sigla = c.sigla
             WHERE a.matricula = ?
            """;
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, matricula);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    return Optional.empty();
                }
                Curso curso = new Curso(
                        rs.getLong("curso_id"),
                        rs.getString("curso_nome"),
                        rs.getString("curso_sigla"),
                        Area.valueOf(rs.getString("curso_area"))
                );
                Aluno a = new Aluno(
                        rs.getLong("matricula"),
                        rs.getString("nome"),
                        rs.getBoolean("maioridade"),
                        curso,
                        rs.getString("sexo")
                );
                return Optional.of(a);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar aluno por ID", e);
        }
    }

    @Override
    public List<Aluno> findByCurso(Curso curso) {
        String sql = """
            SELECT a.matricula,
                   a.nome,
                   a.maioridade,
                   a.sexo,
                   c.id      AS curso_id,
                   c.nome    AS curso_nome,
                   c.sigla   AS curso_sigla,
                   c.area    AS curso_area
              FROM aluno a
              JOIN curso c
                ON a.curso_sigla = c.sigla
             WHERE a.curso_sigla = ?
            """;
        List<Aluno> lista = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, curso.getSigla());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Curso c = new Curso(
                            rs.getLong("curso_id"),
                            rs.getString("curso_nome"),
                            rs.getString("curso_sigla"),
                            Area.valueOf(rs.getString("curso_area"))
                    );
                    Aluno a = new Aluno(
                            rs.getLong("matricula"),
                            rs.getString("nome"),
                            rs.getBoolean("maioridade"),
                            c,
                            rs.getString("sexo")
                    );
                    lista.add(a);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar alunos por curso", e);
        }
        return lista;
    }
}
