package com.evgeny.track.config;

import com.evgeny.track.entity.CustomerEntity;
import com.evgeny.track.repository.CustomerRepository;
import com.evgeny.track.service.CustomerService;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit4.SpringRunner;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@RunWith(SpringRunner.class)
public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerService customerService;

    @Test
    public void testCreateCustomer(){

        CustomerEntity newCustomer = new CustomerEntity(null,"Evgeny Grazhdansky");
        CustomerEntity savedCustomer = new CustomerEntity(1L,"Evgeny Grazhdansky");
        when(customerRepository.save(newCustomer)).thenReturn(savedCustomer);

        CustomerEntity returnCustomer = customerService.createCustomer(newCustomer);

                assertEquals(returnCustomer.getName(),newCustomer.getName());
        verify(customerRepository, times(1)).save(newCustomer);
                ;
/* @Test
    public void testAdd_contactExists_contactWithAddress() {
        User user = new User("test@gmail.com", "test");
        Contact contact = new Contact("Name", user);

        when(contactRepository.findById(contact.getId())).thenReturn(Optional.of(contact));

        AddressDto addressDto = new AddressDto();
        addressDto.contactId = 0;
        addressDto.city = "Berlin";
        addressService.add(addressDto);

        verify(addressRepository, times(1)).save(any());
        verify(addressRepository, times(1)).save(argThat(address -> address.getCity().equals(addressDto.city) &&
                address.getContact().getId() == addressDto.contactId)
        );
    }*/

    }
}




