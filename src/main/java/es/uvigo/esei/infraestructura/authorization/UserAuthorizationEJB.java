package es.uvigo.esei.infraestructura.authorization;

import static java.util.Optional.ofNullable;

import java.security.Principal;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.inject.Inject;

import es.uvigo.esei.infraestructura.entities.User;
import es.uvigo.esei.infraestructura.facade.UserGatewayBean;

@Stateless
public class UserAuthorizationEJB {

    @Inject
    private UserGatewayBean userGateway;

    @Inject
    private Principal principal;

    @RolesAllowed({ "STUDENT", "INTERN", "PROFESSOR" })
    public User getCurrentUser() throws SecurityException {
        return ofNullable(userGateway.find(principal.getName()))
              .orElseThrow(SecurityException::new);
    }

}
