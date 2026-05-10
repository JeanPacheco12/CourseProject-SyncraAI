import type { NextConfig } from "next";


const nextConfig = {
  images: {
    remotePatterns: [
      {
        protocol: "https",
        hostname: "images.unsplash.com",
      },
      {
        protocol: "https",
        hostname: "images.adsttc.com",
      },
      {
        protocol: "https",
        hostname: "www.compracasaenguate.com",
      },
    ],
  },
};


export default nextConfig;