"use client";
import Loader from "@/components/Loader";
import { useAuth } from "@/hooks/useAuth";
import { useRouter } from "next/navigation";

export default function AuthLayout({
  children,
}: {
  children: React.ReactNode;
}) {
  const { user, loading } = useAuth();
  const router = useRouter();
  if (loading) return <Loader />;
  if (!loading && user) {
    if (user.role === "STUDENT") router.push("/student/marks");
    if (user.role === "ADMIN") router.push("/admin/upload");
  }
  return (
    <div className="min-h-screen flex items-center justify-center bg-gradient-to-br from-blue-100 via-white to-blue-200">
      <div className="bg-white/80 backdrop-blur-md shadow-2xl rounded-3xl p-10 w-full max-w-md ">
        {children}
      </div>
    </div>
  );
}
