// src/lib/axios-config.ts
import axios from 'axios';
import {redirect, RedirectType} from 'next/navigation';

const API_BASE_URL = process.env.NEXT_PUBLIC_API_BASE_URL || 'http://localhost:8080/api';

// Create axios instance with default config
export const axiosInstance = axios.create({
    baseURL: API_BASE_URL,
    withCredentials: true, // Important for cookies/sessions
    //timeout: 10000, // 10 seconds
    headers: {
        'Content-Type': 'application/json',
    },
});
// Request interceptor - mainly for logging
axiosInstance.interceptors.request.use(
    (config) => {
        // You can add any request logging here
        console.log(`Making ${config.method?.toUpperCase()} request to: ${config.url}`);
        return config;
    },
    (error) => {
        return Promise.reject(error);
    }
);

// Response interceptor for handling auth errors
axiosInstance.interceptors.response.use(
    (response) => {
        return response;
    },
    (error) => {
        //TODO: Make unauthorized access return code 401 not 403
        // Handle 403 Forbidden - redirect to login
        if (error.response?.status === 403) {
            console.log('Access forbidden, redirecting to login...');
            redirect("/login", RedirectType.replace)

        }

        // Handle 401 Unauthorized - also redirect to login
        if (error.response?.status === 401) {
            console.log('Unauthorized, redirecting to login...');
            redirect("/login", RedirectType.replace)

        }

        return Promise.reject(error);
    }
);