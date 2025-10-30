"use client";
import Loader from "@/components/Loader";
import { useAuth } from "@/hooks/useAuth";
import { useRouter } from "next/navigation";
import { useEffect } from "react";

// TODO: If user not logged in, redirect to login/register, else redirect him to page according to role
// This is the page loaded in / (root)
export default function Home() {
  const { user, loading } = useAuth();
  const router = useRouter();
  useEffect(() => {
    if (!loading) {
      if (!user) router.push("/login");
      else if (user.role === "ADMIN") router.push("/admin/upload");
      else if (user.role === "STUDENT") router.push("/student/marks");
    }
  }, [loading, user, router]);

  if (loading) return <Loader />;

  // Tu peux afficher un écran vide ou "redirecting..."
  return <></>;
  // return <h1>Hello friends !</h1>;
}
