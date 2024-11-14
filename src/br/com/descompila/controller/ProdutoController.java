/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.descompila.controller;

import br.com.descompila.model.dao.CategoriaDAO;
import br.com.descompila.model.entity.Categoria;
import br.com.descompila.model.entity.Produto;
import br.com.descompila.model.dao.ProdutoDAO;
import br.com.descompila.utils.ValidationUtils;
import br.com.descompila.view.produto.ViewProduto;
import java.awt.Color;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author samue
 */
public class ProdutoController {

    private final ViewProduto view;

    public ProdutoController(ViewProduto view) {
        this.view = view;
        ordenarTabela();
    }

    public void salvar() {
        
        if(view.getTxtDesc().getText().isBlank()){
            JOptionPane.showMessageDialog(view, "Descrição não pode ser vazia", "Alerta", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if(view.getTxtQtd().getText().isBlank()){
            JOptionPane.showMessageDialog(view, "Quantidade não pode ser vazia", "Alerta", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if(view.getTxtPreco().getText().isBlank()){
            JOptionPane.showMessageDialog(view, "Preço não pode ser vazio", "Alerta", JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        boolean validNumeroInteiro = ValidationUtils.validarNumeroInteiro(view.getTxtQtd().getText());
        
        if (!validNumeroInteiro) {
            view.getTxtQtd().setBackground(Color.PINK);
            JOptionPane.showMessageDialog(view, "Número inteiro inválido!", "Alerta", JOptionPane.WARNING_MESSAGE);
            return;
        } else {
            view.getTxtQtd().setBackground(Color.WHITE);
        }
        
        boolean validNumeroDecimal = ValidationUtils.validarNumeroDecimal(view.getTxtPreco().getText());
        
        if (!validNumeroDecimal) {
            view.getTxtPreco().setBackground(Color.PINK);
            JOptionPane.showMessageDialog(view, "Número decimal inválido!", "Alerta", JOptionPane.WARNING_MESSAGE);
            return;
        } else {
            view.getTxtQtd().setBackground(Color.WHITE);
        }
        
        try {

            Produto p = new Produto();
            ProdutoDAO dao = new ProdutoDAO();

            p.setNome(view.getTxtDesc().getText());
            p.setQuantidade(Integer.valueOf(view.getTxtQtd().getText()));
            p.setValor(Double.valueOf(view.getTxtPreco().getText()));

            Categoria categoria = (Categoria) view.getCbCategoria().getSelectedItem();

            p.setCategoria(categoria);

            dao.salvar(p);
            carregarTabela();
            JOptionPane.showMessageDialog(view, "Produto salvo", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(view, ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void carregarTabela() {

        DefaultTableModel modelo = (DefaultTableModel) view.getjTProdutos().getModel();
        modelo.setNumRows(0);
        ProdutoDAO pdao = new ProdutoDAO();

        try {
            for (Produto p : pdao.findAll()) {

                modelo.addRow(new Object[]{
                    p.getId(),
                    p.getNome(),
                    p.getQuantidade(),
                    p.getValor()
                });

            }
        } catch (Exception ex) {
            Logger.getLogger(ViewProduto.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void excluir() {
        if (view.getjTProdutos().getSelectedRow() != -1) {

            Produto p = new Produto();
            ProdutoDAO dao = new ProdutoDAO();

            p.setId((long) view.getjTProdutos().getValueAt(view.getjTProdutos().getSelectedRow(), 0));

            try {
                dao.delete(p);
                view.getTxtDesc().setText("");
                view.getTxtQtd().setText("");
                view.getTxtPreco().setText("");

                carregarTabela();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }

        } else {
            JOptionPane.showMessageDialog(null, "Selecione um produto para excluir.");
        }
    }

    public void preencherDadosNosCampos() {
        if (view.getjTProdutos().getSelectedRow() != -1) {
            view.getTxtDesc().setText(view.getjTProdutos().getValueAt(view.getjTProdutos().getSelectedRow(), 1).toString());
            view.getTxtQtd().setText(view.getjTProdutos().getValueAt(view.getjTProdutos().getSelectedRow(), 2).toString());
            view.getTxtPreco().setText(view.getjTProdutos().getValueAt(view.getjTProdutos().getSelectedRow(), 3).toString());
        }
    }

    public void atualizar() {

        if (view.getjTProdutos().getSelectedRow() != -1) {

            Produto p = new Produto();
            ProdutoDAO dao = new ProdutoDAO();

            p.setNome(view.getTxtDesc().getText());
            p.setQuantidade(Integer.valueOf(view.getTxtQtd().getText()));
            p.setValor(Double.valueOf(view.getTxtPreco().getText()));
            p.setId((Long) view.getjTProdutos().getValueAt(view.getjTProdutos().getSelectedRow(), 0));
            try {
                dao.atualizar(p);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }

            view.getTxtDesc().setText("");
            view.getTxtQtd().setText("");
            view.getTxtPreco().setText("");

            carregarTabela();

        }
    }

    public void carregarComboCategorias() {
        CategoriaDAO categoriaDAO = new CategoriaDAO();

        try {
            for (Categoria categoria : categoriaDAO.findAll()) {
                view.getCbCategoria().addItem(categoria);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }
    }

    private void ordenarTabela() {
        DefaultTableModel modelo = (DefaultTableModel) view.getjTProdutos().getModel();
        view.getjTProdutos().setRowSorter(new TableRowSorter(modelo));
    }

    public void pesquisarPorNome() {
        DefaultTableModel modelo = (DefaultTableModel) view.getjTProdutos().getModel();
        modelo.setNumRows(0);
        ProdutoDAO pdao = new ProdutoDAO();

        try {
            for (Produto p : pdao.readForDesc(view.getTxtBuscaDesc().getText())) {

                modelo.addRow(new Object[]{
                    p.getId(),
                    p.getNome(),
                    p.getQuantidade(),
                    p.getValor()
                });

            }
        } catch (Exception ex) {
            Logger.getLogger(ViewProduto.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
