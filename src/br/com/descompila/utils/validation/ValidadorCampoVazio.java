/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.descompila.utils.validation;

/**
 *
 * @author samue
 */
public class ValidadorCampoVazio implements Validador {
    private final String mensagemErro;

    public ValidadorCampoVazio(String mensagemErro) {
        this.mensagemErro = mensagemErro;
    }

    @Override
    public boolean validar(String valor) {
        return !valor.isBlank();
    }

    @Override
    public String getMensagemErro() {
        return mensagemErro;
    }
}
