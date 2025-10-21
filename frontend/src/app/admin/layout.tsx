"use client";
import { AppSideBar } from "@/components/AppSideBar";
import { SidebarProvider } from "@/components/ui/sidebar";
import { useAuth } from "@/hooks/useAuth";
import { ChartSpline, PersonStanding, File } from "lucide-react";

export default function AdminLayout({
  children,
}: {
  children: React.ReactNode;
}) {
  const { user } = useAuth();
  if (user?.role !== "ADMIN") window.location.href = "/";
  const items = [
    {
      title: "Importer des notes",
      url: "/admin/upload",
      icon: File,
    },
    {
      title: "Note des étudiants",
      url: "/admin/students",
      icon: PersonStanding,
    },
    {
      title: "Statistique",
      url: "/admin/stats/filiere",
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
