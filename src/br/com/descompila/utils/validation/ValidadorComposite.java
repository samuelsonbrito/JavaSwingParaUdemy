/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.descompila.utils.validation;

import java.util.List;

/**
 *
 * @author samue
 */
public class ValidadorComposite implements Validador {

    private final List<Validador> validadores;

    public ValidadorComposite(List<Validador> validadores) {
        this.validadores = validadores;
    }

    @Override
    public boolean validar(String valor) {
        for (Validador validador : validadores) {
            if (!validador.validar(valor)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String getMensagemErro() {
        // Retorna a mensagem do primeiro validador que falhou
        for (Validador validador : validadores) {
            if (!validador.validar("")) {  // Aqui, deveria ser o valor que está sendo validado
                return validador.getMensagemErro();
            }
        }
        return null;
    }
}
