"use client";
import AuthForm from "@/components/AuthForm";
import z from "zod";
import myApi from "@/lib/api";
import { useAuth } from "@/hooks/useAuth";
import { useRouter } from "next/navigation";
import { useState } from "react";
import Loader from "@/components/Loader";

const VerifyOtpPage = () => {
  const router = useRouter();
  const { updateUser } = useAuth();
  const [loading, setLoading] = useState(false);

  const fields = [{ name: "otp", placeholder: "code otp ", type: "number" }];

  const otpSchema = z.object({
    otp: z.string().length(6, "Le code OTP doit contenir 6 chiffres."),
  });
  const handleSubmit = async (data: { otp: string }) => {
    try {
      setLoading(true);
      const response = await myApi.loginWithOTP(data);
      if (response.status === 200) {
        console.log(response.data);
        updateUser(response.data);
        const { role } = response.data;
        role === "ADMIN"
          ? router.push("/admin/upload")
          : router.push("/student/marks");
      } else {
        throw new Error(response.data);
      }
    } catch (err: any) {
      console.log(err);
      throw new Error(err?.message || String(err));
    } finally {
      setLoading(false);
    }
  };

  return (
    <>
      {loading && <Loader />}
      <AuthForm
        title="Vérification"
        fields={fields}
        onSubmit={handleSubmit}
        schema={otpSchema}
        buttonText="Vérifier"
        extra="merci de consulter le code envoyé par email"
        type="otp"
      />
    </>
  );
};

export default VerifyOtpPage;
