/*
package com.appointment_management.demo;

import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cert.jcajce.JcaX509CertificateConverter;
import org.bouncycastle.cert.jcajce.JcaX509v3CertificateBuilder;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.operator.ContentSigner;
import org.bouncycastle.operator.jcajce.JcaContentSignerBuilder;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigInteger;
import java.security.*;
import java.security.cert.X509Certificate;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public class SslBootstrap {

    public static void ensureKeystoreExists(String path, String alias, String password, int validDays) {
        try {
            if (Security.getProvider("BC") == null) {
                Security.addProvider(new BouncyCastleProvider());
            }

            File ksFile = new File(path);
            File dir = ksFile.getParentFile();
            if (dir != null) dir.mkdirs();

            if (ksFile.exists()) return;

            KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
            kpg.initialize(2048);
            KeyPair keyPair = kpg.generateKeyPair();

            Instant now = Instant.now();
            Date notBefore = Date.from(now.minus(1, ChronoUnit.MINUTES));
            Date notAfter  = Date.from(now.plus(validDays, ChronoUnit.DAYS));

            X500Name subject = new X500Name("CN=localhost, O=AppointmentApp, C=FR");
            BigInteger serial = new BigInteger(64, new SecureRandom());

            JcaX509v3CertificateBuilder certBuilder = new JcaX509v3CertificateBuilder(
                    subject, serial, notBefore, notAfter, subject, keyPair.getPublic()
            );

            ContentSigner signer = new JcaContentSignerBuilder("SHA256withRSA")
                    .build(keyPair.getPrivate());

            X509CertificateHolder holder = certBuilder.build(signer);

            X509Certificate cert = new JcaX509CertificateConverter()
                    .setProvider("BC")
                    .getCertificate(holder);

            KeyStore ks = KeyStore.getInstance("PKCS12");
            ks.load(null, null);
            ks.setKeyEntry(alias, keyPair.getPrivate(), password.toCharArray(),
                    new java.security.cert.Certificate[]{cert});

            try (FileOutputStream fos = new FileOutputStream(ksFile)) {
                ks.store(fos, password.toCharArray());
            }

            System.out.println("✅ SSL keystore generated: " + ksFile.getAbsolutePath());

        } catch (Exception e) {
            throw new RuntimeException("Failed to generate SSL keystore", e);
        }
    }
}
*/
