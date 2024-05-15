import 'package:flutter/material.dart';
import 'accelerometer.dart';
import 'videoplayerwidget.dart';
import 'proximitysensor.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Navigation Demo',
      home: MainScreen(),
    );
  }
}

class MainScreen extends StatelessWidget {
  // Helper function for navigation
  void _navigateToWidget(BuildContext context, Widget widget) {
    Navigator.push(context, MaterialPageRoute(builder: (context) => widget));
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: Text("")),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            Image.network(
              'https://github.com/shernandezf/resources/blob/main/logo_andes.jpg?raw=true',
              width: 200, // Set your width as needed
              height: 200, // Set your height as needed
            ),
            Text("Tesis Pregrado 2024 Santiago Hernandez",
                style: TextStyle(fontSize: 21, fontWeight: FontWeight.bold)),
            Text("Profesor: Camilo Escobar Velasquez",
                style: TextStyle(fontSize: 18)),
            SizedBox(height: 20), // Adds some space before the buttons
            ElevatedButton(
              onPressed: () => _navigateToWidget(
                  context,
                  VideoPlayerWidget(
                      videoUrl:
                          'https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4')),
              child: Text("Go to video player"),
            ),
            ElevatedButton(
              onPressed: () =>
                  _navigateToWidget(context, ProximitySensorWidget()),
              child: Text("Go to proximity sensor"),
            ),
            ElevatedButton(
              onPressed: () =>
                  _navigateToWidget(context, AccelerometerWidget()),
              child: Text("Go to accelerometer"),
            ),
          ],
        ),
      ),
    );
  }
}
