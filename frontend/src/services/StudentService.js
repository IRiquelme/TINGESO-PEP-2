/* eslint-disable no-unused-vars */
import axios from 'axios';

const API_URL = 'http://localhost:8080/student/';

class StudentService {

    getStudents() {
        return axios.get(API_URL);
    }

    createStudent(student) {
        return axios.post(API_URL, student);
    }
}


const studentService = new StudentService();

export default studentService;