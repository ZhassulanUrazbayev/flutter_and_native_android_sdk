import 'dart:async';

import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

class MyHomePage extends StatelessWidget {

  static const platform = const MethodChannel(
      'urazbayev.zhassulan.flutternativesdkconnection');
  final String title;
  StreamController<String> _currentMessage = StreamController<String>();
  
  MyHomePage({Key key, this.title}) : super(key: key) {
    platform.setMethodCallHandler(_handleMethod);
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Center(child: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        crossAxisAlignment: CrossAxisAlignment.center,
        children: <Widget>[
          RaisedButton(
            child: new Text('Show native view'),
            onPressed: _showNativeView,
          ),
          StreamBuilder(
            stream: _currentMessage.stream,
            builder: (context, snapshot) {
              if(snapshot.hasData){
                return new Text(snapshot.data);
              }
              return new Text("Empty");
            }
          )
        ],
      ),),
    )
    ;
  }

  Future<Null> _showNativeView() async {
    await platform.invokeMethod('showNativeView');
  }

  Future<String> _handleMethod(MethodCall call) async {
    switch(call.method) {
      case "message":
        _currentMessage.add(call.arguments.toString());
        print(call.arguments.toString());
        return call.arguments.toString();
    }
  }

}

