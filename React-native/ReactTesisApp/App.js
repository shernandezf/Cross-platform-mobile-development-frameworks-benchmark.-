import React, { useState, useEffect, useRef } from 'react';
import { SafeAreaView, Text, StyleSheet,View,Button,Image,Pressable  } from 'react-native';
import Video, {VideoRef} from 'react-native-video';
import { accelerometer, setUpdateIntervalForType, SensorTypes } from 'react-native-sensors';
import proximity, { SubscriptionRef } from 'rn-proximity-sensor';
import { NavigationContainer } from '@react-navigation/native';
import { createNativeStackNavigator } from '@react-navigation/native-stack';

const Stack = createNativeStackNavigator();

const HomeScreen = ({ navigation }) => {
  return (
    <View style={styles.container}>
      <Image
        source={{ uri: 'https://github.com/shernandezf/resources/blob/main/logo_andes.jpg?raw=true' }} 
        style={styles.image}
      />
      <Text style={styles.hardText}>Tesis Pregrado 2024 Santiago Hernández</Text>
      <Text style={styles.text}>Profesor: Camilo Escobar Velásquez</Text>
      
      <View style={styles.buttonContainer}>
        <CustomButton
          onPress={() => navigation.navigate('VideoPlayer')}
          title="Video-Player"
          backgroundColor="#ffdb00"
        />
      </View>
      <View style={styles.buttonContainer}>
        <CustomButton
          onPress={() => navigation.navigate('AccelerometerSensor')}
          title="Accelerometer"
          backgroundColor="#ffdb00"
        />
      </View>
      <View style={styles.buttonContainer}>
        <CustomButton
          onPress={() => navigation.navigate('ProximitySensor')}
          title="Proximity Sensor"
          backgroundColor="#ffdb00"
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
      if (values.distance==5){
        setIsClose(false); 
      }else if(values.distance<5){
        setIsClose(true); 
      }
       
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
const CustomButton = ({ onPress , title, backgroundColor }) => {
  return (
    <Pressable
        style={({ pressed }) => [
          styles.button,
          { backgroundColor: pressed ? '#ffdb00' : backgroundColor },
        ]}
        onPress={onPress}
      >
        <Text style={styles.buttonText}>{title}</Text>
    </Pressable>
  );
};
const VideoPlayer = () => {
  const videoURL = 'https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4';
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
      <Stack.Navigator initialRouteName="Home" screenOptions={{headerShown: false}}>
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
    marginVertical: 10,
    width: '95%' , 
  },
  hardText: {
    color: 'black',
    fontSize: 16,
    fontWeight: 'bold',  
    marginBottom: 3,
  },
  text: {
    color: 'black',
    fontSize: 14,
    fontWeight: '400',
    marginBottom: 20, 
  },
  image: {
    width: '60%',
    height: '45%', 
    resizeMode: 'contain',
  },
  backgroundVideo: {
    width: '100%', 
    height: '90%', 
  },
  button: {
    paddingVertical: 8,
    paddingHorizontal: 20,
    borderRadius: 7, 
    alignItems: 'center',
    justifyContent: 'center',
  },
  buttonText: {
    color: 'black',
    fontWeight: '500',
    fontSize: 16,
  },
});

export default App;
