"use client";
import Image from "next/image";
import { Input } from "./ui/input";
import { Button } from "./ui/button";
import { Controller, useForm } from "react-hook-form";
import { z } from "zod";
import { zodResolver } from "@hookform/resolvers/zod";
import {
  InputOTP,
  InputOTPSlot,
  InputOTPGroup,
} from "@/components/ui/input-otp";
import { useState } from "react";

interface Field {
  name: string;
  placeholder: string;
  type: string;
}

interface AuthFormProps {
  fields: Field[];
  buttonText: string;
  extra?: string;
  path?: string;
  schema: z.ZodObject<any>; // ton schéma Zod
  type?: string;
  title: string;
  onSubmit: (data: any) => void;
}

const AuthForm = ({
  fields,
  buttonText,
  extra,
  path,
  schema,
  type,
  title,
  onSubmit,
}: AuthFormProps) => {
  const {
    register,
    formState: { errors, isSubmitting },
    handleSubmit,
    control,
  } = useForm<z.infer<typeof schema>>({
    resolver: zodResolver(schema),
  });
  const [error, setError] = useState<String | null>(null);
  const submitHandler = async (data: z.infer<typeof schema>) => {
    setError(null);
    try {
      await onSubmit(data);
    } catch (err: any) {
      setError(err.message || "Erreur lors de la requête.");
    }
  };

  return (
    <div className="flex flex-col items-center justify-center">
      <Image
        src="/isimm.png"
        alt="isimm logo"
        width={120}
        height={120}
        className="mb-6 animate-fade-in"
      />
      <h1 className="text-2xl font-semibold text-gray-800 mb-4">{title}</h1>
      <p className="text-gray-500 mb-6 text-center">
        Connectez-vous à votre espace.
      </p>
      <form
        onSubmit={handleSubmit(submitHandler)}
        className="space-y-4 flex flex-col justify-center items-center"
      >
        {fields.map((field) => (
          <div
            key={field.name}
            className="flex flex-col w-80 items-center justify-center"
          >
            {type === "otp" ? (
              <Controller
                name={field.name}
                control={control}
                render={({ field }) => (
                  <InputOTP
                    className="border border-gray-300 focus:ring-2 focus:ring-blue-400 rounded-xl px-4 py-2 mb-2 transition-all"
                    maxLength={6}
                    value={typeof field.value === "string" ? field.value : ""}
                    onChange={field.onChange}
                  >
                    <InputOTPGroup>
                      {[...Array(6)].map((_, i) => (
                        <InputOTPSlot key={i} index={i} />
                      ))}
                    </InputOTPGroup>
                  </InputOTP>
                )}
              />
            ) : (
              <Input
                type={field.type}
                placeholder={field.placeholder}
                {...register(field.name as any)}
                className="border border-gray-300 focus:ring-2 focus:ring-blue-400 rounded-xl px-4 py-2 mb-2 transition-all"
              />
            )}
            {errors[field.name] && (
              <span className="text-red-800 text-sm font-semibold">
                {errors[field.name]?.message as string}
              </span>
            )}
          </div>
        ))}
        {error && (
          <div className="bg-red-100 text-red-700 px-4 py-2 rounded-lg shadow-sm border border-red-200">
            {error}
          </div>
        )}
        <Button
          type="submit"
          disabled={isSubmitting}
          className="cursor-pointer w-full mt-4 bg-blue-600 hover:bg-blue-700 text-white font-semibold py-2 rounded-xl transition-all shadow-md hover:shadow-lg"
        >
          {buttonText}
        </Button>

        {extra && path && (
          <p className="text-sm text-center mt-2 text-gray-500">
            <a href={path} className="text-blue-500 hover:underline">
              {extra} ?
            </a>
          </p>
        )}
        {extra && !path && (
          <p className="text-sm text-center mt-2 text-gray-500">{extra}</p>
        )}
      </form>
    </div>
  );
};

export default AuthForm;
