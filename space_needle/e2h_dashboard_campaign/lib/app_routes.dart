import 'package:flutter/material.dart';

import 'screens/home_dashboard/home_dashboard.dart';

class AppRoutes {
  AppRoutes._();

  static const String home = '/';
  static Map<String, WidgetBuilder> get routes => {
        home: (context) => const HomeDashboard(),
      };
}
