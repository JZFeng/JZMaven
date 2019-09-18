package com.jz.java.network.security.cypher;

import javax.crypto.Cipher;
import java.io.*;
import java.security.*;
import java.security.cert.X509Certificate;
import java.util.Base64;

public class X509 {
  private PrivateKey privateKey;

  private X509Certificate certificate;

  private X509(KeyStore ks, String certName, String password) {
    try {
      privateKey = (PrivateKey) ks.getKey(certName, password.toCharArray());
      certificate = (X509Certificate) ks.getCertificate(certName);
    } catch (GeneralSecurityException e) {
      throw new RuntimeException(e);
    }
  }

  private byte[] encrypt(byte[] message) {
    try {
      Cipher cipher = Cipher.getInstance(privateKey.getAlgorithm());
      cipher.init(Cipher.ENCRYPT_MODE, privateKey);
      return cipher.doFinal(message);
    } catch (GeneralSecurityException e) {
      throw new RuntimeException(e);
    }
  }

  private byte[] decrypt(byte[] data) {
    try {
      PublicKey publicKey = certificate.getPublicKey();
      Cipher cipher = Cipher.getInstance(publicKey.getAlgorithm());
      cipher.init(Cipher.DECRYPT_MODE, publicKey);
      return cipher.doFinal(data);
    } catch (GeneralSecurityException e) {
      throw new RuntimeException(e);
    }
  }

  private byte[] sign(byte[] message) {
    try {
      Signature signature = Signature.getInstance(certificate.getSigAlgName());
      signature.initSign(privateKey);
      signature.update(message);
      return signature.sign();
    } catch (GeneralSecurityException e) {
      throw new RuntimeException(e);
    }
  }

  private boolean verify(byte[] message, byte[] sign) {
    try {
      Signature signature = Signature.getInstance(certificate.getSigAlgName());
      signature.initVerify(certificate.getPublicKey());
      signature.update(message);
      return signature.verify(sign);
    } catch (GeneralSecurityException e) {
      throw new RuntimeException(e);
    }
  }

  private static KeyStore loadKeyStore(String keystoreFile, String password) {
    try (InputStream inputStream = new BufferedInputStream(new FileInputStream(keystoreFile))) {
      KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
      ks.load(inputStream, password.toCharArray());
      return ks;
    } catch (GeneralSecurityException | IOException e) {
      throw new RuntimeException(e);
    }

  }

  public static void main(String[] args) throws UnsupportedEncodingException {
    byte[] message = "Hello，使用X.509证书进行加密和签名!".getBytes("UTF-8");

    KeyStore ks = loadKeyStore("my.keystore", "456789");

    X509 x509 = new X509(ks, "mycert", "123456");

    byte[] data = x509.encrypt(message);

    System.out.println("Encrypted Message : " + Base64.getEncoder().encodeToString(data));
    System.out.println("Decrypted Message: " + new String(x509.decrypt(data), "UTF-8"));

    byte[] signature = x509.sign(data);
    boolean verified = x509.verify(data, signature);
    System.out.println(verified);


  }
}

