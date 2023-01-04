import 'package:fl_chart/fl_chart.dart';
import 'package:flutter/material.dart';
import 'package:hub_connect_common/themes/colors/colors.dart';
import 'package:hub_connect_common/themes/typography/typography.dart';
import 'package:hub_connect_sdk/core/domain/entities/indicators_entity.dart';

class IndicatorBarChartHorizontal extends StatelessWidget {
  const IndicatorBarChartHorizontal({
    Key? key,
    required this.indicators,
  }) : super(key: key);

  final IndicatorsEntity indicators;
  @override
  Widget build(BuildContext context) {
    return LineChart(
      LineChartData(
          gridData: FlGridData(
            show: false,
          ),
          maxY: 4,
          minX: 0,
          minY: -1,
          maxX: indicators.totalParticipants.toDouble(),
          lineTouchData: LineTouchData(
            enabled: true,
            touchTooltipData: LineTouchTooltipData(
              tooltipBgColor: Colors.transparent,
              tooltipPadding: const EdgeInsets.all(0),
              tooltipMargin: 8,
              getTooltipItems: _buildTooltipItems,
              showOnTopOfTheChartBoxArea: true,
            ),
          ),
          titlesData: FlTitlesData(
            leftTitles: AxisTitles(
              sideTitles: SideTitles(
                showTitles: true,
                reservedSize: 80,
                interval: 0.5,
                getTitlesWidget: _buildBottomTitles,
              ),
            ),
            topTitles: AxisTitles(
              sideTitles: SideTitles(
                showTitles: false,
              ),
            ),
            rightTitles: AxisTitles(
              sideTitles: SideTitles(
                showTitles: false,
              ),
            ),
            bottomTitles: AxisTitles(
              sideTitles: SideTitles(
                showTitles: true,
                interval: indicators.totalParticipants.toDouble(),
              ),
            ),
          ),
          borderData: FlBorderData(
            border: const Border(
              bottom: BorderSide(
                width: 1,
              ),
              left: BorderSide(
                width: 1,
              ),
              right: BorderSide.none,
              top: BorderSide.none,
            ),
          ),
          lineBarsData: [
            _buildLineBar(
              0,
              indicators.presentParticipants.toDouble(),
              HubConnectColors.successColor,
            ),
            _buildLineBar(
              1.5,
              indicators.accreditedParticipants.toDouble(),
              HubConnectColors.primary,
            ),
            _buildLineBar(
              3,
              indicators.absentParticipants.toDouble(),
              HubConnectColors.errorColor,
            ),
          ]),
    );
  }

  LineChartBarData _buildLineBar(double position, double value, Color color) {
    return LineChartBarData(
      spots: [..._generateSpots(position.toDouble(), value)],
      color: color,
      isCurved: false,
      barWidth: 30,
      isStrokeCapRound: false,
      dotData: FlDotData(
        show: false,
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
    Widget widget = Container();

    switch ((value * 100).toInt()) {
      case 0:
        widget = Text(
          'Presentes',
          style: HubConnectTypography.normal20.copyWith(
            fontSize: 14,
          ),
        );
        break;
      case 150:
        widget = Text(
          'Credenciados',
          style: HubConnectTypography.normal20.copyWith(
            fontSize: 14,
          ),
        );
        break;
      case 300:
        widget = Text(
          'Ausentes',
          style: HubConnectTypography.normal20.copyWith(
            fontSize: 14,
          ),
        );
        break;
      default:
        return Container();
    }
    return Align(
      alignment: Alignment.centerRight,
      child: Padding(
        padding: const EdgeInsets.all(4.0),
        child: widget,
      ),
    );
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

  List<LineTooltipItem?> _buildTooltipItems(List<LineBarSpot> touchedSpots) {
    List<LineTooltipItem?> items = [];
    touchedSpots.forEach(
      (element) {
        String text = '';
        switch ((element.y * 100).round()) {
          case 0:
            text = 'Presentes: ';
            break;
          case 150:
            text = 'Credenciados: ';
            break;
          case 300:
            text = 'Ausentes: ';
            break;
        }
        int participants = 0;
        switch ((element.y * 100).round()) {
          case 0:
            participants = indicators.presentParticipants;
            break;
          case 150:
            participants = indicators.accreditedParticipants;
            break;
          case 300:
            participants = indicators.absentParticipants;
            break;
        }
        items.add(
          LineTooltipItem(
            '$text$participants',
            HubConnectTypography.bold20.copyWith(
              fontSize: 14,
            ),
          ),
        );
      },
    );
    return items;
  }

  /// generate a list of spot to draw a line for eatch 0.2
  List<FlSpot> _generateSpots(double positionY, double value) {
    List<FlSpot> spots = [];
    for (var i = 0.0; i < value; i += 0.1) {
      spots.add(FlSpot(i, positionY));
    }
    return spots;
  }
}
