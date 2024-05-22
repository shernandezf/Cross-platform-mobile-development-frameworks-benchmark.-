import 'package:flutter/material.dart';
import 'package:all_sensors/all_sensors.dart';

class ProximitySensorWidget extends StatefulWidget {
  const ProximitySensorWidget({Key? key}) : super(key: key);

  @override
  _ProximitySensorWidgetState createState() => _ProximitySensorWidgetState();
}

class _ProximitySensorWidgetState extends State<ProximitySensorWidget> {
  bool _isClose = false; // This will hold the proximity status

  @override
  void initState() {
    super.initState();
    proximityEvents?.listen((ProximityEvent event) {
      setState(() {
        _isClose = event.getValue();
      });
    });
  }

  @override
  Widget build(BuildContext context) {
    // Determine text based on the proximity status
    String closenessText = _isClose ? 'Close' : 'Far';
    return Scaffold(
      appBar: AppBar(
        title: const Text(''),
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            Text(
              'Proximity Sensor',
              style: TextStyle(fontSize: 24, fontWeight: FontWeight.bold),
            ),
            Text(
              'You are: $closenessText',
              style: TextStyle(
                fontSize: 14,
                fontWeight: FontWeight.bold,
              ),
            ),
          ],
        ),
      ),
    );
  }
}
