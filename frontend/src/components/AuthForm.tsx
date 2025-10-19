"use client";
import Image from "next/image";
import { Input } from "./ui/input";
import { Button } from "./ui/button";
import { useForm } from "react-hook-form";
import { z } from "zod";
import { zodResolver } from "@hookform/resolvers/zod";

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
  onSubmit: (data: any) => void;
}

const AuthForm = ({
  fields,
  buttonText,
  extra,
  path,
  schema,
  onSubmit,
}: AuthFormProps) => {
  const {
    register,
    formState: { errors, isSubmitting },
    handleSubmit,
    setError,
  } = useForm<z.infer<typeof schema>>({
    resolver: zodResolver(schema),
  });

  const submitHandler = async (data: z.infer<typeof schema>) => {
    await new Promise((resolve) => setTimeout(resolve, 1000));
    console.log("Formulaire soumis :", data);
    onSubmit(data);
  };

  return (
    <div className="flex flex-col items-center justify-center">
      <Image
        src="/isimm.png"
        alt="isimm logo"
        width={150}
        height={150}
        className="mb-4"
      />
      <form
        onSubmit={handleSubmit(submitHandler)}
        className="space-y-4 flex flex-col justify-center items-center"
      >
        {fields.map((field) => (
          <div key={field.name} className="flex flex-col w-80">
            <Input
              type={field.type}
              placeholder={field.placeholder}
              {...register(field.name as any)}
              className="border-2 border-gray-800 mb-2"
            />
            {errors[field.name] && (
              <span className="text-red-800 text-sm font-semibold">
                {errors[field.name]?.message as string}
              </span>
            )}
          </div>
        ))}

        <Button
          type="submit"
          disabled={isSubmitting}
          className="cursor-pointer"
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
