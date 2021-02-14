# Poriskar

Project for CSS Hacks 1.0
___

>USER APP

- The user app will contain both Login & Signup activities for both existing and new users.
- New users will have to enter their name, phone no, login password.
- In the next very important step, users will have to mark their home address by either marking on the map or using gps to automatically mark their address.
- Then they will have to choose one of the several admin defined routes of garbage trucks. For convenience, users must choose the route closest to their home address.
- They will then be taken to the home activity.
In home activity, the dashboard will show if a garbage truck is on its way in the selected route. Home activity will contain a bottom navigation to navigate across various fragments. On pressing a button, a user will be able to mark any public place which he/she thinks lots of unchecked garbage is disposed of. The user will then click/upload pictures of that place and write the address of that place. This information will be shown in the admin website where an administrator will sort out and approve places.

## TECH STACK USED
Tools: Android Studio, Google Maps API.
Client-Side: Java, XML, Material UI.
Database: Firebase.

## FUTURE IMPLEMENTATIONS
- Gamification of User App to make it more appealing.
- Possible rewards for motivating users for helping in cleanliness of public places. Though it will solely depend on Municipal Board whether to implement it or not.
- Better authentication method.

___

> MODERATOR APP

## Route Assign
- The route assign component functions the same way as the Uber App. Unlike the Uber App, which shows the location of passengers and the routes leading to them, to the driver, the route assign component shows the location of registered homes and their routes, to the driver.   


## Social Media for drivers
- The social media for drivers is one of the main features of our app. Unlike instagram and facebook, which display the pictures of a user’s followers and friends, this social media app displays pictures of localities having piles of garbage. Let us look at how this system functions. The registered users of our app will have the ability to click pictures of places having piles of garbage and wastes, and upload them to our database. Our admin panel will then screen these pictures. The pictures, which will get the approval of the admin panel, will then be posted on the social media app, along with their location. The drivers can then select a locality and clean up the place.

## TECH STACK USED
Client-side: 	XML, Material UI
Backend:		Java
Database:		Firebase
Tools:  		Android Studio, Google Maps API	

## FUTURE IMPLEMENTATIONS
- Gamification of Driver App to make it more appealing.
- Possible rewards for drivers for clean public places. Though it will solely depend on Municipal Board whether to implement it or not.
- Better authentication method.

> ADMIN WEBSITE

## Prerequisites

Before you begin, ensure you have met the following requirements:

* You have installed the latest version of `nodejs`

## Run `Poriskar` locally

```bash
git clone https://github.com/Bucephalus-lgtm/Poriskar.git
```

Then redirect to the directory `Poriskar`.

```bash
cd Poriskar
```

Inside here, you will find our code, but the dependencies required are missing, so you get to install them

```bash
npm install
```

Now, since the current branch you have seen is `master` and our operational code lies in `admin-backend` branch, run

```bash
git checkout admin-backend
```



After all the dependenices get installed in your node_modules file, you can run the app locally from your favorite text editor by running

```bash
npm start 
```

## Contributing to `Poriskar`

To contribute to `Poriskar`, follow these steps:

1. Fork this repository.
2. Create a branch: `git checkout -b <branch_name>`.
3. Make your changes and commit them: `git commit -m '<commit_message>'`
4. Push to the original branch: `git push origin <project_name>/<location>`
5. Create the pull request.

Alternatively see the GitHub documentation on [creating a pull request](https://help.github.com/en/github/collaborating-with-issues-and-pull-requests/creating-a-pull-request).

## Contributors

Currently, our team consists of five people who have actively contributed to this project:

* [@Pratik Gupta](https://github.com/inomag)
* [@Abhimayu Giri](https://github.com/abhimanyunlp)
* [@Nilesh Bhandari](https://github.com/css-is-tough)
* [@Bhargab Nath](https://github.com/Bucephalus-lgtm)

