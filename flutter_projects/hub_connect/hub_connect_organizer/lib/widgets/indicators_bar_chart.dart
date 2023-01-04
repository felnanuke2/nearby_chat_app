import 'package:fl_chart/fl_chart.dart';
import 'package:flutter/material.dart';
import 'package:hub_connect_common/themes/colors/colors.dart';
import 'package:hub_connect_common/themes/typography/typography.dart';
import 'package:hub_connect_sdk/core/domain/entities/indicators_entity.dart';

class IndicatorsBarChart extends StatelessWidget {
  const IndicatorsBarChart({
    Key? key,
    required this.indicators,
  }) : super(key: key);
  final IndicatorsEntity indicators;

  @override
  Widget build(BuildContext context) {
    return BarChart(
      BarChartData(
        alignment: BarChartAlignment.spaceEvenly,
        barTouchData: BarTouchData(
          enabled: true,
          touchTooltipData: BarTouchTooltipData(
            tooltipBgColor: Colors.transparent,
            tooltipPadding: const EdgeInsets.all(0),
            tooltipMargin: 8,
            getTooltipItem: _buildTooltipItem,
          ),
        ),
        borderData: FlBorderData(
          show: true,
          border: const Border(
            /// Top border
            top: BorderSide.none,

            /// Bottom border
            bottom: BorderSide(
              width: 1,
            ),

            /// Left border
            left: BorderSide(
              width: 1,
            ),

            /// Right border
            right: BorderSide.none,
          ),
        ),
        gridData: FlGridData(
          show: false,
        ),
        titlesData: FlTitlesData(
          leftTitles: AxisTitles(
            sideTitles: SideTitles(
              showTitles: true,
              interval: 4,
              reservedSize: 25,
              getTitlesWidget: (value, meta) => Text(
                value.toInt().toString(),
                style: HubConnectTypography.normal20.copyWith(
                  fontSize: 14,
                ),
              ),
            ),
          ),
          rightTitles: AxisTitles(
            sideTitles: SideTitles(
              showTitles: false,
            ),
          ),
          topTitles: AxisTitles(
            sideTitles: SideTitles(
              showTitles: false,
            ),
          ),
          bottomTitles: AxisTitles(
            sideTitles: SideTitles(
              showTitles: true,
              getTitlesWidget: _buildBottomTitles,
            ),
          ),
        ),
        groupsSpace: 65,
        barGroups: [
          BarChartGroupData(
            x: 0,
            barRods: [
              _buildBar(
                HubConnectColors.successColor,
                indicators.presentParticipants.toDouble(),
              ),
            ],
          ),
          BarChartGroupData(
            x: 1,
            barRods: [
              _buildBar(
                HubConnectColors.primary,
                indicators.accreditedParticipants.toDouble(),
              ),
            ],
          ),
          BarChartGroupData(
            x: 2,
            barRods: [
              _buildBar(
                HubConnectColors.errorColor,
                indicators.absentParticipants.toDouble(),
              ),
            ],
          ),
        ],
      ),
    );
  }

  BarChartRodData _buildBar(Color color, double value) {
    return BarChartRodData(
      width: 30,
      borderRadius: const BorderRadius.only(
        topLeft: Radius.circular(4),
        topRight: Radius.circular(4),
      ),
      toY: value,
      color: color,
    );
  }

  Widget _buildBottomTitles(double value, TitleMeta meta) {
    switch (value.toInt()) {
      case 0:
        return Text(
          'Presentes',
          style: HubConnectTypography.normal20.copyWith(
            fontSize: 14,
          ),
        );
      case 1:
        return Text(
          'Credenciados',
          style: HubConnectTypography.normal20.copyWith(
            fontSize: 14,
          ),
        );
      case 2:
        return Text(
          'Ausentes',
          style: HubConnectTypography.normal20.copyWith(
            fontSize: 14,
          ),
        );
      default:
        return Container();
    }
  }

  BarTooltipItem? _buildTooltipItem(BarChartGroupData group, int groupIndex,
      BarChartRodData rod, int rodIndex) {
    String text = '';
    switch (groupIndex) {
      case 0:
        text = 'Presentes: ';
        break;
      case 1:
        text = 'Credenciados: ';
        break;
      case 2:
        text = 'Ausentes: ';
        break;
    }

    return BarTooltipItem(
      '$text${rod.toY.round()}',
      HubConnectTypography.normal20.copyWith(
        fontSize: 14,
      ),
    );
  }
}
