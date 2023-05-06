package com.beetrootforum.beetrootforum.services;

import com.beetrootforum.beetrootforum.data.KeyData;
import com.beetrootforum.beetrootforum.jpa.Key;
import org.bouncycastle.openpgp.*;
import org.bouncycastle.openpgp.operator.jcajce.JcaKeyFingerprintCalculator;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Iterator;

@Service
public class KeyService {
    public KeyData convert(Key key) {
        return new KeyData(key.getPemKey(), key.getUser());
    }

    private String[] exploreKey(String publicKey) throws IOException, PGPException {
        String[] result = new String[2];
        PGPPublicKeyRingCollection pgpPublicKeyRingCollection = new PGPPublicKeyRingCollection(
                PGPUtil.getDecoderStream(new ByteArrayInputStream(publicKey.getBytes())), new JcaKeyFingerprintCalculator());
        Iterator<PGPPublicKeyRing> keyRingIterator = pgpPublicKeyRingCollection.getKeyRings();
        while (keyRingIterator.hasNext()) {
            PGPPublicKeyRing keyRing = keyRingIterator.next();
            Iterator<PGPPublicKey> keyIterator = keyRing.getPublicKeys();
            result = keyIterator.next().getUserIDs().next().split(" ");
        }
        return result;
    }

    public String getEmailFromKey(String publicKey) throws PGPException, IOException {
        return exploreKey(publicKey)[1].replaceAll("[<>]", "");
    }

    public String getUsernameFromKey(String publicKey) throws PGPException, IOException {

        return exploreKey(publicKey)[0];
    }
}
