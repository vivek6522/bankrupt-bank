package cc.vivp.bankrupt.service;

import static cc.vivp.bankrupt.util.Generic.throwEntityNotFoundExceptionIfNotPresentElseReturnValue;

import cc.vivp.bankrupt.event.CustomerCreationEvent;
import cc.vivp.bankrupt.exception.EntityNotFoundException;
import cc.vivp.bankrupt.model.api.Customer;
import cc.vivp.bankrupt.model.api.CustomerCommand;
import cc.vivp.bankrupt.model.db.CustomerEntity;
import cc.vivp.bankrupt.repository.CustomersRepository;
import java.time.LocalDateTime;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@AllArgsConstructor(onConstructor_ = {@Autowired})
@Validated
public class CustomerService {

    private final CustomersRepository customersRepository;
    private final ModelMapper modelMapper;

    public Customer registerCustomer(@Valid CustomerCommand customerCommand) {
        return new CustomerCreationEvent(LocalDateTime.now(), customerCommand, customersRepository,
            modelMapper).process();
    }

    public Customer fetchCustomerDetails(@Min(1) long id) throws EntityNotFoundException {
        CustomerEntity foundCustomer = throwEntityNotFoundExceptionIfNotPresentElseReturnValue(
            customersRepository.findById(id));
        return modelMapper.map(foundCustomer, Customer.class);
    }

    public Customer fetchCustomerDetails(@NotBlank String email) throws EntityNotFoundException {
        CustomerEntity foundCustomer = throwEntityNotFoundExceptionIfNotPresentElseReturnValue(
            customersRepository.findByEmail(email));
        return modelMapper.map(foundCustomer, Customer.class);
    }
}
