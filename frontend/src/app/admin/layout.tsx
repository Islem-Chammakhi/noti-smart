export default function AdminLayout({
  children,
}: {
  children: React.ReactNode;
}) {
  return (
    <div className="min-h-screen flex flex-col">
      <header className="bg-indigo-700 text-white p-4 text-xl font-bold">
        Admin Dashboard
      </header>

      <main className="flex-1 p-6 bg-gray-50">{children}</main>
    </div>
  );
}
