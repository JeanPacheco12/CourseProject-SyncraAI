"use client";

import { useState } from "react";
import { addDoc, collection, getDocs } from "firebase/firestore";
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
    imageUrl: "",
    descripcion: "",
  });

  const handleChange = (
    e: React.ChangeEvent<
      HTMLInputElement | HTMLSelectElement | HTMLTextAreaElement
    >
  ) => {
    setForm({
      ...form,
      [e.target.name]: e.target.value,
    });
  };

  const handleSubmit = async (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();

    const title = form.title.trim();
    const type = form.type.trim();
    const location = form.location.trim();
    const price = Number(form.price);
    const imageUrl = form.imageUrl.trim();
    const descripcion = form.descripcion.trim();

    if (!title || !type || !location || !form.price) {
      alert("Título, tipo, ubicación y precio son obligatorios");
      return;
    }

    if (Number.isNaN(price) || price <= 0) {
      alert("El precio debe ser un número mayor a 0");
      return;
    }

    try {
      const snapshot = await getDocs(collection(db, "properties"));
      const nextId = `prop_${snapshot.size + 1}`;

      await addDoc(collection(db, "properties"), {
        id: nextId,
        title,
        type,
        location,
        price,
        descripcion,
        status: form.status,
        interested: 0,
        createdAt: new Date(),

        images: imageUrl ? [imageUrl] : [],
        imageGalleryUrlList: imageUrl ? [imageUrl] : [],
      });

      alert("Propiedad creada correctamente");
      router.push("/properties");
    } catch (error) {
      console.error("Error al crear propiedad:", error);
      alert("Error al crear propiedad");
    }
  };

  return (
    <main className="p-10">
      <h1 className="mb-6 text-3xl font-bold">Nueva propiedad</h1>
       <button
          onClick={() => router.push("/properties")}
          className="mb-6 rounded-xl border px-4 py-2"
        >
          ← Volver
        </button>

      <form onSubmit={handleSubmit} className="max-w-xl space-y-4">
        <input
          name="title"
          placeholder="Título"
          className="w-full rounded border p-3"
          value={form.title}
          onChange={handleChange}
        />

        <input
          name="type"
          placeholder="Tipo (Casa, Cabaña...)"
          className="w-full rounded border p-3"
          value={form.type}
          onChange={handleChange}
        />

        <input
          name="location"
          placeholder="Ubicación"
          className="w-full rounded border p-3"
          value={form.location}
          onChange={handleChange}
        />

        <input
          name="price"
          placeholder="Precio"
          type="number"
          className="w-full rounded border p-3"
          value={form.price}
          onChange={handleChange}
        />

        <input
          name="imageUrl"
          placeholder="URL de imagen principal"
          className="w-full rounded border p-3"
          value={form.imageUrl}
          onChange={handleChange}
        />

        <textarea
          name="descripcion"
          placeholder="Descripción de la propiedad"
          className="w-full rounded border p-3 min-h-[120px]"
          value={form.descripcion}
          onChange={handleChange}
        />

        <select
          name="status"
          className="w-full rounded border p-3"
          value={form.status}
          onChange={handleChange}
        >
          <option>Disponible</option>
          <option>Reservado</option>
          <option>Vendido</option>
          <option>Visitas</option>
          <option>Pendientes</option>
          <option>Disponibles</option>
        </select>

        <button className="rounded bg-[#8bb58f] px-6 py-3 text-white">
          Guardar
        </button>
      </form>
    </main>
  );
}