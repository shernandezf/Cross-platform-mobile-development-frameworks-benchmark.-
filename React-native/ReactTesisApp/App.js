import React , { useState } from 'react';
import { SafeAreaView, Text, StyleSheet } from 'react-native';
import Video, {VideoRef} from 'react-native-video';

const VideoPlayer = () => {
  const videoURL = 'http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4';
  const [loadingTime, setLoadingTime] = useState(null);

  const handleVideoLoad = () => {
    const endTime = new Date().getTime();
    setLoadingTime(endTime - startTime);
  };

  let startTime;

  return (
    <>
      {loadingTime && (
        <Text style={styles.timer}>
          Video loaded in {loadingTime} ms
        </Text>
      )}
      <Video
        source={{ uri: videoURL }}
        style={styles.backgroundVideo}
        controls
        onLoadStart={() => { startTime = new Date().getTime(); }}
        onLoad={handleVideoLoad}
      />
    
    </>
  );
}; 

const App = () => {
  return (
    <SafeAreaView style={styles.container}>
      <Text style={styles.text}>Hello World</Text>
      <VideoPlayer /> 
    </SafeAreaView>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#fff',
  },
  text: {
    fontSize: 24,
    fontWeight: '600',
    marginBottom: 20, // Add some space between the text and the video player
  },
  backgroundVideo: {
    width: '100%', 
    height: '90%', 
  }
});

export default App;
