package banco.dao;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Seguranca {

    public static String criptografarSenha(String senhaPura) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(senhaPura.getBytes());
            StringBuilder hexString = new StringBuilder();

            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            System.err.println("Erro ao inicializar algoritmo de criptografia: " + e);
            return senhaPura;
        }
    }
}