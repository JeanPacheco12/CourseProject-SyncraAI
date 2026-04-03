"use client";

import { useEffect, useState } from "react";
import { db } from "@/lib/firebase";
import { doc, getDoc, updateDoc } from "firebase/firestore";
import { useParams, useRouter } from "next/navigation";

export default function EditClientPage() {
  const router = useRouter();
  const params = useParams();
  const id = params.id as string;

  const [name, setName] = useState("");
  const [email, setEmail] = useState("");
  const [phone, setPhone] = useState("");
  const [interest, setInterest] = useState("");
  const [loading, setLoading] = useState(true);
  const [saving, setSaving] = useState(false);

  useEffect(() => {
    const fetchClient = async () => {
      try {
        const ref = doc(db, "clients", id);
        const snapshot = await getDoc(ref);

        if (!snapshot.exists()) {
          alert("El cliente no existe");
          router.push("/contacts");
          return;
        }

        const data = snapshot.data();
        setName(data.name || "");
        setEmail(data.email || "");
        setPhone(data.phone || "");
        setInterest(data.interest || "");
      } catch (error) {
        console.error("Error cargando cliente:", error);
        alert("No se pudo cargar el cliente");
        router.push("/contacts");
      } finally {
        setLoading(false);
      }
    };

    if (id) fetchClient();
  }, [id, router]);

  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    if (!name || !email || !phone) {
      alert("Completa los campos obligatorios");
      return;
    }

    try {
      setSaving(true);

      await updateDoc(doc(db, "clients", id), {
        name,
        email,
        phone,
        interest,
      });

      alert("Cliente actualizado correctamente");
      router.push("/contacts");
    } catch (error) {
      console.error("Error actualizando cliente:", error);
      alert("No se pudo actualizar el cliente");
    } finally {
      setSaving(false);
    }
  };

  if (loading) {
    return <main className="p-8">Cargando cliente...</main>;
  }

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
          <h1 className="mb-6 text-3xl font-semibold">Editar Cliente</h1>

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

            <button
              type="submit"
              disabled={saving}
              className="h-12 w-full rounded-xl bg-[#8bb58f] font-semibold text-white"
            >
              {saving ? "Guardando..." : "Guardar cambios"}
            </button>
          </form>
        </div>
      </div>
    </main>
  );
}