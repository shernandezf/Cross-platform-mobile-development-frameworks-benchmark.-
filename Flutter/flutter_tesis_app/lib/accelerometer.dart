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
        title: Text(
          '',
        ),
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          crossAxisAlignment: CrossAxisAlignment.center,
          children: <Widget>[
            Text(
              'Accelerometer:',
              style: TextStyle(
                fontSize: 24,
                fontWeight: FontWeight.bold,
                color: Colors.black,
              ),
            ),
            Text(
              'X: ${x.toStringAsFixed(3)} Y: ${y.toStringAsFixed(3)} Z: ${z.toStringAsFixed(3)}',
              style: TextStyle(
                fontSize: 14,
                color: Colors.black,
              ),
            ),
          ],
        ),
      ),
    );
  }
}
