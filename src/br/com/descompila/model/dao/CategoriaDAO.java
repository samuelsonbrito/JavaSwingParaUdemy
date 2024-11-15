/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.descompila.model.dao;

import br.com.descompila.connection.ConnectionFactory;
import br.com.descompila.model.entity.Categoria;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author samue
 */
public class CategoriaDAO {

    public void salvar(Categoria categoria) throws Exception {

        var sql = "INSERT INTO categoria (descricao) VALUES (?)";

        try(var conn = ConnectionFactory.getConnection();
            var stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, categoria.getNome());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            System.err.println(ex);
            throw new Exception(ex);
        }
    }

    public List<Categoria> findAll() throws Exception {

        var sql = "SELECT * FROM categoria";

        var categorias = new ArrayList<Categoria>();

        try (var conn = ConnectionFactory.getConnection();
                var stmt = conn.prepareStatement(sql)){
            
            try(var rs = stmt.executeQuery()){
                while (rs.next()) {
                    var categoria = new Categoria();
                    categoria.setId(rs.getLong("id"));
                    categoria.setNome(rs.getString("descricao"));
                    categorias.add(categoria);
                }
            }
            
        } catch (SQLException ex) {
            System.err.println(ex);
            throw new Exception(ex);
        }

        return categorias;
    }

    public void update(Categoria categoria) throws Exception {

        var sql = "UPDATE categoria SET descricao = ? WHERE id = ?";

        try (var conn = ConnectionFactory.getConnection();
                var stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, categoria.getNome());
            stmt.setLong(2, categoria.getId());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            System.err.println(ex);
            throw new Exception(ex);
        }
    }

    public boolean excluir(Categoria categoria) throws Exception {

        var sql = "DELETE FROM categoria WHERE id = ?";

        try (var conn = ConnectionFactory.getConnection();
                var stmt = conn.prepareStatement(sql)){
            stmt.setLong(1, categoria.getId());
            stmt.executeUpdate();
            return true;
        } catch (SQLException ex) {
            System.err.println(ex);
            throw new Exception(ex);
        }
    }

}
