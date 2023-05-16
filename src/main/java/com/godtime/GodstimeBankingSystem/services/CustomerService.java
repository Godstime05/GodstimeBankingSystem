package com.godtime.GodstimeBankingSystem.services;

import com.godtime.GodstimeBankingSystem.dto.CustomerDTO;
import com.godtime.GodstimeBankingSystem.model.branch.Branch;
import com.godtime.GodstimeBankingSystem.model.customer.Customer;
import com.godtime.GodstimeBankingSystem.model.product.Product;
import com.godtime.GodstimeBankingSystem.repository.BranchRepository;
import com.godtime.GodstimeBankingSystem.repository.CustomerRepository;
import com.godtime.GodstimeBankingSystem.repository.FacilityRepository;
import com.godtime.GodstimeBankingSystem.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;

@Service
@AllArgsConstructor
@EnableAsync
public class CustomerService {


    private final CustomerRepository customerRepository;
    private final BranchRepository branchRepository;
    private final FacilityRepository facilityRepository;
    private final ProductRepository productRepository;
    private ModelMapper mapper;

    //    public ResponseEntity<CustomerDTO> getCustomerById(Long id) {
//        Optional<Customer> customer = customerRepository.findById(id);
//        CustomerDTO customerDTO = mapper.map(customer.get(), CustomerDTO.class);
//        return ResponseEntity.status(HttpStatus.OK).body(customerDTO);
//    }
    public ResponseEntity<?> getCustomerByNationalId(String nationalId) {
        Optional<Customer> customer = customerRepository.findCustomerByNationalId(nationalId);
        try{
            customer.orElseThrow(()->new RuntimeException("Cannot find Customer"));
        }catch(RuntimeException e){
            return ResponseEntity.status(OK).body(e.getMessage());
        }
        CustomerDTO customerDTO = mapper.map(customer.get(), CustomerDTO.class);
        return ResponseEntity.status(OK).body(customerDTO);
    }

    @Transactional
    public ResponseEntity<?> addCustomer(CustomerDTO customerDTO)  {
        Optional<Branch> branch = branchRepository.findBranchById(customerDTO.getBranchId());
        if (branch.get() == null)
            return ResponseEntity.status(OK).body("Incorrect Branch Id");
//        System.out.println(customerDTO.getIsCommissionPaid());
        Customer c = new Customer(
                customerDTO.getFirstName(),
                customerDTO.getLastName(),
                customerDTO.getPhoneNumber(),
                customerDTO.getNationalId(),
                false
        );
        branch.get().addCustomer(c);
        branchRepository.save(branch.get());
        //TODO JSON OBJECT CREATION
        return ResponseEntity.status(OK).body("Added");

    }

    //    public ResponseEntity<?> addCommissionToCustomer(CustomerDTO customerDTO) {
////        if(customerDTO.getCommissionAmount()==customerDTO.getProductType().get)
//    }
    public ResponseEntity<?> addCommissionToCustomer(CustomerDTO customerDTO) {
        Optional<Product> productOptional = productRepository.findById(customerDTO.getProductId());
        if(!productOptional.isPresent())
            return ResponseEntity.status(BAD_REQUEST).body("Incorrect product id !");
        Product product = productOptional.get();

        if (customerDTO.getCommissionAmount() == product.getCommission()) {
            Optional<Customer> customerOptional = customerRepository
                    .findCustomerByNationalId(customerDTO.getNationalId());
            if (customerOptional.get() == null)
                return ResponseEntity.status(BAD_REQUEST).body("Enter a correct National ID");
            if(customerOptional.get().isCommissionPaid())
                return ResponseEntity.status(BAD_REQUEST).body("Already Paid Commission!");
            customerOptional.ifPresent(customer -> {
                customer.setCommissionPaid(true);
                customer.setCommissionAmount(customerDTO.getCommissionAmount());
                customer.setCommissionPaidDate(LocalDate.now());
                product.addCustomer(customer);
                productRepository.save(product);

            });
            CustomerDTO ret = mapper.map(customerOptional.get(), CustomerDTO.class);
            return ResponseEntity.status(OK).body(ret);
        } else {
            return ResponseEntity.status(BAD_REQUEST)
                    .body("Commission amount is not compatible with this product type");
        }
    }
}
