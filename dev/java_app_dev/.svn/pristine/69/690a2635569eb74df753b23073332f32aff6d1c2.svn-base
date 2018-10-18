package com.sftpay.utils;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class RSA {
    public static final String SIGN_ALGORITHMS = "MD5withRSA";
    
    /**
     * RSA生成签名与验证签名示例
     * 
     * @param args
     * @throws Exception
     */
	public static void main(String[] args) throws Exception {
		final String publicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA2F2ZjGH3aREFYBVAoPJY+9BBZl6w2xzUcQgVY8Xb7U1Gc2N93JIrj99WsWWq6r7mj2Tgr6Y5TXNGTWpsTlQcxf6pYZTZmr3LzPGVrO8co5puItWqQjLHFqy3RJEh95zzQWbr9nbiVpLd5fyq0LzBOr37kkxahqTRVr1iKTkZGgJCqWdl3RSYPXEHXJOwr15rDn+VKTT/1Y6QU/L2XxOTyy7iOgrFL/6ssFdkvt0uTV0R96c4avMQmSC/5VqxtOoV0OAqlLqev8XYbTK2pTPCEVIIw7SgW/vet0KWArhbW+XdpGlhrOrQ94Bl2q8oQqJghWr9zoMJDKAryVHSXD1E1wIDAQAB";
		
		final String privateKey = "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQDYXZmMYfdpEQVgFUCg8lj70EFmXrDbHNRxCBVjxdvtTUZzY33ckiuP31axZarqvuaPZOCvpjlNc0ZNamxOVBzF/qlhlNmavcvM8ZWs7xyjmm4i1apCMscWrLdEkSH3nPNBZuv2duJWkt3l/KrQvME6vfuSTFqGpNFWvWIpORkaAkKpZ2XdFJg9cQdck7CvXmsOf5UpNP/VjpBT8vZfE5PLLuI6CsUv/qywV2S+3S5NXRH3pzhq8xCZIL/lWrG06hXQ4CqUup6/xdhtMralM8IRUgjDtKBb+963QpYCuFtb5d2kaWGs6tD3gGXaryhComCFav3OgwkMoCvJUdJcPUTXAgMBAAECggEBAIe1XaPxpo8d//MeIWVR3IJFQ2AEMIWedZlX6qKj7afP+kpRsxXwEKay/NtT23pVtqNmMI+7gOGVVhkkkT4n/8woSPFNvZcTSIsJVEodyCbNrBrpTVssIjeUqXa2WUnIBcOV3JbARoLqp4ejjThTzBceJnbCsV0Wb78qFjGpAJeQf5kFhkx+BK88e5Z06C3JRbQHULmz5oLVRU48Zgcn1Jhp3ZdfKTzO9eIThq/wszcLTzRCVocx1IF66Q4UCmLE2owyR1LhZ4ZA6YioMCZPbMjAeUYPSucT8PrsNPhQZOtoF1bW5nCeAYuQuUyIQBbRNX9bbMDYj9OP7OFabuTJB2ECgYEA+jkBRITPXl6JG/9Pauz6sXFzdu6gm97y9RYHKBW9C7xc0GxIQM4OlVAkUJzIv6pOT5G+kl4T/e02VmX0ffwRp4u4Tna5n4G4Gocsqa4DcECdnvhMbqKlww/5VCv1Fvrt6Q/Rq9dmannwbiA0jcHBknHnCAaS70UDe67xj0Wqwr8CgYEA3Vx5tvYeSFLo/3Y5gcQf0f5Ruk27gx9mu0SvtLSYFjyxExqYAJo8ZwkNJYZDmCcu9J8rh8LboxYKgcFIVKMdefWb7JOHyf8wAiudZMZECt32TaEN71iDgiH9yhM2uQ05J+RINe4zj/7Gbj+FH+ijP1gNi0JOVeoj+lfJUtMQO+kCgYEAg+nFh0/U2tVPxxjDz4T7bMx4qLyIo2PYBekFANblAOjerWpIdRGskn7bhjwBgTnRaxVUuGksdPO3b7j0Oe7Hh+Ka2ZKxrSt/2Uxl+VYpreYCsqoH8VOBu+IR+ZPq86B6CCI00TkPXxbF7+i+i/UXjZLKz2pX0Bg8C9pgsr1xlpUCgYB02LWe8He3sZwwDRX5+67YSCiX8SRD6LVvsKgW+SU2x76o2ObXmpK7yLlZz2+qxzQwCD0QIrmRcrcFGyO1GY0brZwq2w1YgQ20d5VTdpzAJ7416AfVCaIRdSPkIRRHxkUfW48KeLxbDB9uXrVEzKYvb6lmkw+KpldrdB9fSu5M0QKBgQCTFBEbqMimjsf36fdEbgc4MCoqIpmnA/DCJeFiJgox1FRT4wMt9diAhLLwpdzGBfyyuZJ3eSE32eeUSE0PZyrZY9sCMCMhGNl3DtyDVmhUNrsV1+VaGBQjVObKzY3LcVy4LraOB1XVV8sZs9g2Fz6IVosIwnyla/rdBWcagaRWEg==";
		//final String privateKey = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDDxEOW5eGWn6SauR5QO+X0RNnOQrDtaOLByCaBQGVl9l3ojlnQHYxXjyEHjuT2iDZ4N6VQiBUxTzs6WrM/AbBzaR/2DGcdL+Y0biTZwKQ5t2NlD4L/r8w1H03zh4pYqf4QKmEkScTIRz2Wsi3sf5KlS3K72ESWFEY93cNN7bp2q4niaCyVeJwouoCJ5vUEDngRqlARyM+yNa0Rkc22dC3rmMx6mUNUXe0dp0/QMdBqQZT/3vbPcTJasomljtATh9EuBQ0XxlTC/c+1SjG03iLRGztQCAZ6EzxsNDCfk8QghjWZobd+0N5RQqR/T/k635m+Lg0KBDG+SwYp9FpJfBF7AgMBAAECggEANj+//yPq9KN+qMWdydwfaCZgOREwGEoVdF+zk+O1daDk+xnfd5WIm4b1PzPw7QRoG75H2F4tiUGMhyo1O8OLEyBRlAxx0pvAbk0LdzA0Ei5oo8u9oH8l1p67Kj4oL1PELNShoL20g0+CmKz9nujnSVj0DWvr7a7mnCRFB+ZV4qY//EjcUlsQGJnFT0JdbUwArc6IR1cBZSCrEepSxQTVEk9yoLKaUNOtpY8PTJoWiMAyjir20Nl9q1NWuHx9oPZWqAUlNA2ZFXafrc8Sf+HibtIdw6QcgvmRLtnEfE1KP43mM8jLkWNdhP010NH+fX+6/HjzHi+no68RKd0w3Ke8OQKBgQD0fB3PkycDlaudX9XM1T0NUvzZcFT+R69QLxvbW0G1e+p5mdAG3smyaQuc7BDII1DnSbLcih7miJK7STxQZHBjQOD0qTwgRgf4RORVipu/dNmdWyl7tL+F5qZ4Q6qMD1K6bJbvwVAyFRkKmlX2PKkxA+vyVUPj+dN/h4OUVNjH3wKBgQDM/LoGPogytNWaFuo6VFJiBaJz/xpo8p9IMoVxW44fTenTDV5xkUcsUvfnVWLUyUNDdr8VU175swOSJPa3mNHw2KJH6zyz2IwMSyGWi3009cUG7xPZ/WoYbXbH7y0yarWiXh/50j0uiORAOnyJnkkCWC2dQzldJ9lMhGHvE4OZ5QKBgAxXJ3oOx21a5Kqw563LJaXChpjDHscm/yZg+lr8JDjw5Ej3BpUSMcQboUZuc/pmLfM+uRSAtEaWpjFl5PF54GJiJ/ixgiNu5YAWmh/nOy1SzJkwvMtNuE0d81EmuTkQnPBkAspfS5j7j6QFXklrjm627/Xvb9nWBmLtiCq4jaipAoGBAJp3gxJ7gpR7Mk7IH77Olbnptw4j2NnmomJ8N5RBQqqSQrXYaCe6KuDasJZxAGWKOBaZX8qJJZOR8wnossWM8f7EsBzzxa1ca8RDxU61VCdZYBwmglYNISrBKfXlZLCOOsrclMnjRFlRrHY93GSzkx6iJdVQt+Rbx+JiC45lrRCFAoGBANDcA9S5fd+awvDOON0WnpmnHmgVKYiD0oqSlfI+lHrm5UEOviA4gO3r+AdzB1KzrnXcFMGo9PNdxMTU3JlQKCEegx+alvi73lMvRW1m5OdwpCfc6lWdZDqjkGbJLPY9XDCV4Zu1LE1P5J9vK3sEiNCeOqgCfP/Rou/cHGOXGQMO";
		
		String content = "这是一个使用RSA公私钥对测试加解密的例子.";
		System.out.println(content);
		
		String signMsg = sign(content, privateKey, "UTF-8");
		System.out.println("加密后密文 : " + signMsg);
		
		boolean verifySignResult = verify(content, signMsg, publicKey, "UTF-8");
		System.out.println("验证签名结果 : " + verifySignResult);
		
	}
    /**
     * 使用{@code RSA}方式对字符串进行签名
     * 
     * @param content 需要加签名的数据
     * @param privateKey {@code RSA}的私钥
     * @param charset 数据的编码方式
     * @return 返回签名信息
     */
    public static String sign(String content, String privateKey, String charset) {
        try {
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Base64.decode(privateKey));

            KeyFactory keyf = KeyFactory.getInstance("RSA");
            PrivateKey priKey = keyf.generatePrivate(keySpec);

            return sign(content, priKey, charset);
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * 使用{@code RSA}方式对字符串进行签名
     * 
     * @param content 需要加签名的数据
     * @param privateKey {@code RSA}的私钥
     * @param charset 数据的编码方式
     * @return 返回签名信息
     */
    public static String sign(String content, PrivateKey privateKey, String charset) {
        try {
            Signature signature = Signature.getInstance(SIGN_ALGORITHMS);

            signature.initSign(privateKey);
            signature.update(StringUtils.getContentBytes(content, charset));

            return Base64.encode(signature.sign());
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * 使用{@code RSA}方式对签名信息进行验证
     * 
     * @param content 需要加签名的数据
     * @param sign 签名信息
     * @param publicKey {@code RSA}的公钥
     * @param charset 数据的编码方式
     * @return 是否验证通过。{@code True}表示通过
     */
    public static boolean verify(String content, String sign, String publicKey, String charset) {
        try {
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.decode(publicKey));

            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PublicKey pubKey = keyFactory.generatePublic(keySpec);

            return verify(content, sign, pubKey, charset);
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * 使用{@code RSA}方式对签名信息进行验证
     * 
     * @param content 需要加签名的数据
     * @param sign 签名信息
     * @param publicKey {@code RSA}的公钥
     * @param charset 数据的编码方式
     * @return 是否验证通过。{@code True}表示通过
     */
    public static boolean verify(String content, String sign, PublicKey publicKey, String charset) {
        try {
            Signature signature = Signature.getInstance(SIGN_ALGORITHMS);

            signature.initVerify(publicKey);
            signature.update(StringUtils.getContentBytes(content, charset));

            return signature.verify(Base64.decode(sign));
        }
        catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
