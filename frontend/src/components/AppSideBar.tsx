"use client";

import { LogOut } from "lucide-react";
import {
  Sidebar,
  SidebarContent,
  SidebarFooter,
  SidebarGroup,
  SidebarGroupContent,
  SidebarGroupLabel,
  SidebarHeader,
  SidebarMenu,
  SidebarMenuButton,
  SidebarMenuItem,
  SidebarSeparator,
} from "./ui/sidebar";
import Link from "next/link";
import Image from "next/image";
import { ForwardRefExoticComponent, RefAttributes } from "react";
import { usePathname, useRouter } from "next/navigation";
import { cn } from "@/lib/utils"; // helper de shadcn
import api from "@/lib/api";
import { useAuth } from "@/hooks/useAuth";
import { Button } from "./ui/button";

type AppSideBarProps = {
  title: string;
  url: string;
  icon: ForwardRefExoticComponent<
    Omit<React.SVGProps<SVGSVGElement>, "ref"> & RefAttributes<SVGSVGElement>
  >;
};

export const AppSideBar = ({ items }: { items: AppSideBarProps[] }) => {
  const pathname = usePathname();
  const router = useRouter();
  const { updateUser } = useAuth();
  const handleLogout = async () => {
    try {
      const res = await api.logout();
      if (res.status === 200) {
        console.log("deconnexion !");
        updateUser(null);
      }
      router.push("/login");
    } catch (err) {
      console.log(err);
    }
  };
  return (
    <Sidebar className="bg-white/70 backdrop-blur-xl border-r border-blue-100 shadow-lg">
      {/* ---- Header ---- */}
      <SidebarHeader className="flex items-center justify-center py-6">
        <SidebarMenu>
          <SidebarMenuItem>
            <SidebarMenuButton asChild>
              <Link
                href="/"
                className="flex items-center space-x-3 group transition-all duration-200"
              >
                <Image
                  src="/isimm.png"
                  alt="ISIMM logo"
                  width={32}
                  height={32}
                  className="group-hover:scale-110 transition-transform"
                />
                <span className="text-xl font-bold text-blue-700 group-hover:text-blue-900">
                  ISIMM
                </span>
              </Link>
            </SidebarMenuButton>
          </SidebarMenuItem>
          <SidebarSeparator />
        </SidebarMenu>
      </SidebarHeader>

      {/* ---- Content ---- */}
      <SidebarContent>
        <SidebarGroup>
          <SidebarGroupLabel className="text-gray-500 uppercase tracking-wider text-xs mb-2">
            Services
          </SidebarGroupLabel>
          <SidebarGroupContent>
            <SidebarMenu>
              {items.map((item) => {
                const isActive = pathname === item.url;
                return (
                  <SidebarMenuItem key={item.title}>
                    <SidebarMenuButton asChild>
                      <Link
                        href={item.url}
                        className={cn(
                          "flex items-center gap-3 px-3 py-2 rounded-lg transition-all duration-200",
                          isActive
                            ? "bg-blue-100 text-blue-700 font-semibold shadow-inner"
                            : "text-gray-600 hover:bg-blue-50 hover:text-blue-600"
                        )}
                      >
                        <item.icon
                          // size={20}
                          className={cn(
                            "transition-all duration-200",
                            isActive
                              ? "text-blue-700 scale-110"
                              : "text-gray-500 group-hover:text-blue-500"
                          )}
                        />
                        <span>{item.title}</span>
                      </Link>
                    </SidebarMenuButton>
                  </SidebarMenuItem>
                );
              })}
            </SidebarMenu>
          </SidebarGroupContent>
        </SidebarGroup>
      </SidebarContent>

      {/* ---- Footer ---- */}
      <SidebarFooter className="border-t border-blue-100 mt-2 pt-3">
        <SidebarMenu>
          <SidebarMenuItem>
            <SidebarMenuButton asChild>
              <Button
                onClick={handleLogout}
                className="bg-white flex items-center gap-3 text-red-600 hover:text-red-700 transition-all duration-200"
              >
                <LogOut size={20} />
                <span>Se déconnecter</span>
              </Button>
            </SidebarMenuButton>
          </SidebarMenuItem>
        </SidebarMenu>
      </SidebarFooter>
    </Sidebar>
  );
};
