![logo(1)](https://github.com/user-attachments/assets/73b24b86-51e5-440c-ad61-7d4ba396df8e)
# UniEventos - Mobile App for Event Ticket Sales

**UniEventos** is a mobile application built using Kotlin and Jetpack Compose for selling event tickets across various cities in Colombia. This project was developed as part of a final assignment for the Mobile Apps Development course in the Software Engineering program.

## Table of Contents

- [Project Description](#project-description)
- [Features](#features)
- [Technologies Used](#technologies-used)
- [Getting Started](#getting-started)
- [Contributors](#contributors)
- [License](#license)

## Project Description

UniEventos is a platform where users can purchase tickets for concerts, theaters, sports, and other types of events. The app allows users to register, log in, and browse events. Administrators can manage events and coupons while customers can purchase tickets, redeem coupons, and view their order history.

The app is divided into two user types:

1. **Administrators**: Manage events, create coupons, and handle platform configurations.
2. **Clients**: Register, log in, browse events, purchase tickets, and apply discount coupons.

## Features

### Admin Features:
- Log in and manage events.
- Create special coupons with custom discounts for specific events or dates.
- Reset password via email.
  
### Client Features:
- Register and log in using email verification.
- Browse and filter events by name, type, and city.
- Add tickets to the shopping cart and complete purchases.
- Redeem discount coupons at checkout.
- View purchase history.
- Manage account details and reset passwords.

### Event Management:
- Events include details like name, description, type, city, address, date, images, and ticket availability.
- Clients can select events, choose the number of tickets and seat categories, and proceed to payment.

### Coupon System:
- New users receive a 15% discount coupon upon registration.
- First-time purchasers receive a 10% discount coupon.

### Data Persistence:
- Firebase is used for storing event data, user data, and images.

## Technologies Used

- **Kotlin**
- **Jetpack Compose**
- **Firebase** for data persistence and user authentication
- **Material Design** for UI styling
- **Jetpack Navigation** for handling in-app navigation
- **Remember and RememberSaveable** for managing state in Composables

## Getting Started

### Prerequisites

- [Android Studio](https://developer.android.com/studio) installed on your local machine.
- A Firebase project for database and authentication setup.

### Installation

1. Clone the repository:
   ```
   git clone https://github.com/your-username/uni-eventos.git
   ```
2. Open the project in Android Studio.
3. Set up Firebase following the instructions in the [Firebase documentation](https://firebase.google.com/docs/android/setup).
4. Build and run the project on an emulator or connected device.

### Running the App

1. Launch the app and register as a new user.
2. Log in with the registered credentials.
3. Browse available events, add tickets to the cart, and complete purchases.
4. For admin access, use the pre-loaded credentials available in the Firebase database.

## Contributors

- **Nicolás Vargas Cardona** - [GitHub Profile](https://github.com/VargasCardona)
- **Mateo Loaiza García** - [GitHub Profile](https://github.com/Matthub05)

## License

This project is licensed under the GPL License. See the [LICENSE](LICENSE) file for details.
