import {axiosInstance} from "@/lib/axios-config";


class ApiService {
    // Auth methods
    async login(credentials: { cin: string; password: string }) {
        const response = await axiosInstance.post('/auth/login', credentials);
        return response;
    }

    async loginWithOTP(credentials: { email: string; password: string, otp: string }) {
        const response = await axiosInstance.post('/auth/otp', credentials);
        return response.data;
    }

    async register(userData: { confirmPassword: string; password: string; name: string }) {
        const response = await axiosInstance.post('/auth/register', userData);
        return response.data;
    }

    async logout() {
        const response = await axiosInstance.post('/auth/logout');

        //TODO: manage logout
        return response.data;
    }

    async verifyAuth(): Promise<User> {
        const response = await axiosInstance.get<User>('/auth/me');
        return response.data;
    }

}
export default new ApiService();