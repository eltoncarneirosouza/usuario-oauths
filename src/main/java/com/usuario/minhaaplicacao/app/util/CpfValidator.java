package com.usuario.minhaaplicacao.app.util;

public class CpfValidator {
  public static boolean isValid(String cpf) {
    if (cpf == null) return false;
    String onlyDigits = cpf.replaceAll("\\D", "");
    if (onlyDigits.length() != 11) return false;
    if (onlyDigits.matches("(\\d)\\1{10}")) return false;

    int[] digits = new int[11];
    for (int i = 0; i < 11; i++) digits[i] = Character.getNumericValue(onlyDigits.charAt(i));

    int sum = 0;
    for (int i = 0; i < 9; i++) sum += digits[i] * (10 - i);
    int dv1 = 11 - (sum % 11);
    dv1 = (dv1 >= 10) ? 0 : dv1;
    if (dv1 != digits[9]) return false;

    sum = 0;
    for (int i = 0; i < 10; i++) sum += digits[i] * (11 - i);
    int dv2 = 11 - (sum % 11);
    dv2 = (dv2 >= 10) ? 0 : dv2;
    return dv2 == digits[10];
  }
}