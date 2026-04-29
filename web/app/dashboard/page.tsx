"use client";

import { useEffect, useState } from "react";
import Image from "next/image";
import { onAuthStateChanged, signOut } from "firebase/auth";
import { doc, getDoc, collection, getDocs } from "firebase/firestore";
import { auth, db } from "@/lib/firebase";
import { useRouter } from "next/navigation";
import Link from "next/link";
import {
  ChevronDown,
  DollarSign,
  Grid2x2,
  Home,
  User,
  Users,
  Check,
  Settings,
} from "lucide-react";

function SidebarItem({
  icon,
  label,
  active = false,
}: {
  icon: React.ReactNode;
  label: string;
  active?: boolean;
}) {
  return (
    <div
      className={`flex items-center gap-3 rounded-xl px-4 py-3 text-[15px] font-medium transition ${
        active
          ? "bg-emerald-50 text-emerald-700"
          : "text-slate-500 hover:bg-slate-100"
      }`}
    >
      <span className="flex h-5 w-5 items-center justify-center">{icon}</span>
      <span>{label}</span>
    </div>
  );
}

function StatCard({
  icon,
  title,
  value,
  change,
  positive = true,
}: {
  icon: React.ReactNode;
  title: string;
  value: string;
  change: string;
  positive?: boolean;
}) {
  return (
    <div className="rounded-3xl border border-slate-200 bg-white p-6 shadow-sm">
      <div className="mb-5 flex items-center gap-4">
        <div className="flex h-12 w-12 items-center justify-center rounded-2xl bg-slate-100">
          {icon}
        </div>
        <p className="text-[16px] font-semibold text-slate-700">{title}</p>
      </div>

      <h3 className="text-5xl font-semibold tracking-tight text-slate-800">
        {value}
      </h3>

      <p
        className={`mt-4 text-lg font-semibold ${
          positive ? "text-emerald-600" : "text-rose-500"
        }`}
      >
        {change ? `${positive ? "↑" : "↓"} ${change}` : "Actualizado"}
      </p>
    </div>
  );
}

type UserProfile = {
  nombre: string;
  apellido: string;
  email: string;
  profileImageUrl?: string;
};

type Property = {
  id: string;
  title: string;
  type: string;
  location: string;
  price: number;
  interested: number;
  status: string;
  createdAt?: {
    seconds: number;
  };
};

