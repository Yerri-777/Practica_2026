package utilidades;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class UtilidadPassword {

    private UtilidadPassword() {
    }

    public static String encriptar(String password) {

        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());

            StringBuilder hexString = new StringBuilder();

            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1)
                    hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error al encriptar contraseña", e);
        }
    }

    public static boolean verificar(String passwordPlano, String passwordHash) {
        return encriptar(passwordPlano).equals(passwordHash);
    }
}