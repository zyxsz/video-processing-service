import { Skeleton } from "@/components/ui/skeleton";

import { Card, CardHeader } from "@/components/ui/card";

const Page = () => {
  return (
    <div className="w-full h-full min-h-screen flex items-center justify-center">
      <Card className="w-full max-w-md">
        <CardHeader>
          <Skeleton className="h-6 w-64" />
        </CardHeader>
      </Card>
    </div>
  );
};

export default Page;
