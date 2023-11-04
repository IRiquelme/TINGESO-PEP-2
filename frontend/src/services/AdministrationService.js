/* eslint-disable no-unused-vars */
import axios from 'axios';

const API_URL = 'http://localhost:8080/administration/';

class AdministrationService {

    getLastExam() {
        return axios.get(API_URL + 'exam');
    }

    uploadExam(file) {
        return axios.post(API_URL + 'exam', file);
    }

    updateInstallments() {
        return axios.post(API_URL + 'update');
    }

    createReport() {
        return axios.post(API_URL + 'report');
    }
}
const administrationService = new AdministrationService();

export default administrationService;