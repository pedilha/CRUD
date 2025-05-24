// src/main/java/org/example/crud/dao/CursoDAO.java
package org.example.crud.dao;

import org.example.crud.config.ConnectionFactory;
import org.example.crud.model.Curso;
import org.example.crud.model.Area;

import java.sql.*;
import java.util.*;

public class CursoDAO implements ICursoDAO {
    private final Connection conn = new ConnectionFactory().getConnection();

    @Override
    public void create(Curso curso) {
        String sql = "INSERT INTO curso (nome, sigla, area) VALUES (?,?,?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, curso.getNome());
            ps.setString(2, curso.getSigla());
            ps.setString(3, curso.getArea().name());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) curso.setId(rs.getLong(1));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Curso curso) {
        String sql = "UPDATE curso SET nome = ?, area = ? WHERE sigla = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, curso.getNome());
            ps.setString(2, curso.getArea().name());
            ps.setString(3, curso.getSigla());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(String sigla) {
        String sql = "DELETE FROM curso WHERE sigla = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, sigla);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Curso> findAll() {
        String sql = "SELECT id, nome, sigla, area FROM curso";
        List<Curso> lista = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(new Curso(
                        rs.getLong("id"),
                        rs.getString("nome"),
                        rs.getString("sigla"),
                        Area.valueOf(rs.getString("area"))
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return lista;
    }

    @Override
    public Optional<Curso> findById(Long id) {
        String sql = "SELECT id, nome, sigla, area FROM curso WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return Optional.empty();
                return Optional.of(new Curso(
                        rs.getLong("id"),
                        rs.getString("nome"),
                        rs.getString("sigla"),
                        Area.valueOf(rs.getString("area"))
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Curso> findByArea(Area area) {
        String sql = "SELECT id, nome, sigla, area FROM curso WHERE area = ?";
        List<Curso> lista = new ArrayList<>();
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, area.name());
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(new Curso(
                            rs.getLong("id"),
                            rs.getString("nome"),
                            rs.getString("sigla"),
                            area
                    ));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return lista;
    }

    @Override
    public Optional<Curso> findBySigla(String sigla) {
        String sql = "SELECT id, nome, sigla, area FROM curso WHERE sigla = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, sigla);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) return Optional.empty();
                return Optional.of(new Curso(
                        rs.getLong("id"),
                        rs.getString("nome"),
                        rs.getString("sigla"),
                        Area.valueOf(rs.getString("area"))
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
