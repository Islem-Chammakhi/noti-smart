import { Loader2 } from "lucide-react";

export const Loader = () => {
  return (
    <div className="fixed inset-0 flex items-center justify-center bg-white/60 backdrop-blur-sm z-50">
      <Loader2 className="h-12 w-12 animate-spin text-blue-600" />
    </div>
  );
};
