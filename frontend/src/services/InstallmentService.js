/* eslint-disable no-unused-vars */
import axios from 'axios';

const API_URL = 'http://localhost:8080/installment/';

class InstallmentService {

    getInstallmentsByRut(rut) {
        return axios.get(API_URL + rut);
    }

    getInstallment(id) {
        return axios.get(API_URL + id);
    }

    createInstallments(rut, numberOfInstallments) {
        return axios.post(API_URL + rut + "/" + numberOfInstallments);
    }

    payInstallment(id) {
        return axios.post(API_URL + "pay/" +id);
    }
}

const installmentService = new InstallmentService();

export default installmentService;
