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
      appBar: AppBar(title: Text("Main Screen")),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
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
