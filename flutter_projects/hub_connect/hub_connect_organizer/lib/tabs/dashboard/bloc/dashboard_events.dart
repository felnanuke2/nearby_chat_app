import 'package:hub_connect_sdk/core/domain/entities/indicators_entity.dart';

class DashboardEvent {}

class FetchIndicators extends DashboardEvent {}

class LoadingFetchIndicators extends DashboardEvent {}

class LoadedFetchIndicators extends DashboardEvent {
  final IndicatorsEntity indicators;
  LoadedFetchIndicators({
    required this.indicators,
  });
}

class ChangeChartEvent extends DashboardEvent {
  final IndicatorsEntity indicators;
  final int chartIndex;
  ChangeChartEvent({
    required this.indicators,
    required this.chartIndex,
  });
}
