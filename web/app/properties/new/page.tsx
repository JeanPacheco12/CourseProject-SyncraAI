"use client";

import { useState } from "react";
import { addDoc, collection } from "firebase/firestore";
import { db } from "@/lib/firebase";
import { useRouter } from "next/navigation";

export default function NewPropertyPage() {
  const router = useRouter();

  const [form, setForm] = useState({
    title: "",
    type: "",
    location: "",
    price: "",
    status: "Disponible",
    interested: 0,
  });

  const handleChange = (e: any) => {
    setForm({
      ...form,
      [e.target.name]: e.target.value,
    });
  };

  const handleSubmit = async (e: any) => {
    e.preventDefault();

    // 🔥 validación básica
    if (!form.title || !form.price || !form.location) {
      alert("Completa los campos obligatorios");
      return;
    }

    try {
      await addDoc(collection(db, "properties"), {
        ...form,
        price: Number(form.price),
        interested: 0,
        createdAt: new Date(),
      });

      alert("Propiedad creada correctamente");
      router.push("/properties");
    } catch (error) {
      console.error(error);
      alert("Error al crear propiedad");
    }
  };

  return (
    <main className="p-10">
      <h1 className="text-3xl font-bold mb-6">Nueva propiedad</h1>

      <form onSubmit={handleSubmit} className="space-y-4 max-w-xl">
        <input
          name="title"
          placeholder="Título"
          className="w-full border p-3 rounded"
          onChange={handleChange}
        />

        <input
          name="type"
          placeholder="Tipo (Casa, Cabaña...)"
          className="w-full border p-3 rounded"
          onChange={handleChange}
        />

        <input
          name="location"
          placeholder="Ubicación"
          className="w-full border p-3 rounded"
          onChange={handleChange}
        />

        <input
          name="price"
          placeholder="Precio"
          type="number"
          className="w-full border p-3 rounded"
          onChange={handleChange}
        />

        <select
          name="status"
          className="w-full border p-3 rounded"
          onChange={handleChange}
        >
          <option>Disponible</option>
          <option>Reservado</option>
          <option>Vendido</option>
          <option>Visitas</option>
        </select>

        <button className="bg-[#8bb58f] text-white px-6 py-3 rounded">
          Guardar
        </button>
      </form>
    </main>
  );
}