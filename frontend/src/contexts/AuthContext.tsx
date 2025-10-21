"use client";
import React, { createContext, useState, useEffect } from "react";
import myApi from "@/lib/api";
import { useRouter } from "next/navigation";

interface AuthContextType {
  user: User | null;
  isAuthenticated: boolean;
  loading: boolean;
  updateUser: (user: User) => void;
  //   login: (cin: string, password: string) => Promise<void>;
  //   logout: () => Promise<void>;
}

export const AuthContext = createContext<AuthContextType | null>(null);

export const AuthProvider = ({ children }: { children: React.ReactNode }) => {
  const [user, setUser] = useState<User | null>(null);
  const [loading, setLoading] = useState(true);
  const router = useRouter();

  // Vérifie la session au montage (cookie HTTP-only)
  useEffect(() => {
    const checkAuth = async () => {
      try {
        const response = await myApi.verifyAuth();
        setUser(response);
        router.push(`/${user?.role.toLowerCase()}/stats`);
      } catch {
        setUser(null);
      } finally {
        setLoading(false);
      }
    };
    checkAuth();
  }, []);

  return (
    <AuthContext.Provider
      value={{
        user,
        isAuthenticated: !!user,
        loading,
        updateUser: setUser,
        // login,
        // logout,
      }}
    >
      {children}
    </AuthContext.Provider>
  );
};
