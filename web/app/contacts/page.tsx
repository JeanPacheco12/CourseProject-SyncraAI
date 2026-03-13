import Image from "next/image";

const contacts = [
  {
    id: 1,
    name: "José Martínez",
    email: "jose@email.com",
    phone: "+502 5555 5555",
    city: "Ciudad de Guatemala",
    status: "Interesado",
    image: "/google.png",
  },
  {
    id: 2,
    name: "Ana López",
    email: "ana@email.com",
    phone: "+502 7777 2222",
    city: "Antigua Guatemala",
    status: "Nuevo",
    image: "/google.png",
  },
  {
    id: 3,
    name: "Carlos Díaz",
    email: "carlos@email.com",
    phone: "+502 8888 1111",
    city: "Zona 10",
    status: "Negociando",
    image: "/google.png",
  },
  {
    id: 4,
    name: "María Torres",
    email: "maria@email.com",
    phone: "+502 4444 8888",
    city: "Zona 15",
    status: "Interesado",
    image: "/google.png",
  },
];

function getStatusClasses(status: string) {
  switch (status) {
    case "Nuevo":
      return "bg-blue-100 text-blue-700";
    case "Interesado":
      return "bg-emerald-100 text-emerald-700";
    case "Negociando":
      return "bg-amber-100 text-amber-700";
    default:
      return "bg-slate-100 text-slate-600";
  }
}

export default function ContactsPage() {
  return (
    <main className="min-h-screen bg-[#f6f7fb] text-slate-800">
      <div className="flex min-h-screen">
        {/* Sidebar */}
        <aside className="hidden w-[290px] shrink-0 border-r border-slate-200 bg-white lg:flex lg:flex-col">
          <div className="border-b border-slate-100 px-8 py-10">
            <Image
              src="/logo-syncra.png"
              alt="Syncra"
              width={190}
              height={90}
              className="h-auto w-auto"
            />
          </div>

          <nav className="flex-1 space-y-2 px-4 py-6">
            <a
              href="/dashboard"
              className="flex items-center gap-4 rounded-2xl px-5 py-4 text-[18px] text-slate-600 hover:bg-slate-50"
            >
              ⌂ Dashboard
            </a>

            <a
              href="/properties"
              className="flex items-center gap-4 rounded-2xl px-5 py-4 text-[18px] text-slate-600 hover:bg-slate-50"
            >
              ⊞ Inmuebles
            </a>

            <a
              href="/contacts"
              className="flex items-center gap-4 rounded-2xl bg-slate-50 px-5 py-4 text-[18px] font-medium text-slate-800"
            >
              ◌ Contactos
            </a>

            <a
              href="#"
              className="flex items-center gap-4 rounded-2xl px-5 py-4 text-[18px] text-slate-600 hover:bg-slate-50"
            >
              ☷ Citas
            </a>

            <a
              href="#"
              className="flex items-center gap-4 rounded-2xl px-5 py-4 text-[18px] text-slate-600 hover:bg-slate-50"
            >
              ↗ Reportes
            </a>
            <a
              href="#"
              className="flex items-center gap-4 rounded-2xl px-5 py-4 text-[18px] text-slate-600 hover:bg-slate-50"
            >
               ⚙ Ajustes
            </a>
          </nav>
        </aside>

        {/* Content */}
        <section className="flex-1">
          {/* Header */}
          <header className="border-b border-slate-200 px-8 py-7">
            <h1 className="text-5xl font-semibold">Contactos</h1>
            <p className="mt-2 text-lg text-slate-500">
              Gestiona todos los clientes registrados en el sistema.
            </p>
          </header>

          {/* Body */}
          <div className="px-8 py-6">
            {/* Search */}
            <div className="mb-6 flex gap-4">
              <input
                type="text"
                placeholder="Buscar cliente..."
                className="h-12 w-full rounded-xl border border-slate-200 px-4"
              />

              <button className="rounded-xl bg-[#8bb58f] px-6 text-white font-semibold">
                + Nuevo cliente
              </button>
            </div>

            {/* Table */}
            <div className="overflow-hidden rounded-2xl border border-slate-200 bg-white">
              <table className="w-full">
                <thead className="bg-slate-50 text-left">
                  <tr>
                    <th className="px-6 py-4">Cliente</th>
                    <th>Email</th>
                    <th>Teléfono</th>
                    <th>Ciudad</th>
                    <th>Estado</th>
                    <th>Acciones</th>
                  </tr>
                </thead>

                <tbody>
                  {contacts.map((contact) => (
                    <tr key={contact.id} className="border-t">
                      <td className="px-6 py-4 flex items-center gap-3">
                        <Image
                          src={contact.image}
                          alt={contact.name}
                          width={40}
                          height={40}
                          className="rounded-full"
                        />
                        <span className="font-medium">{contact.name}</span>
                      </td>

                      <td>{contact.email}</td>
                      <td>{contact.phone}</td>
                      <td>{contact.city}</td>

                      <td>
                        <span
                          className={`px-3 py-1 rounded-full text-sm font-semibold ${getStatusClasses(
                            contact.status
                          )}`}
                        >
                          {contact.status}
                        </span>
                      </td>

                      <td>
                        <a
                          href="/client-profile"
                          className="text-emerald-700 font-semibold hover:underline"
                        >
                          Ver perfil
                        </a>
                      </td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>
          </div>
        </section>
      </div>
    </main>
  );
}