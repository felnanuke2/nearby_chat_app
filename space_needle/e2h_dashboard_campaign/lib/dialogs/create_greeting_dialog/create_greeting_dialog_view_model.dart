import 'package:e2h_dashboard_campaign/api/entities/greeting_entity.dart';
import 'package:e2h_dashboard_campaign/api/repositories/repository.dart';
import 'package:flutter/material.dart';
import 'package:get/get_state_manager/get_state_manager.dart';
import 'package:get/route_manager.dart';
import 'package:string_validator/string_validator.dart';

class CreateGreetingDialogViewModel extends GetxController {
  final imageUriTEC = TextEditingController();
  final idTEC = TextEditingController();
  final videoUriTEC = TextEditingController();
  final descriptionTEC = TextEditingController();
  final nameTEC = TextEditingController();

  final imageUriKey = GlobalKey<FormState>();

  bool invalidImage = false;

  bool invalidVideo = false;

  String? get videUri => videoUriTEC.text.isEmpty ? null : videoUriTEC.text;

  String? get imageUri => imageUriTEC.text.isEmpty ? null : imageUriTEC.text;

  final videoUriKey = GlobalKey<FormState>();

  final formKey = GlobalKey<FormState>();

  bool loading = false;

  void create() async {
    if (!formKey.currentState!.validate()) return;
    if (!imageUriKey.currentState!.validate()) return;
    if (!videoUriKey.currentState!.validate()) return;
    if (imageUri == null) return;
    if (videUri == null) return;
    final GreetingEntity greeting = GreetingEntity(
      title: nameTEC.text.trim(),
      id: idTEC.text.trim(),
      description: descriptionTEC.text,
      imageUri: imageUri!.trim(),
      videoUri: videUri!.trim(),
    );
    loading = true;
    update();
    try {
      final result = await Repository().createGreeting(greeting);
      Get.back(result: result);
    } catch (e) {}
    loading = false;
    update();
  }

  String? validateImageUri(String? value) {
    if (isURL(value!)) return null;

    return 'URL inválida';
  }

  void onChangedImageUri(String value) {
    if (!imageUriKey.currentState!.validate()) return;
    update();
  }

  String? validateVideoUri(String? value) {
    if (isURL(value!)) return null;
    return 'URL inválida';
  }

  void onChangedVideoUri(String value) {
    if (!videoUriKey.currentState!.validate()) return;
    update();
  }

  String? valudateId(String? value) {
    if (value!.length > 4) return null;
    return 'ID deve ter mais de 4 caracteres';
  }

  String? validateName(String? value) {
    if (value!.length >= 2) return null;
    return 'NOME deve ter ao menos 2 caracteres';
  }
}
