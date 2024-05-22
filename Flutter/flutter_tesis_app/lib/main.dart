import 'package:flutter/material.dart';
import 'accelerometer.dart';
import 'videoplayerwidget.dart';
import 'proximitysensor.dart';
import 'color.dart';

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
              width: MediaQuery.of(context).size.width * 0.6,
              height: MediaQuery.of(context).size.height * 0.35,
              fit: BoxFit.contain,
            ),
            SizedBox(height: 20),
            Text("Tesis Pregrado 2024 Santiago Hernández",
                style: TextStyle(fontSize: 16, fontWeight: FontWeight.bold)),
            Text("Profesor: Profesor: Camilo Escobar Velásquez",
                style: TextStyle(fontSize: 12)),
            SizedBox(height: 20), // Adds some space before the buttons
            Padding(
              padding: EdgeInsets.symmetric(vertical: 10.0),
              child: ElevatedButton(
                onPressed: () => _navigateToWidget(
                    context,
                    VideoPlayerWidget(
                        videoUrl:
                            'https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4')),
                child: Text("Video-Player"),
                style: ButtonStyle(
                  backgroundColor: MaterialStateProperty.all<Color>(
                      Color(0xFFFFD700)), // Strong yellow color
                  foregroundColor:
                      MaterialStateProperty.all<Color>(Colors.black),
                  minimumSize: MaterialStateProperty.all<Size>(Size(
                      MediaQuery.of(context).size.width * 0.92,
                      MediaQuery.of(context).size.height * 0.06)),
                  shape: MaterialStateProperty.all<RoundedRectangleBorder>(
                    RoundedRectangleBorder(
                      borderRadius: BorderRadius.circular(
                          7), // Change the degree of corner rounding
                    ),
                  ),
                ),
              ),
            ),
            Padding(
              padding: EdgeInsets.symmetric(vertical: 10.0),
              child: ElevatedButton(
                onPressed: () =>
                    _navigateToWidget(context, AccelerometerWidget()),
                child: Text("Accelerometer"),
                style: ButtonStyle(
                  backgroundColor: MaterialStateProperty.all<Color>(
                      Color(0xFFFFD700)), // Strong yellow color
                  foregroundColor:
                      MaterialStateProperty.all<Color>(Colors.black),
                  minimumSize: MaterialStateProperty.all<Size>(Size(
                      MediaQuery.of(context).size.width * 0.92,
                      MediaQuery.of(context).size.height * 0.06)),
                  shape: MaterialStateProperty.all<RoundedRectangleBorder>(
                    RoundedRectangleBorder(
                      borderRadius: BorderRadius.circular(
                          7), // Change the degree of corner rounding
                    ),
                  ),
                ),
              ),
            ),
            Padding(
              padding: EdgeInsets.symmetric(vertical: 10.0),
              child: ElevatedButton(
                onPressed: () =>
                    _navigateToWidget(context, ProximitySensorWidget()),
                child: Text("Proximity Sensor"),
                style: ButtonStyle(
                  backgroundColor: MaterialStateProperty.all<Color>(
                      Color(0xFFFFD700)), // Strong yellow color
                  foregroundColor:
                      MaterialStateProperty.all<Color>(Colors.black),
                  minimumSize: MaterialStateProperty.all<Size>(Size(
                      MediaQuery.of(context).size.width * 0.92,
                      MediaQuery.of(context).size.height * 0.06)),
                  shape: MaterialStateProperty.all<RoundedRectangleBorder>(
                    RoundedRectangleBorder(
                      borderRadius: BorderRadius.circular(
                          7), // Change the degree of corner rounding
                    ),
                  ),
                ),
              ),
            ),
            Padding(
              padding: EdgeInsets.symmetric(vertical: 10.0),
              child: ElevatedButton(
                onPressed: () => _navigateToWidget(context, ColorTestPage()),
                child: Text("Color Test"),
                style: ButtonStyle(
                  backgroundColor: MaterialStateProperty.all<Color>(
                      Color(0xFFFFD700)), // Strong yellow color
                  foregroundColor:
                      MaterialStateProperty.all<Color>(Colors.black),
                  minimumSize: MaterialStateProperty.all<Size>(Size(
                      MediaQuery.of(context).size.width * 0.92,
                      MediaQuery.of(context).size.height * 0.06)),
                  shape: MaterialStateProperty.all<RoundedRectangleBorder>(
                    RoundedRectangleBorder(
                      borderRadius: BorderRadius.circular(
                          7), // Change the degree of corner rounding
                    ),
                  ),
                ),
              ),
            ),
          ],
        ),
      ),
    );
  }
}
