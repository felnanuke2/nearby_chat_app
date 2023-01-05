import 'package:flutter/material.dart';
import 'package:flutter_bloc/flutter_bloc.dart';
import 'package:font_awesome_flutter/font_awesome_flutter.dart';
import 'package:hub_connect_organizer/tabs/reports/bloc/reports_bloc.dart';
import 'package:hub_connect_organizer/tabs/reports/bloc/reports_events.dart';
import 'package:hub_connect_organizer/tabs/reports/bloc/reports_states.dart';
import 'package:hub_connect_organizer/widgets/participants_list_tile.dart';

import 'package:hub_connect_common/themes/colors/colors.dart';
import 'package:hub_connect_common/themes/typography/typography.dart';
import 'package:hub_connect_common/widgets/labeled_widget.dart';
import 'package:hub_connect_common/widgets/pagination_widget.dart';

class ReportsTab extends StatefulWidget {
  const ReportsTab({super.key});

  @override
  State<ReportsTab> createState() => _ReportsTabState();
}

class _ReportsTabState extends State<ReportsTab> {
  final _bloc = ReportsBloc();

  @override
  void initState() {
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return BlocBuilder<ReportsBloc, ReportState>(
      bloc: _bloc,
      builder: (context, state) {
        return Scrollbar(
          child: CustomScrollView(
            slivers: [
              _buildFilters(state.reportFilter),
              _buildHeader(),
              if (state is ReportLoadingDataState) _buildLoadingSpinner(),
              if (state is ReportsLoadedState) ...[
                _buildLoadedStates(state),
                _buildPagination(state),
              ]
            ],
          ),
        );
      },
    );
  }

  Widget _buildLoadedStates(ReportsLoadedState state) {
    return SliverList(
      delegate: SliverChildBuilderDelegate(
        childCount: state.participants.length,
        (context, index) {
          return ParticipantListTile(
            participant: state.participants[index],
          );
        },
      ),
    );
  }

  Widget _buildHeader() {
    return SliverPersistentHeader(
      delegate: _TableHeaderDelegate(),
      floating: true,
      pinned: true,
    );
  }

  Widget _buildFilters(int reportFilter) {
    return SliverToBoxAdapter(
      child: Padding(
        padding: const EdgeInsets.all(8.0),
        child: Align(
          alignment: Alignment.centerLeft,
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              LabeledWidget(
                label: 'Tipo de relatório',
                child: DropdownButton(
                  value: reportFilter,
                  items: const [
                    DropdownMenuItem(
                      value: 0,
                      child: Text(
                        'Participantes',
                      ),
                    ),
                    DropdownMenuItem(
                      value: 1,
                      child: Text('Participantes Credenciados'),
                    ),
                  ],
                  onChanged: _onChangeReportFilters,
                ),
              ),
              LabeledWidget(
                label: 'Filtrar por categoria',
                child: DropdownButton<int>(
                  value: 0,
                  items: const [],
                  hint: const Text('Selecione...'),
                  onChanged: _onChangeCategoryFilter,
                ),
              ),
              Padding(
                padding: const EdgeInsets.symmetric(vertical: 16.0),
                child: ElevatedButton.icon(
                  style: ElevatedButton.styleFrom(
                    backgroundColor: Colors.green,
                    fixedSize: const Size(250, 58),
                  ),
                  onPressed: _onExportList,
                  label: const Text('Exportar lista'),
                  icon: const FaIcon(FontAwesomeIcons.fileExcel),
                ),
              )
            ],
          ),
        ),
      ),
    );
  }

  void _onChangeReportFilters(int? value) {
    _bloc.add(
      ChangeReportFilterEvent(reportFilter: value ?? 0),
    );
  }

  void _onChangeCategoryFilter(int? value) {}

  Widget _buildLoadingSpinner() {
    return const SliverToBoxAdapter(
      child: SizedBox(
        height: 120,
        child: Center(
          child: CircularProgressIndicator(),
        ),
      ),
    );
  }

  Widget _buildPagination(ReportsLoadedState state) {
    return SliverToBoxAdapter(
      child: PaginationWidget(
        totalPages: state.totalPages,
        currentPage: state.currentPage,
        onChangePage: _onChangePage,
      ),
    );
  }

  _onChangePage(int index, int reportFilter) {
    _bloc.add(ChangePageEvent(page: index, reportFilter: reportFilter));
  }

  void _onExportList() {}
}

class _TableHeaderDelegate extends SliverPersistentHeaderDelegate {
  @override
  Widget build(
      BuildContext context, double shrinkOffset, bool overlapsContent) {
    return Container(
      padding: const EdgeInsets.symmetric(horizontal: 2),
      decoration: BoxDecoration(
        border: Border.all(),
        color: Colors.white,
      ),
      child: Row(
        children: [
          _buildHeaderItem('Nome Completo'),
          Container(
            width: 1,
            height: 28,
            color: HubConnectColors.primary,
          ),
          _buildHeaderItem('Categoria'),
          Container(
            width: 1,
            height: 28,
            color: HubConnectColors.primary,
          ),
          _buildHeaderItem('Email'),
        ],
      ),
    );
  }

  @override
  double get maxExtent => 28;

  @override
  double get minExtent => 28;

  @override
  bool shouldRebuild(covariant SliverPersistentHeaderDelegate oldDelegate) =>
      true;

  Expanded _buildHeaderItem(String title) {
    return Expanded(
      child: Text(
        title,
        textAlign: TextAlign.center,
        style: HubConnectTypography.bold20.copyWith(fontSize: 16),
      ),
    );
  }
}
