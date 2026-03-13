import Image from "next/image";
import Link from "next/link";

const interestedProperties = [
  {
    id: 1,
    name: "Casa en Polanco",
    zone: "Polanco",
    price: "$1,250,000",
    image: "/bg-login.png",
    status: "Disponible",
  },
  {
    id: 2,
    name: "Penthouse en Santa Fe",
    zone: "Santa Fe",
    price: "$2,200,000",
    image: "/bg-login.png",
    status: "Reservado",
  },
  {
    id: 3,
    name: "Villa en Tulum",
    zone: "Tulum",
    price: "$3,350,000",
    image: "/bg-login.png",
    status: "Disponible",
  },
];

const recentActivity = [
  {
    title: "Visita programada",
    description: "Recorrido agendado para Casa en Polanco.",
    date: "11 mar. 2026 - 10:00 AM",
  },
  {
    title: "Llamada con asesor",
    description: "Se habló sobre presupuesto y zonas de interés.",
    date: "10 mar. 2026 - 4:30 PM",
  },
  {
    title: "Cliente agregado",
    description: "Nuevo cliente registrado en el sistema.",
    date: "08 mar. 2026 - 9:15 AM",
  },
];

function getStatusClasses(status: string) {
  switch (status) {
    case "Disponible":
      return "bg-emerald-100 text-emerald-700";
    case "Reservado":
      return "bg-amber-100 text-amber-700";
    case "Vendido":
      return "bg-slate-200 text-slate-600";
    default:
      return "bg-slate-100 text-slate-600";
  }
}

