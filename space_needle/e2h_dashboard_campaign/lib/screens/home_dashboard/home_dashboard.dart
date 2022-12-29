import 'dart:math';

import 'package:e2h_dashboard_campaign/api/entities/greeting_entity.dart';
import 'package:e2h_dashboard_campaign/screens/home_dashboard/home_dashboard_view_model.dart';
import 'package:e2h_dashboard_campaign/screens/home_dashboard/widgets/greeting_card.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:get/get_state_manager/get_state_manager.dart';

class HomeDashboard extends StatefulWidget {
  const HomeDashboard({Key? key}) : super(key: key);

  @override
  State<HomeDashboard> createState() => _HomeDashboardState();
}

class _HomeDashboardState extends State<HomeDashboard> {
  final swicherKey = GlobalKey();
  @override
  Widget build(BuildContext context) {
    final width = MediaQuery.of(context).size.width;
    return GetBuilder<HomeDashboardViewModel>(
        init: HomeDashboardViewModel(),
        builder: (controller) {
          return Scaffold(
            body: LayoutBuilder(
              builder: (context, constraints) {
                return Stack(
                  children: [
                    Positioned.fill(
                      left: 80,
                      top: 24,
                      bottom: 24,
                      right: 40,
                      child: Column(
                        children: [
                          _buildHeaders(controller),
                          const SizedBox(
                            height: 24,
                          ),
                          _buildCampaignWrap(controller),
                          const SizedBox(
                            height: 24,
                          ),
                        ],
                      ),
                    ),
                    AnimatedPositioned(
                        left: 0,
                        right: controller.isGridExpanded ? 0 : width - 40,
                        top: 0,
                        bottom: 0,
                        child: _buildGridView(controller),
                        duration: Duration(milliseconds: 300))
                  ],
                );
              },
            ),
          );
        });
  }

  Align _buildHeaders(HomeDashboardViewModel controller) {
    return Align(
      alignment: Alignment.centerRight,
      child: Form(
        key: controller.formKey,
        child: Wrap(
          alignment: WrapAlignment.end,
          spacing: 16,
          children: [
            SizedBox(
              width: 250,
              child: TextFormField(
                validator: controller.validateInitialDate,
                controller: controller.initialDateTEC,
                readOnly: true,
                onTap: controller.pickFirstDate,
                decoration: const InputDecoration(
                  labelText: 'Início da Campanha',
                  suffixIcon: Icon(Icons.calendar_month),
                ),
              ),
            ),
            const SizedBox(
              width: 16,
            ),
            SizedBox(
              width: 250,
              child: TextFormField(
                validator: controller.validateEndDate,
                enabled: controller.initialDateTEC.text.isNotEmpty,
                controller: controller.finalDateTEC,
                readOnly: true,
                onTap: controller.pickLastDate,
                decoration: const InputDecoration(
                  labelText: 'Final da Campanha',
                  suffixIcon: Icon(Icons.calendar_month),
                ),
              ),
            ),
            ElevatedButton.icon(
                onPressed: controller.createGreetingCard,
                icon: const Icon(Icons.add),
                label: const Text('Criar cartão de comemoração')),
            ElevatedButton.icon(
              style: ElevatedButton.styleFrom(primary: Colors.green),
              onPressed: controller.save,
              icon: const Icon(Icons.save_alt),
              label: const Text('Salvar'),
            ),
          ],
        ),
      ),
    );
  }

