// api.ts
import axios from 'axios';

const API_BASE_URL = 'https://api.example.com'; // Replace with your API base URL

const api = axios.create({
    baseURL: API_BASE_URL,
    timeout: 1000, // Request timeout
    headers: {'Content-Type': 'application/json'}
});

// Example GET request
export const getData = async (endpoint: string) => {
    try {
        const response = await api.get(endpoint);
        return response.data;
    } catch (error) {
        throw error;
    }
};

// Example POST request
export const postData = async (endpoint: string, data: any) => {
    try {
        const response = await api.post(endpoint, data);
        return response.data;
    } catch (error) {
        throw error;
    }
};

// Add more API methods below as needed
