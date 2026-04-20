"use client";

import { useEffect, useMemo, useState } from "react";
import Image from "next/image";
import { db } from "@/lib/firebase";
import { collection, deleteDoc, doc, getDocs } from "firebase/firestore";
import Link from "next/link";
import { Grid2x2, Home, Settings, User } from "lucide-react";

type Client = {
  id: string;
  name: string;
  email: string;
  phone: string;
  interest: string;
};

function SidebarItem({
  icon,
  label,
  active = false,
}: {
  icon: React.ReactNode;
  label: string;
  active?: boolean;
}) {
  return (
    <div
      className={`flex items-center gap-3 rounded-xl px-4 py-3 text-[15px] font-medium transition ${
        active
          ? "bg-emerald-50 text-emerald-700"
          : "text-slate-500 hover:bg-slate-100"
      }`}
    >
      <span className="flex h-5 w-5 items-center justify-center">{icon}</span>
      <span>{label}</span>
    </div>
  );
}

export default function ContactsPage() {
  const [clients, setClients] = useState<Client[]>([]);
  const [loading, setLoading] = useState(true);
  const [searchTerm, setSearchTerm] = useState("");

  useEffect(() => {
    const fetchClients = async () => {
      try {
        const snapshot = await getDocs(collection(db, "clients"));

        const data = snapshot.docs.map((docItem) => ({
          id: docItem.id,
          ...(docItem.data() as Omit<Client, "id">),
        }));

        setClients(data);
      } catch (error) {
        console.error("Error cargando clientes:", error);
      } finally {
        setLoading(false);
      }
    };

    fetchClients();
  }, []);

  const handleDelete = async (id: string) => {
    const confirmed = window.confirm(
      "¿Seguro que deseas eliminar este cliente?"
    );

    if (!confirmed) return;

    try {
      await deleteDoc(doc(db, "clients", id));
      setClients((prev) => prev.filter((client) => client.id !== id));
    } catch (error) {
      console.error("Error eliminando cliente:", error);
      alert("No se pudo eliminar el cliente");
    }
  };

  const filteredClients = useMemo(() => {
    const term = searchTerm.trim().toLowerCase();

    if (!term) return clients;

    return clients.filter((client) => {
      return (
        client.name?.toLowerCase().includes(term) ||
        client.email?.toLowerCase().includes(term) ||
        client.phone?.toLowerCase().includes(term) ||
        client.interest?.toLowerCase().includes(term)
      );
    });
  }, [clients, searchTerm]);

  return (
    <main className="min-h-screen bg-[#f6f7fb] text-slate-800">
      <div className="flex min-h-screen">
        <aside className="hidden w-[290px] shrink-0 border-r border-slate-200 bg-white lg:flex lg:flex-col">
          <div className="flex flex-col items-center px-8 pb-8 pt-10">
            <div className="relative mb-5 h-[90px] w-[150px]">
              <Image
                src="/logo-syncra.png"
                alt="Syncra Estate AI"
                fill
                className="object-contain"
              />
            </div>

            <h2 className="text-[20px] font-semibold text-slate-800">
              Syncra Estate AI
            </h2>
          </div>

          <nav className="flex-1 space-y-2 px-5">
            <Link href="/dashboard">
              <SidebarItem
                icon={<Home className="h-5 w-5" />}
                label="Dashboard"
              />
            </Link>

            <Link href="/properties">
              <SidebarItem
                icon={<Grid2x2 className="h-5 w-5" />}
                label="Propiedades"
              />
            </Link>

            <Link href="/contacts">
              <SidebarItem
                icon={<User className="h-5 w-5" />}
                label="Contactos"
                active
              />
            </Link>
          </nav>

          <div className="px-5 pb-8 pt-4">
            <div className="flex items-center gap-3 rounded-xl px-4 py-3 text-[15px] font-medium text-slate-500">
              <Settings className="h-5 w-5" />
              <span>Ajustes</span>
            </div>
          </div>
        </aside>

        <section className="flex-1">
          <header className="border-b border-slate-200 px-8 py-7">
            <h1 className="text-5xl font-semibold">Contactos</h1>
            <p className="mt-2 text-lg text-slate-500">
              Gestiona todos los clientes registrados en el sistema.
            </p>
          </header>

          <div className="px-8 py-6">
            <div className="mb-6 flex gap-4">
              <input
                type="text"
                placeholder="Buscar cliente..."
                value={searchTerm}
                onChange={(e) => setSearchTerm(e.target.value)}
                className="h-12 w-full rounded-xl border border-slate-200 px-4 outline-none"
              />

              <a
                href="/contacts/new"
                className="flex items-center rounded-xl bg-[#8bb58f] px-6 font-semibold text-white"
              >
                + Nuevo cliente
              </a>
            </div>

            {loading ? (
              <p className="text-slate-500">Cargando clientes...</p>
            ) : (
              <div className="overflow-hidden rounded-2xl border border-slate-200 bg-white">
                {filteredClients.length > 0 ? (
                  <table className="w-full">
                    <thead className="bg-slate-50 text-left text-slate-600">
                      <tr>
                        <th className="px-6 py-4">Cliente</th>
                        <th className="py-4">Email</th>
                        <th className="py-4">Teléfono</th>
                        <th className="py-4">Interés</th>
                        <th className="px-6 py-4">Acciones</th>
                      </tr>
                    </thead>

                    <tbody>
                      {filteredClients.map((client) => (
                        <tr key={client.id} className="border-t border-slate-200">
                          <td className="px-6 py-4">
                            <div className="flex items-center gap-3">
                              <div className="flex h-10 w-10 items-center justify-center rounded-full bg-emerald-100 font-semibold text-emerald-700">
                                {client.name?.charAt(0).toUpperCase() || "C"}
                              </div>
                              <span className="font-medium text-slate-800">
                                {client.name}
                              </span>
                            </div>
                          </td>

                          <td className="text-slate-600">{client.email}</td>
                          <td className="text-slate-600">{client.phone}</td>
                          <td className="text-slate-600">{client.interest}</td>

                          <td className="px-6 py-5">
                            <div className="flex items-center gap-6 text-slate-500">
                              <a
                                href={`/contacts/edit/${client.id}`}
                                className="transition hover:text-slate-800"
                              >
                                ✎ Editar
                              </a>

                              <button
                                onClick={() => handleDelete(client.id)}
                                className="transition hover:text-red-600"
                              >
                                🗑 Eliminar
                              </button>
                            </div>
                          </td>
                        </tr>
                      ))}
                    </tbody>
                  </table>
                ) : (
                  <p className="p-6 text-slate-500">
                    {clients.length === 0
                      ? "No hay clientes registrados aún."
                      : "No se encontraron clientes con esa búsqueda."}
                  </p>
                )}
              </div>
            )}
          </div>
        </section>
      </div>
    </main>
  );
}