import 'dart:convert';

class SettingsEntity {
  String id;
  String label;
  DateTime start_date;
  DateTime end_date;
  Map<String, String> extras;
  SettingsEntity({
    required this.id,
    this.label = 'settings',
    required this.start_date,
    required this.end_date,
    this.extras = const {},
  });

  Map<String, dynamic> toMap() {
    return {
      'id': id,
      'label': label,
      'start_date': start_date.toIso8601String(),
      'end_date': end_date.toIso8601String(),
    }..addAll(extras);
  }

  factory SettingsEntity.fromMap(Map<String, dynamic> map) {
    return SettingsEntity(
        id: map['id'] ?? '',
        label: map['label'] ?? '',
        start_date: DateTime.parse(map['start_date']),
        end_date: DateTime.parse(map['end_date']),
        extras: Map<String, String>.from(map));
  }

  String toJson() => json.encode(toMap());

  factory SettingsEntity.fromJson(String source) =>
      SettingsEntity.fromMap(json.decode(source));
}
