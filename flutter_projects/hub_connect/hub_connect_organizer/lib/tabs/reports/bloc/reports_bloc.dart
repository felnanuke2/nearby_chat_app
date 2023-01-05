import 'dart:async';

import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:get/get.dart';
import 'package:hub_connect_organizer/tabs/reports/bloc/reports_events.dart';
import 'package:hub_connect_organizer/tabs/reports/bloc/reports_states.dart';
import 'package:hub_connect_sdk/core/domain/dtos/get_participants_request.dart';
import 'package:hub_connect_sdk/core/domain/exceptions/expired_token_exception.dart';
import 'package:hub_connect_sdk/core/domain/repositories/participant_repository.dart';
import 'package:hub_connect_common/widgets/dialogs/expired_session_dialog.dart';

class ReportsBloc extends Bloc<ReportsEvent, ReportState> {
  final ParticipantsRepository _participantRepository = Get.find();

  ReportsBloc() : super(ReportsInitialState()) {
    on<FetchParticipantsEvent>(_getParticipants);

    on<ChangeReportFilterEvent>(_onChangeReportFilter);

    on<ChangePageEvent>(_onChangePage);

    add(
      FetchParticipantsEvent(
        request: GetParticipantsRequest(),
      ),
    );
  }

  FutureOr<void> _getParticipants(
      FetchParticipantsEvent event, Emitter<ReportState> emit) async {
    try {
      emit.call(
        ReportLoadingDataState(),
      );

      final response = await _participantRepository.getParticipants(
        event.request,
      );
      emit.call(
        ReportsLoadedState(
          participants: response.participants,
          currentPage: response.page,
          nextPage: response.nextPage,
          totalPages: response.totalPages,
        ),
      );
    } catch (e, s) {
      onError(e, s);
      rethrow;
    }
  }

  FutureOr<void> _onChangeReportFilter(
      ChangeReportFilterEvent event, Emitter<ReportState> emit) async {
    try {
      emit.call(
        ReportLoadingDataState(
          reportFilter: event.reportFilter,
        ),
      );
      final response = await _participantRepository.getParticipants(
        GetParticipantsRequest(
          onlyCredentials: event.reportFilter == 1,
        ),
      );
      emit.call(
        ReportsLoadedState(
          participants: response.participants,
          reportFilter: event.reportFilter,
          currentPage: response.page,
          nextPage: response.nextPage,
          totalPages: response.totalPages,
        ),
      );
    } catch (e, s) {
      onError(e, s);
      rethrow;
    }
  }

  FutureOr<void> _onChangePage(
      ChangePageEvent event, Emitter<ReportState> emit) async {
    try {
      emit.call(
        ReportLoadingDataState(
          reportFilter: event.reportFilter,
        ),
      );
      final response = await _participantRepository.getParticipants(
        GetParticipantsRequest(
          pageIndex: event.page,
          onlyCredentials: event.reportFilter == 1,
        ),
      );
      emit.call(
        ReportsLoadedState(
          participants: response.participants,
          currentPage: response.page,
          nextPage: response.nextPage,
          totalPages: response.totalPages,
          reportFilter: event.reportFilter,
        ),
      );
    } catch (e, s) {
      onError(e, s);
      rethrow;
    }
  }

  @override
  void onError(Object error, StackTrace stackTrace) async {
    if (error is ExpiredTokenException) {
      await ExpiredSessionDialog.show();
      Get.offAllNamed('/login');
    }
    super.onError(error, stackTrace);
  }
}
