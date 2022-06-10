package com.ba.keycloak.authenticator;

import org.jboss.logging.Logger;
import org.keycloak.authentication.AuthenticationFlowContext;
import org.keycloak.authentication.AuthenticationFlowError;
import org.keycloak.authentication.Authenticator;
import org.keycloak.authentication.authenticators.browser.UsernamePasswordForm;
import org.keycloak.forms.login.LoginFormsProvider;
import org.keycloak.models.AuthenticatorConfigModel;

public class MyLoginForm extends UsernamePasswordForm implements Authenticator {

    private static final Logger LOG = Logger.getLogger(MyLoginForm.class);

    @Override
    public void authenticate(AuthenticationFlowContext context) {
        AuthenticatorConfigModel config = context.getAuthenticatorConfig();
        LoginFormsProvider form = context.form();

        boolean mobileNumberRequired = Boolean.parseBoolean(config.getConfig()
                .getOrDefault("mobile_number", "false"));
        LOG.warn("MobileNumberRequired: "+mobileNumberRequired);
        form.setAttribute("mobileNumberRequired", mobileNumberRequired);
        super.authenticate(context);
    }

    @Override
    public void action(AuthenticationFlowContext context) {

        String mobileNumber = context.getHttpRequest().getDecodedFormParameters().getFirst("mobileNumber");
        LOG.warn("Mobile Number: " + mobileNumber);


        AuthenticatorConfigModel config = context.getAuthenticatorConfig();
        boolean checkMobileNumber = Boolean.parseBoolean(config.getConfig().getOrDefault("mobile_number", "false"));
        if (!checkMobileNumber || "01707571184".equals(mobileNumber)) {
            super.action(context);
        } else {
            context.failureChallenge(AuthenticationFlowError.INVALID_CREDENTIALS
                    , context.form().setError("invalidMobileNumber").createLoginUsernamePassword());
        }
    }
}
