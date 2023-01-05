import 'package:flutter/material.dart';
import 'package:hub_connect_organizer/tabs/dashboard/dashboard.dart';
import 'package:hub_connect_organizer/tabs/reports/reports.dart';
import 'package:hub_connect_organizer/widgets/home_drawer.dart';

class HomeScreen extends StatefulWidget {
  const HomeScreen({super.key});

  @override
  State<HomeScreen> createState() => _HomeScreenState();
}

class _HomeScreenState extends State<HomeScreen> {
  final PageController _pageController = PageController();
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(),
      drawer: HomeDrawer(
        pageController: _pageController,
      ),
      body: PageView(
        physics: const NeverScrollableScrollPhysics(),
        controller: _pageController,
        children: const [
          DashboardTab(),
          ReportsTab(),
        ],
      ),
    );
  }
}
