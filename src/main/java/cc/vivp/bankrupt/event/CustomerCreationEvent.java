package cc.vivp.bankrupt.event;

import cc.vivp.bankrupt.model.api.Customer;
import cc.vivp.bankrupt.model.api.CustomerCommand;
import cc.vivp.bankrupt.model.db.CustomerEntity;
import cc.vivp.bankrupt.repository.CustomersRepository;
import java.time.LocalDateTime;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;

@Slf4j
public class CustomerCreationEvent extends DomainEvent<Customer> {

    private final CustomerCommand customerCommand;
    private final CustomersRepository customersRepository;
    private final ModelMapper modelMapper;

    public CustomerCreationEvent(final LocalDateTime occurred,
        final CustomerCommand customerCommand, final CustomersRepository customersRepository,
        final ModelMapper modelMapper) {
        super(occurred);
        this.customerCommand = customerCommand;
        this.customersRepository = customersRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public Customer process() {

        CustomerEntity newCustomer = new CustomerEntity(customerCommand.getEmail(), customerCommand.getName());
        customersRepository.save(newCustomer);
        log.info("{};{};{};{}", occurred, recorded, newCustomer.getEmail(), newCustomer.getName());

        return modelMapper.map(newCustomer, Customer.class);
    }
}
