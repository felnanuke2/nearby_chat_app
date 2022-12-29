import 'dart:convert';

class GreetingEntity {
  final String id;
  final String title;
  final String videoUri;
  final String imageUri;
  final String label;
  final String description;

  GreetingEntity({
    required this.id,
    required this.videoUri,
    required this.title,
    required this.imageUri,
    this.label = 'greeting',
    required this.description,
  });

  Map<String, dynamic> toMap() {
    return {
      'id': id,
      'videoUri': videoUri,
      'imageUri': imageUri.replaceAll(' ', '%2520'),
      'label': label,
      'description': description,
      'title': title,
    };
  }

  factory GreetingEntity.fromMap(Map<String, dynamic> map) {
    return GreetingEntity(
      description: map['description'] ?? '',
      id: map['id'] ?? '',
      videoUri: map['videoUri'] ?? '',
      imageUri: map['imageUri'] ?? '',
      label: map['label'] ?? '',
      title: map['title'] ?? '',
    );
  }

  String toJson() => json.encode(toMap());

  factory GreetingEntity.fromJson(String source) =>
      GreetingEntity.fromMap(json.decode(source));
}
