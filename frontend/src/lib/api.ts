import { axiosInstance } from "@/lib/axios-config";

class ApiService {
  // Auth methods
  async login(credentials: { cin: string; password: string }) {
    const response = await axiosInstance.post("/auth/login", credentials);
    return response;
  }

  async loginWithOTP(credentials: {
    // email: string;
    // password: string;
    otp: string;
  }) {
    const response = await axiosInstance.post("/auth/otp", credentials);
    return response;
  }

  async register(userData: {
    confirmPassword: string;
    password: string;
    cin: string;
  }) {
    const response = await axiosInstance.post("/auth/register", userData);
    return response;
  }

  async logout() {
    const response = await axiosInstance.post("/auth/logout");

    //TODO: manage logout
    return response.data;
  }

  async verifyAuth(): Promise<User> {
    const response = await axiosInstance.get<User>("/auth/me");
    return response.data;
  }

  async getStudentMarksByFiliereAndSubject(
    filiereId: string,
    subjectId: string
  ) {
    const response = await axiosInstance.get(
      `/admin/${filiereId}/${subjectId}/student_marks`
    );
    return response;
  }

  async getStudentMarks(studentCin: string) {
    const response = await axiosInstance.get(`/student/${studentCin}/marks`);
    return response;
  }
}
export default new ApiService();
