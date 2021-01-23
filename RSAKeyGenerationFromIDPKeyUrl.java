package idprsa;

import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;

public class RSAKeyGenerationFromIDPKeyUrl {
	
	public static void main(String [] args) throws IOException {
		String modulusStr = "value_of_n";
		String exponentStr = "value_of_e";
		
		BigInteger modulus = new BigInteger(1, base64UrlDecode(modulusStr));
		BigInteger publicExponent = new BigInteger(1, base64UrlDecode(exponentStr));
		
		try {
			KeyFactory kf = KeyFactory.getInstance("RSA");
			PublicKey publicKey = kf.generatePublic(new RSAPublicKeySpec(modulus, publicExponent));
			
			Base64.Encoder encoder = Base64.getEncoder();
			FileWriter out = new FileWriter("publicrsa.pub");
			out.write("-----BEGIN RSA PUBLIC KEY-----\n");
			out.write(encoder.encodeToString(publicKey.getEncoded()));
			out.write("\n-----END RSA PUBLIC KEY-----\n");
			out.close();
			System.out.println("done");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	public static byte [] base64UrlDecode(String base64) {
		return Base64.getUrlDecoder().decode(base64);
	}
	


}
