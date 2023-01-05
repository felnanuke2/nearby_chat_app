import 'package:flutter/material.dart';
import 'package:hub_connect_common/themes/colors/colors.dart';
import 'package:hub_connect_common/themes/typography/typography.dart';
import 'package:hub_connect_sdk/core/domain/entities/participant_entity.dart';

import 'dialogs/participant_dialog.dart';

class ParticipantListTile extends StatelessWidget {
  const ParticipantListTile({
    Key? key,
    required this.participant,
  }) : super(key: key);
  final ParticipantEntity participant;

  @override
  Widget build(BuildContext context) {
    return InkWell(
      onTap: _openDialog,
      child: Container(
        padding: const EdgeInsets.symmetric(
          vertical: 10,
          horizontal: 2,
        ),
        decoration: BoxDecoration(
          border: Border.all(
            color: Theme.of(context).colorScheme.onSurface.withOpacity(0.12),
          ),
          borderRadius: BorderRadius.circular(4),
        ),
        child: Row(
          children: [
            Expanded(
              child: Padding(
                padding: const EdgeInsets.all(4.0),
                child: Text(
                  participant.name,
                  style: HubConnectTypography.normal20.copyWith(
                    fontSize: 14,
                  ),
                  textAlign: TextAlign.center,
                ),
              ),
            ),
            Container(
              width: 1,
              height: 28,
              color: HubConnectColors.primary,
            ),
            Expanded(
              child: Padding(
                padding: const EdgeInsets.all(4.0),
                child: Text(
                  participant.category,
                  style: HubConnectTypography.normal20.copyWith(
                    fontSize: 14,
                  ),
                  textAlign: TextAlign.center,
                ),
              ),
            ),
            Container(
              width: 1,
              height: 28,
              color: HubConnectColors.primary,
            ),
            Expanded(
              child: Padding(
                padding: const EdgeInsets.all(4.0),
                child: Text(
                  participant.email ?? '',
                  style: HubConnectTypography.normal20.copyWith(
                    fontSize: 14,
                  ),
                  textAlign: TextAlign.center,
                ),
              ),
            ),
          ],
        ),
      ),
    );
  }

  void _openDialog() {
    ParticipantDialog.show(participant);
  }
}
