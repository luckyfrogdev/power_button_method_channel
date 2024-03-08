import 'dart:async';

import 'package:flutter/services.dart';

class PowerButton {
  //call the method channel android
  static const MethodChannel _channel = MethodChannel('power_button');

  static Future<bool> get isButtonPressed async {
    final bool isPressed = await _channel.invokeMethod('checkPowerButton');
    return isPressed;
  }
}
