//package no.ntnu.bachelor_group3.dataaccessevaluation.Runnables;
//
//import no.ntnu.bachelor_group3.dataaccessevaluation.Data.Customer;
//import no.ntnu.bachelor_group3.dataaccessevaluation.Data.Shipment;
//import no.ntnu.bachelor_group3.dataaccessevaluation.Services.CustomerService;
//import no.ntnu.bachelor_group3.dataaccessevaluation.Services.ShipmentService;
//
//import java.util.concurrent.Callable;
//
//public class CustomerRunnable implements Callable {
//
//    private Customer customer;
//
//    private CustomerService customerService;
//
//    public CustomerRunnable(Customer customer, CustomerService customerService) {
//
//        this.customer = customer;
//        this.customerService = customerService;
//    }
//
//    public Customer getCustomer() {
//        return customer;
//    }
//
//    public void setCustomer(Customer customer) {
//        this.customer = customer;
//    }
//
//    public CustomerService getCustomerService() {
//        return customerService;
//    }
//
//    public void setCustomerService(CustomerService customerService) {
//        this.customerService = customerService;
//    }
//
//    @Override
//    //this method runs another in order to catch exceptions, else they are ignored.
//    public String call() throws Exception {
//        String result = "";
//        try {
//            //result = catchRun();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return result;
//    }
//
//    /**
//     * Adds a customer to the database and returns the timestamp for each query
//     */
//    public void catchRun() {
//        customerService.add(customer);
//    }
//}
