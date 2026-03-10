import Image from "next/image";

export default function Home() {
  return (
    <main className="relative min-h-screen overflow-hidden">
      {/* Fondo */}
      <div className="absolute inset-0">
        <Image
          src="/bg-login.png"
          alt="Lobby background"
          fill
          priority
          className="object-cover"
        />
        <div className="absolute inset-0 bg-black/20" />
      </div>

      {/* Card */}
      <div className="relative z-10 flex min-h-screen items-center justify-center px-4 py-8">
        <section className="w-full max-w-[700px] rounded-[34px] border border-white/30 bg-white/22 px-8 py-5 shadow-[0_20px_80px_rgba(0,0,0,0.28)] backdrop-blur-2xl ">
          {/* Logo */}
          <div className="mb-5 flex justify-center">
            <Image
              src="/logo-syncra.png"
              alt="Syncra Estate AI"
              width={220}
              height={110}
              className="h-auto w-[140px] object-contain sm:w-[140px]"
            />
          </div>

          {/* Heading */}
          <div className="mb-6 text-center">
            <h1 className="text-3xl font-bold tracking-tight text-[#1f2937] sm:text-[38px]">
              Iniciar sesión
            </h1>
            <p className="mt-2 text-[15px] text-gray-700 sm:text-base">
              Accede al panel de administración
            </p>
          </div>

          {/* Google button */}
          <button
            type="button"
            className="mb-5 flex h-[56px] w-full items-center justify-center gap-3 rounded-2xl bg-white text-[17px] font-medium text-gray-800 shadow-[0_8px_24px_rgba(0,0,0,0.10)] transition hover:scale-[1.01]"
          >
            <Image
              src="/google.png"
              alt="Google"
              width={20}
              height={20}
            />
            Continuar con Google
          </button>

          {/* Divider */}
          <div className="mb-5 flex items-center gap-3">
            <div className="h-px flex-1 bg-white/50" />
            <div className="flex h-7 w-7 items-center justify-center rounded-full border border-white/60 bg-white/55 text-xs text-gray-600">
              o
            </div>
            <div className="h-px flex-1 bg-white/50" />
          </div>

          {/* Form */}
          <form className="space-y-3">
            <div>
              <label
                htmlFor="email"
                className="mb-2 block text-[16px] font-semibold text-[#1f2937]"
              >
                Correo electrónico
              </label>
              <input
                id="email"
                type="email"
                placeholder="Correo electrónico"
                className="h-[56px] w-full rounded-2xl border border-white/50 bg-white/82 px-4 text-[16px] text-gray-800 placeholder:text-gray-500 outline-none transition focus:border-lime-500"
              />
            </div>

            <div>
              <label
                htmlFor="password"
                className="mb-2 block text-[16px] font-semibold text-[#1f2937]"
              >
                Contraseña
              </label>

              <div className="flex h-[56px] items-center rounded-2xl border border-white/50 bg-white/82 px-4 transition focus-within:border-lime-500">
                <input
                  id="password"
                  type="password"
                  placeholder="Contraseña"
                  className="w-full bg-transparent text-[16px] text-gray-800 placeholder:text-gray-500 outline-none"
                />
                <button
                  type="button"
                  className="ml-3 text-[15px] font-semibold text-sky-600"
                >
                  Mostrar
                </button>
              </div>
            </div>

            <button
              type="submit"
              className="mt-1 h-[58px] w-full rounded-2xl bg-[#97c93d] text-[20px] font-semibold text-white shadow-[0_12px_30px_rgba(151,201,61,0.35)] transition hover:bg-[#88b736]"
            >
              Iniciar sesión
            </button>
          </form>

          {/* Footer */}
          <p className="mt-6 text-center text-[16px] text-white">
            ¿No tienes cuenta?{" "}
            <a href="#" className="font-semibold text-[#7dd3fc] underline">
              Crear cuenta
            </a>
          </p>
        </section>
      </div>
    </main>
  );
}