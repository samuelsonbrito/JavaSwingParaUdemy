/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.descompila.model.dao;

import br.com.descompila.connection.ConnectionFactory;
import br.com.descompila.model.entity.Categoria;
import br.com.descompila.model.entity.Produto;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Samuelson
 */
public class ProdutoDAO {

    public void salvar(Produto produto) throws SQLException {

        var sql = "INSERT INTO produto (descricao, qtd, valor, categoria_id) VALUES (?,?,?,?)";

        try(var conn = ConnectionFactory.getConnection();
                var stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, produto.getNome());
            stmt.setInt(2, produto.getQuantidade());
            stmt.setDouble(3, produto.getValor());
            stmt.setLong(4, produto.getCategoria().getId());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            System.err.println(ex);
            throw new SQLException("Erro ao inserir", ex);
        }
    }

    public List<Produto> buscarTodos() throws SQLException {

        var sql = "select p.id as pid, p.descricao as pdesc, qtd, valor, c.id as cid, c.descricao as cdesc from produto p inner join categoria c on c.id = p.categoria_id";
        
        List<Produto> produtos = new ArrayList<>();

        try (var conn = ConnectionFactory.getConnection();
                var stmt = conn.prepareStatement(sql)){

            try (var rs = stmt.executeQuery()){
                while (rs.next()) {

                    var produto = new Produto();

                    produto.setId(rs.getLong("pid"));
                    produto.setNome(rs.getString("pdesc"));
                    produto.setQuantidade(rs.getInt("qtd"));
                    produto.setValor(rs.getDouble("valor"));

                    var categoria = new Categoria();
                    categoria.setId(rs.getLong("cid"));
                    categoria.setNome(rs.getString("cdesc"));

                    produto.setCategoria(categoria);

                    produtos.add(produto);
                }
            }
            
        } catch (SQLException ex) {
            System.err.println(ex);
            throw new SQLException("Erro ao listar", ex);
        } 

        return produtos;
    }
    
    public Produto buscarPorId(Long id) throws SQLException{

        var sql = "select p.id as pid, p.descricao as pdesc, qtd, valor, c.id as cid, c.descricao as cdesc from produto p inner join categoria c on c.id = p.categoria_id where p.id = ?";
        
        var produto = new Produto();
        
        try (var conexao = ConnectionFactory.getConnection();
            var stmt = conexao.prepareStatement(sql)) {
                stmt.setLong(1, id);
                try(var rs = stmt.executeQuery()){
                    while (rs.next()) {
                        
                        produto.setId(rs.getLong("pid"));
                        produto.setNome(rs.getString("pdesc"));
                        produto.setQuantidade(rs.getInt("qtd"));
                        produto.setValor(rs.getDouble("valor"));

                        var categoria = new Categoria();
                        categoria.setId(rs.getLong("cid"));
                        categoria.setNome(rs.getString("cdesc"));

                        produto.setCategoria(categoria);
                    }
                }

        } catch (SQLException ex) {
            System.err.println(ex);
            throw new SQLException(ex);
        }

        return produto;
    }

    public void atualizar(Produto produto) throws SQLException {

        var sql = "UPDATE produto SET descricao = ?, qtd = ?, valor = ?, categoria_id = ? WHERE id = ?";

        try (var conn = ConnectionFactory.getConnection();
                var stmt = conn.prepareStatement(sql)){
            
            stmt.setString(1, produto.getNome());
            stmt.setInt(2, produto.getQuantidade());
            stmt.setDouble(3, produto.getValor());
            stmt.setLong(4, produto.getCategoria().getId());
            stmt.setLong(5, produto.getId());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            System.err.println(ex);
            throw new SQLException("Erro ao atualizar", ex);
        }
    }

    public void delete(Produto produto) throws SQLException {

        var sql = "DELETE FROM produto WHERE id = ?";

        try (var conn = ConnectionFactory.getConnection();
                var stmt = conn.prepareStatement(sql)){
            stmt.setLong(1, produto.getId());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            System.err.println(ex);
            throw new SQLException("Erro ao excluir", ex);
        }
    }
    
    public List<Produto> buscarPorDescricao(String desc) throws SQLException {

        var produtos = new ArrayList<Produto>();

        try (var conn = ConnectionFactory.getConnection();
                var stmt = conn.prepareStatement("select p.id as pid, p.descricao as pdesc, qtd, valor, c.id as cid, c.descricao as cdesc from produto p inner join categoria c on c.id = p.categoria_id WHERE p.descricao LIKE ?")){
            
            stmt.setString(1, "%"+desc+"%");
            
            try(var rs = stmt.executeQuery()){
                while (rs.next()) {

                    var produto = new Produto();
                    produto.setId(rs.getLong("pid"));
                    produto.setNome(rs.getString("pdesc"));
                    produto.setQuantidade(rs.getInt("qtd"));
                    produto.setValor(rs.getDouble("valor"));

                    var categoria = new Categoria();
                    categoria.setId(rs.getLong("cid"));
                    categoria.setNome(rs.getString("cdesc"));

                    produto.setCategoria(categoria);

                    produtos.add(produto);
                }
            }

        } catch (SQLException ex) {
            System.err.println(ex);
            throw new SQLException("Erro ao excluir", ex);
        }
        
        return produtos;

    }
}
