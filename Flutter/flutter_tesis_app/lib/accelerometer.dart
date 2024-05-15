import 'package:flutter/material.dart';
import 'package:sensors_plus/sensors_plus.dart';
import 'dart:async';

class AccelerometerWidget extends StatefulWidget {
  @override
  _AccelerometerWidgetState createState() => _AccelerometerWidgetState();
}

class _AccelerometerWidgetState extends State<AccelerometerWidget> {
  late StreamSubscription<AccelerometerEvent> _accelerometerSubscription;
  double x = 0, y = 0, z = 0;
  double threshold = 2.0; // Define the threshold here

  @override
  void initState() {
    super.initState();
    _accelerometerSubscription = SensorsPlatform.instance.accelerometerEvents
        .listen((AccelerometerEvent event) {
      if ((event.x - x).abs() > threshold ||
          (event.y - y).abs() > threshold ||
          (event.z - z).abs() > threshold) {
        setState(() {
          x = event.x;
          y = event.y;
          z = event.z;
        });
      }
    }, onError: (e) {
      // Handle any errors that occur during subscription
      print('Error reading accelerometer events: $e');
    });
  }

  @override
  void dispose() {
    _accelerometerSubscription.cancel();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text('Accelerometer Test'),
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            Text('X-axis: ${x.toStringAsFixed(2)}'),
            Text('Y-axis: ${y.toStringAsFixed(2)}'),
            Text('Z-axis: ${z.toStringAsFixed(2)}'),
          ],
        ),
      ),
    );
  }
}
