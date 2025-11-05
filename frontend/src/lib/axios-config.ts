// src/lib/axios-config.ts
import axios from "axios";
import { redirect, RedirectType } from "next/navigation";

const API_BASE_URL =
  process.env.NEXT_PUBLIC_API_BASE_URL || "http://localhost:8080/api";

let isRefreshing = false;
let refreshSubscribers: (() => void)[] = [];

function onRefreshed() {
  refreshSubscribers.forEach((callback) => callback());
  refreshSubscribers = [];
}
// Create axios instance with default config
export const axiosInstance = axios.create({
  baseURL: API_BASE_URL,
  withCredentials: true, // Important for cookies/sessions
  //timeout: 10000, // 10 seconds
  headers: {
    "Content-Type": "application/json", //! pas toujours
  },
});
// export const axiosFileInstance = axios.create({
//   baseURL: API_BASE_URL,
//   withCredentials: true, // Important for cookies/sessions
//   //timeout: 10000, // 10 seconds
//   headers: {
//     "Content-Type": "multipart/form-data", //! pas toujours
//   },
// });

// Request interceptor - mainly for logging

axiosInstance.interceptors.request.use(
  (config) => {
    // You can add any request logging here
    console.log(
      `Making ${config.method?.toUpperCase()} request to: ${config.url}`
    );
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
  async (error) => {
    const originalRequest = error.config;

    if (
      originalRequest.url.cotnains("/auth/me") &&
      error.response?.status === 401
    ) {
      redirect("/login", RedirectType.replace);
    }
    // Si l’erreur est 401 (token expiré) et qu’on n’a pas encore essayé de refresh
    if (error.response?.status === 401 && !originalRequest._retry) {
      if (isRefreshing) {
        // Si un refresh est déjà en cours → on attend
        return new Promise((resolve) => {
          refreshSubscribers.push(() =>
            resolve(axiosInstance(originalRequest))
          );
        });
      }

      originalRequest._retry = true;
      isRefreshing = true;
      try {
        const response = await axiosInstance.post("/auth/refresh");
        if (response.status === 200) {
          console.log("t5azwa9na !", response.data);
        }
        isRefreshing = false;
        onRefreshed();
        return axiosInstance(originalRequest);
      } catch (error) {
        isRefreshing = false;
        console.log("Unauthorized, redirecting to login...");
        redirect("/login", RedirectType.replace);
      }
    }

    // Handle 403 Forbidden - redirect to login
    if (error.response?.status === 403) {
      console.log("Access forbidden, redirecting to login...");
      redirect("/login", RedirectType.replace);
    }

    return Promise.reject(error);
  }
);
