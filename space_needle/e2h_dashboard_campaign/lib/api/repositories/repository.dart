import 'dart:convert';

import 'package:e2h_dashboard_campaign/api/entities/greeting_entity.dart';
import 'package:e2h_dashboard_campaign/api/entities/settings_entity.dart';
import 'package:http/http.dart' as http;

class Repository {
  Future<GreetingEntity> createGreeting(GreetingEntity entity) async {
    final response = await http.post(
        Uri.parse('https://e2h-objects.azurewebsites.net/greetings'),
        headers: {
          'Content-Type': 'application/json',
          'api-key':
              '50B7AA76A52EE326CBF4EC4E8CB2024E331C4D5135258E4157CEA1FD838EFDE6089BFBA2B8BDB9CD53A86B3B08639D1E2790A827506A401A8371032576C2C9B0',
        },
        body: entity.toJson());
    if (response.statusCode == 200) {
      return GreetingEntity.fromJson(response.body);
    }

    throw Exception('Falha ao criar cartão de comemoração');
  }

  Future<List<GreetingEntity>> getAllGreetings() async {
    final response = await http.get(
      Uri.parse('https://e2h-objects.azurewebsites.net/greetings'),
      headers: {
        'api-key':
            '50B7AA76A52EE326CBF4EC4E8CB2024E331C4D5135258E4157CEA1FD838EFDE6089BFBA2B8BDB9CD53A86B3B08639D1E2790A827506A401A8371032576C2C9B0',
      },
    );
    if (response.statusCode == 200) {
      return List.from((jsonDecode(response.body))
          .map((e) => GreetingEntity.fromMap(e))
          .toList());
    }

    throw Exception('Falha ao criar cartão de comemoração');
  }

  Future<SettingsEntity> getSettings() async {
    final response = await http.get(
      Uri.parse('https://e2h-objects.azurewebsites.net/settings/global-dates'),
      headers: {
        'api-key':
            '50B7AA76A52EE326CBF4EC4E8CB2024E331C4D5135258E4157CEA1FD838EFDE6089BFBA2B8BDB9CD53A86B3B08639D1E2790A827506A401A8371032576C2C9B0',
      },
    );
    if (response.statusCode == 200) {
      return SettingsEntity.fromJson(response.body);
    }

    throw Exception('Falha ao criar cartão de comemoração');
  }

  Future<void> saveSettings(SettingsEntity settings) async {
    final response = await http.put(
        Uri.parse(
            'https://e2h-objects.azurewebsites.net/settings/global-dates'),
        headers: {
          'Content-Type': 'application/json',
          'api-key':
              '50B7AA76A52EE326CBF4EC4E8CB2024E331C4D5135258E4157CEA1FD838EFDE6089BFBA2B8BDB9CD53A86B3B08639D1E2790A827506A401A8371032576C2C9B0',
        },
        body: settings.toJson());
    if (response.statusCode == 200) {
      return;
    }

    throw Exception('Falha ao criar cartão de comemoração');
  }
}
