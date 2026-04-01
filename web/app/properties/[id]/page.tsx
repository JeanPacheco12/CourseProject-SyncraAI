"use client";

import { useEffect, useState } from "react";
import Image from "next/image";
import { db } from "@/lib/firebase";
import {
  collection,
  doc,
  getDoc,
  getDocs,
  query,
  where,
} from "firebase/firestore";
import { useParams, useRouter } from "next/navigation";

type Property = {
  id: string;
  title: string;
  type: string;
  location: string;
  price: number;
  status: string;
  interested?: number;
};

type Client = {
  id: string;
  name: string;
  email: string;
  phone: string;
  interest: string;
  propertyId?: string;
};

function getStatusClasses(status: string) {
  switch (status) {
    case "Disponible":
      return "bg-emerald-100 text-emerald-700";
    case "Reservado":
      return "bg-amber-100 text-amber-700";
    case "Vendido":
      return "bg-slate-200 text-slate-600";
    case "Visitas":
      return "bg-sky-100 text-sky-700";
    default:
      return "bg-slate-100 text-slate-600";
  }
}

export default function PropertyDetailPage() {
  const params = useParams();
  const router = useRouter();
  const id = params.id as string;

  const [property, setProperty] = useState<Property | null>(null);
  const [clients, setClients] = useState<Client[]>([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchPropertyAndClients = async () => {
      try {
        const propertyRef = doc(db, "properties", id);
        const propertySnapshot = await getDoc(propertyRef);

        if (!propertySnapshot.exists()) {
          alert("La propiedad no existe");
          router.push("/properties");
          return;
        }

        setProperty({
          id: propertySnapshot.id,
          ...(propertySnapshot.data() as Omit<Property, "id">),
        });

        const clientsQuery = query(
          collection(db, "clients"),
          where("propertyId", "==", id)
        );

        const clientsSnapshot = await getDocs(clientsQuery);

        const clientsData = clientsSnapshot.docs.map((doc) => ({
          id: doc.id,
          ...(doc.data() as Omit<Client, "id">),
        }));

        setClients(clientsData);
      } catch (error) {
        console.error("Error al cargar propiedad/clientes:", error);
        alert("No se pudo cargar la propiedad");
      } finally {
        setLoading(false);
      }
    };

    if (id) fetchPropertyAndClients();
  }, [id, router]);

  if (loading) {
    return <main className="p-10">Cargando propiedad...</main>;
  }

  if (!property) {
    return <main className="p-10">No se encontró la propiedad.</main>;
  }

  return (
    <main className="min-h-screen bg-[#f6f7fb] p-8 text-slate-800">
      <div className="mx-auto max-w-5xl">
        <div className="mb-6 flex items-center justify-between">
          <button
            onClick={() => router.push("/properties")}
            className="rounded-xl border border-slate-300 bg-white px-4 py-2 text-slate-700"
          >
            ← Volver
          </button>

          <a
            href={`/properties/edit/${property.id}`}
            className="rounded-xl bg-[#8bb58f] px-4 py-2 font-medium text-white"
          >
            Editar propiedad
          </a>
        </div>

        <div className="overflow-hidden rounded-3xl border border-slate-200 bg-white shadow-sm">
          <div className="relative h-[280px] w-full bg-slate-200">
            <Image
              src="/bg-login.png"
              alt={property.title}
              fill
              className="object-cover"
            />
          </div>

          <div className="p-8">
            <div className="mb-6 flex flex-col gap-4 md:flex-row md:items-start md:justify-between">
              <div>
                <h1 className="text-4xl font-semibold text-slate-800">
                  {property.title}
                </h1>
                <p className="mt-2 text-lg text-slate-500">
                  {property.location}
                </p>
              </div>

              <div className="text-left md:text-right">
                <p className="text-sm text-slate-400">Precio</p>
                <p className="text-3xl font-bold text-slate-800">
                  Q{property.price.toLocaleString()}
                </p>
              </div>
            </div>

            <div className="mb-8">
              <span
                className={`inline-flex rounded-full px-4 py-2 text-sm font-semibold ${getStatusClasses(
                  property.status
                )}`}
              >
                {property.status}
              </span>
            </div>

            <div className="grid gap-4 md:grid-cols-3">
              <div className="rounded-2xl bg-slate-50 p-5">
                <p className="text-sm text-slate-400">Tipo</p>
                <p className="mt-2 text-lg font-semibold text-slate-800">
                  {property.type || "No definido"}
                </p>
              </div>

              <div className="rounded-2xl bg-slate-50 p-5">
                <p className="text-sm text-slate-400">Ubicación</p>
                <p className="mt-2 text-lg font-semibold text-slate-800">
                  {property.location}
                </p>
              </div>

              <div className="rounded-2xl bg-slate-50 p-5">
                <p className="text-sm text-slate-400">Interesados</p>
                <p className="mt-2 text-lg font-semibold text-slate-800">
                  {clients.length}
                </p>
              </div>
            </div>

            <div className="mt-10">
              <div className="mb-4 flex items-center justify-between">
                <h2 className="text-2xl font-semibold text-slate-800">
                  Clientes interesados
                </h2>
              </div>

              {clients.length === 0 ? (
                <div className="rounded-2xl bg-slate-50 p-5 text-slate-500">
                  Aún no hay clientes interesados en esta propiedad.
                </div>
              ) : (
                <div className="overflow-hidden rounded-2xl border border-slate-200 bg-white">
                  <table className="w-full">
                    <thead className="bg-slate-50 text-left">
                      <tr>
                        <th className="px-6 py-4">Nombre</th>
                        <th>Email</th>
                        <th>Teléfono</th>
                        <th>Interés</th>
                        <th>Acciones</th>
                      </tr>
                    </thead>
                    <tbody>
                      {clients.map((client) => (
                        <tr key={client.id} className="border-t">
                          <td className="px-6 py-4 font-medium">
                            {client.name}
                          </td>
                          <td>{client.email}</td>
                          <td>{client.phone}</td>
                          <td>{client.interest || "No definido"}</td>
                          <td>
                            <a
                              href={`/contacts/edit/${client.id}`}
                              className="font-semibold text-emerald-700 hover:underline"
                            >
                              Editar cliente
                            </a>
                          </td>
                        </tr>
                      ))}
                    </tbody>
                  </table>
                </div>
              )}
            </div>
          </div>
        </div>
      </div>
    </main>
  );
}