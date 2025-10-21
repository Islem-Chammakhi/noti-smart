"use client";

import AuthForm from "@/components/AuthForm";
import { useRouter } from "next/navigation";
import z from "zod";

// File intermidiare, ta3ml appel l deux fichiers fil page.tsx. Send
const VerifyOtpPage = () => {
  const fields = [{ name: "otp", placeholder: "code otp ", type: "number" }];

  const otpSchema = z.object({
    otp: z.string().length(6, "Le code OTP doit contenir 6 chiffres."),
  });
  const router = useRouter();
  const handleSubmit = (data: { otp: string }) => {
    console.log("otp data:", data);
    router.push("/");
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
