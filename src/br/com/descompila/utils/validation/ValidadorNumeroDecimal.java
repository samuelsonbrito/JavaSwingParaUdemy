/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.descompila.utils.validation;

/**
 *
 * @author samue
 */
public class ValidadorNumeroDecimal implements Validador {

    private final String mensagemErro;

    public ValidadorNumeroDecimal(String mensagemErro) {
        this.mensagemErro = mensagemErro;
    }

    @Override
    public boolean validar(String valor) {
        return valor.matches("[0-9]+([.,][0-9]+)?");
    }

    @Override
    public String getMensagemErro() {
        return mensagemErro;
    }
}
