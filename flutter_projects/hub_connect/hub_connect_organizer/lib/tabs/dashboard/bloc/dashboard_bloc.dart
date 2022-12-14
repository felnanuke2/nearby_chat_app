import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:get/get.dart';
import 'package:hub_connect_common/widgets/dialogs/expired_session_dialog.dart';
import 'package:hub_connect_organizer/tabs/dashboard/bloc/dashboard_events.dart';
import 'package:hub_connect_organizer/tabs/dashboard/bloc/dashboard_states.dart';
import 'package:hub_connect_sdk/core/domain/exceptions/expired_token_exception.dart';
import 'package:hub_connect_sdk/core/domain/exceptions/sdk_exception.dart';
import 'package:hub_connect_sdk/core/domain/repositories/indicators_repository.dart';

class DashboardBloc extends Bloc<DashboardEvent, DashboardState> {
  final IndicatorsRepository _indicatorsRepository = Get.find();

  DashboardBloc() : super(DashboardInitial()) {
    on<FetchIndicators>((event, emit) {
      _getIndicators();
      emit.call(DashboardLoading());
    });

    on<LoadedFetchIndicators>((event, emit) {
      emit.call(DashboardLoaded(event.indicators));
    });

    on<ChangeChartEvent>((event, emit) {
      emit.call(
          DashboardLoaded(event.indicators, chartIndex: event.chartIndex));
    });

    add(FetchIndicators());
  }

  Future<void> _getIndicators() async {
    try {
      final indicators = await _indicatorsRepository.getIndicators();
      add(LoadedFetchIndicators(indicators: indicators));
    } on SdkException catch (e, s) {
      onError(e, s);
      rethrow;
    } catch (e, s) {
      onError(e, s);
      rethrow;
    }
  }

  @override
  void onError(Object error, StackTrace stackTrace) async {
    if (error is ExpiredTokenException) {
      await ExpiredSessionDialog.show();
      Get.toNamed('/login');
    }
    super.onError(error, stackTrace);
  }
}
