"use client";

import { useEffect, useState } from "react";
import { db } from "@/lib/firebase";
import { addDoc, collection, getDocs } from "firebase/firestore";
import { useRouter } from "next/navigation";

type Property = {
  id: string;
  title: string;
};

export default function NewClientPage() {
  const router = useRouter();

  const [name, setName] = useState("");
  const [email, setEmail] = useState("");
  const [phone, setPhone] = useState("");
  const [interest, setInterest] = useState("");
  const [propertyId, setPropertyId] = useState("");
  const [properties, setProperties] = useState<Property[]>([]);
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    const fetchProperties = async () => {
      try {
        const snapshot = await getDocs(collection(db, "properties"));
        const data = snapshot.docs.map((doc) => ({
          id: doc.id,
          title: (doc.data().title as string) || "Sin título",
        }));
        setProperties(data);
      } catch (error) {
        console.error("Error cargando propiedades:", error);
      }
    };

    fetchProperties();
  }, []);

  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    if (!name || !email || !phone) {
      alert("Completa los campos obligatorios");
      return;
    }

    try {
      setLoading(true);

      await addDoc(collection(db, "clients"), {
        name,
        email,
        phone,
        interest,
        propertyId: propertyId || "",
      });

      alert("Cliente creado correctamente");
      router.push("/contacts");
    } catch (error) {
      console.error(error);
      alert("Error al crear cliente");
    } finally {
      setLoading(false);
    }
  };

  return (
    <main className="min-h-screen bg-[#f6f7fb] p-8">
      <div className="mx-auto max-w-2xl">
        <button
          onClick={() => router.push("/contacts")}
          className="mb-6 rounded-xl border px-4 py-2"
        >
          ← Volver
        </button>

        <div className="rounded-2xl border bg-white p-8 shadow-sm">
          <h1 className="mb-6 text-3xl font-semibold">Nuevo Cliente</h1>

          <form onSubmit={handleSubmit} className="space-y-4">
            <input
              type="text"
              placeholder="Nombre"
              className="h-12 w-full rounded-xl border px-4"
              value={name}
              onChange={(e) => setName(e.target.value)}
            />

            <input
              type="email"
              placeholder="Email"
              className="h-12 w-full rounded-xl border px-4"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
            />

            <input
              type="text"
              placeholder="Teléfono"
              className="h-12 w-full rounded-xl border px-4"
              value={phone}
              onChange={(e) => setPhone(e.target.value)}
            />

            <input
              type="text"
              placeholder="Interés"
              className="h-12 w-full rounded-xl border px-4"
              value={interest}
              onChange={(e) => setInterest(e.target.value)}
            />

            <select
              className="h-12 w-full rounded-xl border px-4"
              value={propertyId}
              onChange={(e) => setPropertyId(e.target.value)}
            >
              <option value="">Selecciona una propiedad</option>
              {properties.map((property) => (
                <option key={property.id} value={property.id}>
                  {property.title}
                </option>
              ))}
            </select>

            <button
              type="submit"
              disabled={loading}
              className="h-12 w-full rounded-xl bg-[#8bb58f] font-semibold text-white"
            >
              {loading ? "Guardando..." : "Guardar Cliente"}
            </button>
          </form>
        </div>
      </div>
    </main>
  );
}