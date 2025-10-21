"use client";

import { useAuth } from "@/hooks/useAuth";
import { useRouter } from "next/router";
import { useEffect } from "react";

// TODO: If user not logged in, redirect to login/register, else redirect him to page according to role
// This is the page loaded in / (root)
export default function Home() {
  const { isAuthenticated } = useAuth();
  const router = useRouter();

  useEffect(() => {
    if (!isAuthenticated) {
      console.log("vous n'étes pas   connecté !");
      router.push("/login");
    }
  });
  return <h1>Hello friends !</h1>;
}
