import React, { useRef, useEffect } from 'react';
// import CameraOff from '@/assets/cameraOff.png';


const getWebcam = (callback) => {
  try {
    const constraints = {
      'video': true,
      'audio': false
    }
    navigator.mediaDevices.getUserMedia(constraints)
      .then(callback);
  } catch (err) {
    console.log(err);
    return undefined;
  }
}

const Styles = {
  Video: { width: "100%", height: "100%", background: 'rgba(245, 240, 215, 0.5)' },
  None: { display: 'none' },
}

const AuctionWaitingRoom: React.FC = () => {
  const [playing, setPlaying] = React.useState(undefined);
  const videoRef = useRef<HTMLVideoElement>(null);

  useEffect(() => {
    getWebcam((stream => {
      setPlaying(true);
      videoRef.current.srcObject = stream;
    }));
  }, []);
  
  const startOrStop = () => {
    if (playing) {
      const stream = videoRef.current.srcObject as MediaStream;
      const tracks = stream?.getTracks();
      if (tracks) {
        tracks.forEach((track) => {
          track.stop();
        });
      }
      setPlaying(false);

    } else {
      getWebcam((stream) => {
        videoRef.current.srcObject = stream;
        setPlaying(true);
      });
    }
    setPlaying(!playing);
  }

  return (
    <section className="gap-16 bg-gray-20 py-10 pt-[150px] md:h-full md:pb-0"> 
      <div className="mx-auto w-5/6 items-center md:flex">
        <h1>aT화훼 공판장 (양재동) 경매방</h1>
      </div>

      <div className="mx-auto w-5/6 items-center justify-center md:flex md:flex-wrap md:h-5/6">
        <video ref={videoRef} autoPlay style={Styles.Video} />
        <button color="warning" onClick={startOrStop}>{playing ? 'Stop' : 'Start'} </button>
      </div >

    </section>
  );
};

export default AuctionWaitingRoom;