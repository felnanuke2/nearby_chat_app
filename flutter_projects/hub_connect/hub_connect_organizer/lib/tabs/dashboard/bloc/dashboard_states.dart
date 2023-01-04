import 'package:hub_connect_sdk/core/domain/entities/indicators_entity.dart';

class DashboardState {}

class DashboardInitial extends DashboardState {}

class DashboardLoading extends DashboardState {}

class DashboardLoaded extends DashboardState {
  final IndicatorsEntity indicators;
  final int chartIndex;

  DashboardLoaded(this.indicators, {this.chartIndex = 0});
}

class DashboardError extends DashboardState {
  final Object error;

  DashboardError(this.error);
}
