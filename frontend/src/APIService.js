import axios from 'axios';

const BACKEND_URL = "http://localhost:8080";
const CUSTOMER_REST_API_URL = BACKEND_URL + "/customer";
const TERMINAL_REST_API_URL = BACKEND_URL + "/terminal";
const POSTAL_REST_APO_URL = BACKEND_URL + "/postalcode";

class APIService {

    getCustomers() {
        return axios.get(CUSTOMER_REST_API_URL);
    }


    getTerminals() {
        return axios.get(TERMINAL_REST_API_URL);
    }


    getShipmentEval() {
        return axios.get(  BACKEND_URL + "/shipment/eval");
    }

    getShipmentsFromCustomer = (cellData) => {
        return axios.get("http://localhost:8080/shipment" + "/" + cellData)
    }

    getCheckpointEval() {
        return axios.get(  BACKEND_URL + "/checkpoint/eval");
    }


}

export default new APIService();