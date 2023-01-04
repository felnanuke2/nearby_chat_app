import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:flutter_svg/svg.dart';
import 'package:hub_connect_common/themes/colors/colors.dart';
import 'package:hub_connect_organizer/tabs/dashboard/bloc/dashboard_bloc.dart';
import 'package:hub_connect_organizer/tabs/dashboard/bloc/dashboard_states.dart';
import 'package:hub_connect_organizer/widgets/indicator_bar_chart_horizontal.dart';
import 'package:hub_connect_organizer/widgets/indicator_card.dart';
import 'package:hub_connect_organizer/widgets/indicator_pie_chart.dart';
import 'package:hub_connect_organizer/widgets/indicators_bar_chart.dart';
import 'package:hub_connect_sdk/core/domain/entities/indicators_entity.dart';

import 'bloc/dashboard_events.dart';

class DashboardTab extends StatefulWidget {
  const DashboardTab({super.key});

  @override
  State<DashboardTab> createState() => _DashboardTabState();
}

class _DashboardTabState extends State<DashboardTab> {
  final _bloc = DashboardBloc();
  @override
  Widget build(BuildContext context) {
    return BlocBuilder<DashboardBloc, DashboardState>(
      bloc: _bloc,
      builder: (context, state) {
        if (state is DashboardInitial) {
          return const Center(
            child: CircularProgressIndicator(),
          );
        } else if (state is DashboardLoading) {
          return const Center(
            child: CircularProgressIndicator(),
          );
        } else if (state is DashboardLoaded) {
          return _buildIndicators(state.indicators, state.chartIndex);
        } else if (state is DashboardError) {
          return Center(
            child: Text(state.error.toString()),
          );
        } else {
          return const Center(
            child: Text('Unknown state'),
          );
        }
      },
    );
  }

  Widget _buildIndicators(
    IndicatorsEntity indicators,
    int chart,
  ) {
    return RefreshIndicator(
      onRefresh: _onRefresh,
      child: SingleChildScrollView(
        child: Padding(
          padding: const EdgeInsets.only(top: 16, bottom: 80),
          child: Column(
            children: [
              Wrap(
                spacing: 8,
                alignment: WrapAlignment.spaceEvenly,
                children: [
                  IndicatorCard(
                    value: indicators.totalParticipants,
                    title: 'Total de Participantes',
                    icon: SvgPicture.asset(
                      width: 14,
                      height: 14,
                      'assets/svg/total_users.svg',
                      package: 'hub_connect_common',
                    ),
                    avatarColor: HubConnectColors.linkPrimary,
                  ),
                  IndicatorCard(
                    value: indicators.presentParticipants,
                    title: 'Participantes presentes',
                    icon: SvgPicture.asset(
                      width: 14,
                      height: 14,
                      'assets/svg/present_users.svg',
                      package: 'hub_connect_common',
                    ),
                    avatarColor: HubConnectColors.successColor,
                  ),
                  IndicatorCard(
                    value: indicators.accreditedParticipants,
                    title: 'Participantes Credenciados',
                    icon: SvgPicture.asset(
                      width: 18,
                      height: 18,
                      'assets/svg/acredited_participants.svg',
                      package: 'hub_connect_common',
                    ),
                    avatarColor: HubConnectColors.primary,
                  ),
                  IndicatorCard(
                    value: indicators.absentParticipants,
                    title: 'Participantes ausentes',
                    icon: SvgPicture.asset(
                      width: 14,
                      height: 14,
                      'assets/svg/absent_participants.svg',
                      package: 'hub_connect_common',
                    ),
                    avatarColor: HubConnectColors.errorColor,
                  ),
                ],
              ),
              const SizedBox(
                height: 20,
              ),
              Padding(
                padding: const EdgeInsets.all(16.0),
                child: Align(
                  alignment: Alignment.centerRight,
                  child: DropdownButton(
                    value: chart,
                    items: const [
                      DropdownMenuItem(
                        value: 0,
                        child: Text('Barras (Vertical)'),
                      ),
                      DropdownMenuItem(
                        value: 1,
                        child: Text('Barras (Horizontal)'),
                      ),
                      DropdownMenuItem(
                        value: 2,
                        child: Text('Pizza'),
                      ),
                    ],
                    onChanged: (chart) {
                      _onChangeChart(chart, indicators);
                    },
                  ),
                ),
              ),
              AspectRatio(
                aspectRatio: 1.4,
                child: Padding(
                  padding: const EdgeInsets.all(18.0),
                  child: Builder(
                    builder: (context) {
                      switch (chart) {
                        case 0:
                          return IndicatorsBarChart(
                            indicators: indicators,
                          );
                        case 1:
                          return IndicatorBarChartHorizontal(
                            indicators: indicators,
                          );

                        case 2:
                          return IndicatorPieChart(
                            indicators: indicators,
                          );

                        default:
                          return const SizedBox.shrink();
                      }
                    },
                  ),
                ),
              )
            ],
          ),
        ),
      ),
    );
  }

  void _onChangeChart(int? chart, IndicatorsEntity indicators) {
    _bloc.add(
      ChangeChartEvent(chartIndex: chart!, indicators: indicators),
    );
  }

  Future<void> _onRefresh() async {
    _bloc.add(
      FetchIndicators(),
    );
  }
}
