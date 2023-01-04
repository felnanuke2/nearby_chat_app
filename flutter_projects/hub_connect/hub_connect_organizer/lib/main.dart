import 'package:flutter/material.dart';
import 'package:get/route_manager.dart';
import 'package:hub_connect_common/themes/app_theme.dart';

import 'pages.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return GetMaterialApp(
      getPages: OrganizerPages.pages,
      title: 'Hub Connect Organizer',
      theme: HubConnectThemes.light,
      initialRoute: '/',
    );
  }
}
