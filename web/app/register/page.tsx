"use client";

import Image from "next/image";
import { useState } from "react";
import { useRouter } from "next/navigation";
import { createUserWithEmailAndPassword } from "firebase/auth";
import { auth } from "@/lib/firebase";
import { doc, setDoc } from "firebase/firestore";
import { db } from "@/lib/firebase";

export default function Register() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");
  const router = useRouter();
  const [nombre, setNombre] = useState("");
  const [apellido, setApellido] = useState("");

  const handleRegister = async (e: React.FormEvent) => {
  e.preventDefault();
  setError("");

  try {
    const userCredential = await createUserWithEmailAndPassword(auth, email, password);

    const user = userCredential.user;

    await setDoc(doc(db, "users", user.uid), {
      uid: user.uid,
      nombre,
      apellido,
      email,
    });

    router.push("/dashboard");
  } catch (err: any) {
    setError("Error al crear cuenta");
  }
};

  return (
    <main className="relative min-h-screen overflow-hidden">
      {/* Fondo */}
      <div className="absolute inset-0">
        <Image
          src="/bg-login.png"
          alt="Background"
          fill
          className="object-cover"
        />
        <div className="absolute inset-0 bg-black/20" />
      </div>

      {/* Card */}
      <div className="relative z-10 flex min-h-screen items-center justify-center px-4 py-8">
        <section className="w-full max-w-[700px] rounded-[34px] border border-white/30 bg-white/22 px-8 py-5 backdrop-blur-2xl">
          
          <div className="mb-5 flex justify-center">
            <Image
              src="/logo-syncra.png"
              alt="Logo"
              width={140}
              height={80}
            />
          </div>

          <div className="mb-6 text-center">
            <h1 className="text-3xl font-bold text-[#1f2937]">
              Crear cuenta
            </h1>
          </div>

          <form onSubmit={handleRegister} className="space-y-3">
            <input
                type="text"
                placeholder="Nombre"
                value={nombre}
                onChange={(e) => setNombre(e.target.value)}
            />

            <input
                type="text"
                placeholder="Apellido"
                value={apellido}
                onChange={(e) => setApellido(e.target.value)}
            />
            <input
              type="email"
              placeholder="Correo electrónico"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              className="h-[56px] w-full rounded-2xl px-4"
            />

            <input
              type="password"
              placeholder="Contraseña"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              className="h-[56px] w-full rounded-2xl px-4"
            />

            <button
              type="submit"
              className="h-[58px] w-full rounded-2xl bg-[#97c93d] text-white font-semibold"
            >
              Crear cuenta
            </button>
          </form>

          {error && (
            <p className="text-red-500 text-center mt-2">{error}</p>
          )}

          <p className="mt-6 text-center text-white">
            ¿Ya tienes cuenta?{" "}
            <a href="/" className="underline text-[#7dd3fc]">
              Iniciar sesión
            </a>
          </p>
        </section>
      </div>
    </main>
  );
}