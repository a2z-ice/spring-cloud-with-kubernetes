package queue.pro.cloud.qapi.commons;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JOSEObjectType;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import queue.pro.cloud.qapi.initializer.RSAKeyGenerator;
import queue.pro.cloud.qapi.stubs.OAuth2Stubs;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Map;

public abstract class AbsIntegrationPGBase extends PostgresSupportedBaseTest {

    @Value("${spring.application.name}")
    String resourceName;
    @Autowired
    private RSAKeyGenerator rsaKeyGenerator;

    @Autowired
    private OAuth2Stubs oAuth2Stubs;

    protected String getSignedJWT() throws JOSEException {
        return createJWT("duke", "duke@spring.io");
    }

    protected String getSignedJWT(String... roles) throws JOSEException {
        return createJWT("duke", "duke@spring.io", roles);
    }

    protected String getSignedJWT(String username, String email) throws JOSEException {
        return createJWT(username, email);
    }

    private String createJWT(String username, String email, String... roles) throws JOSEException {
        JWSHeader header =
                new JWSHeader.Builder(JWSAlgorithm.RS256)
                        .type(JOSEObjectType.JWT)
                        .keyID(RSAKeyGenerator.KEY_ID)
                        .build();
        JWTClaimsSet payload =
                new JWTClaimsSet.Builder()
                        .issuer(oAuth2Stubs.getIssuerUri())
                        .audience("account")
                        .subject(username)
                        .claim("preferred_username", username)
                        .claim("email", email)
                        .claim("scope", "openid email profile")
                        .claim("azp", "react-client")
                        .claim("preferred_username", username)
                        .claim("resource_access", Map.of(resourceName, Map.of("roles", List.of(roles))))
                        .expirationTime(Date.from(Instant.now().plusSeconds(120)))
                        .issueTime(new Date())
                        .build();

        SignedJWT signedJWT = new SignedJWT(header, payload);
        signedJWT.sign(new RSASSASigner(rsaKeyGenerator.getPrivateKey()));
        return signedJWT.serialize();
    }
}
