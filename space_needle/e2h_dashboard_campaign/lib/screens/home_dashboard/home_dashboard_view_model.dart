import 'package:e2h_dashboard_campaign/api/entities/greeting_entity.dart';
import 'package:e2h_dashboard_campaign/api/entities/settings_entity.dart';
import 'package:e2h_dashboard_campaign/api/repositories/repository.dart';
import 'package:e2h_dashboard_campaign/dialogs/create_greeting_dialog/create_greeting_dialog.dart';
import 'package:flutter/material.dart';
import 'package:get/get_state_manager/get_state_manager.dart';
import 'package:get/route_manager.dart';
import 'package:intl/intl.dart';

class HomeDashboardViewModel extends GetxController {
  final formKey = GlobalKey<FormState>();

  HomeDashboardViewModel() {
    _getInitialSettings();
  }
  final _repository = Repository();

  late SettingsEntity settings;

  final initialDateTEC = TextEditingController();
  DateTime? _initialDate;
  DateTime? _endDate;

  List<GreetingEntity> greetings = [];

  bool isGridExpanded = false;

  final finalDateTEC = TextEditingController();

  final scrollController = ScrollController();

  set _setInitialDate(DateTime dateTime) {
    initialDateTEC.text = DateFormat('dd/MM/yyyy').format(dateTime);
    _initialDate = dateTime;
  }

  set _setEndDate(DateTime dateTime) {
    finalDateTEC.text = DateFormat('dd/MM/yyyy').format(dateTime);
    _endDate = dateTime;
  }

  final Map<int, GreetingEntity> selectedGreetingsCoins = {};
  final Map<int, GreetingEntity> selectedGreetingsVouchers = {};

  void createGreetingCard() {
    Get.dialog(
      const CreateGreetingDialog(),
    );
  }

  int get campaignDays => _endDate != null
      ? _endDate!.difference(_initialDate ?? DateTime.now()).inDays
      : 0;

  Future<List<GreetingEntity>> getGreetings() async {
    greetings = await _repository.getAllGreetings();

    return greetings;
  }

  void pickFirstDate() async {
    final date = await showDatePicker(
      context: Get.context!,
      initialDate: DateTime.now(),
      firstDate: DateTime.now(),
      lastDate: DateTime.now().add(
        const Duration(days: 360),
      ),
    );
    if (date != null) _setInitialDate = date;
    update();
  }

  void pickLastDate() async {
    final date = await showDatePicker(
      context: Get.context!,
      initialDate: _initialDate!.add(const Duration(hours: 24)),
      firstDate: _initialDate!.add(const Duration(hours: 24)),
      lastDate: DateTime.now().add(
        const Duration(days: 360),
      ),
    );
    if (date != null) _setEndDate = date;
    update();
  }

  void onAcceptedCoin(GreetingEntity data, int index) {
    selectedGreetingsCoins[index] = data;
    update();
  }

  void removeGreetingCoin(int index) {
    selectedGreetingsCoins.remove(index);
    update();
  }

  _getInitialSettings() async {
    settings = await _repository.getSettings();
    _setInitialDate = settings.start_date;
    _setEndDate = settings.end_date;
    update();
    await getGreetings();
    final mock = List.generate(campaignDays, (index) => index);
    for (final item in mock) {
      try {
        selectedGreetingsCoins[item] = greetings.firstWhere((element) =>
            element.id ==
            settings.extras[
                'coin-' + DateFormat('dd/MM/yyyy').format(_getDateDay(item))]);

        selectedGreetingsVouchers[item] = greetings.firstWhere((element) =>
            element.id ==
            settings.extras['voucher-' +
                DateFormat('dd/MM/yyyy').format(_getDateDay(item))]);
      } catch (e) {}
    }
    update();
  }

  DateTime _getDateDay(int index) => _initialDate!.add(Duration(days: index));

  String getDay(int index) =>
      DateFormat(DateFormat.YEAR_ABBR_MONTH_WEEKDAY_DAY, 'pt_BR')
          .format(_getDateDay(index));

  void onDragStarted() => toggleGridExpand();

  void toggleGridExpand() {
    isGridExpanded = !isGridExpanded;
    update();
  }

  void save() async {
    Map<String, String> values = {};
    for (final key in selectedGreetingsCoins.keys) {
      final greeting = selectedGreetingsCoins[key];
      final date = DateFormat('dd/MM/yyyy').format(_getDateDay(key));
      values['coin-' + date] = greeting!.id;
    }
    print(selectedGreetingsVouchers.length);
    for (final key in selectedGreetingsVouchers.keys) {
      final greeting = selectedGreetingsVouchers[key];
      final date = DateFormat('dd/MM/yyyy').format(_getDateDay(key));
      values['voucher-' + date] = greeting!.id;
    }
    final settings = SettingsEntity(
      id: 'global-dates',
      label: 'settings',
      start_date: _initialDate!,
      end_date: _endDate!,
    )..extras = values;
    await _repository.saveSettings(settings);
  }

  String? validateInitialDate(String? value) {
    if (_initialDate != null) return null;
    return 'Data inválida';
  }

  String? validateEndDate(String? value) {
    if (_endDate != null) return null;
    return 'Data inválida';
  }

  onAcceptedVoucher(GreetingEntity data, int index) {
    selectedGreetingsVouchers[index] = data;
    update();
  }

  removeGreetingVoucher(int index) {
    selectedGreetingsVouchers.remove(index);
    update();
  }
}
