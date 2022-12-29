import 'package:e2h_dashboard_campaign/app_routes.dart';
import 'package:flutter/material.dart';
import 'package:get/route_manager.dart';
import 'package:intl/date_symbol_data_local.dart';

import 'theme/app_theme.dart';

void main() {
  initializeDateFormatting('pt_BR', null);
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({Key? key}) : super(key: key);

  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return GetMaterialApp(
      debugShowCheckedModeBanner: false,
      title: 'Flutter Demo',
      theme: AppTheme.theme,
      initialRoute: '/',
      routes: AppRoutes.routes,
    );
  }
}
