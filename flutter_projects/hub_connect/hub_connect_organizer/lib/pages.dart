import 'package:get/route_manager.dart';
import 'package:hub_connect_common/screens/login_screen/login_screen.dart';
import 'package:hub_connect_common/screens/splash_screen/splash_screen.dart';
import 'package:hub_connect_common/bindings/main_binding.dart';
import 'package:hub_connect_organizer/screens/home_screen.dart/home_screen.dart';

class OrganizerPages {
  static final pages = [
    GetPage(
      name: '/login',
      page: () => LoginScreen(
        onSuccessfulLogin: () {
          Get.offAllNamed('/home');
        },
      ),
      binding: MainBinding(),
    ),
    GetPage(
      name: '/',
      transition: Transition.fadeIn,
      page: () => SplashScreen(
        onAuth: () {
          Get.offAllNamed('/home');
        },
        noAuth: () {
          Get.offAllNamed('/login');
        },
      ),
      binding: MainBinding(),
    ),
    GetPage(
      name: '/home',
      page: () => const HomeScreen(),
      transition: Transition.fadeIn,
      binding: MainBinding(),
    ),
  ];
}
