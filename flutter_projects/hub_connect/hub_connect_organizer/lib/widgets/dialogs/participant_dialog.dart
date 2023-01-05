import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:hub_connect_common/themes/colors/colors.dart';
import 'package:hub_connect_common/themes/typography/typography.dart';
import 'package:hub_connect_sdk/core/domain/entities/participant_entity.dart';
import 'package:hub_connect_common/utils/date_extensions.dart';

class ParticipantDialog extends StatelessWidget {
  const ParticipantDialog({
    Key? key,
    required this.participant,
  }) : super(key: key);

  final ParticipantEntity participant;

  @override
  Widget build(BuildContext context) {
    return Dialog(
      child: Column(
        mainAxisSize: MainAxisSize.min,
        children: [
          Align(
            alignment: Alignment.topRight,
            child: IconButton(
              onPressed: Get.back,
              icon: Icon(
                Icons.close,
                color: HubConnectColors.primary,
              ),
            ),
          ),
          Text(
            'Dados do Participante',
            style: HubConnectTypography.bold20,
          ),
          const SizedBox(
            height: 16,
          ),
          Padding(
            padding: const EdgeInsets.all(16.0),
            child: Column(
              mainAxisSize: MainAxisSize.min,
              mainAxisAlignment: MainAxisAlignment.center,
              children: [
                _buildRow('ID', participant.id),
                _buildRow('Nome', participant.name),
                _buildRow('Categoria', participant.category),
                _buildRow(
                  'Credenciamento',
                  participant.firstCredentialed?.normalizedDate() ?? 'Nunca',
                ),
                _buildRow('Nome No Crachá', participant.badgeName),
                if (participant.email != null)
                  _buildRow('Email', participant.email!),
                if (participant.phone != null)
                  _buildRow(
                    'Telefone',
                    participant.phone!,
                  ),
                if (participant.cpf != null)
                  _buildRow(
                    'CPF',
                    participant.cpf!,
                  ),
                _buildRow(
                  'Participou do Evento',
                  participant.attendedEvent ? 'Sim' : 'Não',
                ),
                _buildRow(
                  'Participante Credenciado',
                  participant.participantCredentialed ? 'Sim' : 'Não',
                ),
                _buildRow(
                  'Status',
                  participant.status ? 'Ativo' : 'Inativo',
                ),
                _buildRow(
                  'Código',
                  participant.code,
                ),
                _buildRow(
                  'Última Impressão',
                  participant.lastReprint?.normalizedDate() ?? 'Nunca',
                ),
              ],
            ),
          ),
        ],
      ),
    );
  }

  Widget _buildRow(
    String fieldName,
    String fieldValue, {
    bool copiable = false,
  }) {
    return Container(
      margin: const EdgeInsets.symmetric(vertical: 2),
      child: Column(
        children: [
          Row(
            children: [
              Text(
                '$fieldName:',
                style: HubConnectTypography.bold20.copyWith(
                  fontSize: 14,
                ),
              ),
              const SizedBox(
                width: 8,
              ),
              Expanded(
                child: SelectableText(
                  fieldValue,
                  style: HubConnectTypography.normal20.copyWith(
                    fontSize: 14,
                  ),
                ),
              ),
            ],
          ),
          Divider(
            color: HubConnectColors.secondary,
          ),
        ],
      ),
    );
  }

  static show(ParticipantEntity participant) {
    Get.dialog(
      ParticipantDialog(
        participant: participant,
      ),
    );
  }
}
