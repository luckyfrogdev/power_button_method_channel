import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: MyHomePage(),
    );
  }
}

class MyHomePage extends StatefulWidget {
  const MyHomePage({super.key});

  @override
  State<MyHomePage> createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  static const platform = MethodChannel('power_button');

  @override
  void initState() {
    super.initState();
    // Start listening for power button events
    startListening();
  }

  @override
  void dispose() {
    super.dispose();
    // Stop listening for power button events
    stopListening();
  }

  void startListening() {
    platform.setMethodCallHandler((call) async {
      debugPrint("Power button event: ${call.method}");
      if (call.method == 'powerButtonPressed') {
        if (call.arguments == "power_on") {
          // Handle power button press event here
          debugPrint("Power on pressed!");
        } else {
          debugPrint("Power off pressed!");
        }
      }
    });
  }

  void stopListening() {
    // Cleanup
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text("Power Button Listener"),
      ),
      body: const Center(
        child: Text("Listening for power button press..."),
      ),
    );
  }
}