export default function DashboardPage() {
  const router = useRouter();
  const [userProfile, setUserProfile] = useState<UserProfile | null>(null);
  const [properties, setProperties] = useState<Property[]>([]);

  const handleLogout = async () => {
    await signOut(auth);
    router.push("/");
  };

  useEffect(() => {
    const unsubscribe = onAuthStateChanged(auth, async (user) => {
      if (!user) {
        setUserProfile(null);
        router.push("/");
        return;
      }

      try {
        const docRef = doc(db, "users", user.uid);
        const docSnap = await getDoc(docRef);

        if (docSnap.exists()) {
          const data = docSnap.data() as UserProfile;
          setUserProfile({
            nombre: data.nombre || "",
            apellido: data.apellido || "",
            email: data.email || user.email || "",
            profileImageUrl: data.profileImageUrl || "",
          });
        } else {
          setUserProfile({
            nombre: "",
            apellido: "",
            email: user.email || "",
            profileImageUrl: "",
          });
        }

        const propertiesSnapshot = await getDocs(collection(db, "properties"));

        const propertiesData: Property[] = propertiesSnapshot.docs.map(
          (docItem) => {
            const data = docItem.data() as Partial<Property>;

            return {
              id: data.id || docItem.id,
              title: data.title || "",
              type: data.type || "",
              location: data.location || "",
              price: Number(data.price || 0),
              interested: Number(data.interested || 0),
              status: data.status || "Disponible",
              createdAt: data.createdAt,
            };
          }
        );

        setProperties(propertiesData);
      } catch (error) {
        console.error("Error al obtener datos del usuario:", error);
        setUserProfile({
          nombre: "",
          apellido: "",
          email: user.email || "",
        });
      }
    });

    return () => unsubscribe();
  }, [router]);

  const nombre = userProfile?.nombre || "Usuario";
  const apellido = userProfile?.apellido || "";
  const nombreCompleto = `${nombre} ${apellido}`.trim() || "Usuario";

  const totalInterested = properties.reduce(
    (sum, property) => sum + Number(property.interested || 0),
    0
  );

  const soldProperties = properties.filter(
    (property) =>
      property.status === "Vendido" || property.status === "Vendidos"
  );

  const monthlySales = soldProperties.reduce(
    (sum, property) => sum + Number(property.price || 0),
    0
  );

  const conversionRate =
    properties.length > 0
      ? ((soldProperties.length / properties.length) * 100).toFixed(1)
      : "0";
  
  

  const currentMonth = new Date().getMonth();

  const monthlySalesData = Array.from({ length: 12 }, (_, index) => {
    const total = soldProperties
      .filter((property) => {
        if (!property.createdAt?.seconds) {
          return index === currentMonth;
        }

        const date = new Date(property.createdAt.seconds * 1000);
        return date.getMonth() === index;
      })
      .reduce((sum, property) => sum + Number(property.price || 0), 0);

    return total;
  });

  const maxMonthlySales = Math.max(...monthlySalesData, 1);

  const statusData = [
    {
      label: "DISP",
      total: properties.filter(
        (p) => p.status === "Disponible" || p.status === "Disponibles"
      ).length,
    },
    {
      label: "RES",
      total: properties.filter(
        (p) => p.status === "Reservado" || p.status === "Reservados"
      ).length,
    },
    {
      label: "VEND",
      total: soldProperties.length,
    },
    {
      label: "VIS",
      total: properties.filter((p) => p.status === "Visitas").length,
    },
    {
      label: "PEND",
      total: properties.filter((p) => p.status === "Pendientes").length,
    },
  ];

  const maxStatusTotal = Math.max(
    ...statusData.map((item) => item.total),
    1
  );

  return (
    <main className="min-h-screen bg-[#f6f7fb] text-slate-800">
      <div className="flex min-h-screen">
        <aside className="flex w-[290px] flex-col border-r border-slate-200 bg-white">
          <div className="flex flex-col items-center px-8 pb-8 pt-10">
            <div className="relative mb-5 h-[90px] w-[150px]">
              <Image
                src="/logo-syncra.png"
                alt="Syncra Estate AI"
                fill
                className="object-contain"
              />
            </div>

            <h2 className="text-[20px] font-semibold text-slate-800">
              Syncra Estate AI
            </h2>
          </div>

          <nav className="flex-1 space-y-2 px-5">
            <SidebarItem
              icon={<Home className="h-5 w-5" />}
              label="Dashboard"
              active
            />

            <Link href="/properties">
              <SidebarItem
                icon={<Grid2x2 className="h-5 w-5" />}
                label="Propiedades"
              />
            </Link>

            <Link href="/contacts">
              <SidebarItem
                icon={<User className="h-5 w-5" />}
                label="Contactos"
              />
            </Link>
          </nav>

          <div className="px-5 pb-8 pt-4">
            <button
              onClick={handleLogout}
              className="flex w-full items-center gap-3 rounded-xl px-4 py-3 text-[15px] font-medium text-red-600 transition hover:bg-red-50"
            >
              <Settings className="h-5 w-5" />
              <span>Cerrar sesión</span>
            </button>
          </div>
        </aside>

        <section className="flex-1">
          <header className="border-b border-slate-200 bg-white px-8 py-5" />

          <div className="p-8">
            <div className="mb-8 flex items-start justify-between gap-6">
              <div>
                <h1 className="text-[48px] font-semibold leading-tight text-slate-800">
                  Bienvenido de vuelta, {nombre}
                </h1>
                <p className="mt-3 text-2xl text-slate-400">
                  Aquí están las métricas de tu negocio hoy.
                </p>
              </div>

              <div className="flex min-w-[320px] items-center justify-between rounded-3xl border border-slate-200 bg-white p-5 shadow-sm">
                <div className="flex items-center gap-4">
                  <img
                    src={userProfile?.profileImageUrl || "/avatar-user.png"}
                    alt={nombreCompleto}
                    className="h-[58px] w-[58px] rounded-full object-cover"
                  />
                  <div>
                    <p className="text-[18px] font-semibold text-slate-700">
                      {nombreCompleto}
                    </p>
                    <p className="text-[16px] text-slate-400">Gerente</p>
                  </div>
                </div>

                <ChevronDown className="h-5 w-5 text-slate-400" />
              </div>
            </div>

            <div className="grid grid-cols-4 gap-5">
              <StatCard
                icon={<DollarSign className="h-6 w-6 text-emerald-600" />}
                title="Ventas del mes"
                value={`Q${monthlySales.toLocaleString()}`}
                change=""
              />

              <StatCard
                icon={<Users className="h-6 w-6 text-sky-600" />}
                title="Personas interesadas"
                value={totalInterested.toString()}
                change=""
              />

              <StatCard
                icon={<Home className="h-6 w-6 text-sky-600" />}
                title="Propiedades registradas"
                value={properties.length.toString()}
                change=""
              />

              <StatCard
                icon={<Check className="h-6 w-6 text-emerald-600" />}
                title="Tasa de conversión"
                value={`${conversionRate}%`}
                change=""
              />
            </div>

            <div className="mt-6 grid grid-cols-2 gap-5">
              <div className="rounded-3xl border border-slate-200 bg-white p-6 shadow-sm">
                <div className="mb-5 flex items-center justify-between">
                  <h3 className="text-[22px] font-semibold text-slate-800">
                    Ventas por mes
                  </h3>
                  <span className="rounded-2xl bg-emerald-500 px-4 py-2 text-sm font-semibold text-white">
                    Q{monthlySales.toLocaleString()}
                  </span>
                </div>

                <div className="flex h-[250px] items-end justify-between gap-3 rounded-2xl bg-gradient-to-b from-emerald-50 to-white p-4">
                  {monthlySalesData.map((amount, i) => {
                    const height = Math.max(
                      (amount / maxMonthlySales) * 190,
                      12
                    );

                    return (
                      <div
                        key={i}
                        className="flex flex-1 flex-col items-center gap-3"
                      >
                        <div
                          title={`Q${amount.toLocaleString()}`}
                          className="w-full rounded-t-2xl bg-emerald-300/80"
                          style={{ height: `${height}px` }}
                        />

                        <span className="text-xs font-medium text-slate-400">
                          {
                            [
                              "ENE",
                              "FEB",
                              "MAR",
                              "ABR",
                              "MAY",
                              "JUN",
                              "JUL",
                              "AGO",
                              "SEP",
                              "OCT",
                              "NOV",
                              "DIC",
                            ][i]
                          }
                        </span>
                      </div>
                    );
                  })}
                </div>
              </div>

              <div className="rounded-3xl border border-slate-200 bg-white p-6 shadow-sm">
                <div className="mb-3 flex items-start justify-between">
                  <div>
                    <h3 className="text-[22px] font-semibold text-slate-800">
                      Estado de propiedades
                    </h3>
                    <p className="mt-2 text-lg font-medium text-emerald-600">
                      Basado en Firebase
                    </p>
                  </div>

                  <div className="text-right">
                    <p className="text-6xl font-semibold text-slate-800">
                      {properties.length}
                    </p>
                    <p className="text-xl text-slate-400">Total</p>
                  </div>
                </div>

                <div className="mt-6 flex h-[220px] items-end justify-between gap-4 rounded-2xl bg-slate-50 px-5 pb-5 pt-12">
                  {statusData.map((item) => {
                    const height =
                      item.total > 0
                        ? Math.max((item.total / maxStatusTotal) * 120, 16)
                        : 6;

                    return (
                      <div
                        key={item.label}
                        className="flex flex-1 flex-col items-center gap-3"
                      >
                        <div
                          title={`${item.total} propiedades`}
                          className="w-full rounded-t-xl bg-emerald-400"
                          style={{ height: `${height}px` }}
                        />

                        <span className="text-xs font-medium text-slate-400">
                          {item.label}
                        </span>

                        <span className="text-sm font-semibold text-slate-700">
                          {item.total}
                        </span>
                      </div>
                    );
                  })}
                </div>
              </div>
            </div>

            <div className="mt-6 rounded-3xl border border-slate-200 bg-white p-6 shadow-sm">
              <h3 className="mb-6 text-[22px] font-semibold text-slate-800">
                Propiedades registradas
              </h3>

              <div className="overflow-hidden rounded-2xl border border-slate-100">
                <table className="w-full">
                  <thead className="bg-slate-50 text-left text-[15px] text-slate-400">
                    <tr>
                      <th className="px-6 py-4 font-medium">Título</th>
                      <th className="px-6 py-4 font-medium">Tipo</th>
                      <th className="px-6 py-4 font-medium">Ubicación</th>
                      <th className="px-6 py-4 font-medium">Precio</th>
                      <th className="px-6 py-4 font-medium">Estado</th>
                    </tr>
                  </thead>

                  <tbody>
                    {properties.map((property) => (
                      <tr
                        key={property.id}
                        className="border-t border-slate-100 text-[16px] text-slate-700"
                      >
                        <td className="px-6 py-5 font-medium">
                          {property.title}
                        </td>
                        <td className="px-6 py-5">{property.type}</td>
                        <td className="px-6 py-5">{property.location}</td>
                        <td className="px-6 py-5 text-slate-500">
                          Q{property.price.toLocaleString()}
                        </td>
                        <td className="px-6 py-5 text-slate-500">
                          {property.status}
                        </td>
                      </tr>
                    ))}
                  </tbody>
                </table>
              </div>
            </div>
          </div>
        </section>
      </div>
    </main>
  );
}