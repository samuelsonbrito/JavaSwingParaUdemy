/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.descompila.utils;

import java.util.regex.Pattern;

/**
 *
 * @author samue
 */
public class ValidationUtils {
        // Valida CPF
    public static boolean validarCPF(String cpf) {
        String regexCPF = "^(?!000|111|222|333|444|555|666|777|888|999)\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}$";
        return Pattern.matches(regexCPF, cpf);
    }

    // Valida CNPJ
    public static boolean validarCNPJ(String cnpj) {
        String regexCNPJ = "^\\d{2}\\.\\d{3}\\.\\d{3}/\\d{4}-\\d{2}$";
        return Pattern.matches(regexCNPJ, cnpj);
    }

    // Valida email
    public static boolean validarEmail(String email) {
        String regexEmail = "^[A-Za-z0-9+_.-]+@(.+)$";
        return Pattern.matches(regexEmail, email);
    }

    // Valida telefone
    public static boolean validarTelefone(String telefone) {
        String regexTelefone = "\\(\\d{2}\\) \\d{4,5}-\\d{4}";
        return Pattern.matches(regexTelefone, telefone);
    }

    // Valida data no formato dd/MM/yyyy
    public static boolean validarData(String data) {
        String regexData = "^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[0-2])/\\d{4}$";
        return Pattern.matches(regexData, data);
    }

    // Valida número inteiro
    public static boolean validarNumeroInteiro(String numero) {
        String regexNumeroInteiro = "^\\d+$"; // Aceita apenas números inteiros
        return Pattern.matches(regexNumeroInteiro, numero);
    }

    // Valida número decimal (com ponto ou vírgula como separador)
    public static boolean validarNumeroDecimal(String numero) {
        String regexNumeroDecimal = "^[0-9]+([.,][0-9]+)?$"; // Aceita número decimal com ponto ou vírgula
        return Pattern.matches(regexNumeroDecimal, numero);
    }

    // Valida número com formatação específica (com vírgula como separador de milhar)
    public static boolean validarNumeroComMilhares(String numero) {
        String regexNumeroComMilhares = "^\\d{1,3}(?:\\.(\\d{3}))*$"; // Exemplo: 1.000, 1.000.000
        return Pattern.matches(regexNumeroComMilhares, numero);
    }
}
