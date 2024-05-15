import React, { useState, useEffect, useRef } from 'react';
import { SafeAreaView, Text, StyleSheet,View,Button  } from 'react-native';
import Video, {VideoRef} from 'react-native-video';
import { accelerometer, setUpdateIntervalForType, SensorTypes } from 'react-native-sensors';
import proximity, { SubscriptionRef } from 'rn-proximity-sensor';
import { NavigationContainer } from '@react-navigation/native';
import { createNativeStackNavigator } from '@react-navigation/native-stack';

const Stack = createNativeStackNavigator();

const HomeScreen = ({ navigation }) => {
  return (
    <View style={styles.container}>
      <Text style={styles.title}>Elija que servicio quiere probar</Text>
      <View style={styles.buttonContainer}>
        <Button
          title="Go to Accelerometer Sensor"
          onPress={() => navigation.navigate('AccelerometerSensor')}
        />
      </View>
      <View style={styles.buttonContainer}>
        <Button
          title="Go to Proximity Sensor"
          onPress={() => navigation.navigate('ProximitySensor')}
        />
      </View>
      <View style={styles.buttonContainer}>
        <Button
          title="Go to Video Player"
          onPress={() => navigation.navigate('VideoPlayer')}
        />
      </View>
    </View>
  );
};
const AccelerometerSensor = () => {
  // State for accelerometer data
  const [accelerometerData, setAccelerometerData] = useState({ x: 0, y: 0, z: 0 });

  
  const threshold = 2.0; 

  useEffect(() => {
    
    setUpdateIntervalForType(SensorTypes.accelerometer, 400); // Update every 400ms

    
    const subscription = accelerometer.subscribe(({ x, y, z }) => {
      if (Math.abs(x - accelerometerData.x) > threshold ||
          Math.abs(y - accelerometerData.y) > threshold ||
          Math.abs(z - accelerometerData.z) > threshold) {
        setAccelerometerData({ x, y, z });
      }
    });

  
    return () => {
      subscription.unsubscribe();
    };
  }, [accelerometerData]);

  return (
    <View style={styles.container}>
      <Text style={styles.text}>Accelerometer Data:</Text>
      <Text style={styles.text}>X: {accelerometerData.x.toFixed(3)}</Text>
      <Text style={styles.text}>Y: {accelerometerData.y.toFixed(3)}</Text>
      <Text style={styles.text}>Z: {accelerometerData.z.toFixed(3)}</Text>
    </View>
  );
};

const ProximitySensor = () => {
  // State to store sensor data
  const [distance, setDistance] = useState(null);
  const [isClose, setIsClose] = useState(false);

  const sensorSubscriptionRef = useRef<SubscriptionRef | null>(null);

  useEffect(() => {
    
    sensorSubscriptionRef.current = proximity.subscribe((values) => {
      setDistance(values.distance); 
      setIsClose(values.is_close);  
    });

  
    return () => {
      if (sensorSubscriptionRef.current) {
        sensorSubscriptionRef.current.unsubscribe();
        sensorSubscriptionRef.current = null;
      }
    };
  }, []);

  return (
    <View style={styles.container}>
      <Text style={styles.text}>Proximity Distance: {distance !== null ? `${distance} cm` : 'Unavailable'}</Text>
      <Text style={styles.text}>Is Object Close: {isClose ? 'Yes' : 'No'}</Text>
    </View>
  );
};

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
    <NavigationContainer>
      <Stack.Navigator initialRouteName="Home">
        <Stack.Screen name="Home" component={HomeScreen} />
        <Stack.Screen name="AccelerometerSensor" component={AccelerometerSensor} />
        <Stack.Screen name="ProximitySensor" component={ProximitySensor} />
        <Stack.Screen name="VideoPlayer" component={VideoPlayer} />
      </Stack.Navigator>
    </NavigationContainer>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: '#fff',
  },
  buttonContainer: {
    marginTop: 10,  
    width: '80%'    
  },
  title: {
    fontSize: 24,
    fontWeight: '600',
    marginBottom: 20,
  },
  text: {
    fontSize: 24,
    fontWeight: '600',
    marginBottom: 20, 
  },
  backgroundVideo: {
    width: '100%', 
    height: '90%', 
  }
});

export default App;
