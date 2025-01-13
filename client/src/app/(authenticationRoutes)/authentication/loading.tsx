import { Card, CardContent, CardHeader } from "@/components/ui/card";
import { Skeleton } from "@/components/ui/skeleton";

const LoadingPage = () => {
  return (
    <div className="w-full h-full min-h-screen flex items-center justify-center p-8">
      <Card className="w-full max-w-md">
        <CardHeader>
          <Skeleton className="w-[195px] h-6" />
          <Skeleton className="w-full h-10" />
        </CardHeader>
        <CardContent>
          <div className="flex items-center justify-center gap-2">
            <Skeleton className="w-[195px] h-10" />
            <Skeleton className="w-[195px] h-10" />
          </div>
        </CardContent>
      </Card>
    </div>
  );
};

export default LoadingPage;
