import 'package:flutter/material.dart';

import 'package:hub_connect_common/themes/typography/typography.dart';

class IndicatorCard extends StatelessWidget {
  const IndicatorCard({
    Key? key,
    required this.value,
    required this.title,
    required this.icon,
    required this.avatarColor,
  }) : super(key: key);
  final int value;
  final String title;
  final Widget icon;
  final Color avatarColor;

  @override
  Widget build(BuildContext context) {
    return SizedBox(
      width: 180,
      height: 180,
      child: Card(
        child: Padding(
          padding: const EdgeInsets.all(12.0),
          child: Column(
            mainAxisAlignment: MainAxisAlignment.spaceBetween,
            children: [
              CircleAvatar(
                radius: 24,
                backgroundColor: avatarColor,
                foregroundColor: Colors.white,
                child: icon,
              ),
              Text(
                title,
                textAlign: TextAlign.center,
                style: HubConnectTypography.normal20.copyWith(fontSize: 14),
              ),
              Text(
                value.toString(),
                style: HubConnectTypography.bold20,
              ),
            ],
          ),
        ),
      ),
    );
  }
}
