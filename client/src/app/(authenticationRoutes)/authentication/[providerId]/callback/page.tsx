"use client";
import { useSearchParams, useRouter, useParams } from "next/navigation";
import { LoadingIcon } from "@/components/icons/LoadingIcon";
import { Card, CardDescription, CardHeader } from "@/components/ui/card";
import { useEffect, useRef } from "react";
import { AuthenticationApi } from "@/services/api/authentication-api";

const Page = () => {
  const initialized = useRef(false);
  const router = useRouter();
  const searchParams = useSearchParams();
  const { providerId } = useParams();

  useEffect(() => {
    if (initialized.current) return;

    initialized.current = true;

    const code = searchParams.get("code");

    if (!code) {
      router.replace("/authentication");
      return;
    }

    async function authenticate() {
      console.log("DAS", providerId);

      const callbackResponse = await AuthenticationApi.callback(
        providerId.toString().toLowerCase(),
        code as string
      );

      if (callbackResponse) {
        router.replace("/");
      } else {
        router.replace("/authentication");
      }
    }

    authenticate();
  }, [providerId]);

  return (
    <div className="w-full h-full min-h-screen flex items-center justify-center">
      <Card className="w-full max-w-md">
        <CardHeader>
          <CardDescription className="flex gap-2 items-center">
            <LoadingIcon />
            Gerando token de autenticação...
          </CardDescription>
        </CardHeader>
      </Card>
    </div>
  );
};

export default Page;
