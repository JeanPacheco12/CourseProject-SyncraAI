"use client";

import { useEffect, useState } from "react";
import { db } from "@/lib/firebase";
import { doc, getDoc, updateDoc } from "firebase/firestore";
import { useParams, useRouter } from "next/navigation";

export default function EditPropertyPage() {
  const router = useRouter();
  const params = useParams();
  const id = params.id as string;

  const [loading, setLoading] = useState(true);
  const [form, setForm] = useState({
    title: "",
    type: "",
    location: "",
    price: "",
    status: "Disponible",
    interested: 0,
  });

  useEffect(() => {
    const fetchProperty = async () => {
      try {
        const ref = doc(db, "properties", id);
        const snapshot = await getDoc(ref);

        if (!snapshot.exists()) {
          alert("La propiedad no existe");
          router.push("/properties");
          return;
        }

        const data = snapshot.data();

        setForm({
          title: data.title || "",
          type: data.type || "",
          location: data.location || "",
          price: data.price ? String(data.price) : "",
          status: data.status || "Disponible",
          interested: data.interested || 0,
        });
      } catch (error) {
        console.error("Error al cargar propiedad:", error);
        alert("No se pudo cargar la propiedad");
      } finally {
        setLoading(false);
      }
    };

    if (id) fetchProperty();
  }, [id, router]);

  const handleChange = (
    e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>
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

    if (!title || !type || !location || !form.price) {
      alert("Título, tipo, ubicación y precio son obligatorios");
      return;
    }

    if (Number.isNaN(price) || price <= 0) {
      alert("El precio debe ser un número mayor a 0");
      return;
    }

    try {
      await updateDoc(doc(db, "properties", id), {
        title,
        type,
        location,
        price,
        status: form.status,
      });

      alert("Propiedad actualizada correctamente");
      router.push("/properties");
    } catch (error) {
      console.error("Error al actualizar propiedad:", error);
      alert("No se pudo actualizar la propiedad");
    }
  };

  if (loading) {
    return <main className="p-10">Cargando propiedad...</main>;
  }

  return (
    <main className="p-10">
      <h1 className="mb-6 text-3xl font-bold">Editar propiedad</h1>

      <form onSubmit={handleSubmit} className="max-w-xl space-y-4">
        <input
          name="title"
          placeholder="Título"
          value={form.title}
          onChange={handleChange}
          className="w-full rounded border p-3"
        />

        <input
          name="type"
          placeholder="Tipo"
          value={form.type}
          onChange={handleChange}
          className="w-full rounded border p-3"
        />

        <input
          name="location"
          placeholder="Ubicación"
          value={form.location}
          onChange={handleChange}
          className="w-full rounded border p-3"
        />

        <input
          name="price"
          type="number"
          placeholder="Precio"
          value={form.price}
          onChange={handleChange}
          className="w-full rounded border p-3"
        />

        <select
          name="status"
          value={form.status}
          onChange={handleChange}
          className="w-full rounded border p-3"
        >
          <option>Disponible</option>
          <option>Reservado</option>
          <option>Vendido</option>
          <option>Visitas</option>
        </select>

        <div className="flex gap-3">
          <button
            type="submit"
            className="rounded bg-[#8bb58f] px-6 py-3 text-white"
          >
            Guardar cambios
          </button>

          <button
            type="button"
            onClick={() => router.push("/properties")}
            className="rounded border px-6 py-3"
          >
            Cancelar
          </button>
        </div>
      </form>
    </main>
  );
}