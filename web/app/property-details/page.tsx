import Image from "next/image";

const features = [
  "4 habitaciones",
  "3 baños",
  "2 parqueos",
  "320 m²",
  "Piscina",
  "Jardín",
];

const gallery = [
  "/bg-login.png",
  "/bg-login.png",
  "/bg-login.png",
];

export default function PropertyDetailsPage() {
  return (
    <main className="min-h-screen bg-[#f6f7fb] text-slate-800">
      <div className="flex min-h-screen">
        {/* Sidebar */}
        <aside className="hidden w-[260px] shrink-0 border-r border-slate-200 bg-white lg:flex lg:flex-col">
          <div className="flex items-center justify-center border-b border-slate-100 px-6 py-8">
            <div className="text-center">
              <Image
                src="/logo-syncra.png"
                alt="Syncra Estate AI"
                width={150}
                height={80}
                className="mx-auto h-auto w-auto"
              />
            </div>
          </div>

          <nav className="flex-1 space-y-2 px-4 py-6">
            <a
              href="/dashboard"
              className="flex items-center gap-3 rounded-xl px-4 py-3 text-[18px] text-slate-600 transition hover:bg-slate-50"
            >
              <span>⌂</span>
              <span>Dashboard</span>
            </a>

            <a
              href="#"
              className="flex items-center gap-3 rounded-xl bg-[#eef6ef] px-4 py-3 text-[18px] font-medium text-[#5d8f68]"
            >
              <span>▦</span>
              <span>Inmuebles</span>
            </a>

            <a
              href="#"
              className="flex items-center gap-3 rounded-xl px-4 py-3 text-[18px] text-slate-600 transition hover:bg-slate-50"
            >
              <span>◌</span>
              <span>Contactos</span>
            </a>

            <a
              href="#"
              className="flex items-center gap-3 rounded-xl px-4 py-3 text-[18px] text-slate-600 transition hover:bg-slate-50"
            >
              <span>☷</span>
              <span>Citas</span>
            </a>

            <a
              href="#"
              className="flex items-center gap-3 rounded-xl px-4 py-3 text-[18px] text-slate-600 transition hover:bg-slate-50"
            >
              <span>↗</span>
              <span>Reportes</span>
            </a>

            <a
              href="#"
              className="flex items-center gap-3 rounded-xl px-4 py-3 text-[18px] text-slate-600 transition hover:bg-slate-50"
            >
              <span>⚙</span>
              <span>Ajustes</span>
            </a>
          </nav>

          <div className="border-t border-slate-100 px-4 py-5">
            <a
              href="#"
              className="flex items-center gap-3 rounded-xl px-4 py-3 text-[18px] text-slate-600 transition hover:bg-slate-50"
            >
              <span>⚙</span>
              <span>Ajustes</span>
            </a>
          </div>
        </aside>

        {/* Content */}
        <section className="flex-1">
          {/* Topbar */}
          <header className="flex flex-col gap-4 border-b border-slate-200 bg-[#f6f7fb] px-6 py-6 md:flex-row md:items-center md:justify-between">
            <div>
              <h1 className="text-4xl font-semibold tracking-tight text-slate-800">
                Detalle de propiedad
              </h1>
              <p className="mt-2 text-lg text-slate-500">
                Información completa del inmueble seleccionado.
              </p>
            </div>

            <div className="flex items-center gap-4">
              <div className="hidden h-12 w-[300px] items-center rounded-2xl border border-slate-200 bg-white px-4 md:flex">
                <span className="mr-3 text-slate-400">⌕</span>
                <input
                  type="text"
                  placeholder="Buscar..."
                  className="w-full bg-transparent text-[16px] outline-none placeholder:text-slate-400"
                />
              </div>

              <button className="text-xl text-slate-400">🔔</button>

              <div className="h-12 w-12 overflow-hidden rounded-full bg-slate-200">
                <Image
                  src="/google.png"
                  alt="Avatar"
                  width={48}
                  height={48}
                  className="h-full w-full object-cover"
                />
              </div>
            </div>
          </header>

          {/* Body */}
          <div className="p-6">
            <div className="grid gap-6 xl:grid-cols-[1.6fr_0.9fr]">
              {/* Left column */}
              <div className="space-y-6">
                {/* Hero image */}
                <div className="overflow-hidden rounded-[28px] border border-slate-200 bg-white shadow-sm">
                  <div className="relative h-[340px] w-full">
                    <Image
                      src="/bg-login.png"
                      alt="Propiedad principal"
                      fill
                      className="object-cover"
                    />
                  </div>
                </div>

                {/* Gallery */}
                <div className="grid gap-4 sm:grid-cols-3">
                  {gallery.map((image, index) => (
                    <div
                      key={index}
                      className="overflow-hidden rounded-[22px] border border-slate-200 bg-white shadow-sm"
                    >
                      <div className="relative h-[120px] w-full">
                        <Image
                          src={image}
                          alt={`Vista ${index + 1}`}
                          fill
                          className="object-cover"
                        />
                      </div>
                    </div>
                  ))}
                </div>

                {/* Description */}
                <div className="rounded-[28px] border border-slate-200 bg-white p-6 shadow-sm">
                  <h2 className="text-2xl font-semibold text-slate-800">
                    Descripción
                  </h2>
                  <p className="mt-4 text-[17px] leading-8 text-slate-500">
                    Casa moderna ubicada en una zona exclusiva, con espacios
                    amplios, excelente iluminación natural y acabados de alta
                    calidad. Ideal para familias que buscan comodidad, estilo y
                    una ubicación privilegiada cerca de servicios, comercios y
                    áreas verdes.
                  </p>
                </div>

                {/* Features */}
                <div className="rounded-[28px] border border-slate-200 bg-white p-6 shadow-sm">
                  <h2 className="text-2xl font-semibold text-slate-800">
                    Características
                  </h2>

                  <div className="mt-5 grid gap-4 sm:grid-cols-2 lg:grid-cols-3">
                    {features.map((feature) => (
                      <div
                        key={feature}
                        className="rounded-2xl bg-slate-50 px-4 py-4 text-[16px] font-medium text-slate-600"
                      >
                        {feature}
                      </div>
                    ))}
                  </div>
                </div>
              </div>

              {/* Right column */}
              <div className="space-y-6">
                {/* Summary card */}
                <div className="rounded-[28px] border border-slate-200 bg-white p-6 shadow-sm">
                  <div className="flex items-start justify-between gap-4">
                    <div>
                      <p className="text-sm font-medium uppercase tracking-[0.18em] text-slate-400">
                        Propiedad
                      </p>
                      <h2 className="mt-2 text-3xl font-semibold text-slate-800">
                        Casa en Polanco
                      </h2>
                      <p className="mt-2 text-[17px] text-slate-500">
                        Polanco, Ciudad de México
                      </p>
                    </div>

                    <span className="rounded-full bg-emerald-100 px-4 py-2 text-sm font-semibold text-emerald-700">
                      Disponible
                    </span>
                  </div>

                  <div className="mt-6">
                    <p className="text-sm text-slate-400">Precio</p>
                    <p className="mt-1 text-4xl font-bold text-slate-800">
                      $1,250,000
                    </p>
                  </div>

                  <div className="mt-6 grid grid-cols-2 gap-4">
                    <div className="rounded-2xl bg-slate-50 p-4">
                      <p className="text-sm text-slate-400">ID</p>
                      <p className="mt-1 text-lg font-semibold text-slate-700">
                        254
                      </p>
                    </div>

                    <div className="rounded-2xl bg-slate-50 p-4">
                      <p className="text-sm text-slate-400">Interesados</p>
                      <p className="mt-1 text-lg font-semibold text-slate-700">
                        5
                      </p>
                    </div>
                  </div>

                  <div className="mt-6 space-y-3">
                    <button className="w-full rounded-2xl bg-[#86b58a] px-5 py-4 text-[17px] font-semibold text-white transition hover:opacity-90">
                      Editar propiedad
                    </button>

                    <button className="w-full rounded-2xl border border-slate-200 bg-white px-5 py-4 text-[17px] font-semibold text-slate-700 transition hover:bg-slate-50">
                      Ver interesados
                    </button>
                  </div>
                </div>

                {/* Agent / owner card */}
                <div className="rounded-[28px] border border-slate-200 bg-white p-6 shadow-sm">
                  <h3 className="text-2xl font-semibold text-slate-800">
                    Asesor asignado
                  </h3>

                  <div className="mt-5 flex items-center gap-4">
                    <div className="h-16 w-16 overflow-hidden rounded-full bg-slate-200">
                      <Image
                        src="/google.png"
                        alt="Asesor"
                        width={64}
                        height={64}
                        className="h-full w-full object-cover"
                      />
                    </div>

                    <div>
                      <p className="text-xl font-semibold text-slate-800">
                        Rodrigo Pérez
                      </p>
                      <p className="text-[16px] text-slate-500">
                        Asesor inmobiliario
                      </p>
                    </div>
                  </div>

                  <div className="mt-6 space-y-4 text-[16px] text-slate-600">
                    <p>
                      <span className="font-semibold text-slate-800">
                        Correo:
                      </span>{" "}
                      rodrigo@syncra.com
                    </p>
                    <p>
                      <span className="font-semibold text-slate-800">
                        Teléfono:
                      </span>{" "}
                      +502 5555-5555
                    </p>
                  </div>
                </div>

                {/* Timeline */}
                <div className="rounded-[28px] border border-slate-200 bg-white p-6 shadow-sm">
                  <h3 className="text-2xl font-semibold text-slate-800">
                    Actividad reciente
                  </h3>

                  <div className="mt-5 space-y-4">
                    <div className="rounded-2xl bg-slate-50 p-4">
                      <p className="font-semibold text-slate-700">
                        Nueva visita programada
                      </p>
                      <p className="mt-1 text-sm text-slate-500">
                        12 mar. 2026 - 10:00 AM
                      </p>
                    </div>

                    <div className="rounded-2xl bg-slate-50 p-4">
                      <p className="font-semibold text-slate-700">
                        Cliente interesado agregado
                      </p>
                      <p className="mt-1 text-sm text-slate-500">
                        11 mar. 2026 - 4:20 PM
                      </p>
                    </div>

                    <div className="rounded-2xl bg-slate-50 p-4">
                      <p className="font-semibold text-slate-700">
                        Precio actualizado
                      </p>
                      <p className="mt-1 text-sm text-slate-500">
                        09 mar. 2026 - 1:15 PM
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