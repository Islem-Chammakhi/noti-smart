"use client";

import AuthForm from "@/components/AuthForm";
import z from "zod";

const RegisterPage = () => {
  const fields = [
    { name: "cin", placeholder: "CIN", type: "number" },
    { name: "password", placeholder: "Mot de passe", type: "password" },
    {
      name: "confirmPassword",
      placeholder: "Confirmer mot de passe",
      type: "password",
    },
  ];

  const registerSchema = z
    .object({
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
      confirmPassword: z.string(),
    })
    .refine((data) => data.password === data.confirmPassword, {
      message: "Les mots de passe ne correspondent pas",
      path: ["confirmPassword"], // indique où afficher l'erreur
    });
  const handleRegister = (data: {
    cin: string;
    password: string;
    confirmPassword: string;
  }) => {
    console.log("Login data:", data);
    // Ici tu peux appeler ton backend Spring Boot
  };

  return (
    <AuthForm
      fields={fields}
      onSubmit={handleRegister}
      schema={registerSchema}
      buttonText="Créer compte"
      extra="Vous avez déja un compte"
      path="/login"
    />
  );
};

export default RegisterPage;
