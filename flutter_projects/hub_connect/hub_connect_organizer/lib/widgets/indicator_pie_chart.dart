import 'package:fl_chart/fl_chart.dart';
import 'package:flutter/material.dart';
import 'package:hub_connect_common/themes/colors/colors.dart';
import 'package:hub_connect_sdk/core/domain/entities/indicators_entity.dart';

class IndicatorPieChart extends StatefulWidget {
  const IndicatorPieChart({
    Key? key,
    required this.indicators,
  }) : super(key: key);

  final IndicatorsEntity indicators;

  @override
  State<IndicatorPieChart> createState() => _IndicatorPieChartState();
}

class _IndicatorPieChartState extends State<IndicatorPieChart>
    with TickerProviderStateMixin {
//animation things
  late AnimationController _animationController;
  late Animation<double> _animation;

  @override
  void initState() {
    _animationController = AnimationController(
      vsync: this,
      duration: const Duration(milliseconds: 300),
    );
    _animation = Tween<double>(begin: 0, end: 1).animate(_animationController);
    _animationController.forward();
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    return LayoutBuilder(builder: (context, constraints) {
      return AnimatedBuilder(
        builder: (context, child) {
          return PieChart(
            swapAnimationDuration: const Duration(milliseconds: 300),
            swapAnimationCurve: Curves.linear,
            PieChartData(
              pieTouchData: PieTouchData(enabled: true),
              sections: [
                _buildSlice(
                  constraints,
                  HubConnectColors.successColor,
                  widget.indicators.presentParticipants.toDouble() *
                      _animation.value,
                  widget.indicators.totalParticipants.toDouble(),
                ),
                _buildSlice(
                  constraints,
                  HubConnectColors.errorColor,
                  widget.indicators.absentParticipants.toDouble(),
                  widget.indicators.totalParticipants.toDouble(),
                ),
                _buildSlice(
                  constraints,
                  HubConnectColors.primary,
                  widget.indicators.accreditedParticipants.toDouble() *
                      _animation.value,
                  widget.indicators.totalParticipants.toDouble(),
                ),
              ],
            ),
          );
        },
        animation: _animation,
      );
    });
  }

  PieChartSectionData _buildSlice(
    BoxConstraints constraints,
    Color color,
    double value,
    double total,
  ) {
    return PieChartSectionData(
      color: color,
      value: value,
      title: _stringPercentage(
        value.toInt(),
        widget.indicators.totalParticipants,
      ),
      radius: constraints.maxHeight / 2,
      titleStyle: const TextStyle(
        fontSize: 18,
        fontWeight: FontWeight.bold,
        color: Colors.white,
      ),
    );
  }

  String _stringPercentage(int absentParticipants, int totalParticipants) {
    if (totalParticipants == 0) return '0%';
    return '${(absentParticipants / totalParticipants * 100).toStringAsFixed(0)}%';
  }
}
