import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutternativesdkconnection/MyHomePage.dart';

void main() => runApp(MyApp());

class MyApp extends StatelessWidget {
  static const platform = const MethodChannel(
      'urazbayev.zhassulan.flutternativesdkconnection');
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Android connetion',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: MyHomePage(title: 'Channel demo app'));
  }

  Future<dynamic> _handleMethod(MethodCall call) async {
    switch(call.method) {
      case "message":
        print(call.arguments);
        debugPrint(call.arguments);
        return new Future.value("");
    }
  }
}