  Expanded _buildCampaignWrap(HomeDashboardViewModel controller) {
    return Expanded(
      child: GridView(
        gridDelegate: const SliverGridDelegateWithFixedCrossAxisCount(
          crossAxisCount: 3,
          crossAxisSpacing: 16,
          mainAxisSpacing: 16,
        ),
        children: [
          ...List.generate(
              controller.campaignDays,
              (index) => Column(
                    children: [
                      Expanded(
                        child: DragTarget<GreetingEntity>(
                          onAccept: (data) =>
                              controller.onAcceptedCoin(data, index),
                          builder: (
                            context,
                            candidateData,
                            rejectedData,
                          ) {
                            Widget? child;
                            bool haveData = false;
                            if (controller.selectedGreetingsCoins
                                .containsKey(index)) {
                              child = GreetingCard(
                                greeting:
                                    controller.selectedGreetingsCoins[index]!,
                              );
                              haveData = true;
                            } else {
                              child = const Center(
                                  child: Text('Arraste para cá (Moeda)'));
                            }
                            return Column(
                              children: [
                                Text(controller.getDay(index)),
                                Expanded(
                                  child: Card(
                                    child: Stack(
                                      children: [
                                        Positioned.fill(
                                          child: Container(
                                            child: child,
                                          ),
                                        ),
                                        if (haveData)
                                          Positioned(
                                            right: 8,
                                            top: 8,
                                            child: IconButton(
                                                onPressed: () => controller
                                                    .removeGreetingCoin(index),
                                                icon: const Icon(
                                                  Icons.remove,
                                                  color: Colors.red,
                                                )),
                                          )
                                      ],
                                    ),
                                  ),
                                ),
                              ],
                            );
                          },
                        ),
                      ),
                      Expanded(
                        child: DragTarget<GreetingEntity>(
                          onAccept: (data) =>
                              controller.onAcceptedVoucher(data, index),
                          builder: (
                            context,
                            candidateData,
                            rejectedData,
                          ) {
                            Widget? child;
                            bool haveData = false;
                            if (controller.selectedGreetingsVouchers
                                .containsKey(index)) {
                              child = GreetingCard(
                                greeting: controller
                                    .selectedGreetingsVouchers[index]!,
                              );
                              haveData = true;
                            } else {
                              child = const Center(
                                  child: Text('Arraste para cá (Voucher)'));
                            }
                            return Column(
                              children: [
                                Text(controller.getDay(index)),
                                Expanded(
                                  child: Card(
                                    child: Stack(
                                      children: [
                                        Positioned.fill(
                                          child: Container(
                                            child: child,
                                          ),
                                        ),
                                        if (haveData)
                                          Positioned(
                                            right: 8,
                                            top: 8,
                                            child: IconButton(
                                                onPressed: () => controller
                                                    .removeGreetingVoucher(
                                                        index),
                                                icon: const Icon(
                                                  Icons.remove,
                                                  color: Colors.red,
                                                )),
                                          )
                                      ],
                                    ),
                                  ),
                                ),
                              ],
                            );
                          },
                        ),
                      ),
                    ],
                  ))
        ],
      ),
    );
  }

  Widget _buildGridView(HomeDashboardViewModel controller) {
    return LayoutBuilder(builder: (context, constraints) {
      return Container(
        color: Colors.grey.withOpacity(0.2),
        child: Row(
          children: [
            IconButton(
                onPressed: controller.toggleGridExpand,
                icon: AnimatedRotation(
                    turns: controller.isGridExpanded ? 0.5 : 0,
                    duration: const Duration(milliseconds: 300),
                    child: const Icon(CupertinoIcons.chevron_right))),
            Expanded(
              child: AnimatedSwitcher(
                key: swicherKey,
                duration: Duration(milliseconds: 300),
                child: constraints.maxWidth < 300
                    ? Container()
                    : FutureBuilder<List<GreetingEntity>>(
                        future: controller.getGreetings(),
                        initialData: controller.greetings,
                        builder: (context, snapshot) {
                          return GridView.builder(
                              padding: const EdgeInsets.all(8),
                              itemCount: snapshot.data?.length ?? 0,
                              gridDelegate:
                                  SliverGridDelegateWithFixedCrossAxisCount(
                                      crossAxisCount: 7, childAspectRatio: 1),
                              itemBuilder: (c, i) {
                                final greeting = snapshot.data![i];
                                return Draggable<GreetingEntity>(
                                  onDragStarted: controller.onDragStarted,
                                  onDragCompleted: controller.onDragStarted,
                                  data: greeting,
                                  feedback: SizedBox(
                                    width: 200,
                                    height: 200,
                                    child: GreetingCard(greeting: greeting),
                                  ),
                                  child: GreetingCard(greeting: greeting),
                                );
                              });
                        }),
              ),
            ),
          ],
        ),
      );
    });
  }

  _getCrossAxisCount(double size) {
    final width = size;

    return width ~/ 200;
  }
}
