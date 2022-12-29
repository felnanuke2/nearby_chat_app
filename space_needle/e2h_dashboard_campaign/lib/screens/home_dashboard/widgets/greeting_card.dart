import 'package:flutter/material.dart';

import 'package:e2h_dashboard_campaign/api/entities/greeting_entity.dart';

class GreetingCard extends StatelessWidget {
  const GreetingCard({
    Key? key,
    required this.greeting,
  }) : super(key: key);
  final GreetingEntity greeting;

  @override
  Widget build(BuildContext context) {
    return Card(
      child: Padding(
        padding: const EdgeInsets.all(8.0),
        child: Column(
          children: [
            Text(greeting.description),
            Expanded(
              child: Image.network(greeting.imageUri),
            ),
          ],
        ),
      ),
    );
  }
}
