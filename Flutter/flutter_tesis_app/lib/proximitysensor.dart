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
    String proximityText = _isClose ? '0 cm' : 'Not Detected';
    String closenessText = _isClose ? 'Is Close' : 'Is Far';
    return Scaffold(
      appBar: AppBar(
        title: const Text('Proximity Sensor Test'),
        backgroundColor: _isClose
            ? Colors.red
            : Colors.blue, // Change color based on proximity
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            Text(
              'Distance: $proximityText',
              style: TextStyle(fontSize: 24, fontWeight: FontWeight.bold),
            ),
            Text(
              closenessText,
              style: TextStyle(
                  fontSize: 24,
                  fontWeight: FontWeight.bold,
                  color: _isClose ? Colors.green : Colors.grey),
            ),
          ],
        ),
      ),
    );
  }
}
