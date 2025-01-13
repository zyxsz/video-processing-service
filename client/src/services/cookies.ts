import { isServer } from "@tanstack/react-query";
import { apiClient } from "./api";

export const setCookies = () =>
  import("next/headers").then(({ cookies }) => {
    if (isServer) {
      apiClient.interceptors.request.use(
        function (config) {
          // Do something before request is sent
          console.log(config);
          config.headers.set("Cookie", cookies().toString());

          return config;
        },
        function (error) {
          // Do something with request error
          return Promise.reject(error);
        }
      );
    }
  });
