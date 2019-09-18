package com.jz.java.network.security.cypher;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Base64;

public class X509 {
  private PrivateKey privateKey;
  private X509Certificate certificate;

  public X509(KeyStore ks , String certName , String password) throws KeyStoreException, UnrecoverableKeyException, NoSuchAlgorithmException {
    privateKey = (PrivateKey) ks.getKey(certName, password.toCharArray());
    certificate = (X509Certificate) ks.getCertificate(certName);
  }

  private byte[] encrypt(byte[] message) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
    Cipher cipher = Cipher.getInstance(privateKey.getAlgorithm());
    cipher.init(Cipher.ENCRYPT_MODE, privateKey);
    return cipher.doFinal(message);
  }

  private byte[] decrypt(byte[] data) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
    PublicKey publicKey = certificate.getPublicKey();
    Cipher cipher = Cipher.getInstance(publicKey.getAlgorithm());
    cipher.init(Cipher.DECRYPT_MODE, publicKey);
    return cipher.doFinal(data);
  }

  private byte[] sign(byte[] message) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
    Signature signature = Signature.getInstance(certificate.getSigAlgName());
    signature.initSign(privateKey);
    signature.update(message);
    return signature.sign();
  }

  private boolean verify(byte[] message , byte[] sign) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
    Signature signature = Signature.getInstance(certificate.getSigAlgName());
    signature.initVerify(certificate.getPublicKey());
    signature.update(message);
    return signature.verify(sign);
  }

  private static KeyStore loadKeyStore(String keystoreFile, String password) throws KeyStoreException, IOException, CertificateException, NoSuchAlgorithmException {
    InputStream inputStream = new BufferedInputStream(new FileInputStream(keystoreFile));
    KeyStore ks = KeyStore.getInstance(KeyStore.getDefaultType());
    ks.load(inputStream, password.toCharArray());

    return ks;
  }

  public static void main(String[] args) throws IOException, CertificateException, NoSuchAlgorithmException, KeyStoreException, UnrecoverableKeyException, IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchPaddingException {
    byte[] message = "Hello，使用X.509证书进行加密和签名!".getBytes("UTF-8");
    KeyStore ks = loadKeyStore("my.keystore", "456789");
    X509 x509 = new X509(ks,"mycert", "1234566");
    byte[] data = x509.encrypt(message);
    System.out.println(Base64.getEncoder().encodeToString(data));

    System.out.println(new String(x509.decrypt(data),"UTF-8"));

  }
}

