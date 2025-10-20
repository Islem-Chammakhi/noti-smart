import { AppSideBar } from "@/components/AppSideBar";
import { SidebarProvider } from "@/components/ui/sidebar";
import { ChartSpline } from "lucide-react";

export default function StudentLayout({
  children,
}: {
  children: React.ReactNode;
}) {
  const items = [
    {
      title: "Notes",
      url: "/student/marks",
      icon: ChartSpline,
    },
    // {
    //   title: "Note des étudiants",
    //   url: "/admin/students",
    //   icon: PersonStanding,
    // },
    // {
    //   title: "Importer des notes",
    //   url: "/admin/upload",
    //   icon: File,
    // },
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
