"use client";
import { AppSideBar } from "@/components/AppSideBar";
import Loader from "@/components/Loader";
import { SidebarProvider } from "@/components/ui/sidebar";
import { useAuth } from "@/hooks/useAuth";
import { BookOpenCheck, ChartSpline } from "lucide-react";
import { useRouter } from "next/navigation";

export default function StudentLayout({
  children,
}: {
  children: React.ReactNode;
}) {
  const { user, loading } = useAuth();
  const router = useRouter();
  if (loading) return <Loader />;
  if ((!loading && !user) || user?.role !== "STUDENT") router.push("/login");
  const items = [
    {
      title: "Notes",
      url: "/student/marks",
      icon: BookOpenCheck,
    },
    {
      title: "Statistiques",
      url: "/student/stats",
      icon: ChartSpline,
    },
  ];
  return (
    <div className="min-h-screen flex flex-col">
      <SidebarProvider>
        <AppSideBar items={items} />
        <main className="flex-1 p-6 bg-gray-50">{children}</main>
      </SidebarProvider>
    </div>
  );
}
