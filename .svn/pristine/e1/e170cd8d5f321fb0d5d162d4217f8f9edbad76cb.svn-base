package com.twkj;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.boot.test.context.SpringBootTest;
import sun.misc.BASE64Decoder;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

@RunWith(JUnit4.class)
@SpringBootTest
public class TwkpApplicationTests {

    @Test
    public void contextLoads() {
   }


   private KeyPair keyPair;

    // 生成密钥对
    public void genKeyPair() throws Exception {
        int keyLength=1024;
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(keyLength);
        keyPair = keyPairGenerator.generateKeyPair();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();//获取公钥
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();//获取私钥
//        System.out.println("private="+Base64.encodeToString(publicKey.getEncoded(), Base64.DEFAULT));
//        System.out.println("public="+Base64.encodeToString(privateKey.getEncoded(), Base64.DEFAULT));
    }

   @Test
    public void publicEncrypt() throws Exception {
//        genKeyPair();
        String content = "Zpk0zhGfgjzwfwpt@201811!1577091119761";
        Cipher cipher = Cipher.getInstance("RSA");// java默认"RSA"="RSA/ECB/PKCS1Padding"
        //上网
//        cipher.init(Cipher.ENCRYPT_MODE, getPublicKey("MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCa3+L0QRaExWO6m28pkLvp/c+XRQ/7ZM8DSJ+HAysytAimnn0ouDUFCy1jcLCQLgcaR9WVqBsPHigZJYwMo6/nd5upwP63BfvZoPnhgK216atkStUKDtmMS2TLnJptjtZcb0g8K+zsbTvYPpv3g+1N7rWgCD4zEtVm/LMyZ7NURwIDAQAB"));
        //正式
        cipher.init(Cipher.ENCRYPT_MODE, getPublicKey("MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAgQ0JM8O2j87XeewQc1ry+MFH+YQMnVGP1ZJlXCTlpI835LC6oGuXnRaDB1N/35COlgm9QI8mXWiCKY1NR1hmU9RNHjlQ34wg7ipGp/dIc1GEMznE95ayKTdk0DNAEoTuHuqBK0d+hnNsfgYmlXYELtUVaDdLbMWUcoihqrTo50hR9oGc7WfUrdQlCxdzxdnjg/I8Ih+SiZZpmVbhI66wypI3ncLmQtPhGdsJg7wfBMhB+MdxxcyierG9tVbFhN9l4rRNtKRYXAraMW/ww8/ReRHOa2RVSKKoFrT4NTIeeJKCNmD7kR9kGj7dp/OWjkk3m4THn7zn+vsprZVuiuyrCQIDAQAB"));


        int inputLen = content.getBytes("utf-8").length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;

        for(int i = 0; inputLen - offSet > 0; offSet = i * 244) {
            byte[] cache;
            if(inputLen - offSet > 244) {
                cache = cipher.doFinal( content.getBytes("utf-8"), offSet, 244);
            } else {
                cache = cipher.doFinal( content.getBytes("utf-8"), offSet, inputLen - offSet);
            }

            out.write(cache, 0, cache.length);
            ++i;
        }

        byte[] encryptedData = out.toByteArray();
        out.close();
       System.out.println("测试="+bytesToHexString(encryptedData));
       String aa = privateDecrypt(bytesToHexString(encryptedData), "");
       System.out.println("测试111="+aa);
   }

    private static PublicKey getPublicKey(String publicKey) throws Exception {
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] keyBytes = decoder.decodeBuffer(publicKey);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey1 = keyFactory.generatePublic(keySpec);
        return publicKey1;
    }

    private static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder("");
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    /**
     * 私钥分段解密
     *
     * @param content
     *            公钥加密的16进制字符串
     * @param privateKey
     *            私钥
     * @return
     * @throws Exception
     */
    public static String privateDecrypt(String content, String privateKey) throws Exception {
        privateKey="MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAJrf4vRBFoTFY7qbbymQu+n9z5dFD/tkzwNIn4cDKzK0CKaefSi4NQULLWNwsJAuBxpH1ZWoGw8eKBkljAyjr+d3m6nA/rcF+9mg+eGArbXpq2RK1QoO2YxLZMucmm2O1lxvSDwr7OxtO9g+m/eD7U3utaAIPjMS1Wb8szJns1RHAgMBAAECgYAs4i+S2/4bslzpqrw3jpN3B7COxVwRXjDEYdqhtSBizFwpdYsOLvxmnsujovf4gO5cBm92tMZKxlGWoBQLYbDLbqpGrVnlX1StDKevuUxSf8Z5EVhQm589hdhG0XCaCJa2S9HiIXh66IZMJ6lbyPtMD1gWXSdfF2nfmuCDL8bVIQJBAOMzUzzMW2+l38iYVNOvtbB+bcN3mCOcr6uGO5fGTqUvcaTDfckDuIrNAJiTqEWh+BGvTQfXmrKOp0JNgcPwfbECQQCugZQvrYeGS6xTNy5WwVKd9LSKLUlSciCi+OCgAKy+9/PNFy0pocYx97xhviFpTbUDlct/WKQ6CbQxmc+i/hd3AkEA2s3UDMpWCJj7gkjJ8K/YT2gCt2tWtn0wZQS6IXnWxZiCXoXyfoM511qEh2w0cxMAP6/OhI3sKqfdhqn0HcpekQJBAJUX3rzRfP3Pf57lgXDb4TNVVD7OLFwFaD71di0eBnlurV0nlQ2Byyz75XZ5FGC85I/4lpHp8P4pNkw+hrlScEcCQF/iNadXt9QZWqutrGLC3nwyBBki5y6zPsYTG9N36UgrH2DyVqHe/pOzWv239akD2sAaCxgg77j2Y+vZCVtSyXI=";
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, getPrivateKey(privateKey));
        int inputLen = hexStringToBytes(content).length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;

        for(int i = 0; inputLen - offSet > 0; offSet = i * 256) {
            byte[] cache;
            if(inputLen - offSet > 256) {
                cache = cipher.doFinal(hexStringToBytes(content), offSet, 256);
            } else {
                cache = cipher.doFinal(hexStringToBytes(content), offSet, inputLen - offSet);
            }

            out.write(cache, 0, cache.length);
            ++i;
        }

        byte[] decryptedData = out.toByteArray();
        out.close();
        return new String(decryptedData, "utf-8");
    }

    private static PrivateKey getPrivateKey(String privateKey) throws Exception {
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] keyBytes = decoder.decodeBuffer(privateKey);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return keyFactory.generatePrivate(keySpec);
    }

    private static byte[] hexStringToBytes(String hexString) {
        if (hexString == null || hexString.equals("")) {
            return null;
        }
        hexString = hexString.toUpperCase();
        int length = hexString.length() / 2;
        char[] hexChars = hexString.toCharArray();
        byte[] d = new byte[length];
        for (int i = 0; i < length; i++) {
            int pos = i * 2;
            d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
        }
        return d;
    }
    private static byte charToByte(char c) {
        return (byte) "0123456789ABCDEF".indexOf(c);
    }
}
