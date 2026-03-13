import Link from "next/link";
import Image from "next/image";

export default function SmartPitchPage() {
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
          </nav>
        </aside>

        {/* Main */}
        <section className="flex-1">
          <header className="border-b border-slate-200 bg-[#f6f7fb] px-8 py-7">
            <div className="flex items-start justify-between gap-6">
              <div>
                <p className="text-sm font-medium text-slate-400">
                  Cliente / Smart Pitch
                </p>
                <h1 className="mt-2 text-5xl font-semibold tracking-tight text-slate-800">
                  Smart Pitch
                </h1>
                <p className="mt-3 text-xl text-slate-500">
                  Genera un mensaje persuasivo basado en el perfil del cliente y
                  la propiedad seleccionada.
                </p>
              </div>

              <Link
                href="/client-profile"
                className="rounded-2xl border border-slate-200 bg-white px-5 py-3 text-[16px] font-medium text-slate-700 transition hover:bg-slate-50"
              >
                ← Volver al perfil
              </Link>
            </div>
          </header>

          <div className="px-8 py-6">
            <div className="grid gap-6 xl:grid-cols-[0.95fr_1.25fr]">
              {/* Panel izquierdo */}
              <div className="space-y-6">
                <div className="rounded-[28px] border border-slate-200 bg-white p-7 shadow-sm">
                  <h2 className="text-2xl font-semibold text-slate-800">
                    Cliente seleccionado
                  </h2>

                  <div className="mt-6 flex items-center gap-4">
                    <div className="h-16 w-16 overflow-hidden rounded-full bg-slate-200">
                      <Image
                        src="/google.png"
                        alt="Cliente"
                        width={64}
                        height={64}
                        className="h-full w-full object-cover"
                      />
                    </div>

                    <div>
                      <p className="text-xl font-semibold text-slate-800">
                        José Martínez
                      </p>
                      <p className="text-[16px] text-slate-500">
                        Comprador · Presupuesto Q8,000/mes
                      </p>
                    </div>
                  </div>

                  <div className="mt-6 rounded-2xl bg-slate-50 p-5">
                    <p className="text-sm text-slate-400">Necesidad principal</p>
                    <p className="mt-2 text-[16px] leading-7 text-slate-700">
                      Busca una casa moderna en una zona segura, con 3
                      habitaciones y 2 parqueos, dentro de su presupuesto.
                    </p>
                  </div>
                </div>

                <div className="rounded-[28px] border border-slate-200 bg-white p-7 shadow-sm">
                  <h2 className="text-2xl font-semibold text-slate-800">
                    Propiedad elegida
                  </h2>

                  <div className="mt-6 overflow-hidden rounded-3xl border border-slate-200">
                    <div className="relative h-56 w-full">
                      <Image
                        src="/bg-login.png"
                        alt="Propiedad"
                        fill
                        className="object-cover"
                      />
                    </div>

                    <div className="p-5">
                      <h3 className="text-xl font-semibold text-slate-800">
                        Casa moderna en Bulevar Austriaco
                      </h3>
                      <p className="mt-1 text-[16px] text-slate-500">
                        Zona 16 · 3 habitaciones · 2 parqueos
                      </p>
                      <p className="mt-3 text-lg font-bold text-slate-800">
                        Q8,000/mes
                      </p>
                    </div>
                  </div>
                </div>

                <div className="rounded-[28px] border border-slate-200 bg-white p-7 shadow-sm">
                  <h2 className="text-2xl font-semibold text-slate-800">
                    Instrucciones para IA
                  </h2>

                  <textarea
                    placeholder="Escribe detalles extra para personalizar el mensaje..."
                    className="mt-5 h-36 w-full resize-none rounded-2xl border border-slate-200 bg-slate-50 px-4 py-4 text-[16px] text-slate-700 outline-none placeholder:text-slate-400"
                    defaultValue="Resaltar que la propiedad encaja con su presupuesto, la ubicación y el espacio para familia."
                  />

                  <button className="mt-5 w-full rounded-2xl bg-[#8bb58f] px-5 py-4 text-[17px] font-semibold text-white transition hover:opacity-90">
                    Generar mensaje
                  </button>
                </div>
              </div>

              {/* Panel derecho */}
              <div className="rounded-[28px] border border-slate-200 bg-white p-7 shadow-sm">
                <div className="flex items-center justify-between gap-4">
                  <div>
                    <h2 className="text-2xl font-semibold text-slate-800">
                      Resultado generado
                    </h2>
                    <p className="mt-2 text-[17px] text-slate-500">
                      Vista previa del mensaje persuasivo para enviar al cliente.
                    </p>
                  </div>

                  <span className="rounded-full bg-violet-100 px-4 py-2 text-sm font-semibold text-violet-700">
                    Gemini API
                  </span>
                </div>

                <div className="mt-8 flex min-h-[520px] flex-col justify-between rounded-[32px] bg-[#f3f5f9] p-8">
                  <div>
                    <div className="mx-auto flex h-20 w-20 items-center justify-center rounded-full bg-white shadow-[0_0_40px_rgba(168,85,247,0.18)]">
                      <span className="text-3xl text-violet-500">✦</span>
                    </div>

                    <h3 className="mt-8 text-center text-4xl font-semibold text-slate-800">
                      Smart Pitch <span className="text-violet-500">listo ✨</span>
                    </h3>

                    <div className="mx-auto mt-8 max-w-3xl rounded-[28px] bg-[#dbe4ec] px-8 py-8 text-center">
                      <p className="text-[28px] leading-[1.8] text-slate-700">
                        Hola Carlos. Encontré una opción que encaja perfecto con
                        lo que buscas. Es una casa moderna en Bulevar Austriaco,
                        Zona 16. Tiene las 3 habitaciones que necesitas y 2
                        parqueos por Q8,000/mes, justo dentro de tu presupuesto.
                        ¿Te gustaría que agendemos una visita para conocerla?
                      </p>
                    </div>
                  </div>

                  <div className="mt-8 flex flex-col gap-4 md:flex-row">
                    <button className="flex-1 rounded-[24px] bg-[#8cc63f] px-6 py-5 text-xl font-semibold text-white transition hover:opacity-90">
                      Enviar por WhatsApp
                    </button>

                    <button className="rounded-[24px] bg-[#244d66] px-8 py-5 text-xl font-semibold text-white transition hover:opacity-90">
                      Regenerar
                    </button>
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