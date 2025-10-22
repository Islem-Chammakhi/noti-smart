// "use client";

import { Loader2 } from "lucide-react";

const Loader = () => {
  return (
    <div className="fixed inset-0 flex items-center justify-center bg-white/60 backdrop-blur-sm z-50">
      <Loader2 className="h-15 w-15 animate-spin " />
    </div>
  );
};

export default Loader;
