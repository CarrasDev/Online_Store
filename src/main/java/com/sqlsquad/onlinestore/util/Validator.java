package com.sqlsquad.onlinestore.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

// Clase de utilidades para validar datos

public class Validator {

    private static final String EMAIL_REGEX = "^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$";

    // Valida un email
    public static boolean esEmailValido(String email) {
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    // Valida un DNI o NIE
    public static boolean esDniONieValido(String dni) {
        return dni.matches("^[XYZ]?[0-9]{7,8}[A-Za-z]$");
    }
}