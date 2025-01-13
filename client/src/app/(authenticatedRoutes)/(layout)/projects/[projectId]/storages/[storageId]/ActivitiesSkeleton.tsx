import { Skeleton } from "@/components/ui/skeleton";

const ActivitiesSkeleton = () => {
  return (
    <section className="border rounded-lg">
      <header className="flex flex-col gap-2 p-4 border-b">
        <Skeleton style={{ width: 250, height: 32 }} />
        <Skeleton style={{ width: 600, height: 20 }} />
      </header>
      <div className="p-4">
        <div className="flex flex-col gap-2">
          <Skeleton style={{ width: "100%", height: 106 }} />
          <Skeleton style={{ width: "100%", height: 106 }} />
          <Skeleton style={{ width: "100%", height: 106 }} />
          <Skeleton style={{ width: "100%", height: 106 }} />
        </div>
      </div>
    </section>
  );
};

export { ActivitiesSkeleton };
