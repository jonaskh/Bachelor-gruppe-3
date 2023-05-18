package no.ntnu.bachelor_group3.dataaccessevaluation.Runnables;

import no.ntnu.bachelor_group3.dataaccessevaluation.Data.Customer;
import no.ntnu.bachelor_group3.dataaccessevaluation.Data.Terminal;
import no.ntnu.bachelor_group3.dataaccessevaluation.Services.CustomerService;
import no.ntnu.bachelor_group3.dataaccessevaluation.Services.ShipmentService;
import no.ntnu.bachelor_group3.dataaccessevaluation.Services.TerminalService;

public class UpdateCustomerRunnable implements Runnable{


    private CustomerService customerService;
    private Customer customer;



    public UpdateCustomerRunnable(CustomerService customerService, Customer customer) {
        this.customerService = customerService;
        this.customer = customer;
    }

    @Override
    public void run() {
        try {
            catchRun();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void catchRun() {
        System.out.println("Old address: " + customerService.findByID(customer.getCustomerID()).get().getAddress());
        try {
            customerService.findByID(customer.getCustomerID()).get().setAddress();
        } catch (NullPointerException e) {
            System.out.println("No customer with that id in database");
        }
        System.out.println("new address: " + customerService.findByID(customer.getCustomerID()).get().getAddress());
    }
}


