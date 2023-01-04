import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:hub_connect_common/themes/colors/colors.dart';
import 'package:hub_connect_sdk/core/domain/repositories/auth_repository.dart';

class HomeDrawer extends StatelessWidget {
  final PageController pageController;
  const HomeDrawer({
    Key? key,
    required this.pageController,
  }) : super(key: key);

  int get _currentPage => pageController.page!.round();

  @override
  Widget build(BuildContext context) {
    return Drawer(
      backgroundColor: HubConnectColors.primary,
      child: Column(
        children: [
          DrawerHeader(
            child: Image.asset(
              'assets/logo_secondary_letter.png',
              package: 'hub_connect_common',
            ),
          ),
          Expanded(
            child: ListView(
              padding: const EdgeInsets.only(left: 16),
              children: [
                ListTile(
                  leading: const Icon(Icons.home),
                  title: const Text('Início'),
                  selected: _currentPage == 0,
                  onTap: () => _navigate(0, context),
                ),
                const SizedBox(
                  height: 16,
                ),
                ListTile(
                  leading: const Icon(Icons.file_copy),
                  title: const Text('Relatórios'),
                  selected: _currentPage == 1,
                  onTap: () => _navigate(1, context),
                ),
                ListTile(
                  leading: const Icon(Icons.logout),
                  title: const Text('Sair'),
                  selected: _currentPage == -1,
                  onTap: _logout,
                ),
              ],
            ),
          ),
        ],
      ),
    );
  }

  void _navigate(int page, BuildContext context) {
    pageController.jumpToPage(page);
    Navigator.pop(context);
  }

  void _logout() async {
    final authRepository = Get.find<AuthRepository>();
    await authRepository.logout();
    Get.offAllNamed('/');
  }
}
