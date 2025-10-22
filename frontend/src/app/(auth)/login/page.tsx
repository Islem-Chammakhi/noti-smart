"use client";
import AuthForm from "@/components/AuthForm";
import { useRouter } from "next/navigation";
import { z } from "zod";
import myApi from "@/lib/api";
import { useState } from "react";
import { Loader } from "lucide-react";

const LoginPage = () => {
  const router = useRouter();
  const [loading, setLoading] = useState(false);

  const fields = [
    { name: "cin", placeholder: "CIN", type: "number" },
    { name: "password", placeholder: "Mot de passe", type: "password" },
  ];

  const loginSchema = z.object({
    cin: z
      .string()
      .regex(/^\d{8}$/, "Le CIN doit contenir exactement 8 chiffres"),
    password: z
      .string()
      .min(8, "Le mot de passe doit contenir au moins 8 caractères")
      .regex(/[A-Z]/, "Le mot de passe doit contenir une majuscule")
      .regex(/[a-z]/, "Le mot de passe doit contenir une minuscule")
      .regex(/\d/, "Le mot de passe doit contenir un chiffre")
      .regex(
        /[!@#$%^&*(),.?":{}|<>]/,
        "Le mot de passe doit contenir un caractère spécial"
      ),
  });

  const handleLogin = async (data: { cin: string; password: string }) => {
    try {
      setLoading(true);
      const response = await myApi.login(data);
      if (response.status === 200) {
        console.log("credentials true moving to otp !");
        router.push("/verify");
      }
    } catch (err) {
      console.log(err);
    } finally {
      setLoading(false);
    }
  };

  return (
    <>
      {loading && <Loader />}
      <AuthForm
        fields={fields}
        onSubmit={handleLogin}
        schema={loginSchema}
        buttonText="Se connecter"
        extra="Je n'ai pas de compte"
        path="/register"
      />
    </>
  );
};

export default LoginPage;