export default function ClientProfilePage() {
  return (
    <main className="min-h-screen bg-[#f6f7fb] text-slate-800">
      <div className="flex min-h-screen">
        {/* Sidebar */}
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
              className="flex items-center gap-4 rounded-2xl px-5 py-4 text-[18px] text-slate-600 transition hover:bg-slate-50"
            >
              <span className="text-xl text-emerald-700">⊞</span>
              <span>Inmuebles</span>
            </a>

            <a
              href="/client-profile"
              className="flex items-center gap-4 rounded-2xl bg-slate-50 px-5 py-4 text-[18px] font-medium text-slate-800"
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

        {/* Main content */}
        <section className="flex-1">
          {/* Header */}
          <header className="border-b border-slate-200 bg-[#f6f7fb] px-8 py-7">
            <div className="flex flex-col gap-5 xl:flex-row xl:items-start xl:justify-between">
              <div>
                <h1 className="text-5xl font-semibold tracking-tight text-slate-800">
                  Perfil del cliente
                </h1>
                <p className="mt-3 text-xl text-slate-500">
                  Información detallada del cliente y sus propiedades de interés.
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

          {/* Body */}
          <div className="px-8 py-6">
            <div className="grid gap-6 xl:grid-cols-[0.95fr_1.45fr]">
              {/* Left column */}
              <div className="space-y-6">
                <div className="rounded-[28px] border border-slate-200 bg-white p-7 shadow-sm">
                  <div className="flex flex-col items-center text-center">
                    <div className="h-28 w-28 overflow-hidden rounded-full bg-slate-200">
                      <Image
                        src="/google.png"
                        alt="Cliente"
                        width={112}
                        height={112}
                        className="h-full w-full object-cover"
                      />
                    </div>

                    <h2 className="mt-5 text-3xl font-semibold text-slate-800">
                      José Martínez
                    </h2>
                    <p className="mt-2 text-lg text-slate-500">Comprador</p>

                    <span className="mt-4 rounded-full bg-emerald-100 px-4 py-2 text-sm font-semibold text-emerald-700">
                      Interesado
                    </span>
                  </div>

                  <div className="mt-8 space-y-5 text-[17px]">
                    <div>
                      <p className="text-sm text-slate-400">Correo</p>
                      <p className="mt-1 font-medium text-slate-700">
                        jose@email.com
                      </p>
                    </div>

                    <div>
                      <p className="text-sm text-slate-400">Teléfono</p>
                      <p className="mt-1 font-medium text-slate-700">
                        +502 5555-5555
                      </p>
                    </div>

                    <div>
                      <p className="text-sm text-slate-400">Ubicación</p>
                      <p className="mt-1 font-medium text-slate-700">
                        Ciudad de Guatemala
                      </p>
                    </div>

                    <div>
                      <p className="text-sm text-slate-400">Presupuesto</p>
                      <p className="mt-1 font-medium text-slate-700">
                        $800,000 - $1,200,000
                      </p>
                    </div>

                    <div>
                      <p className="text-sm text-slate-400">Tipo de propiedad</p>
                      <p className="mt-1 font-medium text-slate-700">
                        Casas y apartamentos
                      </p>
                    </div>
                  </div>

                  <div className="mt-8 space-y-3">
                    <button className="w-full rounded-2xl bg-[#8bb58f] px-5 py-4 text-[17px] font-semibold text-white transition hover:opacity-90">
                        Editar perfil
                    </button>

                    <button className="w-full rounded-2xl border border-slate-200 bg-white px-5 py-4 text-[17px] font-semibold text-slate-700 transition hover:bg-slate-50">
                        Contactar cliente
                    </button>

                    <Link
                        href="/smart_pitch"
                        className="block w-full rounded-2xl bg-[#244d66] px-5 py-4 text-center text-[17px] font-semibold text-white transition hover:opacity-90"
                    >
                        Generar Smart Pitch
                    </Link>
                    </div>
                </div>

                <div className="rounded-[28px] border border-slate-200 bg-white p-7 shadow-sm">
                  <h3 className="text-2xl font-semibold text-slate-800">
                    Notas del asesor
                  </h3>

                  <div className="mt-5 rounded-2xl bg-slate-50 p-5 text-[16px] leading-8 text-slate-600">
                    Cliente interesado en propiedades modernas, con buena
                    ubicación y cercanas a centros comerciales. Busca mudarse en
                    los próximos meses y prioriza seguridad, parqueo y espacios
                    amplios para familia.
                  </div>
                </div>
              </div>

              {/* Right column */}
              <div className="space-y-6">
                <div className="rounded-[28px] border border-slate-200 bg-white p-7 shadow-sm">
                  <div className="flex items-center justify-between gap-4">
                    <div>
                      <h3 className="text-2xl font-semibold text-slate-800">
                        Propiedades de interés
                      </h3>
                      <p className="mt-2 text-[17px] text-slate-500">
                        Inmuebles que el cliente ha consultado recientemente.
                      </p>
                    </div>

                    <button className="rounded-2xl border border-slate-200 bg-white px-5 py-3 text-[16px] font-medium text-slate-700 transition hover:bg-slate-50">
                      Ver todas
                    </button>
                  </div>

                  <div className="mt-6 space-y-4">
                    {interestedProperties.map((property) => (
                      <div
                        key={property.id}
                        className="flex flex-col gap-4 rounded-2xl border border-slate-200 p-4 md:flex-row md:items-center"
                      >
                        <div className="relative h-28 w-full overflow-hidden rounded-2xl bg-slate-200 md:h-24 md:w-40">
                          <Image
                            src={property.image}
                            alt={property.name}
                            fill
                            className="object-cover"
                          />
                        </div>

                        <div className="min-w-0 flex-1">
                          <h4 className="text-xl font-semibold text-slate-800">
                            {property.name}
                          </h4>
                          <p className="mt-1 text-[16px] text-slate-500">
                            {property.zone}
                          </p>
                          <p className="mt-2 text-[17px] font-semibold text-slate-800">
                            {property.price}
                          </p>
                        </div>

                        <div className="flex flex-col items-start gap-3 md:items-end">
                          <span
                            className={`rounded-full px-4 py-2 text-sm font-semibold ${getStatusClasses(
                              property.status
                            )}`}
                          >
                            {property.status}
                          </span>

                          <button className="rounded-xl bg-slate-100 px-4 py-2 text-sm font-medium text-slate-700 transition hover:bg-slate-200">
                            Ver detalle
                          </button>
                        </div>
                      </div>
                    ))}
                  </div>
                </div>

                <div className="rounded-[28px] border border-slate-200 bg-white p-7 shadow-sm">
                  <h3 className="text-2xl font-semibold text-slate-800">
                    Actividad reciente
                  </h3>
                  <p className="mt-2 text-[17px] text-slate-500">
                    Historial de interacciones y seguimiento del cliente.
                  </p>

                  <div className="mt-6 space-y-4">
                    {recentActivity.map((item, index) => (
                      <div
                        key={`${item.title}-${index}`}
                        className="rounded-2xl bg-slate-50 p-5"
                      >
                        <div className="flex flex-col gap-2 md:flex-row md:items-start md:justify-between">
                          <div>
                            <h4 className="text-lg font-semibold text-slate-800">
                              {item.title}
                            </h4>
                            <p className="mt-1 text-[16px] text-slate-600">
                              {item.description}
                            </p>
                          </div>

                          <p className="text-sm text-slate-400">{item.date}</p>
                        </div>
                      </div>
                    ))}
                  </div>
                </div>

                <div className="rounded-[28px] border border-slate-200 bg-white p-7 shadow-sm">
                  <h3 className="text-2xl font-semibold text-slate-800">
                    Resumen rápido
                  </h3>

                  <div className="mt-6 grid gap-4 sm:grid-cols-3">
                    <div className="rounded-2xl bg-slate-50 p-5">
                      <p className="text-sm text-slate-400">Interés activo</p>
                      <p className="mt-2 text-2xl font-bold text-slate-800">
                        3
                      </p>
                    </div>

                    <div className="rounded-2xl bg-slate-50 p-5">
                      <p className="text-sm text-slate-400">Visitas</p>
                      <p className="mt-2 text-2xl font-bold text-slate-800">
                        2
                      </p>
                    </div>

                    <div className="rounded-2xl bg-slate-50 p-5">
                      <p className="text-sm text-slate-400">Último contacto</p>
                      <p className="mt-2 text-lg font-bold text-slate-800">
                        Hoy
                      </p>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </section>
      </div>
    </main>
  );
}