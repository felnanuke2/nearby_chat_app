import 'package:chewie/chewie.dart';
import 'package:e2h_dashboard_campaign/dialogs/create_greeting_dialog/create_greeting_dialog_view_model.dart';
import 'package:flutter/material.dart';
import 'package:get/get_state_manager/get_state_manager.dart';
import 'package:video_player/video_player.dart';

class CreateGreetingDialog extends StatelessWidget {
  const CreateGreetingDialog({Key? key}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return GetBuilder<CreateGreetingDialogViewModel>(
      init: CreateGreetingDialogViewModel(),
      builder: (controller) {
        return LayoutBuilder(
          builder: (context, constraints) {
            return Dialog(
              insetPadding: const EdgeInsets.all(16),
              child: Container(
                constraints: const BoxConstraints(maxWidth: 380),
                child: Padding(
                  padding: const EdgeInsets.all(16.0),
                  child: SingleChildScrollView(
                    child: Column(
                      mainAxisSize: MainAxisSize.min,
                      children: [
                        if (controller.imageUri != null)
                          _buildImage(controller),
                        if (controller.videUri != null) _buildVideo(controller),
                        const SizedBox(
                          height: 24,
                        ),
                        _buildForm(controller),
                        const SizedBox(
                          height: 24,
                        ),
                        Align(
                          alignment: Alignment.centerRight,
                          child: ElevatedButton.icon(
                              onPressed: controller.create,
                              icon: const Icon(Icons.save),
                              label: const Text('Salvar')),
                        )
                      ],
                    ),
                  ),
                ),
              ),
            );
          },
        );
      },
    );
  }

  Image _buildImage(CreateGreetingDialogViewModel controller) {
    return Image.network(
      controller.imageUri!,
      fit: BoxFit.cover,
      errorBuilder: (BuildContext context, Object child, StackTrace? _) {
        controller.invalidImage = true;
        return const Center(child: Text('Erro ao carregar imagem'));
      },
      width: 100,
      height: 100,
    );
  }

  Form _buildForm(CreateGreetingDialogViewModel controller) {
    return Form(
      key: controller.formKey,
      child: Column(
        mainAxisSize: MainAxisSize.min,
        children: [
          TextFormField(
            enabled: !controller.loading,
            controller: controller.nameTEC,
            validator: controller.validateName,
            decoration:
                const InputDecoration(labelText: 'Nome', hintText: 'Ex: Vinci'),
          ),
          const SizedBox(
            height: 12,
          ),
          TextFormField(
            enabled: !controller.loading,
            controller: controller.idTEC,
            validator: controller.valudateId,
            decoration: const InputDecoration(
                labelText: 'ID', hintText: 'leo_greeting_card'),
          ),
          const SizedBox(
            height: 12,
          ),
          Form(
            key: controller.videoUriKey,
            child: TextFormField(
              enabled: !controller.loading,
              controller: controller.videoUriTEC,
              validator: controller.validateVideoUri,
              autovalidateMode: AutovalidateMode.onUserInteraction,
              onChanged: controller.onChangedVideoUri,
              decoration: const InputDecoration(labelText: 'Video URI'),
            ),
          ),
          const SizedBox(
            height: 12,
          ),
          Form(
            key: controller.imageUriKey,
            child: TextFormField(
              enabled: !controller.loading,
              controller: controller.imageUriTEC,
              decoration: const InputDecoration(labelText: 'Image URI'),
              validator: controller.validateImageUri,
              autovalidateMode: AutovalidateMode.onUserInteraction,
              onChanged: controller.onChangedImageUri,
            ),
          ),
          const SizedBox(
            height: 12,
          ),
          TextFormField(
            enabled: !controller.loading,
            controller: controller.descriptionTEC,
            minLines: 3,
            maxLines: 4,
            decoration: const InputDecoration(labelText: 'Description'),
          ),
        ],
      ),
    );
  }

  Widget _buildVideo(CreateGreetingDialogViewModel controller) {
    return Container(
      constraints: const BoxConstraints(maxHeight: 240),
      child: Chewie(
        controller: ChewieController(
          aspectRatio: 3 / 4,
          errorBuilder: (BuildContext context, String uri) {
            controller.invalidVideo = true;
            return const Center(child: Text('Erro ao carregar video'));
          },
          videoPlayerController:
              VideoPlayerController.network(controller.videUri!),
        ),
      ),
    );
  }
}
