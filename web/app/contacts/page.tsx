"use client";

import { useEffect, useState } from "react";
import Image from "next/image";
import { db } from "@/lib/firebase";
import { collection, deleteDoc, doc, getDocs } from "firebase/firestore";
import Link from "next/link";
import {
  Bell,
  ChevronDown,
  ClipboardList,
  DollarSign,
  Grid2x2,
  Home,
  LineChart,
  Search,
  Settings,
  User,
  Users,
  CalendarDays,
  Check,
} from "lucide-react";

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
  const handleDelete = async (id: string) => {
  const confirmed = window.confirm("¿Seguro que deseas eliminar este cliente?");

  if (!confirmed) return;

  try {
    await deleteDoc(doc(db, "clients", id));

    setClients((prev) => prev.filter((client) => client.id !== id));
  } catch (error) {
    console.error("Error eliminando cliente:", error);
    alert("No se pudo eliminar el cliente");
  }
};

  return (
    <main className="min-h-screen bg-[#f6f7fb] text-slate-800">
      <div className="flex min-h-screen">
        {/* Sidebar */}
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
            <SidebarItem
              icon={<ClipboardList className="h-5 w-5" />}
              label="Citas"
            />
            <SidebarItem
              icon={<LineChart className="h-5 w-5" />}
              label="Reportes"
            />
            <SidebarItem
              icon={<Settings className="h-5 w-5" />}
              label="Ajustes"
            />
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

                        <td className="px-6 py-5">
                          <div className="flex items-center gap-6 text-slate-500">
                            <a
                              href={`/properties/edit/${client.id}`}
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