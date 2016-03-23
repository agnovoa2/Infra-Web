package es.uvigo.esei.infraestructura.ejb;

import java.security.Principal;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import es.uvigo.esei.infraestructura.entities.User;

import static java.util.Optional.ofNullable;

@Stateless
public class UserAuthorizationEJB {

    @PersistenceContext
    private EntityManager em;

    @Inject
    private Principal principal;

    @RolesAllowed({ "STUDENT", "INTERN", "PROFESSOR" })
    public User getCurrentUser() throws SecurityException {
        return ofNullable(em.find(User.class, principal.getName()))
              .orElseThrow(SecurityException::new);
    }

}
