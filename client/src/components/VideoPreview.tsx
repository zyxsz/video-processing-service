"use client";
import dynamic from "next/dynamic";
import { useEffect, useRef } from "react";

// @ts-ignore
import shaka from "shaka-player/dist/shaka-player.ui";

// const shaka = dynamic(
//   // @ts-ignore
//   () => import("shaka-player/dist/shaka-player.ui"),
//   { ssr: false }
// );

const videoUrl =
  "https://vps-server-storage.s3.us-east-1.amazonaws.com/video_1_env.mp4?response-content-disposition=inline&X-Amz-Security-Token=IQoJb3JpZ2luX2VjEHMaCXVzLWVhc3QtMSJHMEUCICZoQLP8Q3YN9sGn3I8mQ%2FtHhFXaxj8LQC5h8YGO2Pp1AiEAh3BRw47hOCWvHcAqJ96IDnPjaDAIlbsr5SpkEayX0Hgq6AIIfBADGgw2MjQwNDk1OTI1NzgiDLI0t8xMh69yOws0sCrFAijerl%2Fkvkjpr%2Baa62aVq2Sbh0JOzg%2FH4Q42AMcKnHS5yZIwiChG%2BPWox82RzUoLg%2FxVEtBQvgI92cQy%2BcfqDogO4zH%2FekhdLCVB7o7xR7kYRAtAxKyzZ7Hxc2HNyvzENI7g5rdBBwAChLfEIcVKPZylZ%2B%2BUMK2PGQS5ghQnNBvO%2Fy53Zie7Vzq1UAsljhhrnz%2B5PSxAt%2Bcg2ENNtCIiQuO0G3FsfoQjsHoA5EHilC18mpfEl6FBOo1J9aAVP0vgOjn0R78Y%2BwwAr4At4cqQSE2UFW%2BXKKzLttZsLHguTDDndw0LNQc2ziqM%2BR3crkL4eoCkttl0zG%2FTHF9Xz%2B3SOMSslx3%2Fg0H%2FjAaqh%2FRV48OJTIH94%2BEaMfcDZCJDmNO6CwA0RxKKAWnjt57H%2BYud7z74BcGZYHeC9jLghmvxUTVRDqf19aAw6uOYtgY6swJ4RqymKVkAYFSm77PZgwddUqICcGaroG4cuHCOA1UXpw2BVE7ctSyAAg60LeTaCUHSBTsJRGHFvAkgbd%2FxSWO3%2BSTs6UJHOrO4%2FjzM94uD4nDd18Cou9g%2Fh0YIBzRZ5KGWDoK7oulWx0LXOMesNCHaSpAq53BXTL1mDV81SupGNjj5rlluy7SPR251g0LMW5DCYCb8oww8Yb5Eis1HrA9FgnGMMvSY4l%2FIbiVSTrslTgbaT3smZUj4SZG3I%2Fg5oKKOvf0UJsgpjUmwe3VZ1txnYN5dBGtM%2FHAMRaUVHj9F%2FLV7U%2Bpw8uAblZvTG0BT8VL4GfXgyXKPWwCkwWjw%2B0JDCxgaBlS0X3DLUtcBgmhycqR4wzyZFBpGKOlUMrko762f%2BtIz1dWW38ei9gsW28vu08Zz&X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Date=20240821T182941Z&X-Amz-SignedHeaders=host&X-Amz-Expires=43200&X-Amz-Credential=ASIAZCTCBDEBGWA7UD4I%2F20240821%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Signature=24f99d300a7e72dfe5f0f689f3a6688dc7428a851a03950b69e8ec7f01e61e8f";

const VideoPreview = () => {
  const initialized = useRef(false);
  const videoRef = useRef<HTMLVideoElement>(null);

  useEffect(() => {
    if (initialized.current) return;
    if (!videoRef.current) return;
    if (typeof window === "undefined") return;

    initialized.current = true;

    const init = async () => {
      const player = new (shaka as any).Player();
      console.log(player, shaka);

      player.addEventListener("error", onErrorEvent);

      player.configure({
        drm: {
          clearKeys: {
            // 'key-id-in-hex': 'key-in-hex',
            "85060829ebb4ffd3232137500cef9384":
              "f3c9586b03ea7de18ca48471cba40fa7",
            // '02030507011013017019023029031037': '03050701302303204201080425098033'
          },
        },
      });

      await player.attach(videoRef.current);

      try {
        await player.load(videoUrl);
        // This runs if the asynchronous load is successful.
        console.log("The video has now been loaded!");
      } catch (e) {
        // onError is executed if the asynchronous load fails.
        onError(e);
      }

      function onErrorEvent(event: any) {
        // Extract the shaka.util.Error object from the event.
        onError(event.detail);
      }

      function onError(error: any) {
        // Log the error.
        console.error("Error code", error.code, "object", error);
      }
    };

    init();
  }, [videoRef, initialized]);

  return <video ref={videoRef} />;
};

export { VideoPreview };
