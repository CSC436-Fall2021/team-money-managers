package csc.arizona.moneymanager.database;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Random;

/**
 * This class contains the functionality related to generating a secure
 * password hash and salt as well as a method to verify a password given
 * the secured password and salt.
 *
 * @author Kris Rangel
 */
public class PasswordUtilities {

    private final Random RANDOM = new SecureRandom();
    private final int HASH_LENGTH = 256;
    private final int SALT_LENGTH = 44;
    private final int ITERATIONS = 5000;

    public PasswordUtilities(){}

    /**
     * Randomly generates a String of the specified length from randomly selected alpha-numeric
     * characters.
     *
     * @param length the length of String to generate.
     * @return a random alpha-numeric salt of the specified length.
     */
    public String getSalt(int length) {
        String alphabet = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        StringBuilder salt = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            salt.append(alphabet.charAt(RANDOM.nextInt(alphabet.length())));
        }
        return new String(salt);
    }

    /**
     * Generates a hashed password from a specified password and salt value.
     *
     * @param password the password to hash.
     * @param salt     the salt to use in the hashing.
     * @return the String representation of the secure hashed password.
     */
    public String generateSecurePassword(String password, String salt) {
        String securePassword = "";

        // Setting the password based encryption key specifications
        PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), ITERATIONS, HASH_LENGTH);

        // Creating hash and storing value in securePassword
        try {
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] hash = skf.generateSecret(spec).getEncoded();
            securePassword = Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            System.out.printf("Error occurred during hashing: %s\n", e.getMessage());
        } finally {
            // Clearing plaintext passwords in memory
            spec.clearPassword();                           // Clearing password in pbe key specs
            char[] passArray = new char[password.length()]; // Creating empty char array same length as password
            Arrays.fill(passArray, Character.MIN_VALUE);    // Setting char array values to minimum character
            password = String.valueOf(passArray);           // Overwriting argument password value
        }

        return securePassword;
    }

    /**
     * Tests whether provided password was the original password used to generate the hashed password.
     *
     * @param providedPassword the user-provided password
     * @param securedPassword  the hashed secure password
     * @param salt             the salt used to hash the original password
     * @return true if the provided password matches the original, false otherwise.
     */
    public boolean verifyUserPassword(String providedPassword, String securedPassword, String salt) {
        boolean areSame = false;

        String securePasswordFromProvided = generateSecurePassword(providedPassword, salt);

        // Clearing plaintext password in memory
        char[] passArray = new char[providedPassword.length()]; // Creating empty char array same length as password
        Arrays.fill(passArray, Character.MIN_VALUE);            // Setting char array values to minimum character
        providedPassword = String.valueOf(passArray);           // Overwriting argument password value

        areSame = securePasswordFromProvided.equals(securedPassword);

        return areSame;
    }
}