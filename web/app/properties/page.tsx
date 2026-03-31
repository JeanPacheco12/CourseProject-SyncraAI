"use client";

import Image from "next/image";
import { useEffect, useState } from "react";
import { collection, deleteDoc, doc, getDocs } from "firebase/firestore";
import { db } from "@/lib/firebase";

type Property = {
  id: string;
  title: string;
  type: string;
  location: string;
  price: number;
  status: string;
  interested: number;
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

export default function PropertiesPage() {
  const [properties, setProperties] = useState<Property[]>([]);
  const [searchTerm, setSearchTerm] = useState("");
  const [statusFilter, setStatusFilter] = useState("Todos");
  const [locationFilter, setLocationFilter] = useState("Todas");
  const [priceOrder, setPriceOrder] = useState("default");

  const fetchProperties = async () => {
    try {
      const snapshot = await getDocs(collection(db, "properties"));

      const data: Property[] = snapshot.docs.map((docItem) => ({
        id: docItem.id,
        ...(docItem.data() as Omit<Property, "id">),
      }));

      setProperties(data);
    } catch (error) {
      console.error("Error al obtener propiedades:", error);
    }
  };

  const handleDelete = async (id: string, title: string) => {
    const confirmed = window.confirm(
      `¿Seguro que deseas eliminar la propiedad "${title}"?`
    );

    if (!confirmed) return;

    try {
      await deleteDoc(doc(db, "properties", id));
      await fetchProperties();
      alert("Propiedad eliminada correctamente");
    } catch (error) {
      console.error("Error al eliminar propiedad:", error);
      alert("No se pudo eliminar la propiedad");
    }
  };
  useEffect(() => {
    fetchProperties();
  }, []);

  const locationOptions = Array.from(
    new Set(properties.map((property) => property.location).filter(Boolean))
  );
  const filteredProperties = [...properties]
    .filter((property) => {
      const term = searchTerm.trim().toLowerCase();

      const matchesSearch =
        term === "" ||
        property.title.toLowerCase().includes(term) ||
        property.type.toLowerCase().includes(term) ||
        property.location.toLowerCase().includes(term);

      const matchesStatus =
        statusFilter === "Todos" || property.status === statusFilter;

      const matchesLocation =
        locationFilter === "Todas" || property.location === locationFilter;

      return matchesSearch && matchesStatus && matchesLocation;
    })
    .sort((a, b) => {
      if (priceOrder === "asc") return a.price - b.price;
      if (priceOrder === "desc") return b.price - a.price;
      return 0;
    });
  const clearFilters = () => {
    setSearchTerm("");
    setStatusFilter("Todos");
    setLocationFilter("Todas");
    setPriceOrder("default");
  };
    

  return (
    <main className="min-h-screen bg-[#f6f7fb] text-slate-800">
      <div className="flex min-h-screen">
        <aside className="hidden w-[290px] shrink-0 border-r border-slate-200 bg-white lg:flex lg:flex-col">
          <div className="border-b border-slate-100 px-8 py-10">
            <Image
              src="/logo-syncra.png"
              alt="Syncra Estate AI"
              width={190}
              height={90}
              className="h-auto w-auto"
            />
          </div>

          <nav className="flex-1 space-y-2 px-4 py-6">
            <a
              href="/dashboard"
              className="flex items-center gap-4 rounded-2xl px-5 py-4 text-[18px] text-slate-600 transition hover:bg-slate-50"
            >
              <span className="text-xl text-emerald-700">⌂</span>
              <span>Dashboard</span>
            </a>

            <a
              href="/properties"
              className="flex items-center gap-4 rounded-2xl bg-slate-50 px-5 py-4 text-[18px] font-medium text-slate-800"
            >
              <span className="text-xl text-emerald-700">⊞</span>
              <span>Inmuebles</span>
            </a>

            <a
              href="#"
              className="flex items-center gap-4 rounded-2xl px-5 py-4 text-[18px] text-slate-600 transition hover:bg-slate-50"
            >
              <span className="text-xl">◌</span>
              <span>Contactos</span>
            </a>

            <a
              href="#"
              className="flex items-center gap-4 rounded-2xl px-5 py-4 text-[18px] text-slate-600 transition hover:bg-slate-50"
            >
              <span className="text-xl">☷</span>
              <span>Citas</span>
            </a>

            <a
              href="#"
              className="flex items-center gap-4 rounded-2xl px-5 py-4 text-[18px] text-slate-600 transition hover:bg-slate-50"
            >
              <span className="text-xl">↗</span>
              <span>Reportes</span>
            </a>

            <a
              href="#"
              className="flex items-center gap-4 rounded-2xl px-5 py-4 text-[18px] text-slate-600 transition hover:bg-slate-50"
            >
              <span className="text-xl">⚙</span>
              <span>Ajustes</span>
            </a>
          </nav>

          <div className="px-4 py-6">
            <a
              href="#"
              className="flex items-center justify-between rounded-2xl px-5 py-4 text-[18px] text-slate-600 transition hover:bg-slate-50"
            >
              <div className="flex items-center gap-4">
                <span className="text-xl">⚙</span>
                <span>Ajustes</span>
              </div>
              <span>⌄</span>
            </a>
          </div>
        </aside>

        <section className="flex-1">
          <header className="border-b border-slate-200 bg-[#f6f7fb] px-8 py-7">
            <div className="flex flex-col gap-5 xl:flex-row xl:items-start xl:justify-between">
              <div>
                <h1 className="text-5xl font-semibold tracking-tight text-slate-800">
                  Inmuebles
                </h1>
                <p className="mt-3 text-xl text-slate-500">
                  Administra las propiedades disponibles y su estado actual.
                </p>
              </div>

              <div className="flex items-center gap-5 self-start">
                <div className="hidden h-14 w-[340px] items-center rounded-2xl border border-slate-200 bg-white px-5 md:flex">
                  <span className="mr-3 text-xl text-slate-400">⌕</span>
                  <input
                    type="text"
                    placeholder="Buscar..."
                    className="w-full bg-transparent text-[18px] outline-none placeholder:text-slate-400"
                  />
                </div>

                <button className="relative text-2xl text-slate-400">
                  🔔
                  <span className="absolute right-0 top-0 h-3 w-3 rounded-full bg-emerald-500 ring-2 ring-[#f6f7fb]" />
                </button>

                <div className="h-14 w-14 overflow-hidden rounded-full bg-slate-200">
                  <Image
                    src="/google.png"
                    alt="Avatar"
                    width={56}
                    height={56}
                    className="h-full w-full object-cover"
                  />
                </div>
              </div>
            </div>
          </header>

          <div className="px-8 py-6">
            <div className="mb-6 flex flex-col gap-4 xl:flex-row">
              <div className="flex h-14 flex-1 items-center rounded-2xl border border-slate-200 bg-white px-5">
                <span className="mr-3 text-xl text-slate-400">⌕</span>
                <input
                  type="text"
                  placeholder="Buscar propiedad..."
                  value={searchTerm}
                  onChange={(e) => setSearchTerm(e.target.value)}
                  className="w-full bg-transparent text-[18px] outline-none placeholder:text-slate-400"
                />
              </div>

              <div className="flex h-14 min-w-[250px] items-center rounded-2xl border border-slate-200 bg-white px-5">
                <select
                  value={statusFilter}
                  onChange={(e) => setStatusFilter(e.target.value)}
                  className="w-full bg-transparent text-[18px] text-slate-700 outline-none"
                >
                  <option value="Todos">Estado: Todos</option>
                  <option value="Disponible">Disponible</option>
                  <option value="Reservado">Reservado</option>
                  <option value="Vendido">Vendido</option>
                  <option value="Visitas">Visitas</option>
                </select>
              </div>

              <div className="flex h-14 min-w-[250px] items-center rounded-2xl border border-slate-200 bg-white px-5">
                <select
                  value={locationFilter}
                  onChange={(e) => setLocationFilter(e.target.value)}
                  className="w-full bg-transparent text-[18px] text-slate-700 outline-none"
                >
                  <option value="Todas">Ubicación: Todas</option>
                  {locationOptions.map((location) => (
                    <option key={location} value={location}>
                      {location}
                    </option>
                  ))}
                </select>
              </div>
              <div className="flex h-14 min-w-[250px] items-center rounded-2xl border border-slate-200 bg-white px-5">
                <select
                  value={priceOrder}
                  onChange={(e) => setPriceOrder(e.target.value)}
                  className="w-full bg-transparent text-[18px] text-slate-700 outline-none"
                >
                  <option value="default">Precio: Sin orden</option>
                  <option value="asc">Precio: Menor a mayor</option>
                  <option value="desc">Precio: Mayor a menor</option>
                </select>
              </div>

              <a
                href="/properties/new"
                className="flex h-14 min-w-[250px] items-center justify-center rounded-2xl bg-[#8bb58f] px-8 text-[18px] font-semibold text-white hover:opacity-90"
              >
                + Nuevo inmueble
              </a>
            </div>

            <div className="overflow-hidden rounded-[26px] border border-slate-200 bg-white shadow-sm">
  <div className="flex items-center justify-between border-b border-slate-200 px-6 py-5 text-[18px] text-slate-400">
    <div className="flex items-center gap-4">
      <span>
        <span className="font-semibold text-slate-700">Propiedades</span>{" "}
        {filteredProperties.length} registradas
      </span>
    </div>

    <button
      onClick={clearFilters}
      className="font-medium text-slate-500 transition hover:text-slate-700"
    >
      Limpiar filtros
    </button>
  </div>

  {filteredProperties.length === 0 ? (
    <div className="flex flex-col items-center justify-center px-6 py-20 text-center">
      <div className="mb-5 flex h-20 w-20 items-center justify-center rounded-full bg-slate-100 text-4xl">
        🏠
      </div>

      <h2 className="text-2xl font-semibold text-slate-800">
        {properties.length === 0
          ? "Aún no hay propiedades registradas"
          : "No se encontraron propiedades"}
      </h2>

      <p className="mt-3 max-w-xl text-[17px] text-slate-500">
        {properties.length === 0
          ? "Empieza agregando tu primera propiedad para visualizarla aquí y administrarla dentro del sistema."
          : "Prueba cambiando la búsqueda o limpiando los filtros para ver más resultados."}
      </p>

      {properties.length === 0 && (
        <a
          href="/properties/new"
          className="mt-6 rounded-2xl bg-[#8bb58f] px-6 py-3 text-[17px] font-semibold text-white transition hover:opacity-90"
        >
          + Crear primera propiedad
        </a>
      )}
    </div>
  ) : (
    <>
      <div className="hidden overflow-x-auto lg:block">
        <table className="w-full min-w-[980px]">
          <thead className="bg-[#fafbfc]">
            <tr className="border-b border-slate-200 text-left text-[16px] text-slate-500">
              <th className="px-6 py-4 font-semibold">ID</th>
              <th className="px-6 py-4 font-semibold">Propiedad</th>
              <th className="px-6 py-4 font-semibold">Tipo</th>
              <th className="px-6 py-4 font-semibold">Ubicación</th>
              <th className="px-6 py-4 font-semibold">Precio</th>
              <th className="px-6 py-4 font-semibold">Estado</th>
              <th className="px-6 py-4 font-semibold">Interesados</th>
              <th className="px-6 py-4 font-semibold">Acciones</th>
            </tr>
          </thead>

          <tbody>
            {filteredProperties.map((property) => (
              <tr
                key={property.id}
                className="border-b border-slate-100 text-[17px] text-slate-700"
              >
                <td className="px-6 py-5 text-slate-500">{property.id}</td>

                <td className="px-6 py-5">
                  <div className="flex items-center gap-4">
                    <div className="relative h-[52px] w-[102px] overflow-hidden rounded-xl bg-slate-200">
                      <Image
                        src="/bg-login.png"
                        alt={property.title}
                        fill
                        className="object-cover"
                      />
                    </div>
                    <span className="font-medium text-slate-800">
                      {property.title}
                    </span>
                  </div>
                </td>

                <td className="px-6 py-5 text-slate-500">{property.type}</td>

                <td className="px-6 py-5 text-slate-500">
                  {property.location}
                </td>

                <td className="px-6 py-5 font-medium text-slate-800">
                  Q{property.price.toLocaleString()}
                </td>

                <td className="px-6 py-5">
                  <span
                    className={`inline-flex rounded-full px-4 py-2 text-[15px] font-semibold ${getStatusClasses(
                      property.status
                    )}`}
                  >
                    {property.status}
                  </span>
                </td>

                <td className="px-6 py-5 text-center text-slate-600">
                  {property.interested}
                </td>

                <td className="px-6 py-5">
                  <div className="flex items-center gap-6 text-slate-500">
                    <a
                      href={`/properties/${property.id}`}
                      className="transition hover:text-slate-800"
                    >
                      👁 Ver
                    </a>
                    <a
                      href={`/properties/edit/${property.id}`}
                      className="transition hover:text-slate-800"
                    >
                      ✎ Editar
                    </a>
                    <button
                      onClick={() => handleDelete(property.id, property.title)}
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
      </div>

      <div className="space-y-4 p-4 lg:hidden">
       {filteredProperties.map((property) => (
          <div
            key={property.id}
            className="rounded-2xl border border-slate-200 p-4"
          >
            <div className="flex gap-4">
              <div className="relative h-24 w-28 shrink-0 overflow-hidden rounded-xl bg-slate-200">
                <Image
                  src="/bg-login.png"
                  alt={property.title}
                  fill
                  className="object-cover"
                />
              </div>

              <div className="min-w-0 flex-1">
                <p className="text-sm text-slate-400">ID {property.id}</p>
                <h3 className="truncate text-lg font-semibold text-slate-800">
                  {property.title}
                </h3>
                <p className="mt-1 text-sm text-slate-500">
                  {property.location}
                </p>
                <p className="mt-1 text-sm text-slate-500">{property.type}</p>
                <p className="mt-2 font-semibold text-slate-800">
                  Q{property.price.toLocaleString()}
                </p>
                <span
                  className={`mt-3 inline-flex rounded-full px-3 py-1.5 text-sm font-semibold ${getStatusClasses(
                    property.status
                  )}`}
                >
                  {property.status}
                </span>
              </div>
            </div>

            <div className="mt-4 flex items-center justify-between text-sm text-slate-500">
              <span>Interesados: {property.interested}</span>
              <div className="flex gap-4">
                <a href={`/properties/${property.id}`}>Ver</a>
                <a href={`/properties/edit/${property.id}`}>Editar</a>
                <button
                  onClick={() => handleDelete(property.id, property.title)}
                  className="text-red-600"
                >
                  Eliminar
                </button>
              </div>
            </div>
          </div>
        ))}
      </div>

      <div className="flex items-center justify-end gap-3 px-6 py-5">
        <button className="text-lg text-slate-400">‹</button>
        <button className="flex h-10 w-10 items-center justify-center rounded-xl bg-[#8bb58f] font-semibold text-white">
          1
        </button>
        <button className="text-lg text-slate-400">›</button>
      </div>
    </>
  )}
</div>
          </div>
        </section>
      </div>
    </main>
  );
}