package idprsa;

import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;

public class TokenValidationTests {
	
	public static void main(String [] args) throws IOException {
		String modulusStr = "ipjRdnJXyCZopR1jFvV8RRnjbn834DLlS577i-5c_PWk_qGNSMqT7wL7ibN442-7HAcGdx5ZG9bsAmfKM3Fx987CZCeh2Kpih1JECTL-iyB_T9pditDQgYF1Y0dHnR0OjeUelVwi-YLsgEI8_8INoNrHOxXDD3o2eWMDyHVvOn7g2MnYT-v24-0k_Mdll32tW4z3dIKJdj0w9FG4zvnD3C6hJpsUArXWGvQMeF_BEE5ccPqJggsd1GAkjhypRjLi2g3h2kOhr4JZS3NUb3ybQJB00w2BxAbpEty9A2ERgEoL-zepWveRxsSEVs0zF5mtFGa91LGig74gRxAnLEgQww";
		String exponentStr = "AQAB";
		
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
