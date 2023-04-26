import axios from 'axios';

const BACKEND_URL = "http://localhost:8080";
const CUSTOMER_REST_API_URL = BACKEND_URL + "/customer";

class APIService {
    getCustomers() {
        return axios.get(CUSTOMER_REST_API_URL);
    }

    /*addCustomers(newData) {
        return axios.post(CUSTOMER_REST_API_URL, newData);
    }*/
}

export default new APIService();
