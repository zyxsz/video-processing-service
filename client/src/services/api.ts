//NEXT_PUBLIC_BASE_URL

import axios from "axios";
// import { cookies } from "next/headers";

const API_BASE_URL = process.env.NEXT_PUBLIC_BASE_URL;

export const apiClient = axios.create({
  baseURL: API_BASE_URL,
  withCredentials: true,
});

// if (typeof window == "undefined") {
//   axios.interceptors.request.use(
//     function (config) {
//       // Do something before request is sent
//       console.log(config);
//       config.headers.set("Cookie", cookies().toString());

//       return config;
//     },
//     function (error) {
//       // Do something with request error
//       return Promise.reject(error);
//     }
//   );
// } else {
// }

export { API_BASE_URL };
