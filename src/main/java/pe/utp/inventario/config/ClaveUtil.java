package pe.utp.inventario.config;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.GeneralSecurityException;
import java.util.Base64;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/** Verifica credenciales PBKDF2 almacenadas como pbkdf2:sha256:iter:salt:hash. */
public final class ClaveUtil {
    private ClaveUtil() { }

    public static boolean verificar(String claveIngresada, String almacenada) {
        if (claveIngresada == null || almacenada == null) return false;
        try {
            String[] partes = almacenada.split(":", -1);
            if (partes.length != 5 || !"pbkdf2".equals(partes[0]) || !"sha256".equals(partes[1])) {
                return false; // El APF1 guardaba texto plano; aplicar migración SQL del APF2.
            }
            int iteraciones = Integer.parseInt(partes[2]);
            if (iteraciones < 100000 || iteraciones > 1000000) return false;
            byte[] salt = Base64.getDecoder().decode(partes[3]);
            byte[] esperado = Base64.getDecoder().decode(partes[4]);
            if (salt.length != 16 || esperado.length != 32) return false;
            PBEKeySpec spec = new PBEKeySpec(claveIngresada.toCharArray(), salt, iteraciones, esperado.length * 8);
            byte[] obtenido;
            try {
                obtenido = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256").generateSecret(spec).getEncoded();
            } finally {
                spec.clearPassword();
            }
            return MessageDigest.isEqual(esperado, obtenido);
        } catch (GeneralSecurityException | IllegalArgumentException ex) {
            return false;
        }
    }
}
