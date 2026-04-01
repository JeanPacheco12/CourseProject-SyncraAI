"use client";

import { useState } from "react";
import { db } from "@/lib/firebase";
import { addDoc, collection } from "firebase/firestore";
import { useRouter } from "next/navigation";

export default function NewClientPage() {
  const router = useRouter();

  const [name, setName] = useState("");
  const [email, setEmail] = useState("");
  const [phone, setPhone] = useState("");
  const [interest, setInterest] = useState("");
  const [loading, setLoading] = useState(false);

  const handleSubmit = async (e: any) => {
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

        <div className="rounded-2xl bg-white p-8 shadow-sm border">
          <h1 className="text-3xl font-semibold mb-6">Nuevo Cliente</h1>

          <form onSubmit={handleSubmit} className="space-y-4">
            <input
              type="text"
              placeholder="Nombre"
              className="w-full h-12 border rounded-xl px-4"
              value={name}
              onChange={(e) => setName(e.target.value)}
            />

            <input
              type="email"
              placeholder="Email"
              className="w-full h-12 border rounded-xl px-4"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
            />

            <input
              type="text"
              placeholder="Teléfono"
              className="w-full h-12 border rounded-xl px-4"
              value={phone}
              onChange={(e) => setPhone(e.target.value)}
            />

            <input
              type="text"
              placeholder="Interés (ej: Casa en Antigua Q500k)"
              className="w-full h-12 border rounded-xl px-4"
              value={interest}
              onChange={(e) => setInterest(e.target.value)}
            />

            <button
              type="submit"
              disabled={loading}
              className="w-full h-12 rounded-xl bg-[#8bb58f] text-white font-semibold"
            >
              {loading ? "Guardando..." : "Guardar Cliente"}
            </button>
          </form>
        </div>
      </div>
    </main>
  );
}