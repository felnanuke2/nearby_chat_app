import 'package:flutter/material.dart';

class AppTheme {
  AppTheme._();

  static ThemeData get theme => ThemeData(
        colorScheme: const ColorScheme.highContrastLight(
          primary: Color(0xff6E5C92),
        ),
        elevatedButtonTheme: elevatedButtonTheme,
        inputDecorationTheme: InputDecorationTheme(
          border: OutlineInputBorder(
            borderRadius: BorderRadius.circular(8),
          ),
        ),
      );

  static ElevatedButtonThemeData get elevatedButtonTheme =>
      ElevatedButtonThemeData(
        style: ElevatedButton.styleFrom(
            padding: const EdgeInsets.all(8),
            minimumSize: const Size(48, 48),
            textStyle:
                const TextStyle(fontWeight: FontWeight.bold, fontSize: 14)),
      );
}
