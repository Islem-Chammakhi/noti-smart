"use client";
import AuthForm from "@/components/AuthForm";
import z from "zod";
import myApi from "@/lib/api";
import { useAuth } from "@/hooks/useAuth";
import { useRouter } from "next/navigation";

const VerifyOtpPage = () => {
  const { updateUser } = useAuth();
  const router = useRouter();

  const fields = [{ name: "otp", placeholder: "code otp ", type: "number" }];

  const otpSchema = z.object({
    otp: z.string().length(6, "Le code OTP doit contenir 6 chiffres."),
  });
  const handleSubmit = async (data: { otp: string }) => {
    try {
      const response = await myApi.loginWithOTP(data);
      if (response.status === 200) {
        console.log(
          "login successfully moving to persist login !",
          response.data
        );
        console.log(response.data);
        updateUser(response.data);
        router.push("/student/marks");
      }
    } catch (err) {
      console.log(err);
    }
  };

  return (
    <AuthForm
      fields={fields}
      onSubmit={handleSubmit}
      schema={otpSchema}
      buttonText="Vérifier"
      extra="merci de consulter le code envoyé par email"
    />
  );
};

export default VerifyOtpPage;
