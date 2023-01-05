import 'package:hub_connect_sdk/core/domain/entities/participant_entity.dart';

class ReportState {
  final int reportFilter;
  final int categoryFilter;

  ReportState({
    this.reportFilter = 0,
    this.categoryFilter = 0,
  });
}

class ReportsInitialState extends ReportState {}

class ReportLoadingDataState extends ReportState {
  ReportLoadingDataState({
    super.reportFilter,
  });
}

class ReportsLoadedState extends ReportState {
  final List<ParticipantEntity> participants;
  final int currentPage;
  final int totalPages;
  final int? nextPage;

  ReportsLoadedState({
    required this.participants,
    this.currentPage = 0,
    this.totalPages = 0,
    this.nextPage,
    super.reportFilter,
  });
}
