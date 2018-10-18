package com.jzh.util;
import java.io.*;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class Goldacnt
{
    public static PrivateKey privateKey;
    public static PublicKey publicKey;
    private static String privateKeyPath;
    private static String publicKeyPath;
    
    
   static{
	   
		privateKeyPath = ConfigReader.getConfig("privateKeyPath");
		publicKeyPath = ConfigReader.getConfig("publicKeyPath");
	   try {
	        java.security.Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
	    }
	    catch (Exception e) {
	    	e.printStackTrace();
	    	System.out.println("密钥初始化失败");
	    }
   }

    public static PrivateKey getPrivateKey() {
		return privateKey;
	}

	public static void setPrivateKey(PrivateKey privateKey) {
		Goldacnt.privateKey = privateKey;
	}

	public static PublicKey getPublicKey() {
		return publicKey;
	}

	public static void setPublicKey(PublicKey publicKey) {
		Goldacnt.publicKey = publicKey;
	}

	public static String getPrivateKeyPath() {
		return privateKeyPath;
	}

	public static void setPrivateKeyPath(String privateKeyPath) {
		Goldacnt.privateKeyPath = privateKeyPath;
	}

	public static String getPublicKeyPath() {
		return publicKeyPath;
	}

	public static void setPublicKeyPath(String publicKeyPath) {
		Goldacnt.publicKeyPath = publicKeyPath;
	}

	public static void initPrivateKey()
    {
        try
        {
            privateKey = getPrivateKey(getPrivateKeyPath());
        }
        catch(Exception e)
        {
          
            e.printStackTrace();
        
        }
    }

    public static void initPublicKey()
    {
        try
        {
            if(publicKey == null)
                publicKey = getPublicKey(getPublicKeyPath());
        }
        catch(Exception e)
        {
            System.out.println((new StringBuilder("SecurityUtils\u521D\u59CB\u5316\u5931\u8D25")).append(e.getMessage()).toString());
            e.printStackTrace();
            System.out.println("\u5BC6\u94A5\u521D\u59CB\u5316\u5931\u8D25");
        }
    }

    public static String sign(String inputStr)
    {
        String result = null;
        try
        {
            if(privateKey == null)
                initPrivateKey();
            Signature signature = Signature.getInstance("SHA1withRSA", "BC");
            signature.initSign(privateKey);
            signature.update(inputStr.getBytes("UTF-8"));
            byte tByte[] = signature.sign();
            result = Base64.encode(tByte);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            System.out.println("\u5BC6\u94A5\u521D\u59CB\u5316\u5931\u8D25");
        }
        return result;
    }

    public static boolean verifySign(String src, String signValue)
    {
        boolean bool = false;
        try
        {
            initPublicKey();
            Signature signature = Signature.getInstance("SHA1withRSA", "BC");
            signature.initVerify(publicKey);
            signature.update(src.getBytes("UTF-8"));
            bool = signature.verify(Base64.decode(signValue));
        }
        catch(Exception e)
        {
            e.printStackTrace();
            System.out.println("\u5BC6\u94A5\u521D\u59CB\u5316\u5931\u8D25");
        }
        return bool;
    }

    private static PrivateKey getPrivateKey(String filePath)
    {
        String base64edKey = readFile(filePath);
        PrivateKey privateKey = null;
        try
        {
            KeyFactory kf = KeyFactory.getInstance("RSA", "BC");
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Base64.decode(base64edKey));
            privateKey = kf.generatePrivate(keySpec);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            System.out.println("\u5BC6\u94A5\u521D\u59CB\u5316\u5931\u8D25");
        }
        return privateKey;
    }

    private static PublicKey getPublicKey(String filePath)
    {
        String base64edKey = readFile(filePath);
        PublicKey publickey = null;
        try
        {
            KeyFactory kf = KeyFactory.getInstance("RSA", "BC");
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.decode(base64edKey));
            publickey = kf.generatePublic(keySpec);
        }
        catch(Exception e)
        {
            e.printStackTrace();
            System.out.println("\u5BC6\u94A5\u521D\u59CB\u5316\u5931\u8D25");
        }
        return publickey;
    }

    private static String readFile(String fileName) {
        try {
        	File f = new File(fileName);
            FileInputStream in = new FileInputStream(f);
            int len = (int)f.length();
            
            byte[] data = new byte[len];
            int read = 0;
            while (read <len) {
                read += in.read(data, read, len-read);
            }
            in.close();
            return new String(data);
        } catch (IOException e) {
            return null;
        }
    }



  
}
