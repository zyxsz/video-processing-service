"use client";
import { LocalIcons } from "@/components/icons";
import { Button } from "@/components/ui/button";
import {
  Card,
  CardContent,
  CardDescription,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";
import { Skeleton } from "@/components/ui/skeleton";
import { AuthenticationApi } from "@/services/api/authentication-api";
import { useSuspenseQuery } from "@tanstack/react-query";

const Page = () => {
  const { data } = useSuspenseQuery({
    queryKey: ["authentication:oauth2:providers"],
    queryFn: AuthenticationApi.findOAuth2Providers,
  });

  const handleSelectProvider = async (providerId: string) => {
    const response = await AuthenticationApi.getOAuth2RedirectUrl(
      providerId.toLowerCase()
    );

    if (!response) return;

    window.location.replace(response.redirectUrl);
  };

  return (
    <div className="w-full h-full min-h-screen flex items-center justify-center p-8">
      <Card className="w-full max-w-md">
        <CardHeader>
          <CardTitle>Autenticação</CardTitle>

          <CardDescription>
            Utilize algum dos provedores abaixo para se autenticar em nossa
            aplicação.
          </CardDescription>
        </CardHeader>
        <CardContent>
          <div className="flex items-center justify-center gap-2">
            {data.map((provider) => (
              <Button
                className="flex-1"
                variant="outline"
                key={provider.id}
                onClick={() => handleSelectProvider(provider.id)}
              >
                {LocalIcons[provider.id]({ className: "mr-2 h-4 w-4" })}
                {provider.name}
              </Button>
            ))}
          </div>
        </CardContent>
      </Card>
    </div>
  );
};
export default Page;
