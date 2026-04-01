"use client";

import { useEffect, useState } from "react";
import Image from "next/image";
import { db } from "@/lib/firebase";
import { collection, getDocs } from "firebase/firestore";

type Client = {
  id: string;
  name: string;
  email: string;
  phone: string;
  interest: string;
};

function getStatusClasses(status: string) {
  switch (status) {
    case "Nuevo":
      return "bg-blue-100 text-blue-700";
    case "Interesado":
      return "bg-emerald-100 text-emerald-700";
    case "Negociando":
      return "bg-amber-100 text-amber-700";
    default:
      return "bg-slate-100 text-slate-600";
  }
}

export default function ContactsPage() {
  const [clients, setClients] = useState<Client[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchClients = async () => {
      try {
        const snapshot = await getDocs(collection(db, "clients"));

        const data = snapshot.docs.map((doc) => ({
          id: doc.id,
          ...(doc.data() as Omit<Client, "id">),
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

  return (
    <main className="min-h-screen bg-[#f6f7fb] text-slate-800">
      <div className="flex min-h-screen">
        {/* Sidebar */}
        <aside className="hidden w-[290px] shrink-0 border-r border-slate-200 bg-white lg:flex lg:flex-col">
          <div className="border-b border-slate-100 px-8 py-10">
            <Image
              src="/logo-syncra.png"
              alt="Syncra"
              width={190}
              height={90}
              className="h-auto w-auto"
            />
          </div>

          <nav className="flex-1 space-y-2 px-4 py-6">
            <a href="/dashboard" className="flex items-center gap-4 rounded-2xl px-5 py-4 text-[18px] text-slate-600 hover:bg-slate-50">
              ⌂ Dashboard
            </a>

            <a href="/properties" className="flex items-center gap-4 rounded-2xl px-5 py-4 text-[18px] text-slate-600 hover:bg-slate-50">
              ⊞ Inmuebles
            </a>

            <a href="/contacts" className="flex items-center gap-4 rounded-2xl bg-slate-50 px-5 py-4 text-[18px] font-medium text-slate-800">
              ◌ Contactos
            </a>
          </nav>
        </aside>

        {/* Content */}
        <section className="flex-1">
          <header className="border-b border-slate-200 px-8 py-7">
            <h1 className="text-5xl font-semibold">Contactos</h1>
            <p className="mt-2 text-lg text-slate-500">
              Gestiona todos los clientes registrados en el sistema.
            </p>
          </header>

          <div className="px-8 py-6">
            {/* Search + Button */}
            <div className="mb-6 flex gap-4">
              <input
                type="text"
                placeholder="Buscar cliente..."
                className="h-12 w-full rounded-xl border border-slate-200 px-4"
              />

              <a
                href="/contacts/new"
                className="rounded-xl bg-[#8bb58f] px-6 text-white font-semibold flex items-center"
              >
                + Nuevo cliente
              </a>
            </div>

            {/* Loading */}
            {loading ? (
              <p>Cargando clientes...</p>
            ) : (
              <div className="overflow-hidden rounded-2xl border border-slate-200 bg-white">
                <table className="w-full">
                  <thead className="bg-slate-50 text-left">
                    <tr>
                      <th className="px-6 py-4">Cliente</th>
                      <th>Email</th>
                      <th>Teléfono</th>
                      <th>Interés</th>
                      <th>Acciones</th>
                    </tr>
                  </thead>

                  <tbody>
                    {clients.map((client) => (
                      <tr key={client.id} className="border-t">
                        <td className="px-6 py-4 flex items-center gap-3">
                          <Image
                            src="/google.png"
                            alt={client.name}
                            width={40}
                            height={40}
                            className="rounded-full"
                          />
                          <span className="font-medium">{client.name}</span>
                        </td>

                        <td>{client.email}</td>
                        <td>{client.phone}</td>
                        <td>{client.interest}</td>

                        <td>
                          <a
                            href={`/contacts/edit/${client.id}`}
                            className="text-emerald-700 font-semibold hover:underline"
                          >
                            Editar
                          </a>
                        </td>
                      </tr>
                    ))}
                  </tbody>
                </table>

                {clients.length === 0 && (
                  <p className="p-6 text-slate-500">
                    No hay clientes registrados aún.
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