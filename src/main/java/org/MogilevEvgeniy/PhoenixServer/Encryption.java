package org.MogilevEvgeniy.PhoenixServer;

public class Encryption {

  private static final String RUSSIAN_ALPHABET = "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ";
  private static final String LATIN_ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

  public static String encrypt(String text, String key) {
    StringBuilder encrypted = new StringBuilder();
    key = key.toUpperCase();
    int keyIndex = 0;

    for (char c : text.toCharArray()) {
      if (Character.isWhitespace(c)) {
        encrypted.append(c);
        continue;
      }

      char encryptedChar = c;
      char originalChar = Character.toUpperCase(c);
      if (RUSSIAN_ALPHABET.indexOf(originalChar) >= 0) {
        int shift = RUSSIAN_ALPHABET.indexOf(
            Character.toUpperCase(key.charAt(keyIndex % key.length())));
        int originalIndex = RUSSIAN_ALPHABET.indexOf(originalChar);
        int newIndex = (originalIndex + shift) % RUSSIAN_ALPHABET.length();
        encryptedChar = RUSSIAN_ALPHABET.charAt(newIndex);
        keyIndex++;
      } else if (LATIN_ALPHABET.indexOf(originalChar) >= 0) {
        int shift = LATIN_ALPHABET.indexOf(
            Character.toUpperCase(key.charAt(keyIndex % key.length())));
        int originalIndex = LATIN_ALPHABET.indexOf(originalChar);
        int newIndex = (originalIndex + shift) % LATIN_ALPHABET.length();
        encryptedChar = LATIN_ALPHABET.charAt(newIndex);
        keyIndex++;
      }
      // Сохраняем регистр исходного символа
      if (Character.isLowerCase(c)) {
        encryptedChar = Character.toLowerCase(encryptedChar);
      }
      encrypted.append(encryptedChar);
    }
    return encrypted.toString();
  }

  public static String decrypt(String text, String key) {
    StringBuilder decrypted = new StringBuilder();
    key = key.toUpperCase();
    int keyIndex = 0;

    for (char c : text.toCharArray()) {
      if (Character.isWhitespace(c)) {
        decrypted.append(c);
        continue;
      }

      char decryptedChar = c;
      char originalChar = Character.toUpperCase(c);
      if (RUSSIAN_ALPHABET.indexOf(originalChar) >= 0) {
        int shift = RUSSIAN_ALPHABET.indexOf(
            Character.toUpperCase(key.charAt(keyIndex % key.length())));
        int originalIndex = RUSSIAN_ALPHABET.indexOf(originalChar);
        int newIndex =
            (originalIndex - shift + RUSSIAN_ALPHABET.length()) % RUSSIAN_ALPHABET.length();
        decryptedChar = RUSSIAN_ALPHABET.charAt(newIndex);
        keyIndex++;
      } else if (LATIN_ALPHABET.indexOf(originalChar) >= 0) {
        int shift = LATIN_ALPHABET.indexOf(
            Character.toUpperCase(key.charAt(keyIndex % key.length())));
        int originalIndex = LATIN_ALPHABET.indexOf(originalChar);
        int newIndex = (originalIndex - shift + LATIN_ALPHABET.length()) % LATIN_ALPHABET.length();
        decryptedChar = LATIN_ALPHABET.charAt(newIndex);
        keyIndex++;
      }
      // Сохраняем регистр исходного символа
      if (Character.isLowerCase(c)) {
        decryptedChar = Character.toLowerCase(decryptedChar);
      }
      decrypted.append(decryptedChar);
    }
    return decrypted.toString();
  }
}
