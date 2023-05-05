import axios from 'axios';

const BACKEND_URL = "http://localhost:8080";
const CUSTOMER_REST_API_URL = BACKEND_URL + "/customer";
const TERMINAL_REST_API_URL = BACKEND_URL + "/terminal";
const POSTAL_REST_APO_URL = BACKEND_URL + "/postalcode";

class APIService {

    getCustomers() {
        return axios.get(CUSTOMER_REST_API_URL);
    }

    getCustomerEval() {
        return axios.get(CUSTOMER_REST_API_URL + "/eval");
    }

    getTerminals() {
        return axios.get(TERMINAL_REST_API_URL);
    }

    getPostalCodes() {
        return axios.get(POSTAL_REST_APO_URL);
    }

    getCustomerParcels = (cellData) => {
        return axios.get(CUSTOMER_REST_API_URL + "/" + cellData);
    }

    getCustomerParcels2 = (cellData) => {
        return axios.get("http://localhost:8080/parcel/" + cellData);
    }

    getShipmentEval() {
        return axios.get(  BACKEND_URL + "/shipment/eval");
    }


}

export default new APIService();