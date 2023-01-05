import 'package:hub_connect_sdk/core/domain/dtos/get_participants_request.dart';
import 'package:hub_connect_sdk/core/domain/dtos/get_participants_result.dart';

class ReportsEvent {}

class FetchParticipantsEvent extends ReportsEvent {
  GetParticipantsRequest request;
  FetchParticipantsEvent({
    required this.request,
  });
}

class ParticipantsLoadedEvent extends ReportsEvent {
  GetParticipantsResult response;

  ParticipantsLoadedEvent({
    required this.response,
  });
}

class ChangeReportFilterEvent extends ReportsEvent {
  int reportFilter;

  ChangeReportFilterEvent({
    required this.reportFilter,
  });
}

class ChangePageEvent extends ReportsEvent {
  int page;
  int reportFilter;

  ChangePageEvent({
    required this.page,
    required this.reportFilter,
  });
}
