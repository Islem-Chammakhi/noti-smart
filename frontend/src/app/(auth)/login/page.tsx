"use client";

import AuthForm from "@/components/AuthForm";
import { useRouter } from "next/navigation";
import { z } from "zod";
import myApi from "@/lib/api"


const LoginPage = () => {
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

  const router = useRouter();
  const handleLogin = (data: { cin: string; password: string }) => {
    myApi.login(data).then(res=>{
      if(res.status ==200){
        router.push("/verify");

      }
    }).catch(e =>{
      console.error("Error logging in", e)
    })
  };

  return (
    <AuthForm
      fields={fields}
      onSubmit={handleLogin}
      schema={loginSchema}
      buttonText="Se connecter"
      extra="Je n'ai pas de compte"
      path="/register"
    />
  );
};

export default LoginPage;
