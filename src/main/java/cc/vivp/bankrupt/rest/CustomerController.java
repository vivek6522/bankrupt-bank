package cc.vivp.bankrupt.rest;

import cc.vivp.bankrupt.exception.AccessDeniedException;
import cc.vivp.bankrupt.exception.EntityNotFoundException;
import cc.vivp.bankrupt.model.api.Customer;
import cc.vivp.bankrupt.model.api.CustomerCommand;
import cc.vivp.bankrupt.service.CustomerService;
import cc.vivp.bankrupt.util.MessageKeys;
import javax.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.keycloak.KeycloakSecurityContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor(onConstructor_ = {@Autowired})
public class CustomerController {

    private HttpServletRequest request;
    private CustomerService customerService;

    @PostMapping("customers")
    public Customer registerCustomer(@RequestBody CustomerCommand customerCommand) {
        return customerService.registerCustomer(customerCommand);
    }

    @GetMapping("customers/{id}")
    public Customer fetchCustomerDetails(@PathVariable("id") long id)
        throws EntityNotFoundException, AccessDeniedException {
        KeycloakSecurityContext keycloakSecurityContext = (KeycloakSecurityContext) request
            .getAttribute(KeycloakSecurityContext.class.getName());
        Customer loggedInCustomer = customerService
            .fetchCustomerDetails(keycloakSecurityContext.getIdToken().getEmail());
        Customer requestedCustomer = customerService.fetchCustomerDetails(id);
        if (requestedCustomer.getId().longValue() != loggedInCustomer.getId().longValue()) {
            throw new AccessDeniedException(MessageKeys.CUSTOMER_ID_MISMATCH);
        }
        return requestedCustomer;
    }
}
